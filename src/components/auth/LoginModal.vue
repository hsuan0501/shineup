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
                            <input v-model="loginForm.password" type="password" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="請輸入密碼">
                        </div>

                        <div class="flex items-center justify-between text-sm">
                            <label class="flex items-center cursor-pointer">
                                <input v-model="loginForm.rememberMe" type="checkbox"
                                    class="w-4 h-4 rounded border-gray-300 text-cyan-500 focus:ring-cyan-500">
                                <span class="ml-2 text-gray-600 dark:text-gray-400">記住我</span>
                            </label>
                            <a href="#" class="text-cyan-500 hover:text-cyan-600 font-medium">忘記密碼？</a>
                        </div>

                        <button type="submit"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98]">
                            登入
                        </button>

                        <!-- Demo Hint -->
                        <div class="mt-4 p-3 bg-blue-50 dark:bg-blue-900/20 rounded-lg border border-blue-200 dark:border-blue-800">
                            <p class="text-xs text-blue-600 dark:text-blue-400">
                                <strong>測試帳號：</strong>matcha@example.com<br>
                                <strong>測試密碼：</strong>20001108
                            </p>
                        </div>
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
                            <input v-model="registerForm.password" type="password" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="至少 6 個字元">
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                確認密碼
                            </label>
                            <input v-model="registerForm.confirmPassword" type="password" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="再次輸入密碼">
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
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from '../../store/app'

const props = defineProps({
    modelValue: Boolean
})

const emit = defineEmits(['update:modelValue'])

const store = useStore()
const activeTab = ref('login')
const isLoading = ref(false)

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
    }, 300)
}

// Handle Login - 串接後端 API
const handleLogin = async () => {
    isLoading.value = true
    try {
        const result = await store.login({
            email: loginForm.value.email,
            password: loginForm.value.password
        })

        if (result.success) {
            store.showToast('登入成功！', 'success')
            closeModal()
        } else {
            store.showToast(result.message || '登入失敗', 'error')
        }
    } catch (error) {
        store.showToast('登入失敗，請稍後再試', 'error')
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
