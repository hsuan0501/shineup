<template>
    <div ref="chatBoxContainer" class="fixed bottom-6 right-6 sm:right-8 lg:right-10 z-50 flex flex-col items-end">
        <!-- Chat Window -->
        <transition name="fade-slide">
            <div v-if="isOpen"
                class="mb-4 w-80 sm:w-96 h-[500px] bg-zinc-50 dark:bg-gray-800 rounded-2xl shadow-2xl border border-zinc-200 dark:border-gray-700 flex flex-col overflow-hidden backdrop-blur-xl backdrop-saturate-150">
                <!-- Header -->
                <div
                    class="p-4 bg-gradient-to-br from-sky-400 to-purple-400 text-white flex justify-between items-center">
                    <div class="flex items-center gap-3">
                        <div class="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
                            </svg>
                        </div>
                        <div>
                            <h3 class="font-bold text-sm">線上客服</h3>
                            <p class="text-xs text-white/80">通常在幾分鐘內回覆</p>
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
                <div class="flex-1 p-4 overflow-y-auto space-y-4 bg-zinc-50 dark:bg-gray-900/50"
                    ref="messagesContainer">
                    <div v-for="(msg, index) in messages" :key="index" :class="[
                        'flex w-full',
                        msg.isUser ? 'justify-end' : 'justify-start'
                    ]">
                        <div :class="[
                            'max-w-[80%] p-3 rounded-2xl text-sm',
                            msg.isUser
                                ? 'bg-gradient-to-r from-sky-100 to-indigo-100 text-gray-900 rounded-tr-none'
                                : 'bg-white dark:bg-gray-700 text-gray-800 dark:text-gray-200 rounded-tl-none shadow-sm'
                        ]">
                            {{ msg.text }}
                        </div>
                    </div>
                </div>

                <!-- Input Area -->
                <div class="p-4 bg-zinc-50 dark:bg-gray-800 border-t border-zinc-200 dark:border-gray-700">
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
        <button @click="toggleChat"
            class="h-14 w-14 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 text-zinc-600 dark:text-gray-300 hover:scale-105 active:scale-95 transition-all duration-300 z-50 group shadow-lg backdrop-blur-xl">
            <span
                class="absolute -top-1 -right-1 w-4 h-4 bg-red-500 rounded-full border-2 border-white dark:border-gray-800"
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
import { ref, nextTick, watch, onMounted, onUnmounted } from 'vue'

const isOpen = ref(false)
const newMessage = ref('')
const messagesContainer = ref(null)
const chatBoxContainer = ref(null)

const messages = ref([
    { text: '您好！歡迎來到 Shine Level Up。請問有什麼我可以幫您的嗎？', isUser: false }
])

const scrollToBottom = async () => {
    await nextTick()
    if (messagesContainer.value) {
        messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
}

const sendMessage = () => {
    if (!newMessage.value.trim()) return

    // Add user message
    messages.value.push({
        text: newMessage.value,
        isUser: true
    })

    const userMsg = newMessage.value
    newMessage.value = ''
    scrollToBottom()

    // Simulate bot response
    setTimeout(() => {
        let reply = '收到您的訊息！我們的客服人員會盡快回覆您。'
        if (userMsg.includes('積分') || userMsg.includes('點數')) {
            reply = '積分可以透過完成每日任務和參與活動獲得，並可用於兌換各種精美禮品。'
        } else if (userMsg.includes('禮品') || userMsg.includes('兌換')) {
            reply = '您可以到首頁下方的「全部禮品」區塊查看所有可兌換的獎勵。'
        } else if (userMsg.includes('等級') || userMsg.includes('升級')) {
            reply = '累積足夠積分後，您的等級會自動提升，解鎖更多專屬權益！'
        }

        messages.value.push({
            text: reply,
            isUser: false
        })
        scrollToBottom()
    }, 1000)
}

// 切換聊天窗口
const isToggling = ref(false)
const toggleChat = async () => {
    if (isToggling.value) return
    
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
    if (chatBoxContainer.value && !chatBoxContainer.value.contains(event.target) && isOpen.value) {
        isOpen.value = false
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
