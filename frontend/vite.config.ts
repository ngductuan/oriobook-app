import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import _checker from 'vite-plugin-checker'
import path from 'path'
import _packageJson from './package.json'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue()
    // checker({
    //   enableBuild: true,
    //   typescript: true,
    //   eslint: {
    //     lintCommand: packageJson.scripts.lint
    //   }
    // })
  ],
  server: {
    port: 3001
  },
  css: {
    devSourcemap: true
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  }
})
