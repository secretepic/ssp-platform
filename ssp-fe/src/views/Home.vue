<template>
  <el-container class="home-container">
    <!-- 侧边栏 -->
    <el-aside
      :width="sidebarWidth"
      class="sidebar"
      :class="{ 'sidebar-collapsed': isCollapsed }"
    >
      <div class="sidebar-header">
        <div class="logo">
          <el-icon class="logo-icon">
            <Management />
          </el-icon>
          <span v-if="!isCollapsed" class="logo-text">SSP平台</span>
        </div>
        <el-button
          type="text"
          @click="toggleSidebar"
          class="collapse-btn"
          :title="isCollapsed ? '展开侧边栏' : '收起侧边栏'"
        >
          <el-icon>
            <ArrowRight v-if="isCollapsed" />
            <ArrowLeft v-else />
          </el-icon>
        </el-button>
      </div>

      <el-scrollbar>
        <el-menu
          :default-active="activeMenu"
          class="sidebar-menu"
          background-color="transparent"
          text-color="#94a3b8"
          active-text-color="#ffffff"
          :collapse="isCollapsed"
          :collapse-transition="false"
          router
        >
          <el-menu-item index="/home">
            <template #icon>
              <el-icon><House /></el-icon>
            </template>
            <template #title>首页</template>
          </el-menu-item>

          <el-sub-menu index="system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/system/user">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/system/role">
              <el-icon><UserFilled /></el-icon>
              <span>角色管理</span>
            </el-menu-item>
            <el-menu-item index="/system/menu">
              <el-icon><Menu /></el-icon>
              <span>菜单管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="business">
            <template #title>
              <el-icon><Document /></el-icon>
              <span>业务管理</span>
            </template>
            <el-menu-item index="/business/order">
              <el-icon><Ticket /></el-icon>
              <span>订单管理</span>
            </el-menu-item>
            <el-menu-item index="/business/product">
              <el-icon><Goods /></el-icon>
              <span>产品管理</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <!-- 主内容区 -->
    <el-container>
      <!-- 顶部导航栏 -->
      <el-header class="header">
        <div class="header-left">
          <el-icon class="hamburger" @click="toggleSidebar">
            <Menu />
          </el-icon>
          <!-- 面包屑 -->
          <el-breadcrumb separator="/" class="breadcrumb">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="header-right">
          <!-- 通知图标 -->
          <div class="notification-btn">
            <el-icon class="notification-icon">
              <Bell />
            </el-icon>
            <span class="notification-badge">3</span>
          </div>

          <!-- 用户信息 -->
          <el-dropdown trigger="click">
            <span class="user-info">
              <el-avatar :size="36" class="user-avatar">
                {{
                  userStore.userInfo?.username?.charAt(0).toUpperCase() || 'U'
                }}
              </el-avatar>
              <span class="username" v-if="userStore.userInfo?.username">
                {{ userStore.userInfo.username }}
              </span>
              <el-icon class="arrow-down">
                <ArrowDown />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleProfile">
                  <el-icon><User /></el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="handleSetting">
                  <el-icon><Setting /></el-icon>
                  系统设置
                </el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区域 -->
      <el-main class="main-content">
        <!-- 欢迎卡片 -->
        <el-card class="welcome-card">
          <template #header>
            <div class="card-header">
              <h2>欢迎回来，{{ userStore.userInfo?.username || '管理员' }}</h2>
              <el-button type="primary" size="small">
                <el-icon><Plus /></el-icon>
                新建任务
              </el-button>
            </div>
          </template>
          <div class="welcome-content">
            <el-descriptions :column="3" border>
              <el-descriptions-item label="系统版本"
                >v1.0.0</el-descriptions-item
              >
              <el-descriptions-item label="登录时间">
                {{ formatDate(new Date()) }}
              </el-descriptions-item>
              <el-descriptions-item label="上次登录">
                {{ formatDate(new Date(Date.now() - 86400000)) }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>

        <!-- 统计卡片 -->
        <div class="stats-container">
          <div class="stat-card">
            <div class="stat-header">
              <h3 class="stat-title">总用户数</h3>
              <div
                class="stat-icon"
                style="
                  background: linear-gradient(135deg, #3b82f6 0%, #60a5fa 100%);
                "
              >
                <el-icon><User /></el-icon>
              </div>
            </div>
            <div class="stat-value">1,234</div>
            <div class="stat-change positive">
              <el-icon><ArrowUp /></el-icon>
              <span>12.5% 较上月</span>
            </div>
          </div>

          <div class="stat-card">
            <div class="stat-header">
              <h3 class="stat-title">今日订单</h3>
              <div
                class="stat-icon"
                style="
                  background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
                "
              >
                <el-icon><Ticket /></el-icon>
              </div>
            </div>
            <div class="stat-value">56</div>
            <div class="stat-change positive">
              <el-icon><ArrowUp /></el-icon>
              <span>8.2% 较昨日</span>
            </div>
          </div>

          <div class="stat-card">
            <div class="stat-header">
              <h3 class="stat-title">产品总数</h3>
              <div
                class="stat-icon"
                style="
                  background: linear-gradient(135deg, #f59e0b 0%, #fbbf24 100%);
                "
              >
                <el-icon><Goods /></el-icon>
              </div>
            </div>
            <div class="stat-value">456</div>
            <div class="stat-change negative">
              <el-icon><ArrowDown /></el-icon>
              <span>2.1% 较上月</span>
            </div>
          </div>

          <div class="stat-card">
            <div class="stat-header">
              <h3 class="stat-title">系统状态</h3>
              <div
                class="stat-icon"
                style="
                  background: linear-gradient(135deg, #8b5cf6 0%, #a78bfa 100%);
                "
              >
                <el-icon><CircleCheck /></el-icon>
              </div>
            </div>
            <div class="stat-value">正常</div>
            <div class="stat-change positive">
              <el-icon><Clock /></el-icon>
              <span>运行 720 小时</span>
            </div>
          </div>
        </div>

        <!-- 数据表格 -->
        <el-card class="data-table">
          <template #header>
            <div class="card-header">
              <h3>最近订单</h3>
              <el-input
                placeholder="搜索订单"
                prefix-icon="Search"
                size="small"
                style="width: 200px"
              ></el-input>
            </div>
          </template>
          <el-table :data="orderList" stripe border style="width: 100%">
            <el-table-column
              prop="id"
              label="订单ID"
              width="120"
            ></el-table-column>
            <el-table-column
              prop="product"
              label="产品名称"
              width="200"
            ></el-table-column>
            <el-table-column prop="customer" label="客户名称"></el-table-column>
            <el-table-column
              prop="amount"
              label="金额"
              width="120"
              align="right"
            >
              <template #default="scope"
                >¥{{ scope.row.amount.toFixed(2) }}</template
              >
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag
                  :type="
                    scope.row.status === 'completed' ? 'success' : 'warning'
                  "
                >
                  {{ scope.row.status === 'completed' ? '已完成' : '处理中' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="createTime"
              label="创建时间"
              width="160"
            ></el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template #default="scope">
                <el-button type="primary" size="small" plain> 查看 </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useRoute } from 'vue-router'
import { useUserStore } from '../store/modules/user'
import {
  Management,
  ArrowRight,
  ArrowLeft,
  House,
  Setting,
  Document,
  Menu,
  User,
  UserFilled,
  Ticket,
  Goods,
  SwitchButton,
  ArrowDown,
  Bell,
  Plus,
  ArrowUp,
  CircleCheck,
  Clock,
  Search
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 侧边栏状态
const isCollapsed = ref(false)

// 侧边栏宽度配置
const sidebarWidth = computed(() => (isCollapsed.value ? '64px' : '200px'))

// 当前激活的菜单
const activeMenu = computed(() => route.path)

// 订单数据
const orderList = ref([
  {
    id: 'ORD-2025-0001',
    product: '产品A',
    customer: '客户甲',
    amount: 1280.0,
    status: 'completed',
    createTime: '2025-12-18 09:30:00'
  },
  {
    id: 'ORD-2025-0002',
    product: '产品B',
    customer: '客户乙',
    amount: 2560.0,
    status: 'processing',
    createTime: '2025-12-18 10:15:00'
  },
  {
    id: 'ORD-2025-0003',
    product: '产品C',
    customer: '客户丙',
    amount: 890.0,
    status: 'completed',
    createTime: '2025-12-17 14:20:00'
  },
  {
    id: 'ORD-2025-0004',
    product: '产品A',
    customer: '客户丁',
    amount: 1280.0,
    status: 'processing',
    createTime: '2025-12-17 16:45:00'
  },
  {
    id: 'ORD-2025-0005',
    product: '产品D',
    customer: '客户戊',
    amount: 3180.0,
    status: 'completed',
    createTime: '2025-12-16 11:05:00'
  }
])

// 切换侧边栏
const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value
}

// 格式化日期
const formatDate = (date: Date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

// 个人中心
const handleProfile = () => {
  console.log('个人中心')
}

// 系统设置
const handleSetting = () => {
  console.log('系统设置')
}

// 退出登录
const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.home-container {
  height: 100vh;
  overflow: hidden;
  background-color: #f5f7fa;
}

/* 侧边栏样式 */
.sidebar {
  background: linear-gradient(180deg, #1a1f35 0%, #0f172a 100%);
  color: #e2e8f0;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border-right: 1px solid #334155;
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.1);
}

.sidebar-collapsed {
  width: 72px !important;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 64px;
  background: rgba(255, 255, 255, 0.05);
  border-bottom: 1px solid #334155;
  backdrop-filter: blur(10px);
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  transition: all 0.3s ease;
}

.logo-icon {
  font-size: 26px;
  color: #60a5fa;
  transition: all 0.3s ease;
}

.logo-text {
  font-size: 18px;
  font-weight: 700;
  color: #f1f5f9;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
}

.collapse-btn {
  color: #94a3b8;
  font-size: 16px;
  transition: all 0.3s ease;
  padding: 8px;
  border-radius: 8px;
}

.collapse-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #f1f5f9;
}

.sidebar-menu {
  border-right: none;
  height: calc(100% - 64px);
  background: transparent;
}

.sidebar-menu :deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
  border-radius: 8px;
  margin: 8px 12px;
  background: transparent;
  color: #94a3b8;
  transition: all 0.3s ease;
}

.sidebar-menu :deep(.el-menu-item:hover) {
  background: rgba(96, 165, 250, 0.1);
  color: #60a5fa;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  color: #ffffff;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.3);
}

.sidebar-menu :deep(.el-sub-menu) {
  margin: 8px 12px;
  border-radius: 8px;
  overflow: hidden;
  background: transparent;
}

.sidebar-menu :deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
  background: transparent;
  color: #94a3b8;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.sidebar-menu :deep(.el-sub-menu__title:hover) {
  background: rgba(96, 165, 250, 0.1);
  color: #60a5fa;
}

.sidebar-menu :deep(.el-sub-menu__icon-arrow) {
  color: #94a3b8;
  transition: all 0.3s ease;
}

.sidebar-menu :deep(.el-sub-menu.is-active > .el-sub-menu__title) {
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  color: #ffffff;
}

.sidebar-menu :deep(.el-sub-menu__list) {
  background: rgba(255, 255, 255, 0.03);
  border-radius: 0 0 8px 8px;
  padding: 4px 0;
  margin-top: -4px;
}

.sidebar-menu :deep(.el-sub-menu__list .el-menu-item) {
  margin: 4px 12px;
  height: 44px;
  line-height: 44px;
  font-size: 13px;
}

/* 头部样式 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 64px;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  border-bottom: 1px solid #e2e8f0;
  box-shadow: 0 2px 16px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.hamburger {
  font-size: 22px;
  cursor: pointer;
  color: #64748b;
  padding: 8px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.hamburger:hover {
  background: #f1f5f9;
  color: #475569;
}

/* 面包屑样式 */
.breadcrumb {
  font-size: 14px;
}

/* 头部右侧样式 */
.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

/* 通知图标 */
.notification-btn {
  position: relative;
  color: #64748b;
  font-size: 20px;
  padding: 8px;
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.notification-btn:hover {
  background: #f1f5f9;
  color: #475569;
}

.notification-badge {
  position: absolute;
  top: 4px;
  right: 4px;
  background: #ef4444;
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 16px;
  text-align: center;
}

/* 用户信息 */
.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 12px;
  transition: all 0.3s ease;
  background: transparent;
  border: 1px solid transparent;
}

.user-info:hover {
  background: #f1f5f9;
  border-color: #e2e8f0;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.user-avatar {
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  font-size: 14px;
  font-weight: 600;
  color: white;
  transition: all 0.3s ease;
}

.username {
  font-size: 14px;
  font-weight: 600;
  color: #334155;
  transition: all 0.3s ease;
}

.arrow-down {
  font-size: 12px;
  color: #94a3b8;
  transition: all 0.3s ease;
}

/* 内容区域样式 */
.main-content {
  background-color: #f5f7fa;
  padding: 24px;
  overflow-y: auto;
}

/* 欢迎卡片样式 */
.welcome-card {
  margin-bottom: 24px;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  border: none;
}

.welcome-card:hover {
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
  transform: translateY(-2px);
}

.welcome-card :deep(.el-card__header) {
  background: linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%);
  color: white;
  padding: 20px 24px;
  border-radius: 16px 16px 0 0;
  border-bottom: none;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.card-header h2 {
  font-size: 20px;
  font-weight: 700;
  margin: 0;
  color: white;
}

.welcome-content {
  padding: 24px;
  background: white;
  border-radius: 0 0 16px 16px;
}

/* 统计卡片样式 */
.stats-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  background: white;
  padding: 24px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  border: 1px solid #e2e8f0;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
  border-color: #60a5fa;
}

.stat-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.stat-title {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
  margin: 0;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: white;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 8px;
}

.stat-change {
  font-size: 12px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 4px;
}

.stat-change.positive {
  color: #10b981;
}

.stat-change.negative {
  color: #ef4444;
}

/* 表格样式 */
.data-table {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  border: none;
}

.data-table :deep(.el-card__header) {
  padding: 20px 24px;
  border-bottom: 1px solid #e2e8f0;
  background: #f8fafc;
}

.data-table :deep(.el-card__body) {
  padding: 24px;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .stats-container {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 16px;
  }

  .main-content {
    padding: 16px;
  }

  .header {
    padding: 0 16px;
  }
}

@media (max-width: 768px) {
  .stats-container {
    grid-template-columns: 1fr;
  }

  .username {
    display: none;
  }

  .header-right {
    gap: 8px;
  }
}
</style>
