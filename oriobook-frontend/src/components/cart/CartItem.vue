<template>
  <li class="product-item row" :class="{ 'd-none': removeItem }">
    <router-link to="/" class="col-3">
      <img :src="cartItem.image" alt="" class="product-img" />
    </router-link>
    <div class="col-9">
      <router-link to="/" class="product-title">{{
        cartItem.name
      }}</router-link>
      <p class="product-price">{{ cartItem?.price }}$</p>
      <div class="product-quantity row gx-0">
        <button class="col" @click="minus(cartItem.id)">
          <i class="fa-light fa-minus"></i>
        </button>
        <input
          type="text"
          class="col text-center"
          id="quantity"
          disabled
          :value="selfQuantity"
        />
        <button
          class="col"
          @click="plus(cartItem?.id, cartItem.stock)"
          :disabled="isDisabled(cartItem.quantity, cartItem.stock)"
        >
          <i class="fa-light fa-plus"></i>
        </button>
      </div>
      <button
        class="fa-regular fa-trash-can product-remove-btn"
        @click="RemoveProduct(cartItem.id)"
      ></button>
    </div>
  </li>
</template>

<script>
import axios from "@/config/axios";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import { ref, computed, onMounted, watch } from "vue";
import { CartActionEnum } from "@/types/enum.type";

export default {
  name: "CartItem",
  props: {
    cartItem: Object,
  },
  setup(props, { emit }) {
    let cartItem = ref(props.cartItem);
    const selfQuantity = ref(cartItem?.quantity);
    const removeItem = ref(false);

    watch(
      () => props.cartItem,
      (updatedCartItem) => {
        // cartItem = updatedCartItem;
        if (updatedCartItem?.quantity) {
          selfQuantity.value = updatedCartItem?.quantity;
        }
      },
      { immediate: true }
    );

    const minus = async (id) => {
      // console.log(id);
      if (selfQuantity.value <= 1) return;

      selfQuantity.value -= 1;

      const response = await axios.put(
        `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.SUBTRACT}`
      );

      if (response.data.statusCode == 200) {
        await update();
      }
    };

    async function plus(id, stock) {
      if (stock > 0 && selfQuantity.value < stock) {
        selfQuantity.value += 1;

        const response = await axios.put(
          `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.ADD}`
        );

        if (response.data.statusCode == 200) {
          await update();
        }
      } else {
        toast.error("Sold out!", {
          autoClose: 1000,
        });
      }
    }

    const RemoveProduct = async (id) => {
      removeItem.value = true;

      const response = await axios.put(
        `${process.env.VUE_APP_MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.DELETE}`
      );

      if (response.data.statusCode == 200) {
        toast.success("Removed Product!", {
          autoClose: 1000,
        });

        await update();
      }
    };

    const isDisabled = computed(() => {
      return (quantities, stock) => {
        return quantities === stock;
      };
    });

    onMounted(async () => {
      // console.log("cartItem", cartItem);
      selfQuantity.value = cartItem?.quantity;
    });

    return {
      cartItem,
      minus,
      plus,
      RemoveProduct,
      isDisabled,
      selfQuantity,
      removeItem,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/cart.scss";
</style>
