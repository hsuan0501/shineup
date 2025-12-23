<template>
  <div ref="levelCardContainer" class="relative">
    <!-- 管理員專用簡化標籤 -->
    <div v-if="isAdmin"
      class="flex items-center justify-center bg-gradient-to-br from-amber-400 to-orange-500 border border-amber-300 dark:border-amber-600 lg:gap-2 lg:px-3 lg:py-1.5 lg:rounded-full w-8 h-8 lg:w-auto lg:h-auto rounded-full">
      <svg class="w-4 h-4 lg:w-[18px] lg:h-[18px] text-white" fill="currentColor" viewBox="0 0 24 24">
        <path d="M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm0 10.99h7c-.53 4.12-3.28 7.79-7 8.94V12H5V6.3l7-3.11v8.8z"/>
      </svg>
      <span class="hidden lg:inline text-sm font-bold text-white">管理員</span>
    </div>

    <!-- 一般會員 Level Card Trigger -->
    <!-- 手機/平板為小圓形星星icon，桌面(lg+)顯示完整等級資訊 -->
    <div v-else @click="toggleLevelCard"
      :class="[
        'flex items-center justify-center cursor-pointer hover:scale-[1.02] transition-all active:scale-95 bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 hover:opacity-100 opacity-90',
        'lg:gap-2 lg:px-3 lg:py-1.5 lg:rounded-full',
        'w-8 h-8 lg:w-auto lg:h-auto rounded-full'
      ]">
      <svg class="w-4 h-4 lg:w-[18px] lg:h-[18px]" fill="none" viewBox="0 0 24 24">
        <defs>
          <linearGradient :id="`level-star-${currentLevel.levelNumber}`" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" :stop-color="currentLevel.gradientFromHex || '#c4b5fd'" />
            <stop offset="100%" :stop-color="currentLevel.gradientToHex || '#7c3aed'" />
          </linearGradient>
        </defs>
        <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z"
          :fill="`url(#level-star-${currentLevel.levelNumber})`" />
      </svg>
      <!-- Show Lv and level name on desktop (lg and up) -->
      <span class="hidden lg:inline text-sm font-bold" :style="`color: ${currentLevel.color}`">Lv{{ currentLevel.levelNumber }} {{ currentLevel.level }}</span>
      <svg class="hidden lg:block w-3 h-3 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
      </svg>
    </div>

    <!-- Dropdown Menu -->
    <transition enter-active-class="transition duration-200 ease-out" enter-from-class="transform scale-95 opacity-0"
      enter-to-class="transform scale-100 opacity-100" leave-active-class="transition duration-75 ease-in"
      leave-from-class="transform scale-100 opacity-100" leave-to-class="transform scale-95 opacity-0">
      <div v-if="showLevelCard"
        class="fixed md:absolute top-[60px] md:top-full right-4 md:right-0 mt-0 md:mt-2 bg-white dark:bg-gray-900 rounded-2xl border border-gray-200 dark:border-gray-700 overflow-hidden z-50 w-[min(calc(100vw-2rem),320px)] md:w-80">
        <!-- Header -->
        <div :class="[
          'p-3 md:p-4 border-b border-gray-100 dark:border-gray-800',
          `bg-gradient-to-br ${currentLevel.gradientFrom}/5 ${currentLevel.gradientTo}/5`
        ]">
          <!-- NEW LAYOUT: Icon and Text on left, Lv Number on right (top aligned) -->
          <div class="flex justify-between items-start mb-3 md:mb-4">
            <div class="flex items-center gap-2 md:gap-4">
              <div :class="[
                'w-12 h-12 md:w-16 md:h-16 rounded-xl md:rounded-2xl flex items-center justify-center transition-transform duration-300 hover:scale-[1.02]',
                `bg-gradient-to-br ${currentLevel.gradientFrom} ${currentLevel.gradientTo}`
              ]">
                <svg class="w-6 h-6 md:w-8 md:h-8" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white" />
                </svg>
              </div>
              <div>
                <h3 class="text-base md:text-lg font-bold text-gray-900 dark:text-white">Lv{{ currentLevel.levelNumber }} {{ currentLevel.level }}</h3>
                <p class="text-xs md:text-sm text-gray-500 dark:text-gray-400">{{ currentLevel.multiplier }}x 積分倍率</p>
              </div>
            </div>
          </div>

          <!-- 雙軌積分顯示 -->
          <div class="grid grid-cols-2 gap-2 md:gap-3">
            <!-- 升級積分 -->
            <div
              class="bg-sky-50 dark:bg-sky-900/20 rounded-lg p-2 md:p-3 transition-transform duration-300 hover:scale-[1.02]">
              <div class="flex items-center gap-1 md:gap-2 mb-1">
                <svg class="w-3 h-3 md:w-4 md:h-4 text-sky-500" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="currentColor" />
                </svg>
                <span class="text-[10px] md:text-xs font-medium text-sky-600 dark:text-sky-400">升級積分</span>
              </div>
              <div class="text-base md:text-lg font-bold text-sky-700 dark:text-sky-300">{{
                formatPoints(userLevelPoints) }}</div>
              <div class="text-[10px] md:text-xs text-sky-600 dark:text-sky-400">用於提升等級</div>
            </div>

            <!-- 兌換積分 -->
            <div
              class="bg-purple-50 dark:bg-purple-900/20 rounded-lg p-2 md:p-3 transition-transform duration-300 hover:scale-[1.02]">
              <div class="flex items-center gap-1 md:gap-2 mb-1">
                <svg class="w-3 h-3 md:w-4 md:h-4 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                  <path
                    d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                  <path fill-rule="evenodd"
                    d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z"
                    clip-rule="evenodd" />
                </svg>
                <span class="text-[10px] md:text-xs font-medium text-purple-600 dark:text-purple-400">兌換積分</span>
              </div>
              <div class="text-base md:text-lg font-bold text-purple-700 dark:text-purple-300">{{
                formatPoints(userRewardPoints) }}</div>
              <div class="text-[10px] md:text-xs text-purple-600 dark:text-purple-400">用於兌換禮品</div>
            </div>
          </div>
        </div>

        <!-- Progress -->
        <div v-if="nextLevel" class="p-3 md:p-4 border-b border-gray-100 dark:border-gray-800">
          <div class="flex justify-between text-[10px] md:text-xs mb-1.5">
            <span class="text-gray-600 dark:text-gray-400">升級進度</span>
            <span class="font-medium" :style="`color: ${currentLevel.color}`">{{
              formatPoints(userLevelPoints) }} / {{ formatPoints(nextLevel.minPoints) }}</span>
          </div>
          <div class="h-2 bg-gray-100 dark:bg-gray-800 rounded-full overflow-hidden transition-transform duration-300 hover:scale-[1.02]">
            <div :class="`h-full bg-gradient-to-r ${currentLevel.gradientFrom} ${currentLevel.gradientTo} rounded-full`"
              :style="`width: ${progressPercentage}%`"></div>
          </div>
          <p class="mt-2 text-[10px] md:text-xs text-gray-500 text-center">
            再獲得 <span :style="`color: ${currentLevel.color}`" class="font-medium">{{ formatPoints(nextLevel.minPoints -
              userLevelPoints) }}</span> 升級積分即可升級
          </p>
        </div>
        <div v-else class="p-3 md:p-4 border-b border-gray-100 dark:border-gray-800">
          <div class="text-center">
            <span :class="`text-xs md:text-sm font-medium`" :style="`color: ${currentLevel.color}`">已達最高等級！</span>
            <p class="text-[10px] md:text-xs text-gray-500 mt-1">享受所有專屬權益</p>
          </div>
        </div>

        <!-- Benefits Preview -->
        <div class="p-3 md:p-4 bg-gray-50/50 dark:bg-gray-800/30">
          <h4 class="text-[10px] md:text-xs font-semibold text-gray-900 dark:text-white mb-2 md:mb-3">當前等級權益</h4>
          <ul class="space-y-1.5 md:space-y-2">
            <li class="flex items-center gap-2 text-xs md:text-sm text-gray-600 dark:text-gray-400">
              <div
                :class="`w-2 h-2 rounded-full ${currentLevel.gradientFrom.includes('emerald') ? 'bg-emerald-400' : currentLevel.gradientFrom.includes('cyan') ? 'bg-cyan-400' : currentLevel.gradientFrom.includes('amber') ? 'bg-amber-400' : 'bg-purple-400'}`">
              </div>
              <span>任務積分 {{ currentLevel.multiplier }} 倍加成</span>
            </li>
            <li class="flex items-center gap-2 text-xs md:text-sm text-gray-600 dark:text-gray-400">
              <div
                :class="`w-2 h-2 rounded-full ${currentLevel.gradientFrom.includes('emerald') ? 'bg-emerald-400' : currentLevel.gradientFrom.includes('cyan') ? 'bg-cyan-400' : currentLevel.gradientFrom.includes('amber') ? 'bg-amber-400' : 'bg-purple-400'}`">
              </div>
              <span>可兌換<span v-if="currentLevel.level === 'EXPLORER'">「永續探索」</span><span
                  v-else-if="currentLevel.level === 'CREATOR'">「質感創造」</span><span
                  v-else-if="currentLevel.level === 'VISIONARY'">「美學先鋒」</span><span
                  v-else-if="currentLevel.level === 'LUMINARY'">「品味閃耀」</span>系列禮品</span>
            </li>
          </ul>
          <RouterLink to="/profile" @click="showLevelCard = false"
            :class="`mt-3 md:mt-4 block w-full py-1.5 md:py-2 text-center text-xs md:text-sm font-medium hover:bg-opacity-10 rounded-lg transition-all duration-300 hover:scale-[1.02]`"
            :style="`color: ${currentLevel.color}; background-color: ${currentLevel.color}15`">
            查看完整等級權益 →
          </RouterLink>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { RouterLink } from 'vue-router'
