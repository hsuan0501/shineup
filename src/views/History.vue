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
        :class="['pb-3 px-4 text-sm font-medium transition-colors relative', activeTab === 'points' ? 'text-gray-800 dark:text-gray-100' : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200']">
        積分紀錄
        <div v-if="activeTab === 'points'" class="absolute bottom-0 left-0 w-full h-0.5 bg-gray-800 dark:bg-gray-100 rounded-t-full"></div>
      </button>
      <button @click="activeTab = 'orders'"
        :class="['pb-3 px-4 text-sm font-medium transition-colors relative', activeTab === 'orders' ? 'text-gray-800 dark:text-gray-100' : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200']">
        兌換紀錄
        <div v-if="activeTab === 'orders'" class="absolute bottom-0 left-0 w-full h-0.5 bg-gray-800 dark:bg-gray-100 rounded-t-full"></div>
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
      <div v-for="record in displayedRecords" :key="record.id"
        class="flex items-center justify-between p-4 rounded-xl bg-light-card dark:bg-dark-card border border-gray-200 dark:border-gray-700">
        <div class="flex items-center gap-4">
          <div :class="[
              'w-10 h-10 rounded-full flex items-center justify-center',
              record.type === 'task' ? 'bg-indigo-100 dark:bg-indigo-900/30' :
              record.type === 'login' ? 'bg-pink-100 dark:bg-pink-900/30' :
              record.type === 'streak' ? 'bg-purple-100 dark:bg-purple-900/30' :
              record.type === 'reward' ? 'bg-green-100 dark:bg-green-900/30' :
              record.type === 'invite' ? 'bg-amber-100 dark:bg-amber-900/30' :
              'bg-gray-100 dark:bg-gray-900/30'
            ]">
            <!-- 任務類 - 藍色 -->
            <svg v-if="record.type === 'task'" class="w-5 h-5 text-indigo-500 dark:text-indigo-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            <!-- 登入類 - 粉紅色 -->
            <svg v-else-if="record.type === 'login'" class="w-5 h-5 text-pink-500 dark:text-pink-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            <!-- 連續登入 - 紫色 -->
            <svg v-else-if="record.type === 'streak'" class="w-5 h-5 text-purple-500 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
            </svg>
            <!-- 兌換禮品 - 綠色 -->
            <svg v-else-if="record.type === 'reward'" class="w-5 h-5 text-green-500 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
            </svg>
            <!-- 邀請好友 - 黃色 -->
            <svg v-else-if="record.type === 'invite'" class="w-5 h-5 text-amber-500 dark:text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
            </svg>
            <!-- 預設 - 灰色 -->
            <svg v-else class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
          </div>
          <div>
            <h3 class="font-bold text-light-text dark:text-dark-text">{{ record.title }}</h3>
            <p class="text-xs text-gray-500 dark:text-gray-400">{{ formatDate(record.createdAt) }}</p>
          </div>
        </div>
        <span :class="['font-bold text-lg', record.points > 0 ? 'text-green-500' : 'text-pink-500']">
          {{ record.points > 0 ? '+' : '' }}{{ record.points }}
        </span>
      </div>

      <!-- 載入更多按鈕 -->
      <button v-if="hasMoreRecords" @click="loadMore"
        class="w-full py-3 text-sm font-medium text-gray-600 dark:text-gray-300 bg-gray-100 dark:bg-gray-800 hover:bg-gray-200 dark:hover:bg-gray-700 rounded-xl transition-colors flex items-center justify-center gap-2">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
        </svg>
        載入更多 ({{ activityRecords.length - displayLimit }} 筆)
      </button>
    </div>

    <!-- Orders History -->
    <div v-else-if="activeTab === 'orders'" class="space-y-4">
      <div v-if="orders.length === 0" class="text-center py-12 text-gray-500 dark:text-gray-400">
        目前沒有兌換紀錄
      </div>
      <div v-for="order in orders" :key="order.id"
        class="p-4 rounded-xl bg-light-card dark:bg-dark-card border border-gray-200 dark:border-gray-700">
        <!-- 訂單編號 + 狀態標籤 -->
        <div class="flex items-center justify-between mb-3">
          <p class="text-xs text-gray-500 dark:text-gray-400">訂單編號: #{{ order.id }}</p>
          <span :class="[
            'px-2 py-0.5 rounded-full text-xs font-medium',
            order.status === 'COMPLETED' ? 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400' :
            order.status === 'CANCELLED' ? 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400' :
            'bg-pink-100 text-pink-700 dark:bg-pink-900/30 dark:text-pink-400'
          ]">{{ getStatusLabel(order.status) }}</span>
        </div>

        <!-- 訂單資訊 -->
        <div>
          <!-- 訂單明細（多個禮品） -->
          <div v-if="order.items && order.items.length > 0" class="space-y-2 mb-3">
            <div v-for="item in order.items" :key="item.id" class="flex items-center gap-3 p-2 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
              <img v-if="item.gift?.image" :src="item.gift.image" :alt="item.gift.title" class="w-10 h-10 rounded-lg object-cover" />
              <div class="flex-1 min-w-0">
                <h3 class="font-medium text-gray-800 dark:text-gray-100 text-sm">{{ item.gift?.title || '禮品' }}</h3>
                <p class="text-xs text-gray-500 dark:text-gray-400">x{{ item.quantity }} · {{ item.subtotalPoints }} 積分</p>
              </div>
            </div>
          </div>

          <!-- 單一禮品（向後兼容） -->
          <div v-else-if="order.gift" class="flex items-center gap-3 mb-3">
            <img v-if="order.gift?.image" :src="order.gift.image" :alt="order.gift.title" class="w-12 h-12 rounded-lg object-cover" />
            <div>
              <h3 class="font-bold text-gray-800 dark:text-gray-100">{{ order.gift?.title || '禮品' }}</h3>
              <p v-if="order.quantity > 1" class="text-xs text-gray-500 dark:text-gray-400">x{{ order.quantity }}</p>
            </div>
          </div>

          <!-- 收件資訊 -->
          <div class="text-sm text-gray-500 dark:text-gray-400 space-y-0.5">
            <div class="flex items-center gap-2">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              <span>{{ order.user?.name || store.currentUser?.name || '-' }}</span>
            </div>
            <div class="flex items-center gap-2">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
              </svg>
              <span>{{ order.user?.phone || store.currentUser?.phone || '未設定' }}</span>
            </div>
            <div class="flex items-start gap-2">
              <svg class="w-4 h-4 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
              <span>{{ formatAddress(order.user?.address || store.currentUser?.address) }}</span>
            </div>
          </div>
        </div>

        <!-- 水平時間軸：左到右 -->
        <div v-if="order.status !== 'CANCELLED'" class="mt-4 pt-4 border-t border-gray-100 dark:border-gray-700">
          <div class="flex items-center justify-between">
            <template v-for="(step, index) in orderSteps" :key="step.status">
              <!-- 步驟 -->
              <div class="flex flex-col items-center">
                <!-- 圓點 + 圖示 -->
                <div :class="[
                  'w-8 h-8 rounded-full border-2 flex items-center justify-center',
                  getStepIndex(order.status) >= index
                    ? 'bg-green-500 border-green-500 text-white'
                    : 'bg-white dark:bg-gray-800 border-gray-300 dark:border-gray-500 text-gray-400'
                ]">
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path v-if="index === 0" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                    <path v-else-if="index === 1" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4" />
                    <path v-else-if="index === 2" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16V6a1 1 0 00-1-1H4a1 1 0 00-1 1v10a1 1 0 001 1h1m8-1a1 1 0 01-1 1H9m4-1V8a1 1 0 011-1h2.586a1 1 0 01.707.293l3.414 3.414a1 1 0 01.293.707V16a1 1 0 01-1 1h-1m-6-1a1 1 0 001 1h1M5 17a2 2 0 104 0m-4 0a2 2 0 114 0m6 0a2 2 0 104 0m-4 0a2 2 0 114 0" />
                    <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
                </div>
                <!-- 文字標籤 -->
                <span :class="[
                  'mt-1 text-xs',
                  getStepIndex(order.status) >= index
                    ? 'text-green-600 dark:text-green-400 font-medium'
                    : 'text-gray-400 dark:text-gray-500'
                ]">{{ step.label }}</span>
                <!-- 時間（已完成的步驟顯示虛擬時間） -->
                <span v-if="getStepIndex(order.status) >= index" class="text-[10px] text-gray-400 dark:text-gray-500">
                  {{ formatShortDate(getStepTime(order.createdAt, index)) }}
                </span>
              </div>
              <!-- 連接線 -->
              <div v-if="index < orderSteps.length - 1" :class="[
                'flex-1 h-0.5 mx-2',
                getStepIndex(order.status) > index ? 'bg-green-500' : 'bg-gray-300 dark:bg-gray-600'
              ]" style="margin-top: -1rem;"></div>
            </template>
          </div>
        </div>
        <!-- 已取消狀態 -->
        <div v-else class="mt-4 pt-4 border-t border-gray-100 dark:border-gray-700 text-center">
          <span class="px-3 py-1 rounded-full text-sm font-medium bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400">
            已取消
          </span>
        </div>

        <!-- 底部：時間和積分 -->
        <div class="flex justify-between items-end mt-3 pt-3 border-t border-gray-100 dark:border-gray-700">
          <div class="text-sm text-gray-500 dark:text-gray-400">
            <div>兌換時間: {{ formatDate(order.createdAt) }}</div>
          </div>
          <div class="font-bold text-gray-500 dark:text-gray-400">-{{ order.totalPoints }} 積分</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from '@/store'
