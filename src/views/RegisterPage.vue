<template>
  <div class="min-h-screen bg-gradient-to-br from-cyan-50 via-white to-purple-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 flex items-center justify-center p-4">
    <!-- 背景裝飾 -->
    <div class="absolute inset-0 overflow-hidden pointer-events-none">
      <div class="absolute -top-40 -right-40 w-80 h-80 bg-cyan-200/30 dark:bg-cyan-900/20 rounded-full blur-3xl"></div>
      <div class="absolute -bottom-40 -left-40 w-80 h-80 bg-purple-200/30 dark:bg-purple-900/20 rounded-full blur-3xl"></div>
    </div>

    <!-- 註冊卡片 -->
    <div class="relative w-full max-w-md">
      <!-- Logo -->
      <div class="text-center mb-8">
        <router-link to="/" class="inline-flex items-center gap-2">
          <img src="/images/logo.png" alt="ShineUp" class="h-12">
        </router-link>
        <h1 class="mt-4 text-2xl font-bold text-gray-800 dark:text-white">加入 ShineUp</h1>
        <p class="mt-2 text-gray-600 dark:text-gray-400">建立帳號，開始您的理財之旅</p>
      </div>

      <!-- 註冊表單 -->
      <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-xl p-8">
        <form @submit.prevent="handleRegister" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">姓名</label>
            <input v-model="form.name" type="text" required
              class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
              placeholder="請輸入姓名">
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">電子信箱</label>
            <input v-model="form.email" type="email" required
              class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
              placeholder="example@email.com">
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">密碼</label>
            <div class="relative">
              <input v-model="form.password" :type="showPassword ? 'text' : 'password'" required
                class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                placeholder="至少 6 個字元">
              <button type="button" @click="showPassword = !showPassword"
                class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
                <svg v-if="!showPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                </svg>
                <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
                </svg>
              </button>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">確認密碼</label>
            <div class="relative">
              <input v-model="form.confirmPassword" :type="showConfirmPassword ? 'text' : 'password'" required
                class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                placeholder="再次輸入密碼">
              <button type="button" @click="showConfirmPassword = !showConfirmPassword"
                class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
                <svg v-if="!showConfirmPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                </svg>
                <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
                </svg>
              </button>
            </div>
          </div>

          <!-- 推薦碼欄位 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              推薦碼 <span class="text-gray-400 font-normal">(選填)</span>
            </label>
            <input v-model="referralCode" type="text" maxlength="8"
              class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all uppercase"
              :class="{ 'bg-green-50 dark:bg-green-900/20 border-green-300 dark:border-green-600': referralCode }"
              placeholder="輸入朋友的推薦碼">
            <p v-if="referralCode" class="text-xs text-green-600 dark:text-green-400 mt-1">
              ✓ 已套用推薦碼，註冊後您的朋友將獲得獎勵
            </p>
          </div>

          <button type="submit" :disabled="isLoading"
            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98] disabled:opacity-50 disabled:cursor-not-allowed">
            {{ isLoading ? '註冊中...' : '註冊' }}
          </button>
        </form>

        <p class="text-xs text-center text-gray-500 dark:text-gray-400 mt-4">
          註冊即表示您同意我們的服務條款和隱私政策
        </p>

        <div class="mt-6 text-center">
          <p class="text-sm text-gray-600 dark:text-gray-400">
            已有帳號？
            <router-link to="/" class="text-cyan-600 hover:text-cyan-700 dark:text-cyan-400 font-medium">
              返回登入
            </router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from '@/store'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const store = useStore()

const form = ref({
  name: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const referralCode = ref('')
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)

// 從 URL 讀取推薦碼
onMounted(() => {
  const refCode = route.query.ref
  if (refCode) {
    referralCode.value = refCode
  }
})

const handleRegister = async () => {
  // 驗證密碼
  if (form.value.password !== form.value.confirmPassword) {
    store.showToast('兩次輸入的密碼不一致', 'error')
    return
  }

  if (form.value.password.length < 6) {
    store.showToast('密碼至少需要 6 個字元', 'error')
    return
  }

  isLoading.value = true

  try {
    const requestData = {
      name: form.value.name,
      email: form.value.email,
      password: form.value.password
    }

    if (referralCode.value) {
      requestData.referralCode = referralCode.value
    }

    const response = await axios.post('http://localhost:8080/api/auth/register', requestData)

    if (response.data.success) {
      store.showToast('註冊成功！請查收驗證碼信件', 'success')
      // 導向首頁，讓用戶登入
      router.push('/')
    } else {
      store.showToast(response.data.message || '註冊失敗', 'error')
    }
  } catch (error) {
    console.error('Registration error:', error)
    store.showToast(error.response?.data?.message || '註冊失敗，請稍後再試', 'error')
  } finally {
    isLoading.value = false
  }
}
</script>
