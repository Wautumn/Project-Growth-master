<template>
  <div>
    <div class="block">
      <DatePicker
        type="daterange"
        show-week-numbers
        placement="bottom-end"
        placeholder="Select date"
        style="width: 200px"
        @on-change="timeChange"
      ></DatePicker>
    </div>
    <ve-line :data="chartData" :settings="chartSettings" :grid="grid"></ve-line>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
      <el-table-column prop="tomatocount" label="tomatocount" width="180"></el-table-column>
      <el-table-column prop="taskCount" label="taskCount"></el-table-column>
    </el-table>
    <Page :total="size" @on-change="pageChange" show-elevator class="footer"/>
  </div>
</template>

<script>
import VeLine from "v-charts/lib/line";

const source_data = [
  { date: "2018-11-20", tomatocount: 6, taskCount: 0 },
  { date: "2018-12-02", tomatocount: 3, taskCount: 1 },
  { date: "2018-12-20", tomatocount: 6, taskCount: 0 },
  { date: "2018-12-23", tomatocount: 5, taskCount: 2 },
  { date: "2018-12-25", tomatocount: 5, taskCount: 1 }
];

const DATA_FROM_BACKEND = {
  columns: ["date", "tomatocount", "taskCount"],
  rows: source_data
};
const EMPTY_DATA = {
  columns: ["date", "tomatocount", "taskCount"],
  rows: []
};

function date_slice(start_date, end_date, source) {
  var result = [];
  for (let j = 0; j < source.length; j++) {
    var item = source[j];
    //debugger;
    if (item.date >= start_date && item.date <= end_date) {
      result.push(item);
    } else if (item.date > end_date) {
      break;
    }
  }
  return result;
}

export default {
  data() {
    this.chartSettings = {
      xAxisType: "time"
    };
    this.userID = 2;
    this.grid = { right: 30 }; //坐标图右侧宽度为60
    return {
      value3: "",
      chartData: EMPTY_DATA,
      PO: {
        firstDayOfWeek: 1
      },
      tableData: EMPTY_DATA.rows,
      size: 0
    };
  },
  components: { VeLine },
  mounted() {
    // Lambda写法
    this.$http
      .get("http://localhost:8080//getHistoryData", {
        params: { userid: this.userID }
      })
      .then(
        res => {
          // 响应成功回调
          //console.log(res.body);
          var selectedData = res.body;
          this.tableData = selectedData;
          this.chartData.rows = selectedData;
          this.size = selectedData.length;
        },
        res => {
          // 响应错误回调
          console.log("fail");
        }
      );
  },
  methods: {
    timeChange(val) {
      var selectedData = date_slice(val[0], val[1], source_data);
      console.log(selectedData);
      this.tableData = selectedData;
      this.chartData.rows = selectedData;
      this.size = selectedData.length;
    },
    pageChange(val) {
      console.log(val);
    }
  }
};
</script>

<style>
.footer {
  margin-top: 50px;
}
</style>
