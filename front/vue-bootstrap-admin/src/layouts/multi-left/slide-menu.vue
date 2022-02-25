<template>
  <div class="slide-menu">
    <ul :class="`nav flex-column `">
      <li class="nav-item" :key="index" v-for="(route,index) of menus">
        <b-link :id="`slide-nav-item-${index}`"
                @click="collapseToggle(index)"
                v-if="route.children && route.children.length>0"
                :class="`nav-link collapse-button rounded-0 ${isMenuOpen[index] || ( isMini && isMiniMenuOpen[index] )?'not-collapsed':'collapsed'}`"
        >
          <!--                v-b-tooltip.hover.right :title="route.name">-->
          <div class="d-flex flex-nowrap align-items-center text-nowrap overflow-hidden">
            <span><b-icon :icon="route.meta.icon" scale="0.8"/></span>
            <span class="pl-3 mr-auto collapse-hidden">{{route.name}}</span>
            <b-icon :icon="isMenuOpen[index]?'chevron-up':'chevron-down'" class="collapse-hidden" scale="0.6"/>
          </div>
          <b-tooltip :target="`slide-nav-item-${index}`" placement="left" triggers="hover">
            {{route.name}}
          </b-tooltip>
          <b-popover custom-class="slide-menu" container="popover-layout"
                     :target="`slide-nav-item-${index}`" disabled
                     triggers="hover" placement="right">
            <b-nav @click="popoverHide(`slide-nav-item-${index}`,index)" vertical>
              <b-nav-item :title="child.name" :to="pathJoin(route.path,child.path)"
                          :key="`${index}-${idx}`"
                          v-for="(child,idx) of route.children" class="p-0 rounded-0">
                <b-icon :icon="child.meta.icon" scale="0.7" class="ml-2"/>
                <span> {{child.name}}</span>
              </b-nav-item>
            </b-nav>
          </b-popover>
        </b-link>
        <collapse>
          <div v-if="route.children && route.children.length>0 && isMenuOpen[index]"
               :id="`accordion-${index}`"
               :accordion="`accordion-${multiExpand?index:0}`"
               :class="`sub-menu `">
            <!--               :class="`sub-menu ${isMenuOpen[index]?'visible':'invisible'}`">-->
            <b-nav vertical pills class=" pt-2 pb-2 rounded-0 ">
              <b-nav-item v-b-tooltip.hover.right :title="child.name" :to="pathJoin(route.path,child.path)"
                          :key="`${index}-${idx}`"
                          v-for="(child,idx) of route.children" class="rounded-0">
                <div class="d-flex flex-nowrap align-items-center text-nowrap overflow-hidden">
                  <b-icon :icon="child.meta.icon" scale="0.8" class="ml-2"/>
                  <span class="pl-3 mr-auto collapse-hidden"> {{child.name}}</span>
                </div>
              </b-nav-item>
            </b-nav>
          </div>
        </collapse>
        <b-link v-b-tooltip.hover.right :title="route.name" :to="route.path"
                v-if="!(route.children && route.children.length>0)" class="nav-link rounded-0">
          <div class="d-flex flex-nowrap align-items-center text-nowrap overflow-hidden">
            <span> <b-icon :icon="route.meta.icon" scale="0.8"/></span>
            <span class="pl-3 mr-auto collapse-hidden"> {{route.name}}</span>
          </div>

        </b-link>
      </li>

    </ul>

  </div>


</template>

<script>
// import routes from './multi-routes'
import collapse from './collapse.js'

var path = require('path')
export default {
  name: 'slider-menu',
  components: { 'collapse': collapse, },
  props: {
    menus: Array, //路由菜单
    multiExpand: Boolean, //多个子菜单是否可以同时展开，true是允许展开，false是不允许
    isMini: Boolean,
  },
  data () {
    let isMenuOpen = []
    let isMiniMenuOpen = []
    this.menus.forEach(function (v, i) {
      isMiniMenuOpen[i] = false
      isMenuOpen[i] = false
    })
    return {
      isMenuOpen: isMenuOpen,
      isMiniMenuOpen: isMiniMenuOpen,
      lastMenuOpenedIdx: -1,
      lastMiniMenuOpenedIdx: -1,
      routes: this.menus,
    }
  },

  currentActived: null,
  mounted: function () {
    this.$bus.on('to-mini', this.toMini)
  },
  computed: {},
  methods: {
    popoverHide: function (id, idx) {
      this.$root.$emit('bv::hide::popover', id)
      if (this.lastMiniMenuOpenedIdx >= 0) {
        this.$set(this.isMiniMenuOpen, this.lastMiniMenuOpenedIdx, false)
      }
      this.$set(this.isMiniMenuOpen, idx, true)
      this.lastMiniMenuOpenedIdx = idx
    },
    toMini: function (isMini) {

      let tEventName = 'bv::enable::tooltip'
      let pEventName = 'bv::disable::popover'
      if (isMini) {
        tEventName = 'bv::disable::tooltip'
        pEventName = 'bv::enable::popover'
      }
      let _this = this
      this.isMenuOpen.forEach(function (v, i) {
        _this.$root.$emit(tEventName, 'slide-nav-item-' + i)
        _this.$root.$emit(pEventName, 'slide-nav-item-' + i)

      })
    },

    pathJoin: function (...p) {
      return path.join(...p)
    },
    collapseToggle: function (idx) {
      //如果左右折叠为最小的
      if (this.isMini) {
        console.log(this.isMini)
        return
      }
      //支持多个子菜单同时打开
      if (this.multiExpand) {
        //切换当前操作的菜单折叠状态
        this.$set(this.isMenuOpen, idx, !this.isMenuOpen[idx])
        return
      }

      //不能同时展开多个子菜单（也是同时只能展开一个子菜单）
      if (!this.multiExpand
        // 并且已经有展开的子菜单
        && this.lastMenuOpenedIdx >= 0
        // 并且已经展开的子菜单处于展开状态
        && this.isMenuOpen[this.lastMenuOpenedIdx]
        //并且已经展开的子菜单不是当前操作的子菜单
        && this.lastMenuOpenedIdx != idx) {
        //将已经展开的子菜单收缩
        this.$set(this.isMenuOpen, this.lastMenuOpenedIdx, false)
      }
      //切换当前操作的菜单折叠状态
      this.$set(this.isMenuOpen, idx, !this.isMenuOpen[idx])
      //不能同时展开多个子菜单（也是同时只能展开一个子菜单）
      if (!this.multiExpand) {
        //如果当前子菜单处于展开状态
        if (this.isMenuOpen[idx]) {
          this.lastMenuOpenedIdx = idx
        }
        //如果已经展开的子菜单和当前操作的子菜单是同一个,并且当前子菜单处于折叠状态，
        //则设置当前无展开菜单
        if (this.lastMenuOpenedIdx == idx && !this.isMenuOpen[idx]) {
          this.lastMenuOpenedIdx = -1
        }
      }
      // let id = `accordion-${idx}`
      // this.$root.$emit('bv::toggle::collapse', id)
    }
  }
}
</script>


<style scoped>

</style>
