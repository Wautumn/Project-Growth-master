<template>
  <div class="list-container">
    <div class="list-side">
      <DatePicker
        type="daterange"
        show-week-numbers
        placement="bottom-end"
        placeholder="Select date"
        @on-change="timeChange"
      ></DatePicker>
      <Select v-model="selectedItem" class="selector" @on-change="valueChange">
        <OptionGroup label="Task">
          <Option value="已完成的任务">已完成的任务</Option>
          <Option value="放弃的任务">放弃的任务</Option>
        </OptionGroup>
        <OptionGroup label="Pomoto">
          <Option value="已完成的番茄">已完成的番茄</Option>
          <Option value="中断的番茄">中断的番茄</Option>
        </OptionGroup>
      </Select>
    </div>
    <div class="list-head" v-for="item in items" :key="item.id">
      <div class="list-second-head">
        <p class="card-head">{{selectedDate}}</p>
        <p class="card-extra">{{weekday}}</p>
      </div>
      <Timeline class="time-line">
        <TimelineItem v-for="i in item" :key="i.id">
          <p class="time">{{i.time}}</p>
          <p class="content">{{i.content}}</p>
        </TimelineItem>
      </Timeline>
      <div class="box-card" v-if="selectedItem === '已完成的番茄'">
        <p>完成了{{tomotoNum(item)}}个番茄</p>
        <p>总计{{totalTime(item)}}</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '已完成的任务'">
        <p>完成了{{tomotoNum(item)}}个任务</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '放弃的任务'">
        <p>放弃了{{tomotoNum(item)}}个任务</p>
      </div>
      <div class="box-card" v-else-if="selectedItem === '中断的番茄'">
        <p>放弃了{{tomotoNum(item)}}个番茄</p>
      </div>
      <el-card shadow="never" class="box-card card-sum">每日小结
        <p>{{sumContents}}</p>
      </el-card>
    </div>
    <Page :total="size" @on-change="pageChange" show-elevator class="footer"/>
  </div>
</template>


<script>
var sourceData = [
  [
    { time: "10:00-10:25", content: "Apple I 问世" },
    { time: "11:00-11:25", content: "发布 Macintosh" },
    { time: "11:00-11:25", content: "发布 Macintosh" }
  ],
  [
    { time: "10:00-10:25", content: "Apple I 问世" },
    { time: "11:00-11:25", content: "发布 Macintosh" },
    { time: "11:00-11:25", content: "发布 Macintosh" }
  ]
];

var sourceData2 = [
  [
    { time: "11:00", content: "发布 Macintosh" },
    { time: "11:25", content: "发布 Macintosh" }
  ],
  [
    { time: "10:25", content: "Apple I 问世" },
    { time: "11:25", content: "发布 Macintosh" },
    { time: "11:00", content: "发布 Macintosh" }
  ]
];

function getWeekByDay(dayValue) {
  var day = new Date(Date.parse(dayValue.replace(/-/g, "/"))); //将日期值格式化
  var today = new Array(
    "星期日",
    "星期一",
    "星期二",
    "星期三",
    "星期四",
    "星期五",
    "星期六"
  ); //创建星期数组
  //console.log(today[day.getDay()]);
  return today[day.getDay()]; //返一个星期中的某一天，其中0为星期日
}

function getTimeDiff(timeSpan) {
  const strtime = "2014-04-23 ";
  var time_list = timeSpan.split("-");
  var from_date = new Date(strtime + time_list[0]);
  var end_date = new Date(strtime + time_list[1]);
  var time_different = (end_date - from_date) / 60000; //毫秒转分钟
  return time_different;
}

export default {
  data() {
    return {
      items: sourceData,
      selectedDate: "请选择时间范围",
      weekday: "",
      sumContents: "",
      size: 0,
      options1: {
        shortcuts: [
          {
            text: "Today",
            value() {
              return new Date();
            },
            onClick: picker => {
              this.$Message.info("Click today");
            }
          },
          {
            text: "Yesterday",
            value() {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              return date;
            },
            onClick: picker => {
              this.$Message.info("Click yesterday");
            }
          },
          {
            text: "One week",
            value() {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              return date;
            },
            onClick: picker => {
              this.$Message.info("Click a week ago");
            }
          }
        ]
      },
      selectedItem: "已完成的番茄"
    };
  },
  methods: {
    timeChange(val) {
      this.selectedDate = val[0];
      this.weekday = getWeekByDay(val[0]);
    },
    valueChange(val) {
      this.items = sourceData2;
    },
    pageChange(val) {
      console.log(val);
    },
    tomotoNum: function(val) {
      return val.length;
    },
    totalTime: function(items) {
      var total = 0;
      for (var i = 0; i < items.length; i++) {
        total += getTimeDiff(items[i].time);
      }
      var hours = parseInt(total / 60); //小时
      var minutes = total % 60; //分钟
      if (hours === 0) {
        return minutes + "分钟";
      }
      return hours + "小时" + minutes + "分钟";
    }
  },
  computed: {}
};
</script>

<style scoped>
.time {
  font-size: 14px;
  font-weight: bold;
}
.content {
  padding-left: 5px;
}

.list-side {
  width: 90%;
  margin: 10px;
  margin-left: 60px;
}

.list-container {
  display: flex;
  flex-flow: column;
  height: 100%;
}

.list-head {
  margin: 20px;
  display: flex;
  padding: 20px;
  flex-flow: row;
  border: 1px solid #ebeef5;
  border-radius: 18px;
}

.list-second-head {
  float: left;
}

.time-line {
  margin-left: 50px;
  margin-top: 50px;
}

.box-card {
  width: 300px;
  margin: 50px;
  margin-left: 80px;
  font-size: 13px;
}

.card-head {
  font-size: 15px;
  font-weight: bold;
}

.card-extra {
  font-size: 12px;
  font-style: oblique;
}

.card-sum {
  margin: 50px;
  width: 380px;
  font-size: 15px;
  font-weight: bold;
}

.selector {
  width: 150px;
  margin-left: 0px;
  float: right;
}
</style>
