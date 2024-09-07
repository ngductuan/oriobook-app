<template>
  <div class="col-3">
    <form style="padding-left: 20px">
      <h6 style="margin-top: 56px" class="text-uppercase">Categories:</h6>
      <div
        class="form-group form-check"
        v-for="category in categories"
        :key="category.id"
      >
        <input
          type="radio"
          class="form-check-input js-category-option"
          :id="category.id"
          @click="selectCategory(category.name, category.id)"
        />
        <div class="d-flex align-items-center justify-content-between">
          <label class="form-check-label" :for="category.id"
            >{{ category.name }} ({{ category.numProducts }})</label
          >
          <i
            class="fa-regular fa-chevron-down"
            :id="'sub-container-btn-' + category.id"
            data-v-9d9a21ac=""
            :class="category?.children?.length > 0 ? '' : 'd-none'"
            role="button"
            @click="showSubCategory(category.id)"
          ></i>
        </div>
        <div :id="'sub-container-' + category.id" class="sub-container d-none">
          <div
            class="form-group form-check ml-4"
            v-for="subCate in category?.children"
            :key="subCate.id"
          >
            <input
              type="radio"
              class="form-check-input js-category-option"
              :id="subCate.id"
              @click="selectCategory(subCate.name, subCate.id)"
            />
            <label class="form-check-label" :for="subCate.id">
              {{ subCate.name }} ({{ subCate.numProducts }})
            </label>
          </div>
        </div>
      </div>

      <br />

      <h6 class="text-uppercase">Author:</h6>
      <div
        class="form-group form-check"
        v-for="author in authors"
        :key="author.id"
      >
        <input
          type="radio"
          class="form-check-input js-author-option"
          :id="author.id"
          @click="selectAuthor(author.name, author.id)"
        />
        <label class="form-check-label" :for="author.id"
          >{{ author.name }} ({{ author.publishedBook }})</label
        >
      </div>

      <br />
    </form>
    <br />
  </div>
  <div class="shop-product" :class="author_page ? 'col-12' : 'col-9'">
    <div class="row gx-0">
      <div
        class="col-12 woocommerce-ordering pwb-dropdown dropdown show px-3"
        :class="{ 'no-show': author_page }"
      >
        <span
          class="pwb-dropdown-toggle dropdown-toggle"
          data-toggle="dropdown"
          aria-expanded="true"
          @click="clickDropdown"
        >
          {{ sort.label }}
        </span>
        <ul
          class="pwb-dropdown-menu dropdown-menu p-0"
          :class="[{ show: toggleMenu }]"
          x-placement="bottom-start"
        >
          <li
            v-for="option in sortingOptions"
            :key="option.value"
            :data-value="option.value"
            :class="{ active: sort.value === option.value }"
            class="dropdown-menu-option"
          >
            <a role="button" @click="selectSorting(option)">
              {{ option.label }}
            </a>
          </li>
        </ul>
      </div>
      <div class="row gx-3 px-0 js-product-wrapper" style="min-height: 826px">
        <div
          class="mt-3"
          :class="author_page ? 'm-20' : 'col-3'"
          v-for="product in products"
          :key="product.id"
        >
          <HomeProductCard :product="product" @reloadcart="addCart" />
        </div>
        <div
          class="d-flex mt-5 justify-content-center"
          :class="totalPages > 0 || isLoading ? 'd-none' : ''"
        >
          <p class="woocommerce-info">
            No products were found matching your selection.
          </p>
        </div>
      </div>
    </div>
    <Pagination :totalPages="totalPages" :curPage="curPage" />
  </div>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import axios from "../../config/axios";
import HomeProductCard from "./HomeProductCard.vue";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import { scrollToTop } from "@/helpers/helperFunctions";
import Pagination from "@/components/Pagination.vue";
import { SortEnum } from "@/types/enum.type";

