<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header -->
    <div class="flex items-center gap-4 mb-8">
      <router-link to="/profile" class="p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors">
        <svg class="w-6 h-6 text-gray-600 dark:text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
      </router-link>
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text">查詢紀錄</h1>
    </div>

    <!-- Tabs -->
    <div class="flex gap-4 mb-6 border-b border-gray-200 dark:border-gray-700">
      <button @click="activeTab = 'points'"
        :class="['pb-3 px-4 text-sm font-medium transition-colors relative', activeTab === 'points' ? 'text-primary-purple' : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200']">
        積分紀錄
        <div v-if="activeTab === 'points'" class="absolute bottom-0 left-0 w-full h-0.5 bg-primary-purple rounded-t-full"></div>
      </button>
      <button @click="activeTab = 'orders'"
        :class="['pb-3 px-4 text-sm font-medium transition-colors relative', activeTab === 'orders' ? 'text-primary-purple' : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200']">
        兌換紀錄
        <div v-if="activeTab === 'orders'" class="absolute bottom-0 left-0 w-full h-0.5 bg-primary-purple rounded-t-full"></div>
      </button>
    </div>

    <!-- Points History -->
    <div v-if="activeTab === 'points'" class="space-y-4">
      <div v-for="record in pointsHistory" :key="record.id"
        class="flex items-center justify-between p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
        <div class="flex items-center gap-4">
          <div :class="['w-10 h-10 rounded-full flex items-center justify-center', record.type === 'earn' ? 'bg-green-100 text-green-600' : 'bg-red-100 text-red-600']">
            <svg v-if="record.type === 'earn'" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
            <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
            </svg>
          </div>
          <div>
            <h3 class="font-bold text-light-text dark:text-dark-text">{{ record.title }}</h3>
            <p class="text-xs text-light-text-secondary dark:text-dark-text-secondary">{{ record.date }}</p>
          </div>
        </div>
        <span :class="['font-bold text-lg', record.type === 'earn' ? 'text-green-500' : 'text-red-500']">
          {{ record.type === 'earn' ? '+' : '-' }}{{ record.points }}
        </span>
      </div>
    </div>

    <!-- Orders History -->
    <div v-if="activeTab === 'orders'" class="space-y-4">
      <div v-for="order in mockOrders" :key="order.id"
        class="p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
        <div class="flex justify-between items-start mb-2">
          <div>
            <h3 class="font-bold text-light-text dark:text-dark-text">{{ order.rewardTitle }}</h3>
            <p class="text-xs text-light-text-secondary dark:text-dark-text-secondary">訂單編號: {{ order.id }}</p>
          </div>
          <span :class="['px-2.5 py-0.5 rounded-full text-xs font-medium', getStatusClass(order.status)]">
            {{ getStatusLabel(order.status) }}
          </span>
        </div>
        <div class="flex justify-between items-end mt-2">
          <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">
            <div>兌換時間: {{ order.createdAt }}</div>
            <div v-if="order.deliveredAt">送達時間: {{ order.deliveredAt }}</div>
          </div>
          <div class="font-bold text-primary-purple">-{{ order.pointsSpent }} 積分</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { mockOrders } from '../mock'

const activeTab = ref('points')

// 模擬積分紀錄數據
const pointsHistory = [
  { id: 1, title: '完成每日登入', points: 5, type: 'earn', date: '2024-11-23 09:00' },
  { id: 2, title: '兌換 LAMY 鋼筆', points: 300, type: 'spend', date: '2024-11-05 14:30' },
  { id: 3, title: '完成新手任務', points: 80, type: 'earn', date: '2024-10-25 10:15' },
  { id: 4, title: '邀請好友註冊', points: 50, type: 'earn', date: '2024-10-24 16:20' },
]

const getStatusLabel = (status) => {
  const map = {
    'delivered': '已送達',
    'shipped': '運送中',
    'processing': '處理中'
  }
  return map[status] || status
}

const getStatusClass = (status) => {
  const map = {
    'delivered': 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-300',
    'shipped': 'bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-300',
    'processing': 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-300'
  }
  return map[status] || 'bg-gray-100 text-gray-700'
}
</script>
