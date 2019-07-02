import Vue from 'vue'
import Router from 'vue-router';
import server from '@/page/manage/server/server'
import message from '@/page/manage/message/message'

Vue.use(Router);
const routerConfig = [
    {
        path: '/',
        redirect: '/server',

    },
    {
        path: '/server',
        name: '服务器管理',
        icon: 'el-icon-menu',
        component: server,
        meta: {
            requireAuth: true, // 判断是否需要登录
        },
    },
    {
        path: '/message',
        name: '消息管理',
        icon: 'el-icon-menu',
        component: message,
        meta: {
            requireAuth: true, // 判断是否需要登录
        },
    },

]
const router = new Router({
    mode: 'history',
    routes: routerConfig
})
export {routerConfig, router}
//export的语法，可以先定义变量，然后再export {要导出的变量}
//第二种方法： exports.变量名 = ......
