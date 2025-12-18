import { defineStore } from 'pinia'

export interface UserState {
  token: string | null
  userInfo: any
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: localStorage.getItem('token') || null,
    userInfo: null
  }),

  actions: {
    // 设置token
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },

    // 设置用户信息
    setUserInfo(userInfo: any) {
      this.userInfo = userInfo
    },

    // 登录
    async login(loginForm: any) {
      // 这里需要替换为实际的登录API调用
      const mockToken = 'mock-token-' + Math.random().toString(36).substr(2)
      this.setToken(mockToken)
      this.setUserInfo({ username: loginForm.username })
      return { success: true }
    },

    // 登出
    logout() {
      this.token = null
      this.userInfo = null
      localStorage.removeItem('token')
    }
  }
})
