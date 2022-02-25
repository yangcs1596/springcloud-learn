import LayoutSimple from './simple-left/index.vue'
import LayoutMulti from './multi-left/index.vue'

const Loading = function (Vue) {
  Vue.component('simple-layout', LayoutSimple)
  Vue.component('multi-layout', LayoutMulti)
}

export default Loading
