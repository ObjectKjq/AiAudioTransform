import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import Antd, { ConfigProvider } from 'ant-design-vue'
import App from './App.vue'
import router from './router'
import { OpenAPI } from '@/api'
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import moment from 'moment';
import 'moment/locale/zh-cn';


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

// 设置 moment 的语言为中文
moment.locale('zh-cn');

// 使用 ConfigProvider 全局配置语言为中文
app.use(Antd).use(ConfigProvider, {
  locale: zhCN,
});

app.use(createPinia())
app.use(router)
app.use(Antd)

app.mount('#app')
