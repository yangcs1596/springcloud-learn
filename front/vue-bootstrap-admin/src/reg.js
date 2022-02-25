import Vue from 'vue'
import Components from '@/components/index'
import Layouts from '@/layouts/index'
//只有用Vue.use才可以全局使用，注意在main有导入
Vue.use(Components)
Vue.use(Layouts)

import site from '@/config/site'

Vue.prototype.$site = site.site


// import Fontawesome from '@fortawesome/fontawesome-svg-core'
// import solid from '@fortawesome/free-solid-svg-icons'
// import brands from '@fortawesome/free-brands-svg-icons'
// import regular from '@fortawesome/free-regular-svg-icons'
// Fontawesome.library.add(solid, brands, regular)
// import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome'
// Vue.component('fa-icon', FontAwesomeIcon)
// Vue.component('fa-layers', FontAwesomeLayers)
// Vue.component('fa-text', FontAwesomeLayersText)