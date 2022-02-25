<template>
  <div id="nav-bar" :class="`nav-bar-theme`">
    <b-navbar :class="`layout-nav-bar  `">
      <b-navbar-nav class="body-left">
        <b-nav-item link-classes="p-0">
          <div class="logo d-flex flex-nowrap align-items-center overflow-hidden ">
            <span><b-img :src="require('@/assets/logo.png')"></b-img></span>
            <span class="brand collapse-hidden">Terrace Console</span>
          </div>
        </b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="body-right dropdown">
        <b-nav-item class="nav-title  ">
          <a @click="switchMenu">
            <b-icon :icon="isLeftIcon?'text-indent-left':'text-indent-right'" scale="1.3"/>
          </a>
        </b-nav-item>
        
        <b-nav-item>
          <b-breadcrumb>
            <a-breadcrumb-item href="">
              <div class="divider">
                <b-icon :icon="$route.meta.icon" scale="0.8"/>
              </div>
            </a-breadcrumb-item>
            <b-breadcrumb-item :id="`bc-nav-item-${index}`"
                               :key="r.name+idx"
                               v-for="(r,idx) of $route.matched">
              {{r.name}}
              <b-icon icon="chevron-down" v-if="idx!=$route.matched.length-1" scale="0.5"></b-icon>
              <b-popover v-if="idx<$route.matched.length-1"
                         container="popover-layout"
                         :target="`bc-nav-item-${index}`"
                         triggers="hover" placement="bottom-right">
                <b-nav vertical>
                  <b-nav-item :title="item.name"
                              :key="item.name+index"
                              v-for="(item,index) of getChildrenRoutes(r)"
                              :to="item.path"
                              class="p-0 rounded-0">
                    <b-icon :icon="item.meta.icon" scale="0.7" class="ml-2"/>
                    <span> {{item.name}}</span>
                  </b-nav-item>
                </b-nav>
              </b-popover>
            </b-breadcrumb-item>
          </b-breadcrumb>
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
        <b-nav-item-dropdown class="user-profile" right
                             toggle-class="text-decoration-none" no-caret>
          <template v-slot:button-content>
            <b-button class=" btn-circle  " size="tiny">
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
</template>

<script>
export default {
  name: 'layout-navbar',
  props: {
    theme: String,
    type: String,
    variant: String,
  },
  data() {
    return {
      isLeftIcon: false
    }
  },
  computed: {},

  methods: {
    switchMenu() {
      this.isLeftIcon = !this.isLeftIcon
      this.$emit('switch-menu', this.isLeftIcon)
      this.$bus.emit('to-mini', this.isLeftIcon)
      // console.log("type:" , typeof this.isLeftIcon)
      console.log(this.$route)

    },
    getChildrenRoutes(route) {
      console.log(route)
      for (let r of this.$router.options.routes) {
        if (r.name == route.name && r.path == route.path) {
          return r.children
        }
      }
      return []
    }


  }
}
</script>

<style scoped>
  .divider {
    padding-left: 0.75rem;
    border-left: #ffffff solid 1px;
  }


</style>