import { orderAPI, activityAPI } from '@/api'

const route = useRoute()
const store = useStore()
const activeTab = ref(route.query.tab === 'orders' ? 'orders' : 'points')
const loading = ref(true)
const orders = ref([])
const activityRecords = ref([])
const displayLimit = ref(10)

// 分頁顯示的積分紀錄
const displayedRecords = computed(() => {
  return activityRecords.value.slice(0, displayLimit.value)
})

// 是否還有更多紀錄
const hasMoreRecords = computed(() => {
  return activityRecords.value.length > displayLimit.value
})

// 載入更多
const loadMore = () => {
  displayLimit.value += 10
}

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

// 格式化地址（臺→台）
const formatAddress = (address) => {
  if (!address) return '未設定'
  return address.replace(/臺/g, '台')
}

// 格式化短日期（用於時間軸）
const formatShortDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-TW', {
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 訂單時間線步驟
const orderSteps = [
  { status: 'PENDING', label: '訂單確認中' },
  { status: 'SHIPPED', label: '已出貨' },
  { status: 'DELIVERED', label: '已送達' },
  { status: 'COMPLETED', label: '取貨完成' }
]

// 取得狀態對應的步驟索引
const getStepIndex = (status) => {
  const index = orderSteps.findIndex(step => step.status === status)
  return index >= 0 ? index : 0
}