import { levelConfig } from '../../mock.js'
import { useStore } from '@/store'
import { formatPoints } from '../../utils/formatPoints.js'

const showLevelCard = ref(false)
const levelCardContainer = ref(null)
const store = useStore()

// 檢查是否登入
const isLoggedIn = computed(() => store.isAuthenticated)

// 檢查是否為管理員
const isAdmin = computed(() => store.currentUser?.admin === true)

// 雙軌積分系統 - 使用 store.userPoints 即時數據
const userLevelPoints = computed(() => {
  if (!isLoggedIn.value) return 0
  return store.userPoints.upgradePoints || 0
})

const userRewardPoints = computed(() => {
  if (!isLoggedIn.value) return 0
  return store.userPoints.rewardPoints || 0
})

// 未登入時的預設等級配置
const defaultUnstartedLevel = {
  level: 'UNSTARTED',
  name: '未啟程',
  levelNumber: 0,
  minPoints: 0,
  maxPoints: 0,
  multiplier: 1.0,
  color: '#9ca3af', // gray-400
  gradientFrom: 'from-gray-300',
  gradientTo: 'to-gray-500'
}

// 計算當前等級
const currentLevel = computed(() => {
  if (!isLoggedIn.value) return defaultUnstartedLevel

  return levelConfig.find(level =>
    userLevelPoints.value >= level.minPoints && userLevelPoints.value <= level.maxPoints
  ) || levelConfig[0]
})

// 計算下一等級
const nextLevel = computed(() => {
  const currentIndex = levelConfig.findIndex(level => level.level === currentLevel.value.level)
  return currentIndex < levelConfig.length - 1 ? levelConfig[currentIndex + 1] : null
})

// 計算進度百分比
const progressPercentage = computed(() => {
  if (!nextLevel.value) return 100
  const current = userLevelPoints.value - currentLevel.value.minPoints
  const total = nextLevel.value.minPoints - currentLevel.value.minPoints
  return Math.min((current / total) * 100, 100)
})

// 切換等級卡片
const toggleLevelCard = () => {
  showLevelCard.value = !showLevelCard.value
}

// 點擊外部關閉等級卡片
const handleClickOutside = (event) => {
  if (levelCardContainer.value && !levelCardContainer.value.contains(event.target)) {
    showLevelCard.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>
