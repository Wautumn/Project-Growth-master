<template>
  <div class="container">
    <div class="section-header">
      <h2>推荐文章</h2>
      <div style="margin-top:10px">
        <el-tag
          class="head-tag"
          v-for="tag in tags"
          :key="tag.name"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
          :type="tag.type"
        >{{tag.name}}</el-tag>
      </div>
    </div>
    <div class="section-container">
      <el-card class="box-card">
        <img src="@/assets/logo.png" class="image">
        <div class="article-container">
          <a href="https://tutorialzine.com/articles">
            <h3>The Best JavaScript and CSS Libraries for 2018</h3>
          </a>
          <p>A recap of our favorite web dev resources for 2018, featuring some of the best libraries, frameworks and tools of the past year.</p>
        </div>
        <div class="article-footer">
          <p class="aurthor">Aurthor</p>
          <p class="aurthor">date</p>
          <p class="tag">tag</p>
        </div>
      </el-card>
    </div>
  </div>
</template>


<style scoped>
.head-tag {
  margin: 3px;
}
.container {
  margin-left: 30px;
}

.tag {
  margin: 3px;
  border: 1px solid #dcdfe6;
  border-color: #ebeef5;
  border-radius: 18px;
  padding: 7px 15px;
  float: right;
}

.aurthor {
  color: #3dd07d;
  font-size: 14px;
  margin: 5px;
}

.article-footer {
  position: relative;
}

.article-container {
  padding: 14px;
  position: relative;
}

.section-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
}

.section-header h2:before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 70px;
  height: 2px;
  background: #3dd07d;
  transform: translateX(-50%);
}

.section-header h2 {
  position: relative;
  display: inline-block;
  font-size: 18px;
  padding-bottom: 10px;
}

.section-header-class {
  margin-top: 15px;
  font-size: 15px;
}

.section-header {
  margin: 0 0 60px;
  margin-top: 60px;
  text-align: center;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
}

.image {
  display: block;
  width: 100%;
  height: 180px;
  object-fit: cover;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-card {
  padding: "0px";
  width: 380px;
  margin: 13px;
}

h3 {
  color: #343434;
  font-size: 1.5em;
  line-height: 1.35;
  margin-bottom: 1em;
}
</style>

<script>
export default {
  data() {
    return {
      currentDate: new Date(),
      tags: [
        { name: "标签一", type: "" },
        { name: "标签二", type: "success" },
        { name: "标签三", type: "info" },
        { name: "标签四", type: "warning" },
        { name: "标签五", type: "danger" }
      ]
    };
  },
  methods: {
    handleClose(tag) {
      this.tags.splice(this.tags.indexOf(tag), 1);
    }
  }
};
</script>
