<template>
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl font-bold mb-8 text-light-text dark:text-dark-text">購物車</h1>

        <div v-if="cartItems.length > 0" class="grid grid-cols-1 lg:grid-cols-3 gap-8">
            <!-- Cart Items List -->
            <div class="lg:col-span-2 space-y-4">
                <div v-for="item in cartItems" :key="item.id"
                    class="flex gap-4 p-4 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border transition-all hover:shadow-md">
                    <!-- Image -->
                    <div class="w-24 h-24 rounded-xl overflow-hidden flex-shrink-0 bg-gray-100 dark:bg-gray-700">
                        <img :src="item.image" :alt="item.title" class="w-full h-full object-cover">
                    </div>

                    <!-- Content -->
                    <div class="flex-1 flex flex-col justify-between">
                        <div class="flex justify-between items-start">
                            <div>
                                <h3 class="font-bold text-light-text dark:text-dark-text line-clamp-1">{{ item.title }}
                                </h3>
                                <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary">{{ item.level
                                    }}</p>
                            </div>
                            <button @click="removeItem(item.id)"
                                class="text-gray-400 hover:text-red-500 transition-colors">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                </svg>
                            </button>
                        </div>

                        <div class="flex justify-between items-end">
                            <div class="font-bold text-primary-purple">{{ item.points }} 積分</div>

                            <!-- Quantity Control -->
                            <div class="flex items-center gap-3 bg-gray-100 dark:bg-gray-800 rounded-lg p-1">
                                <button @click="updateQuantity(item.id, -1)"
                                    class="w-6 h-6 flex items-center justify-center rounded hover:bg-white dark:hover:bg-gray-700 text-gray-600 dark:text-gray-300 transition-colors"
                                    :disabled="item.quantity <= 1">
                                    -
                                </button>
                                <span class="text-sm font-medium w-4 text-center dark:text-white">{{ item.quantity
                                    }}</span>
                                <button @click="updateQuantity(item.id, 1)"
                                    class="w-6 h-6 flex items-center justify-center rounded hover:bg-white dark:hover:bg-gray-700 text-gray-600 dark:text-gray-300 transition-colors">
                                    +
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Summary Card -->
            <div class="lg:col-span-1">
                <div
                    class="sticky top-24 p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border space-y-6">
                    <h2 class="text-xl font-bold text-light-text dark:text-dark-text">訂單摘要</h2>

                    <div class="space-y-3">
                        <div
                            class="flex justify-between text-sm text-light-text-secondary dark:text-dark-text-secondary">
                            <span>商品總數</span>
                            <span>{{ totalItems }} 件</span>
                        </div>
                        <div
                            class="flex justify-between text-sm text-light-text-secondary dark:text-dark-text-secondary">
                            <span>所需積分</span>
                            <span>{{ totalPoints }} 分</span>
                        </div>
                        <div
                            class="pt-3 border-t border-gray-200 dark:border-gray-700 flex justify-between items-center">
                            <span class="font-bold text-light-text dark:text-dark-text">總計</span>
                            <span
                                class="text-2xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-primary-purple to-primary-blue">
                                {{ totalPoints }}
                            </span>
                        </div>
                    </div>

                    <button @click="checkout"
                        class="w-full py-3 rounded-xl bg-gradient-to-r from-primary-purple to-primary-blue text-white font-bold hover:shadow-lg hover:scale-[1.02] active:scale-[0.98] transition-all duration-300">
                        確認兌換
                    </button>
                </div>
            </div>
        </div>

        <!-- Empty State -->
        <div v-else class="text-center py-24">
            <div
                class="w-24 h-24 mx-auto mb-6 bg-gray-100 dark:bg-gray-800 rounded-full flex items-center justify-center">
                <svg class="w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                </svg>
            </div>
            <h2 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">購物車是空的</h2>
            <p class="text-light-text-secondary dark:text-dark-text-secondary mb-8">快去挑選喜歡的禮品吧！</p>
            <router-link to="/"
                class="inline-block px-8 py-3 rounded-full bg-primary-purple text-white font-semibold hover:bg-primary-purple/90 transition-colors">
                去逛逛
            </router-link>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { mockRewards } from '../mock'

// Mock cart data - using first 3 items from mockRewards
const cartItems = ref(mockRewards.slice(0, 3).map(item => ({
    ...item,
    quantity: 1
})))

const totalItems = computed(() => {
    return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

const totalPoints = computed(() => {
    return cartItems.value.reduce((sum, item) => sum + (item.points * item.quantity), 0)
})

const updateQuantity = (id, change) => {
    const item = cartItems.value.find(i => i.id === id)
    if (item) {
        const newQty = item.quantity + change
        if (newQty >= 1) {
            item.quantity = newQty
        }
    }
}

const removeItem = (id) => {
    cartItems.value = cartItems.value.filter(i => i.id !== id)
}

const checkout = () => {
    alert(`兌換成功！共扣除 ${totalPoints.value} 積分。`)
    cartItems.value = []
}
</script>
