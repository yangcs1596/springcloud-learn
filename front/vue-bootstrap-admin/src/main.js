import Vue from 'vue'
import less from 'less'
import App from './App.vue'
import bus from '@/utils/bus'

Vue.prototype.$bus = bus.events
//BootstrapVue
import {BootstrapVue, BootstrapVueIcons} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(less)
Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

//全局导入js
import 'es6-promise/auto'
import './css/app.css'
import router from './router'
import './reg'
import store from './store'

Vue.config.productionTip = false

new Vue({
    store,
    router,
    render: h => h(App),
}).$mount('#app')


