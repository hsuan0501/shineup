<template>
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <!-- Scroll to Top Button -->
        <button @click="scrollToTop"
            class="fixed bottom-6 left-6 sm:left-8 lg:left-10 z-50 h-12 w-12 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 hover:scale-105 active:scale-95 transition-all duration-400 ease-out shadow-[0_4px_16px_rgba(0,0,0,0.08)] dark:shadow-[0_4px_16px_rgba(0,0,0,0.3)] hover:shadow-[0_6px_20px_rgba(0,0,0,0.12)] dark:hover:shadow-[0_6px_20px_rgba(0,0,0,0.4)] backdrop-blur-xl backdrop-saturate-150 group"
            aria-label="回到頂部">
            <svg class="w-5 h-5 text-zinc-600 dark:text-gray-300 group-hover:text-zinc-800 dark:group-hover:text-white transition-colors duration-300"
                fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
            </svg>
        </button>
        <!-- 頁面標題 -->
        <div class="mb-8">
            <h1 class="text-3xl font-bold text-light-text dark:text-dark-text mb-2">購物車</h1>
            <p class="text-gray-600 dark:text-gray-400">使用兌換積分選購心儀的禮品</p>
        </div>

        <div v-if="cartItems.length > 0" class="grid grid-cols-1 lg:grid-cols-5 gap-8">
            <!-- 購物車商品列表 -->
            <div class="lg:col-span-3 space-y-6">
                <div v-for="item in cartItems" :key="item.id"
                    class="group relative overflow-hidden bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg transition-all duration-300 hover:shadow-xl hover:scale-[1.02]">
                    
                    <!-- 系列標識 -->
                    <div class="absolute top-4 right-4">
                        <span class="px-2 py-1 text-xs font-bold rounded-lg"
                              :class="getSeriesStyle(item.series)">
                            {{ getSeriesName(item.series) }}
                        </span>
                    </div>

                    <div class="flex gap-6">
                        <!-- 商品圖片 -->
                        <div class="relative flex-shrink-0">
                            <div class="w-24 h-24 rounded-xl overflow-hidden bg-gray-100 dark:bg-gray-700 shadow-lg">
                                <img :src="item.image" :alt="item.title" class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110">
                            </div>
                            
                            <!-- 等級徽章 -->
                            <div class="absolute -top-2 -left-2 w-6 h-6 rounded-md flex items-center justify-center shadow-lg"
                                 :class="getLevelStyle(item.level)">
                                <svg class="w-3 h-3" fill="none" viewBox="0 0 24 24">
                                    <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                                </svg>
                            </div>
                        </div>

                        <!-- 商品資訊 -->
                        <div class="flex-1 min-w-0">
                            <div class="flex justify-between items-start mb-3">
                                <div class="flex-1">
                                    <h3 class="font-bold text-lg text-gray-900 dark:text-white line-clamp-1 mb-1">
                                        {{ item.title }}
                                    </h3>
                                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">{{ item.description }}</p>
                                    
                                    <!-- 積分與數量 -->
                                    <div class="flex items-center justify-between">
                                        <div class="flex items-center gap-2">
                                            <div class="flex items-center gap-1">
                                                <svg class="w-4 h-4 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                                                    <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z"/>
                                                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clip-rule="evenodd"/>
                                                </svg>
                                                <span class="font-bold text-purple-600 dark:text-purple-400">{{ item.points }}</span>
                                            </div>
                                            <span class="text-sm text-gray-500">積分</span>
                                        </div>

                                        <!-- 數量控制 -->
                                        <div class="flex items-center gap-3">
                                            <div class="flex items-center gap-2 bg-gray-100 dark:bg-gray-700 rounded-lg p-1">
                                                <button @click="updateQuantity(item.id, -1)"
                                                    class="w-8 h-8 flex items-center justify-center rounded-md hover:bg-white dark:hover:bg-gray-600 text-gray-600 dark:text-gray-300 transition-colors disabled:opacity-50"
                                                    :disabled="item.quantity <= 1">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4"/>
                                                    </svg>
                                                </button>
                                                <span class="text-sm font-bold w-8 text-center text-gray-900 dark:text-white">{{ item.quantity }}</span>
                                                <button @click="updateQuantity(item.id, 1)"
                                                    class="w-8 h-8 flex items-center justify-center rounded-md hover:bg-white dark:hover:bg-gray-600 text-gray-600 dark:text-gray-300 transition-colors">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"/>
                                                    </svg>
                                                </button>
                                            </div>
                                            
                                            <!-- 移除按鈕 -->
                                            <button @click="removeItem(item.id)"
                                                class="w-8 h-8 flex items-center justify-center rounded-md text-gray-400 hover:text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                                </svg>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 訂單摘要卡片 -->
            <div class="lg:col-span-2">
                <div class="sticky top-24 bg-white dark:bg-gray-800 rounded-2xl p-6 shadow-lg">
                    <h2 class="text-xl font-bold text-gray-900 dark:text-white mb-6">
                        訂單摘要
                    </h2>

                    <!-- 積分餘額 -->
                    <div class="mb-6 p-4 bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 rounded-xl">
                        <div class="flex items-center justify-between mb-2">
                            <span class="text-sm font-medium text-purple-600 dark:text-purple-400">可用兌換積分</span>
                            <svg class="w-4 h-4 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                                <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z"/>
                                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clip-rule="evenodd"/>
                            </svg>
                        </div>
                        <div class="text-2xl font-bold text-purple-600 dark:text-purple-400">{{ availablePoints }}</div>
                    </div>

                    <!-- 訂單詳情 -->
                    <div class="space-y-3 mb-6">
                        <div class="flex justify-between text-gray-600 dark:text-gray-400">
                            <span>商品總數</span>
                            <span class="font-medium">{{ totalItems }} 件</span>
                        </div>
                        <div class="flex justify-between text-gray-600 dark:text-gray-400">
                            <span>所需積分</span>
                            <span class="font-medium">{{ totalPoints }} 分</span>
                        </div>
                        
                        <div class="border-t border-gray-200 dark:border-gray-700 pt-3">
                            <div class="flex justify-between items-center">
                                <span class="text-lg font-bold text-gray-900 dark:text-white">總計</span>
                                <div class="text-right">
                                    <div class="text-2xl font-bold text-purple-600 dark:text-purple-400">
                                        {{ totalPoints }}
                                    </div>
                                    <div class="text-sm text-gray-500">積分</div>
                                </div>
                            </div>
                        </div>

                        <!-- 餘額提醒 -->
                        <div v-if="totalPoints > availablePoints" class="p-3 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-800">
                            <div class="flex items-center gap-2 text-red-600 dark:text-red-400">
                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd"/>
                                </svg>
                                <span class="text-sm font-medium">積分不足</span>
                            </div>
                            <p class="text-sm text-red-600 dark:text-red-400 mt-1">
                                還需要 {{ totalPoints - availablePoints }} 積分才能完成兌換
                            </p>
                        </div>
                    </div>

                    <!-- 兌換按鈕 -->
                    <button @click="checkout"
                        :disabled="totalPoints > availablePoints"
                        :class="[
                            'w-full py-4 rounded-full font-bold transition-all duration-300 flex items-center justify-center',
                            totalPoints > availablePoints 
                                ? 'bg-gray-300 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                                : 'bg-gradient-to-r from-sky-400 to-blue-600 text-white hover:shadow-lg hover:shadow-blue-500/25 hover:scale-[1.02] active:scale-[0.98]'
                        ]">
                        {{ totalPoints > availablePoints ? '積分不足' : '確認兌換' }}
                    </button>

                    <!-- 提示訊息 -->
                    <div class="mt-4 p-3 bg-blue-50 dark:bg-blue-900/20 rounded-lg">
                        <div class="flex items-start gap-2">
                            <svg class="w-4 h-4 text-blue-500 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd"/>
                            </svg>
                            <div class="text-sm text-blue-700 dark:text-blue-300">
                                <p class="font-medium mb-1">兌換說明</p>
                                <p class="text-blue-600 dark:text-blue-400">兌換後積分將被扣除，禮品將於3-5個工作日內寄送。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 空購物車狀態 -->
        <div v-else class="text-center py-24">
            <div class="w-32 h-32 mx-auto mb-8 bg-gradient-to-br from-purple-100 to-pink-100 dark:from-purple-900/30 dark:to-pink-900/30 rounded-full flex items-center justify-center">
                <svg class="w-16 h-16 text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                        d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-3">購物車是空的</h2>
            <p class="text-gray-600 dark:text-gray-400 mb-8 max-w-md mx-auto">
                還沒有選擇任何禮品嗎？快去瀏覽精選商品，找到您心儀的獎勵吧！
            </p>
            
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
                <router-link 
                    to="/rewards"
                    class="px-6 py-3 bg-gradient-to-r from-purple-600 to-pink-600 text-white rounded-xl font-medium hover:shadow-lg hover:shadow-purple-500/25 transition-all duration-300 hover:scale-105">
                    瀏覽禮品商城
                </router-link>
                <router-link 
                    to="/tasks"
                    class="px-6 py-3 border-2 border-purple-300 dark:border-purple-600 text-purple-600 dark:text-purple-400 rounded-xl font-medium hover:bg-purple-50 dark:hover:bg-purple-900/20 transition-all duration-300">
                    完成任務賺積分
                </router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { mockUsers } from '../mock.js'

