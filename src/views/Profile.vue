<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 會員中心標題 -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text mb-2">會員中心</h1>
      <p class="text-gray-600 dark:text-gray-400">管理您的積分、等級與獎勵</p>
    </div>

    <!-- 主要內容區域 -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <!-- 左側：用戶資訊與等級進度 -->
      <div class="lg:col-span-2 space-y-6">
        <!-- 用戶資訊卡片 -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
          <div class="flex items-start gap-6">
            <!-- 頭像 -->
            <div class="relative">
              <img 
                :src="user.avatar" 
                :alt="user.name"
                class="w-20 h-20 rounded-full object-cover"
              >
              <div class="absolute -bottom-2 -right-2 w-8 h-8 rounded-full bg-gradient-to-br flex items-center justify-center shadow-lg"
                   :class="currentLevelConfig.gradientFrom + ' ' + currentLevelConfig.gradientTo">
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                </svg>
              </div>
            </div>

            <!-- 用戶基本資訊 -->
            <div class="flex-1">
              <div class="flex items-center gap-3 mb-2">
                <h2 class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.name }}</h2>
                <span 
                  class="px-3 py-1 text-sm font-bold rounded-full"
                  :class="currentLevelConfig.bg + ' ' + currentLevelConfig.text"
                >
                  {{ currentLevelConfig.name }} Lv.{{ currentLevelConfig.levelNumber }}
                </span>
              </div>
              
              <p class="text-gray-600 dark:text-gray-400 mb-4">{{ user.email }}</p>

              <!-- 雙軌積分顯示 -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <!-- 升級積分 -->
                <div class="bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 rounded-xl p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-sky-600 dark:text-sky-400">升級積分</span>
                    <div class="flex items-center gap-1 text-sky-600 dark:text-sky-400">
                      <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                        <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="currentColor"/>
                      </svg>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-sky-700 dark:text-sky-300">{{ user.levelPoints }}</div>
                  <div class="text-sm text-sky-600 dark:text-sky-400">用於提升等級</div>
                </div>

                <!-- 兌換積分 -->
                <div class="bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 rounded-xl p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-purple-600 dark:text-purple-400">兌換積分</span>
                    <div class="flex items-center gap-1 text-purple-600 dark:text-purple-400">
                      <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z"/>
                        <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clip-rule="evenodd"/>
                      </svg>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-purple-700 dark:text-purple-300">{{ user.rewardPoints }}</div>
                  <div class="text-sm text-purple-600 dark:text-purple-400">用於兌換禮品</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 等級進度 -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-4">等級進度</h3>
          
          <!-- 當前等級資訊 -->
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-xl flex items-center justify-center"
                   :class="`bg-gradient-to-br ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`">
                <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                </svg>
              </div>
              <div>
                <h4 class="font-bold text-gray-900 dark:text-white">{{ currentLevelConfig.name }}</h4>
                <p class="text-sm text-gray-600 dark:text-gray-400">倍率 {{ currentLevelConfig.multiplier }}x</p>
              </div>
            </div>
            
            <div class="text-right">
              <div class="text-lg font-bold text-gray-900 dark:text-white">{{ user.levelPoints }} / {{ nextLevelPoints || '∞' }}</div>
              <div class="text-sm text-gray-600 dark:text-gray-400">升級積分</div>
            </div>
          </div>

          <!-- 進度條 -->
          <div class="mb-4">
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3">
              <div 
                class="h-3 rounded-full transition-all duration-500"
                :class="`bg-gradient-to-r ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`"
                :style="`width: ${levelProgress}%`"
              ></div>
            </div>
            <div class="flex justify-between mt-2 text-sm text-gray-600 dark:text-gray-400">
              <span>{{ currentLevelConfig.minPoints }}</span>
              <span v-if="nextLevelPoints">還需 {{ nextLevelPoints - user.levelPoints }} 分升級</span>
              <span v-else>已達最高等級</span>
              <span>{{ nextLevelPoints || '∞' }}</span>
            </div>
          </div>

          <!-- 等級獎勵預覽 -->
          <div v-if="nextLevelConfig" class="bg-gray-50 dark:bg-gray-700/50 rounded-xl p-4">
            <div class="flex items-center gap-3 mb-2">
              <div class="w-8 h-8 rounded-lg flex items-center justify-center"
                   :class="`bg-gradient-to-br ${nextLevelConfig.gradientFrom} ${nextLevelConfig.gradientTo}`">
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                </svg>
              </div>
              <div>
                <h5 class="font-bold text-gray-900 dark:text-white">下一等級：{{ nextLevelConfig.name }}</h5>
                <p class="text-sm text-gray-600 dark:text-gray-400">任務積分倍率提升至 {{ nextLevelConfig.multiplier }}x</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 活動統計 -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-6">活動統計</h3>
          
          <div class="grid grid-cols-2 md:grid-cols-3 gap-4">
            <!-- 任務完成 -->
            <div class="text-center p-4 bg-blue-50 dark:bg-blue-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-blue-400 to-blue-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-blue-600 dark:text-blue-400">{{ user.stats?.tasksCompleted || 0 }}</div>
              <div class="text-sm text-blue-600/80 dark:text-blue-400/80">已完成任務</div>
            </div>

            <!-- 連續登入 -->
            <div class="text-center p-4 bg-orange-50 dark:bg-orange-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-orange-400 to-orange-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 18.657A8 8 0 016.343 7.343S7 9 9 10c0-2 .5-5 2.986-7C14 5 16.09 5.777 17.656 7.343A7.975 7.975 0 0120 13a7.975 7.975 0 01-2.343 5.657z"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-orange-600 dark:text-orange-400">{{ user.stats?.consecutiveDays || 0 }}</div>
              <div class="text-sm text-orange-600/80 dark:text-orange-400/80">連續登入</div>
            </div>

            <!-- 總登入次數 -->
            <div class="text-center p-4 bg-green-50 dark:bg-green-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-green-400 to-green-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-green-600 dark:text-green-400">{{ user.stats?.totalLogins || 0 }}</div>
              <div class="text-sm text-green-600/80 dark:text-green-400/80">總登入次數</div>
            </div>

            <!-- 已兌換禮品 -->
            <div class="text-center p-4 bg-purple-50 dark:bg-purple-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-purple-400 to-purple-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-purple-600 dark:text-purple-400">{{ user.stats?.rewardsRedeemed || 0 }}</div>
              <div class="text-sm text-purple-600/80 dark:text-purple-400/80">已兌換禮品</div>
            </div>

            <!-- 邀請好友 -->
            <div class="text-center p-4 bg-pink-50 dark:bg-pink-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-pink-400 to-pink-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-pink-600 dark:text-pink-400">{{ user.stats?.friendsInvited || 0 }}</div>
              <div class="text-sm text-pink-600/80 dark:text-pink-400/80">邀請好友</div>
            </div>

            <!-- 會員天數 -->
            <div class="text-center p-4 bg-indigo-50 dark:bg-indigo-900/20 rounded-xl">
              <div class="w-12 h-12 mx-auto mb-3 bg-gradient-to-br from-indigo-400 to-indigo-600 rounded-full flex items-center justify-center">
                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                </svg>
              </div>
              <div class="text-2xl font-bold text-indigo-600 dark:text-indigo-400">{{ memberDays }}</div>
              <div class="text-sm text-indigo-600/80 dark:text-indigo-400/80">會員天數</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右側：歷史紀錄與等級說明 -->
      <div class="lg:col-span-1 space-y-6">
        <!-- 最近紀錄 -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-4">最近紀錄</h3>
          
          <div class="space-y-3">
            <div v-for="record in recentHistory" :key="record.id" 
                 class="flex items-center gap-3 p-3 bg-gray-50 dark:bg-gray-700/50 rounded-xl">
              <div class="w-10 h-10 rounded-lg flex items-center justify-center"
                   :class="getRecordStyle(record.type).bg">
                <component :is="getRecordIcon(record.type)" class="w-5 h-5" :class="getRecordStyle(record.type).text"/>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-sm font-medium text-gray-900 dark:text-white truncate">{{ record.title }}</p>
                <p class="text-xs text-gray-600 dark:text-gray-400">{{ formatDate(record.date) }}</p>
              </div>
              <div class="text-sm font-bold" :class="getRecordStyle(record.type).text">
                {{ record.type === 'task' ? '+' + record.points : '-' + record.points }}
              </div>
            </div>
          </div>

          <button class="w-full mt-4 py-2 text-sm font-medium text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white transition-colors">
            查看完整紀錄 →
          </button>
        </div>

        <!-- 等級說明 -->
        <div class="bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-4">等級說明</h3>
          
          <div class="space-y-3">
            <div v-for="levelInfo in levelConfig" :key="levelInfo.level"
                 class="flex items-center gap-3 p-3 rounded-xl transition-all"
                 :class="levelInfo.level === user.level ? 'bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 ring-2 ring-sky-200 dark:ring-sky-800' : 'bg-gray-50 dark:bg-gray-700/50'">
              <div class="w-8 h-8 rounded-lg flex items-center justify-center"
                   :class="`bg-gradient-to-br ${levelInfo.gradientFrom} ${levelInfo.gradientTo}`">
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                </svg>
              </div>
              <div class="flex-1">
                <div class="flex items-center justify-between">
                  <h4 class="text-sm font-bold text-gray-900 dark:text-white">{{ levelInfo.name }}</h4>
                  <span class="text-xs font-medium px-2 py-1 rounded-full bg-gray-200 dark:bg-gray-600 text-gray-700 dark:text-gray-300">
                    {{ levelInfo.multiplier }}x
                  </span>
                </div>
                <p class="text-xs text-gray-600 dark:text-gray-400">{{ levelInfo.minPoints }}+ 分</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { mockUsers, levelConfig } from '../mock.js'

