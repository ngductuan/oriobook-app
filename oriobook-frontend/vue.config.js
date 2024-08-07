const { defineConfig } = require("@vue/cli-service");
const fs = require("fs");
const Dotenv = require("dotenv-webpack");
const path = require("path");
const webpack = require("webpack");

module.exports = defineConfig({
  // transpileDependencies: true,
  // devServer: {
  //   proxy: "http://localhost:3000",
  //   https: {
  //     key: fs.readFileSync("./src/cert/key.pem"),
  //     cert: fs.readFileSync("./src/cert/cert.pem"),
  //   },
  //   hot: true,
  // },
  // rules: [
  //   {
  //     test: /\.vue$/,
  //     loader: "vue-loader",
  //     options: {
  //       hotReload: true, // disables Hot Reload
  //     },
  //   },
  // ],
  publicPath: "./",
  outputDir: "dist",
  assetsDir: "static",
  configureWebpack: {
    plugins: [
      new Dotenv(),
      new webpack.DefinePlugin({
        "process.env.NODE_ENV": JSON.stringify(
          process.env.NODE_ENV || "production"
        ), // Thiết lập NODE_ENV
      }),
    ],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "src"),
      },
    },
  },
});
