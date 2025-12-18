<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="background-decoration">
      <!-- 装饰网格 -->
      <div class="decoration-grid"></div>
      
      <!-- 大型装饰元素 -->
      <div class="decoration-circle circle-1"></div>
      <div class="decoration-circle circle-2"></div>
      <div class="decoration-circle circle-3"></div>
      <div class="decoration-circle circle-4"></div>
      <div class="decoration-circle circle-5"></div>
      
      <!-- 装饰光效 -->
      <div class="decoration-light light-1"></div>
      <div class="decoration-light light-2"></div>
      
      <!-- 小型装饰点 -->
      <div class="decoration-dot dot-1"></div>
      <div class="decoration-dot dot-2"></div>
      <div class="decoration-dot dot-3"></div>
      <div class="decoration-dot dot-4"></div>
      <div class="decoration-dot dot-5"></div>
      <div class="decoration-dot dot-6"></div>
      <div class="decoration-dot dot-7"></div>
      <div class="decoration-dot dot-8"></div>
    </div>

    <div class="login-wrapper">
      <!-- 左侧品牌区域 -->
      <div class="login-brand">
        <div class="brand-content">
          <div class="brand-icon">
            <el-icon class="icon-large">
              <Management />
            </el-icon>
          </div>
          <h1 class="brand-title">SSP平台</h1>
          <p class="brand-description">智能、高效、安全的广告管理平台</p>
        </div>
      </div>

      <!-- 右侧表单区域 -->
      <div class="login-form-container">
        <div class="login-box">
          <!-- 登录/注册切换标签 -->
          <div class="form-tabs">
            <div 
              class="tab-item" 
              :class="{ active: isLogin }" 
              @click="isLogin = true"
            >
              登录
            </div>
            <div 
              class="tab-item" 
              :class="{ active: !isLogin }" 
              @click="isLogin = false"
            >
              注册
            </div>
          </div>

          <!-- 登录表单 -->
          <el-form
            v-if="isLogin"
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            class="login-form is-login"
          >
            <el-form-item prop="username" class="form-item-custom">
              <el-input
                v-model="loginForm.username"
                placeholder="用户名"
                size="large"
                auto-complete="off"
                class="form-input"
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <User />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="password" class="form-item-custom">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="密码"
                size="large"
                auto-complete="off"
                class="form-input"
                show-password
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <Lock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="captcha" class="form-item-custom">
              <div class="captcha-container">
                <el-input
                  v-model="loginForm.captcha"
                  placeholder="验证码"
                  size="large"
                  auto-complete="off"
                  class="form-input captcha-input"
                >
                  <template #prefix>
                    <el-icon class="input-icon">
                      <Check />
                    </el-icon>
                  </template>
                </el-input>
                <div class="captcha-image">
                  <el-icon class="captcha-icon">
                    <Picture />
                  </el-icon>
                  <span>获取验证码</span>
                </div>
              </div>
            </el-form-item>

            <div class="form-actions">
              <el-checkbox v-model="rememberMe" class="remember-checkbox">
                记住我
              </el-checkbox>
            </div>

            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                @click="handleLogin"
                size="large"
                class="login-btn"
              >
                登录
              </el-button>
            </el-form-item>
          </el-form>

          <!-- 注册表单 -->
          <el-form
            v-else
            ref="registerFormRef"
            :model="registerForm"
            :rules="registerRules"
            class="login-form"
          >
            <el-form-item prop="username" class="form-item-custom">
              <el-input
                v-model="registerForm.username"
                placeholder="用户名"
                size="large"
                auto-complete="off"
                class="form-input"
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <User />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="password" class="form-item-custom">
              <el-input
                v-model="registerForm.password"
                type="password"
                placeholder="密码"
                size="large"
                auto-complete="off"
                class="form-input"
                show-password
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <Lock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="confirmPassword" class="form-item-custom">
              <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                placeholder="确认密码"
                size="large"
                auto-complete="off"
                class="form-input"
                show-password
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <Lock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="phone" class="form-item-custom">
              <el-input
                v-model="registerForm.phone"
                placeholder="手机号"
                size="large"
                auto-complete="off"
                class="form-input"
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <Phone />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="email" class="form-item-custom">
              <el-input
                v-model="registerForm.email"
                placeholder="邮箱"
                size="large"
                auto-complete="off"
                class="form-input"
              >
                <template #prefix>
                  <el-icon class="input-icon">
                    <Message />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item prop="captcha" class="form-item-custom">
              <div class="captcha-container">
                <el-input
                  v-model="registerForm.captcha"
                  placeholder="验证码"
                  size="large"
                  auto-complete="off"
                  class="form-input captcha-input"
                >
                  <template #prefix>
                    <el-icon class="input-icon">
                      <Check />
                    </el-icon>
                  </template>
                </el-input>
                <div class="captcha-image">
                  <el-icon class="captcha-icon">
                    <Picture />
                  </el-icon>
                  <span>获取验证码</span>
                </div>
              </div>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                :loading="loading"
                @click="handleRegister"
                size="large"
                class="login-btn"
              >
                注册
              </el-button>
            </el-form-item>
          </el-form>

          <div class="login-footer">
            <span class="copyright">© 2025 SSP Platform. All rights reserved.</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../store/modules/user'
