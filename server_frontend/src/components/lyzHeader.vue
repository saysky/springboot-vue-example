<template>
  <div class="header-main">
    <div class="header-start" @click="modifyCollapse">
      <img src="../assets/img/navCloseIcon.png">
    </div>
    <div class="header-end">
      <el-dropdown  trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <span v-html="loginUserName"></span> <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出</el-dropdown-item>
<!--          <el-dropdown-item command="detailMessage">详细信息</el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex';
  export default {
    name: "lyzHeader",
    data(){
      return {
          loginUserName: ''
      }
    },
    created(){
      this.getCurrentUser();
    },
    computed: mapState(['collapse']),
    methods:{
      getCurrentUser() {
        this.$http.get('/user/current-user',{}).then(({body}) => {
          console.log(body);
          if (body.success === true) {
            var unReadSize = body.data.unReadSize;
            if(unReadSize > 0) {
              this.$message.warning("有未读消息");
              this.loginUserName = body.data.userName +"<span style='color:red'>("+unReadSize+"条未读消息)</span>";
            } else {
              this.loginUserName = body.data.userName;
            }
          } else {
            window.location.href = '/login.html';
          }
        });
      },
      modifyCollapse(){
        this.$store.commit('setCollapse',!this.collapse);
      },
      handleCommand(command){
        if(command === 'logout'){
          this.$http.get('/user/logout',{ }).then(({body}) => {
            if (body.success === true) {
              window.location.href = "/login.html";
            }
          })
        }
        else if(command ==='detailMessage'){

        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .header-main{
    display:flex;
    justify-content: space-between;
    .header-start{
      line-height:60px;
      margin-left:10px;
    }
    .header-end{
      margin-right:30px;
      line-height:59px;
      cursor:pointer;

      .el-dropdown{
        color:white;
      }

    }
  }
  .el-popper[x-placement^=bottom]{
    margin-top:0;
  }

</style>
