const { defineConfig } = require("@vue/cli-service");
const fs = require("fs");
const Dotenv = require("dotenv-webpack");
const path = require("path");
const webpack = require("webpack");

module.exports = defineConfig({
  devServer: {
    port: 3010,
  },
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "src"),
      },
      extensions: [".js", ".vue", ".json"],
    },
  },
  // transpileDependencies: true,
  // devServer: {
  //   proxy: {
  //     "/api": {
  //       target: "https://stage.orio-studio.online",
  //       changeOrigin: true,
  //       secure: false, // Chỉ nên đặt là false nếu bạn đang sử dụng chứng chỉ SSL không hợp lệ hoặc tự ký.
  //     },
  //   },
  //   https: {
  //     key: fs.readFileSync("./src/cert/key.pem"),
  //     cert: fs.readFileSync("./src/cert/cert.pem"),
  //   },
  //   hot: true,
  // },
  // configureWebpack: {
  //   plugins: [
  //     new Dotenv(),
  //     // new webpack.DefinePlugin({
  //     //   "process.env": {
  //     //     NODE_ENV: JSON.stringify(process.env.NODE_ENV || "production"),
  //     //     MAIN_URL: JSON.stringify(
  //     //       process.env.MAIN_URL || "http://localhost:8080/v1/api"
  //     //     ),
  //     //     VUE_APP_ACCESS_TOKEN_SECRET: JSON.stringify(
  //     //       process.env.VUE_APP_ACCESS_TOKEN_SECRET || "Access_Token_Secret"
  //     //     ),
  //     //     // Add other environment variables here
  //     //   },
  //     // }),
  //   ],
  //   resolve: {
  //     alias: {
  //       "@": path.resolve(__dirname, "src"),
  //     },
  //   },
  // },
  publicPath: "/",
});
