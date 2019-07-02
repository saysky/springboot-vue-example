<template>
  <div id="app">
    <el-container class="fill-contain">
      <side-nav></side-nav>
      <el-container>
        <el-header>
          <top-header></top-header>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>
    <!--<router-view></router-view>-->
  </div>

</template>

<script>
  import topHeader from '@/components/lyzHeader';
  import sideNav from '@/components/lyzSideNav';
  import {mapState} from 'vuex';

  export default {
    name: "app",
    components:{topHeader,sideNav},
    created(){
      // this.getPrivilege();

    },
    computed:mapState['routesList'],
    methods:{
      // getPrivilege(){
      //   this.$http.get('/v1/uaa/current-privileges').then(({body})=>{
      //     console.log(body);
      //     if(body.status===200){
      //       let buttonSet = new Set();
      //       body.data.forEach((item)=>{
      //           buttonSet.add(item.code);
      //       });
      //       console.log(buttonSet)
      //       this.$store.commit('setButtonList',buttonSet );
      //     }
      //   })
      // },

    },
  }
</script>

<style  src="./style/common.scss">

</style>
