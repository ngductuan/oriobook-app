<template>
  <div class="col-9 order-section" style="min-height: 660px">
    <div class="type-filter">
      <h4
        class="pending text-primary underline-animation"
        :class="{ active: toggle_1 }"
        @click="pendingClick"
      >
        Pending
      </h4>
      <h4
        class="accept text-success underline-animation"
        :class="{ active: toggle_2 }"
        @click="successfulClick"
      >
        Successful
      </h4>
      <h4
        class="reject text-danger underline-animation"
        :class="{ active: toggle_3 }"
        @click="cancelledClick"
      >
        Cancelled
      </h4>
    </div>

    <div class="order-container" style="min-height: 810px">
      <div
        class="each-order"
        v-for="(order, index) in orderData"
        :key="index"
        style=""
      >
        <div class="title-order-section" style="margin-right: 12px">
          <p class="order-code">Order code: {{ order.id }}</p>
          <p class="order-code">
            Date: {{ convertDateFormat(order.createdAt) }}
          </p>
          <p class="total">Total price: ${{ order.totalPrice }}</p>
        </div>
        <div
          :class="{ 'scroll-bar-custom-3': order?.detailsList?.length > 2 }"
          :style="{
            'overflow-y': order?.detailsList?.length > 2 ? 'scroll' : 'unset',
            'max-height': '360px',
            'margin-right': order?.detailsList?.length > 2 ? 'unset' : '12px',
          }"
        >
          <table class="order-table table-bordered">
            <thead>
              <tr>
                <th class="product-thumbnail-col" width="60%">Product</th>
                <th class="product-quantity-col" width="20%">Quantity</th>
                <th class="product-subtotal-col" width="10%">Price</th>
              </tr>
            </thead>
            <tbody>
              <tr
                class="cart_item"
                v-for="(item, i_item) in order?.detailsList"
                :key="i_item"
              >
                <td class="product-thumbnail">
                  <div class="product-cart-info">
                    <a
                      v-if="item?.productNode?.id"
                      :href="'/products/' + item.id"
                      ><img
                        :src="item?.productNode?.image"
                        class="product-img"
                        alt=""
                    /></a>

                    <img
                      v-if="!item.id"
                      :src="item?.productNode?.image"
                      class="product-img"
                      alt=""
                    />

                    <div class="product-name">
                      <a
                        v-if="item?.productNode?.id"
                        :href="'/products/' + item.productNode?.id"
                        >{{
                          item.productNode ? item?.productNode?.name : ""
                        }}</a
                      >

                      <p v-if="!item?.productNode?.id" class="mb-0">
                        {{ item?.productNode ? item.productNode?.name : "" }}
                      </p>

                      <p class="price">
                        <span class="woocommerce-Price-amount amount">
                          {{ item?.productNode?.price ?? ""
                          }}<span class="currency">$</span>
                        </span>
                      </p>
                    </div>

                    <div class="evaluate-btn" :class="{ 'no-show': !toggle_2 }">
                      <!-- <button type="button" class="btn btn-primary btn-submit" data-bs-toggle="modal" data-bs-target="#exampleModal">Evaluate</button> -->
                      <button
                        type="button"
                        class="btn btn-primary btn-submit"
                        @click="
                          clickModal(item.productNode?.id, order.id, i_item)
                        "
                        :class="{
                          'btn-secondary': item?.isReviewed || !item.id,
                        }"
                        :disabled="item?.isReviewed || !item.productNode.id"
                      >
                        {{ item.isReviewed ? "Evaluated" : "Evaluate" }}
                      </button>
                    </div>
                  </div>
                </td>

                <td class="product-quantity" data-title="Quantity">
                  <div class="quantity">
                    <p class="number">{{ item.quantity }}</p>
                  </div>
                </td>
                <td class="product-subtotal" data-title="Subtotal">
                  <span class="woocommerce-Price-amount amount"
                    ><bdi>${{ item?.itemTotalPrice }}</bdi></span
                  >
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Notify -->
    <div class="notify-box" :class="{ 'no-show': orderData?.length }">
      <p class="text-center" style="font-size: 20px; margin-top: 2rem">
        There are no orders!
      </p>
    </div>

    <!-- :class="{ 'no-show': orderData.length }" -->

    <!-- Pagination -->
    <!-- :class="{ 'no-show': !orderData.length }" -->
    <div class="col-12 mt-2" :class="{ 'no-show': !orderData?.length }">
      <nav aria-label="Page navigation example">
        <ul class="pagination d-flex justify-content-end">
          <li class="page-item">
            <a class="page-link js-prev-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
              <span class="sr-only">Previous</span>
            </a>
          </li>
          <li
            v-for="(page, index) in totalPages"
            class="page-item"
            :key="index"
          >
            <a
              class="page-link js-number-link"
              :class="{ active: page == curPage }"
              href="#"
              >{{ page }}</a
            >
          </li>
          <li class="page-item">
            <a class="page-link js-next-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
              <span class="sr-only">Next</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>

    <!-- Modal -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <section class="section">
            <div class="star-rating-bx">
              <h2 class="text-center">Feedback</h2>
              <div class="star-widget">
                <form @submit.prevent="feedBack()">
                  <input type="radio" name="star" id="rate-5" value="5" />
                  <label
                    for="rate-5"
                    class="fa-solid fa-star"
                    style="margin-right: 62px"
                  ></label>
                  <input type="radio" name="star" id="rate-4" value="4" />
                  <label for="rate-4" class="fa-solid fa-star"></label>
                  <input type="radio" name="star" id="rate-3" value="3" />
                  <label for="rate-3" class="fa-solid fa-star"></label>
                  <input type="radio" name="star" id="rate-2" value="2" />
                  <label for="rate-2" class="fa-solid fa-star"></label>
                  <input type="radio" name="star" id="rate-1" value="1" />
                  <label for="rate-1" class="fa-solid fa-star"></label>
                  <!-- <p class="rating-desc"></p> -->

                  <div class="textarea">
                    <p id="error-rating"></p>
                    <textarea
                      cols="30"
                      id="comment"
                      placeholder="Describe your comment"
                      style="width: 100%"
                    ></textarea>
                    <p id="error-comment"></p>
                  </div>
                  <div class="btn">
                    <button type="submit" class="btn">Submit</button>
                  </div>
                </form>
              </div>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { displayLoading, removeLoading } from "@/helpers/loadingScreen";
