<template>
    <!-- Modal Overlay -->
    <Transition name="modal">
        <div v-if="modelValue" @click.self="handleOverlayClick"
            class="fixed inset-0 z-50 flex items-center justify-center bg-black/30 backdrop-blur-xl backdrop-saturate-150 px-4">
            <!-- Modal Card -->
            <div
                class="relative w-full max-w-md bg-white dark:bg-gray-800 rounded-2xl shadow-2xl overflow-hidden transform transition-all">
                <!-- Close Button -->
                <button @click="closeModal"
                    class="absolute top-4 right-4 w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors z-10">
                    <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none" stroke="currentColor"
                        viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                    </svg>
                </button>

                <!-- Header with Tabs -->
                <div class="bg-gradient-to-r from-cyan-500 to-blue-500 p-6 pb-0">
                    <!-- Icon and Title in same row - Left Aligned -->
                    <div class="flex items-center gap-3 mb-4">
                        <div class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center">
                            <svg class="w-6 h-6" viewBox="0 0 24 24">
                                <path d="M12 0 L14 10 L24 12 L14 14 L12 24 L10 14 L0 12 L10 10 Z" fill="white" />
                            </svg>
                        </div>
                        <h2 class="text-2xl font-bold text-white">
                            開始您的旅程
                        </h2>
                    </div>
                    <!-- Left Aligned Tab Buttons -->
                    <div class="flex gap-8 border-b border-white/20">
                        <button @click="activeTab = 'login'" :class="[
                            'pb-3 px-4 text-sm font-semibold transition-all relative',
                            activeTab === 'login' ? 'text-white' : 'text-white/60 hover:text-white/80'
                        ]">
                            登入
                            <div v-if="activeTab === 'login'"
                                class="absolute bottom-0 left-0 w-full h-0.5 bg-white rounded-t-full"></div>
                        </button>
                        <button @click="activeTab = 'register'" :class="[
                            'pb-3 px-4 text-sm font-semibold transition-all relative',
                            activeTab === 'register' ? 'text-white' : 'text-white/60 hover:text-white/80'
                        ]">
                            註冊
                            <div v-if="activeTab === 'register'"
                                class="absolute bottom-0 left-0 w-full h-0.5 bg-white rounded-t-full"></div>
                        </button>
                    </div>
                </div>

                <!-- Form Content -->
                <div class="p-6">
                    <!-- Email Verification Form (註冊後顯示) -->
                    <div v-if="showVerification" class="space-y-4">
                        <div class="text-center mb-4">
                            <div class="w-16 h-16 mx-auto mb-3 bg-gradient-to-br from-cyan-100 to-blue-100 dark:from-cyan-900/30 dark:to-blue-900/30 rounded-full flex items-center justify-center">
                                <svg class="w-8 h-8 text-cyan-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                </svg>
                            </div>
                            <h3 class="text-lg font-bold text-gray-800 dark:text-white">驗證您的信箱</h3>
                            <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">
                                驗證碼已發送至 <span class="font-medium text-cyan-500">{{ pendingUser?.email }}</span>
                            </p>
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                6 位數驗證碼
                            </label>
                            <input v-model="verificationCode" type="text" required maxlength="6"
                                class="w-full px-4 py-3 text-center text-2xl font-bold tracking-[0.5em] border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="000000">
                        </div>

                        <button @click="handleVerifyEmail" :disabled="isLoading || verificationCode.length !== 6"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98] disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none">
                            {{ isLoading ? '驗證中...' : '驗證信箱' }}
                        </button>

                        <div class="flex items-center justify-between text-sm">
                            <button type="button" @click="handleResendCode" :disabled="resendCooldown > 0"
                                class="text-cyan-500 hover:text-cyan-600 font-medium disabled:text-gray-400 disabled:cursor-not-allowed">
                                {{ resendCooldown > 0 ? `${resendCooldown} 秒後可重新發送` : '重新發送驗證碼' }}
                            </button>
                            <button type="button" @click="cancelVerification"
                                class="text-gray-500 hover:text-gray-600 dark:text-gray-400 dark:hover:text-gray-300">
                                返回
                            </button>
                        </div>
                    </div>

                    <!-- Login Form -->
                    <form v-else-if="activeTab === 'login'" @submit.prevent="handleLogin" class="space-y-4">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                電子信箱
                            </label>
                            <input v-model="loginForm.email" type="email" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="example@email.com">
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                密碼
                            </label>
                            <div class="relative">
                                <input v-model="loginForm.password" :type="showLoginPassword ? 'text' : 'password'" required
                                    class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                    placeholder="請輸入密碼">
                                <button type="button" @click="showLoginPassword = !showLoginPassword"
                                    class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
                                    <!-- Eye Open -->
                                    <svg v-if="!showLoginPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                                    </svg>
                                    <!-- Eye Closed -->
                                    <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
                                    </svg>
                                </button>
                            </div>
                        </div>

                        <div class="flex items-center justify-between text-sm">
                            <label class="flex items-center cursor-pointer">
                                <input v-model="loginForm.rememberMe" type="checkbox"
                                    class="w-4 h-4 rounded border-gray-300 text-cyan-500 focus:ring-cyan-500">
                                <span class="ml-2 text-gray-600 dark:text-gray-400">記住我</span>
                            </label>
                            <button type="button" @click="openForgotPassword" class="text-cyan-500 hover:text-cyan-600 font-medium">忘記密碼？</button>
                        </div>

                        <!-- 圖形驗證碼 -->
                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                驗證碼
                            </label>
                            <div class="flex gap-2 items-center">
                                <input v-model="captchaCode" type="text" required maxlength="5"
                                    class="flex-1 px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all uppercase"
                                    placeholder="請輸入驗證碼">
                                <img v-if="captchaImage" :src="captchaImage" @click="refreshCaptcha"
                                    class="h-10 rounded cursor-pointer border border-gray-300 dark:border-gray-600 hover:opacity-80 transition-opacity"
                                    title="點擊更換驗證碼" alt="驗證碼">
                                <div v-else class="h-10 w-[120px] bg-gray-200 dark:bg-gray-600 rounded animate-pulse"></div>
                            </div>
                            <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">點擊圖片可更換驗證碼</p>
                        </div>

                        <!-- 登入按鈕：LINE 在左，一般登入在右 -->
                        <div class="flex gap-3">
                            <button type="button" @click="handleLineLogin" :disabled="isLoading"
                                class="flex-1 py-3 bg-[#06C755] text-white font-semibold rounded-lg hover:bg-[#05b34c] transition-all duration-300 hover:scale-[1.02] active:scale-[0.98] flex items-center justify-center gap-2">
                                <svg class="w-5 h-5" viewBox="0 0 24 24" fill="currentColor">
                                    <path d="M19.365 9.863c.349 0 .63.285.63.631 0 .345-.281.63-.63.63H17.61v1.125h1.755c.349 0 .63.283.63.63 0 .344-.281.629-.63.629h-2.386c-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63h2.386c.349 0 .63.285.63.63 0 .349-.281.63-.63.63H17.61v1.125h1.755zm-3.855 3.016c0 .27-.174.51-.432.596-.064.021-.133.031-.199.031-.211 0-.391-.09-.51-.25l-2.443-3.317v2.94c0 .344-.279.629-.631.629-.346 0-.626-.285-.626-.629V8.108c0-.27.173-.51.43-.595.06-.023.136-.033.194-.033.195 0 .375.104.495.254l2.462 3.33V8.108c0-.345.282-.63.63-.63.345 0 .63.285.63.63v4.771zm-5.741 0c0 .344-.282.629-.631.629-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63.349 0 .631.285.631.63v4.771zm-2.466.629H4.917c-.345 0-.63-.285-.63-.629V8.108c0-.345.285-.63.63-.63.348 0 .63.285.63.63v4.141h1.756c.348 0 .629.283.629.63 0 .344-.281.629-.629.629M24 10.314C24 4.943 18.615.572 12 .572S0 4.943 0 10.314c0 4.811 4.27 8.842 10.035 9.608.391.082.923.258 1.058.59.12.301.079.766.038 1.08l-.164 1.02c-.045.301-.24 1.186 1.049.645 1.291-.539 6.916-4.078 9.436-6.975C23.176 14.393 24 12.458 24 10.314"/>
                                </svg>
                                LINE
                            </button>
                            <button type="submit" :disabled="isLoading"
                                class="flex-1 py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98]">
                                登入
                            </button>
                        </div>

                    </form>



                    <!-- Register Form -->
                    <form v-else-if="activeTab === 'register' && !showVerification" @submit.prevent="handleRegister" class="space-y-4">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                姓名
                            </label>
                            <input v-model="registerForm.name" type="text" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="請輸入姓名">
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                電子信箱
                            </label>
                            <input v-model="registerForm.email" type="email" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="example@email.com">
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                密碼
                            </label>
                            <div class="relative">
                                <input v-model="registerForm.password" :type="showRegisterPassword ? 'text' : 'password'" required
                                    class="w-full px-4 py-2.5 pr-10 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                    placeholder="至少 6 個字元">
                                <button type="button" @click="showRegisterPassword = !showRegisterPassword"
                                    class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
                                    <svg v-if="!showRegisterPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
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
                                確認密碼
                            </label>
                            <div class="relative">
                                <input v-model="registerForm.confirmPassword" :type="showConfirmPassword ? 'text' : 'password'" required
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

                        <!-- 推薦碼欄位（可選） -->
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

                        <button type="submit"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98]">
                            註冊
                        </button>

                        <p class="text-xs text-center text-gray-500 dark:text-gray-400 mt-4">
                            註冊即表示您同意我們的服務條款和隱私政策
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </Transition>

    <!-- 忘記密碼 Modal -->
    <ForgotPasswordModal v-model="showForgotPassword" />
