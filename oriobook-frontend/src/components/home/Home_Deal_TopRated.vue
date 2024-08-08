<template>
  <section class="deals">
    <div class="container">
      <DealsOfTheWeek @reloadcart="addCart" />
      <TopRatedAuthor :author="author" />
    </div>
  </section>
</template>

<script>
import DealsOfTheWeek from "@/components/home/DealsOfTheWeek.vue";
import TopRatedAuthor from "@/components/home/TopRatedAuthor.vue";
import { ref, onMounted } from "vue";
import axios from "../../config/axios";

import { useRoute } from "vue-router";

export default {
  components: {
    DealsOfTheWeek,
    TopRatedAuthor,
  },
  setup() {
    const route = useRoute();
    const id = ref(route.params.id);
    const author = ref({});

    onMounted(async () => {
      try {
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/authors?page=0&limit=6`
        );
        // console.log(response.data);
        author.value = response.data.data;
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });

    const addCart = () => {
      emit("reloadcart");
    };

    return {
      author,
      addCart,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/home/home_deal_toprated.scss";
</style>
