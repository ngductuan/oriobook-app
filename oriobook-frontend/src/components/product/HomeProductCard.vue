<template>
  <div class="product-card" style="position: relative">
    <div class="image-container">
      <a
        :href="'/products/' + product.id"
        class="img-1"
        @click="handleLinkClick('/products')"
      >
        <img
          v-if="!product.image"
          src="/public/placeholder/loading-image.png"
          alt="Loading..."
          class="img-1"
        />

        <img v-else :src="product.image" :alt="product.name" class="img-1" />
      </a>
      <button
        v-if="!isAdminBool"
        class="add-to-cart"
        @click="AddProduct(product.id, product.stock)"
      >
        <i class="fa-solid fa-cart-plus"></i>
      </button>
    </div>
    <div class="product-info">
      <a class="author-name" href="#">{{ product?.authorNode?.name }} </a>
      <div class="product-name-box">
        <a class="product-name ellipsis-custom-1 me-2" href="#">{{
          product.name
        }}</a>
      </div>
      <p style="font-size: 16px; font-weight: 800">
        ${{ product?.price?.toFixed(2) }}
      </p>
    </div>
    <div class="overlay d-none">
      <div class="overlay-icons">
        <i class="fas fa-shopping-cart"></i>
        <i class="fas fa-heart"></i>
        <i class="fas fa-search"></i>
      </div>
    </div>

    <div style="position: absolute; top: -3px; right: 0" v-if="!product.stock">
      <span class="badge bg-danger" style="border-radius: 0">SOLD OUT</span>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed, watch } from "vue";
import axios from "../../config/axios";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import { CartActionEnum, RoleEnum } from "@/types/enum.type";
import { getTokenInfo } from "@/helpers/helperFunctions";
import { StorageKey } from "@/constants/storage.const";
import { getFromLocalStorage } from "@/utils/local-storage.util";
import { isAdmin } from "@/helpers/helperFunctions";

export default {
  name: "HomeProductCard",
  inject: ["eventBus"],
  props: ["product"],

  methods: {},

  setup(props, { emit }) {
    const imgHover = ref(true);

    const isAdminBool = ref(false);

    function handleLinkClick(to) {
      localStorage.setItem("activeLink", to);
    }

    const AddProduct = async (id, stock) => {
      if (stock > 0) {
        try {
          // console.log(id);
          const response = await axios.put(
            `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.ADD}`
          );
          emit("reloadcart");

          toast.success("Added Product!", {
            autoClose: 1000,
          });
        } catch (error) {
          console.error("Lỗi khi gọi API", error);
          // window.location.href = "/login";
        }
      } else {
        toast.error("Sold out!", {
          autoClose: 1000,
        });
      }
    };

    onMounted(async () => {
      isAdminBool.value = await isAdmin();
    });

    return {
      imgHover,
      handleLinkClick,
      AddProduct,
      isAdminBool,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/home/home_product_cart.scss";
</style>
