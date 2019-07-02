<template>
  <div class="lyz-title">
    <span class="decorative-line"></span>
    <label class="label-name">{{label}}</label>
  </div>
</template>

<script>
  export default {
    name: "lyzTitle",
    props:{label:String,}
  }
</script>

<style scoped lang="scss">
  .lyz-title{
    display:flex;
    align-items: center;
  }
.decorative-line{
  width: 3px;
  height: 17px;
  background: #2e6eff;
  margin-right: 4px;
}
</style>
