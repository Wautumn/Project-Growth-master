<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <navmenu></navmenu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import NavMenu from "@/components/NavMenu";

export default {
  components: {
    navmenu: NavMenu
  }
};
</script>

