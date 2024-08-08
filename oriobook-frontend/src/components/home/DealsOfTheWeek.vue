<template>
  <div class="left-col">
    <div class="left-col-content">
      <div class="content-heading">
        <h1>The New Collection Of Books</h1>
      </div>

      <div class="list-product">
        <div
          class="content-product-list"
          v-for="group in productGroups"
          :key="group"
        >
          <HomeProductDeal :group="group" @reloadcart="addCart" />
          <!-- v-for="item in group" :key="item" :item="item" -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HomeProductDeal from "../product/HomeProductDeal.vue";
import { ref, onMounted } from "vue";
import axios from "../../config/axios";
import { useRoute } from "vue-router";

export default {
  name: "DealsOfTheWeek",

  components: {
    HomeProductDeal,
  },

  setup() {
    const number = ref(2);
    const route = useRoute();
    const id = ref(route.params.id);
    const productGroups = ref([]);
    onMounted(async () => {
      try {
        console.log("*** process.env.MAIN_URL", process.env.MAIN_URL);
        const response = await axios.get(
          `${process.env.MAIN_URL}/products?page=2&limit=4`
        );
        const products = response.data.data; // Access 'products' property

        // Ensure products is an array
        if (Array.isArray(products)) {
          // Split the products into two groups using array indexing
          const firstTwoProducts = products.slice(0, 2);
          const nextTwoProducts =
            products.length > 2 ? products.slice(2, 4) : [];

          // Assign the groups to productGroups
          productGroups.value = [firstTwoProducts, nextTwoProducts];
          console.log(productGroups);
        } else {
          console.error("API did not return an array of products.");
        }
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });

    const addCart = () => {
      emit("reloadcart");
    };

    return {
      productGroups,
      number,
      addCart,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/home/home_deal.scss";
</style>
