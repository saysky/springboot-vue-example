<template>
  <div class="layout">
    <div class="banner" v-if="showBanner">
      <lyz-title :label="label"></lyz-title>
      <slot name="banner"></slot>
    </div>
    <div class="main">
      <slot name="main" ></slot>
      <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pagination.pageIndex"
              :page-sizes="[10, 20, 30, 40]"
              :page-size=pagination.pageSize
              layout="total, sizes, prev, pager, next, jumper"
              :total=pagination.total>
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import lyzTitle from './lyzTitle';
  export default {
    name: "lyzLayout",
    data(){
      return {
        showBanner:true,
      }
    },
    props:{
      // total:Number,
      // pageIndex:Number,
      // pageSize:Number,
      pagination:{
        type: Object,
        default:()=>{},
      },
      label:String,

    },
    components:{
      lyzTitle,
    },
    methods:{
      handleSizeChange(val){
        this.$emit('handleSizeChange',val)
      },
      handleCurrentChange(val){
        this.$emit('handleCurrentChange',val)
      }
    }
  }
</script>

<style  lang="scss">
  .layout{
    height:100%;
    display:flex;
    flex-direction: column;
    .banner{
      display:flex;
      justify-content: space-between;
      background:white;
      padding:0 20px;
      height:54px;
      align-items:center;
      .top-right{
        height:30px;
        display:flex;

        .el-input-group{
          width:120px;
          margin-right:10px;
          .el-input__inner{
            font-size:12px;
            height:30px !important;
            width:120px;
          }
          .el-input-group__append{
            padding:0 10px;
          }
        }
        .right-select{

          margin-right:10px;
          width:120px;
          .el-input__inner{
            font-size:12px;
            height:30px;
          }
          .el-input__icon{
            line-height:30px;
          }
        }
        .new-btn{
          padding:6px 20px !important;
        }


      }
    }
    .main{
      padding:25px 25px 0;
      margin-top:20px;
      flex:1;
      overflow:hidden;
      background:white;
      .main-body{
        height: calc(100% - 44px)!important;
        .el-table{
          .el-tag{
            cursor:pointer;
          }
        }

      }
      .el-pagination{
        height:40px;
        display: flex;
        justify-content: center;
        align-items:center ;
      }
    }
  }
</style>
