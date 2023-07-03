const { defineConfig } = require('@vue/cli-service')
const path = require("path")
module.exports = defineConfig({
  outputDir: path.resolve(__dirname, "../backend/src/main/resources/static"),
  transpileDependencies: true
})