<template>
  <section class="container">
    <section class="row">
      <Sidebar></Sidebar>
      <section class="edit-product col-9">
        <div class="d-flex align-items-center">
          <router-link class="edit-product-breadcrumb" to="/admin/manage">
            <div class="d-flex align-items-center">
              <i class="fa-regular fa-caret-left me-2"></i>
              <span>Products</span>
            </div>
          </router-link>
          <a
            class="btn text-uppercase ms-auto js-save-btn"
            href="#"
            @click.prevent="addOrUpdateProduct"
            >Save</a
          >
        </div>
        <form class="edit-product-forms row gx-0" id="edit-form">
          <ul class="edit-product-form col-7">
            <li class="edit-product-form-item mb-3">
              <label for="product-name"
                >Name <span class="text-danger">{{ nameError }}</span>
              </label>
              <input
                id="product-name"
                type="text"
                :value="product.name"
                name="name"
              />
            </li>
            <li class="edit-product-form-item mb-3 row">
              <div class="col">
                <label for="product-price"
                  >Price ($)
                  <span class="text-danger">{{ priceError }}</span></label
                >
                <input
                  id="product-price"
                  type="number"
                  :value="product.price"
                  name="price"
                />
              </div>
              <div class="col">
                <label for="product-stock"
                  >Stock
                  <span class="text-danger">{{ stockError }}</span></label
                >
                <input
                  id="product-stock"
                  type="number"
                  :value="product.stock"
                  name="stock"
                />
              </div>
            </li>
            <li class="edit-product-form-item mb-3">
              <label for="product-description"
                >Description
                <span class="text-danger">{{ descriptionError }}</span></label
              >
              <textarea
                name="description"
                id="product-description"
                cols="30"
                rows="20"
                >{{ product.description }}</textarea
              >
            </li>
          </ul>
          <div class="edit-product-form col">
            <div class="product-category mb-3">
              <label class="product-category-label"> Category </label>
              <select class="edit-product-select" name="categoryId">
                <option
                  v-for="category in categories"
                  :key="category"
                  :value="category.id"
                  :selected="isSelected(category.id, product?.categoryNode?.id)"
                >
                  {{ category.name }}
                </option>
              </select>
            </div>
            <div class="product-category">
              <label class="product-category-label"> Author </label>
              <select class="edit-product-select" name="authorId">
                <option
                  v-for="author in authors"
                  :key="author.id"
                  :value="author.id"
                  :selected="isSelected2(author.id, product?.authorNode?.id)"
                >
                  {{ author.name }}
                </option>
              </select>
            </div>

            <div class="mb-2">
              <input
                type="file"
                name="image"
                class="form-control"
                id="formFile"
                accept="image/*"
              />
            </div>
          </div>
        </form>
      </section>
    </section>
  </section>
</template>

<script>
import Sidebar from "@/components/account/SideBar";
import { onMounted, ref } from "vue";
import axios from "@/config/axios";
import {
  getFromLocalStorage,
  setToLocalStorage,
} from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

