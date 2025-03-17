<template>
  <a-form
    :model="formState"
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
      <a-input v-model:value="formState.username" />
    </a-form-item>

    <a-form-item
      label="密码"
      name="password"
      :rules="[{ required: true, message: 'Please input your password!' }]"
    >
      <a-input-password v-model:value="formState.password" />
    </a-form-item>

    <a-form-item name="remember" :wrapper-col="{ offset: 8, span: 16 }">
      <a-checkbox v-model:checked="formState.remember">Remember me</a-checkbox>
    </a-form-item>

    <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
      <a-button type="primary" html-type="submit">登录</a-button>
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { UserControllerService } from '@/api'
import type { UserLoginReqVO } from '@/api'
import { OpenAPI } from '@/api'
import { useRouter } from 'vue-router'

interface FormState {
  username: string
  password: string
  remember: boolean
}

const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
})
const router = useRouter()
const onFinish = (values: FormState) => {
  // 创建UserLoginReqVO对象
  const requestBody: UserLoginReqVO = {
    username: values.username,
    password: values.password,
  }
  // 调用UserControllerService的userLogin方法
  UserControllerService.userLogin({ requestBody }).then((res) => {
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
  })
}

const onFinishFailed = (errorInfo: FormState) => {
  console.log('Failed:', errorInfo)
}
</script>

<style scoped></style>
