<template>
  <div
    class="col-3"
    :class="{
      'pe-3':
        $route.name == 'Manage' ||
        $route.name == 'Edit' ||
        $route.name == 'Dashboard',
    }"
  >
    <nav class="navigation">
      <ul>
        <li
          class="navigation-link"
          data-path="/account-details"
          @click="directPage($event.target)"
        >
          <!-- <router-link to="/account-details"></router-link> -->
          Account details
        </li>

        <template v-if="!admin">
          <li
            class="navigation-link"
            data-path="/account-order"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/account-order">Orders</router-link> -->
            My orders
          </li>
        </template>

        <template v-if="admin">
          <li
            class="navigation-link"
            data-path="/admin/dashboard"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/admin/dashboard">Dashboard</router-link> -->
            Dashboard
          </li>
          <li
            class="navigation-link"
            data-path="/admin/manage"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/admin/manage">Manage products</router-link> -->
            Manage products
          </li>
          <li
            class="navigation-link"
            data-path="/admin/manage-author"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/admin/manage-author"></router-link> -->
            Manage authors
          </li>
          <li
            class="navigation-link"
            data-path="/admin/manage-category"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/admin/manage-category"
              ></router-link -->
            Manage categories
          </li>
          <li
            class="navigation-link"
            data-path="/admin/order"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/admin/order"></router-link> -->
            Manage orders
          </li>
        </template>

        <li
          class="navigation-link"
          data-path="/account-wallet"
          @click="directPage($event.target)"
        >
          <!-- <router-link to="/account-wallet"></router-link> -->
          My wallet
        </li>
        <li class="navigation-link" @click="LogOut">Log out</li>

        <template v-if="!admin">
          <li
            class="navigation-link"
            data-path="/account-delete"
            @click="directPage($event.target)"
          >
            <!-- <router-link to="/account-order">Orders</router-link> -->
            Delete account
          </li>
        </template>
      </ul>
    </nav>
  </div>
</template>

<script>
import { getTokenInfo } from "../../helpers/helperFunctions";
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "../../config/axios";
import { RoleEnum } from "@/types/enum.type";
import { removeFromLocalStorage } from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

export default {
  name: "SideBar",

  components: {},
  setup() {
    const router = useRouter();
    const route = useRoute();

    let admin = ref(false);
    async function LogOut() {
      // const response = await axios.post(
      //   `${process.env.VUE_APP_MAIN_URL}/account/logout`
      // );
      // let res = response.data;

      // if (res.result === "success") {
      //   localStorage.removeItem("accessToken");
      //   localStorage.removeItem("sidebar");

      //   window.location.href = "/login";
      // }

      removeFromLocalStorage(StorageKey.ACCESS_TOKEN);
      removeFromLocalStorage(StorageKey.SIDEBAR_STATE);
      removeFromLocalStorage(StorageKey.USER_INFO);
      removeFromLocalStorage(StorageKey.ACTIVE_LINk);

      window.location.href = "/login";
    }

    async function checkAdmin() {
      try {
        const { role } = await getTokenInfo();
        admin.value = role === RoleEnum.ADMIN;
        // console.log("admin.value", role, RoleEnum.ADMIN);
      } catch (error) {
        console.log("error", error);
      }
    }

    function directPage(element) {
      const dataPath = $(element).data("path");
      $(".navigation-link").removeClass("active");

      localStorage.setItem("sidebar", dataPath);
      $(element).addClass("active");

      router.push(dataPath);
    }

    function updateActive(element) {
      const dataPath = $(element).data("path");
      $(".navigation-link").removeClass("active");

      localStorage.setItem("sidebar", dataPath);
      $(element).addClass("active");
    }

    onMounted(async () => {
      await checkAdmin();
      let path = router.currentRoute.value?.path;
      let dataPaths = $(".navigation-link")
        .map(function () {
          return $(this).data("path");
        })
        .get();

      // console.log(path);

      if (dataPaths.includes(path)) {
        let activeSideBar =
          localStorage.getItem("sidebar") ?? "/account-details";

        if (path === "/account-wallet") {
          localStorage.setItem("sidebar", path);
          activeSideBar = path;
        }

        // let markedElement = $(`.navigation-link[data-path="${activeSideBar}"]`);
        // let markedElement = $(`.navigation-link[data-path="${activeSideBar}"]`);
        let markedElement = $(`.navigation-link[data-path='${activeSideBar}']`);

        if (markedElement && markedElement.length === 1) {
          directPage(markedElement);
          router.push(activeSideBar);
        }
      } else {
        let activeSideBar =
          localStorage.getItem("sidebar") ?? "/account-details";
        let markedElement = $(`.navigation-link[data-path='${activeSideBar}']`);

        updateActive(markedElement);
      }
    });

    return {
      LogOut,
      checkAdmin,
      admin,

      directPage,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/account/sidebar.scss";
</style>
