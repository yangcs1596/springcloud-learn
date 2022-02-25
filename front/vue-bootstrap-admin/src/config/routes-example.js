const routesExample = [
  {
    path: '/home',
    name: '概况',
    component: () => import('@/views/Home.vue'),
    // redirect: '/dashboard/workplace',
    // hidden: false,
    meta: {
      // target: '_blank',
      title: 'menu.home', icon: 'house', permission: [ 'home' ]
    },
  },
  {
    path: '/list',
    name: '列表',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'grid', permission: [ 'home' ]
    },
  },
  {
    path: '/view2',
    name: '菜单2',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.ext', icon: 'intersect', permission: [ 'home' ]
    },
  },
  {
    path: '/view3',
    name: '菜单3',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
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
    path: '/count',
    name: '统计',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'kanban-fill', permission: [ 'home' ]
    },
  },
  {
    path: '/profile',
    name: '个人信息',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'person-circle', permission: [ 'home' ]
    },
  },
  {
    path: '/about',
    name: '关于',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue'),
    meta: {
      // target: '_blank',
      title: 'menu.about', icon: 'info-circle-fill', permission: [ 'home' ]
    },
  }
]

export default {
  routes: routesExample
}
