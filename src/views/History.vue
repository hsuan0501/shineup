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

    <!-- Loading -->
    <div v-if="loading" class="flex justify-center py-12">
      <svg class="w-8 h-8 animate-spin text-primary-purple" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
      </svg>
    </div>

    <!-- Points History -->
    <div v-else-if="activeTab === 'points'" class="space-y-4">
      <div v-if="activityRecords.length === 0" class="text-center py-12 text-gray-500 dark:text-gray-400">
        目前沒有積分紀錄
      </div>
      <div v-for="record in activityRecords" :key="record.id"
        class="flex items-center justify-between p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
        <div class="flex items-center gap-4">
          <div :class="['w-10 h-10 rounded-full flex items-center justify-center', record.points > 0 ? 'bg-green-100 text-green-600 dark:bg-green-900/30 dark:text-green-400' : 'bg-red-100 text-red-600 dark:bg-red-900/30 dark:text-red-400']">
            <svg v-if="record.points > 0" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
            <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
            </svg>
          </div>
          <div>
            <h3 class="font-bold text-light-text dark:text-dark-text">{{ record.title }}</h3>
            <p class="text-xs text-light-text-secondary dark:text-dark-text-secondary">{{ formatDate(record.createdAt) }}</p>
          </div>
        </div>
        <span :class="['font-bold text-lg', record.points > 0 ? 'text-green-500' : 'text-red-500']">
          {{ record.points > 0 ? '+' : '' }}{{ record.points }}
        </span>
      </div>
    </div>

    <!-- Orders History -->
    <div v-else-if="activeTab === 'orders'" class="space-y-4">
      <div v-if="orders.length === 0" class="text-center py-12 text-gray-500 dark:text-gray-400">
        目前沒有兌換紀錄
      </div>
      <div v-for="order in orders" :key="order.id"
        class="p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
        <div class="flex justify-between items-start mb-2">
          <div class="flex items-center gap-3">
            <img v-if="order.gift?.image" :src="order.gift.image" :alt="order.gift.title" class="w-12 h-12 rounded-lg object-cover" />
            <div>
              <h3 class="font-bold text-light-text dark:text-dark-text">{{ order.gift?.title || '禮品' }}</h3>
              <p class="text-xs text-light-text-secondary dark:text-dark-text-secondary">訂單編號: #{{ order.id }}</p>
            </div>
          </div>
          <span :class="['px-2.5 py-0.5 rounded-full text-xs font-medium', getStatusClass(order.status)]">
            {{ getStatusLabel(order.status) }}
          </span>
        </div>
        <div class="flex justify-between items-end mt-3 pt-3 border-t border-gray-100 dark:border-gray-700">
          <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">
            <div>兌換時間: {{ formatDate(order.createdAt) }}</div>
            <div v-if="order.quantity > 1">數量: {{ order.quantity }}</div>
          </div>
          <div class="font-bold text-primary-purple">-{{ order.totalPoints }} 積分</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useStore } from '@/store'
import { orderAPI, activityAPI } from '@/api'

const store = useStore()
const activeTab = ref('points')
const loading = ref(true)
const orders = ref([])
const activityRecords = ref([])

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 取得訂單狀態文字
const getStatusLabel = (status) => {
  const map = {
    'PENDING': '處理中',
    'COMPLETED': '已完成',
    'SHIPPED': '運送中',
    'CANCELLED': '已取消'
  }
  return map[status] || status
}

// 取得訂單狀態樣式
const getStatusClass = (status) => {
  const map = {
    'PENDING': 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-300',
    'COMPLETED': 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-300',
    'SHIPPED': 'bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-300',
    'CANCELLED': 'bg-gray-100 text-gray-700 dark:bg-gray-900/30 dark:text-gray-300'
  }
  return map[status] || 'bg-gray-100 text-gray-700'
}

// 載入資料
const fetchData = async () => {
  if (!store.isAuthenticated || !store.currentUser?.id) {
    loading.value = false
    return
  }

  loading.value = true
  try {
    const userId = store.currentUser.id

    // 並行載入訂單和活動紀錄
    const [ordersRes, activitiesRes] = await Promise.all([
      orderAPI.getByUserId(userId),
      activityAPI.getByUserId(userId)
    ])

    orders.value = ordersRes.data || []
    activityRecords.value = activitiesRes.data || []
  } catch (error) {
    console.error('Failed to fetch history:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchData()
})

// 當用戶登入狀態改變時重新載入
watch(() => store.isAuthenticated, (newVal) => {
  if (newVal) {
    fetchData()
  }
})
</script>