// 用戶積分
const availablePoints = computed(() => mockUsers[1]?.rewardPoints || 0)

// 購物車狀態
const cartItems = ref([
  {
    id: 1,
    title: 'LIFE PEN 種子鉛筆組',
    points: 100,
    image: '/images/gifts/gift-1.jpg',
    series: 'sustainable',
    level: 'EXPLORER',
    quantity: 1,
    description: '市價NT$280'
  },
  {
    id: 2,
    title: 'ekax 雲朵筆電包',
    points: 220,
    image: '/images/gifts/gift-9.jpg',
    series: 'quality', 
    level: 'CREATOR',
    quantity: 2,
    description: '市價NT$650'
  }
])

// 計算總數量
const totalItems = computed(() => {
  return cartItems.value.reduce((total, item) => total + item.quantity, 0)
})

// 計算總積分
const totalPoints = computed(() => {
  return cartItems.value.reduce((total, item) => total + (item.points * item.quantity), 0)
})

// 更新商品數量
const updateQuantity = (itemId, change) => {
  const item = cartItems.value.find(item => item.id === itemId)
  if (item) {
    item.quantity = Math.max(1, item.quantity + change)
  }
}

// 移除商品
const removeItem = (itemId) => {
  cartItems.value = cartItems.value.filter(item => item.id !== itemId)
}