</template>

<script setup>
import { ref, watch, onUnmounted, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from '@/store'
import ForgotPasswordModal from './ForgotPasswordModal.vue'
import axios from 'axios'

const props = defineProps({
    modelValue: Boolean
})

const emit = defineEmits(['update:modelValue'])

const router = useRouter()
const route = useRoute()
const store = useStore()
const activeTab = ref('login')
const isLoading = ref(false)

// 推薦碼（從 URL 讀取）
const referralCode = ref('')

// 讀取 URL 中的推薦碼參數
onMounted(() => {
    const refCode = route.query.ref
    if (refCode) {
        referralCode.value = refCode
        activeTab.value = 'register'  // 自動切換到註冊頁
    }
})

// 信箱驗證相關
const showVerification = ref(false)
const verificationCode = ref('')
const pendingUser = ref(null)
const resendCooldown = ref(0)
let cooldownTimer = null

// 圖形驗證碼
const captchaId = ref('')
const captchaImage = ref('')
const captchaCode = ref('')

// 取得驗證碼
const refreshCaptcha = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/captcha/generate')
        captchaId.value = response.data.captchaId
        captchaImage.value = response.data.image
        captchaCode.value = ''
    } catch (error) {
        console.error('Failed to get captcha:', error)
    }
}

// 當 modal 打開時，取得驗證碼並檢查推薦碼
watch(() => props.modelValue, (newVal) => {
    if (newVal) {
        refreshCaptcha()
        // 檢查 URL 中的推薦碼參數
        const refCode = route.query.ref
        if (refCode) {
            referralCode.value = refCode
            activeTab.value = 'register'  // 自動切換到註冊頁
        }
    }
})

