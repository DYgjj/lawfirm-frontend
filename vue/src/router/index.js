import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Layout/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import Layout from "@/views/Layout";
import LawyerView from "@/views/Layout/LawyerView";
import ClientView from "@/views/Layout/ClientView";
import LawCaseView from "@/views/Layout/LawCaseView";
import UserView from "@/views/Layout/UserView";
import RegisterView from "@/views/RegisterView";
import Auth from "@/views/Auth";
import profile from "@/views/Layout/Profile";
import password from "@/views/Layout/Password";
import LawCaseAcceptanceView from "@/views/Layout/LawCaseAcceptanceView";
import LawCaseCompletionView from "@/views/Layout/LawCaseCompletionView";
import LawCaseRefusalView from "@/views/Layout/LawCaseRefusalView";
import LogsView from "@/views/Layout/LogsView";

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    meta: { name: 'Login'},
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    meta: { name: 'Register'},
    component: RegisterView
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        meta: { name: 'Dashboard'},
        component: HomeView
      },
      {
        path: 'user',
        name: 'user',
        meta: { name: 'Account Information'},
        component: UserView
      },
      {
        path: 'lawyer',
        name: 'lawyer',
        meta: { name: 'Lawyer'},
        component: LawyerView
      },
      {
        path: 'client',
        name: 'client',
        meta: { name: 'Client'},
        component: ClientView
      },
      {
        path: 'lawCase',
        name: 'lawCase',
        meta: { name: 'Rejected Case'},
        component: LawCaseView
      },
      {
        path: 'lawCaseAcceptance',
        name: 'lawCaseAcceptance',
        meta: { name: 'Ongoing Case'},
        component: LawCaseAcceptanceView
      },
      {
        path: 'lawCaseCompletion',
        name: 'lawCaseCompletion',
        meta: { name: 'Closed Case'},
        component: LawCaseCompletionView
      },
      {
        path: 'lawCaseRefusal',
        name: 'lawCaseRefusal',
        meta: { name: 'Audit Case'},
        component: LawCaseRefusalView
      },
      {
        path: 'profile',
        name: 'profile',
        meta: { name: 'Profile'},
        component: profile
      },
      {
        path: 'password',
        name: 'password',
        meta: { name: 'Change password'},
        component: password
      },
      {
        path: 'logs',
        name: 'logs',
        meta: { name: 'Logs'},
        component: LogsView
      },
    ]
  },
  {
    path: '/403',
    name: '403',
    meta: { name: 'No Permission'},
    component: Auth
  },
  {
    path: '*',
    name: '404',
    meta: { name: '404 NOT FOUND'},
    component: () => import('../views/404.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



// //路由守卫
// router.beforeEach((to ,from, next) => {
//   const user = localStorage.getItem("user");
//   if (!user && to.path !== '/login' && to.path !== '/register') {
//     next("/login");
//   } else {
//     next();
//   }
//
// })

router.beforeEach((to, from, next) => {
  // to 是到达的路由信息
  // from 是开源的路由信息
  // next 是帮助我们跳转路由的函数
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('user') || '{}')
  if (user.role !== 'ROLE_ADMIN' && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，然后当前的到达的路径是管理员才有权限访问的路径，那这个时候我就让用户去到一个没有权限的页面，不让他访问实际的页面
    next('/403')
  } else {
    next()
  }
})


export default router
