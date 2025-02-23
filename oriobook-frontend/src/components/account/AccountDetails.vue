<template>
  <form class="woocommerce-EditAccountForm">
    <p class="woocommerce-form-row">
      <label for="account_first_name"
        >First name&nbsp;<span class="required">*</span></label
      >
      <input
        type="text"
        id="account_first_name"
        autocomplete="given-name"
        v-model="formData.account_first_name"
      />
      <span
        v-for="error in v$.account_first_name.$errors"
        :key="error.$uid"
        style="color: red"
      >
        {{ error.$message + "." }}<br />
      </span>
    </p>
    <p class="woocommerce-form-row">
      <label for="account_last_name"
        >Last name&nbsp;<span class="required">*</span></label
      >
      <input
        type="text"
        id="account_last_name"
        autocomplete="family-name"
        v-model="formData.account_last_name"
      />
      <span
        v-for="error in v$.account_last_name.$errors"
        :key="error.$uid"
        style="color: red"
      >
        {{ error.$message + "." }}<br />
      </span>
    </p>
    <div class="clear"></div>

    <p class="woocommerce-form-row">
      <label for="account_address"
        >Address&nbsp;<span class="required"></span
      ></label>
      <input
        type="text"
        id="account_address"
        autocomplete="given-name"
        v-model="formData.account_address"
      />
      <span
        v-for="error in v$.account_address.$errors"
        :key="error.$uid"
        style="color: red"
      >
        {{ error.$message + "." }}<br />
      </span>
    </p>
    <p class="woocommerce-form-row">
      <label for="account_phone"
        >Phone number&nbsp;<span class="required"></span
      ></label>
      <input
        type="tel"
        id="account_phone"
        autocomplete="family-name"
        v-model="formData.account_phone"
      />
      <span
        v-for="error in v$.account_phone.$errors"
        :key="error.$uid"
        style="color: red"
      >
        {{ error.$message + "." }}<br />
      </span>
    </p>
    <div class="clear"></div>

    <p class="woocommerce-form-row">
      <label for="account_email"
        >Email address&nbsp;<span class="required">*</span></label
      >
      <input
        type="email"
        id="account_email"
        autocomplete="email"
        class="bg-secondary-subtle"
        v-model="formData.account_email"
        disabled
      />
    </p>

    <div class="clear"></div>

    <p>
      <button
        type="button"
        class="woocommerce-Button button"
        name="save_account_details"
        value="Save changes"
        @click="SaveData"
      >
        Save changes
      </button>
    </p>
  </form>
</template>

<script>
import { reactive, computed, onMounted, ref } from "vue";
import useVuelidate from "@vuelidate/core";
import { required, minLength, maxLength, numeric } from "@vuelidate/validators";
import { toast } from "vue3-toastify";
import { useRouter } from "vue-router";
import "vue3-toastify/dist/index.css";
import axios from "../../config/axios";
import {
  setToLocalStorage,
  getFromLocalStorage,
} from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

export default {
  name: "AccountDetails",
  props: ["userInfo"],
  setup(props, { emit }) {
    const router = useRouter();
    const userImage = ref("");

    const formData = reactive({
      account_first_name: "",
      account_last_name: "",
      account_address: "",
      account_phone: "",
      account_email: "",
    });

    onMounted(async () => {
      // // console.log("localStorage", localStorage.getItem("token"));
      try {
        const token = getFromLocalStorage(StorageKey.ACCESS_TOKEN);
        if (!token) {
          throw new Error("Token not found in localStorage");
        }

        const response = await axios.get(
          `${process.env.VUE_APP_MAIN_URL}/users/profile`
        );

        const mainData = response.data;

        if (response.status === 200) {
          formData.account_first_name = mainData.firstName;
          formData.account_last_name = mainData.lastName;
          formData.account_address = mainData.address;
          formData.account_phone = mainData.phone;
          formData.account_email = mainData.email;
          userImage.value = mainData.image;
        }
      } catch (error) {
        console.error("Lỗi khi gọi API", error);
      }
    });

    const rules = computed(() => {
      return {
        account_first_name: { required, minLength: minLength(3) },
        account_last_name: { required, minLength: minLength(3) },
        account_address: { minLength: minLength(5) },
        account_email: { required },
        account_phone: {
          minLength: minLength(10),
          maxLength: maxLength(10),
          numeric,
        },
      };
    });

    const v$ = useVuelidate(rules, formData);

    async function SaveData() {
      const result = await v$.value.$validate();
      if (result) {
        // alert(`Account details changed successfully.`);
        const coreFormData = {
          firstName: formData.account_first_name,
          lastName: formData.account_last_name,
          address: formData.account_address,
          phone: formData.account_phone,
          image: userImage.value,
        };

        try {
          const response = await axios.put(
            `${process.env.VUE_APP_MAIN_URL}/users/profile`,
            {
              ...coreFormData,
            }
          );

          if (response.status == 200) {
            toast.success("Saved successfully!", {
              autoClose: 2000,
            });
            router.push("/account-details");
          }
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
@import "@/styles/account/account_detail.scss";
</style>
