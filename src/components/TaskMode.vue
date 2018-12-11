<template>
  <div>
    <el-container>
      <el-aside width="300px">
        <tasklist @transferTask="getTask" :tableData="taskData"></tasklist>
      </el-aside>

      <el-main>
        <div style="text-align: center">
          <strong
            style="font-size: 200px; line-height: 300px; color: #409eff"
          >{{minutes}}:{{seconds}}</strong>
          <br>
          <el-button
            :type="countButtonType"
            v-show="!currentCondition"
            :disabled="countOn"
            @click="startCount"
          >开始番茄钟</el-button>
          <el-button
            type="warning"
            v-show="!currentCondition"
            :disabled="!countOn"
            @click="stopCount"
          >中止番茄钟</el-button>
          <el-button type="success" v-show="currentCondition" @click="deletePomos">完成任务</el-button>
          <br>
        </div>

        <h2>{{currentTaskName}}</h2>
        <el-steps :active="currentFinishedPomo" space="100px" finish-status="success">
          <el-step v-for="n in currentTotalPomo"></el-step>
        </el-steps>
        <br>
        <h1 v-show="selected">任务详情</h1>
        <el-form v-show="selected">
          <el-form-item>
            <el-input type="textarea"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button type="danger" v-show="!currentCondition" @click="deletePomos">废弃任务</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { clearInterval } from "timers";
import { setInterval } from "timers";
import TaskList from "@/components/TaskList";

export default {
  components: {
    tasklist: TaskList
  },
  data() {
    return {
      countButtonType: "success",
      countOn: false,
      selected: false,
      count: "0",
      minutes: "0",
      seconds: "0",
      timer: null,
      currentTask: null,
      currentTaskName: "",
      currentFinishedPomo: null,
      currentTotalPomo: null,
      currentCondition: false,
      taskData: [
        {
          index: 0,
          task: "java ee项目",
          detail: "数据库设计",
          currentPomo: "1",
          totalPomo: "3",
          Pomo: "1/3"
        },
        {
          index: 1,
          task: "软件工程项目",
          detail: "前端",
          currentPomo: "3",
          totalPomo: "3",
          Pomo: "3/3"
        }
      ]
    };
  },
  methods: {
    startCount() {
      if (!this.selected) {
        this.$alert("请选择一个任务以开始本番茄钟", "提示", {
          confirmButtonText: "确定",
          callback: action => {}
        });
        return;
      }
      const TIME_COUNT = 5;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.countOn = true;
        this.countButtonType = "danger";
        this.seconds = this.count % 60;
        this.minutes = parseInt(this.count / 60);
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
            this.seconds = this.count % 60;
            this.minutes = parseInt(this.count / 60);
          } else {
            this.countOn = false;
            this.countButtonType = "success";
            clearInterval(this.timer);
            this.timer = null;
            this.currentFinishedPomo++;
            this.taskData[this.currentTask].currentPomo++;
            this.taskData[this.currentTask].Pomo =
              this.currentFinishedPomo + "/" + this.currentTotalPomo;
            if (
              this.taskData[this.currentTask].currentPomo ==
              this.taskData[this.currentTask].totalPomo
            )
              this.currentCondition = true;
            else this.currentCondition = false;
          }
        }, 1000);
      }
    },
    stopCount() {
      this.count = "0";
      this.minutes = "0";
      this.seconds = "0";
      this.currentFinishedPomo--;
      this.taskData[this.currentTask].Pomo =
        this.currentFinishedPomo + "/" + this.currentTotalPomo;
    },
    getTask(msg) {
      this.selected = true;
      this.currentTask = msg.index;
      this.currentTaskName = msg.task;
      this.currentFinishedPomo = msg.currentPomo;
      this.currentTotalPomo = parseInt(msg.totalPomo);
      if (
        this.taskData[this.currentTask].currentPomo ==
        this.taskData[this.currentTask].totalPomo
      )
        this.currentCondition = true;
      else this.currentCondition = false;
    }
  }
};
</script>

<style>
</style>