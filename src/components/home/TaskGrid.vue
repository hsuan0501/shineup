<template>
    <section class="w-full pt-8 sm:pt-12 md:pt-16 lg:pt-20 pb-6 px-4 sm:px-6 lg:px-8">
        <div class="max-w-7xl mx-auto">
            <!-- Task Category Tabs -->
            <div id="tasks" class="grid grid-cols-3 sm:flex sm:flex-wrap gap-2 sm:gap-4 mb-3 sm:mb-4 pb-2 justify-center"
                style="scroll-margin-top: 80px;">
                <button v-for="cat in taskCategories" :key="cat.id" @click="selectTaskCategory(cat.id)" type="button"
                    :class="[
                        'px-3 sm:px-6 py-1.5 sm:py-2.5 rounded-full whitespace-nowrap font-semibold text-[10px] sm:text-sm transition-all duration-300 cursor-pointer hover:scale-105 active:scale-95',
                        selectedTaskCategory === cat.id
                            ? cat.activeClass
                            : cat.inactiveClass
                    ]">
                    {{ cat.label }}
                </button>
            </div>

            <!-- Current Category Info -->
            <div class="mb-3 sm:mb-4 p-2 sm:p-3 rounded-lg sm:rounded-xl" :class="currentTaskCategoryInfo.bgClass">
                <div class="flex justify-between items-center gap-2">
                    <!-- 左側：分類資訊 -->
                    <div class="flex flex-col sm:flex-row sm:items-center gap-0.5 sm:gap-4 flex-1 min-w-0">
                        <h3 class="text-xs sm:text-sm font-bold text-light-text dark:text-dark-text whitespace-nowrap">
                            {{ currentTaskCategoryInfo.label }}
                        </h3>
                        <span class="text-[10px] sm:text-xs font-medium text-gray-500 dark:text-gray-400 truncate">
                            {{ currentTaskCategoryInfo.description }}
                        </span>
                    </div>
                    <!-- 右側：任務數量 -->
                    <div class="flex items-center gap-1 sm:gap-2 flex-shrink-0">
                        <span class="text-[10px] sm:text-xs font-medium text-gray-500 dark:text-gray-400">任務數量</span>
                        <span class="text-xs sm:text-sm font-bold" :class="getTaskCountColorClass">{{ filteredTasks.length }}</span>
                    </div>
                </div>
            </div>

            <!-- Tasks Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3 mb-8">
                <div v-for="task in paginatedTasks" :key="task.id"
                    class="p-3.5 rounded-2xl bg-light-card dark:bg-gray-700/60 dark:backdrop-blur-xl hover:scale-105 transition-all duration-300 group cursor-pointer dark:shadow-2xl border"
                    :class="getTaskBorderClass(task.category)">

                    <div class="flex items-start justify-between mb-2.5">
                        <div class="flex items-center gap-2">
                            <div class="w-9 h-9 rounded-xl flex items-center justify-center"
                                :class="getTaskIconBgClass(task.category)">
                                <svg class="w-4 h-4" :class="getTaskIconColorClass(task.category)" fill="none"
                                    stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        :d="getTaskIconPathByTitle(task.title)" />
                                </svg>
                            </div>
                            <span :class="[
                                'px-2.5 py-0.5 rounded-full text-xs font-semibold',
                                getCategoryBadgeClassForTask(task.category)
                            ]">
                                {{ getCategoryLabelForTask(task.category) }}
                            </span>
                        </div>
                        <span :class="['text-base font-bold', getPointsColorClass(task.category)]">+{{ task.points
                            }} 分</span>
                    </div>

                    <h3 class="text-base font-bold text-light-text dark:text-dark-text mb-1">{{ task.title }}</h3>
                    <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary mb-2">{{
                        task.description }}</p>

                    <div class="flex items-center justify-between">
                        <div
                            class="flex items-center gap-2 text-xs text-light-text-secondary dark:text-dark-text-secondary">
                            <span class="px-2 py-0.5 rounded-md bg-light-bg dark:bg-dark-bg">{{ task.frequency
                                }}</span>
                            <span class="px-2 py-0.5 rounded-md bg-light-bg dark:bg-dark-bg">{{ task.level }}</span>
                        </div>
                        <button :disabled="task.completed" :class="[
                            'px-3 py-1.5 rounded-full text-xs font-semibold transition-all duration-300 pointer-events-auto',
                            task.completed
                                ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                                : 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95'
                        ]">
                            {{ task.completed ? '已完成' : '立即完成' }}
                        </button>
                    </div>
                </div>
            </div>

            <!-- Task Pagination (固定顯示5頁) -->
            <div class="flex items-center justify-center gap-3">
                <button v-for="page in 5" :key="page" @click="isTaskPageAvailable(page) && (currentTaskPage = page)"
                    :disabled="!isTaskPageAvailable(page)" :class="[
                        'w-10 h-10 rounded-full font-semibold text-sm',
                        (selectedTaskCategory === 'all' || selectedTaskCategory === '') && page === currentTaskPage || (selectedTaskCategory !== 'all' && selectedTaskCategory !== '' && isTaskPageAvailable(page))
                            ? 'bg-gradient-to-br from-sky-400 to-purple-400 text-white'
                            : isTaskPageAvailable(page)
                                ? 'bg-gray-100 dark:bg-gray-700/60 dark:backdrop-blur-xl text-gray-600 dark:text-gray-400 cursor-pointer dark:shadow-xl'
                                : 'bg-gray-200 dark:bg-gray-800 text-gray-400 dark:text-gray-600 cursor-not-allowed opacity-50'
                    ]">
                    {{ page }}
                </button>
            </div>

            <!-- Empty State -->
            <div v-if="filteredTasks.length === 0" class="text-center py-12">
                <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm">
                    此分類暫無任務
                </p>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref, computed } from 'vue'