// 計算各步驟的虛擬時間（讓時間看起來更真實）
const getStepTime = (createdAt, stepIndex) => {
  if (!createdAt) return null
  const date = new Date(createdAt)
  // 每個步驟的天數和時間設定
  const stepConfig = [
    { days: 0, hour: null },           // 訂單確認：保持原時間
    { days: 1, hour: 9, minute: 30 },  // 出貨：+1天 上午9:30
    { days: 3, hour: 14, minute: 15 }, // 送達：+3天 下午2:15
    { days: 4, hour: 16, minute: 45 }  // 完成：+4天 下午4:45
  ]
  const config = stepConfig[stepIndex] || { days: 0, hour: null }
  date.setDate(date.getDate() + config.days)
  if (config.hour !== null) {
    date.setHours(config.hour, config.minute || 0, 0, 0)
  }
  return date.toISOString()
}

// 取得狀態標籤文字
const getStatusLabel = (status) => {
  const labels = {
    PENDING: '處理中',
    SHIPPED: '處理中',
    DELIVERED: '處理中',
    COMPLETED: '已完成',
    CANCELLED: '已取消'
  }
  return labels[status] || status
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

    // 依照時間排序（最新的在最上面）
    orders.value = (ordersRes.data || []).sort((a, b) =>
      new Date(b.createdAt) - new Date(a.createdAt)
    )
    activityRecords.value = (activitiesRes.data || []).sort((a, b) =>
      new Date(b.createdAt) - new Date(a.createdAt)
    )
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