import { ElMessage } from 'element-plus'
import {
  Management,
  User,
  Lock,
  Check,
  Picture,
  Phone,
  Message
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

// 表单引用
const loginFormRef = ref()
const registerFormRef = ref()
const loading = ref(false)
const rememberMe = ref(false)

// 登录/注册切换
const isLogin = ref(true)

// 登录表单
const loginForm = reactive({
  username: '',
  password: '',
  captcha: ''
})

// 注册表单
const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  phone: '',
  email: '',
  captcha: ''
})

// 登录表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, message: '用户名长度不能少于3位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 4, max: 4, message: '验证码长度为4位', trigger: 'blur' }
  ]
}

// 注册表单验证规则
const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, message: '用户名长度不能少于3位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule: any, value: any, callback: any) => {
        if (value !== registerForm.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 4, max: 4, message: '验证码长度为4位', trigger: 'blur' }
  ]
}

// 登录处理
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate((valid: boolean) => {
    if (valid) {
      loading.value = true
      userStore
        .login(loginForm)
        .then(() => {
          router.push('/home')
          ElMessage.success('登录成功')
        })
        .catch((error: any) => {
          ElMessage.error(error.message || '登录失败')
        })
        .finally(() => {
          loading.value = false
        })
    }
  })
}

// 注册处理
const handleRegister = async () => {
  if (!registerFormRef.value) return
  
  await registerFormRef.value.validate((valid: boolean) => {
    if (valid) {
      loading.value = true
      // 这里需要替换为实际的注册API调用
      setTimeout(() => {
        loading.value = false
        ElMessage.success('注册成功，请登录')
        isLogin.value = true
      }, 1000)
    }
  })
}
</script>

<style>
/* 全局样式重置，确保背景铺满 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #4f46e5 0%, #8b5cf6 35%, #ec4899 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
}

/* 背景装饰 */
.background-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 0;
}

/* 装饰网格 */
.decoration-grid {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    linear-gradient(rgba(255, 255, 255, 0.03) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255, 255, 255, 0.03) 1px, transparent 1px);
  background-size: 60px 60px;
  animation: gridMove 25s linear infinite;
  opacity: 0.7;
}

@keyframes gridMove {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(60px, 60px);
  }
}

/* 大型装饰元素 - 柔和的光晕效果 */
.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.12) 0%, rgba(255, 255, 255, 0.02) 100%);
  animation: float 8s ease-in-out infinite;
  backdrop-filter: blur(15px);
  box-shadow: 0 0 40px rgba(255, 255, 255, 0.1);
}

.circle-1 {
  width: 400px;
  height: 400px;
  top: -150px;
  left: -150px;
  animation-delay: 0s;
}

.circle-2 {
  width: 300px;
  height: 300px;
  bottom: -100px;
  right: -100px;
  animation-delay: 3s;
}

.circle-3 {
  width: 200px;
  height: 200px;
  top: 50%;
  right: 10%;
  animation-delay: 1.5s;
}

.circle-4 {
  width: 150px;
  height: 150px;
  bottom: 20%;
  left: 15%;
  animation-delay: 4.5s;
}

.circle-5 {
  width: 100px;
  height: 100px;
  top: 20%;
  right: 30%;
  animation-delay: 2.5s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px) scale(1);
    opacity: 0.6;
  }
  50% {
    transform: translateY(-25px) scale(1.1);
    opacity: 0.9;
  }
}

/* 小型装饰点 - 柔和的脉冲效果 */
.decoration-dot {
  position: absolute;
  width: 3px;
  height: 3px;
  background: rgba(255, 255, 255, 0.4);
  border-radius: 50%;
  animation: pulse 4s ease-in-out infinite;
  box-shadow: 0 0 8px rgba(255, 255, 255, 0.5);
}

/* 随机分布的装饰点 */
.dot-1 { top: 10%; left: 25%; animation-delay: 0s; }
.dot-2 { top: 20%; left: 75%; animation-delay: 0.8s; }
.dot-3 { top: 35%; left: 15%; animation-delay: 1.5s; }
.dot-4 { top: 50%; left: 65%; animation-delay: 2.2s; }
.dot-5 { top: 65%; left: 85%; animation-delay: 3s; }
.dot-6 { bottom: 15%; left: 30%; animation-delay: 1s; }
.dot-7 { bottom: 25%; left: 70%; animation-delay: 2s; }
.dot-8 { top: 75%; left: 20%; animation-delay: 3.5s; }

@keyframes pulse {
  0%, 100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(2);
    box-shadow: 0 0 12px rgba(255, 255, 255, 0.8);
  }
}

