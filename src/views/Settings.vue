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
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">姓名</label>
            <input type="text" v-model="form.name"
              class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
              placeholder="請輸入姓名">
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
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">生日</label>
            <input type="date" v-model="form.birthday"
              class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all">
            <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">生日資料僅限修改一次，用於生日禮發放</p>
          </div>

          <!-- 電話 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">手機號碼</label>
            <input type="tel" v-model="form.phone"
              class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
              placeholder="請輸入手機號碼">
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
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">縣市</label>
              <select v-model="form.city"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all">
                <option value="">請選擇</option>
                <option v-for="city in cities" :key="city" :value="city">{{ city }}</option>
              </select>
            </div>
            <!-- 區域 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">區域</label>
              <input type="text" v-model="form.district"
                class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
                placeholder="請輸入區域">
            </div>
          </div>

          <!-- 詳細地址 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">詳細地址</label>
            <input type="text" v-model="form.address"
              class="w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all"
              placeholder="請輸入詳細地址">
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
              <p class="text-sm font-medium text-gray-900 dark:text-white">任務提醒</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當有新任務或任務即將到期時通知</p>
            </div>
            <button @click="notifications.tasks = !notifications.tasks"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notifications.tasks
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notifications.tasks ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">積分變動</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當積分增加或使用時通知</p>
            </div>
            <button @click="notifications.points = !notifications.points"
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
              <p class="text-sm font-medium text-gray-900 dark:text-white">等級提升</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">當等級提升時通知</p>
            </div>
            <button @click="notifications.levelUp = !notifications.levelUp"
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
              <p class="text-sm font-medium text-gray-900 dark:text-white">優惠活動</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">接收限時優惠與活動通知</p>
            </div>
            <button @click="notifications.promotions = !notifications.promotions"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notifications.promotions
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notifications.promotions ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>
        </div>
      </div>

      <!-- 帳號安全 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-emerald-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
          </svg>
          帳號安全
        </h3>

        <div class="space-y-4">
          <div class="flex items-center justify-between py-3 border-b border-gray-100 dark:border-gray-600/30">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">修改密碼</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">建議定期更換密碼以確保帳號安全</p>
            </div>
            <button class="px-4 py-2 text-sm font-medium text-sky-600 dark:text-sky-400 hover:bg-sky-50 dark:hover:bg-sky-900/20 rounded-lg transition-colors">
              修改
            </button>
          </div>

          <div class="flex items-center justify-between py-3">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">刪除帳號</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">永久刪除您的帳號及所有資料</p>
            </div>
            <button class="px-4 py-2 text-sm font-medium text-red-600 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition-colors">
              刪除
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
import { ref, reactive } from 'vue'
import { useStore } from '../store/app.js'
import { mockUsers } from '../mock.js'

const store = useStore()

// 表單資料
const form = reactive({
  name: mockUsers[1]?.name || '',
  email: mockUsers[1]?.email || '',
  birthday: '',
  phone: '',
  city: '',
  district: '',
  address: ''
})

// 通知設定
const notifications = reactive({
  tasks: true,
  points: true,
  levelUp: true,
  promotions: false
})

// 台灣縣市
const cities = [
  '台北市', '新北市', '桃園市', '台中市', '台南市', '高雄市',
  '基隆市', '新竹市', '新竹縣', '苗栗縣', '彰化縣', '南投縣',
  '雲林縣', '嘉義市', '嘉義縣', '屏東縣', '宜蘭縣', '花蓮縣',
  '台東縣', '澎湖縣', '金門縣', '連江縣'
]

// 儲存設定
const saveSettings = () => {
  // 這裡可以呼叫 API 儲存設定
  store.showToast('設定已儲存', 'success')
}
</script>