// 密碼顯示狀態
const showLoginPassword = ref(false)
const showRegisterPassword = ref(false)
const showConfirmPassword = ref(false)

// 點擊外面時的處理 - 有填寫資料時不關閉
const handleOverlayClick = () => {
    // 如果正在驗證信箱，不允許點外面關閉
    if (showVerification.value) return

    // 如果是登入頁且有填寫資料，不允許點外面關閉
    if (activeTab.value === 'login') {
        const hasData = loginForm.value.email || loginForm.value.password
        if (hasData) return
    }

    // 如果是註冊頁且有填寫資料，不允許點外面關閉
    if (activeTab.value === 'register') {
        const hasData = registerForm.value.name ||
                        registerForm.value.email ||
                        registerForm.value.password ||
                        registerForm.value.confirmPassword
        if (hasData) return
    }

    // 其他情況可以關閉
    closeModal()
}

// 忘記密碼 Modal
const showForgotPassword = ref(false)
const openForgotPassword = () => {
    emit('update:modelValue', false)  // 先關閉登入 Modal
    setTimeout(() => {
        showForgotPassword.value = true  // 再打開忘記密碼 Modal
    }, 200)
}

// Login Form
const loginForm = ref({
    email: '',
    password: '',
    rememberMe: false
})

// Register Form
const registerForm = ref({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
})

