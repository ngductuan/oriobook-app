<template>
  <div class="box-form-login">
    <div class="title-form register">Register</div>
    <div class="box-content">
      <div class="form-register">
        <form method="post" class="register">
          <div class="email">
            <input
              type="email"
              id="email"
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
          <div class="firstname">
            <input
              type="text"
              id="firstname"
              class="input-text"
              placeholder="First name*"
              name="firstname"
              v-model="formData.firstName"
            />
            <span
              v-for="error in v$.firstName.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="lastname">
            <input
              type="text"
              id="lastname"
              class="input-text"
              placeholder="Last name*"
              name="lastname"
              v-model="formData.lastName"
            />
            <span
              v-for="error in v$.lastName.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="address">
            <input
              type="text"
              id="address"
              class="input-text"
              placeholder="Address"
              name="address"
              v-model="formData.address"
            />
            <span
              v-for="error in v$.address.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="phone">
            <input
              type="tel"
              id="phone"
              class="input-text"
              placeholder="Phone number"
              name="phone"
              v-model="formData.phone"
            />
            <span
              v-for="error in v$.phone.$errors"
              :key="error.$uid"
              style="color: red"
            >
              {{ error.$message + "." }}<br />
            </span>
          </div>
          <div class="password">
            <input
              type="password"
              id="input-text"
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
          <div class="button-register">
            <button
              type="button"
              class="woocommerce-Button button fw-bold"
              name="register"
              value="Register"
              @click="SaveData"
            >
              Register
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import useVuelidate from "@vuelidate/core";
import {
  required,
  minLength,
  maxLength,
  numeric,
  email,
} from "@vuelidate/validators";
import "vue3-toastify/dist/index.css";
import axios from "../config/axios";

import { useRouter } from "vue-router";
import { toast } from "vue3-toastify";

export default {
  name: "SignUp",

  setup() {
    const router = useRouter();

    const formData = reactive({
      email: "",
      firstName: "",
      lastName: "",
      address: "",
      phone: "",
      password: "",
    });

    const rules = computed(() => {
      return {
        email: { required, minLength: minLength(12), email },
        firstName: { required, minLength: minLength(3) },
        lastName: { required, minLength: minLength(3) },
        address: { minLength: minLength(5) },
        phone: {
          minLength: minLength(10),
          maxLength: maxLength(10),
          numeric,
        },
        password: { required, minLength: minLength(3) },
      };
    });

    const v$ = useVuelidate(rules, formData);

    async function SaveData() {
      const result = await v$.value.$validate();
      console.log("data", formData);
      if (result) {
        try {
          const response = await axios.post(
            `${process.env.VUE_APP_MAIN_URL}/auth/sign-up`,
            {
              ...formData,
            }
          );

          console.log(response.data);
          toast.success("Register successfully. Please login", {
            autoClose: 2000,
            position: "top-center",
          });
        } catch (error) {
          toast.error(error.response.data.message, {
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
