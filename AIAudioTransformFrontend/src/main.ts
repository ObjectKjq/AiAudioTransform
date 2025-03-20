import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import Antd from 'ant-design-vue'
import App from './App.vue'
import router from './router'
import { OpenAPI } from '@/api'

// 设置Authorization，放置用户刷新后删除
const Authorization = localStorage.getItem('Authorization')
let headers: Record<string, string> = {}
if (Authorization != undefined) {
  headers = {
    Authorization: Authorization,
  }
}
OpenAPI.HEADERS = headers

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Antd)

app.mount('#app')