import { useRoute, useRouter } from "vue-router";
export default {
  name: "Edit",
  components: {
    Sidebar,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const id = ref(route.params.id);
    const product = ref({}); // product
    const categories = ref([]);
    const categoryList = ref([]);
    const authors = ref([]);
    const authorName = ref("");
    const isSelected = (option, productOption) => {
      return option === productOption;
    };

    const isSelected2 = (option, authorOption) => {
      return option === authorOption;
    };

    // Refs for validation error messages
    const nameError = ref("");
    const priceError = ref("");
    const stockError = ref("");
    const descriptionError = ref("");

    const validateForm = (values) => {
      let isValid = true;

      // Validation for name
      values.name = values.name.trim();
      product.value.name = values.name;
      if (values.name === "") {
        nameError.value = "cannot be empty.";
        isValid = false;
      } else if (values.name.length > 150) {
        nameError.value = "cannot exceed 150 characters.";
        isValid = false;
      } else {
        nameError.value = "";
      }

      // Validation for price
      values.price = parseFloat(values.price);
      product.value.price = values.price;
      if (isNaN(values.price)) {
        priceError.value = "cannot be empty.";
        isValid = false;
      } else if (values.price < 0) {
        priceError.value = "cannot be negative.";
        isValid = false;
      } else if (values.price > 1000) {
        priceError.value = "cannot exceed 1000$.";
        isValid = false;
      } else {
        priceError.value = "";
      }

      // Validation for stock
      values.stock = parseFloat(values.stock);
      product.value.stock = values.stock;
      if (isNaN(values.stock)) {
        stockError.value = "cannot be empty.";
        isValid = false;
      } else if (values.stock % 1 != 0) {
        stockError.value = "cannot be float.";
        isValid = false;
      } else if (values.stock < 0) {
        stockError.value = "cannot be negative.";
        isValid = false;
      } else if (values.stock > 1000) {
        stockError.value = "cannot exceed 1000 items.";
        isValid = false;
      } else {
        stockError.value = "";
      }

      // Validation for description
      values.description = values.description.trim();
      product.value.description = values.description;
      if (values.description === "") {
        descriptionError.value = "cannot be empty.";
        isValid = false;
      } else if (values.description.length > 2000) {
        descriptionError.value = "cannot exceed 2000 characters.";
        isValid = false;
      } else {
        descriptionError.value = "";
      }

      // console.log("Values: ", values);

      return isValid;
    };

    const addOrUpdateProduct = async () => {
      const formData = new FormData(document.getElementById("edit-form"));
      const values = {};
      formData.forEach((value, key) => {
        values[key] = value;
      });

      // Validate before submitting
      if (!validateForm(values)) {
        return;
      }

      try {
        const idProduct = product.value.id ? product.value.id : "";
        // Hiển thị hiệu ứng loading
        $(".edit-product-forms").html(`
          <div class="w-100 text-center mt-5">
            <div class="spinner-border" role="status">
              <span class="sr-only">Loading...</span>
            </div>
          </div>
        `);

        const { image, ...productInfo } = values;

        // console.log(image, productInfo);
        const token = getFromLocalStorage(StorageKey.ACCESS_TOKEN);
        // console.log("token", token);

        const formData = new FormData();
        formData.append("image", image); // 'image' là đối tượng file bạn muốn upload

        const response = await fetch(
          `${process.env.VUE_APP_MAIN_URL}/upload/image`,
          {
            method: "POST",
            body: formData,
            headers: {
              Authorization: `Bearer ${token}`,
              // Không cần thiết phải chỉ định 'Content-Type': 'multipart/form-data' với fetch,
              // vì trình duyệt sẽ tự động thiết lập nó khi gửi FormData.
            },
          }
        );

        const imageRes = await response.json();

        // console.log(imageRes);

        productInfo.image = imageRes.secureUrl ?? product.value.image;

        // console.log("productInfo", productInfo);

        const response2 = await axios.put(
          `${process.env.VUE_APP_MAIN_URL}/products/${idProduct}`,
          productInfo
        );

        router.push("/admin/manage");
      } catch (error) {
        console.error("Error submitting form:", error);
      }
    };

    $(() => {
      $("#formFile").fileinput({
        theme: "fa6 ",
        showUpload: false,
        previewFileType: "any",
      });
    });

    onMounted(async () => {
      try {
        // Lấy tất cả category
        let response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/categories`
        );
        categoryList.value = response.data.data;
        for (let cate of categoryList.value) {
          // console.log(cate);
          categories.value.push({
            id: cate.id,
            name: cate.name,
          });
          for (let subCate of cate.children) {
            // console.log(subCate);
            categories.value.push({
              id: subCate.id,
              name: cate.name + " / " + subCate.name,
            });
          }
        }
        // Lấy tất cả author
        let res2 = await axios.get(`${process.env.VUE_APP_MAIN_URL}/authors`);
        authors.value = res2.data.data;

        // console.log("authors", authors.value);

        // console.log("route", route.name);
        if (route.name == "EditForUpdate") {
          response = await axios.get(
            `${process.env.VUE_APP_MAIN_URL}/products/${id.value}`
          );

          if (response.status == 200) {
            product.value = response.data;

            // console.log(
            //   "response for product",
            //   response.data,
            //   product.value.authorNode.name
            // );

            authorName.value = product.value?.authorNode.id;

            // Hiển thị hình ảnh preview
            $(() => {
              $(".file-drop-zone-title").css({
                padding: "0px",
              });
              $(".file-drop-zone-title").html(`
            <img src="${product.value?.image}"/>
          `);
            });
          } else
            throw {
              code: 400,
              errMsg: "Bad request",
            };
        }
      } catch (error) {
        console.error("Error:", error);
      }
    });

    return {
      product,
      categories,
      isSelected,
      isSelected2,
      authors,
      authorName,
      addOrUpdateProduct,
      // Expose error messages
      nameError,
      priceError,
      stockError,
      descriptionError,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/admin/edit.scss";
</style>
