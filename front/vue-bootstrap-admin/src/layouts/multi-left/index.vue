<template>
  <div class="multi">
    <div :class="`  ${isMiniLayoutClass?'layout-min':''}`">
      <layout-navbar @switch-menu="switchLayoutClass"/>
      <div :class="`body-left menu-left`">
        <slider-menu :menus="menus"
                     :multiExpand="multiExpand"
                     :is-mini="isMiniLayoutClass"
        />
      </div>
      <div class="body-right">
        <div class="container-fluid p-0 content">
          <layout-main/>
        </div>
      </div>
    </div>
    <div id="popover-layout"></div>
  </div>
</template>

<script>
import SliderMenu from './slide-menu'
import LayoutMain from './main'
import LayoutNavbar from './navbar'

export default {
  name: 'multi-left-layout',
  props: {
    menus: Array, //路由菜单
    multiExpand: Boolean, //多个子菜单是否可以同时展开,
    theme: String,
  },
  data () {
    return {
      isMiniLayoutClass: false,
    }
  },
  methods: {
    switchLayoutClass: function (isLeftIcon) {
      // console.log("isMiniLayoutClass: ",isLeftIcon)
      if (isLeftIcon) {
        this.isMiniLayoutClass = true
      } else {
        this.isMiniLayoutClass = false
      }

    }
  },
  components: {
    SliderMenu,
    LayoutMain,
    LayoutNavbar,
  }
}
</script>


<style lang="less">
@import "dark-blue";
</style>
