<template>
    <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <!-- 購物車/心願清單 標題與切換 -->
        <div class="mb-8 flex items-end justify-between">
            <div>
                <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-2">
                    {{ activeTab === 'cart' ? '購物車' : '心願清單' }}
                </h1>
                <p class="text-sm text-gray-600 dark:text-gray-400">
                    {{ activeTab === 'cart' ? '使用兌換積分選購心儀的禮品' : '收藏您喜愛的禮品' }}
                </p>
            </div>
            <!-- 心願清單/購物車切換按鈕 -->
            <button @click="activeTab = activeTab === 'cart' ? 'wishlist' : 'cart'" :class="[
                'px-5 py-2.5 rounded-full hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl',
                activeTab === 'cart'
                    ? 'bg-pink-50/80 dark:bg-pink-900/20 text-pink-600 dark:text-pink-400 hover:bg-pink-100 dark:hover:bg-pink-900/30 border border-pink-200 dark:border-pink-800'
                    : 'bg-sky-50/80 dark:bg-sky-900/20 text-sky-600 dark:text-sky-400 hover:bg-sky-100 dark:hover:bg-sky-900/30 border border-sky-200 dark:border-sky-800'
            ]">
                <!-- 購物車圖案（當在心願清單時顯示） -->
                <svg v-if="activeTab === 'wishlist'" class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
                <!-- 愛心圖案（當在購物車時顯示） -->
                <svg v-else class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                </svg>
                {{ activeTab === 'cart' ? '心願清單' : '購物車' }}
            </button>
        </div>

        <!-- 購物車列表 -->
        <div v-if="activeTab === 'cart' && cartItems.length > 0" class="grid grid-cols-1 lg:grid-cols-5 gap-6">
            <!-- 商品列表 -->
            <div class="lg:col-span-3 space-y-4 h-[600px] overflow-y-auto pr-2">
                <div v-for="item in cartItems" :key="item.id"
                    class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-5 dark:shadow-2xl border dark:border-gray-600/30 hover:scale-[1.01] transition-transform duration-300 group">
                    <div class="flex gap-6">
                        <!-- 商品圖片 -->
                        <div class="relative flex-shrink-0">
                            <div class="w-28 h-28 rounded-xl overflow-hidden bg-gray-100 dark:bg-gray-700 shadow-lg">
                                <img :src="item.image" :alt="item.title"
                                    class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110">
                            </div>

                            <!-- 等級徽章 -->
                            <div class="absolute -top-2 -left-2 w-6 h-6 rounded-md flex items-center justify-center shadow-lg"
                                :class="getLevelStyle(item.level)">
                                <svg class="w-3 h-3" fill="none" viewBox="0 0 24 24">
                                    <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z"
                                        fill="white" />
                                </svg>
                            </div>
                        </div>

                        <!-- 商品資訊 -->
                        <div class="flex-1 min-w-0 flex flex-col justify-between">
                            <div>
                                <h3 class="font-bold text-lg text-gray-900 dark:text-white line-clamp-1 mb-1">
                                    {{ item.title }}
                                </h3>
                                <p class="text-sm text-gray-600 dark:text-gray-400 mb-3">{{ item.description }}
                                </p>
                            </div>

                            <!-- 積分與數量控制 -->
                            <div class="flex items-end justify-between">
                                <div class="flex items-center gap-2">
                                    <div class="flex items-center gap-1">
                                        <svg class="w-4 h-4 text-purple-500" fill="currentColor"
                                            viewBox="0 0 20 20">
                                            <path
                                                d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                                            <path fill-rule="evenodd"
                                                d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z"
                                                clip-rule="evenodd" />
                                        </svg>
                                        <span class="font-bold text-purple-600 dark:text-purple-400">{{
                                            item.points }}</span>
                                    </div>
                                    <span class="text-sm text-gray-500">積分</span>
                                </div>

                                <!-- 數量控制 -->
                                <div class="flex items-center gap-3">
                                    <div
                                        class="flex items-center gap-2 bg-gray-100 dark:bg-gray-700 rounded-lg p-1">
                                        <button @click="updateQuantity(item.id, -1)"
                                            class="w-8 h-8 flex items-center justify-center rounded-md hover:bg-white dark:hover:bg-gray-600 text-gray-600 dark:text-gray-300 transition-colors disabled:opacity-50"
                                            :disabled="item.quantity <= 1">
                                            <svg class="w-4 h-4" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round"
                                                    stroke-width="2" d="M20 12H4" />
                                            </svg>
                                        </button>
                                        <span
                                            class="text-sm font-bold w-8 text-center text-gray-900 dark:text-white">{{
                                                item.quantity }}</span>
                                        <button @click="updateQuantity(item.id, 1)"
                                            class="w-8 h-8 flex items-center justify-center rounded-md hover:bg-white dark:hover:bg-gray-600 text-gray-600 dark:text-gray-300 transition-colors">
                                            <svg class="w-4 h-4" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round"
                                                    stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                                            </svg>
                                        </button>
                                    </div>

                                    <!-- 移除按鈕 -->
                                    <button @click="removeItem(item.id)"
                                        class="w-8 h-8 flex items-center justify-center rounded-md text-gray-400 hover:text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors">
                                        <svg class="w-4 h-4" fill="none" stroke="currentColor"
                                            viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                stroke-width="2"
                                                d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 訂單摘要卡片 -->
            <div class="lg:col-span-2">
                <div
                    class="sticky top-24 bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30 h-[600px] flex flex-col">
                    <h2 class="text-lg font-bold text-gray-900 dark:text-white mb-4">
                        訂單摘要
                    </h2>

                    <div class="flex-1 flex flex-col">
                        <!-- 積分餘額 -->
                        <div
                            class="mb-4 p-4 bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 rounded-xl hover:scale-[1.02] transition-transform duration-300">
                            <div class="flex items-center justify-between mb-2">
                                <span class="text-sm font-medium text-purple-600 dark:text-purple-400">可用兌換積分</span>
                                <svg class="w-4 h-4 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                                    <path
                                        d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                                    <path fill-rule="evenodd"
                                        d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z"
                                        clip-rule="evenodd" />
                                </svg>
                            </div>
                            <div class="text-2xl font-bold text-purple-600 dark:text-purple-400">{{ availablePoints }}
                            </div>
                        </div>

                        <!-- 兌換說明 -->
                        <div class="mb-6 p-3 bg-blue-50 dark:bg-blue-900/20 rounded-lg hover:scale-[1.02] transition-transform duration-300">
                            <div class="flex items-center justify-between mb-2">
                                <span class="text-sm font-medium text-blue-700 dark:text-blue-300">兌換後積分將被扣除</span>
                                <svg class="w-4 h-4 text-blue-500" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd"
                                        d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                                        clip-rule="evenodd" />
                                </svg>
                            </div>
                            <p class="text-sm text-blue-600 dark:text-blue-400">禮品將於3-5個工作日內寄送。</p>
                        </div>

                        <div class="flex-1"></div>

                        <!-- 兌換按鈕區域 -->
                        <div class="mt-auto">
                            <!-- 訂單詳情 -->
                            <div class="space-y-3 mb-4">
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
                                <div v-if="totalPoints > availablePoints"
                                    class="p-3 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-800">
                                    <div class="flex items-center gap-2 text-red-600 dark:text-red-400">
                                        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd"
                                                d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                                                clip-rule="evenodd" />
                                        </svg>
                                        <span class="text-sm font-medium">積分不足</span>
                                    </div>
                                    <p class="text-sm text-red-600 dark:text-red-400 mt-1">
                                        還需要 {{ totalPoints - availablePoints }} 積分才能完成兌換
                                    </p>
                                </div>
                            </div>

                            <!-- 兌換按鈕 -->
                            <button @click="checkout" :disabled="totalPoints > availablePoints || isCheckingOut" :class="[
                                'w-full py-4 rounded-full font-bold transition-all duration-300 flex items-center justify-center gap-2',
                                totalPoints > availablePoints || isCheckingOut
                                    ? 'bg-gray-300 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                                    : 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-[1.02] active:scale-[0.98]'
                            ]">
                                <svg v-if="isCheckingOut" class="w-5 h-5 animate-spin" fill="none" viewBox="0 0 24 24">
                                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                </svg>
                                {{ isCheckingOut ? '兌換中...' : (totalPoints > availablePoints ? '積分不足' : '確認兌換') }}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 心願清單 -->
        <div v-if="activeTab === 'wishlist' && store.wishlistItems.length > 0" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            <div v-for="item in store.wishlistItems" :key="item.id"
                class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl overflow-hidden border dark:border-gray-600/30 hover:scale-105 transition-transform duration-300 group">
                <div class="relative aspect-[4/3] overflow-hidden">
                    <img :src="item.image" :alt="item.title"
                        class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300">
                    <!-- 移除愛心按鈕 -->
                    <button @click="store.removeFromWishlist(item.id)"
                        class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center rounded-full bg-white/90 dark:bg-gray-800/90 backdrop-blur-sm shadow-lg hover:scale-110 active:scale-95 transition-all duration-300 border border-white/50 dark:border-gray-600/50">
                        <svg class="w-5 h-5 text-pink-500" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd"
                                d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                                clip-rule="evenodd" />
                        </svg>
                    </button>
                </div>
                <div class="p-3">
                    <h3 class="text-sm font-bold text-gray-900 dark:text-white mb-2 line-clamp-1">{{ item.title }}</h3>
                    <div class="flex items-center justify-between">
                        <div class="flex items-baseline gap-1">
                            <span class="text-lg font-bold text-purple-600 dark:text-purple-400">{{ item.points }}</span>
                            <span class="text-xs text-gray-500 dark:text-gray-400">積分</span>
                        </div>
                        <button @click="handleAddFromWishlist(item)"
                            class="px-3 py-1.5 rounded-full text-xs font-semibold bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95 transition-all duration-300">
                            加入購物車
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 空購物車狀態 -->
        <div v-else-if="activeTab === 'cart' && cartItems.length === 0" class="text-center py-24">
            <div
                class="w-32 h-32 mx-auto mb-4 bg-gradient-to-br from-sky-100 to-blue-100 dark:from-sky-900/30 dark:to-blue-900/30 rounded-full flex items-center justify-center">
                <svg class="w-16 h-16 text-sky-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                        d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
            </div>
            <h2 class="text-lg font-normal text-gray-600 dark:text-gray-400">購物車是空的</h2>
        </div>

        <!-- 空心願清單狀態 -->
        <div v-else-if="activeTab === 'wishlist' && store.wishlistItems.length === 0" class="text-center py-24">
            <div
                class="w-32 h-32 mx-auto mb-4 bg-gradient-to-br from-pink-100 to-rose-100 dark:from-pink-900/30 dark:to-rose-900/30 rounded-full flex items-center justify-center">
                <svg class="w-16 h-16 text-pink-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                        d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                </svg>
            </div>
            <h2 class="text-lg font-normal text-gray-600 dark:text-gray-400">心願清單是空的</h2>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from '../store/app.js'

