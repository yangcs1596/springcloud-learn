<template>
  <div id="nav-bar" :class="`nav-bar-theme shadow`">
    <div class="body-left ">
      <div class="logo d-flex flex-nowrap align-items-center overflow-hidden ">
        <span><b-img :src="require('@/assets/logo.png')"/></span>
        <span class="brand collapse-hidden">Terrace Console</span>
      </div>
    </div>
    <div class="body-right">
      <b-navbar :class="`layout-nav-bar  `">
        <b-navbar-nav>
          <b-nav-item class="nav-title">
            <span @click="switchMenu">
              <b-icon :icon="isLeftIcon?'text-indent-left':'text-indent-right'" scale="1.3"/>
            </span>
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav v-if="multi">
          <b-nav-item @click="changeSubMenu(index,route)" :key="index" v-for="(route,index) of menus">
            {{route.name}}
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          
          <b-nav-item class="nav-title">
            <b-icon :icon="this.$route.meta.icon" scale="1"/>
            <span>{{this.$route.name}}</span>
          </b-nav-item>
        </b-navbar-nav>
        
        
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-input-group size="sm" class="layout-input-group">
              <b-form-input placeholder="Host"></b-form-input>
              <b-input-group-append>
                <b-button type="submit">
                  <b-icon icon="arrow-right"/>
                </b-button>
              </b-input-group-append>
            </b-input-group>
          </b-nav-form>
          <b-nav-text></b-nav-text>
          <b-nav-item-dropdown class="user-profile" right toggle-class="text-decoration-none" no-caret>
            <template v-slot:button-content>
              <b-button class="btn-circle" size="tiny">
                <b-icon icon="person-fill" scale="1.5"/>
              </b-button>
            </template>
            <b-dropdown-item href="#">
              <b-icon icon="person" scale="0.8"/>
              <span>个人设置</span>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <b-icon icon="gear-fill" scale="0.8"/>
              <span>系统设置</span>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <b-icon icon="power" scale="0.8"/>
              <span>登出</span>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-navbar>
    </div>
  </div>
</template>

<script>
export default {
  name: 'layout-navbar',
  props: {
    menus: Array, //路由一级菜单
    multi: Boolean,
  },
  data() {
    return {
      isLeftIcon: false
    }
  },
  methods: {
    switchMenu: function () {

      if (this.isLeftIcon) {
        this.isLeftIcon = false
      } else {
        this.isLeftIcon = true
      }
      this.$emit('switch-menu', this.isLeftIcon)
    },
    changeSubMenu: function (idx, route) {
      // console.log(idx, route, route.children)
      this.$bus.emit('changeSubMenu', idx, route.children)
      let deft = route
      if (route.children && route.children.length > 0) {
        deft = route.children[0]
        route.children.forEach(function (v) {
          if (v.meta.default) {
            deft = v
          }
        })
      }

      this.$router.push(deft)
    }
  }
}
</script>

<style scoped lang="less">


</style>