// 結帳
const checkout = () => {
  if (totalPoints.value > availablePoints.value) {
    alert('積分不足，無法完成兌換')
    return
  }
  
  alert(`兌換成功！共使用 ${totalPoints.value} 積分`)
  // 實際應用中這裡會調用API
}

// 獲取系列樣式
const getSeriesStyle = (series) => {
  const styles = {
    sustainable: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-300',
    quality: 'bg-cyan-100 dark:bg-cyan-900/30 text-cyan-700 dark:text-cyan-300', 
    aesthetic: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-300',
    premium: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-300'
  }
  return styles[series] || styles.sustainable
}

// 獲取系列名稱
const getSeriesName = (series) => {
  const names = {
    sustainable: '永續探索',
    quality: '質感創造',
    aesthetic: '美學先鋒', 
    premium: '品味閃耀'
  }
  return names[series] || '永續探索'
}

// 獲取等級樣式
const getLevelStyle = (level) => {
  const styles = {
    EXPLORER: 'bg-gradient-to-br from-emerald-400 to-emerald-600',
    CREATOR: 'bg-gradient-to-br from-cyan-400 to-blue-500',
    VISIONARY: 'bg-gradient-to-br from-amber-400 to-amber-600',
    LUMINARY: 'bg-gradient-to-br from-purple-400 to-purple-600'
  }
  return styles[level] || styles.EXPLORER
}

// Scroll to Top
const showScrollTop = ref(false)

const handleScroll = () => {
  showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    left: 0,
    behavior: 'smooth'
  })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
}

// 滾動事件監聽
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})


</script>