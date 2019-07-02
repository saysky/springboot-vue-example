const utils = require('./utils');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
//用到的基础插件
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const {VueLoaderPlugin} = require('vue-loader');
module.exports = function() {
  return {
    //打包入口文件
    entry:{
      app:'./src/main.js',
      login:'./src/page/base/login/login.js'
    },

    resolve: {
      extensions: ['.js','.vue','.json'],
      alias:{
        'vue$': 'vue/dist/vue.esm.js',
        '@': utils.root('src'),
      }
    },
    //module 配置处理模块的规则
    module:{
      // //使用正则表达式
      //   noParse: /jquery|chartjs/
      // //   //使用函数，从webpack3.0.0开始支持
      //   noParse: (content)=>{
      //     return /jquery|chartjs/.test(content);
      //   }
      rules:[
        {
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          enforce: 'pre',
          include: [utils.root('src')],
          options: {
            formatter: require('eslint-friendly-formatter'),
          },
        },
        {
          //处理vue，vue-loader可以将vue文件中的内容分开，提取每个语言块，然后再用相应的loader去加载
          test: /\.vue$/,
          loader: 'vue-loader',
        },
        {
          //处理es6 版本的js，接入babel，需要先按照模块 babel-core babel-loader
          test: /\.js$/,
          loader: 'babel-loader',
          include: [
            utils.root('src'),
            utils.root('test'),
          ],
          exclude:[/node_modules/],
        },
        // 处理字体
        {
          test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
          loader: 'url-loader',
          options:{
            limit:10000,
            outputPath: 'assets/img',
          },
        },
        {
          test :/\.(woff2?|eot|ttf|otf)(\?.*)?$/,
          loader: 'url-loader',
          options: {
            limit:10000,
            outputPath: 'assets/fonts',
          },
        },



      ],

    },
    plugins:[
      new CleanWebpackPlugin((['dist'])),
      new HtmlWebpackPlugin({
        filename:utils.root('dist/login.html'),
        title:'登录页面',
        template: utils.root('login.html'),
        //将chunks按引入的顺序排序，不用这个的话，引入到html中的js可能是错乱排序的
        chunksSortMode: 'manual',
        //chunks用来设置引入的js，不设置的话会默认把入口的所有文件都引入html中
        chunks: ['login'],
        inject: 'body',
      }),
      new HtmlWebpackPlugin({
        filename:utils.root('dist/index.html'),
        title:'服务器信息管理系统',
        template: utils.root('index.html'),
        chunks: ['app'],
        inject: 'body',
      }),

      new VueLoaderPlugin(),
      new MiniCssExtractPlugin({
        filename: '[name].css',
      }),
    ]

  }


}
