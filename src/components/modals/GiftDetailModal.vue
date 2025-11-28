<template>
  <!-- Backdrop -->
  <div v-if="isOpen" @click="closeModal"
    class="fixed inset-0 z-50 bg-black/50 dark:bg-black/70 flex items-center justify-center p-4 backdrop-blur-sm transition-all duration-300">

    <!-- Modal Content -->
    <div @click.stop
      class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl max-w-2xl w-full max-h-[85vh] overflow-y-auto transform transition-all duration-300 scale-100">

      <!-- Close Button -->
      <button @click="closeModal"
        class="absolute top-4 right-4 z-10 w-8 h-8 flex items-center justify-center rounded-full bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors duration-200">
        <svg class="w-5 h-5 text-gray-600 dark:text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>

      <!-- Gift Image -->
      <div class="relative w-full h-64 sm:h-80 overflow-hidden rounded-t-2xl">
        <img :src="gift.image" :alt="gift.title"
          class="w-full h-full object-cover">

        <!-- Level Badge -->
        <div class="absolute top-4 left-4">
          <span :class="getLevelBadgeClass(gift.level)"
            class="px-3 py-1.5 rounded-full text-sm font-semibold shadow-lg backdrop-blur-sm">
            {{ gift.level }}
          </span>
        </div>

        <!-- Series Badge -->
        <div class="absolute top-4 right-14">
          <span :class="getSeriesBadgeClass(gift.series)"
            class="px-3 py-1.5 rounded-full text-sm font-semibold shadow-lg backdrop-blur-sm">
            {{ getSeriesName(gift.series) }}
          </span>
        </div>
      </div>

      <!-- Gift Header -->
      <div class="p-6 sm:p-8 border-b border-gray-200 dark:border-gray-700">
        <h2 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">
          {{ gift.title }}
        </h2>
        <p class="text-gray-600 dark:text-gray-400 mb-4">
          {{ gift.description }}
        </p>
      </div>

      <!-- Gift Details -->
      <div class="p-6 sm:p-8 space-y-6">
        <!-- Points Required -->
        <div class="flex items-center justify-between p-4 rounded-xl bg-gradient-to-r from-purple-50 to-cyan-50 dark:from-purple-900/20 dark:to-cyan-900/20">
          <span class="text-lg font-semibold text-light-text dark:text-dark-text">所需積分</span>
          <span :class="getPointsColorClass(gift.series)" class="text-2xl font-bold">
            {{ gift.points }}
          </span>
        </div>

        <!-- Market Value (Placeholder) -->
        <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-700/50">
          <h3 class="text-lg font-semibold text-light-text dark:text-dark-text mb-2">市場價值</h3>
          <p class="text-gray-600 dark:text-gray-300">
            市價 NT$ XXX
          </p>
        </div>

        <!-- Extended Description (Placeholder) -->
        <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-700/50">
          <h3 class="text-lg font-semibold text-light-text dark:text-dark-text mb-3">商品說明</h3>
          <div class="space-y-2 text-gray-600 dark:text-gray-300">
            <p>這裡是商品的詳細說明文字，將由使用者後續提供實際內容。</p>
            <p>第二行說明文字預留位置。</p>
            <p>第三行說明文字預留位置。</p>
            <p>第四行說明文字預留位置。</p>
          </div>
        </div>

        <!-- Gift Series Info -->
        <div class="p-4 rounded-xl" :class="getSeriesBgClass(gift.series)">
          <h3 class="text-lg font-semibold text-light-text dark:text-dark-text mb-2">商品系列</h3>
          <p :class="getSeriesTextClass(gift.series)" class="font-medium">
            {{ getSeriesName(gift.series) }}
          </p>
        </div>

        <!-- Action Buttons -->
        <div class="flex gap-3 pt-4">
          <button
            class="flex-1 px-6 py-3 rounded-xl bg-gradient-to-r from-purple-500 to-cyan-400 hover:from-purple-600 hover:to-cyan-500 text-white font-semibold shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105 active:scale-95">
            立即兌換
          </button>
          <button
            class="px-6 py-3 rounded-xl border-2 border-purple-300 dark:border-purple-600 text-purple-600 dark:text-purple-400 font-semibold hover:bg-purple-50 dark:hover:bg-purple-900/20 transition-all duration-300">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
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
  gift: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['close'])

const closeModal = () => {
  emit('close')
}

// Helper functions matching GiftGrid.vue styling
const getLevelBadgeClass = (level) => {
  const classes = {
    'EXPLORER': 'bg-emerald-500/90 text-white',
    'CREATOR': 'bg-indigo-500/90 text-white',
    'VISIONARY': 'bg-orange-500/90 text-white',
    'LUMINARY': 'bg-violet-500/90 text-white'
  }
  return classes[level] || 'bg-gray-500/90 text-white'
}

const getSeriesBadgeClass = (series) => {
  const classes = {
    'sustainable': 'bg-emerald-100/90 dark:bg-emerald-800/90 text-emerald-700 dark:text-emerald-200',
    'quality': 'bg-indigo-100/90 dark:bg-indigo-800/90 text-indigo-700 dark:text-indigo-200',
    'aesthetic': 'bg-orange-100/90 dark:bg-orange-800/90 text-orange-700 dark:text-orange-200',
    'premium': 'bg-violet-100/90 dark:bg-violet-800/90 text-violet-700 dark:text-violet-200'
  }
  return classes[series] || 'bg-gray-100/90 dark:bg-gray-800/90 text-gray-700 dark:text-gray-200'
}

const getPointsColorClass = (series) => {
  const classes = {
    'sustainable': 'text-emerald-600 dark:text-emerald-400',
    'quality': 'text-indigo-600 dark:text-indigo-400',
    'aesthetic': 'text-orange-600 dark:text-orange-400',
    'premium': 'text-violet-600 dark:text-violet-400'
  }
  return classes[series] || 'text-gray-600 dark:text-gray-400'
}

const getSeriesBgClass = (series) => {
  const classes = {
    'sustainable': 'bg-gradient-to-br from-emerald-50/80 to-teal-50/80 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-100 dark:border-emerald-800',
    'quality': 'bg-gradient-to-br from-indigo-50/80 to-blue-50/80 dark:from-indigo-950/30 dark:to-blue-950/30 border border-indigo-100 dark:border-indigo-800',
    'aesthetic': 'bg-gradient-to-br from-orange-50/80 to-pink-50/80 dark:from-orange-950/30 dark:to-pink-950/30 border border-orange-100 dark:border-orange-800',
    'premium': 'bg-gradient-to-br from-violet-50/80 to-purple-50/80 dark:from-violet-950/30 dark:to-purple-950/30 border border-violet-100 dark:border-violet-800'
  }
  return classes[series] || 'bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700'
}

const getSeriesTextClass = (series) => {
  const classes = {
    'sustainable': 'text-emerald-600 dark:text-emerald-400',
    'quality': 'text-indigo-600 dark:text-indigo-400',
    'aesthetic': 'text-orange-600 dark:text-orange-400',
    'premium': 'text-violet-600 dark:text-violet-400'
  }
  return classes[series] || 'text-gray-600 dark:text-gray-400'
}

const getSeriesName = (series) => {
  const names = {
    'sustainable': '永續環保',
    'quality': '品質生活',
    'aesthetic': '美學設計',
    'premium': '尊榮精選'
  }
  return names[series] || '未分類'
}
</script>
