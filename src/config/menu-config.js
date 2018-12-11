var menus = [{
  name: '历史记录',
  id: 'record',
  icon: 'th-large',
  sub: [{
    name: '日视图',
    componentName: 'DayView1',
  }, {
    name: '月视图',
    componentName: 'MonthView1',
  }]
}, {
  name: '建议&分析',
  id: 'analysis',
  icon: 'wpforms',
  sub: [{
    name: '统计分析',
    componentName: 'WeekView2',
  }, {
    name: '建议',
    componentName: 'Advice',
  }]
}]

export default menus;
