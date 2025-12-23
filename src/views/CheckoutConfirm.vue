<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 標題 -->
    <div class="mb-8 flex items-end justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-2">確認兌換</h1>
        <p class="text-sm text-gray-600 dark:text-gray-400">請確認收件資訊後完成兌換</p>
      </div>
      <!-- 右上角返回購物車按鈕 -->
      <router-link to="/cart"
        class="px-4 py-2 rounded-full hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl bg-gray-100 dark:bg-gray-700 text-gray-600 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 border border-gray-200 dark:border-gray-600">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
        </svg>
        返回購物車
      </router-link>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-5 gap-6 items-stretch">
      <!-- 左側：收件資訊 -->
      <div class="lg:col-span-3 flex flex-col gap-6">
        <!-- 寄送方式選擇 -->
        <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">寄送方式</h3>
          <div class="space-y-3">
            <label class="flex items-center gap-3 p-4 rounded-xl border cursor-pointer transition-all"
              :class="deliveryMethod === 'home' ? 'border-sky-500 bg-sky-50 dark:bg-sky-900/20' : 'border-gray-200 dark:border-gray-600 hover:border-gray-300'">
              <input type="radio" v-model="deliveryMethod" value="home" class="w-4 h-4 text-sky-500">
              <div>
                <p class="font-medium text-gray-900 dark:text-white">宅配到府</p>
                <p class="text-sm text-gray-500 dark:text-gray-400">送至指定地址</p>
              </div>
            </label>
            <label class="flex items-center gap-3 p-4 rounded-xl border cursor-pointer transition-all"
              :class="deliveryMethod === 'store' ? 'border-sky-500 bg-sky-50 dark:bg-sky-900/20' : 'border-gray-200 dark:border-gray-600 hover:border-gray-300'">
              <input type="radio" v-model="deliveryMethod" value="store" class="w-4 h-4 text-sky-500">
              <div>
                <p class="font-medium text-gray-900 dark:text-white">超商取貨</p>
                <p class="text-sm text-gray-500 dark:text-gray-400">至指定超商門市取貨</p>
              </div>
            </label>
          </div>
        </div>

        <!-- 收件地址 (宅配) -->
        <div v-if="deliveryMethod === 'home'" class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">收件地址</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">收件人姓名 <span class="text-red-500">*</span></label>
              <input type="text" v-model="form.name"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入收件人姓名">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">手機號碼 <span class="text-red-500">*</span></label>
              <input type="tel" v-model="form.phone"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入手機號碼">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">收件地址 <span class="text-red-500">*</span></label>
              <input type="text" v-model="form.address"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入完整地址">
            </div>
          </div>
        </div>

        <!-- 超商取貨 -->
        <div v-if="deliveryMethod === 'store'" class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">超商取貨</h3>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">收件人姓名 <span class="text-red-500">*</span></label>
              <input type="text" v-model="form.name"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入收件人姓名">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">手機號碼 <span class="text-red-500">*</span></label>
              <input type="tel" v-model="form.phone"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入手機號碼">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">超商門市 <span class="text-red-500">*</span></label>
              <select v-model="form.storeBrand"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all">
                <option value="">請選擇超商</option>
                <option value="7-11">7-ELEVEN</option>
                <option value="family">全家</option>
                <option value="hilife">萊爾富</option>
                <option value="okmart">OK超商</option>
              </select>
            </div>
            <div v-if="form.storeBrand">
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">門市名稱 <span class="text-red-500">*</span></label>
              <input type="text" v-model="form.storeName"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入門市名稱（如：信義門市）">
            </div>
          </div>
        </div>
      </div>

      <!-- 右側：訂單摘要 -->
      <div class="lg:col-span-2 flex flex-col">
        <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30 flex flex-col flex-1">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">訂單摘要</h3>

          <!-- 商品列表 -->
          <div class="space-y-3 mb-4 flex-1 overflow-y-auto">
            <div v-for="item in store.checkoutItems" :key="item.id" class="flex gap-3 p-2 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
              <img :src="item.image" :alt="item.title" class="w-16 h-16 object-cover rounded-lg">
              <div class="flex-1 min-w-0">
                <p class="font-medium text-gray-900 dark:text-white text-sm line-clamp-1">{{ item.title }}</p>
                <p class="text-xs text-gray-500 dark:text-gray-400">x{{ item.quantity }}</p>
                <p class="text-sm font-bold text-purple-600 dark:text-purple-400">{{ item.points * item.quantity }} 積分</p>
              </div>
            </div>
          </div>

          <!-- 總計 -->
          <div class="border-t border-gray-200 dark:border-gray-600 pt-4 mb-4">
            <div class="flex justify-between items-center">
              <span class="text-gray-600 dark:text-gray-400">商品總數</span>
              <span class="font-medium text-gray-900 dark:text-white">{{ totalItems }} 件</span>
            </div>
            <div class="flex justify-between items-center mt-2">
              <span class="text-lg font-bold text-gray-900 dark:text-white">總計</span>
              <span class="text-2xl font-bold text-purple-600 dark:text-purple-400">{{ totalPoints }} 積分</span>
            </div>
          </div>

          <!-- 按鈕 -->
          <button @click="confirmCheckout" :disabled="!isFormValid || isProcessing"
            :class="[
              'w-full py-4 rounded-full font-bold transition-all duration-300 flex items-center justify-center gap-2',
              isFormValid && !isProcessing
                ? 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-[1.02] active:scale-[0.98]'
                : 'bg-gray-300 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed'
            ]">
            <svg v-if="isProcessing" class="w-5 h-5 animate-spin" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ isProcessing ? '處理中...' : '確認兌換' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from '@/store'