import { mockTasks } from '../../mock'
import { useStore } from '../../store/app'

const store = useStore()

// Tasks
const selectedTaskCategory = ref('')
const currentTaskPage = ref(1)
const itemsPerTaskPage = 6

const taskCategories = [
    {
        id: 'all',
        label: '全部任務',
        icon: '📋',
        activeClass: 'bg-gradient-to-br from-cyan-500 to-cyan-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-cyan-200 to-cyan-50 dark:from-cyan-900/40 dark:to-cyan-700/40 text-cyan-700 dark:text-cyan-300',
        bgClass: 'bg-gradient-to-br from-cyan-50 to-blue-50 dark:from-cyan-950/30 dark:to-blue-950/30 border border-cyan-200 dark:border-cyan-800',
        description: '查看所有類型的任務'
    },
    {
        id: 'daily',
        label: '日常互動',
        icon: '📱',
        activeClass: 'bg-gradient-to-br from-rose-500 to-pink-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-pink-200 to-pink-50 dark:from-pink-900/40 dark:to-pink-700/40 text-pink-700 dark:text-pink-300',
        bgClass: 'bg-gradient-to-br from-pink-50 to-rose-50 dark:from-pink-950/30 dark:to-rose-950/30 border border-pink-200 dark:border-pink-800',
        description: '每日登入、分享、互動等簡單任務'
    },
    {
        id: 'financial',
        label: '理財學習',
        icon: '💰',
        activeClass: 'bg-gradient-to-br from-indigo-600 to-blue-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-indigo-200 to-blue-100 dark:from-indigo-900/40 dark:to-blue-800/40 text-indigo-700 dark:text-indigo-300',
        bgClass: 'bg-gradient-to-br from-indigo-50 to-blue-50 dark:from-indigo-950/30 dark:to-blue-950/30 border border-indigo-200 dark:border-indigo-800',
        description: '閱讀文章、觀看影片、完成測驗'
    },
    {
        id: 'investment',
        label: '投資實踐',
        icon: '📊',
        activeClass: 'bg-gradient-to-br from-orange-500 to-yellow-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-orange-200 to-amber-100 dark:from-orange-900/40 dark:to-amber-800/40 text-orange-700 dark:text-orange-300',
        bgClass: 'bg-gradient-to-br from-orange-50 via-amber-50 to-yellow-50 dark:from-orange-950/30 dark:via-amber-950/30 dark:to-yellow-950/30 border border-orange-200 dark:border-orange-800',
        description: '風險測驗、投資規劃、開戶諮詢'
    },
    {
        id: 'esg',
        label: '永續行動',
        icon: '🌱',
        activeClass: 'bg-gradient-to-br from-emerald-600 to-teal-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-emerald-200 to-teal-50 dark:from-emerald-900/40 dark:to-teal-700/40 text-emerald-700 dark:text-emerald-300',
        bgClass: 'bg-gradient-to-br from-emerald-50 to-teal-50 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-200 dark:border-emerald-800',
        description: 'ESG學習、碳足跡計算、綠色目標'
    },
    {
        id: 'social',
        label: '社群成就',
        icon: '🎁',
        activeClass: 'bg-gradient-to-br from-violet-600 to-fuchsia-400 text-white',
        inactiveClass: 'bg-gradient-to-br from-violet-200 to-fuchsia-100 dark:from-violet-900/40 dark:to-fuchsia-800/40 text-violet-700 dark:text-violet-300',
        bgClass: 'bg-gradient-to-br from-violet-50 via-fuchsia-50 to-purple-100 dark:from-violet-950/30 dark:via-fuchsia-950/30 dark:to-purple-900/30 border border-violet-200 dark:border-violet-800',
        description: '邀請好友、升級獎勵、參加活動'
    }
]

