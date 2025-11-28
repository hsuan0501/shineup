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

      <!-- 內容區 -->
      <div class="flex flex-col">
        <div class="flex-1 flex flex-col">
          <!-- 標題與標籤 -->
          <div class="mb-3">
            <h2 class="text-xl sm:text-2xl font-bold text-light-text dark:text-dark-text mb-2 pr-8">
              {{ task.title }}
            </h2>
            <div class="flex gap-2">
              <span class="px-3 py-1 rounded-full text-xs font-medium" :class="getFrequencyBadgeClass(task.category)">
                {{ task.frequency }}
              </span>
              <span class="px-3 py-1 rounded-full text-xs font-medium" :class="getLevelBadgeClass(task.category)">
                {{ task.level }}
              </span>
            </div>
          </div>

          <!-- 任務說明 -->
          <div class="mb-4 flex-1">
            <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
              {{ task.description }}
            </p>
          </div>

          <!-- 底部區域：積分 + 按鈕 -->
          <div class="space-y-2.5">
            <!-- 積分顯示 -->
            <div class="flex items-center justify-between px-4 py-2.5 rounded-lg bg-gradient-to-r from-purple-50 to-cyan-50 dark:from-purple-900/20 dark:to-cyan-900/20">
              <span class="text-sm font-medium text-light-text dark:text-dark-text">獲得積分</span>
              <span :class="getPointsColorClass(task.category)" class="text-lg font-bold">
                +{{ task.points }}
              </span>
            </div>

            <!-- 完成按鈕 -->
            <button
              class="w-full px-5 py-2.5 rounded-lg bg-gradient-to-r from-purple-500 to-cyan-400 hover:from-purple-600 hover:to-cyan-500 text-white font-medium shadow-md transition-all duration-300 hover:scale-[1.02]">
              立即完成
            </button>
          </div>
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
</script>
