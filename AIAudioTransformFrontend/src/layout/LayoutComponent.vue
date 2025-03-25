<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>
      <img class="logo" v-if="!collapsed" src="../assets/Logo-2 (1).png" alt="" />
      <img class="logo" v-else src="../assets/yinpin (1).png" alt="" />
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item @click="$router.push('/')" key="/">
          <pie-chart-outlined />
          <span>处理结果</span>
        </a-menu-item>
        <a-menu-item @click="$router.push('/audio')" key="/audio">
          <desktop-outlined />
          <span>我的音频</span>
        </a-menu-item>
        <a-menu-item @click="$router.push('/user')" key="/user">
          <file-outlined />
          <span>用户管理</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0; display: flex; align-items: center">
        <menu-unfold-outlined
          v-if="collapsed"
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined v-else class="trigger" @click="() => (collapsed = !collapsed)" />
        <a-dropdown>
          <a-avatar
            @click.prevent
            shape="square"
            size="large"
            :src="
              user_info?.avatar_url ? urlApi + user_info?.avatar_url : '/src/assets/icon-test.png'
            "
            style="margin-left: auto; margin-right: 16px"
          />
          <template #overlay>
            <a-menu>
              <a-menu-item>
                <a href="javascript:;">{{ user_info?.username }}</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" @click="logout">退出登录</a>
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </a-layout-header>
      <a-layout-content style="margin: 0 16px">
        <a-breadcrumb style="margin: 8px 0"> </a-breadcrumb>
        <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
          <router-view />
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {
  PieChartOutlined,
  DesktopOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  FileOutlined,
} from '@ant-design/icons-vue'
import { ref } from 'vue'
import { userStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { OpenAPI } from '@/api'

const collapsed = ref<boolean>(false)
const router = useRouter()

// 获取路由地址
const route = router.currentRoute.value
const selectedKeys = ref<string[]>([route.path])

const urlApi = OpenAPI.BASE

// 退出登录

const { removeUserInfo, user_info } = userStore()
const logout = () => {
  localStorage.removeItem('Authorization')
  removeUserInfo()
  OpenAPI.HEADERS = undefined
  // vue3路由跳转到登录页面
  router.push('/login')
}
</script>
<style scoped>
.trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

.trigger:hover {
  color: #1890ff;
}

.logo {
  height: 32px;
  /* background: rgba(255, 255, 255, 0.3); */
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}
[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
