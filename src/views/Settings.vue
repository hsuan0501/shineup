<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 標題 -->
    <div class="mb-8">
      <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">帳號設定</h1>
      <p class="text-sm text-gray-600 dark:text-gray-400">管理您的個人資料與偏好設定</p>
    </div>

    <!-- 設定區塊 -->
    <div class="space-y-6">
      <!-- 個人資料 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-sky-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
          個人資料
        </h3>

        <div class="space-y-4">
          <!-- 姓名 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              姓名 <span class="text-pink-500">*</span>
            </label>
            <input type="text" v-model="form.name"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.name ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入姓名">
            <p v-if="errors.name" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.name }}</p>
          </div>

          <!-- Email -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">電子郵件</label>
            <input type="email" v-model="form.email" disabled
              class="w-full px-4 py-2.5 rounded-xl bg-gray-100 dark:bg-gray-800/80 border border-gray-200 dark:border-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed">
            <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">電子郵件無法修改</p>
          </div>

          <!-- 生日 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              生日 <span class="text-pink-500">*</span>
            </label>
            <input type="date" v-model="form.birthday"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.birthday ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]">
            <p v-if="errors.birthday" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.birthday }}</p>
            <p v-else class="text-xs text-gray-500 dark:text-gray-400 mt-1">生日資料僅限修改一次</p>
          </div>

          <!-- 電話 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              手機號碼 <span class="text-pink-500">*</span>
            </label>
            <input type="tel" v-model="form.phone"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.phone ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入手機號碼">
            <p v-if="errors.phone" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.phone }}</p>
          </div>
        </div>
      </div>

      <!-- 收件地址 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          收件地址
        </h3>

        <div class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <!-- 縣市 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                縣市 <span class="text-pink-500">*</span>
              </label>
              <select v-model="form.city"
                :class="[
                  'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                  errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
                ]">
                <option value="">請選擇</option>
                <option v-for="city in cities" :key="city" :value="city">{{ city }}</option>
              </select>
            </div>
            <!-- 區域 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                區域 <span class="text-pink-500">*</span>
              </label>
              <input type="text" v-model="form.district"
                :class="[
                  'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                  errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
                ]"
                placeholder="請輸入區域">
            </div>
          </div>

          <!-- 詳細地址 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              詳細地址 <span class="text-pink-500">*</span>
            </label>
            <input type="text" v-model="form.address"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入詳細地址">
            <p v-if="errors.address" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.address }}</p>
          </div>
        </div>
      </div>

      <!-- 通知設定 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-amber-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
          </svg>
          通知設定
        </h3>

        <div class="space-y-4">
          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">等級提升</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當等級提升時通知</p>
            </div>
            <button @click="toggleNotification('levelUp', '等級提升')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notifications.levelUp
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notifications.levelUp ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">積分變動</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當積分增加或使用時通知</p>
            </div>
            <button @click="toggleNotification('points', '積分變動')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notifications.points
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notifications.points ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">出貨通知</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當兌換禮品出貨時通知</p>
            </div>
            <button @click="toggleNotification('shipping', '出貨通知')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notifications.shipping
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notifications.shipping ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>
        </div>
      </div>

      <!-- 儲存按鈕 -->
      <div class="flex justify-end gap-3">
        <router-link to="/profile"
          class="px-6 py-2.5 rounded-full bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-all font-medium text-sm">
          取消
        </router-link>
        <button @click="saveSettings"
          class="px-6 py-2.5 rounded-full bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95 transition-all font-medium text-sm shadow-lg">
          儲存變更
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useStore } from '../store/app.js'

const store = useStore()

// 錯誤訊息
const errors = reactive({
  name: '',
  birthday: '',
  phone: '',
  address: ''
})

// 表單資料 - 從 store.currentUser 載入
const form = reactive({
  name: '',
  email: '',
  birthday: '',
  phone: '',
  city: '',
  district: '',
  address: ''
})

// 載入現有資料
onMounted(() => {
  if (store.currentUser) {
    form.name = store.currentUser.name || ''
    form.email = store.currentUser.email || ''
    form.birthday = store.currentUser.birthday || ''
    form.phone = store.currentUser.phone || ''
    // 地址可能需要拆分
    if (store.currentUser.address) {
      form.address = store.currentUser.address
    }
  }
})

// 通知設定
const notifications = reactive({
  points: true,
  levelUp: true,
  shipping: false
})

// 台灣縣市
const cities = [
  '台北市', '新北市', '桃園市', '台中市', '台南市', '高雄市',
  '基隆市', '新竹市', '新竹縣', '苗栗縣', '彰化縣', '南投縣',
  '雲林縣', '嘉義市', '嘉義縣', '屏東縣', '宜蘭縣', '花蓮縣',
  '台東縣', '澎湖縣', '金門縣', '連江縣'
]

// 驗證表單
const validateForm = () => {
  let isValid = true

  // 清除錯誤
  errors.name = ''
  errors.birthday = ''
  errors.phone = ''
  errors.address = ''

  // 驗證姓名
  if (!form.name || !form.name.trim()) {
    errors.name = '請輸入姓名'
    isValid = false
  }

  // 驗證生日
  if (!form.birthday) {
    errors.birthday = '請選擇生日'
    isValid = false
  }

  // 驗證手機
  if (!form.phone) {
    errors.phone = '請輸入手機號碼'
    isValid = false
  } else if (!/^09\d{8}$/.test(form.phone)) {
    errors.phone = '請輸入有效的手機號碼（09開頭共10碼）'
    isValid = false
  }

  // 驗證地址（縣市 + 區域 + 詳細地址）
  if (!form.city || !form.district || !form.address) {
    errors.address = '請填寫完整收件地址（縣市、區域及詳細地址）'
    isValid = false
  }

  return isValid
}

// 切換通知設定
const toggleNotification = (key, label) => {
  notifications[key] = !notifications[key]
  const status = notifications[key] ? '開啟' : '關閉'
  const type = notifications[key] ? 'success' : 'error'
  store.showToast(`${label}通知已${status}`, type)
}

// 儲存設定
const saveSettings = () => {
  if (!validateForm()) {
    store.showToast('請填寫所有必填欄位', 'error')
    return
  }

  // 組合完整地址
  const fullAddress = `${form.city}${form.district}${form.address}`

  // 更新 store.currentUser
  if (store.currentUser) {
    store.currentUser.name = form.name
    store.currentUser.birthday = form.birthday
    store.currentUser.phone = form.phone
    store.currentUser.address = fullAddress
  }

  store.showToast('設定已儲存', 'success')
}
</script>
