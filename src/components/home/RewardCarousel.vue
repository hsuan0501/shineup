<template>
    <div class="relative z-10 w-full overflow-visible mt-8">
        <!-- First Row - Left to Right (Gifts 1-12) -->
        <div class="flex gap-3 animate-scroll-left mb-3">
            <div v-for="gift in [...firstRowGifts, ...firstRowGifts]" :key="gift.id + '-row1'"
                class="relative flex-shrink-0 w-60 p-2 rounded-xl bg-gradient-to-br from-white/70 to-white/40 dark:from-gray-600/90 dark:to-gray-600/50 backdrop-blur-sm hover:scale-105 hover:z-50 transition-all duration-300">
                <div class="aspect-[4/3] rounded-lg overflow-hidden mb-2">
                    <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover" />
                </div>
                <div class="text-center">
                    <h4 class="text-xs font-semibold text-light-text dark:text-dark-text line-clamp-1">{{
                        gift.title }}</h4>
                    <p :class="getPointsColorClass(gift.level)" class="text-[10px] font-bold">{{ gift.points }}
                        積分</p>
                </div>
            </div>
        </div>

        <!-- Second Row - Right to Left (Gifts 13-24) -->
        <div class="flex gap-3 animate-scroll-right">
            <div v-for="gift in [...secondRowGifts, ...secondRowGifts]" :key="gift.id + '-row2'"
                class="relative flex-shrink-0 w-60 p-2 rounded-xl bg-gradient-to-br from-white/70 to-white/40 dark:from-gray-600/90 dark:to-gray-600/50 backdrop-blur-sm hover:scale-105 hover:z-50 transition-all duration-300">
                <div class="aspect-[4/3] rounded-lg overflow-hidden mb-2">
                    <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover" />
                </div>
                <div class="text-center">
                    <h4 class="text-xs font-semibold text-light-text dark:text-dark-text line-clamp-1">{{
                        gift.title }}</h4>
                    <p :class="getPointsColorClass(gift.level)" class="text-[10px] font-bold">{{ gift.points }}
                        積分</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue'
import { mockRewards } from '../../mock'

// 滾動展示的禮品 - 使用 mockRewards 中的前12個和後12個
const firstRowGifts = computed(() => {
    return mockRewards.slice(0, 12) // 禮品 1-12
})

const secondRowGifts = computed(() => {
    return mockRewards.slice(12, 24) // 禮品 13-24
})

const getPointsColorClass = (category) => {
    const classes = {
        'daily': 'text-pink-500 dark:text-pink-400',
        'financial': 'text-indigo-500 dark:text-indigo-400',
        'investment': 'text-amber-500 dark:text-amber-400',
        'esg': 'text-green-500 dark:text-green-400',
        'social': 'text-purple-500 dark:text-purple-400'
    }
    return classes[category] || 'text-primary-purple'
}
</script>

<style scoped>
@keyframes scroll-left {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}

@keyframes scroll-right {
    0% {
        transform: translateX(-50%);
    }

    100% {
        transform: translateX(0);
    }
}

.animate-scroll-left {
    animation: scroll-left 30s linear infinite;
}

.animate-scroll-right {
    animation: scroll-right 30s linear infinite;
}

.animate-scroll-left:hover,
.animate-scroll-right:hover {
    animation-play-state: paused;
}
</style>