/* 装饰光效 - 柔和的光线效果 */
.decoration-light {
  position: absolute;
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.15) 0%, transparent 70%);
  border-radius: 50%;
  animation: lightMove 20s ease-in-out infinite;
  filter: blur(20px);
}

.light-1 {
  top: 15%;
  left: 45%;
  animation-delay: 0s;
}

.light-2 {
  bottom: 20%;
  right: 35%;
  animation-delay: 8s;
}

@keyframes lightMove {
  0%, 100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.5;
  }
  50% {
    transform: translate(30px, -30px) scale(1.2);
    opacity: 0.8;
  }
}

/* 登录包装器 */
.login-wrapper {
  display: flex;
  width: 900px;
  height: 550px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
  animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 品牌区域 */
.login-brand {
  flex: 1;
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  animation: fadeInLeft 0.8s ease-out;
}

@keyframes fadeInLeft {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.brand-content {
  text-align: center;
}

.brand-icon {
  margin-bottom: 20px;
}

.icon-large {
  font-size: 80px;
  color: white;
  opacity: 0.9;
}

.brand-title {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 10px;
  letter-spacing: -0.5px;
}

.brand-description {
  font-size: 16px;
  opacity: 0.9;
  line-height: 1.5;
  max-width: 300px;
  margin: 0 auto;
}

/* 表单容器 */
.login-form-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  overflow: hidden;
  animation: fadeInRight 0.8s ease-out;
  position: relative;
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.login-box {
  width: 100%;
  max-width: 360px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.login-form {
  width: 100%;
}

/* 登录表单 - 不滚动 */
.login-form-container .login-form {
  max-height: none;
  overflow-y: visible;
  padding-right: 0;
}

/* 注册表单 - 超出内容滚动 */
.login-form-container .login-form:not(.is-login) {
  max-height: 380px;
  overflow-y: auto;
  padding-right: 10px;
}

/* 滚动条样式 */
.login-form-container .login-form::-webkit-scrollbar {
  width: 6px;
}

.login-form-container .login-form::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.login-form-container .login-form::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.login-form-container .login-form::-webkit-scrollbar-thumb:hover {
  background: #a1a1a1;
}

.login-header {
  margin-bottom: 40px;
  text-align: center;
}

.login-title {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 8px;
}

.login-subtitle {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
}

/* 表单标签切换 */
.form-tabs {
  display: flex;
  margin-bottom: 32px;
  background: #f8fafc;
  border-radius: 12px;
  padding: 4px;
}

.tab-item {
  flex: 1;
  padding: 12px;
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  color: #64748b;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-item:hover {
  color: #4f46e5;
  background: rgba(79, 70, 229, 0.05);
}

.tab-item.active {
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  color: white;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
}

/* 表单样式 */
.login-form {
  width: 100%;
}

.form-item-custom {
  margin-bottom: 20px;
}

.form-input {
  height: 50px;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  transition: all 0.3s ease;
  background: #fafafa;
  font-size: 15px;
}

.form-input:focus {
  border-color: #4f46e5;
  background: white;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.input-icon {
  color: #9ca3af;
  font-size: 18px;
}

/* 验证码容器 */
.captcha-container {
  display: flex;
  gap: 12px;
  align-items: center;
  width: 100%;
}

.captcha-input {
  flex: 1;
  width: 0;
}

.captcha-image {
  width: 130px;
  height: 50px;
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  gap: 6px;
  user-select: none;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
}

.captcha-image:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(79, 70, 229, 0.4);
}

.captcha-icon {
  font-size: 18px;
}

.form-actions {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 24px;
}

.remember-checkbox {
  font-size: 14px;
  color: #4b5563;
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  height: 50px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  border: none;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
  margin-top: 12px;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(79, 70, 229, 0.4);
  background: linear-gradient(135deg, #4338ca 0%, #6d28d9 100%);
}

.login-btn:active {
  transform: translateY(0);
}

.login-btn:disabled {
  transform: none;
  box-shadow: none;
}

/* 登录页脚 */
.login-footer {
  margin-top: 30px;
  text-align: center;
}

.copyright {
  font-size: 12px;
  color: #9ca3af;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .login-wrapper {
    width: 95vw;
    height: auto;
    flex-direction: column;
  }

  .login-brand {
    padding: 30px;
  }

  .login-form-container {
    padding: 30px;
  }

  .brand-title {
    font-size: 28px;
  }

  .icon-large {
    font-size: 60px;
  }
}

@media (max-width: 768px) {
  .login-wrapper {
    margin: 20px;
    height: auto;
  }

  .login-brand {
    padding: 20px;
  }

  .login-form-container {
    padding: 20px;
  }

  .brand-title {
    font-size: 24px;
  }

  .icon-large {
    font-size: 50px;
  }

  .login-title {
    font-size: 24px;
  }
}
</style>
