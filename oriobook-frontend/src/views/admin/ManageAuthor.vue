<template>
  <div class="container">
    <section class="row">
      <Sidebar></Sidebar>
      <section class="manage-product col-9">
        <div class="d-flex align-items-center justify-content-between mb-3">
          <!-- <p class="manage-product-breadcrumb">Authors</p> -->
          <div class="manage-product-search-bar">
            <label for="search-input-id" class="search-label">
              <i class="fa-solid fa-magnifying-glass search-icon"></i>
            </label>
            <input
              type="email"
              class="search-input"
              id="search-input-id"
              placeholder="Search for author"
              :value="searchQuery"
            />
          </div>
          <a class="btn text-uppercase" href="/admin/edit-author" role="button"
            >Add author</a
          >
        </div>
        <section class="manage-product-content">
          <ul class="manage-product-titles row gx-0">
            <input
              class="manage-product-checkbox col-1"
              type="checkbox"
              value=""
            />
            <p class="manage-product-title text-center mb-0 col-1">Image</p>
            <p class="manage-product-title text-center mb-0 col">Name</p>
            <p class="manage-product-title text-center mb-0 col-2">Gender</p>
            <p class="manage-product-title text-center mb-0 col-2">
              Published Book
            </p>
          </ul>
          <ul class="manage-product-list">
            <p
              class="text-center py-4 fs-5"
              :class="authors.length > 0 ? 'd-none' : ''"
            >
              There's no authors.
            </p>
            <template v-for="author in authors">
              <article class="manage-product-item row gx-0">
                <input
                  class="manage-product-checkbox col-1"
                  type="checkbox"
                  :value="author.id"
                />
                <a
                  :href="'/admin/edit-author/' + author.id"
                  class="manage-product-item-link col"
                >
                  <ul
                    class="manage-product-item-infos row gx-0 align-items-center"
                  >
                    <li class="manage-product-info text-center col-1">
                      <img
                        :src="author.image"
                        :alt="author.name"
                        class="manage-product-img"
                      />
                    </li>
                    <li class="manage-product-info text-center col">
                      {{ author.name }}
                    </li>
                    <li class="manage-product-info text-center col-2 me-2">
                      {{ toCapitalize(author.gender) }}
                    </li>
                    <li class="manage-product-info text-center col-2 me-2">
                      {{ author.publishedBook }}
                    </li>
                  </ul>
                </a>
              </article>
            </template>
          </ul>
        </section>
        <div class="d-flex justify-content-between mt-2">
          <a class="btn text-uppercase js-delete-btn" href="#" role="button"
            >Delete</a
          >
          <Pagination :totalPages="totalPages" :curPage="curPage" />
        </div>
      </section>
    </section>
  </div>
</template>

<script>
import Sidebar from "@/components/account/SideBar";
import { onMounted, ref } from "vue";
import axios from "../../config/axios";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import Pagination from "@/components/Pagination.vue";
import { toCapitalize } from "@/utils/common.util";

export default {
  name: "Manage",
  components: {
    Sidebar,
    Pagination,
  },
  setup() {
    const authors = ref([]);
    const totalPages = ref(0);
    let page = 1;
    const curPage = ref(page);
    const perPage = 4;
    const searchQuery = ref("");

    const handleSearchQuery = () => {
      $(`#search-input-id`).keypress(async function (event) {
        var keycode = event.keyCode ? event.keyCode : event.which;
        if (keycode == "13") {
          searchQuery.value = $("#search-input-id").val();
          await requestPage();
        }
      });
    };

    const requestPage = async () => {
      try {
        displayLoading(".manage-product-list", -32, -32);
        let url = `${process.env.VUE_APP_MAIN_URL}/authors?page=${
          page - 1
        }&limit=${perPage}&sortByDate=DESC`;
        if (searchQuery) url += `&authorName=${searchQuery.value}`;
        const response = await axios.get(url);
        curPage.value = page;
        authors.value = response.data.data;
        totalPages.value = response.data.totalPages;

        removeLoading();
        $(() => {
          handleDelete();
        });
      } catch (error) {
        console.error(error);
      }
    };

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

    const handleDelete = () => {
      $(".manage-product-titles .manage-product-checkbox").off("click");
      const deleteBtn = document.querySelector(
        ".manage-product .js-delete-btn"
      );

      if (totalPages === 0) {
        manageProductContent.classList.add("d-none");
        deleteBtn.classList.add("d-none");
        manageProductScreen.insertAdjacentHTML(
          "beforeend",
          `<p class="text-center" style="font-size: 2rem; margin-top: 2rem;">You haven't add any items! </p>`
        );
      } else {
        const mainCheckbox = document.querySelector(
          ".manage-product-titles .manage-product-checkbox"
        );
        const checkboxes = document.querySelectorAll(
          ".manage-product-item .manage-product-checkbox"
        );

        mainCheckbox.addEventListener("click", () => {
          if (mainCheckbox.checked)
            checkboxes.forEach((checkbox) => {
              checkbox.checked = true;
            });
          else
            checkboxes.forEach((checkbox) => {
              checkbox.checked = false;
            });
        });

        deleteBtn.addEventListener("click", (e) => {
          e.preventDefault();
          // Remove item khỏi giao diện
          checkboxes.forEach(async (checkbox) => {
            if (checkbox.checked) {
              const id_author = $(checkbox).val();
              displayLoading(".manage-product-list", -32, -32);
              console.log("GỌi hàm delete");
              const response = await axios.delete(
                `${process.env.VUE_APP_MAIN_URL}/authors/${id_author}`
              );
              if (response.status === 200) {
                checkbox.parentElement.remove();
                removeLoading();
              }
              window.location.reload();
            }
            mainCheckbox.checked = false;
          });
        });
      }
    };

    onMounted(async () => {
      try {
        handleSearchQuery();
        await requestPage();
        $(() => {
          paginationControl();
        });
      } catch (error) {
        console.error(error);
      }
    });
    return {
      authors,
      totalPages,
      curPage,
      searchQuery,
      toCapitalize,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/admin/manage.scss";
</style>
