<template>
  <div id="app">
    <el-container>
      <el-header class="header">
        <vheader/>
      </el-header>
      <router-view></router-view>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Header";

export default {
  name: "app",
  components: {
    vheader: Header
  }
};
</script>

