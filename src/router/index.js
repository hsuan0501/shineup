import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Profile from '../views/Profile.vue'

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
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/Cart.vue'),
  },
  {
    path: '/history',
    name: 'History',
    component: () => import('../views/History.vue'),
  },
  {
    path: '/roadmap',
    name: 'Roadmap',
    component: () => import('../views/Roadmap.vue'),
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import('../views/SearchResults.vue'),
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

export default router