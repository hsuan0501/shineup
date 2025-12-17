<template>
    <!-- Modal Overlay -->
    <Transition name="modal">
        <div v-if="modelValue" @click.self="closeModal"
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
                    <!-- Login Form -->
                    <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="space-y-4">
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

                        <button type="submit" :disabled="isLoading"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98]">
                            登入
                        </button>

                    </form>



                    <!-- Register Form -->
                    <form v-if="activeTab === 'register'" @submit.prevent="handleRegister" class="space-y-4">
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
import { ref, watch } from 'vue'
import { useStore } from '../../store/app'
import ForgotPasswordModal from './ForgotPasswordModal.vue'
import axios from 'axios'

const props = defineProps({
    modelValue: Boolean
})

const emit = defineEmits(['update:modelValue'])

const store = useStore()
const activeTab = ref('login')
const isLoading = ref(false)

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

// 當 modal 打開時，取得驗證碼
watch(() => props.modelValue, (newVal) => {
    if (newVal) {
        refreshCaptcha()
    }
})

// 密碼顯示狀態
const showLoginPassword = ref(false)
const showRegisterPassword = ref(false)
const showConfirmPassword = ref(false)

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
    }, 300)
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
        const result = await store.register({
            name: registerForm.value.name,
            email: registerForm.value.email,
            password: registerForm.value.password
        })

        if (result.success) {
            store.showToast('註冊成功！', 'success')
            closeModal()
        } else {
            store.showToast(result.message || '註冊失敗', 'error')
        }
    } catch (error) {
        store.showToast('註冊失敗，請稍後再試', 'error')
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
