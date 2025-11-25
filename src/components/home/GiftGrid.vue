<template>
    <section class="w-full pt-12 pb-6 px-4 sm:px-6 lg:px-8">
        <div class="max-w-7xl mx-auto">
            <!-- Gift Series Tabs -->
            <div id="gifts" class="flex gap-4 mb-4 overflow-x-auto pb-2 justify-center"
                style="scroll-margin-top: 80px;">
                <button v-for="series in giftSeries" :key="series.id" @click="selectSeries(series.id)" type="button"
                    :class="[
                        'px-6 py-2.5 rounded-full whitespace-nowrap font-semibold text-sm transition-all duration-300 cursor-pointer hover:scale-105 active:scale-95',
                        selectedSeries === series.id
                            ? series.activeClass
                            : series.inactiveClass
                    ]">
                    <span>{{ series.level ? series.level + ' ' + series.levelChinese : series.levelChinese }}</span>
                </button>
            </div>

            <!-- Current Series Info -->
            <div class="mb-4 p-3 rounded-xl" :class="currentSeriesInfo.bgClass">
                <div class="flex justify-between items-center">
                    <!-- 左側：等級資訊 -->
                    <div class="flex items-center gap-4">
                        <h3 class="text-sm font-bold text-light-text dark:text-dark-text">
                            {{ currentSeriesInfo.level }}
                        </h3>
                        <span class="text-xs font-medium text-gray-500 dark:text-gray-400">
                            {{ currentSeriesInfo.pointRange }}
                        </span>
                        <div class="text-xs text-gray-500 dark:text-gray-400">
                            {{ currentSeriesInfo.restriction }}
                        </div>
                    </div>
                    <!-- 右側：庫存圖例 -->
                    <div class="flex items-center gap-3 flex-wrap">
                        <div class="flex items-center gap-1">
                            <div class="w-2 h-2 rounded-full bg-green-500"></div>
                            <span class="text-xs font-medium text-gray-500 dark:text-gray-400">充足</span>
                        </div>
                        <div class="flex items-center gap-1">
                            <div class="w-2 h-2 rounded-full bg-yellow-500"></div>
                            <span class="text-xs font-medium text-gray-500 dark:text-gray-400">適量</span>
                        </div>
                        <div class="flex items-center gap-1">
                            <div class="w-2 h-2 rounded-full bg-orange-500"></div>
                            <span class="text-xs font-medium text-gray-500 dark:text-gray-400">有限</span>
                        </div>
                        <div class="flex items-center gap-1">
                            <div class="w-2 h-2 rounded-full bg-red-500"></div>
                            <span class="text-xs font-medium text-gray-500 dark:text-gray-400">稀少</span>
                        </div>
                        <div class="flex items-center gap-1">
                            <div class="w-2 h-2 rounded-full bg-gray-500"></div>
                            <span class="text-xs font-medium text-gray-500 dark:text-gray-400">已售罄</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Gifts Grid -->
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 mb-8">
                <div v-for="gift in paginatedGifts" :key="gift.id" :class="[
                    'group rounded-lg overflow-hidden bg-light-card dark:bg-gray-600/70 dark:backdrop-blur-xl hover:scale-105 transition-all duration-300 cursor-pointer dark:shadow-2xl',
                    !isGiftInSelectedSeries(gift) ? 'opacity-20 grayscale brightness-50 border border-gray-300 dark:border-gray-600' : `border ${getSeriesBorderClass()}`,
                ]">

                    <!-- Gift Image -->
                    <div class="relative aspect-[4/3] overflow-hidden">
                        <img :src="gift.image" :alt="gift.title"
                            class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300" />

                        <!-- Level Badges (Left Top) - Separated Design -->
                        <div class="absolute top-2 left-2 flex flex-col gap-1 items-start">
                            <!-- Main Level Badge -->
                            <div :class="getLevelBadgeClass(gift.level)"
                                class="px-2.5 py-1.5 rounded-full backdrop-blur-md shadow-lg border border-white/20 dark:border-gray-700/50">
                                <div class="text-[11px] font-bold leading-tight tracking-wide">{{ gift.level }}
                                </div>
                            </div>
                            <!-- Category Label - Independent sizing -->
                            <div :class="getCategoryBadgeClass(gift.level)"
                                class="px-3 py-2 rounded-full backdrop-blur-sm shadow-md">
                                <div class="text-xs font-medium text-white leading-none">{{
                                    getCategoryLabel(gift.level) }}</div>
                            </div>
                        </div>

                        <!-- Wishlist Heart Button (Right Top) -->
                        <button @click.stop="handleToggleWishlist(gift)"
                            class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center rounded-full bg-white/90 dark:bg-gray-800/90 backdrop-blur-sm shadow-lg hover:scale-110 active:scale-95 transition-all duration-300 border border-white/50 dark:border-gray-600/50">
                            <svg v-if="store.isInWishlist(gift.id)" class="w-5 h-5 text-pink-500" fill="currentColor"
                                viewBox="0 0 20 20">
                                <path fill-rule="evenodd"
                                    d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                                    clip-rule="evenodd" />
                            </svg>
                            <svg v-else class="w-5 h-5 text-gray-400 dark:text-gray-500" fill="none"
                                stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                            </svg>
                        </button>
                    </div>

                    <!-- Gift Info -->
                    <div class="p-2 relative">
                        <!-- Stock Dot Indicator (Right Top) -->
                        <div class="absolute top-2 right-2">
                            <div :class="getStockDotClass(gift.stockStatus)" class="w-2.5 h-2.5 rounded-full"></div>
                        </div>

                        <h3 class="text-sm font-bold text-light-text dark:text-dark-text mb-1.5 line-clamp-1 pr-5">
                            {{ gift.title }}
                        </h3>

                        <div class="flex items-center justify-between mb-1.5">
                            <div class="flex items-baseline gap-1">
                                <span class="text-lg font-bold text-primary-purple">{{ gift.points }}</span>
                                <span class="text-xs text-light-text-secondary dark:text-dark-text-secondary">積分</span>
                            </div>
                            <button @click="handleAddToCart(gift)" :class="[
                                'px-3 py-1.5 rounded-full text-xs font-semibold transition-all duration-300',
                                !isGiftInSelectedSeries(gift) || gift.canExchange === false || gift.points > user.rewardPoints
                                    ? 'bg-gray-300 dark:bg-gray-700 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                                    : 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95'
                            ]"
                                :disabled="!isGiftInSelectedSeries(gift) || gift.canExchange === false || gift.points > user.rewardPoints">
                                {{ !isGiftInSelectedSeries(gift) || gift.canExchange === false || gift.points > user.rewardPoints ? '積分不足' : '立即兌換' }} </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Pagination (固定顯示4頁) -->
            <div class="flex items-center justify-center gap-3">
                <button v-for="page in 4" :key="page" @click="isPageAvailable(page) && (currentPage = page)"
                    :disabled="!isPageAvailable(page)" :class="[
                        'w-10 h-10 rounded-full font-semibold text-sm',
                        (selectedSeries === 'all' || selectedSeries === '') && page === currentPage || (selectedSeries !== 'all' && selectedSeries !== '' && isPageAvailable(page))
                            ? 'bg-gradient-to-br from-sky-400 to-purple-400 text-white'
                            : isPageAvailable(page)
                                ? 'bg-gray-100 dark:bg-gray-700/60 dark:backdrop-blur-xl text-gray-600 dark:text-gray-400 cursor-pointer dark:shadow-xl'
                                : 'bg-gray-200 dark:bg-gray-800 text-gray-400 dark:text-gray-600 cursor-not-allowed opacity-50'
                    ]">
                    {{ page }}
                </button>
            </div>

            <!-- Empty State -->
            <div v-if="filteredGifts.length === 0" class="text-center py-12">
                <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm">
                    此系列暫無可兌換禮物
                </p>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import { mockRewards, mockUsers } from '../../mock'
