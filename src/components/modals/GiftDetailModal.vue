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
        <div class="flex-shrink-0">
          <div class="relative w-48 h-48 rounded-2xl overflow-hidden">
            <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover">
          </div>
        </div>

        <!-- 右側：說明區 -->
        <div class="flex-1 flex flex-col">
          <!-- 標題與標籤 -->
          <div class="mb-3">
            <h2 class="text-xl sm:text-2xl font-bold text-light-text dark:text-dark-text mb-2 pr-8">
              {{ gift.title }}
            </h2>
            <div class="flex gap-2">
              <span :class="getLevelBadgeClass(gift.level)"
                class="px-3 py-1 rounded-full text-xs font-semibold">
                {{ gift.level }}
              </span>
              <span :class="getSeriesBadgeClass(gift.series)"
                class="px-3 py-1 rounded-full text-xs font-semibold">
                {{ getSeriesName(gift.series) }}
              </span>
            </div>
          </div>

          <!-- 市價 -->
          <div class="mb-3">
            <p class="text-sm font-medium text-gray-700 dark:text-gray-300">市價 {{ formatPrice(gift.marketPrice) }}</p>
          </div>

          <!-- 商品說明 -->
          <div class="mb-4 flex-1">
            <p class="text-sm text-gray-500 dark:text-gray-500 leading-relaxed">
              這是商品的詳細說明文字，提供更多關於商品特色、材質、尺寸等資訊。讓使用者能夠充分了解商品內容，做出更好的兌換決策。
            </p>
          </div>

          <!-- 底部區域：積分 + 按鈕 -->
          <div class="space-y-2.5">
            <!-- 積分顯示 -->
            <div class="flex items-center justify-between px-4 py-2.5 rounded-lg bg-gradient-to-r from-purple-50 to-cyan-50 dark:from-purple-900/20 dark:to-cyan-900/20">
              <span class="text-sm font-medium text-light-text dark:text-dark-text">所需積分</span>
              <span :class="getPointsColorClass(gift.series)" class="text-lg font-bold">
                {{ gift.points }}
              </span>
            </div>

            <!-- 按鈕區 -->
            <div class="flex gap-2.5">
              <button
                class="flex-1 px-5 py-2.5 rounded-lg bg-gradient-to-r from-purple-500 to-cyan-400 hover:from-purple-600 hover:to-cyan-500 text-white font-medium shadow-md transition-all duration-300 hover:scale-[1.02]">
                立即兌換
              </button>
              <button
                class="w-10 h-10 flex items-center justify-center rounded-lg border-2 border-purple-300 dark:border-purple-600 text-purple-600 dark:text-purple-400 hover:bg-purple-50 dark:hover:bg-purple-900/20 transition-all duration-300">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                </svg>
              </button>
            </div>
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

const getSeriesName = (series) => {
  const names = {
    'sustainable': '永續環保',
    'quality': '品質生活',
    'aesthetic': '美學設計',
    'premium': '尊榮精選'
  }
  return names[series] || '未分類'
}

const formatPrice = (price) => {
  if (!price) return 'NT$280'

  // 如果 price 是字串且包含 NT$，先提取數字
  let numericPrice = price
  if (typeof price === 'string' && price.includes('NT$')) {
    numericPrice = price.replace('NT$', '').trim()
  }

  // 將數字轉換為帶千分號的格式
  const number = parseInt(numericPrice)
  if (isNaN(number)) return price

  const formatted = number.toLocaleString('en-US')
  return `NT$${formatted}`
}
</script>
