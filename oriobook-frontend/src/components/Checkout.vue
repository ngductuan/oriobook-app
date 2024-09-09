<template>
  <div
    class="d-flex w-100 justify-content-center"
    style="min-height: 850px; position: relative"
  >
    <div v-if="isLoading" class="loading-checkout">
      <img src="/other/loading_icon.svg" style="width: 56px" class="mt-4" />
    </div>

    <div
      class="container"
      style="margin-left: auto; margin-right: auto; padding-left: 30px"
    >
      <div class="row" style="padding-right: 5px">
        <div class="col-8" style="position: relative">
          <!-- Content for the 60% width div -->

          <div
            class="coupon-content"
            id="coupon-content"
            style="
              display: none;
              width: 400px;
              height: 200px;
              background-color: #ffffff;
              box-shadow: 10px 10px 20px rgba(0, 0, 0, 0.3);
              position: absolute;
              right: 30px;
              z-index: 50;
              text-align: center;
              justify-content: center;
            "
          >
            <div
              style="
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 400px;
              "
            >
              <label
                for="coupon-code"
                style="padding-top: 50px; padding-bottom: 50px; font-size: 15px"
                >If you have a coupon code, please apply it below.</label
              >
              <div style="display: flex; gap: 10px">
                <input
                  type="text"
                  id="coupon-code"
                  placeholder="Coupon code..."
                  style="width: 300px; padding-left: 10px"
                />
                <button
                  @click="applyCoupon"
                  class="btn"
                  style="width: 100px; height: 50px"
                >
                  Apply
                </button>
              </div>
            </div>
          </div>
          <div>
            <h4 style="font-weight: 400; font-size: 23px">Billing details</h4>
            <br />
            <h6 style="font-weight: 400; font-size: 17px">First name *</h6>
            <input
              type="text"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              :value="accountData.firstName"
              disabled
            />
            <br />
            <h6 style="font-weight: 400; font-size: 17px">Last name *</h6>
            <input
              type="text"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              :value="accountData.lastName"
              disabled
            />
            <br />

            <div class="d-flex">
              <h6 style="font-weight: 400; font-size: 17px">Address *</h6>
              <h6
                id="address-noti"
                class="text-danger"
                style="margin-left: 8px"
              ></h6>
            </div>
            <input
              type="text"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              :value="accountData.address"
              disabled
            />

            <br />

            <div class="d-flex">
              <h6 style="font-weight: 400; font-size: 17px">Phone *</h6>
              <h6
                id="phone-noti"
                class="text-danger"
                style="margin-left: 8px"
              ></h6>
            </div>
            <input
              type="text"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              :value="accountData.phone"
              disabled
            />
            <br />
            <h6 style="font-weight: 400; font-size: 17px">Email *</h6>
            <input
              type="text"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              :value="accountData.email"
              disabled
            />
            <br />
            <h6 style="font-weight: 400; font-size: 17px">
              Order notes (optional)
            </h6>

            <!-- Max length -->
            <div class="block-form">
              <textarea
                class="form-control note-form"
                v-model="note"
                aria-label="With textarea"
                placeholder="Note about your delivery (eg. free,..)"
                style="
                  padding-top: 15px;
                  outline: none;
                  max-height: 80px;
                  min-height: 80px;
                "
                maxlength="200"
              ></textarea>

              <span class="note-max">0</span>/ 200 letters
            </div>
          </div>
        </div>
        <div class="col-4">
          <div
            style="
              padding-left: 30px;
              padding-right: 250px;
              padding-right: 30px;
              padding-top: 30px;
              padding-bottom: 30px;
              border: 1px solid black;
            "
          >
            <h4
              style="
                font-family: Jost, sans-serif;
                font-weight: 400;
                font-size: 23px;
              "
            >
              Product
            </h4>
            <div style="user-select: none">
              __________________________________________
            </div>

            <div class="product-section scroll-bar-custom-2">
              <div
                v-for="(item, index) in accountData.cart"
                :key="index"
                style="position: relative; margin-top: 40px"
              >
                <img :src="item.image" style="width: 30%" />
                <div style="position: absolute; top: 0; left: 160px">
                  <p class="mb-0 ellipsis-custom-2">
                    {{ item?.name }}
                  </p>
                  <p style="font-weight: 500" class="mb-0 mt-2">
                    ${{ item?.price }}
                  </p>
                  <div style="font-weight: 500">
                    Quantity: {{ item.quantity }}
                  </div>
                </div>
              </div>
            </div>
            <div style="user-select: none; color: rgb(119, 119, 119)">
              __________________________________________
            </div>
            <br />
            <div>
              <div class="d-flex">
                <div style="width: 280px">Total</div>
                <div style="font-weight: 700">
                  ${{ accountData.total_price }}
                </div>
              </div>

              <!-- <div class="d-flex">
              <div style="width: 280px">Current balance</div>
              <div style="font-weight: 700">
                ${{ parseFloat(accountData.balance).toFixed(2) }}
              </div>
            </div> -->
            </div>
            <div style="user-select: none; color: rgb(119, 119, 119)">
              __________________________________________
            </div>
            <br />
            <div>
              <p id="error-balance" style="color: red"></p>
              <p id="place-success" style="color: green"></p>
              <button
                class="btn"
                style="width: 375px; height: 50px"
                @click="placeOrder()"
              >
                Place Order
              </button>

              <!-- data-bs-toggle="modal"
                data-bs-target="#qrCheckoutModal" -->

              <!-- <button
              type="button"
              class="btn btn-primary"
              data-bs-toggle="modal"
              data-bs-target="#qrCheckoutModal"
            >
              Launch demo modal
            </button> -->

              <!-- Modal -->
              <div
                class="modal fade d-none"
                id="qrCheckoutModal"
                tabindex="-1"
                aria-labelledby="qrCheckoutLabel"
                aria-hidden="true"
                data-bs-backdrop="static"
                data-bs-keyboard="false"
              >
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">
                        Thanh toán đơn hàng
                      </h5>
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      ></button>
                    </div>
                    <div class="modal-body">
                      <img :src="qrCode" alt="QR Code" />
                    </div>
                    <div class="modal-footer">
                      <button
                        data-bs-dismiss="modal"
                        type="button"
                        class="btn btn-primary"
                        style="padding: 12px 0"
                        @click="finishPayment"
                      >
                        Hoàn thành
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VueRouter from "vue-router";
import { ref, onMounted, inject } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "../config/axios";
// import QR from "./qr/QR.vue";