import { useStore } from '../../store/app'

const store = useStore()
// 用戶資料
const user = ref(mockUsers[1]) // 使用第一個用戶作為範例

// Gifts
const selectedSeries = ref('')
const currentPage = ref(1)
const itemsPerPage = 8

const giftSeries = [
    {
        id: 'all',
        level: 'ALL',
        levelChinese: '全部禮品',
        activeClass: 'bg-gradient-to-br from-cyan-500 to-cyan-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-cyan-200 to-cyan-50 dark:from-cyan-900/40 dark:to-cyan-700/40 text-cyan-700 dark:text-cyan-300',
        bgClass: 'bg-gradient-to-br from-cyan-50 to-blue-50 dark:from-cyan-950/30 dark:to-blue-950/30 border border-cyan-200 dark:border-cyan-800',
        pointRange: '0-5000+分',
        restriction: '包含所有等級禮物'
    },
    {
        id: 'sustainable',
        level: 'EXPLORER',
        levelChinese: '探索者',
        activeClass: 'bg-gradient-to-br from-emerald-600 to-teal-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-emerald-200 to-teal-50 dark:from-emerald-900/40 dark:to-teal-700/40 text-emerald-700 dark:text-emerald-300',
        bgClass: 'bg-gradient-to-br from-emerald-50 to-teal-50 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-200 dark:border-emerald-800',
        pointRange: '0-299分',
        restriction: 'Lv1+ 可兌換'
    },
    {
        id: 'quality',
        level: 'CREATOR',
        levelChinese: '創造者',
        activeClass: 'bg-gradient-to-br from-indigo-600 to-blue-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-indigo-200 to-blue-100 dark:from-indigo-900/40 dark:to-blue-800/40 text-indigo-700 dark:text-indigo-300',
        bgClass: 'bg-gradient-to-br from-indigo-50 to-blue-50 dark:from-indigo-950/30 dark:to-blue-950/30 border border-indigo-200 dark:border-indigo-800',
        pointRange: '300-799分',
        restriction: 'Lv2+ 可兌換'
    },
    {
        id: 'aesthetic',
        level: 'VISIONARY',
        levelChinese: '先行者',
        activeClass: 'bg-gradient-to-br from-orange-500 to-yellow-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-orange-200 to-amber-100 dark:from-orange-900/40 dark:to-amber-800/40 text-orange-700 dark:text-orange-300',
        bgClass: 'bg-gradient-to-br from-orange-50 via-amber-50 to-yellow-50 dark:from-orange-950/30 dark:via-amber-950/30 dark:to-yellow-950/30 border border-orange-200 dark:border-orange-800',
        pointRange: '800-1499分',
        restriction: 'Lv3+ 可兌換'
    },
    {
        id: 'premium',
        level: 'LUMINARY',
        levelChinese: '閃耀者',
        activeClass: 'bg-gradient-to-br from-violet-600 to-fuchsia-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-violet-200 to-fuchsia-100 dark:from-violet-900/40 dark:to-fuchsia-800/40 text-violet-700 dark:text-violet-300',
        bgClass: 'bg-gradient-to-br from-violet-50 via-fuchsia-50 to-purple-100 dark:from-violet-950/30 dark:via-fuchsia-950/30 dark:to-purple-900/30 border border-violet-200 dark:border-violet-800',
        pointRange: '1500+分',
        restriction: 'Lv4 專屬頂級禮物'
    }
]

