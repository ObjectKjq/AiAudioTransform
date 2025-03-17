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

// 1.1.1 未拿到token，跳转到登录页面
// 1.1.2 拿到了token，请求用户信息

import { UserControllerService } from '@/api'
import { message } from 'ant-design-vue'
import { userStore } from '@/stores/user'
import { OpenAPI } from '@/api'
// 定义白名单
const whiteList = ['/login', '/register']
// 前置路由守卫，校验用户登录状态
router.beforeEach((to, from, next) => {
  // UserControllerService.getUser({ id: 1 }).then((res) => {
  //   console.log(res)
  // })
  // 1. 在pinia中获取用户信息
  const { getUserInfo } = userStore()
  const user = getUserInfo()
  if (user == null) {
    // 1.1 未拿到用户信息，异步请求用户信息
    UserControllerService.getUserInfo().then((res) => {
      if (res.code == 0) {
        // 1.1.1 拿到了用户信息，继续导航
        if (whiteList.indexOf(to.path) !== -1) {
          next(`/`) // 跳转到首页
        } else {
          next()
        }
      } else {
        // 1.1.2 未拿到了用户信息，跳转到登录页面
        // 删除Authorization
        localStorage.removeItem('Authorization')
        OpenAPI.HEADERS = undefined
        if (whiteList.indexOf(to.path) !== -1) {
          next()
        } else {
          message.error('未登录！！')
          next(`/login?redirect=${to.path}`)
        }
      }
    })
  } else {
    // 1.2 拿到了用户信息，继续导航
    if (whiteList.indexOf(to.path) !== -1) {
      next(`/`) // 跳转到首页
    } else {
      next()
    }
  }
})

export default router