// 使用全局 store
const store = useStore()
const router = useRouter()

// 頁籤切換
const activeTab = ref('cart')

// 檢測登入modal是否開啟
const isLoginModalOpen = ref(false)

// 監聽 document 上的 modal 變化
if (typeof document !== 'undefined') {
  const observer = new MutationObserver(() => {
    const modal = document.querySelector('.fixed.inset-0.z-50')
    isLoginModalOpen.value = !!modal
  })
  observer.observe(document.body, { childList: true, subtree: true })
}

// 用戶積分 - 使用 store 的即時數據
const availablePoints = computed(() => store.userPoints.rewardPoints || 0)

// 使用全局購物車狀態
const cartItems = computed(() => store.cartItems)

// 計算總數量
const totalItems = computed(() => {
    return store.cartItemCount
})

// 計算總積分
const totalPoints = computed(() => {
    return cartItems.value.reduce((total, item) => total + (item.points * item.quantity), 0)
})

// 更新商品數量
const updateQuantity = (itemId, change) => {
    store.updateCartItemQuantity(itemId, change)
}

// 移除商品
const removeItem = (itemId) => {
    store.removeFromCart(itemId)
}

// 結帳 - 跳轉到確認頁面
const isCheckingOut = ref(false)

const checkout = () => {
    if (totalPoints.value > availablePoints.value) {
        store.showToast('積分不足，無法完成兌換', 'error')
        return
    }

    if (!store.isAuthenticated) {
        store.showToast('請先登入', 'error')
        return
    }

    // 把購物車內容複製到 checkoutItems
    store.setCheckoutItems(cartItems.value)

    // 跳轉到確認頁面
    router.push('/checkout-confirm')
}

// 獲取系列樣式
const getSeriesStyle = (series) => {
    const styles = {
        sustainable: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-300',
        quality: 'bg-sky-100 dark:bg-sky-900/30 text-sky-700 dark:text-sky-300',
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

// 從心願清單加入購物車
const handleAddFromWishlist = (item) => {
    // 檢查是否已登入
    if (!store.isAuthenticated) {
        store.showToast('請先登入以使用購物車功能', 'error')
        return
    }

    store.addToCart(item)
    store.showToast(`已將「${item.title}」加入購物車！`, 'success')
}

// 初始化：載入心願清單
onMounted(() => {
    store.loadWishlist()
})
</script>