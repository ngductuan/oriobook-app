<template>
  <div class="authorlist-page">
    <div class="img-cover d-flex align-items-center justify-content-center">
      <h1 class="title-heading">List Authors</h1>
    </div>

    <Authors :authors="authors" />
    <div class="container mt-0 justify-content-end">
      <Pagination :totalPages="totalPages" :curPage="curPage" />
    </div>
  </div>
</template>

<script>
import Authors from "@/components/author/Authors.vue";
import { ref, onMounted } from "vue";
import axios from "../config/axios";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import Pagination from "@/components/Pagination.vue";

export default {
  components: {
    Authors,
    Pagination,
  },
  setup() {
    const authors = ref([]);
    const totalPages = ref(0);
    let page = 1;
    const curPage = ref(page);
    const perPage = 8;

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
        displayLoading(".js-author-container", -64, 0);
        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/authors?page=${
            page - 1
          }&limit=${perPage}`
        );
        curPage.value = page;
        authors.value = response.data.data;

        console.log("authors", response.data);

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
      totalPages,
      curPage,
      authors,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/author/authors.scss";
</style>
