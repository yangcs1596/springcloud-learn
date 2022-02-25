import app from '@/apis/app.js'
import Vue from "vue";
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    all: []
  },
  mutations: {
    setCards(state, cards) {
      state.all = cards
    }
  },
  actions: {
    getAllApps(ctx) {
      var cards = app.getApps()
      ctx.commit("setCards", cards)
    }
  }

})
export default store