import Vue from 'vue'
import VueRouter from 'vue-router'
import config from './config.js'
import store from '../store'


Vue.use(VueRouter)

// const routes = [
//   {
//     path: '/',
//     name: 'Home',
//     component: Home
//   },
//   {
//     path: '/about',
//     name: 'About',
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
//   }
// ]

const router = new VueRouter(config)

// router.beforeEach(function(to,from,next){ //每次通过路由去其他组件的时候都要先运行该方法
//     if(to.meta.auth){ //meta的信息要在路由配置中先写好
//         if(store.state.loginUser.isLoading){
//             next({name:"auth",query:{ returnurl : to.fullPath }}); //如果仓库里面的isLoading为true则跳转到验证页面auth.vue
//         }
//         else if(store.state.loginUser.data){
//             next() //如果仓库里有数据的则跳转到to页面,也就是说点击要跳转的页面
//         }
//         else{
//             next({name:'Login'})//还没登录的则跳转到登录页面
//         }
//     }
//     else{
//         next();
//     }
// })

// 等之后搞登录的时候再来处理这个
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        return next()
    }else{
        if (sessionStorage.getItem('Authorization')) {
            next();
        } else {
            next({path:'/login'});
        }
    }


});

export default router