// 用戶資料
const user = computed(() => mockUsers[1])

// 當前等級配置
const currentLevelConfig = computed(() => {
  return levelConfig.find(level => level.level === user.value.level) || levelConfig[0]
})

// 下一等級配置
const nextLevelConfig = computed(() => {
  const currentIndex = levelConfig.findIndex(level => level.level === user.value.level)
  return currentIndex < levelConfig.length - 1 ? levelConfig[currentIndex + 1] : null
})

// 下一等級所需積分
const nextLevelPoints = computed(() => {
  return nextLevelConfig.value?.minPoints || null
})

// 等級進度百分比
const levelProgress = computed(() => {
  if (!nextLevelPoints.value) return 100
  const current = user.value.levelPoints - currentLevelConfig.value.minPoints
  const total = nextLevelPoints.value - currentLevelConfig.value.minPoints
  return Math.min((current / total) * 100, 100)
})

// 會員天數
const memberDays = computed(() => {
  const joinDate = new Date(user.value.createdAt)
  const now = new Date()
  const diffTime = Math.abs(now - joinDate)
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24))
})

// 模擬最近紀錄
const recentHistory = computed(() => [
  { id: 1, type: 'task', title: '完成每日登入', points: 5, date: '2024-11-24' },
  { id: 2, type: 'reward', title: '兌換環保便攜吸管組', points: 100, date: '2024-11-23' },
  { id: 3, type: 'task', title: '閱讀理財文章', points: 10, date: '2024-11-23' },
  { id: 4, type: 'task', title: '分享到社群', points: 15, date: '2024-11-22' },
  { id: 5, type: 'reward', title: '兌換種子鉛筆組', points: 100, date: '2024-11-21' }
])

// 獲取紀錄樣式
const getRecordStyle = (type) => {
  if (type === 'task') {
    return {
      bg: 'bg-green-100 dark:bg-green-900/30',
      text: 'text-green-600 dark:text-green-400'
    }
  } else {
    return {
      bg: 'bg-purple-100 dark:bg-purple-900/30', 
      text: 'text-purple-600 dark:text-purple-400'
    }
  }
}

// 獲取紀錄圖標
const getRecordIcon = (type) => {
  if (type === 'task') {
    return 'svg' // 任務圖標
  } else {
    return 'svg' // 兌換圖標
  }
}

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = now - date
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24))
  
  if (diffDays === 0) return '今天'
  if (diffDays === 1) return '昨天'
  if (diffDays < 7) return `${diffDays}天前`
  return date.toLocaleDateString('zh-TW', { month: 'short', day: 'numeric' })
}
</script>