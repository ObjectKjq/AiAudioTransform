import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'about',
      component: () => import('../views/user/Login.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/user/Register.vue'),
    },
    {
      path: '/:pathMatch(.*)*',
      name: '404',
      component: () => import('../views/404.vue'),
    },
    {
      path: '/',
      name: 'layout',
      component: () => import('../layout/LayoutComponent.vue'),
      children: [
        {
          path: '/',
          name: 'myaudio',
          component: () => import('../views/myaudio/MyAudioList.vue'),
        },
        {
          path: '/audio',
          name: 'audio',
          component: () => import('../views/audio/AudioList.vue'),
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('../views/user/UserList.vue'),
        },
      ],
    },
  ],
})


import { UserControllerService } from '@/api'
import { message } from 'ant-design-vue'
import { userStore } from '@/stores/user'
import { OpenAPI } from '@/api'
// 定义白名单
const whiteList = ['/login', '/register']
// 前置路由守卫，校验用户登录状态
router.beforeEach((to, from, next) => {
  // 1. 在pinia中获取用户信息
  const { getUserInfo } = userStore()
  const user = getUserInfo()
  if (user == null) {
    // 1.1 未拿到用户信息，先检测有没有Authorization
    const Authorization = localStorage.getItem('Authorization')
    if (Authorization == undefined) {
      // 1.1.1 未拿到Authorization，跳转到登录页面
      toLogin(to, next) // 跳转登录
    } else {
      // 1.1.2 拿到了Authorization，异步请求用户信息
      UserControllerService.getUserInfo().then((res) => {
        if (res.code == 0) {
          // 1.1.2.1 拿到了用户信息，继续导航
          toNext(to, next) // 继续导航
        } else {
          // 1.1.2.2 未拿到了用户信息，非法token或者已经过期，跳转到登录页面
          // 删除非法Authorization
          localStorage.removeItem('Authorization')
          OpenAPI.HEADERS = undefined
          toLogin(to, next) // 跳转登录
        }
      }, (err) => {
        message.error('服务端异常！！')
      })
    }
  } else {
    // 1.2 拿到了用户信息，继续导航
    toNext(to, next) // 继续导航
  }
})

// 跳转登录页面
const toLogin = (to:any, next:any) => {
  if (whiteList.indexOf(to.path) !== -1) {
    next()
  } else {
    message.error('未登录！！')
    next(`/login?redirect=${to.path}`)
  }
}

// 继续导航
const toNext = (to:any, next:any) => {
  if (whiteList.indexOf(to.path) !== -1) {
    next(`/`) // 跳转到首页
  } else {
    next()
  }
}

export default router
