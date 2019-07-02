<template>
  <el-menu :default-active="$route.path" :router="true" mode="horizontal" @select="handleSelect"  background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b">
    <el-menu-item v-for="(item,index) in routerList" :key="index" :index="item.path+''">
      {{item.name}}
    </el-menu-item>
  </el-menu>
</template>

<script>
  //对于export {}这种格式的导出，引入必须：import {}
  //对于exports.xxx这种格式的导出，引入和export default这种导出的引入方法一样，都是import xxx，只是前者引入的是一个对象
  import {routerConfig } from "../router";

  export default {
    name: "zcl-top-nav",
    data(){
      return {
        //es6的简写模式 本来是routerConfig：routerConfig
        routerList:routerConfig.slice(1),
        activeIndex: '1',
      }
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<style lang="scss">

</style>