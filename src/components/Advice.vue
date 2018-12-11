<template>
  <div class="container">
    <el-card class="box-card-his">
      <div slot="header" class="clearfix">
        <span>最近一周工作情况</span>
        <el-button style="float: right; padding: 3px 0" type="text">查看详细数据</el-button>
      </div>

      <ve-line :data="chartData" :settings="chartSettings"></ve-line>
    </el-card>

    <el-card class="box-card">
      <ve-pie :data="chartData1"></ve-pie>
    </el-card>
  </div>
</template>


<style scoped>
.container {
  display: flex;
  flex-direction: row;
}

.box-card-his {
  width: 600px;
  margin: 50px;
  margin-left: 80px;
  font-size: 13px;
}

.box-card {
  margin: 50px;
  margin-left: 80px;
  font-size: 13px;
  width: 400px;
}
</style>

<script>
import "echarts/lib/chart/line";
import VeLine from "v-charts/lib/line";
import VePie from "v-charts/lib/pie.js";

export default {
  components: { VeLine, VePie },
  data() {
    this.chartSettings = {
      stack: { 用户: ["访问用户", "下单用户"] },
      area: true
    };
    return {
      chartData1: {
        columns: ["日期", "访问用户"],
        rows: [
          { 日期: "1/1", 访问用户: 1393 },
          { 日期: "1/2", 访问用户: 3530 },
          { 日期: "1/3", 访问用户: 2923 },
          { 日期: "1/4", 访问用户: 1723 },
          { 日期: "1/5", 访问用户: 3792 },
          { 日期: "1/6", 访问用户: 4593 }
        ]
      },
      chartData: {
        columns: ["日期", "访问用户", "下单用户", "下单率"],
        rows: [
          { 日期: "1/1", 访问用户: 1393, 下单用户: 1093, 下单率: 0.32 },
          { 日期: "1/2", 访问用户: 3530, 下单用户: 3230, 下单率: 0.26 },
          { 日期: "1/3", 访问用户: 2923, 下单用户: 2623, 下单率: 0.76 },
          { 日期: "1/4", 访问用户: 1723, 下单用户: 1423, 下单率: 0.49 },
          { 日期: "1/5", 访问用户: 3792, 下单用户: 3492, 下单率: 0.323 },
          { 日期: "1/6", 访问用户: 4593, 下单用户: 4293, 下单率: 0.78 }
        ]
      }
    };
  }
};
</script>

