import Vue from 'vue';
import Login from './login.vue';
// 引入和使用elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueResource from 'vue-resource';

Vue.use(VueResource);
Vue.use(ElementUI);

new Vue({
    el: '#app',
    template: '<Login/>',
    components: {Login},
})
