<template>
  <div>
    <b-nav vertical :class="`slide-menu  ml-0 mr-0 rounded-0` ">
      <b-nav-item :id="`slide-nav-item-${index}`"
                  :key="index" v-for="(item,index) of subMenus"
                  :to="item.path"
                  v-show="!item.hidden"
      >
        <div class="d-flex flex-nowrap align-items-center text-nowrap overflow-hidden">
          <span> <b-icon :icon="item.meta.icon" scale="0.8"/></span>
          <span class="collapse-hidden"> {{item.name}}</span>
        </div>
        <b-tooltip :target="`slide-nav-item-${index}`" placement="left" triggers="hover">{{ item.name }}</b-tooltip>
      </b-nav-item>
      <!--      <b-nav-item to="/home">-->
      <!--        <b-icon icon="house" scale="0.8"/>-->
      <!--        <span> 主页</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/app/list">-->
      <!--        <b-icon icon="grid" scale="0.8" exact exact-active-class="active"/>-->
      <!--        <span>应用列表</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/ext/points">-->
      <!--        <b-icon icon="intersect" scale="0.8"/>-->
      <!--        <span>扩展点</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/biz/rule">-->
      <!--        <b-icon icon="toggles" scale="0.8"/>-->
      <!--        <span>业务规则</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/abilities">-->
      <!--        <b-icon icon="layout-wtf" scale="0.8"/>-->
      <!--        <span>能力</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/kanban">-->
      <!--        <b-icon icon="kanban" scale="0.8"/>-->
      <!--        <span>统计</span>-->
      <!--      </b-nav-item>-->
      <!--      <b-nav-item to="/profile">-->
      <!--        <b-icon icon="person-circle" scale="0.8"/>-->
      <!--        <span>个人信息 </span>-->
      <!--      </b-nav-item>-->
      <b-nav-item to="/about" disabled>
        <span> <b-icon icon="info-circle-fill" scale="0.8"/></span>
        <span class="collapse-hidden"> 关于</span>
      </b-nav-item>
    </b-nav>
  </div>


</template>

<script>
export default {
  name: 'slider-menu',
  data () {
    return {
      subMenus: [],
    }
  },
  props: {
    menus: Array, //路由菜单
    multi: Boolean,
  },
  mounted () {
    this.$bus.on('changeSubMenu', this.changeSubMenu)
    if (this.multi) {
      if (this.menus && this.menus.length > 0) {
        this.changeSubMenu(0, this.menus[0].children)
      } else {
        this.changeSubMenu(0, [ this.menus[0] ])
      }
    } else {
      this.changeSubMenu(0, this.menus)
    }

  },
  methods: {
    changeSubMenu: function (idx, children) {
      this.subMenus = children
      // console.log(idx, children)
    }
  }
}
</script>


<style scoped lang="less">

</style>
