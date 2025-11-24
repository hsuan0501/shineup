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

// 滾動展示的禮品 - 兩排都從最便宜的開始顯示
// 第一排：永續探索(綠色,1-8) + 質感創造(藍色,9-16) - 向左滾動，最左邊顯示第1個
// 第二排：品味閃耀(紫色,32-25) + 美學先鋒(橙色,24-17) - 向右滾動，最右邊顯示橙色530積分
const firstRowGifts = computed(() => {
    return mockRewards.slice(0, 16) // 禮品 1-16 (100-500積分)
})

const secondRowGifts = computed(() => {
    return mockRewards.slice(16, 32).reverse() // 禮品 32-17 反轉
})

const getPointsColorClass = (level) => {
    const classes = {
        'EXPLORER': 'text-emerald-600 dark:text-emerald-400',
        'CREATOR': 'text-indigo-600 dark:text-indigo-400',
        'VISIONARY': 'text-orange-600 dark:text-orange-400',
        'LUMINARY': 'text-violet-600 dark:text-violet-400'
    }
    return classes[level] || 'text-primary-purple'
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
        transform: translateX(-100%);
    }

    100% {
        transform: translateX(-50%);
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
