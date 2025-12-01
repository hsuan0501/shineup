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
          <div class="relative w-48 h-48 rounded-2xl overflow-hidden">
            <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover">
          </div>
          <!-- 收藏愛心按鈕 -->
          <button @click="handleToggleWishlist"
            class="absolute top-2 left-2 w-8 h-8 flex items-center justify-center rounded-full bg-white/90 dark:bg-gray-800/90 backdrop-blur-sm shadow-lg hover:scale-110 active:scale-95 transition-all duration-300 border border-white/50 dark:border-gray-600/50">
            <svg v-if="store.isInWishlist(gift.id)" class="w-4 h-4 text-pink-500" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd"
                d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                clip-rule="evenodd" />
            </svg>
            <svg v-else class="w-4 h-4 text-gray-400 dark:text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
            </svg>
          </button>
        </div>

        <!-- 右側：說明區 -->
        <div class="flex-1 flex flex-col">
          <!-- 標題與標籤 -->
          <div class="mb-3">
            <h2 class="text-xl sm:text-2xl font-bold text-light-text dark:text-dark-text mb-2 pr-8">
              {{ gift.title }}
            </h2>
            <div class="flex gap-2">
              <span :class="getLevelBadgeClassLight(gift.level)"
                class="px-3 py-1 rounded-full text-xs font-semibold">
                {{ gift.level }}
              </span>
              <span :class="getSeriesBadgeClassLight(gift.series)"
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
              {{ gift.details || '這是商品的詳細說明文字，提供更多關於商品特色、材質、尺寸等資訊。讓使用者能夠充分了解商品內容，做出更好的兌換決策。' }}
            </p>
          </div>

          <!-- 底部區域：積分 + 按鈕（同行） -->
          <div class="flex gap-2.5">
            <!-- 積分顯示 -->
            <div :class="getPointsBgClass(gift.level)" class="flex-1 flex items-center justify-between px-4 py-2.5 rounded-lg">
              <span class="text-sm font-medium text-light-text dark:text-dark-text">所需積分</span>
              <span :class="getPointsColorClass(gift.level)" class="text-lg font-bold">
                {{ formatPoints(gift.points) }}
              </span>
            </div>

            <!-- 兌換按鈕 -->
            <button @click="handleAddToCart" :class="[
              'flex-1 px-5 py-2.5 rounded-lg text-white font-medium shadow-md transition-all duration-300',
              gift.canExchange === false || gift.points > user.rewardPoints || gift.stockStatus === 'out'
                ? 'bg-gray-300 dark:bg-gray-700 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                : 'bg-gradient-to-br from-cyan-400 to-blue-500 hover:opacity-90 hover:scale-[1.02]'
            ]"
              :disabled="gift.canExchange === false || gift.points > user.rewardPoints || gift.stockStatus === 'out'">
              {{ gift.canExchange === false || gift.points > user.rewardPoints || gift.stockStatus === 'out' ? '積分不足' : '立即兌換' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { useStore } from '../../store/app'
import { mockUsers } from '../../mock'
import { ref } from 'vue'
import { formatPoints } from '../../utils/formatPoints'

const store = useStore()
const user = ref(mockUsers[1])

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
const getLevelBadgeClassLight = (level) => {
  // 淺色背景 (詳細頁標籤)
  const classes = {
    'EXPLORER': 'bg-emerald-50 dark:bg-emerald-900/20 text-emerald-700 dark:text-emerald-200',
    'CREATOR': 'bg-indigo-50 dark:bg-indigo-900/20 text-indigo-700 dark:text-indigo-200',
    'VISIONARY': 'bg-amber-50 dark:bg-amber-900/20 text-amber-700 dark:text-amber-200',
    'LUMINARY': 'bg-purple-50 dark:bg-purple-900/20 text-purple-700 dark:text-purple-200'
  }
  return classes[level] || 'bg-gray-50 dark:bg-gray-900/20 text-gray-700 dark:text-gray-200'
}

const getSeriesBadgeClassLight = (series) => {
  // 淺色背景 (詳細頁系列標籤)
  const classes = {
    'sustainable': 'bg-emerald-50 dark:bg-emerald-900/20 text-emerald-700 dark:text-emerald-200',
    'quality': 'bg-indigo-50 dark:bg-indigo-900/20 text-indigo-700 dark:text-indigo-200',
    'aesthetic': 'bg-amber-50 dark:bg-amber-900/20 text-amber-700 dark:text-amber-200',
    'premium': 'bg-purple-50 dark:bg-purple-900/20 text-purple-700 dark:text-purple-200'
  }
  return classes[series] || 'bg-gray-50 dark:bg-gray-900/20 text-gray-700 dark:text-gray-200'
}

const getPointsColorClass = (level) => {
  const colors = {
    'EXPLORER': 'text-emerald-600 dark:text-emerald-400',
    'CREATOR': 'text-indigo-600 dark:text-indigo-400',
    'VISIONARY': 'text-amber-500 dark:text-amber-400',
    'LUMINARY': 'text-purple-600 dark:text-purple-400'
  }
  return colors[level] || 'text-purple-600 dark:text-purple-400'
}

const getPointsBgClass = (level) => {
  const classes = {
    'EXPLORER': 'bg-emerald-50 dark:bg-emerald-900/20',
    'CREATOR': 'bg-indigo-50 dark:bg-indigo-900/20',
    'VISIONARY': 'bg-amber-50 dark:bg-amber-900/20',
    'LUMINARY': 'bg-purple-50 dark:bg-purple-900/20'
  }
  return classes[level] || 'bg-purple-50 dark:bg-purple-900/20'
}

const getSeriesName = (series) => {
  const names = {
    'sustainable': '永續探索',
    'quality': '質感創造',
    'aesthetic': '美學先鋒',
    'premium': '品味閃耀'
  }
  return names[series] || '未分類'
}

const formatPrice = (price) => {
  if (!price) return 'NT$280'

  // 如果 price 已經是完整格式（包含 NT$），直接返回
  if (typeof price === 'string' && price.includes('NT$')) {
    return price
  }

  // 如果是純數字，添加 NT$ 前綴
  const number = parseInt(price)
  if (isNaN(number)) return price

  return `NT$${number.toLocaleString('zh-TW')}`
}

// 處理加入購物車
const handleAddToCart = () => {
  // 檢查是否已登入
  if (!store.isAuthenticated) {
    store.showToast('請先登入以使用購物車功能', 'error')
    return
  }

  if (props.gift.canExchange === false) {
    return
  }

  if (props.gift.points > user.value.rewardPoints) {
    store.showToast('您的積分不足，無法兌換此禮品', 'error')
    return
  }

  if (props.gift.stockStatus === 'out') {
    store.showToast('此禮品已售罄', 'error')
    return
  }

  store.addToCart(props.gift)
  store.showToast(`已將「${props.gift.title}」加入購物車！`, 'success')
  closeModal()
}

// 處理收藏/取消收藏
const handleToggleWishlist = () => {
  // 檢查是否已登入
  if (!store.isAuthenticated) {
    store.showToast('請先登入以使用收藏功能', 'error')
    return
  }

  const wasInWishlist = store.isInWishlist(props.gift.id)
  store.toggleWishlist(props.gift)

  if (wasInWishlist) {
    store.showToast(`已將「${props.gift.title}」從收藏清單移除`, 'wishlist-remove')
  } else {
    store.showToast(`已將「${props.gift.title}」加入收藏清單`, 'wishlist-add')
  }
}
</script>
