<template>
  <div class="home" id="home">
    <div class="t-toolbar">
      <b-navbar type="light" variant="light">
        <b-navbar-brand class="t-toolbar-title">列表</b-navbar-brand>
        <b-navbar-nav class="ml-auto">
          <b-nav-form right>
            <b-form-input size="sm" class="mr-sm-2" placeholder="名称"></b-form-input>
            <b-form-input size="sm" class="mr-sm-2" placeholder="其他"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">查询</b-button>
            <b-button-group class="ml-2">
              <b-button size="sm" variant="outline-primary">
                <b-icon icon="grid"/>
              </b-button>
              <b-button size="sm" variant="outline-primary">
                <b-icon icon="list-task"/>
              </b-button>
            </b-button-group>
          </b-nav-form>
        </b-navbar-nav>
      </b-navbar>
    </div>
    <div class="t-content">

    </div>
  </div>
</template>

<script>
import {
  mapState,
  // mapActions
} from 'vuex'

export default {
  name: 'Home',
  computed: mapState({
    cards: state => state.all
  }),
  created () {
    this.$store.dispatch('getAllApps')
  },
  data () {
    return {}
  },
}
</script>

<style>
.home {
}

.t-toolbar {
  margin: -16px -16px 0px -16px;
  background-color: #ffffff;
}

.t-toolbar-title {
  border-left: #007bff 3px solid;
  padding-left: 24px;
}

.t-content {
  background-color: #ffffff;
  height: 100%;
}

.t-card-container {
  width: 280px;
}
</style>