// Close Modal
const closeModal = () => {
    emit('update:modelValue', false)
    // Reset forms
    setTimeout(() => {
        activeTab.value = 'login'
        loginForm.value = { email: '', password: '', rememberMe: false }
        registerForm.value = { name: '', email: '', password: '', confirmPassword: '' }
        showLoginPassword.value = false
        showRegisterPassword.value = false
        showConfirmPassword.value = false
        captchaCode.value = ''
        // 重置驗證相關狀態
        showVerification.value = false
        verificationCode.value = ''
        pendingUser.value = null
        resendCooldown.value = 0
        if (cooldownTimer) {
            clearInterval(cooldownTimer)
            cooldownTimer = null
        }
    }, 300)
}

// 取消驗證，返回註冊頁
const cancelVerification = () => {
    showVerification.value = false
    verificationCode.value = ''
    pendingUser.value = null
    resendCooldown.value = 0
    if (cooldownTimer) {
        clearInterval(cooldownTimer)
        cooldownTimer = null
    }
}

// 開始重發冷卻倒數
const startResendCooldown = () => {
    resendCooldown.value = 60
    cooldownTimer = setInterval(() => {
        resendCooldown.value--
        if (resendCooldown.value <= 0) {
            clearInterval(cooldownTimer)
            cooldownTimer = null
        }
    }, 1000)
}

// 驗證信箱
const handleVerifyEmail = async () => {
    if (!verificationCode.value || verificationCode.value.length !== 6) {
        store.showToast('請輸入 6 位數驗證碼', 'error')
        return
    }

    isLoading.value = true
    try {
        const response = await axios.post('http://localhost:8080/api/auth/verify-email', {
            userId: pendingUser.value.id.toString(),
            code: verificationCode.value
        })

        if (response.data.success) {
            // 驗證成功，儲存 token 並登入
            store.showToast('驗證成功！歡迎加入 ShineUp', 'success')

            // 更新 store 狀態（使用正確的屬性名稱）
            const user = response.data.user
            localStorage.setItem('token', response.data.token)
            localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
            store.currentUser = user
            store.isAuthenticated = true
            store.currentLevel = user.level || 'EXPLORER'
            store.userPoints = {
                upgradePoints: user.upgradePoints || 0,
                rewardPoints: user.rewardPoints || 0
            }

            closeModal()
        } else {
            store.showToast(response.data.message || '驗證失敗', 'error')
        }
    } catch (error) {
        console.error('Verification error:', error)
        store.showToast(error.response?.data?.message || '驗證失敗，請稍後再試', 'error')
    } finally {
        isLoading.value = false
    }
}

