<template>
  <!-- Backdrop with blur -->
  <div v-if="isOpen" @click="closeModal"
    class="fixed inset-0 z-50 bg-black/30 dark:bg-black/50 backdrop-blur-md flex items-center justify-center p-4">

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
        <!-- 左側：圖示區 -->
        <div class="flex-shrink-0 flex flex-col items-center gap-3">
          <div :class="getTaskIconBgClass(task.category)"
            class="w-24 h-24 rounded-2xl flex items-center justify-center text-4xl">
            {{ task.icon }}
          </div>
          <div class="flex gap-2">
            <span class="px-3 py-1 rounded-full text-xs font-medium" :class="getFrequencyBadgeClass(task.category)">
              {{ task.frequency }}
            </span>
            <span class="px-3 py-1 rounded-full text-xs font-medium" :class="getLevelBadgeClass(task.category)">
              {{ task.level }}
            </span>
          </div>
        </div>

        <!-- 右側：說明區 -->
        <div class="flex-1 space-y-4">
          <!-- 標題 -->
          <h2 class="text-xl sm:text-2xl font-bold text-light-text dark:text-dark-text pr-8">
            {{ task.title }}
          </h2>

          <!-- 描述 -->
          <p class="text-sm text-gray-600 dark:text-gray-300">
            {{ task.description }}
          </p>

          <!-- 積分 -->
          <div class="flex items-center justify-between p-3 rounded-xl bg-gradient-to-r from-purple-50 to-cyan-50 dark:from-purple-900/20 dark:to-cyan-900/20">
            <span class="text-sm font-semibold text-light-text dark:text-dark-text">獲得積分</span>
            <span :class="getPointsColorClass(task.category)" class="text-xl font-bold">
              +{{ task.points }}
            </span>
          </div>

          <!-- 詳細說明 (預留) -->
          <div class="text-sm text-gray-500 dark:text-gray-400">
            <p>市價 NT$ XXX｜任務說明預留位置</p>
          </div>

          <!-- 完成按鈕 -->
          <button
            class="w-full px-6 py-3 rounded-xl bg-gradient-to-r from-purple-500 to-cyan-400 hover:from-purple-600 hover:to-cyan-500 text-white font-semibold shadow-lg transition-all duration-300 hover:scale-105">
            立即完成
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

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
    'daily': 'bg-blue-100 dark:bg-blue-900/30',
    'social': 'bg-purple-100 dark:bg-purple-900/30',
    'esg': 'bg-emerald-100 dark:bg-emerald-900/30',
    'learning': 'bg-orange-100 dark:bg-orange-900/30'
  }
  return classes[category] || 'bg-gray-100 dark:bg-gray-900/30'
}

const getFrequencyBadgeClass = (category) => {
  const classes = {
    'daily': 'bg-blue-100 dark:bg-blue-900/50 text-blue-700 dark:text-blue-300',
    'social': 'bg-purple-100 dark:bg-purple-900/50 text-purple-700 dark:text-purple-300',
    'esg': 'bg-emerald-100 dark:bg-emerald-900/50 text-emerald-700 dark:text-emerald-300',
    'learning': 'bg-orange-100 dark:bg-orange-900/50 text-orange-700 dark:text-orange-300'
  }
  return classes[category] || 'bg-gray-100 dark:bg-gray-900/50 text-gray-700 dark:text-gray-300'
}

const getLevelBadgeClass = (category) => {
  return getFrequencyBadgeClass(category)
}

const getPointsColorClass = (category) => {
  const classes = {
    'daily': 'text-blue-600 dark:text-blue-400',
    'social': 'text-purple-600 dark:text-purple-400',
    'esg': 'text-emerald-600 dark:text-emerald-400',
    'learning': 'text-orange-600 dark:text-orange-400'
  }
  return classes[category] || 'text-gray-600 dark:text-gray-400'
}

const getCategoryBgClass = (category) => {
  const classes = {
    'daily': 'bg-gradient-to-br from-blue-50/80 to-cyan-50/80 dark:from-blue-950/30 dark:to-cyan-950/30 border border-blue-100 dark:border-blue-800',
    'social': 'bg-gradient-to-br from-purple-50/80 to-pink-50/80 dark:from-purple-950/30 dark:to-pink-950/30 border border-purple-100 dark:border-purple-800',
    'esg': 'bg-gradient-to-br from-emerald-50/80 to-teal-50/80 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-100 dark:border-emerald-800',
    'learning': 'bg-gradient-to-br from-orange-50/80 to-yellow-50/80 dark:from-orange-950/30 dark:to-yellow-950/30 border border-orange-100 dark:border-orange-800'
  }
  return classes[category] || 'bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700'
}

const getCategoryTextClass = (category) => {
  const classes = {
    'daily': 'text-blue-600 dark:text-blue-400',
    'social': 'text-purple-600 dark:text-purple-400',
    'esg': 'text-emerald-600 dark:text-emerald-400',
    'learning': 'text-orange-600 dark:text-orange-400'
  }
  return classes[category] || 'text-gray-600 dark:text-gray-400'
}

const getCategoryName = (category) => {
  const names = {
    'daily': '日常互動',
    'social': '社群參與',
    'esg': '永續行動',
    'learning': '知識探索'
  }
  return names[category] || '未分類'
}
</script>
