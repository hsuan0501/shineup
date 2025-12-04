<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 標題 -->
    <div class="mb-8">
      <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">管理後台</h1>
      <p class="text-sm text-gray-600 dark:text-gray-400">管理會員、任務、禮品與系統設定</p>
    </div>

    <!-- 統計卡片 -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-sky-400 to-sky-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ stats.totalUsers }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">總會員數</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-emerald-400 to-emerald-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ stats.tasksCompleted }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">已完成任務</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-purple-400 to-purple-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ stats.giftsRedeemed }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">已兌換禮品</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-amber-400 to-amber-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ stats.totalPoints }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">累計發放積分</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 標籤頁 -->
    <div class="flex gap-2 mb-6 overflow-x-auto pb-2">
      <button v-for="tab in tabs" :key="tab.id" @click="activeTab = tab.id"
        :class="[
          'px-5 py-2.5 rounded-full text-sm font-medium whitespace-nowrap transition-all',
          activeTab === tab.id
            ? 'bg-gradient-to-r from-sky-400 to-purple-400 text-white shadow-lg'
            : 'bg-white dark:bg-gray-700 text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600 border border-gray-200 dark:border-gray-600'
        ]">
        {{ tab.name }}
      </button>
    </div>

    <!-- 會員管理 -->
    <div v-if="activeTab === 'users'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">會員列表</h3>
        <div class="relative">
          <input type="text" v-model="searchQuery" placeholder="搜尋會員..."
            class="pl-9 pr-4 py-2 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">等級</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">升級積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">兌換積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">註冊日期</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="user in filteredUsers" :key="user.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <div class="flex items-center gap-3">
                  <img :src="user.avatar" class="w-8 h-8 rounded-full object-cover bg-gray-200">
                  <div>
                    <p class="text-sm font-medium text-gray-900 dark:text-white">{{ user.name }}</p>
                    <p class="text-xs text-gray-500 dark:text-gray-400">{{ user.email }}</p>
                  </div>
                </div>
              </td>
              <td class="px-4 py-3">
                <span :class="getLevelBadgeClass(user.level)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ user.level }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ user.levelPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ user.rewardPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ user.createdAt }}</td>
              <td class="px-4 py-3">
                <button class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 任務管理 -->
    <div v-if="activeTab === 'tasks'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">任務列表</h3>
        <button class="px-4 py-2 rounded-lg bg-gradient-to-r from-sky-400 to-purple-400 text-white text-sm font-medium hover:opacity-90 transition-opacity">
          + 新增任務
        </button>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">任務名稱</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">分類</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">等級限制</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="task in tasks" :key="task.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <p class="text-sm font-medium text-gray-900 dark:text-white">{{ task.title }}</p>
              </td>
              <td class="px-4 py-3">
                <span :class="getCategoryBadgeClass(task.category)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ task.category }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">+{{ task.levelPoints }} / +{{ task.rewardPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ task.levelRequired }}</td>
              <td class="px-4 py-3">
                <span class="px-2 py-1 text-xs font-medium rounded-full bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400">
                  啟用
                </span>
              </td>
              <td class="px-4 py-3 flex gap-2">
                <button class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
                <button class="text-red-600 dark:text-red-400 hover:underline text-sm">停用</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 禮品管理 -->
    <div v-if="activeTab === 'gifts'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">禮品列表</h3>
        <button class="px-4 py-2 rounded-lg bg-gradient-to-r from-sky-400 to-purple-400 text-white text-sm font-medium hover:opacity-90 transition-opacity">
          + 新增禮品
        </button>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">系列</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">庫存</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="gift in gifts" :key="gift.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <div class="flex items-center gap-3">
                  <img :src="gift.image" class="w-10 h-10 rounded-lg object-cover bg-gray-200">
                  <p class="text-sm font-medium text-gray-900 dark:text-white">{{ gift.title }}</p>
                </div>
              </td>
              <td class="px-4 py-3">
                <span :class="getSeriesBadgeClass(gift.series)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ gift.series }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ gift.points }}</td>
              <td class="px-4 py-3 text-sm" :class="gift.stock < 10 ? 'text-red-600 dark:text-red-400' : 'text-gray-900 dark:text-white'">
                {{ gift.stock }}
              </td>
              <td class="px-4 py-3">
                <span :class="gift.stock > 0 ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'"
                  class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ gift.stock > 0 ? '有庫存' : '已售完' }}
                </span>
              </td>
              <td class="px-4 py-3 flex gap-2">
                <button class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
                <button class="text-amber-600 dark:text-amber-400 hover:underline text-sm">調整庫存</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 兌換記錄 -->
    <div v-if="activeTab === 'redemptions'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600">
        <h3 class="font-bold text-gray-900 dark:text-white">兌換記錄</h3>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">訂單編號</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">日期</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="order in redemptions" :key="order.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ order.id }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.userName }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.giftName }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.points }}</td>
              <td class="px-4 py-3">
                <span :class="getStatusBadgeClass(order.status)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ getStatusText(order.status) }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ order.date }}</td>
              <td class="px-4 py-3">
                <select v-model="order.status"
                  class="text-sm bg-transparent border border-gray-200 dark:border-gray-600 rounded-lg px-2 py-1 focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                  <option value="pending">待處理</option>
                  <option value="completed">已完成</option>
                  <option value="shipped">已出貨</option>
                  <option value="cancelled">已取消</option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { mockUsers, mockTasks, mockRewards } from '../mock.js'

