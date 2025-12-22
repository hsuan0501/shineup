import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Profile from '../views/Profile.vue'
import { useAuthStore } from '../store/useAuthStore'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    meta: { requiresAuth: true }
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/Cart.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/history',
    name: 'History',
    component: () => import('../views/History.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import('../views/SearchResults.vue'),
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../views/Settings.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/Admin.vue'),
    meta: { requiresAdmin: true }
  },
  {
    path: '/checkout-confirm',
    name: 'CheckoutConfirm',
    component: () => import('../views/CheckoutConfirm.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/redemption-complete',
    name: 'RedemptionComplete',
    component: () => import('../views/RedemptionComplete.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/reset-password',
    name: 'ResetPassword',
    component: () => import('../views/ResetPassword.vue'),
  },
  {
    path: '/auth/line/callback',
    name: 'LineCallback',
    component: () => import('../views/LineCallback.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 如果有 hash（錨點），優先滾動到該位置
    if (to.hash) {
      return {
        el: to.hash,
        behavior: 'smooth',
      }
    }
    // 只在頁面導航時使用保存的位置（不是重新整理）
    if (savedPosition && from.name) {
      return savedPosition
    }
    // 默認滾動到頂部（包括重新整理的情況）
    return { top: 0, behavior: 'instant' }
  },
})

// 路由守衛：檢查登入與管理員權限
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  // 檢查是否需要登入
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    // 未登入，導向首頁
    next({ name: 'Home' })
    return
  }

  // 檢查是否需要管理員權限
  if (to.meta.requiresAdmin) {
    if (!authStore.isAuthenticated || !authStore.isAdmin) {
      // 非管理員，導向首頁
      next({ name: 'Home' })
      return
    }
  }
  next()
})

export default router