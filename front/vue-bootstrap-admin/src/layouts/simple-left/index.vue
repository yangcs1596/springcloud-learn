<template>
  <div class="simple">
    <div :class="` ${isMiniLayoutClass?'layout-min':''}`">
      <layout-navbar :menus="menus" :multi="multi" @switch-menu="switchLayoutClass"/>

      <div class="body-left  menu-left">
        <slider-menu :menus="menus" :multi="multi"/>
      </div>
      <div class="body-right content">
        <div class="container-fluid">
          <layout-main/>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import SliderMenu from './slide-menu'
import LayoutMain from './main'
import LayoutNavbar from './navbar'

export default {
  name: 'simple-layout',
  props: {
    menus: Array, //路由菜单
    multi: Boolean,
  },
  data () {
    return {
      isMiniLayoutClass: false,
    }
  },
  methods: {
    switchLayoutClass: function (isLeftIcon) {
      console.log(isLeftIcon)
      if (isLeftIcon) {
        this.isMiniLayoutClass = true
      } else {
        this.isMiniLayoutClass = false
      }

    }
  }
  ,
  components: {
    SliderMenu,
    LayoutMain,
    LayoutNavbar,
  }
}
</script>

<style lang="less">
@import "dark-orange";
</style>
