import Vue from 'vue'
import Router from 'vue-router'
import Menus from '@/config/header-config'
import menus from '@/config/menu-config'

Vue.use(Router)

var routes = [];
var croutes = [];



Menus.forEach(element => {
  routes.push({
    path: `/${element.componentName}`,
    name: element.componentName,
    component: () => import(`@/components/${element.componentName}`)
  })
});

menus.forEach(element => {
  element.sub.forEach((sub) => {
    croutes.push({
      path: `/${sub.componentName}`,
      name: sub.componentName,
      component: () => import(`@/components/${sub.componentName}`)
    })
  })
});

routes[2].children = croutes;

routes.push({
  path: `/`,
  name: "Login",
  component: () => import(`@/components/Login`),
})


export default new Router({
  routes
})
