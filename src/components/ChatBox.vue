<template>
    <div ref="chatBoxContainer"
        class="fixed bottom-6 right-6 z-40 flex flex-col items-end">
        <!-- Chat Window -->
        <transition name="fade-slide">
            <div v-if="isOpen"
                class="mb-4 w-[300px] sm:w-[340px] h-[450px] bg-zinc-50 dark:bg-gray-700/80 rounded-2xl shadow-2xl border border-zinc-200 dark:border-gray-600/40 flex flex-col overflow-hidden backdrop-blur-xl backdrop-saturate-150 dark:backdrop-blur-2xl">
                <!-- Header -->
                <div
                    class="p-4 bg-gradient-to-br from-cyan-400 to-blue-500 text-white flex justify-between items-center">
                    <div class="flex items-center gap-3">
                        <div class="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center">
                            <svg class="w-5 h-5" viewBox="0 0 24 24">
                                <path d="M12 0 L14 10 L24 12 L14 14 L12 24 L10 14 L0 12 L10 10 Z" fill="white" />
                            </svg>
                        </div>
                        <div>
                            <h3 class="font-bold text-sm">ShineUp 智能客服</h3>
                        </div>
                    </div>
                    <button @click="isOpen = false" class="text-white/80 hover:text-white transition-colors">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M6 18L18 6M6 6l12 12" />
                        </svg>
                    </button>
                </div>

                <!-- Messages Area -->
                <div class="flex-1 p-4 overflow-y-auto space-y-3 bg-zinc-50 dark:bg-gray-900/50"
                    ref="messagesContainer">
                    <div v-for="(msg, index) in messages" :key="index" :class="[
                        'flex w-full',
                        msg.isUser ? 'justify-end' : 'justify-start'
                    ]">
                        <div :class="[
                            'max-w-[80%] p-3 rounded-2xl text-sm',
                            msg.isUser
                                ? 'bg-gradient-to-br from-cyan-100 to-blue-100 dark:from-cyan-700/50 dark:to-blue-700/50 text-gray-900 dark:text-white rounded-tr-none border border-cyan-200 dark:border-cyan-600/50 shadow-sm'
                                : 'bg-white dark:bg-gray-700 text-gray-800 dark:text-gray-200 rounded-tl-none shadow-sm'
                        ]">
                            <span class="whitespace-pre-wrap">{{ msg.text }}</span>
                        </div>
                    </div>
                    <!-- 載入中動畫 -->
                    <div v-if="isLoading" class="flex w-full justify-start">
                        <div class="bg-white dark:bg-gray-700 p-3 rounded-2xl rounded-tl-none shadow-sm">
                            <div class="flex gap-1">
                                <span class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0ms"></span>
                                <span class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 150ms"></span>
                                <span class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 300ms"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 快速問題按鈕 (固定在底部) -->
                <div class="px-3 py-2 bg-zinc-50 dark:bg-gray-800/50 border-t border-zinc-100 dark:border-gray-700/50">
                    <div class="flex flex-wrap gap-1.5 justify-center">
                        <button
                            v-for="q in quickQuestions"
                            :key="q"
                            @click.stop="askQuickQuestion(q)"
                            class="px-2.5 py-1 text-[11px] bg-white dark:bg-gray-600 text-gray-600 dark:text-gray-300 rounded-full border border-gray-200 dark:border-gray-500 hover:bg-cyan-50 dark:hover:bg-cyan-700/30 hover:border-cyan-300 dark:hover:border-cyan-500 transition-all"
                        >
                            {{ q }}
                        </button>
                    </div>
                </div>

                <!-- Input Area -->
                <div
                    class="p-4 bg-zinc-50 dark:bg-gray-700/80 border-t border-zinc-200 dark:border-gray-600/40 backdrop-blur-xl">
                    <form @submit.prevent="sendMessage" class="flex gap-2">
                        <input v-model="newMessage" type="text" placeholder="輸入訊息..."
                            class="flex-1 px-4 py-2 rounded-full bg-gray-100 dark:bg-gray-700 text-gray-800 dark:text-gray-200 text-sm focus:outline-none focus:ring-2 focus:ring-primary-purple/50 transition-all placeholder-gray-400 dark:placeholder-gray-500" />
                        <button type="submit" :disabled="!newMessage.trim()"
                            class="p-2 rounded-full bg-primary-purple text-white hover:bg-primary-purple/90 disabled:opacity-50 disabled:cursor-not-allowed transition-all">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
                            </svg>
                        </button>
                    </form>
                </div>
            </div>
        </transition>

        <!-- Toggle Button -->
        <button @click="toggleChat" :class="[
            'relative h-14 w-14 flex items-center justify-center rounded-full border transition-all duration-300 z-50 group shadow-lg backdrop-blur-xl dark:shadow-2xl bg-zinc-100/90 dark:bg-gray-700/80 border-zinc-200/50 dark:border-gray-600/40 hover:bg-zinc-200 dark:hover:bg-gray-600/70 text-zinc-600 dark:text-gray-300 hover:scale-105 active:scale-95'
        ]">
            <span
                class="absolute top-0 right-0 w-3 h-3 bg-pink-500 rounded-full"
                v-if="!isOpen"></span>
            <svg v-if="!isOpen" class="w-7 h-7 group-hover:rotate-12 transition-transform duration-300" fill="none"
                stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
            </svg>
            <svg v-else class="w-7 h-7 group-hover:rotate-90 transition-transform duration-300" fill="none"
                stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
        </button>
    </div>
