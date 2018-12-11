<template>
  <div>
    <div style="margin-top: 10px">
      <el-button size="medium" @click="createTask">新建任务</el-button>
    </div>
    <el-table
      ref="singleTable"
      :data="tableData"
      :row-class-name="tableRowClassName"
      highlight-current-row
      @current-change="handleCurrentChange"
      style="width: 100%"
    >
      <el-table-column type="index" width="30"></el-table-column>
      <el-table-column property="task" label="任务"></el-table-column>
      <el-table-column property="Pomo" label="进度" width="50"></el-table-column>
      <!--
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            style="margin-left: 5px"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template> 
        
      </el-table-column>
      -->
    </el-table>
    <!--
    <div style="margin-top: 10px; float: right">
      <el-button size="medium" @click="setCurrent()">取消选择</el-button>
    </div> 
    -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentRow: null
    };
  },
  props: ["tableData"],
  methods: {
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.$emit("transferTask", null);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      this.$emit("transferTask", this.currentRow);
      console.log(val);
    },
    tableRowClassName({ row }) {
      if (row.currentPomo == 0) {
        return "new-task";
      } else if (row.currentPomo > 0 && row.currentPomo < row.totalPomo) {
        return "processing-task";
      } else if (row.currentPomo == row.totalPomo) {
        return "finished-task";
      }
      return "";
    },
    createTask() {
      this.$prompt("请输入任务内容", "新建任务", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      });
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    }
  }
};
</script>

<style>
.el-table .processing-task {
  background: oldlace;
}
.el-table .finished-task {
  background: #f0f9eb;
}
</style>