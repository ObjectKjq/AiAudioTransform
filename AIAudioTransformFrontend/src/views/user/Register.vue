<template>
  <a-row style="height: 100vh" justify="space-around" align="middle">
    <a-form
      style="width: 400px"
      :model="userRegisterReqVO"
      name="basic"
      :label-col="{ span: 8 }"
      :wrapper-col="{ span: 16 }"
      autocomplete="off"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="用户名"
        name="username"
        :rules="[{ required: true, message: 'Please input your username!' }]"
      >
        <a-input v-model:value="userRegisterReqVO.username" />
      </a-form-item>

      <a-form-item
        label="密码"
        name="password"
        :rules="[{ required: true, message: 'Please input your password!' }]"
      >
        <a-input-password v-model:value="userRegisterReqVO.password" />
      </a-form-item>

      <a-form-item
        label="确认密码"
        name="checkPassword"
        :rules="[{ required: true, message: 'Please input your password!' }]"
      >
        <a-input-password v-model:value="userRegisterReqVO.checkPassword" />
      </a-form-item>

      <a-form-item name="remember" :wrapper-col="{ offset: 8, span: 16 }">
        <router-link to="/login">去登录</router-link>
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
        <a-button type="primary" html-type="submit">注册</a-button>
      </a-form-item>
    </a-form>
  </a-row>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { UserControllerService } from '@/api'
import type { UserRegisterReqVO } from '@/api'
import { OpenAPI } from '@/api'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'

const userRegisterReqVO = reactive<UserRegisterReqVO>({
  username: '',
  password: '',
  checkPassword: '',
})
const router = useRouter()
const onFinish = (userRegisterReqVO: UserRegisterReqVO) => {
  // 调用UserControllerService的userLogin方法
  UserControllerService.userRegister(userRegisterReqVO).then(
    (res) => {
      const Authorization = res.data
      if (res.code == 0 && Authorization != undefined) {
        localStorage.setItem('Authorization', Authorization)
        // 更新token配置信息
        let headers: Record<string, string> = {}
        if (Authorization != undefined) {
          headers = {
            Authorization: Authorization,
          }
        }
        OpenAPI.HEADERS = headers
        // 跳转到首页
        router.push('/')
      }
    },
    (err) => {
      message.error(err.message)
    },
  )
}

const onFinishFailed = (errorInfo: UserRegisterReqVO) => {
  console.log('Failed:', errorInfo)
}
</script>

<style scoped></style>