const currentSeriesInfo = computed(() => {
    return giftSeries.find(s => s.id === selectedSeries.value) || giftSeries[0]
})

const filteredGifts = computed(() => {
    if (selectedSeries.value === 'all' || selectedSeries.value === '') {
        return mockRewards
    }

    // 等級階層定義
    const levelHierarchy = {
        'sustainable': 1, // EXPLORER
        'quality': 2,     // CREATOR
        'aesthetic': 3,   // VISIONARY 
        'premium': 4      // LUMINARY
    }

    const selectedLevel = levelHierarchy[selectedSeries.value] || 0

    // 顯示所有禮品，但標記可兌換性
    return mockRewards.map(gift => {
        const giftLevel = levelHierarchy[gift.level] || 0
        const canExchange = giftLevel <= selectedLevel
        const isSelectedLevel = gift.series === selectedSeries.value
        return { ...gift, canExchange, isSelectedLevel }
    }).sort((a, b) => {
        // 選中等級的禮品排在最前面
        if (a.isSelectedLevel && !b.isSelectedLevel) return -1
        if (!a.isSelectedLevel && b.isSelectedLevel) return 1

        // 在同樣是否為選中等級的情況下，可兌換的排在前面
        if (a.canExchange && !b.canExchange) return -1
        if (!a.canExchange && b.canExchange) return 1

        // 最後按等級由高到低排序
        const aLevel = levelHierarchy[a.level] || 0
        const bLevel = levelHierarchy[b.level] || 0
        return bLevel - aLevel
    })
})

const paginatedGifts = computed(() => {
    const start = (currentPage.value - 1) * itemsPerPage
    const end = start + itemsPerPage
    return filteredGifts.value.slice(start, end)
})

// 檢查禮物是否可以兌換（不可兌換的會暗掉）
const isGiftInSelectedSeries = (gift) => {
    if (selectedSeries.value === 'all' || selectedSeries.value === '') {
        return true
    }

    // 如果禮品有 canExchange 屬性，使用它來決定是否暗掉
    if (gift.hasOwnProperty('canExchange')) {
        return gift.canExchange
    }

    // 如果沒有 canExchange 屬性，手動計算
    const levelHierarchy = {
        'sustainable': 1, // EXPLORER
        'quality': 2,     // CREATOR
        'aesthetic': 3,   // VISIONARY 
        'premium': 4      // LUMINARY
    }

    const selectedLevel = levelHierarchy[selectedSeries.value] || 0
    const giftLevel = levelHierarchy[gift.level] || 0

    return giftLevel <= selectedLevel
}

