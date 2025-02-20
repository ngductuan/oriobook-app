<template>
  <div class="product-details container">
    <div class="product-content row mt-5">
      <div class="col-6 justify-content-center d-flex">
        <img
          :src="isLoading ? '/placeholder/loading-image.png' : product.image"
          :alt="product.name"
          class="col-7 product-img"
          style="height: 600px"
        />
      </div>
      <div class="product-info col-6 ms-auto">
        <div class="product-title">{{ product.name }}</div>
        <div class="product-rating">
          {{ product.rating }} / 5
          <i class="fa-solid fa-star" style="color: orange"></i>
        </div>
        <div class="product-price mt-4">
          <strong>${{ product.price }}</strong>
        </div>
        <p class="product-desc">
          <span
            >Author:
            <a
              :href="'/authors/' + idAuthor"
              class="product-author-name"
              @click="handleLinkClick('/authors')"
              >{{ nameAuthor }}</a
            ></span
          >
        </p>
        <p class="product-desc">
          <span>Category: {{ product.category_name }}</span>
        </p>
        <p class="product-desc">
          <span>Description: {{ product.description }}</span>
        </p>
        <p class="product-desc">
          <span>Stock: {{ product.stock }}</span>
        </p>

        <div class="d-flex pt-5">
          <div class="product-quantity row gx-0 me-2">
            <button
              class="col"
              @click="changeQuantity('minus')"
              :disabled="isDisabled(quantity, 1)"
            >
              <i class="fa-light fa-minus"></i>
            </button>
            <input
              type="text"
              class="col text-center"
              disabled
              :value="quantity"
            />
            <button
              class="col"
              @click="changeQuantity('plus')"
              :disabled="isDisabled(quantity, product.stock)"
            >
              <i class="fa-light fa-plus"></i>
            </button>
          </div>
          <button
            class="product-add-cart-btn"
            @click="AddProduct(product._id, quantity, product.stock)"
          >
            <span class="text-uppercase">Add to cart</span>
          </button>
        </div>
        <button
          class="product-quick-buy-btn"
          @click="BuyNow(product._id, quantity, product.stock)"
        >
          <span class="text-uppercase">Buy now</span>
        </button>

        <ul class="product-shipping-delivers d-flex">
          <li class="product-shipping">
            <div class="content-info">
              <div class="content">
                <i class="fa-light fa-box"></i>
                <p>Free worldwide shipping on all orders over $100</p>
              </div>
            </div>
          </li>
          <li class="product-delivers">
            <div class="content-info">
              <div class="content">
                <i class="fa-light fa-clock-three"></i>
                <p>
                  Delivers in: 3-7 Working Days
                  <a href="/refund-policy">Shipping &amp; Return </a>
                </p>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!-- <tabProduct :product="product" /> -->
    <div class="row mt-5 gx-2 justify-content-center">
      <div class="col-12 title">Related Products</div>

      <div class="js-related-product row">
        <div
          class="col-4 mt-3 m-20"
          v-for="relatedProduct in relatedProducts"
          :key="relatedProduct.id"
        >
          <HomeProductCard :product="relatedProduct" />
        </div>
      </div>
      <Pagination :totalPages="totalPages" :curPage="curPage" />
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from "vue";
import axios from "../config/axios";

import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import HomeProductCard from "@/components/product/HomeProductCard.vue";
import tabProduct from "@/components/tabProduct.vue";
import { useRoute } from "vue-router";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import Pagination from "@/components/Pagination.vue";

export default {
  name: "ProductDetails",
  inject: ["eventBus"],
  components: {
    HomeProductCard,
    tabProduct,
    Pagination,
  },
  setup() {
    const route = useRoute();
    const id = ref(route.params.id);
    const product = ref({});
    const relatedProducts = ref([]);
    const nameAuthor = ref("");
    const idAuthor = ref("");
    const isLoading = ref(true);

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

    const requestProductDetails = async () => {
      isLoading.value = true;
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/products/${id.value}`
        );
        isLoading.value = false;

        console.log("response (requestProductDetails)", response);

        product.value = response.data;
        product.value.category_name = product.value.categoryNode.name;
        nameAuthor.value = product.value.authorNode.name;
        idAuthor.value = product.value.authorNode.id;
      } catch (error) {
        console.error(error);
      }
    };

    const requestPage = async () => {
      try {
        displayLoading(".js-related-product", -50, 0);
        const res = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/products?page=${
            page - 1
          }&limit=${perPage}&categoryId=${product?.value?.categoryNode?.id}`
        );

        console.log("response (requestPage)", res);
        const mainData = res.data;

        relatedProducts.value = mainData?.data;
        curPage.value = mainData.currentPage + 1;
        totalPages.value = mainData.totalPages;
        removeLoading();
      } catch (error) {
        console.error(error);
      }
    };

    let quantity = ref(1);

    function handleLinkClick(to) {
      localStorage.setItem("activeLink", to);
    }

    onMounted(async () => {
      try {
        await requestProductDetails();
        await requestPage();
        paginationControl();
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });

    const isDisabled = computed(() => {
      return (quantities, temp) => {
        return quantities === temp || quantities > temp;
      };
    });

    function changeQuantity(temp) {
      if (temp === "plus") {
        quantity.value += 1;
      } else if (temp === "minus") {
        quantity.value -= 1;
      }
    }

    const AddProduct = async (id, quantity, stock) => {
      if (stock > 0) {
        try {
          console.log(id);
          const quantity = 1;
          const response = await axios.put(
            `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.ADD}`
          );
          emit("reloadcart");
          console.log("response", response);
          // if (response.data.status == true) {
          //   const response1 = await axios.get(
          //     `${process.env.VUE_APP_MAIN_URL}/carts/total-quantity`
          //   );
          //   let newquantity = ref(0);
          //   // for (let i = 0; i < response1.data.length; i++) {
          //   //   newquantity.value += response1.data[i].quantities;
          //   // }
          //   newquantity.value = response1.data;
          //   this.eventBus.emit("reload", newquantity.value);
          //   toast.success("Added Product!", {
          //     autoClose: 1000,
          //   });
          // }
        } catch (error) {
          console.error("Lỗi khi gọi API", error);
          window.location.href = "/login";
        }
      } else {
        toast.error("Sold out!", {
          autoClose: 1000,
        });
      }
    };

    return {
      product,
      nameAuthor,
      idAuthor,
      relatedProducts,
      curPage,
      totalPages,
      quantity,
      isDisabled,
      changeQuantity,
      handleLinkClick,
      isLoading,
    };
  },
  methods: {
    async BuyNow(id, quantity, stock) {
      if (stock > 0) {
        try {
          console.log(id);
          const quantity = 1;
          const response = await axios.put(
            `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.ADD}`
          );
          emit("reloadcart");
          console.log("response", response);
          // if (response.data.status == true) {
          //   const response1 = await axios.get(
          //     `${process.env.VUE_APP_MAIN_URL}/carts/total-quantity`
          //   );
          //   let newquantity = ref(0);
          //   // for (let i = 0; i < response1.data.length; i++) {
          //   //   newquantity.value += response1.data[i].quantities;
          //   // }
          //   newquantity.value = response1.data;
          //   this.eventBus.emit("reload", newquantity.value);
          //   toast.success("Added Product!", {
          //     autoClose: 1000,
          //   });
          // }
        } catch (error) {
          console.error("Lỗi khi gọi API", error);
          window.location.href = "/login";
        }
      } else {
        toast.error("Sold out!", {
          autoClose: 1000,
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/product/product_details.scss";
</style>
