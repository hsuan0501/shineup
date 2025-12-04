<template>
  <!-- Backdrop with blur -->
  <div v-if="isOpen" @click="closeModal"
    class="fixed inset-0 z-50 bg-black/30 dark:bg-black/50 backdrop-blur-xl backdrop-saturate-150 flex items-center justify-center p-4">

    <!-- Modal Content - 左右排列 -->
    <div @click.stop
      class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl max-w-3xl w-full p-6 sm:p-8">

      <!-- Close Button -->
      <button @click="closeModal"
        class="absolute top-4 right-4 w-8 h-8 flex items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">
        <svg class="w-5 h-5 text-gray-600 dark:text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>

      <!-- 左右排列內容 -->
      <div class="flex flex-col sm:flex-row gap-6">
        <!-- 左側：圖片區 -->
        <div class="flex-shrink-0 relative">
          <div class="relative w-48 h-48 rounded-2xl overflow-hidden bg-gradient-to-br from-blue-100 to-purple-100 dark:from-blue-900/20 dark:to-purple-900/20 flex items-center justify-center">
            <!-- 任務圖片區域 -->
            <img v-if="task.image" :src="task.image" :alt="task.title" class="w-full h-full object-cover">
            <!-- 備用圖示區域（當沒有圖片時） -->
            <div v-else :class="getTaskIconBgClass(task.category)" class="w-32 h-32 rounded-2xl flex items-center justify-center">
              <span class="text-5xl">{{ task.icon || '📋' }}</span>
            </div>
          </div>
        </div>

        <!-- 右側：說明區 -->
        <div class="flex-1 flex flex-col">
          <!-- 標題與標籤 -->
          <div class="mb-3">
            <h2 class="text-xl sm:text-2xl font-bold text-light-text dark:text-dark-text mb-2 pr-8">
              {{ task.title }}
            </h2>
            <div class="flex gap-2">
              <!-- 分類標籤 (深色) -->
              <span :class="getCategoryBadgeClass(task.category)" class="px-3 py-1 rounded-full text-xs font-semibold">
                {{ getCategoryLabelForTask(task.category) }}
              </span>
              <!-- 頻率標籤 (淺色) -->
              <span :class="getFrequencyBadgeClassNew(task.category)" class="px-3 py-1 rounded-full text-xs font-semibold">
                {{ task.frequency }}
              </span>
            </div>
          </div>

          <!-- 任務說明 -->
          <div class="mb-4 flex-1">
            <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
              {{ task.details || task.description }}
            </p>
          </div>

          <!-- 底部區域：積分 + 按鈕（同行） -->
          <div class="flex gap-2.5">
            <!-- 積分顯示 -->
            <div :class="getPointsBgClass(task.category)" class="flex-1 flex items-center justify-between px-4 py-2.5 rounded-lg">
              <span class="text-sm font-medium text-light-text dark:text-dark-text">獲得積分</span>
              <span :class="getPointsColorClass(task.category)" class="text-lg font-bold">
                +{{ formatPoints(task.points) }}
              </span>
            </div>

            <!-- 完成按鈕 -->
            <button
              class="flex-1 px-5 py-2.5 rounded-lg text-white font-medium shadow-md transition-all duration-300 hover:opacity-90 hover:scale-[1.02] bg-gradient-to-br from-cyan-400 to-blue-500">
              立即完成
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { formatPoints } from '../../utils/formatPoints'

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true
  },
  task: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['close'])

const closeModal = () => {
  emit('close')
}

// Helper functions matching TaskGrid.vue styling
const getTaskIconBgClass = (category) => {
  const classes = {
    'daily': 'bg-pink-100 dark:bg-pink-900/30',
    'financial': 'bg-indigo-100 dark:bg-indigo-900/30',
    'investment': 'bg-amber-100 dark:bg-amber-900/30',
    'esg': 'bg-emerald-100 dark:bg-emerald-900/30',
    'social': 'bg-purple-100 dark:bg-purple-900/30'
  }
  return classes[category] || 'bg-gray-100 dark:bg-gray-900/30'
}

const getCategoryLabelForTask = (category) => {
  const labels = {
    'daily': '日常互動',
    'financial': '理財學習',
    'investment': '投資實踐',
    'esg': '永續行動',
    'social': '社群成就'
  }
  return labels[category] || category
}

const getCategoryBadgeClass = (category) => {
  // 淺色背景 (分類標籤，與頻率標籤統一)
  const classes = {
    'daily': 'bg-pink-100/90 dark:bg-pink-900/40 text-pink-700 dark:text-pink-200',
    'financial': 'bg-indigo-100/90 dark:bg-indigo-900/40 text-indigo-700 dark:text-indigo-200',
    'investment': 'bg-amber-100/90 dark:bg-amber-900/40 text-amber-600 dark:text-amber-400',
    'esg': 'bg-emerald-100/90 dark:bg-emerald-900/40 text-emerald-700 dark:text-emerald-200',
    'social': 'bg-violet-100/90 dark:bg-violet-900/40 text-violet-700 dark:text-violet-200'
  }
  return classes[category] || 'bg-gray-100/90 dark:bg-gray-900/40 text-gray-700 dark:text-gray-200'
}

const getFrequencyBadgeClassNew = (category) => {
  // 淺色背景 (分類詳情標籤，如永續環保)
  const classes = {
    'daily': 'bg-pink-100/90 dark:bg-pink-900/40 text-pink-700 dark:text-pink-200',
    'financial': 'bg-indigo-100/90 dark:bg-indigo-900/40 text-indigo-700 dark:text-indigo-200',
    'investment': 'bg-amber-100/90 dark:bg-amber-900/40 text-amber-600 dark:text-amber-400',
    'esg': 'bg-emerald-100/90 dark:bg-emerald-900/40 text-emerald-700 dark:text-emerald-200',
    'social': 'bg-violet-100/90 dark:bg-violet-900/40 text-violet-700 dark:text-violet-200'
  }
  return classes[category] || 'bg-gray-100/90 dark:bg-gray-900/40 text-gray-700 dark:text-gray-200'
}

const getPointsColorClass = (category) => {
  const classes = {
    'daily': 'text-pink-600 dark:text-pink-400',
    'financial': 'text-indigo-600 dark:text-indigo-400',
    'investment': 'text-amber-500 dark:text-amber-400',
    'esg': 'text-emerald-600 dark:text-emerald-400',
    'social': 'text-violet-600 dark:text-violet-400'
  }
  return classes[category] || 'text-gray-600 dark:text-gray-400'
}

const getPointsBgClass = (category) => {
  const classes = {
    'daily': 'bg-pink-50 dark:bg-pink-900/20',
    'financial': 'bg-indigo-50 dark:bg-indigo-900/20',
    'investment': 'bg-amber-50 dark:bg-amber-900/20',
    'esg': 'bg-emerald-50 dark:bg-emerald-900/20',
    'social': 'bg-violet-50 dark:bg-violet-900/20'
  }
  return classes[category] || 'bg-gray-50 dark:bg-gray-900/20'
}
</script>
