<template>
  <nav class="nav container">
    <ul class="nav-list">
      <li class="nav-item">
        <router-link
          to="/"
          class="nav-link"
          :class="{ active: activeLink === '/' }"
          @click="handleLinkClick('/')"
          ><span>Home</span></router-link
        >
      </li>
      <li class="nav-item">
        <a
          href="/products"
          class="nav-link"
          :class="{ active: activeLink === '/products' }"
          @click="handleLinkClick('/products')"
          ><span>Shop</span></a
        >
      </li>
      <li class="nav-item">
        <router-link
          to="/authors"
          class="nav-link"
          :class="{ active: activeLink === '/authors' }"
          @click="handleLinkClick('/authors')"
          ><span>Authors</span></router-link
        >
      </li>
      <li class="nav-item sub-nav-container">
        <router-link to="#" class="nav-link">
          <span class="d-flex align-items-center">
            More
            <i class="fa-regular fa-angle-down down-icon"></i>
            <i class="fa-regular fa-angle-up up-icon"></i>
          </span>
        </router-link>
        <ul class="sub-nav">
          <li class="nav-item">
            <router-link
              to="/aboutus"
              class="nav-link"
              :class="{ active: activeLink === '/aboutus' }"
              @click="handleLinkClick('/aboutus')"
              >About Us</router-link
            >
          </li>
          <li class="nav-item">
            <router-link
              to="/contact"
              class="nav-link"
              :class="{ active: activeLink === '/contact' }"
              @click="handleLinkClick('/contact')"
              >Contact</router-link
            >
          </li>
        </ul>
      </li>
    </ul>
    <ul class="nav-list">
      <li class="nav-item">
        <router-link
          to="/faq"
          class="nav-link"
          :class="{ active: activeLink === '/faq' }"
          @click="handleLinkClick('/faq')"
        >
          <i class="fa-regular fa-circle-question me-2"></i>
          <span>Frequently Asked Question?</span>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link
          :to="login_path"
          class="nav-link"
          @mouseover="Click"
          :class="{ active: activeLink === login_path }"
          @click="handleLinkClick(login_path)"
        >
          <i class="fa-regular fa-user me-2"></i>
          <span>Account</span>
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "../config/axios";
import {
  setToLocalStorage,
  getFromLocalStorage,
} from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

export default {
  name: "Nav",

  setup() {
    const router = useRouter();

    let accessToken;
    const login_path = ref("");
    function Click() {
      accessToken = getFromLocalStorage(StorageKey.ACCESS_TOKEN);
      // console.log("accessToken (nav)", accessToken);
      debugger;
      if (accessToken) {
        login_path.value = "/account-details";
      } else {
        login_path.value = "/login";
      }
    }

    // Step 1: Create a ref to store the active link
    const activeLink = ref(getFromLocalStorage(StorageKey.ACTIVE_LINk) || "/");

    // Watch for changes in the activeLink value
    // watch(activeLink, (newVal, oldVal) => {
    //   if (newVal !== oldVal) {
    //     console.log("Link mới:", newVal);
    //     handleLinkClick(newVal);
    //   }
    // });

    // Step 2: Handle router link click event
    function handleLinkClick(to) {
      activeLink.value = to;
      setToLocalStorage(StorageKey.ACTIVE_LINk, to);
      setToLocalStorage(StorageKey.SIDEBAR_STATE, "/account-details");
    }

    // Step 3: Check localStorage on component mount
    onMounted(() => {
      const storedActiveLink = localStorage.getItem(StorageKey.ACTIVE_LINk);
      if (storedActiveLink) {
        activeLink.value = storedActiveLink;
      }
    });

    return {
      Click,
      accessToken,
      login_path,
      activeLink,
      handleLinkClick,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/nav.scss";
</style>
