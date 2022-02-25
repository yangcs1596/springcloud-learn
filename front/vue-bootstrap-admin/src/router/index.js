import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '@/views/Home.vue'
import routesCfg from '@/config/routes'

Vue.use(VueRouter)
const routes = routesCfg.routes
routes.push(
  {
    path: '/',
    name: '主页',
    component: import(/* webpackChunkName: "about" */ '@/views/Home.vue'),
    redirect: '/home',
    hidden: true,
    meta: {
      // target: '_blank',
      title: 'menu.home', icon: 'house', permission: [ 'home' ]
    },
  })

const router = new VueRouter({
  routes,
  linkActiveClass: 'active',
})

export default router
