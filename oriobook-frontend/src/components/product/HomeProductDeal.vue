<template>
  <div class="item-product">
    <div class="items" v-for="item in group" :key="item">
      <div class="product-button">
        <div class="products-thumb">
          <div class="product-lable">
            <div class="hot">Hot</div>
          </div>
          <div class="product-thumb-hover">
            <a
              :href="'/products/' + item.id"
              class="woocommerce-LoopProduct-link"
              @click="handleLinkClick('/products')"
            >
              <img
                loading="lazy"
                :src="item.image"
                :alt="item.name"
                class="fade-in lazyload wp-post-image"
                decoding="async"
              />
            </a>
          </div>
          <button class="add-to-cart" @click="AddProduct(item.id, item.stock)">
            <i class="fa-solid fa-cart-plus"></i>
          </button>
        </div>
      </div>

      <div class="products-content">
        <div class="contents">
          <div class="list-author">
            <p>
              <a
                :href="'/authors/' + item?.authorNode?.id"
                class="item-author"
                @click="handleLinkClick('/authors')"
                >{{ item?.authorNode?.name }}</a
              >
            </p>
          </div>
          <h3>
            <a
              :href="'/products/' + item.id"
              class="product-title ellipsis-custom-3"
              >{{ item.name }}
            </a>
          </h3>
          <span class="price">
            <span class="woocommerce-Price-amount amount">
              <bdi>
                {{ item.price
                }}<span class="woocommerce-Price-currencySymbol">&#36;</span>
              </bdi>
            </span>
          </span>
          <div class="available-box">
            <label class="hugry">Hurry Up!</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "../../config/axios";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

export default {
  name: "HomeProductDeal",
  inject: ["eventBus"],
  props: ["group"],

  methods: {},

  setup(props, { emit }) {
    const number = ref(2);

    function handleLinkClick(to) {
      localStorage.setItem("activeLink", to);
    }

    const AddProduct = async (id, stock) => {
      if (stock > 0) {
        try {
          console.log(id);
          const response = await axios.put(
            `${process.env.MAIN_URL}/carts/adjust/${id}?adjustMode=${CartActionEnum.ADD}`
          );
          emit("reloadcart");
          console.log("response", response);
          // if (response.data.status == true) {
          //   const response1 = await axios.get(
          //     `${process.env.MAIN_URL}/carts/total-quantity`
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
      number,
      AddProduct,
      handleLinkClick,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/home/home_deal.scss";
</style>
