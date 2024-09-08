<template>
  <section class="cart row">
    <div class="cart-slider d-flex flex-column">
      <h3
        class="cart-heading text-uppercase text-center d-flex justify-content-center"
      >
        Cart
      </h3>
      <button class="fa-regular fa-xmark cart-close-btn"></button>
      <template v-if="cart.length !== 0">
        <ul class="product-list scroll-bar-custom-2">
          <!-- <li class="product-item row" v-for="element in cart" :key="element">
            <router-link to="/" class="col-3">
              <img :src="element.image" alt="" class="product-img" />
            </router-link>
            <div class="col-9">
              <router-link to="/" class="product-title">{{
                element.name
              }}</router-link>
              <p class="product-price">{{ element?.price }}$</p>
              <div class="product-quantity row gx-0">
                <button class="col" @click="minus(element.id)">
                  <i class="fa-light fa-minus"></i>
                </button>
                <input
                  type="text"
                  class="col text-center"
                  id="quantity"
                  disabled
                  :value="element?.quantity"
                />
                <button
                  class="col"
                  @click="plus(element.id, element.stock)"
                  :disabled="isDisabled(element.quantity, element.stock)"
                >
                  <i class="fa-light fa-plus"></i>
                </button>
              </div>
              <button
                class="fa-regular fa-trash-can product-remove-btn"
                @click="RemoveProduct(element.id)"
              ></button>
            </div>
          </li> -->
          <CartItem
            v-for="element in cart"
            :key="element.id"
            :cartItem="element"
          />
        </ul>
        <p class="product-total d-flex justify-content-between">
          <span>Subtotal: </span><span>{{ price }}$</span>
        </p>
        <router-link to="/checkout" class="cart-checkout-btn text-uppercase">
          <span>Check out</span>
        </router-link>
      </template>

      <template v-if="cart.length === 0">
        <div class="empty">
          <span>No products in the cart.</span>
          <a class="go-shop underline-animation" href="/products">
            Shop all products
          </a>
        </div>
      </template>
    </div>
  </section>
</template>

<script>
import axios from "@/config/axios";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import { ref, computed, onMounted } from "vue";
import { CartActionEnum } from "@/types/enum.type";
import CartItem from "./cart/CartItem.vue";

export default {
  name: "Cart",
  components: {
    CartItem,
  },
  inject: ["eventBus"],

  setup(props, { emit }) {
    const cart = ref([]);
    let price = ref(0);

    async function init() {
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/carts`
        );
        if (response.data && Array.isArray(response.data.data)) {
          cart.value = response.data.data;

          console.log("response cart", response.data);

          price.value = response.data?.totalPrice;
        } else {
          console.error("Unexpected API response structure", response);
        }
      } catch (error) {
        console.error("Lỗi khi gọi API", error);
      }
    }

    $(".cart-btn").click(async function (e) {
      e.preventDefault();

      $(".cart").addClass("enable");
      $(".cart-slider").click(function (e) {
        e.stopPropagation();
      });
      // Chưa fix cứng được app
      $("#app").scroll(function (e) {
        e.preventDefault();
      });
      $(".cart").click(function () {
        $(".cart").removeClass("enable");
      });

      console.log("open cart");
      await init();
      // price.value = await Price();
    });

    const handleCart = () => {
      $(".cart").click(async () => {
        console.log("close");
        $(".cart").removeClass("enable");
      });
      $(".cart-close-btn").click(async () => {
        console.log("close");
        $(".cart").removeClass("enable");
      });
    };

    onMounted(async () => {
      await init();
      handleCart();
    });

    return {
      // RemoveProduct,
      // minus,
      // plus,
      cart,
      price,
      // isDisabled,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/cart.scss";
</style>
