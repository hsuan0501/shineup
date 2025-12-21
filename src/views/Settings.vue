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

      <!-- 帳號連結 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.828 10.172a4 4 0 00-5.656 0l-4 4a4 4 0 105.656 5.656l1.102-1.101m-.758-4.899a4 4 0 005.656 0l4-4a4 4 0 00-5.656-5.656l-1.1 1.1" />
          </svg>
          帳號連結
        </h3>

        <div class="space-y-4">
          <!-- LINE 綁定 -->
          <div class="flex items-center justify-between py-3 px-4 rounded-xl bg-gray-50 dark:bg-gray-800/50">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-[#06C755] flex items-center justify-center">
                <svg class="w-6 h-6 text-white" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M19.365 9.863c.349 0 .63.285.63.631 0 .345-.281.63-.63.63H17.61v1.125h1.755c.349 0 .63.283.63.63 0 .344-.281.629-.63.629h-2.386c-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63h2.386c.349 0 .63.285.63.63 0 .349-.281.63-.63.63H17.61v1.125h1.755zm-3.855 3.016c0 .27-.174.51-.432.596-.064.021-.133.031-.199.031-.211 0-.391-.09-.51-.25l-2.443-3.317v2.94c0 .344-.279.629-.631.629-.346 0-.626-.285-.626-.629V8.108c0-.27.173-.51.43-.595.06-.023.136-.033.194-.033.195 0 .375.104.495.254l2.462 3.33V8.108c0-.345.282-.63.63-.63.345 0 .63.285.63.63v4.771zm-5.741 0c0 .344-.282.629-.631.629-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63.349 0 .631.285.631.63v4.771zm-2.466.629H4.917c-.345 0-.63-.285-.63-.629V8.108c0-.345.285-.63.63-.63.348 0 .63.285.63.63v4.141h1.756c.348 0 .629.283.629.63 0 .344-.281.629-.629.629M24 10.314C24 4.943 18.615.572 12 .572S0 4.943 0 10.314c0 4.811 4.27 8.842 10.035 9.608.391.082.923.258 1.058.59.12.301.079.766.038 1.08l-.164 1.02c-.045.301-.24 1.186 1.049.645 1.291-.539 6.916-4.078 9.436-6.975C23.176 14.393 24 12.458 24 10.314"/>
                </svg>
              </div>
              <div>
                <p class="text-sm font-medium text-gray-900 dark:text-white">LINE</p>
                <p class="text-xs text-gray-500 dark:text-gray-400">
                  {{ isLineBound ? '已綁定' : '綁定後可使用 LINE 快速登入' }}
                </p>
              </div>
            </div>
            <button v-if="!isLineBound" @click="handleBindLine"
              class="px-4 py-2 rounded-full bg-[#06C755] text-white text-sm font-medium hover:bg-[#05b34c] transition-all hover:scale-105 active:scale-95">
              綁定
            </button>
            <button v-else @click="handleUnbindLine"
              class="px-4 py-2 rounded-full bg-gray-200 dark:bg-gray-600 text-gray-600 dark:text-gray-300 text-sm font-medium hover:bg-gray-300 dark:hover:bg-gray-500 transition-all">
              解除綁定
            </button>
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
import { useStore } from '@/store'
import axios from 'axios'

const store = useStore()

// LINE 綁定狀態
const isLineBound = computed(() => !!store.currentUser?.lineId)

// 錯誤訊息
const errors = reactive({
  name: '',
  birthday: '',
  phone: '',
  address: ''
})

// 默認值
const defaults = {
  birthday: '2025-09-01',
  phone: '0912345678',
  city: '台北市',
  district: '中山區',
  address: '民生東路三段67號'
}

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
    form.birthday = store.currentUser.birthday || defaults.birthday
    form.phone = store.currentUser.phone || defaults.phone
    // 地址解析或使用默認值
    if (store.currentUser.address) {
      // 嘗試解析地址（縣市+區域+詳細地址）
      const addr = store.currentUser.address
      const cityMatch = cities.find(c => addr.startsWith(c))
      if (cityMatch) {
        form.city = cityMatch
        const rest = addr.substring(cityMatch.length)
        // 嘗試解析區域（找到第一個「區」、「市」、「鄉」、「鎮」）
        const districtMatch = rest.match(/^(.+?[區市鄉鎮])/)
        if (districtMatch) {
          form.district = districtMatch[1]
          form.address = rest.substring(districtMatch[1].length)
        } else {
          form.address = rest
        }
      } else {
        form.address = addr
      }
    } else {
      // 使用默認值
      form.city = defaults.city
      form.district = defaults.district
      form.address = defaults.address
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

// LINE 綁定
const handleBindLine = async () => {
  try {
    // 導向 LINE 授權頁面，帶上 bind 參數表示是綁定操作
    const response = await axios.get('http://localhost:8080/api/auth/line/auth-url')
    if (response.data.url) {
      // 在 URL 加上 state 參數標記這是綁定操作
      const bindUrl = response.data.url.replace('state=shineup', 'state=bind')
      window.location.href = bindUrl
    }
  } catch (error) {
    console.error('LINE bind error:', error)
    store.showToast('LINE 綁定失敗，請稍後再試', 'error')
  }
}

// LINE 解除綁定
const handleUnbindLine = async () => {
  if (!confirm('確定要解除 LINE 綁定嗎？')) return

  try {
    const token = localStorage.getItem('token')
    const response = await axios.post('http://localhost:8080/api/auth/line/unbind', {}, {
      headers: { Authorization: `Bearer ${token}` }
    })

    if (response.data.success) {
      store.currentUser.lineId = null
      store.showToast('已解除 LINE 綁定', 'success')
    } else {
      store.showToast(response.data.message || '解除綁定失敗', 'error')
    }
  } catch (error) {
    console.error('LINE unbind error:', error)
    store.showToast('解除綁定失敗，請稍後再試', 'error')
  }
}
</script>
