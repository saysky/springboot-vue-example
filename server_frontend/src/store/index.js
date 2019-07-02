//vuex的使用：先安装vuex，npm install vuex --save
import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);

const state ={
  collapse:false,
  routesList:[],
  buttonList:new Set(),
};
const mutations = {
  setCollapse(state,collapse){
    state.collapse = collapse;
  },
  setRouteList(state,list){
    state.routesList = list;
  },
};
const actions = {};
export default new Vuex.Store({
  state,
  actions,
  mutations,
  modules:{

  }
})
