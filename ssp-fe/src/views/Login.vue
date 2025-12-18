<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="background-decoration">
      <!-- 大型装饰元素 -->
      <div class="decoration-circle circle-1"></div>
      <div class="decoration-circle circle-2"></div>
      <div class="decoration-circle circle-3"></div>
      <div class="decoration-circle circle-4"></div>
      <div class="decoration-circle circle-5"></div>

      <!-- 小型装饰点 -->
      <div class="decoration-dot dot-1"></div>
      <div class="decoration-dot dot-2"></div>
      <div class="decoration-dot dot-3"></div>
      <div class="decoration-dot dot-4"></div>
      <div class="decoration-dot dot-5"></div>
      <div class="decoration-dot dot-6"></div>
      <div class="decoration-dot dot-7"></div>
      <div class="decoration-dot dot-8"></div>

      <!-- 装饰线条 -->
      <div class="decoration-line line-1"></div>
      <div class="decoration-line line-2"></div>
      <div class="decoration-line line-3"></div>

      <!-- 装饰网格 -->
      <div class="decoration-grid"></div>
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
          <p class="brand-description">智能、高效、安全的企业管理平台</p>
        </div>
      </div>

      <!-- 右侧登录表单区域 -->
      <div class="login-form-container">
        <div class="login-box">
          <div class="login-header">
            <h2 class="login-title">欢迎回来</h2>
            <p class="login-subtitle">请登录您的账号</p>
          </div>

          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
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

            <div class="form-actions">
              <el-checkbox v-model="rememberMe" class="remember-checkbox">
                记住我
              </el-checkbox>
              <el-button type="text" class="forgot-btn"> 忘记密码？ </el-button>
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

            <div class="login-footer">
              <span class="copyright"
                >© 2025 SSP Platform. All rights reserved.</span
              >
            </div>
          </el-form>
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
import { Management, User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref()
const loading = ref(false)
const rememberMe = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, message: '用户名长度不能少于3位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
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
  background-image: linear-gradient(
      rgba(255, 255, 255, 0.05) 1px,
      transparent 1px
    ),
    linear-gradient(90deg, rgba(255, 255, 255, 0.05) 1px, transparent 1px);
  background-size: 50px 50px;
  animation: gridMove 20s linear infinite;
}

@keyframes gridMove {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(50px, 50px);
  }
}

/* 大型装饰元素 */
.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
  backdrop-filter: blur(10px);
}

.circle-1 {
  width: 300px;
  height: 300px;
  top: -100px;
  left: -100px;
  animation-delay: 0s;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.15) 0%,
    rgba(255, 255, 255, 0.05) 100%
  );
}

.circle-2 {
  width: 200px;
  height: 200px;
  bottom: -50px;
  right: 100px;
  animation-delay: 2s;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.1) 0%,
    rgba(255, 255, 255, 0.03) 100%
  );
}

.circle-3 {
  width: 150px;
  height: 150px;
  top: 50%;
  right: -50px;
  animation-delay: 4s;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.12) 0%,
    rgba(255, 255, 255, 0.04) 100%
  );
}

.circle-4 {
  width: 100px;
  height: 100px;
  top: 20%;
  right: 20%;
  animation-delay: 1s;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.08) 0%,
    rgba(255, 255, 255, 0.02) 100%
  );
  animation-duration: 8s;
}

.circle-5 {
  width: 80px;
  height: 80px;
  bottom: 30%;
  left: 15%;
  animation-delay: 3s;
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 0.06) 0%,
    rgba(255, 255, 255, 0.01) 100%
  );
  animation-duration: 10s;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg) scale(1);
  }
  50% {
    transform: translateY(-20px) rotate(180deg) scale(1.1);
  }
}

/* 小型装饰点 */
.decoration-dot {
  position: absolute;
  width: 4px;
  height: 4px;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  animation: pulse 3s ease-in-out infinite;
}

.dot-1 {
  top: 15%;
  left: 30%;
  animation-delay: 0s;
}

.dot-2 {
  top: 25%;
  left: 70%;
  animation-delay: 0.5s;
}

.dot-3 {
  top: 40%;
  left: 20%;
  animation-delay: 1s;
}

.dot-4 {
  top: 60%;
  left: 60%;
  animation-delay: 1.5s;
}

.dot-5 {
  top: 75%;
  left: 80%;
  animation-delay: 2s;
}

.dot-6 {
  bottom: 20%;
  left: 35%;
  animation-delay: 2.5s;
}

.dot-7 {
  bottom: 10%;
  left: 65%;
  animation-delay: 0.8s;
}

.dot-8 {
  top: 80%;
  left: 15%;
  animation-delay: 1.2s;
}

@keyframes pulse {
  0%,
  100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.5);
  }
}

/* 装饰线条 */
.decoration-line {
  position: absolute;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(255, 255, 255, 0.2),
    transparent
  );
  animation: lineMove 15s ease-in-out infinite;
}

.line-1 {
  width: 200px;
  height: 2px;
  top: 30%;
  left: -200px;
  animation-delay: 0s;
}

.line-2 {
  width: 150px;
  height: 1px;
  top: 50%;
  right: -150px;
  animation-delay: 5s;
}

.line-3 {
  width: 250px;
  height: 1.5px;
  bottom: 40%;
  left: -250px;
  animation-delay: 10s;
}

@keyframes lineMove {
  0% {
    transform: translateX(-100%);
  }
  100% {
    transform: translateX(calc(100vw + 100%));
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
  animation: fadeInRight 0.8s ease-out;
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

/* 表单样式 */
.login-form {
  width: 100%;
}

.form-item-custom {
  margin-bottom: 24px;
}

.form-input {
  height: 50px;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  transition: all 0.3s ease;
  background: #fafafa;
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

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.remember-checkbox {
  font-size: 14px;
  color: #4b5563;
}

.forgot-btn {
  font-size: 14px;
  color: #4f46e5;
  padding: 0;
}

.forgot-btn:hover {
  color: #4338ca;
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
