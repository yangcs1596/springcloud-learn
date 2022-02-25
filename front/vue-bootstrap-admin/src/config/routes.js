//如果使用嵌套路由，注意父路由中包含router-view组件
const RouteView = {
  name: 'RouteView',
  render: (h) => h('router-view')
}


const routes = [
  {
    path: '/main',
    name: '主页',
    component: RouteView,
    // redirect: '/dashboard/workplace',
    // hidden: false,
    meta: {
      // target: '_blank',
      title: 'menu.home', icon: 'house', permission: [ 'home' ]
    },
    children: [
      {
        path: 'home',
        name: '概况',
        component: () => import(/* webpackChunkName: "about" */ '@/views/Home.vue'),
        meta: {
          title: 'menu.bar', default: true, icon: 'house', permission: [ 'home' ]
        }
      },
      {
        path: 'list',
        name: '列表',
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.about', icon: 'grid', permission: [ 'home' ]
        },
      },

    ]
  },
  {
    path: '/vb/table',
    name: '表格',
    component: () => import(/* webpackChunkName: "about" */ '../views/table.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.ext', icon: 'intersect', permission: [ 'home' ]
    },
  },
  {
    path: '/example1',
    name: 'Example 1',
    component: () => import(/* webpackChunkName: "about" */ '../views/Example1.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.biz', icon: 'toggles', permission: [ 'home' ]
    },
  },
  {
    path: '/view4',
    name: '菜单4',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.ability', icon: 'layout-wtf', permission: [ 'home' ]
    },
  },
  {
    path: '/ex',
    name: '例子',
    component: RouteView,
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'puzzle-fill', permission: [ 'home' ]
    },
    children: [
      {
        path: 'example1-1',
        name: '例子1',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About1.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'example2',
        name: '例子2',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About2.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'example3',
        name: '例子3',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About2.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'example4',
        name: '例子4',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About4.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'example5',
        name: '例子5',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      }

    ],
  },
  {
    path: '/count',
    name: '统计',
    component: RouteView,
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'kanban-fill', permission: [ 'home' ]
    },
    children: [
      {
        path: 'count-1',
        name: '统计1',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About1.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'count-2',
        name: '统计2',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About2.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
      {
        path: 'count-3',
        name: '统计3',
        component: () => import(/* webpackChunkName: "about" */ '@/views/About3.vue'),
        meta: {
          // target: '_blank',
          title: 'menu.example', icon: 'kanban-fill', permission: [ 'home' ]
        },
      },
    ]
  },
  {
    path: '/profile',
    name: '个人信息',
    component: () => import(/* webpackChunkName: "about" */ '@/views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'person-circle', permission: [ 'home' ]
    },
  },
  {
    path: '/about',
    name: '关于',
    component: () => import(/* webpackChunkName: "about" */ '@/views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'info-circle-fill', permission: [ 'home' ]
    },
  }
]

export default {
  routes: routes,
}
