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
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, _from, savedPosition) {
    // 如果有保存的位置（例如使用瀏覽器的前進/後退按鈕）
    if (savedPosition) {
      return savedPosition
    }
    // 如果有 hash（錨點），滾動到該位置
    if (to.hash) {
      return {
        el: to.hash,
        behavior: 'smooth',
      }
    }
    // 默認滾動到頂部
    return { top: 0 }
  },
})

export default router