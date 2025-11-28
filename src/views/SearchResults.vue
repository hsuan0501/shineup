<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 搜尋標題 -->
    <div class="mb-8">
      <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">搜尋結果</h1>
      <p class="text-sm text-gray-600 dark:text-gray-400">
        關鍵字：<span class="font-semibold text-light-text dark:text-dark-text">{{ store.searchQuery }}</span>
      </p>
    </div>

    <div class="max-w-7xl mx-auto">

      <!-- 任務結果 -->
      <section v-if="filteredTasks.length > 0" class="mb-12">
        <h2 class="text-xl font-bold text-light-text dark:text-dark-text mb-4">
          任務 ({{ filteredTasks.length }})
        </h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <div v-for="task in filteredTasks" :key="task.id"
            class="group bg-light-card dark:bg-gray-700/60 dark:backdrop-blur-xl rounded-xl p-4 border border-gray-200 dark:border-gray-600 hover:scale-[1.02] transition-all duration-300 cursor-pointer dark:shadow-xl">
            <!-- Task Header -->
            <div class="flex items-start justify-between mb-3">
              <h3 class="font-semibold text-light-text dark:text-dark-text flex-1">{{ task.title }}</h3>
              <span v-if="task.completed"
                class="ml-2 px-2 py-1 rounded-full bg-green-100 dark:bg-green-900/30 text-green-600 dark:text-green-400 text-xs font-semibold flex-shrink-0">
                已完成
              </span>
            </div>

            <!-- Task Description -->
            <p class="text-sm text-gray-600 dark:text-gray-400 mb-3 line-clamp-2">{{ task.description }}</p>

            <!-- Task Footer -->
            <div class="flex items-center justify-between pt-3 border-t border-gray-200 dark:border-gray-600">
              <div class="flex items-center gap-2">
                <span class="text-sm font-semibold" :class="getPointsColorClass(task.category)">+{{ task.points }} 積分</span>
              </div>
              <button :disabled="task.completed" :class="[
                'px-3 py-1.5 rounded-full text-xs font-semibold transition-all duration-300 pointer-events-auto',
                task.completed
                  ? 'bg-gray-200 dark:bg-gray-700 text-gray-400 dark:text-gray-500 cursor-not-allowed'
                  : 'bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-90 hover:scale-105 active:scale-95'
              ]">
                {{ task.completed ? '已完成' : '立即完成' }}
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- 禮品結果 -->
      <section v-if="filteredGifts.length > 0" class="mb-12">
        <h2 class="text-xl font-bold text-light-text dark:text-dark-text mb-4">
          禮品 ({{ filteredGifts.length }})
        </h2>
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
          <div v-for="gift in filteredGifts" :key="gift.id"
            class="group rounded-lg overflow-hidden bg-light-card dark:bg-gray-600/70 dark:backdrop-blur-xl hover:scale-105 transition-all duration-300 cursor-pointer dark:shadow-2xl border border-gray-200 dark:border-gray-600">

            <!-- Gift Image -->
            <div class="relative aspect-[4/3] overflow-hidden">
              <img :src="gift.image" :alt="gift.title"
                class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300" />

              <!-- Level Badge (Left Top) -->
              <div class="absolute top-2 left-2">
                <div :class="getLevelBadgeClass(gift.level)"
                  class="px-2.5 py-1.5 rounded-full backdrop-blur-md shadow-lg border border-white/20 dark:border-gray-700/50">
                  <div class="text-[11px] font-bold leading-tight tracking-wide">{{ getLevelWithNumber(gift.level) }}</div>
                </div>
              </div>
            </div>

            <!-- Gift Info -->
            <div class="p-3">
              <h3 class="font-semibold text-sm text-light-text dark:text-dark-text mb-1 line-clamp-1">{{ gift.title
                }}</h3>
              <p class="text-xs text-gray-500 dark:text-gray-400 mb-3 line-clamp-1">{{ gift.description }}</p>

              <!-- Points and Exchange Button -->
              <div class="flex items-center justify-between">
                <div class="flex items-baseline gap-1">
                  <span class="text-lg font-bold" :class="getPointsColorByLevel(gift.level)">{{ gift.points }}</span>
                  <span class="text-xs text-gray-500 dark:text-gray-400">積分</span>
                </div>
                <button @click.stop="handleAddToCart(gift)" :class="[
                  'px-3 py-1.5 rounded-full text-xs font-semibold transition-all duration-300 pointer-events-auto',
                  gift.points > user.rewardPoints
                    ? 'bg-gray-300 dark:bg-gray-700 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                    : 'bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-90 hover:scale-105 active:scale-95'
                ]"
                  :disabled="gift.points > user.rewardPoints">
                  {{ gift.points > user.rewardPoints ? '積分不足' : '立即兌換' }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 無結果 -->
      <div v-if="filteredTasks.length === 0 && filteredGifts.length === 0" class="text-center py-20">
        <div class="inline-flex items-center justify-center w-24 h-24 rounded-full bg-gradient-to-br from-amber-100 to-amber-200 dark:from-amber-900/30 dark:to-amber-800/30 mb-6">
          <svg class="w-12 h-12 text-amber-600 dark:text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
        <p class="text-xl font-semibold text-light-text dark:text-dark-text mb-2">
          找不到符合「{{ store.searchQuery }}」的結果
        </p>
        <p class="text-gray-500 dark:text-gray-400 mb-6">
          試試其他關鍵字或瀏覽所有內容
        </p>
        <RouterLink to="/"
          class="inline-block px-6 py-3 rounded-full bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 font-semibold hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors">
          返回首頁
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import { useStore } from '../store/app'
import { mockTasks, mockRewards, mockUsers } from '../mock'

const store = useStore()
const user = ref(mockUsers[1]) // 使用第一個用戶作為範例

// 搜尋過濾任務
const filteredTasks = computed(() => {
  if (!store.searchQuery || store.searchQuery.trim() === '') {
    return []
  }

  const query = store.searchQuery.toLowerCase().trim()
  return mockTasks.filter(task =>
    task.title.toLowerCase().includes(query) ||
    (task.description && task.description.toLowerCase().includes(query))
  )
})

// 搜尋過濾禮品
const filteredGifts = computed(() => {
  if (!store.searchQuery || store.searchQuery.trim() === '') {
    return []
  }

  const query = store.searchQuery.toLowerCase().trim()
  return mockRewards.filter(gift =>
    (gift.title && gift.title.toLowerCase().includes(query)) ||
    (gift.description && gift.description.toLowerCase().includes(query)) ||
    (gift.category && gift.category.toLowerCase().includes(query)) ||
    (gift.level && gift.level.toLowerCase().includes(query))
  )
})

// 任務相關函數
const getPointsColorClass = (category) => {
  const classes = {
    'daily': 'text-pink-500 dark:text-pink-400',
    'financial': 'text-indigo-500 dark:text-indigo-400',
    'investment': 'text-amber-500 dark:text-amber-400',
    'esg': 'text-green-500 dark:text-green-400',
    'social': 'text-purple-500 dark:text-purple-400'
  }
  return classes[category] || 'text-purple-600 dark:text-purple-400'
}

// 禮品相關函數
const getLevelBadgeClass = (level) => {
  const classes = {
    'EXPLORER': 'bg-emerald-500/90 text-white',
    'CREATOR': 'bg-indigo-500/90 text-white',
    'VISIONARY': 'bg-amber-500/90 text-white',
    'LUMINARY': 'bg-purple-500/90 text-white'
  }
  return classes[level] || 'bg-gray-500/90 text-white'
}

const getLevelWithNumber = (level) => {
  const levelNumbers = {
    'EXPLORER': 'Lv1 EXPLORER',
    'CREATOR': 'Lv2 CREATOR',
    'VISIONARY': 'Lv3 VISIONARY',
    'LUMINARY': 'Lv4 LUMINARY'
  }
  return levelNumbers[level] || level
}

const getPointsColorByLevel = (level) => {
  const colors = {
    'EXPLORER': 'text-emerald-600 dark:text-emerald-400',
    'CREATOR': 'text-indigo-600 dark:text-indigo-400',
    'VISIONARY': 'text-amber-600 dark:text-amber-400',
    'LUMINARY': 'text-purple-600 dark:text-purple-400'
  }
  return colors[level] || 'text-purple-600 dark:text-purple-400'
}

const handleAddToCart = (gift) => {
  store.addToCart(gift)
}
</script>
