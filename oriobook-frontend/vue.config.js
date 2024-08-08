const { defineConfig } = require("@vue/cli-service");
const fs = require("fs");
const Dotenv = require("dotenv-webpack");
const path = require("path");
const webpack = require("webpack");

module.exports = defineConfig({
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
  configureWebpack: {
    plugins: [
      new Dotenv(),
      new webpack.DefinePlugin({
        "process.env.NODE_ENV": JSON.stringify(
          process.env.NODE_ENV || "production"
        ),
      }),
    ],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "src"),
      },
    },
  },
});
