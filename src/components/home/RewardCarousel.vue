<template>
    <div class="relative z-10 w-full overflow-hidden py-1">
        <!-- Gift Detail Modal -->
        <GiftDetailModal :isOpen="isGiftModalOpen" :gift="selectedGift" @close="closeGiftModal" />
        <!-- First Row - Left to Right (Gifts 1-12) -->
        <div class="flex gap-2 sm:gap-3 animate-scroll-left mb-2 sm:mb-3 overflow-visible">
            <div v-for="gift in [...firstRowGifts, ...firstRowGifts]" :key="gift.id + '-row1'"
                @click="openGiftModal(gift)"
                class="relative flex-shrink-0 w-40 sm:w-48 md:w-52 lg:w-60 p-1.5 sm:p-2 rounded-xl bg-gradient-to-br from-white/70 to-white/40 dark:from-gray-600/90 dark:to-gray-600/50 backdrop-blur-sm hover:scale-105 hover:z-50 transition-all duration-300 cursor-pointer">
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
        <div class="flex gap-2 sm:gap-3 animate-scroll-right overflow-visible">
            <div v-for="gift in [...secondRowGifts, ...secondRowGifts]" :key="gift.id + '-row2'"
                @click="openGiftModal(gift)"
                class="relative flex-shrink-0 w-40 sm:w-48 md:w-52 lg:w-60 p-1.5 sm:p-2 rounded-xl bg-gradient-to-br from-white/70 to-white/40 dark:from-gray-600/90 dark:to-gray-600/50 backdrop-blur-sm hover:scale-105 hover:z-50 transition-all duration-300 cursor-pointer">
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
import { computed, ref } from 'vue'
import { mockRewards } from '../../mock'
import GiftDetailModal from '../modals/GiftDetailModal.vue'

// Gift Modal State
const isGiftModalOpen = ref(false)
const selectedGift = ref({})

const openGiftModal = (gift) => {
    selectedGift.value = gift
    isGiftModalOpen.value = true
}

const closeGiftModal = () => {
    isGiftModalOpen.value = false
}

// 滾動展示的禮品 - 兩排都從最便宜的開始顯示
// 第一排：永續探索(綠色,1-8) + 質感創造(藍色,9-16) - 向左滾動，最左邊顯示第1個
// 第二排：美學先鋒(橙色,17-24) + 品味閃耀(紫色,25-32) - 向右滾動，最右邊顯示第17個
const firstRowGifts = computed(() => {
    return mockRewards.slice(0, 16) // 禮品 1-16 (100-500積分)
})

const secondRowGifts = computed(() => {
    return mockRewards.slice(16, 32) // 禮品 17-32 (550-1600積分)
})

const getPointsColorClass = (level) => {
    const classes = {
        'EXPLORER': 'text-emerald-600 dark:text-emerald-400',
        'CREATOR': 'text-indigo-600 dark:text-indigo-400',
        'VISIONARY': 'text-amber-500 dark:text-amber-400',
        'LUMINARY': 'text-purple-600 dark:text-purple-400'
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
        transform: translateX(0);
    }

    100% {
        transform: translateX(50%);
    }
}

.animate-scroll-left {
    animation: scroll-left 40s linear infinite;
}

.animate-scroll-right {
    animation: scroll-right 40s linear infinite;
    direction: rtl;
}

.animate-scroll-left:hover,
.animate-scroll-right:hover {
    animation-play-state: paused;
}
</style>