import axios from "../../config/axios";
import { convertDateFormat } from "@/helpers/helperFunctions";

export default {
  name: "OrderAccount",
  components: {},
  props: [],
  setup(props, { emit }) {
    const router = useRouter();

    const idProduct = ref("");
    const idOrder = ref("");
    const orderIndex = ref(null);

    const toggle_1 = ref(true);
    const toggle_2 = ref(false);
    const toggle_3 = ref(false);
    const orderData = ref([]);

    const totalPages = ref(null);
    let page = 1;
    const curPage = ref(page);
    let perPage = 2;
    let pathRef = ref("pending");

    const clickModal = (_idProduct, _idOrder, _orderIndex) => {
      idProduct.value = _idProduct;
      idOrder.value = _idOrder;
      orderIndex.value = _orderIndex;
      $("#exampleModal").modal("show");
    };

    const feedBack = async () => {
      try {
        let error = false;

        if (!$("input[name=star]:checked").val()) {
          document.getElementById("error-rating").innerHTML =
            "* Please choose any star";
          error = true;
        } else {
          document.getElementById("error-rating").innerHTML = "";
        }

        if (document.getElementById("comment").value === "") {
          document.getElementById("error-comment").innerHTML =
            "* Please enter comment";
          error = true;
        } else {
          document.getElementById("error-comment").innerHTML = "";
        }

        if (error) return;

        let comment = document.getElementById("comment").value;
        let rating = document.querySelector("input[name=star]:checked").value;

        let data = {
          idOrder: idOrder.value,
          orderIndex: orderIndex.value,
          comment,
          rating,
        };

        // console.log(data);

        const response = await axios.post(
          `${process.env.VUE_APP_MAIN_URL}/product/handle-review/${idProduct.value}`,
          data
        );
        let res = response.data;
        if (res.msg === "success") {
          hideModal();
          successfulClick();
        }
        // console.log(res.updatedOrder);
      } catch (error) {
        console.error("Error calling API:", error);
      }
    };

    const hideModal = () => {
      $("input[name=star]:checked").prop("checked", false);
      // $("#comment").text('')
      document.getElementById("comment").value = "";
      $("#exampleModal").modal("hide");
    };

    const pendingClick = async () => {
      toggle_1.value = true;
      toggle_2.value = false;
      toggle_3.value = false;
      // console.log("da vo");
      // fetchData('order/pending')
      pathRef.value = "pending";
      page = 1;
      curPage.value = page;
      await requestPage();
      init();
    };

    const successfulClick = async () => {
      toggle_1.value = false;
      toggle_2.value = true;
      toggle_3.value = false;
      // fetchData('order/successful')
      pathRef.value = "successful";
      page = 1;
      curPage.value = page;
      await requestPage();
      init();
    };

    const cancelledClick = async () => {
      toggle_1.value = false;
      toggle_2.value = false;
      toggle_3.value = true;
      // fetchData('order/cancelled')
      pathRef.value = "cancelled";
      page = 1;
      curPage.value = page;
      await requestPage();
      init();
    };

    const requestPage = async () => {
      displayLoading(".order-container", -48);
      const response = await axios.get(
        `${process.env.VUE_APP_MAIN_URL}/orders?page=${page}&limit=${perPage}&${pathRef.value}`
      );

      curPage.value = page;
      orderData.value = response.data?.data;
      totalPages.value = response.data?.totalPages;
      removeLoading();
    };

    const init = function () {
      $(() => {
        if (totalPages.value) {
          $(".js-number-link").click(async function (e) {
            e.preventDefault();
            page = parseInt($(this).text());
            await requestPage();
          });

          $(".js-prev-link").click(async function (e) {
            e.preventDefault();
            page = page > 1 ? page - 1 : page;
            await requestPage();
          });

          $(".js-next-link").click(async function (e) {
            e.preventDefault();
            page = page < totalPages.value ? page + 1 : page;
            await requestPage();
          });
        }
      });
    };

    onMounted(async () => {
      try {
        await requestPage("pending");
        init();
      } catch (error) {
        console.error("Lỗi khi gọi API:", error);
      }
    });

    return {
      feedBack,
      pendingClick,
      successfulClick,
      cancelledClick,
      toggle_1,
      toggle_2,
      toggle_3,
      orderData,
      clickModal,

      totalPages,
      curPage,

      convertDateFormat: convertDateFormat,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/order/order_account.scss";
</style>
