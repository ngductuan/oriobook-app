<template>
  <form class="woocommerce-EditPassWordForm">
    <fieldset>
      <legend>Password change</legend>

      <p class="woocommerce-form-row">
        <label for="password_current"
          >Current password (leave blank to leave unchanged)</label
        >
        <input
          type="password"
          id="password_current"
          autocomplete="off"
          v-model="formData.password_current"
        />
        <span
          v-for="error in v$.password_current.$errors"
          :key="error.$uid"
          style="color: red"
        >
          {{ error.$message + "." }}<br />
        </span>
      </p>

      <p class="woocommerce-form-row">
        <label for="password_1">
          New password (leave blank to leave unchanged)
        </label>
        <span class="password-input">
          <input
            type="password"
            id="password_1"
            autocomplete="off"
            v-model="formData.password_1"
          />
          <span
            v-for="error in v$.password_1.$errors"
            :key="error.$uid"
            style="color: red"
          >
            {{ error.$message + "." }}<br />
          </span>
        </span>
      </p>

      <p class="woocommerce-form-row">
        <label for="password_2">Confirm new password</label>
        <input
          type="password"
          id="password_2"
          autocomplete="off"
          v-model="formData.password_2"
        />
        <span
          v-for="error in v$.password_2.$errors"
          :key="error.$uid"
          style="color: red"
        >
          {{ error.$message + " " }}
        </span>
      </p>
    </fieldset>
    <div class="clear"></div>

    <p>
      <button
        type="button"
        class="woocommerce-Button button"
        name="save_account_details"
        value="Save changes"
        @click="Save"
      >
        Save changes
      </button>
    </p>
  </form>
</template>

<script>
import { reactive, computed } from "vue";
import useVuelidate from "@vuelidate/core";
import { required, minLength, sameAs } from "@vuelidate/validators";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import { useRouter } from "vue-router";
import axios from "../../config/axios";

export default {
  name: "AccountDetails_Pass",
  setup() {
    const router = useRouter();

    const formData = reactive({
      password_current: "",
      password_1: "",
      password_2: "",
    });

    const rules = computed(() => {
      return {
        password_current: { required },
        password_1: { required, minLength: minLength(3) },
        password_2: { required, sameAs: sameAs(formData.password_1) },
      };
    });

    const v$ = useVuelidate(rules, formData);

    async function Save() {
      const result = await v$.value.$validate();
      if (result) {
        // alert(`Account details changed successfully.`);
        const response = await axios.post(
          `${process.env.VUE_APP_MAIN_URL}/account/updateAccountPassword`,
          {
            ...formData,
          }
        );

        if (response.data.status == true) {
          toast.success("Saved successfully!", {
            autoClose: 2000,
          });
          router.push("/account-details");
        } else if (response.data == "password error") {
          toast.error("Your password is wrong.", {
            autoClose: 2000,
            position: "top-center",
          });
        }
      }
    }

    return { Save, formData, rules, v$ };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/account/account_detail.scss";
</style>
