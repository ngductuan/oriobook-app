<template>
  <div class="box-form-login">
    <div class="title-form">Login</div>
    <div class="box-content">
      <div class="form-login">
        <form method="post" class="login">
          <div class="email">
            <input
              type="email"
              class="input-text"
              placeholder="Email address*"
              name="email"
              v-model="formData.email"
            />
            <span
              v-for="error in v$.email.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="password">
            <input
              type="password"
              placeholder="Password*"
              name="password"
              v-model="formData.password"
            />
            <span
              v-for="error in v$.password.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="button-login">
            <button
              type="button"
              class="woocommerce-Button button fw-bold"
              name="login"
              value="Login"
              @click="SaveData"
            >
              Login
            </button>
          </div>

          <p
            class="text-center fw-bold mt-3 text-muted"
            style="width: 100%; font-size: 14px"
          >
            OR
          </p>

          <a
            class="btn btn-danger rounded-0 text-light d-flex align-items-center justify-content-center fw-bold"
            style="width: 100%; height: 50px; font-size: 13px"
            :href="`https://oriobook-main-sys.onrender.com/auth/google`"
            role="button"
          >
            <img
              src="../assets/img/google.png"
              style="height: 22px; width: 22px"
              class="m-2"
            />LOGIN WITH GOOGLE
          </a>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import useVuelidate from "@vuelidate/core";
import { required, minLength, email } from "@vuelidate/validators";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import axios from "../config/axios";
import { useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode";
import { setToLocalStorage } from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

export default {
  name: "SignIn",

  setup() {
    const router = useRouter();

    const formData = reactive({
      email: "",
      password: "",
    });

    const rules = computed(() => {
      return {
        email: { required, minLength: minLength(12), email },
        password: { required, minLength: minLength(3) },
      };
    });

    const v$ = useVuelidate(rules, formData);
    console.log("CHECK", `${process.env.VUE_APP_MAIN_URL}/auth/login`);

    async function SaveData() {
      const result = await v$.value.$validate();
      if (result) {
        try {
          const response = await axios.post(
            `${process.env.VUE_APP_MAIN_URL}/auth/login`,
            formData
          );

          const accessToken = response?.data?.accessToken;
          console.log("response (login)", response);

          setToLocalStorage(StorageKey.ACCESS_TOKEN, accessToken);

          const decodedPayload = jwtDecode(accessToken);
          console.log(decodedPayload);

          setToLocalStorage(StorageKey.USER_INFO, decodedPayload);

          // if (response.status === 200) {
          window.location.href = "/";
          // }
        } catch (error) {
          toast.error(error?.response?.data?.message, {
            autoClose: 2000,
            position: "top-center",
          });
        }
      }
    }

    return { SaveData, formData, rules, v$ };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/login.scss";
</style>
