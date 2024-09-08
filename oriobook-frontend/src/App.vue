<template>
  <Nav />
  <Header />
  <Cart />
  <router-view v-slot="{ Component }">
    <component :is="Component" @reloadcart="handleMyEvent" />
  </router-view>
  <Footer />
</template>

<script>
import Nav from "@/components/Nav.vue";
import Header from "@/components/Header.vue";
import Cart from "@/components/Cart.vue";
import Footer from "@/components/Footer";
import { ref, provide, onMounted } from "vue";
import axios from "./config/axios";
import { isAdmin } from "./helpers/helperFunctions";

const quantity = ref(0); // Khai báo biến quantity là biến toàn cục

export default {
  name: "App",

  components: {
    Nav,
    Header,
    Cart,
    Footer,
  },

  inject: ["eventBus"],
  methods: {
    getCookie() {
      const username = this.$cookies;
    },
  },
  setup() {
    let quantityTemp = ref(0);
    const isAdminBool = ref(false);

    const fetchCart = async () => {
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/carts/total-quantity`
        );

        quantityTemp.value = response.data;

        // console.log("response.data (cart count)", response.data);
      } catch (error) {
        console.error("Lỗi khi gọi API", error);
      }
    };

    onMounted(async () => {
      isAdminBool.value = await isAdmin();
      if (!isAdminBool.value) {
        await fetchCart();
      }
    });

    const handleMyEvent = () => {
      console.log("da nghe");
      fetchCart();
    };

    provide("quantity", quantityTemp);

    return {
      handleMyEvent,
    };
  },

  mounted() {
    // this.eventBus.on("reload", (newquantity) => {
    //   console.log("newquantity " + newquantity);
    //   // Cập nhật giá trị mới cho quantity
    //   quantity.value = newquantity;
    // });
    // this.eventBus.on("reload", async () => {
    //   try {
    //     const response = await axios.get(
    //       `${process.env.VUE_APP_MAIN_URL}/carts/total-quantity`
    //     );
    //     console.log("newquantity ", response.data);
    //     quantity.value = response.data;
    //   } catch (error) {
    //     console.error("Lỗi khi gọi API reload quantity", error);
    //   }
    //   // Cập nhật giá trị mới cho quantity
    //   // quantity.value = newquantity;
    // });
  },
};
</script>

<style lang="scss">
@import "@/styles/app.scss";
</style>
