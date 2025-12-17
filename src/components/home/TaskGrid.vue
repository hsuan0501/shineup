<template>
    <section class="w-full pt-8 sm:pt-12 md:pt-16 lg:pt-20 pb-6 px-4 sm:px-6 lg:px-8">
        <div class="max-w-7xl mx-auto">
            <!-- Task Category Tabs -->
            <div id="tasks" class="grid grid-cols-3 sm:flex sm:flex-wrap gap-2 sm:gap-4 mb-3 sm:mb-4 pb-2 justify-center"
                style="scroll-margin-top: 80px;">
                <button v-for="cat in taskCategories" :key="cat.id" @click="selectTaskCategory(cat.id)" type="button"
                    :class="[
                        'px-3 sm:px-6 py-1.5 sm:py-2.5 rounded-full whitespace-nowrap font-semibold text-[10px] sm:text-sm transition-all duration-300 hover:scale-105 active:scale-95',
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
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-2 sm:gap-3 mb-8">
                <div v-for="task in paginatedTasks" :key="task.id"
                    @click="openTaskModal(task)"
                    class="p-2.5 sm:p-3.5 rounded-xl sm:rounded-2xl bg-light-card dark:bg-gray-700/60 dark:backdrop-blur-xl hover:scale-105 transition-all duration-300 group dark:shadow-2xl border cursor-pointer flex flex-row gap-2 sm:gap-3"
                    :class="getTaskBorderClass(task.category)">

                    <!-- 左側：圖片 -->
                    <div v-if="task.image" class="flex-shrink-0">
                        <div class="w-16 h-16 sm:w-28 sm:h-28 rounded-lg sm:rounded-xl overflow-hidden flex items-center justify-center bg-gradient-to-br from-gray-100 to-gray-200 dark:from-gray-700 dark:to-gray-800">
                            <img :src="task.image" :alt="task.title" class="w-full h-full object-cover">
                        </div>
                    </div>

                    <!-- 右側：內容 -->
                    <div class="flex-1 flex flex-col min-w-0">
                        <!-- 頂部：標題 -->
                        <h3 class="text-sm sm:text-base font-bold text-light-text dark:text-dark-text mb-1 sm:mb-2 truncate">{{ task.title }}</h3>

                        <!-- 標籤 - 手機版隱藏 -->
                        <div class="hidden sm:flex gap-2 mb-2">
                            <!-- 分類標籤 (深色) -->
                            <span :class="getCategoryBadgeClass(task.category)" class="px-2.5 py-1 rounded-full text-xs font-semibold">
                                {{ getCategoryLabelForTask(task.category) }}
                            </span>
                            <!-- 頻率標籤 (淺色) -->
                            <span :class="getFrequencyBadgeClassNew(task.category)" class="px-2.5 py-1 rounded-full text-xs font-semibold">
                                {{ task.frequency }}
                            </span>
                        </div>

                        <!-- 簡潔描述（一兩句）- 手機版只顯示1行 -->
                        <p class="text-[11px] sm:text-xs text-gray-600 dark:text-gray-400 mb-1.5 sm:mb-3 line-clamp-1 sm:line-clamp-2 flex-1">{{
                            task.description }}</p>

                        <!-- 底部區域：積分 + 按鈕（同行） -->
                        <div class="flex gap-1.5 sm:gap-2.5 items-center">
                            <!-- 積分顯示 -->
                            <div class="flex items-baseline gap-0.5 sm:gap-1 flex-1">
                                <span class="text-sm sm:text-lg font-bold" :class="getPointsColorClass(task.category)">+{{ formatPoints(task.points) }}</span>
                                <span class="text-[10px] sm:text-xs text-gray-500 dark:text-gray-400">積分</span>
                            </div>

                            <!-- 完成按鈕 -->
                            <button :disabled="task.completed || isAutoCompleteTask(task.title)" :class="[
                                'px-2 sm:px-3 py-1 sm:py-1.5 rounded-full text-[10px] sm:text-xs font-semibold transition-all duration-300 pointer-events-auto',
                                task.completed
                                    ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                                    : isAutoCompleteTask(task.title)
                                        ? 'bg-gray-200 text-gray-500 cursor-default'
                                        : 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95'
                            ]">
                                {{ task.completed ? '已完成' : isAutoCompleteTask(task.title) ? '自動完成' : '立即完成' }}
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Task Pagination (固定顯示5頁) -->
            <div class="flex items-center justify-center gap-3">
                <button v-for="page in 5" :key="page" @click="isTaskPageAvailable(page) && (currentTaskPage = page)"
                    :disabled="!isTaskPageAvailable(page)" :class="[
                        'w-10 h-10 rounded-full font-semibold text-sm transition-all duration-300',
                        (selectedTaskCategory === 'all' || selectedTaskCategory === '') && page === currentTaskPage || (selectedTaskCategory !== 'all' && selectedTaskCategory !== '' && isTaskPageAvailable(page))
                            ? 'bg-gradient-to-br from-sky-400 to-purple-400 text-white'
                            : isTaskPageAvailable(page)
                                ? 'bg-gray-100 dark:bg-gray-700/60 dark:backdrop-blur-xl text-gray-600 dark:text-gray-400 dark:shadow-xl'
                                : 'bg-gray-200 dark:bg-gray-800 text-gray-400 dark:text-gray-600 cursor-not-allowed opacity-50'
                    ]">
                    {{ page }}
                </button>
            </div>

            <!-- Loading State -->
            <div v-if="isLoading" class="text-center py-12">
                <div class="inline-block w-6 h-6 border-2 border-cyan-500 border-t-transparent rounded-full animate-spin mb-2"></div>
                <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm">
                    載入任務中...
                </p>
            </div>

            <!-- Empty State -->
            <div v-else-if="filteredTasks.length === 0" class="text-center py-12">
                <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm">
                    此分類暫無任務
                </p>
            </div>
        </div>

        <!-- Task Detail Modal -->
        <TaskDetailModal :isOpen="isTaskModalOpen" :task="selectedTask" @close="closeTaskModal" />
    </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { mockTasks } from '../../mock'