// 重新發送驗證碼
const handleResendCode = async () => {
    if (resendCooldown.value > 0) return

    isLoading.value = true
    try {
        const response = await axios.post('http://localhost:8080/api/auth/resend-verification', {
            userId: pendingUser.value.id.toString()
        })

        if (response.data.success) {
            store.showToast('驗證碼已重新發送', 'success')
            startResendCooldown()
        } else {
            store.showToast(response.data.message || '發送失敗', 'error')
        }
    } catch (error) {
        console.error('Resend error:', error)
        store.showToast('發送失敗，請稍後再試', 'error')
    } finally {
        isLoading.value = false
    }
}

// 清理計時器
onUnmounted(() => {
    if (cooldownTimer) {
        clearInterval(cooldownTimer)
    }
})

// Handle LINE Login - 導向 LINE 授權頁面
const handleLineLogin = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/auth/line/auth-url')
        if (response.data.url) {
            window.location.href = response.data.url
        }
    } catch (error) {
        console.error('LINE login error:', error)
        store.showToast('LINE 登入失敗，請稍後再試', 'error')
    }
}

// Handle Login - 串接後端 API
const handleLogin = async () => {
    // 檢查驗證碼
    if (!captchaCode.value) {
        store.showToast('請輸入驗證碼', 'error')
        return
    }

    isLoading.value = true
    try {
        // 先驗證圖形驗證碼
        const captchaResponse = await axios.post('http://localhost:8080/api/captcha/verify', {
            captchaId: captchaId.value,
            captchaCode: captchaCode.value
        })

        if (!captchaResponse.data.valid) {
            store.showToast('驗證碼錯誤', 'error')
            refreshCaptcha()
            return
        }

        const result = await store.login({
            email: loginForm.value.email,
            password: loginForm.value.password
        })

        if (result.success) {
            store.showToast('登入成功！', 'success')
            closeModal()
            // 管理員自動跳轉到管理頁面
            if (result.user?.admin) {
                router.push('/admin')
            }
        } else {
            store.showToast(result.message || '登入失敗', 'error')
            refreshCaptcha()
        }
    } catch (error) {
        console.error('Login error:', error)
        store.showToast('登入失敗，請稍後再試', 'error')
        refreshCaptcha()
    } finally {
        isLoading.value = false
    }
}

// Handle Register - 串接後端 API
const handleRegister = async () => {
    // 驗證密碼
    if (registerForm.value.password !== registerForm.value.confirmPassword) {
        store.showToast('密碼不一致，請重新輸入', 'error')
        return
    }

    if (registerForm.value.password.length < 6) {
        store.showToast('密碼至少需要 6 個字元', 'error')
        return
    }

    isLoading.value = true
    try {
        // 直接呼叫 API，不經過 store
        const requestData = {
            name: registerForm.value.name,
            email: registerForm.value.email,
            password: registerForm.value.password
        }
        // 如果有推薦碼，加入請求
        if (referralCode.value) {
            requestData.referralCode = referralCode.value
        }
        const response = await axios.post('http://localhost:8080/api/auth/register', requestData)

        if (response.data.success) {
            // 註冊成功，顯示驗證碼輸入畫面
            store.showToast('驗證碼已發送至您的信箱', 'success')
            pendingUser.value = response.data.user
            showVerification.value = true
            startResendCooldown()
        } else {
            store.showToast(response.data.message || '註冊失敗', 'error')
        }
    } catch (error) {
        console.error('Register error:', error)
        store.showToast(error.response?.data?.message || '註冊失敗，請稍後再試', 'error')
    } finally {
        isLoading.value = false
    }
}
</script>

<style scoped>
/* Modal Transition */
.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
    opacity: 0;
}

.modal-enter-active .relative,
.modal-leave-active .relative {
    transition: transform 0.3s ease;
}

.modal-enter-from .relative,
.modal-leave-to .relative {
    transform: scale(0.9);
}
</style>
