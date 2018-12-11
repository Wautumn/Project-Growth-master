<template>
  <div>
    <full-calendar :events="fcEvents" locale="en"></full-calendar>
  </div>
</template>

<script>
var demoEvents = [
  {
    title: "Sunny Out of Office",
    start: "2018-12-06",
    end: "2018-12-07"
  }
];

export default {
  data() {
    return {
      fcEvents: demoEvents
    };
  },
  components: {
    "full-calendar": require("vue-fullcalendar")
  }
};
</script>

<style>
.comp-full-calendar {
  padding: 15px;
  background: #fff;
  max-width: none;
  margin: 0 auto;
  font-size: medium;
}
</style>