import { taskAPI } from '../../api'
import { useStore } from '../../store/app'
import { formatPoints } from '../../utils/formatPoints'
import TaskDetailModal from '../modals/TaskDetailModal.vue'

const store = useStore()

// 任務資料 - 先用 mock 顯示，背景載入後端資料
const tasks = ref(mockTasks)
const isLoading = ref(false)

// 從後端取得任務（背景更新）
const fetchTasks = async () => {
    try {
        const response = await taskAPI.getAll()
        // 後端回傳的欄位轉換為前端格式
        tasks.value = response.data.map(task => ({
            ...task,
            levelPoints: task.upgradePoints,
            rewardPoints: task.rewardPoints,
            points: task.upgradePoints, // 顯示用
            level: task.requiredLevel === 'EXPLORER' ? '全等級' :
                   task.requiredLevel === 'CREATOR' ? 'Lv2+' :
                   task.requiredLevel === 'VISIONARY' ? 'Lv3+' : 'Lv4+',
            completed: false,
            frequency: getFrequencyByTitle(task.title)
        }))
    } catch (error) {
        console.error('Failed to fetch tasks from API, keeping mock data:', error)
        // 失敗時保持 mock 資料，不需要再設定
    }
}

// 根據任務標題判斷頻率
const getFrequencyByTitle = (title) => {
    if (title.includes('每日')) return '每日'
    if (title.includes('連續') && title.includes('七天')) return '每週'
    if (title.includes('邀請')) return '無限'
    if (title.includes('設定') && title.includes('目標')) return '可重複'
    return '一次性'
}

onMounted(() => {
    fetchTasks()
})

// Task Modal State
const isTaskModalOpen = ref(false)
const selectedTask = ref({})

const openTaskModal = (task) => {
    selectedTask.value = task
    isTaskModalOpen.value = true
}

const closeTaskModal = () => {
    isTaskModalOpen.value = false
}

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
        inactiveClass: 'bg-gradient-to-br from-orange-200 to-amber-100 dark:from-orange-900/40 dark:to-amber-800/40 text-amber-600 dark:text-amber-400',
        bgClass: 'bg-gradient-to-br from-orange-50 via-amber-50 to-yellow-50 dark:from-orange-950/30 dark:via-amber-950/30 dark:to-yellow-950/30 border border-orange-200 dark:border-orange-800',
        description: '風險測驗、投資規劃、開戶諮詢'
    },
    {
        id: 'esg',
        label: '永續行動',
        icon: '🌱',
        activeClass: 'bg-gradient-to-br from-emerald-500 to-teal-300 text-white',
        inactiveClass: 'bg-gradient-to-br from-emerald-100 to-teal-50 dark:from-emerald-900/40 dark:to-teal-700/40 text-emerald-600 dark:text-emerald-300',
        bgClass: 'bg-gradient-to-br from-emerald-50/80 to-teal-50/80 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-100 dark:border-emerald-800',
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
        return tasks.value
    }
    return tasks.value.filter(t => t.category === selectedTaskCategory.value)
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


