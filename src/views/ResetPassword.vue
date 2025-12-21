<template>
    <!-- 背景圖片 -->
    <div class="fixed inset-0 bg-gradient-to-br from-cyan-100 via-blue-50 to-purple-100"></div>

    <!-- Modal Overlay -->
    <div class="fixed inset-0 z-50 flex items-center justify-center bg-black/30 backdrop-blur-sm px-4">
        <div class="w-full max-w-md bg-white dark:bg-gray-800 rounded-2xl shadow-2xl overflow-hidden">
            <!-- Header -->
            <div class="bg-gradient-to-r from-cyan-500 to-blue-500 p-6">
                <div class="flex items-center gap-3">
                    <div class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center">
                        <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z" />
                        </svg>
                    </div>
                    <h2 class="text-2xl font-bold text-white">重設密碼</h2>
                </div>
            </div>

            <!-- Content -->
            <div class="p-6">
                <!-- Loading -->
                <div v-if="isValidating" class="text-center py-8">
                    <div class="w-12 h-12 border-4 border-cyan-500 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
                    <p class="text-gray-500">驗證中...</p>
                </div>

                <!-- Token Invalid -->
                <div v-else-if="!isValidToken" class="text-center py-8">
                    <div class="w-16 h-16 mx-auto bg-red-100 rounded-full flex items-center justify-center mb-4">
                        <svg class="w-8 h-8 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                        </svg>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">連結無效</h3>
                    <p class="text-gray-600 dark:text-gray-400 text-sm mb-6">
                        此重設連結已過期或已使用，請重新申請。
                    </p>
                    <router-link to="/"
                        class="inline-block px-6 py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90">
                        返回首頁
                    </router-link>
                </div>

                <!-- Reset Form -->
                <form v-else-if="!isSuccess" @submit.prevent="handleResetPassword" class="space-y-4">
                    <p class="text-gray-600 dark:text-gray-400 text-sm mb-4">
                        請輸入您的新密碼。
                    </p>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                            新密碼
                        </label>
                        <div class="relative">
                            <input v-model="newPassword" :type="showPassword ? 'text' : 'password'" required
                                class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="至少 6 個字元">
                            <button type="button" @click="showPassword = !showPassword"
                                class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600">
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
                        <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                            確認新密碼
                        </label>
                        <div class="relative">
                            <input v-model="confirmPassword" :type="showConfirmPassword ? 'text' : 'password'" required
                                class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="再次輸入新密碼">
                            <button type="button" @click="showConfirmPassword = !showConfirmPassword"
                                class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600">
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

                    <button type="submit" :disabled="isLoading"
                        class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98] disabled:opacity-50">
                        {{ isLoading ? '處理中...' : '重設密碼' }}
                    </button>
                </form>

                <!-- Success -->
                <div v-else class="text-center py-8">
                    <div class="w-16 h-16 mx-auto bg-green-100 rounded-full flex items-center justify-center mb-4">
                        <svg class="w-8 h-8 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">密碼重設成功</h3>
                    <p class="text-gray-600 dark:text-gray-400 text-sm mb-6">
                        請使用新密碼登入您的帳號。
                    </p>
                    <router-link to="/"
                        class="inline-block px-6 py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90">
                        返回首頁登入
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from '@/store'
import api from '../api'

const route = useRoute()
const store = useStore()

const token = ref('')
const isValidating = ref(true)
const isValidToken = ref(false)
const isLoading = ref(false)
const isSuccess = ref(false)

const newPassword = ref('')
const confirmPassword = ref('')
const showPassword = ref(false)
const showConfirmPassword = ref(false)

onMounted(async () => {
    token.value = route.query.token || ''

    if (!token.value) {
        isValidating.value = false
        isValidToken.value = false
        return
    }

    // 驗證 Token
    try {
        const response = await api.get('/auth/validate-reset-token', {
            params: { token: token.value }
        })
        isValidToken.value = response.data.valid
    } catch (error) {
        isValidToken.value = false
    } finally {
        isValidating.value = false
    }
})

const handleResetPassword = async () => {
    if (newPassword.value.length < 6) {
        store.showToast('密碼至少需要 6 個字元', 'error')
        return
    }

    if (newPassword.value !== confirmPassword.value) {
        store.showToast('兩次密碼不一致', 'error')
        return
    }

    isLoading.value = true
    try {
        const response = await api.post('/auth/reset-password', {
            token: token.value,
            newPassword: newPassword.value
        })

        if (response.data.success) {
            isSuccess.value = true
            store.showToast('密碼重設成功', 'success')
        } else {
            store.showToast(response.data.message || '重設失敗', 'error')
        }
    } catch (error) {
        store.showToast(error.response?.data?.message || '發生錯誤', 'error')
    } finally {
        isLoading.value = false
    }
}
</script>