export default {
  name: "checkout",
  // inject: ["eventBus"],
  setup(props, { emit }) {
    const router = useRouter();
    const accountData = ref({});
    const note = ref("");
    const eventBus = inject("eventBus");
    const qrCode = ref("");
    const isLoading = ref(true);

    const toggleCoupon = () => {
      var content = document.getElementById("coupon-content");
      content.style.display =
        content.style.display === "block" ? "none" : "block";
    };

    const applyCoupon = () => {
      // Add your coupon application logic here
      console.log("Coupon applied!");
    };

    const toggleContent = (contentId) => {
      var content = document.getElementById(contentId);
      content.style.display =
        content.style.display === "block" ? "none" : "block";
    };

    const fetchData = async () => {
      try {
        // Execute both API calls concurrently
        const [response, responseCart] = await Promise.all([
          axios.get(`${process.env.VUE_APP_MAIN_URL}/users/profile`),
          axios.get(`${process.env.VUE_APP_MAIN_URL}/carts`),
        ]);
        isLoading.value = false;

        // Update account data with the responses
        accountData.value = response.data;
        accountData.value.cart = responseCart.data?.data;
        accountData.value.total_price = responseCart.data?.totalPrice;

        console.log("accountData", accountData.value);
      } catch (error) {
        console.error("Error calling API:", error);
      }
    };

    onMounted(async () => {
      // Max length
      $(() => {
        $(".note-form")
          .keyup(function () {
            let value = $(this).val();
            $(".note-max").text(parseInt(value.length));
          })
          .keyup();
      });

      // let qrModalHook = $("#qrCheckoutModal");

      // qrModalHook.on("show.bs.modal", function () {
      //   $("html").css("overflow", "hidden");
      // });

      // qrModalHook.on("hidden.bs.modal", function () {
      //   $("html").css("overflow", "");
      // });

      await fetchData();
    });

    const placeOrder = async () => {
      let errorHold = false;
      if (accountData.value?.address.trim() === "") {
        $("#address-noti").text(`(Can't be empty)`);
        errorHold = true;
      }
      if (accountData.value?.phone.trim() === "") {
        $("#phone-noti").text(`(Can't be empty)`);
        errorHold = true;
      }
      if (errorHold) return;
      let dataSend = {
        note: note.value,
      };
      const response = await axios.post(
        `${process.env.VUE_APP_MAIN_URL}/orders`,
        dataSend
      );
      let res = response.data;
      if (res.result === "fail") {
        document.getElementById("error-balance").innerHTML =
          "* " + res.msg + ". Please add more balance";
      } else {
        document.getElementById("error-balance").innerHTML = "";
        document.getElementById("place-success").innerHTML =
          "* Place order successfully";
        // emit("reloadcart");
        setTimeout(() => {
          router.push({ name: "MyWallet" });
        }, 2000);
      }
    };

    const finishPayment = () => {
      router.push({ name: "MyWallet" });
    };

    // You can return data or methods that you want to expose to the template
    return {
      toggleCoupon,
      applyCoupon,
      toggleContent,
      accountData,
      placeOrder,
      note,
      router,
      qrCode: "https://img.vietqr.io/image/970415-113366668888-compact2.png",
      isLoading,
      finishPayment,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "../styles/checkout.scss";
</style>
