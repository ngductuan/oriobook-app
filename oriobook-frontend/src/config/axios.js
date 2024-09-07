import axios from "axios";
import Cookies from "js-cookie";
import {
  getFromLocalStorage,
  setToLocalStorage,
} from "@/utils/local-storage.util";
import { StorageKey } from "@/constants/storage.const";

const getTokenInCookie = () => {
  return getFromLocalStorage(StorageKey.ACCESS_TOKEN) ?? "";
};
const instances = axios.create();
// Add a request interceptor
instances.interceptors.request.use(
  function (request) {
    const token = getTokenInCookie();

    // Đính token vào header mới
    const newHeaders = {
      ...request.headers,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "POST, GET, OPTIONS, PUT, DELETE",
      "Access-Control-Allow-Headers": "*",
      "Content-Type": request.headers["Content-Type"] || "application/json",
      Authorization: "Bearer " + token,
    };

    // console.log("Content-Type", newHeaders);

    // Đính header mới vào lại request trước khi được gửi đi
    request = {
      ...request,
      headers: newHeaders,
    };

    return request;
  },
  function (error) {
    // Xử lý lỗi
    return Promise.reject(error);
  }
);

// Add a response interceptor
instances.interceptors.response.use(
  function (response) {
    const newHeaders = {
      ...response.headers,
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "POST, GET, OPTIONS, PUT, DELETE",
      "Access-Control-Allow-Headers": "*",
      "Content-Type": response.headers["Content-Type"] || "application/json",
    };

    response = {
      ...response,
      headers: newHeaders,
    };
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  }
);

export default instances;