// 統計數據
const stats = ref({
  totalUsers: 1248,
  tasksCompleted: 15420,
  giftsRedeemed: 892,
  totalPoints: 284500
})

// 標籤頁
const tabs = [
  { id: 'users', name: '會員管理' },
  { id: 'tasks', name: '任務管理' },
  { id: 'gifts', name: '禮品管理' },
  { id: 'redemptions', name: '兌換記錄' }
]
const activeTab = ref('users')

// 搜尋
const searchQuery = ref('')

// 使用者資料
const users = ref([
  { id: 1, name: 'Matcha', email: 'matcha@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Matcha', level: 'CREATOR', levelPoints: 700, rewardPoints: 600, createdAt: '2024-10-15' },
  { id: 2, name: 'Alice', email: 'alice@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Alice', level: 'EXPLORER', levelPoints: 120, rewardPoints: 80, createdAt: '2024-11-01' },
  { id: 3, name: 'Bob', email: 'bob@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Bob', level: 'VISIONARY', levelPoints: 980, rewardPoints: 450, createdAt: '2024-09-20' },
  { id: 4, name: 'Carol', email: 'carol@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Carol', level: 'LUMINARY', levelPoints: 1850, rewardPoints: 1200, createdAt: '2024-08-05' }
])

const filteredUsers = computed(() => {
  if (!searchQuery.value) return users.value
  const query = searchQuery.value.toLowerCase()
  return users.value.filter(user =>
    user.name.toLowerCase().includes(query) ||
    user.email.toLowerCase().includes(query)
  )
})

// 任務資料
const tasks = computed(() => mockTasks.slice(0, 10))

// 禮品資料
const gifts = computed(() => mockRewards.slice(0, 10))

// 兌換記錄
const redemptions = ref([
  { id: 1001, userName: 'Matcha', giftName: '環保便攜吸管組', points: 100, status: 'completed', date: '2024-11-24' },
  { id: 1002, userName: 'Alice', giftName: '雲朵筆電包', points: 350, status: 'shipped', date: '2024-11-23' },
  { id: 1003, userName: 'Bob', giftName: '香氛蠟燭暖燈', points: 800, status: 'pending', date: '2024-11-22' },
  { id: 1004, userName: 'Carol', giftName: '星級饗宴餐券', points: 2500, status: 'completed', date: '2024-11-21' }
])

// 樣式輔助函數
const getLevelBadgeClass = (level) => {
  const classes = {
    EXPLORER: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    CREATOR: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    VISIONARY: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    LUMINARY: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[level] || classes.EXPLORER
}

const getCategoryBadgeClass = (category) => {
  const classes = {
    daily: 'bg-pink-100 dark:bg-pink-900/30 text-pink-700 dark:text-pink-400',
    financial: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    investment: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    esg: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    social: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[category] || 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-400'
}

const getSeriesBadgeClass = (series) => {
  const classes = {
    '永續探索': 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    '質感創造': 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    '美學先鋒': 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    '品味閃耀': 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[series] || 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-400'
}

const getStatusBadgeClass = (status) => {
  const classes = {
    pending: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    completed: 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400',
    shipped: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    cancelled: 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'
  }
  return classes[status] || classes.pending
}

const getStatusText = (status) => {
  const texts = {
    pending: '待處理',
    completed: '已完成',
    shipped: '已出貨',
    cancelled: '已取消'
  }
  return texts[status] || status
}
</script>
