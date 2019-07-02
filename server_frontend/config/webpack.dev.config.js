const utils = require('./utils');
const webpackMerge = require('webpack-merge');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
// const webpack = require('webpack');
const baseWebpackConfig = require('./webpack.base.conf');
process.env.NODE_ENV = process.env.NODE_ENV ?
  process.env.NODE_ENV : 'development';

module.exports = function () {
  return webpackMerge(baseWebpackConfig(),{
    mode: 'development',
    //devtool 用于排错，正确找到错误的位置，并且是我们能看懂的错误代码
    devtool: 'cheap-module-source-map',
    output: {
      path: utils.root('dist'),
      filename: '[name].js',
      chunkFilename: '[name].chunk.js',
      sourceMapFilename: '[file].map'
    },
    module:{
      rules: utils.styleLoaders({sourceMap: false}),
    },
    plugins:[
      new MiniCssExtractPlugin({
        filename: '[name].css',
      }),
    ],


    //先把webpack-dev-server工具安装到本地,webpack-dev-server只用于开发环境
    devServer: {
      host:'localhost',
      port:9090,
      inline:true,
      watchOptions:{
        ignored:/node_modules/,
      },
      // hot:true,
      historyApiFallback: true,
      proxy: {
        '/api': {
          target:'http://127.0.0.1:8080',
          secure: false,
          changeOrigin: true,
        },
      },
    }
  })
}
