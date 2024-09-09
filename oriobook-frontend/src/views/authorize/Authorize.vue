<template></template>

<script>
import { StorageKey } from "@/constants/storage.const";

export default {
  name: "Authorize",
  components: [],
  setup() {
    const urlParams = new URLSearchParams(window.location.search);
    localStorage.setItem(
      StorageKey.ACCESS_TOKEN,
      urlParams.get(StorageKey.ACCESS_TOKEN)
    );
    window.location = "/account-details";
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/login.scss";
</style>