const getTaskBorderClass = (taskCategory) => {
    // 根據當前選擇的任務分類來決定邊框顏色（與說明欄一致）
    const borderClasses = {
        'all': 'border-cyan-200 dark:border-cyan-800',           // 全部任務 - 淺青色
        'daily': 'border-pink-200 dark:border-pink-800',         // 日常互動 - 淺粉紅
        'financial': 'border-indigo-200 dark:border-indigo-800', // 理財學習 - 淺靛藍
        'investment': 'border-orange-200 dark:border-orange-800', // 投資實踐 - 淺橘色
        'esg': 'border-emerald-100 dark:border-emerald-800',     // 永續行動 - 淺翡翠
        'social': 'border-violet-200 dark:border-violet-800'     // 社交分享 - 淺紫色
    }
    return borderClasses[selectedTaskCategory.value] || 'border-cyan-200 dark:border-cyan-800'
}


const getPointsColorClass = (category) => {
    const classes = {
        'daily': 'text-pink-600 dark:text-pink-400',
        'financial': 'text-indigo-600 dark:text-indigo-400',
        'investment': 'text-amber-500 dark:text-amber-400',
        'esg': 'text-emerald-600 dark:text-emerald-400',
        'social': 'text-violet-600 dark:text-violet-400'
    }
    return classes[category] || 'text-gray-600 dark:text-gray-400'
}


// 根據當前選擇的分類返回任務數量的顏色
const getTaskCountColorClass = computed(() => {
    const classes = {
        'all': 'text-cyan-600 dark:text-cyan-400',
        'daily': 'text-pink-600 dark:text-pink-400',
        'financial': 'text-indigo-600 dark:text-indigo-400',
        'investment': 'text-amber-600 dark:text-amber-400',
        'esg': 'text-emerald-500 dark:text-emerald-400',
        'social': 'text-violet-600 dark:text-violet-400'
    }
    return classes[selectedTaskCategory.value] || classes['all']
})

// 任務分類的配色系統 - 參考禮品設計
const getCategoryBadgeClass = (category) => {
    // 淺色背景 (分類標籤，與頻率標籤統一)
    const classes = {
        'daily': 'bg-pink-100/90 dark:bg-pink-900/40 text-pink-700 dark:text-pink-200',
        'financial': 'bg-indigo-100/90 dark:bg-indigo-900/40 text-indigo-700 dark:text-indigo-200',
        'investment': 'bg-amber-100/90 dark:bg-amber-900/40 text-amber-600 dark:text-amber-400',
        'esg': 'bg-emerald-100/90 dark:bg-emerald-900/40 text-emerald-700 dark:text-emerald-200',
        'social': 'bg-violet-100/90 dark:bg-violet-900/40 text-violet-700 dark:text-violet-200'
    }
    return classes[category] || 'bg-gray-100/90 dark:bg-gray-900/40 text-gray-700 dark:text-gray-200'
}

const getFrequencyBadgeClassNew = (category) => {
    // 淺色背景 (分類詳情標籤，如永續環保)
    const classes = {
        'daily': 'bg-pink-100/90 dark:bg-pink-900/40 text-pink-700 dark:text-pink-200',
        'financial': 'bg-indigo-100/90 dark:bg-indigo-900/40 text-indigo-700 dark:text-indigo-200',
        'investment': 'bg-amber-100/90 dark:bg-amber-900/40 text-amber-600 dark:text-amber-400',
        'esg': 'bg-emerald-100/90 dark:bg-emerald-900/40 text-emerald-700 dark:text-emerald-200',
        'social': 'bg-violet-100/90 dark:bg-violet-900/40 text-violet-700 dark:text-violet-200'
    }
    return classes[category] || 'bg-gray-100/90 dark:bg-gray-900/40 text-gray-700 dark:text-gray-200'
}

// 判斷是否為自動完成的登入類任務
const isAutoCompleteTask = (title) => {
    return title === '每日登入' || title === '連續登入七天'
}
</script>