const currentTaskCategoryInfo = computed(() => {
    return taskCategories.find(c => c.id === selectedTaskCategory.value) || taskCategories[0]
})

const filteredTasks = computed(() => {
    if (selectedTaskCategory.value === 'all' || selectedTaskCategory.value === '') {
        return mockTasks
    }
    return mockTasks.filter(t => t.category === selectedTaskCategory.value)
})

const paginatedTasks = computed(() => {
    const start = (currentTaskPage.value - 1) * itemsPerTaskPage
    const end = start + itemsPerTaskPage
    return filteredTasks.value.slice(start, end)
})

const selectTaskCategory = (categoryId) => {
    selectedTaskCategory.value = categoryId
    currentTaskPage.value = 1
}

// 判斷任務頁碼是否可用
const isTaskPageAvailable = (page) => {
    if (selectedTaskCategory.value === 'all' || selectedTaskCategory.value === '') {
        return true // 全部任務所有頁都可用
    }
    // 其他分類只有對應的頁碼可用
    const categoryPageMap = {
        'daily': 1,      // 日常互動 - 第1頁
        'financial': 2,  // 理財學習 - 第2頁
        'investment': 3, // 投資實踐 - 第3頁
        'esg': 4,        // 永續行動 - 第4頁
        'social': 5      // 社群成就 - 第5頁
    }
    return categoryPageMap[selectedTaskCategory.value] === page
}

const getCategoryLabelForTask = (category) => {
    const labels = {
        'daily': '日常互動',
        'financial': '理財學習',
        'investment': '投資實踐',
        'esg': '永續行動',
        'social': '社群成就'
    }
    return labels[category] || category
}

const getCategoryBadgeClassForTask = (category) => {
    const classes = {
        'daily': 'bg-pink-100 dark:bg-pink-900/30 text-pink-700 dark:text-pink-300',
        'financial': 'bg-indigo-100 dark:bg-indigo-900/30 text-indigo-700 dark:text-indigo-300',
        'investment': 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-300',
        'esg': 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-300',
        'social': 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-300'
    }
    return classes[category] || 'bg-gray-100 dark:bg-gray-900/30 text-gray-700 dark:text-gray-300'
}

const getTaskIconBgClass = (category) => {
    const classes = {
        'daily': 'bg-pink-100 dark:bg-pink-900/30',
        'financial': 'bg-indigo-100 dark:bg-indigo-900/30',
        'investment': 'bg-amber-100 dark:bg-amber-900/30',
        'esg': 'bg-green-100 dark:bg-green-900/30',
        'social': 'bg-purple-100 dark:bg-purple-900/30'
    }
    return classes[category] || 'bg-gray-100 dark:bg-gray-900/30'
}

const getTaskIconColorClass = (category) => {
    const classes = {
        'daily': 'text-pink-600 dark:text-pink-400',
        'financial': 'text-indigo-600 dark:text-indigo-400',
        'investment': 'text-amber-600 dark:text-amber-400',
        'esg': 'text-green-600 dark:text-green-400',
        'social': 'text-purple-600 dark:text-purple-400'
    }
    return classes[category] || 'text-gray-600 dark:text-gray-400'
}