</template>

<script setup>
import { ref, nextTick, watch, onMounted, onUnmounted, computed } from 'vue'
import { useStore } from '@/store'
import { chatAPI } from '@/api'

const store = useStore()
const isLoading = ref(false)
const isOpen = ref(false)
const newMessage = ref('')
const messagesContainer = ref(null)
const chatBoxContainer = ref(null)

// 檢查是否登入
const isLoggedIn = computed(() => store.isAuthenticated)

// 檢測登入modal是否開啟
const isLoginModalOpen = ref(false)

// 監聽 document 上的 modal 變化
if (typeof document !== 'undefined') {
  const observer = new MutationObserver(() => {
    const modal = document.querySelector('.fixed.inset-0.z-50')
    isLoginModalOpen.value = !!modal
  })
  observer.observe(document.body, { childList: true, subtree: true })
}

const messages = ref([
    { text: '您好！歡迎來到 ShineUp，請問有什麼可以幫忙的嗎？', isUser: false }
])

// 快速問題選項
const quickQuestions = [
    '如何獲得積分？',
    '如何兌換禮品？',
    '等級制度說明',
    '出貨進度',
    '人工客服'
]

// 點擊快速問題
const askQuickQuestion = (question) => {
    newMessage.value = question
    sendMessage()
}

const scrollToBottom = async () => {
    await nextTick()
    if (messagesContainer.value) {
        messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
}

const sendMessage = async () => {
    if (!newMessage.value.trim() || isLoading.value) return

    // Add user message
    const userMsg = newMessage.value
    messages.value.push({
        text: userMsg,
        isUser: true
    })
    newMessage.value = ''
    scrollToBottom()

    // 呼叫 AI API
    isLoading.value = true
    try {
        const response = await chatAPI.send(userMsg)
        if (response.data.success) {
            messages.value.push({
                text: response.data.reply,
                isUser: false
            })
        } else {
            messages.value.push({
                text: '抱歉，發生錯誤，請稍後再試。',
                isUser: false
            })
        }
    } catch (error) {
        console.error('Chat error:', error)
        messages.value.push({
            text: '抱歉，無法連接客服，請稍後再試。',
            isUser: false
        })
    } finally {
        isLoading.value = false
        scrollToBottom()
    }
}

// 切換聊天窗口
const isToggling = ref(false)
const toggleChat = async () => {
    if (isToggling.value) return

    // 如果未登入，提示先登入
    if (!isLoggedIn.value) {
        store.showToast('請先登入以體驗智能客服功能！', 'info')
        return
    }

    isToggling.value = true
    isOpen.value = !isOpen.value

    // 防抖動延遲
    setTimeout(() => {
        isToggling.value = false
    }, 300)
}

// 點擊外部區域關閉對話框
const handleClickOutside = (event) => {
    if (isToggling.value) return
    if (chatBoxContainer.value && !chatBoxContainer.value.contains(event.target)) {
        if (isOpen.value) isOpen.value = false
    }
}

// Watch for open state to scroll to bottom
watch(isOpen, (newVal) => {
    if (newVal) {
        scrollToBottom()
    }
})

// 添加和移除事件監聽器
onMounted(() => {
    document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
    document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
    transition: all 0.3s ease;
}

.fade-slide-enter-from,
.fade-slide-leave-to {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
}
</style>
