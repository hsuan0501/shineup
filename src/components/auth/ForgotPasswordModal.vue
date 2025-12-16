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

                <!-- Header -->
                <div class="bg-gradient-to-r from-cyan-500 to-blue-500 p-6">
                    <div class="flex items-center gap-3">
                        <div class="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center">
                            <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z" />
                            </svg>
                        </div>
                        <h2 class="text-2xl font-bold text-white">忘記密碼</h2>
                    </div>
                </div>

                <!-- Content -->
                <div class="p-6">
                    <!-- Step 1: 輸入 Email -->
                    <div v-if="step === 1" class="space-y-4">
                        <p class="text-gray-600 dark:text-gray-400 text-sm">
                            請輸入您的電子信箱，我們會產生重設密碼的連結。
                        </p>

                        <div>
                            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                                電子信箱
                            </label>
                            <input v-model="email" type="email" required
                                class="w-full px-4 py-2.5 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-cyan-500 focus:border-transparent bg-white dark:bg-gray-700 text-gray-900 dark:text-white transition-all"
                                placeholder="example@email.com"
                                @keyup.enter="handleForgotPassword">
                        </div>

                        <button @click="handleForgotPassword" :disabled="isLoading"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all duration-300 hover:scale-[1.02] active:scale-[0.98] disabled:opacity-50">
                            {{ isLoading ? '處理中...' : '發送重設連結' }}
                        </button>

                        <button @click="closeModal" class="w-full py-2 text-gray-500 hover:text-gray-700 text-sm">
                            返回登入
                        </button>
                    </div>

                    <!-- Step 2: 信件已寄出 -->
                    <div v-if="step === 2" class="space-y-4">
                        <div class="text-center">
                            <div class="w-16 h-16 mx-auto bg-green-100 rounded-full flex items-center justify-center mb-4">
                                <svg class="w-8 h-8 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                </svg>
                            </div>
                            <h3 class="text-lg font-semibold text-gray-900 dark:text-white mb-2">信件已寄出</h3>
                            <p class="text-gray-600 dark:text-gray-400 text-sm mb-2">
                                若該信箱已註冊，您將會收到重設密碼的連結。
                            </p>
                            <p class="text-gray-500 dark:text-gray-500 text-xs">
                                請檢查您的收件匣或垃圾郵件。
                            </p>
                        </div>

                        <button @click="closeModal"
                            class="w-full py-3 bg-gradient-to-r from-cyan-500 to-blue-500 text-white font-semibold rounded-lg hover:opacity-90 transition-all">
                            關閉
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </Transition>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from '../../store/app'
import api from '../../api'

const props = defineProps({
    modelValue: Boolean
})

const emit = defineEmits(['update:modelValue'])

const store = useStore()

const step = ref(1)
const email = ref('')
const isLoading = ref(false)

const closeModal = () => {
    emit('update:modelValue', false)
    // 重置狀態
    setTimeout(() => {
        step.value = 1
        email.value = ''
    }, 300)
}

const handleForgotPassword = async () => {
    if (!email.value) {
        store.showToast('請輸入電子信箱', 'error')
        return
    }

    isLoading.value = true
    try {
        const response = await api.post('/auth/forgot-password', {
            email: email.value
        })

        if (response.data.success) {
            // 顯示信件已寄出畫面
            step.value = 2
        }
    } catch (error) {
        store.showToast(error.response?.data?.message || '發生錯誤，請稍後再試', 'error')
    } finally {
        isLoading.value = false
    }
}
</script>

<style scoped>
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