const getTaskBorderClass = (taskCategory) => {
    // 根據當前選擇的任務分類來決定邊框顏色（與說明欄一致）
    const borderClasses = {
        'all': 'border-cyan-200 dark:border-cyan-800',           // 全部任務 - 淺青色
        'daily': 'border-pink-200 dark:border-pink-800',         // 日常互動 - 淺粉紅
        'financial': 'border-indigo-200 dark:border-indigo-800', // 理財學習 - 淺靛藍
        'investment': 'border-orange-200 dark:border-orange-800', // 投資實踐 - 淺橘色
        'esg': 'border-emerald-200 dark:border-emerald-800',     // 永續行動 - 淺翡翠
        'social': 'border-violet-200 dark:border-violet-800'     // 社交分享 - 淺紫色
    }
    return borderClasses[selectedTaskCategory.value] || 'border-cyan-200 dark:border-cyan-800'
}

// 根據任務標題返回特定圖標
const getTaskIconPathByTitle = (title) => {
    const iconMap = {
        '每日登入': 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z', // 時鐘
        '完善個人資料': 'M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z', // 個人頭像
        '連續3天登入': 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z', // 時鐘
        '連續7天登入': 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z', // 時鐘
        '分享到社群': 'M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z', // 分享
        '按讚或留言': 'M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z', // 對話
        '點擊閱讀文章': 'M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253', // 書本
        '播放影片': 'M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z M21 12a9 9 0 11-18 0 9 9 0 0118 0z', // 播放
        '完成小測驗': 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4', // 文件打勾
        '完成開戶流程說明': 'M7 12l3-3 3 3 4-4M8 21l4-4 4 4M3 4h18M4 4h16v12a1 1 0 01-1 1H5a1 1 0 01-1-1V4z', // 股票圖表
        '訂閱市場分析報告': 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z', // 圖表
        '追蹤官方社群': 'M14 10h4.764a2 2 0 011.789 2.894l-3.5 7A2 2 0 0115.263 21h-4.017c-.163 0-.326-.02-.485-.06L7 20m7-10V5a2 2 0 00-2-2h-.095c-.5 0-.905.405-.905.905 0 .714-.211 1.412-.608 2.006L7 11v9m7-10h-2M7 20H5a2 2 0 01-2-2v-6a2 2 0 012-2h2.5', // 讚
        '完成風險測驗': 'M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3', // 天平
        '使用退休計算器': 'M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z', // 計算機
        '設定儲蓄目標': 'M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z', // 目標打勾
        '填寫開戶意向調查': 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2', // 文件
        '完善投資偏好': 'M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z', // 公事包
        '預約理專諮詢': 'M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z', // 電話
        '閱讀ESG文章': 'M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253', // 書本
        'ESG知識測驗': 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z', // 考試卷
        '使用碳足跡計算器': 'M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z', // 計算機
        '設定綠色目標': 'M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z', // 星光目標
        '分享ESG內容': 'M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z', // 分享
        '觀看ESG影片': 'M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z M21 12a9 9 0 11-18 0 9 9 0 0118 0z', // 播放
        '邀請好友註冊': 'M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z', // 添加用戶
        '首次升級獎勵': 'M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z', // 星光目標
        '參加線上說明會': 'M19 11a7 7 0 01-7 7m0 0a7 7 0 01-7-7m7 7v4m0 0H8m4 0h4m-4-8a3 3 0 01-3-3V5a3 3 0 116 0v6a3 3 0 01-3 3z', // 麥克風
        '完成新手任務': 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z', // 啞鈴/健身
        '連續活躍獎勵': 'M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7', // 禮物
        '社群貢獻獎': 'M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7' // 禮物
    }
    return iconMap[title] || 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2'
}

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

// 根據當前選擇的分類返回任務數量的顏色
const getTaskCountColorClass = computed(() => {
    const classes = {
        'all': 'text-cyan-600 dark:text-cyan-400',
        'daily': 'text-pink-600 dark:text-pink-400',
        'financial': 'text-indigo-600 dark:text-indigo-400',
        'investment': 'text-amber-600 dark:text-amber-400',
        'esg': 'text-emerald-600 dark:text-emerald-400',
        'social': 'text-violet-600 dark:text-violet-400'
    }
    return classes[selectedTaskCategory.value] || classes['all']
})
</script>
