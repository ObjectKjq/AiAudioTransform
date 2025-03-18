import { ref } from 'vue'
import { defineStore } from 'pinia'
import { UserControllerService } from '@/api'

// 定义用户信息的类型
interface UserInfo {
  username: string | undefined
  role: number | undefined
  avatar_url: string | undefined
}

export const userStore = defineStore('user', () => {
  const user_info = ref<UserInfo | null>(null)

  const setUserInfo = (user: UserInfo) => {
    user_info.value = user
  }

  const removeUserInfo = () => {
    user_info.value = null
  }

  const getUserInfo = () => {
    if (user_info.value != null) {
      return user_info.value
    }
    UserControllerService.getUserInfo().then((res) => {
      const user = res.data
      if (res.code == 0 && user != undefined) {
        // 创建userInfo
        const userInfo: UserInfo = {
          username: user.username,
          role: user.role,
          avatar_url: user.avatarUrl,
        }
        setUserInfo(userInfo)
        return userInfo
      } else {
        return null
      }
    })
  }

  // 返回
  return { user_info, setUserInfo, getUserInfo, removeUserInfo }
})
