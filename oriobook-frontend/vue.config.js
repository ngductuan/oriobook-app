const { defineConfig } = require("@vue/cli-service");
const fs = require("fs");
const Dotenv = require("dotenv-webpack");
const path = require("path");

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
  configureWebpack: {
    plugins: [new Dotenv()],
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "src"),
      },
    },
  },
});