export default {
  name: "ShopProduct",
  components: {
    HomeProductCard,
    Pagination,
  },
  props: ["author_page"],
  setup(props, { emit }) {
    const products = ref([]);
    const totalPages = ref(0);
    const author_page = ref(props.author_page);
    const route = useRoute();
    const toggleMenu = ref(false);
    const isLoading = ref(true);

    const sortingOptions = [
      { value: "", label: "Default sorting" },
      // { value: "rating", label: "Sort by average rating" },
      { value: SortEnum.DESC, label: "Sort by latest", key: "sortByDate" },
      {
        value: SortEnum.ASC,
        label: "Sort by price: low to high",
        key: "sortByPrice",
      },
      {
        value: SortEnum.DESC,
        label: "Sort by price: high to low",
        key: "sortByPrice",
      },
    ];
    // Sidebar shop
    const categories = ref([]);
    const authors = ref([]);
    const showSubCategory = function (idSubContainer) {
      $(`#sub-container-btn-${idSubContainer}`).toggleClass("active");
      $(`#sub-container-${idSubContainer}`).toggleClass("d-none");
    };
    let page = 1;
    const curPage = ref(page);
    const perPage = 8;
    const sort = ref({
      value: "default",
      label: "Default sorting",
    });

    // const routeQuery = ref(route);
    const searchQuery = ref(route.query.search || "");

    const queryObject = {
      productName: searchQuery.value,
      categoryId: "",
      authorId: "",
      // sort: sort.value.value,
      sortByPrice: "",
      sortByDate: "",
    };

    // watch(routeQuery, (newVal, oldVal) => {
    //   // Check if the route query has changed
    //   if (newVal.query.search !== oldVal.query.search) {
    //     // Update the searchQuery value
    //     searchQuery.value = newVal.query.search || "";
    //     // Do something when searchQuery changes
    //     console.log("searchQuery changed:", searchQuery.value);
    //   }
    // });

    const clickDropdown = () => {
      toggleMenu.value = !toggleMenu.value;
    };

    const selectCategory = async (newCategory, newCategoryID) => {
      console.log("Select category:", newCategory, newCategoryID);
      if (queryObject.categoryId == newCategoryID) {
        $(`#${newCategoryID}`).prop("checked", false);
        queryObject.categoryId = "";
      } else {
        queryObject.categoryId = newCategoryID;
      }
      $(`.js-category-option:not(#${newCategoryID})`).prop("checked", false);
      page = 1;
      await requestPage();
      paginationControl();
    };

    const selectAuthor = async (newAuthor, newAuthorID) => {
      if (queryObject.authorId == newAuthorID) {
        $(`#${newAuthorID}`).prop("checked", false);
        queryObject.authorId = "";
      } else {
        queryObject.authorId = newAuthorID;
        $(`#${newAuthorID}`).prop("checked", true);
      }
      $(`.js-author-option:not(#${newAuthorID})`).prop("checked", false);
      page = 1;
      await requestPage();
      paginationControl();
    };

    const selectSorting = async (option) => {
      // sort.value = option;
      toggleMenu.value = false;
      // queryObject.sort = option.value;

      queryObject.sortByDate = "";
      queryObject.sortByPrice = "";

      if (option.key) {
        queryObject[option.key] = option.value;
      }

      page = 1;
      await requestPage();
      paginationControl();
    };

    const requestPage = async () => {
      if (!isLoading.value) {
        displayLoading(".js-product-wrapper", -32);
      }

      try {
        // scrollToTop(440);
        const params = new URLSearchParams(queryObject).toString();
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/products?page=${
            page - 1
          }&limit=${perPage}&${params}`
        );
        // console.log(page);
        curPage.value = page;
        products.value = response.data.data;
        // console.log("products.value", response.data);
        totalPages.value = response.data.totalPages;
        removeLoading();
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    };

    const paginationControl = () => {
      $(".js-number-link").click(async function (e) {
        e.preventDefault();
        page = parseInt($(this).text());
        requestPage();
      });
    };

    $(() => {
      $(".js-prev-link").click(async function (e) {
        e.preventDefault();
        page = page > 1 ? page - 1 : page;
        requestPage();
      });

      $(".js-next-link").click(async function (e) {
        e.preventDefault();
        page = page < totalPages.value ? page + 1 : page;
        requestPage();
      });
    });

    onMounted(async () => {
      displayLoading(".js-product-wrapper", -32);
      try {
        await requestPage();
        isLoading.value = false;

        let response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/categories`
        );
        categories.value = response.data.data;
        // console.log("categories", categories.value);

        // Lấy tất cả tác giả
        response = await axios.get(`${process.env.VUE_APP_MAIN_URL}/authors`);
        authors.value = response.data.data;
        paginationControl();
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });

    const addCart = () => {
      emit("reloadcart");
    };

    return {
      toggleMenu,
      sort,
      sortingOptions,
      clickDropdown,
      selectSorting,
      author_page,
      products,
      totalPages,
      curPage,
      authors,
      categories,
      showSubCategory,
      selectCategory,
      selectAuthor,
      addCart,
      isLoading,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/product/shop_product.scss";
@import "@/styles/SliderShop.scss";
</style>