const router = useRouter()
const store = useStore()

// 寄送方式
const deliveryMethod = ref('home')

// 默認值
const defaults = {
  phone: '0912345678',
  address: '台北市中山區民生東路三段67號'
}

// 表單資料
const form = reactive({
  name: store.currentUser?.name || '',
  phone: store.currentUser?.phone || defaults.phone,
  address: store.currentUser?.address || defaults.address,
  storeBrand: '',
  storeName: ''
})

// 處理中狀態
const isProcessing = ref(false)

// 計算總數量
const totalItems = computed(() => {
  return store.checkoutItems.reduce((total, item) => total + item.quantity, 0)
})

// 計算總積分
const totalPoints = computed(() => {
  return store.checkoutItems.reduce((total, item) => total + (item.points * item.quantity), 0)
})

// 表單驗證
const isFormValid = computed(() => {
  if (!form.name || !form.phone) return false
  if (deliveryMethod.value === 'home') {
    return !!form.address
  } else {
    return !!form.storeBrand && !!form.storeName
  }
})

// 確認兌換
const confirmCheckout = async () => {
  if (!isFormValid.value || isProcessing.value) return

  isProcessing.value = true

  // 記錄兌換的商品名稱（用於通知）
  const itemNames = store.checkoutItems.map(item => item.title).join('、')

  try {
    const { orderAPI } = await import('@/api')

    // 準備批次兌換的資料
    const items = store.checkoutItems.map(item => ({
      giftId: item.id,
      quantity: item.quantity
    }))

    // 準備收件資訊
    const deliveryInfo = {
      recipientName: form.name,
      recipientPhone: form.phone,
      deliveryMethod: deliveryMethod.value,
      deliveryAddress: deliveryMethod.value === 'home' ? form.address : null,
      storeBrand: deliveryMethod.value === 'store' ? form.storeBrand : null,
      storeName: deliveryMethod.value === 'store' ? form.storeName : null
    }

    // 呼叫批次兌換 API（合併成一筆訂單）
    const response = await orderAPI.createBatch(store.currentUser.id, items, deliveryInfo)

    if (response.data) {
      // 更新用戶積分
      await store.checkAuth()

      // 觸發訂單刷新，讓 NavBar 更新出貨進度
      store.triggerOrderRefresh()

      // 發送出貨通知
      const settings = store.notificationSettings || {}
      if (settings.shipping !== false) {
        store.addNotification({
          type: 'shipping',
          title: '兌換成功',
          description: `「${itemNames}」已兌換，將於 3-5 個工作天出貨`,
          link: '/history?tab=orders'
        })
      }

      // 清空購物車和 checkoutItems
      store.clearCart()
      store.clearCheckoutItems()

      // 跳轉到完成頁面
      router.push('/redemption-complete')
    }

  } catch (error) {
    const message = error.response?.data?.message || '兌換過程發生錯誤'
    store.showToast(message, 'error')
  } finally {
    isProcessing.value = false
  }
}
</script>