// Reset to page 1 when changing series
const selectSeries = (seriesId) => {
    selectedSeries.value = seriesId
    currentPage.value = 1
}

// 判斷頁碼是否可用
const isPageAvailable = (page) => {
    if (selectedSeries.value === 'all' || selectedSeries.value === '') {
        return true // 全部分類所有頁都可用
    }
    // 其他分類只有對應的頁碼可用
    const seriesPageMap = {
        'sustainable': 1, // EXPLORER - 第1頁
        'quality': 2,     // CREATOR - 第2頁
        'aesthetic': 3,   // VISIONARY - 第3頁
        'premium': 4      // LUMINARY - 第4頁
    }
    return seriesPageMap[selectedSeries.value] === page
}

// Helper functions for gift display
const getLevelBadgeClass = (level) => {
    const classes = {
        'EXPLORER': 'bg-gradient-to-br from-emerald-200/90 to-emerald-100/90 dark:from-emerald-800/70 dark:to-emerald-700/70 text-emerald-700 dark:text-emerald-300',
        'CREATOR': 'bg-gradient-to-br from-indigo-200/90 to-blue-100/90 dark:from-indigo-800/70 dark:to-blue-700/70 text-indigo-700 dark:text-indigo-300',
        'VISIONARY': 'bg-gradient-to-br from-orange-200/90 to-amber-100/90 dark:from-orange-800/70 dark:to-amber-700/70 text-orange-700 dark:text-orange-300',
        'LUMINARY': 'bg-gradient-to-br from-violet-200/90 to-fuchsia-100/90 dark:from-violet-800/70 dark:to-fuchsia-700/70 text-violet-700 dark:text-violet-300'
    }
    return classes[level] || 'bg-gray-100/90 dark:bg-gray-800/70 text-gray-600 dark:text-gray-300'
}

const getStockDotClass = (stockStatus) => {
    const classes = {
        'high': 'bg-green-500',
        'medium': 'bg-yellow-500',
        'low': 'bg-orange-500',
        'critical': 'bg-red-500',
        'sold_out': 'bg-gray-500'
    }
    return classes[stockStatus] || classes['high']
}

const getCategoryLabel = (level) => {
    const labels = {
        'EXPLORER': '永續探索',
        'CREATOR': '質感創造',
        'VISIONARY': '美學先鋒',
        'LUMINARY': '品味閃耀'
    }
    return labels[level] || ''
}

const getCategoryBadgeClass = (level) => {
    const classes = {
        'EXPLORER': 'bg-gradient-to-br from-emerald-600 to-emerald-400',
        'CREATOR': 'bg-gradient-to-br from-indigo-600 to-blue-500',
        'VISIONARY': 'bg-gradient-to-br from-orange-500 to-amber-400',
        'LUMINARY': 'bg-gradient-to-br from-violet-600 to-fuchsia-500'
    }
    return classes[level] || 'bg-gray-500'
}

const getSeriesBorderClass = () => {
    const series = selectedSeries.value
    const borderClasses = {
        '': 'border-cyan-200 dark:border-cyan-800',
        'all': 'border-cyan-200 dark:border-cyan-800',
        'sustainable': 'border-emerald-200 dark:border-emerald-800',
        'quality': 'border-indigo-200 dark:border-indigo-800',
        'aesthetic': 'border-orange-200 dark:border-orange-800',
        'premium': 'border-violet-200 dark:border-violet-800'
    }
    return borderClasses[series] || 'border-light-border dark:border-dark-border'
}

// 處理加入購物車
const handleAddToCart = (gift) => {
    if (gift.canExchange === false) {
        return
    }

    // 檢查積分是否足夠
    if (gift.points > user.value.rewardPoints) {
        alert('您的積分不足，無法兌換此禮品')
        return
    }

    // 檢查庫存狀態
    if (gift.stockStatus === 'out') {
        alert('此禮品已售罄')
        return
    }

    store.addToCart(gift)
    alert(`已將「${gift.title}」加入購物車！`)
}

// 處理收藏/取消收藏
const handleToggleWishlist = (gift) => {
    store.toggleWishlist(gift)
}
</script>
