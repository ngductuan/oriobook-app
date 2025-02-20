<template>
  <div class="author-details-section container">
    <div class="row">
      <div class="col-12 bwp-author-heading">
        <div class="author-image">
          <div class="images">
            <img :src="author.image" :alt="author.name" />
          </div>
        </div>

        <div class="author-content">
          <div class="author-title">
            <h2>
              <span>{{ author.name }}</span>
            </h2>
          </div>
          <div class="author-description">
            <div class="term-description ellipsis-custom-6">
              <p>
                {{ author.description }}
              </p>
            </div>
          </div>
          <div class="author-infomation">
            <ul class="author-info">
              <li><label>Style:</label> {{ author.style }}</li>
              <li><label>Address:</label> {{ author.address }}</li>
              <li>
                <label>Year of Birth:</label>
                {{ convertDateFormat(author.date_of_birth) }}
              </li>
              <li><label>Gender:</label> {{ author.gender }}</li>
              <li>
                <label>Published Book:</label> {{ author.published_book }}
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="title mt-5 ps-4">Author's books</div>
      <div
        class="row gx-2 gy-3 row-products js-container-author-product justify-content-center"
      >
        <div class="row">
          <div class="col m-20" v-for="product in products" :key="product">
            <HomeProductCard :product="product" />
          </div>
        </div>
      </div>
      <Pagination :totalPages="totalPages" :curPage="curPage" />
    </div>
  </div>
</template>

<script>
import ShopProduct from "../product/ShopProduct.vue";
import { convertDateFormat } from "@/helpers/helperFunctions";
import HomeProductCard from "../product/HomeProductCard.vue";
import { ref, onMounted } from "vue";
import axios from "../../config/axios";
import { useRoute } from "vue-router";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import Pagination from "@/components/Pagination.vue";

export default {
  name: "Author",
  components: {
    ShopProduct,
    HomeProductCard,
    Pagination,
  },
  props: ["author"],
  setup() {
    const route = useRoute();
    const products = ref([]);
    const id = ref(route.params.id);
    const totalPages = ref(0);
    let page = 1;
    const curPage = ref(page);
    const perPage = 5;

    const paginationControl = () => {
      $(".js-number-link").click(async function (e) {
        e.preventDefault();
        page = parseInt($(this).text());
        requestPage();
      });

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
    };

    const requestPage = async () => {
      try {
        displayLoading(".js-container-author-product", -50, 0);
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/product/productAuthor/${id.value}?page=${page}&perPage=${perPage}`
        );
        curPage.value = page;
        products.value = response.data.products;
        totalPages.value = response.data.totalPages;
        removeLoading();
      } catch (error) {
        console.error(error);
      }
    };

    onMounted(async () => {
      try {
        await requestPage();
        paginationControl();
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });
    return {
      products,
      convertDateFormat: convertDateFormat,
      curPage,
      totalPages,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/author/authors_detail.scss";
</style>
