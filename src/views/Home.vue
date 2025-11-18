<template>
  <div class="w-full" id="home-top">
    <!-- Scroll to Top Button -->
    <button
      @click="scrollToTop"
      class="fixed bottom-8 left-8 sm:left-12 lg:left-16 z-50 h-16 w-16 flex items-center justify-center rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 backdrop-blur-sm"
      aria-label="回到頂部">
      <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
      </svg>
    </button>

    <!-- Customer Service Button -->
    <button
      class="fixed bottom-8 right-8 sm:right-12 lg:right-16 z-50 h-16 w-16 flex items-center justify-center rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 backdrop-blur-sm"
      aria-label="客服">
      <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
      </svg>
    </button>

    <!-- Hero Section -->
    <section class="relative min-h-screen flex flex-col items-center justify-start px-4 sm:px-6 lg:px-8 w-full pt-24">
      <!-- Animated Background with Blobs -->
      <div class="blobs absolute inset-0 overflow-hidden pointer-events-none z-0">
        <div ref="blob1" class="blob blob-1"></div>
        <div ref="blob2" class="blob blob-2"></div>
        <div ref="blob3" class="blob blob-3"></div>
        <div ref="blob4" class="blob blob-4"></div>
        <div ref="blob5" class="blob blob-5"></div>
        <div ref="blob6" class="blob blob-6"></div>
        <div ref="blob7" class="blob blob-7"></div>
      </div>

      <!-- Sparkle Stars -->
      <div class="sparkle-container absolute inset-0 pointer-events-none z-0">
        <div v-for="n in 20" :key="n" class="sparkle" :style="{
          top: `${Math.random() * 100}%`,
          left: `${Math.random() * 100}%`,
          animationDelay: `${Math.random() * 3}s`,
          animationDuration: `${2 + Math.random() * 2}s`
        }"></div>
      </div>

      <!-- Hero Content -->
      <div class="relative z-10 text-center max-w-4xl mx-auto animate-fade-in w-full">
        <div class="text-white drop-shadow-[0_12px_48px_rgba(34,211,238,0.8)] dark:drop-shadow-none">
          <h1 class="hero-title text-5xl sm:text-10xl lg:text-7xl font-light mb-3">
            Shine Level Up
          </h1>
          <p class="hero-subtitle text-lg sm:text-3xl font-light mb-8">
            Keep shining, keep earning.
          </p>
        </div>

        <!-- CTA Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center mb-16">
          <a href="#tasks" @click="scrollToTasks" class="px-8 py-3 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white text-lg font-normal transition-all duration-300 hover:scale-110 cursor-pointer">
            開始探險
          </a>
        </div>
      </div>

      <!-- Scrolling Rewards Section -->
      <div class="relative z-10 w-full overflow-visible mt-8">
        <!-- First Row - Left to Right (Gifts 1-12) -->
        <div class="flex gap-4 animate-scroll-left mb-4">
          <div v-for="gift in [...firstRowGifts, ...firstRowGifts]" :key="gift.id + '-row1'" class="relative flex-shrink-0 w-60 p-2 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 hover:z-50 transition-all duration-300">
            <div class="aspect-square rounded-lg overflow-hidden mb-2">
              <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover" />
            </div>
            <div class="text-center">
              <h4 class="text-xs font-semibold text-light-text dark:text-dark-text line-clamp-1">{{ gift.title }}</h4>
              <p :class="getPointsColorClass(gift.level)" class="text-[10px] font-bold">{{ gift.points }} 積分</p>
            </div>
          </div>
        </div>

        <!-- Second Row - Right to Left (Gifts 13-24) -->
        <div class="flex gap-4 animate-scroll-right">
          <div v-for="gift in [...secondRowGifts, ...secondRowGifts]" :key="gift.id + '-row2'" class="relative flex-shrink-0 w-60 p-2 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 hover:z-50 transition-all duration-300">
            <div class="aspect-square rounded-lg overflow-hidden mb-2">
              <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover" />
            </div>
            <div class="text-center">
              <h4 class="text-xs font-semibold text-light-text dark:text-dark-text line-clamp-1">{{ gift.title }}</h4>
              <p :class="getPointsColorClass(gift.level)" class="text-[10px] font-bold">{{ gift.points }} 積分</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Tasks Section -->
    <section class="w-full py-16 px-4 sm:px-6 lg:px-8">
      <div class="max-w-7xl mx-auto">
        <!-- Task Category Tabs -->
        <div id="tasks" class="flex gap-4 mb-4 overflow-x-auto pb-2 justify-center" style="scroll-margin-top: 80px;">
          <button v-for="cat in taskCategories" :key="cat.id"
            @click="selectTaskCategory(cat.id)"
            type="button"
            :class="[
              'px-6 py-2.5 rounded-full whitespace-nowrap font-semibold text-sm transition-all duration-300 cursor-pointer hover:scale-105 active:scale-95',
              selectedTaskCategory === cat.id
                ? cat.activeClass
                : cat.inactiveClass
            ]">
            {{ cat.label }}
          </button>
        </div>

        <!-- Current Category Info -->
        <div class="mb-4 p-3 rounded-xl" :class="currentTaskCategoryInfo.bgClass">
          <div class="flex justify-between items-center">
            <!-- 左側：分類資訊 -->
            <div class="flex items-center gap-4">
              <h3 class="text-sm font-bold text-light-text dark:text-dark-text">
                {{ currentTaskCategoryInfo.label }}
              </h3>
              <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">
                {{ currentTaskCategoryInfo.description }}
              </span>
            </div>
            <!-- 右側：任務數量 -->
            <div class="flex items-center gap-2">
              <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">任務數量</span>
              <span class="text-sm font-bold text-light-text dark:text-dark-text">{{ filteredTasks.length }} 個</span>
            </div>
          </div>
        </div>

        <!-- Tasks Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
          <div v-for="task in paginatedTasks" :key="task.id"
            class="p-4 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 transition-all duration-300 group cursor-pointer">

            <div class="flex items-start justify-between mb-3">
              <div class="w-10 h-10 rounded-xl flex items-center justify-center" :class="getTaskIconBgClass(task.category)">
                <svg class="w-5 h-5" :class="getTaskIconColorClass(task.category)" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="getTaskIconPath(task.category)" />
                </svg>
              </div>
              <span :class="[
                'px-2.5 py-0.5 rounded-full text-xs font-semibold',
                getCategoryBadgeClassForTask(task.category)
              ]">
                {{ getCategoryLabelForTask(task.category) }}
              </span>
            </div>

            <h3 class="text-base font-bold text-light-text dark:text-dark-text mb-1.5">{{ task.title }}</h3>
            <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary mb-2.5">{{ task.description }}</p>
            
            <div class="flex items-center gap-2 mb-3 text-xs text-light-text-secondary dark:text-dark-text-secondary">
              <span class="px-2 py-0.5 rounded-md bg-light-bg dark:bg-dark-bg">{{ task.frequency }}</span>
              <span class="px-2 py-0.5 rounded-md bg-light-bg dark:bg-dark-bg">{{ task.level }}</span>
            </div>

            <div class="flex items-center justify-between">
              <span class="text-base font-bold text-primary-purple">+{{ task.points }} 分</span>
              <button :disabled="task.completed"
                :class="[
                  'px-3 py-1.5 rounded-full text-sm font-semibold transition-all duration-300',
                  task.completed
                    ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                    : 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90'
                ]">
                {{ task.completed ? '已完成' : '完成' }}
              </button>
            </div>
          </div>
        </div>

        <!-- Task Pagination (固定顯示5頁) -->
        <div class="flex items-center justify-center gap-3">
          <button
            v-for="page in 5"
            :key="page"
            @click="isTaskPageAvailable(page) && (currentTaskPage = page)"
            :disabled="!isTaskPageAvailable(page)"
            :class="[
              'w-10 h-10 rounded-full font-semibold text-sm transition-all duration-300',
              (selectedTaskCategory === 'all' && page === 1) || (selectedTaskCategory !== 'all' && isTaskPageAvailable(page))
                ? 'bg-gradient-to-r from-primary-purple to-primary-blue text-white'
                : isTaskPageAvailable(page)
                ? 'bg-light-card dark:bg-dark-card text-light-text dark:text-dark-text hover:scale-105 border border-light-border dark:border-dark-border cursor-pointer'
                : 'bg-gray-200 dark:bg-gray-800 text-gray-400 dark:text-gray-600 border border-gray-300 dark:border-gray-700 cursor-not-allowed opacity-50'
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

    <!-- Gifts Section -->
    <section class="w-full py-12 px-4 sm:px-6 lg:px-8 pb-16">
      <div class="max-w-7xl mx-auto">
        <!-- Gift Series Tabs -->
        <div id="gifts" class="flex gap-4 mb-4 overflow-x-auto pb-2 justify-center" style="scroll-margin-top: 80px;">
          <button v-for="series in giftSeries" :key="series.id"
            @click="selectSeries(series.id)"
            type="button"
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
              <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">
                {{ currentSeriesInfo.pointRange }}
              </span>
              <div class="text-xs text-light-text dark:text-dark-text opacity-80">
                {{ currentSeriesInfo.restriction }}
              </div>
            </div>
            <!-- 右側：庫存圖例 -->
            <div class="flex items-center gap-3 flex-wrap">
              <div class="flex items-center gap-1">
                <div class="w-2 h-2 rounded-full bg-green-500"></div>
                <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">充足</span>
              </div>
              <div class="flex items-center gap-1">
                <div class="w-2 h-2 rounded-full bg-yellow-500"></div>
                <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">適量</span>
              </div>
              <div class="flex items-center gap-1">
                <div class="w-2 h-2 rounded-full bg-orange-500"></div>
                <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">有限</span>
              </div>
              <div class="flex items-center gap-1">
                <div class="w-2 h-2 rounded-full bg-red-500"></div>
                <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">稀少</span>
              </div>
              <div class="flex items-center gap-1">
                <div class="w-2 h-2 rounded-full bg-gray-500"></div>
                <span class="text-xs font-medium text-light-text-secondary dark:text-dark-text-secondary">已售罄</span>
              </div>
            </div>
          </div>
        </div>



        <!-- Gifts Grid -->
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3 mb-8">
          <div v-for="gift in paginatedGifts" :key="gift.id"
            :class="[
              'group rounded-lg overflow-hidden bg-light-card dark:bg-dark-card hover:scale-105 hover:shadow-xl transition-all duration-300 cursor-pointer',
              !isGiftInSelectedSeries(gift) ? 'opacity-20 grayscale brightness-50 border border-gray-300 dark:border-gray-600' : `border ${getSeriesBorderClass()}`,
            ]">

            <!-- Gift Image -->
            <div class="relative aspect-square overflow-hidden">
              <img :src="gift.image" :alt="gift.title" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300" />

              <!-- Level Badges (Left Top) - Separated Design -->
              <div class="absolute top-2 left-2 flex flex-col gap-1 items-start">
                <!-- Main Level Badge -->
                <div :class="getLevelBadgeClass(gift.level)" class="px-2.5 py-1.5 rounded-full backdrop-blur-md shadow-lg border border-white/20 dark:border-gray-700/50">
                  <div class="text-[11px] font-bold leading-tight tracking-wide">{{ gift.level }}</div>
                </div>
                <!-- Category Label - Independent sizing -->
                <div :class="getCategoryBadgeClass(gift.level)" class="px-3 py-2 rounded-full backdrop-blur-sm shadow-md">
                  <div class="text-xs font-medium text-white leading-none">{{ getCategoryLabel(gift.level) }}</div>
                </div>
              </div>
            </div>

            <!-- Gift Info -->
            <div class="p-3 relative">
              <!-- Stock Dot Indicator (Right Top) -->
              <div class="absolute top-3 right-3">
                <div :class="getStockDotClass(gift.stockStatus)" class="w-2.5 h-2.5 rounded-full"></div>
              </div>

              <h3 class="text-sm font-bold text-light-text dark:text-dark-text mb-2 line-clamp-1 pr-5">
                {{ gift.title }}
              </h3>

              <div class="flex items-center justify-between mb-2">
                <div class="flex items-baseline gap-1">
                  <span class="text-lg font-bold text-primary-purple">{{ gift.points }}</span>
                  <span class="text-xs text-light-text-secondary dark:text-dark-text-secondary">積分</span>
                </div>
                <button 
                  @click="handleExchange(gift)"
                  :class="[
                    'px-4 py-2 rounded-full text-sm font-semibold transition-all duration-300',
                    isGiftInSelectedSeries(gift) && gift.canExchange !== false
                      ? 'bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90'
                      : 'bg-gray-400 dark:bg-gray-700 text-gray-600 dark:text-gray-500 cursor-not-allowed'
                  ]"
                  :disabled="!isGiftInSelectedSeries(gift) || gift.canExchange === false">
                  {{ isGiftInSelectedSeries(gift) && gift.canExchange !== false ? '兌換' : '等級不足' }}
                </button>
              </div>


            </div>
          </div>
        </div>

        <!-- Pagination (固定顯示4頁) -->
        <div class="flex items-center justify-center gap-3">
          <button
            v-for="page in 4"
            :key="page"
            @click="isPageAvailable(page) && (currentPage = page)"
            :disabled="!isPageAvailable(page)"
            :class="[
              'w-10 h-10 rounded-full font-semibold text-sm transition-all duration-300',
              (selectedSeries === 'all' && page === 1) || (selectedSeries !== 'all' && isPageAvailable(page))
                ? 'bg-gradient-to-r from-primary-purple to-primary-blue text-white'
                : isPageAvailable(page)
                ? 'bg-light-card dark:bg-dark-card text-light-text dark:text-dark-text hover:scale-105 border border-light-border dark:border-dark-border cursor-pointer'
                : 'bg-gray-200 dark:bg-gray-800 text-gray-400 dark:text-gray-600 border border-gray-300 dark:border-gray-700 cursor-not-allowed opacity-50'
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
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { mockTasks, mockRewards, mockUsers } from '../mock'

// 用戶資料
const user = ref(mockUsers[1]) // 使用第一個用戶作為範例

// Scroll to Top
const showScrollTop = ref(false)

const handleScroll = () => {
  showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
  // 立即滾動到頂部
  window.scrollTo({ top: 0, behavior: 'smooth' })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
}

// 處理禮品兌換
const handleExchange = (gift) => {
  if (gift.canExchange === false) {
    return
  }
  
  // 檢查用戶積分是否足夠
  if (user.value.points < gift.points) {
    alert('積分不足，無法兌換此禮品')
    return
  }
  
  // 檢查庫存狀態
  if (gift.stockStatus === 'out') {
    alert('此禮品已售罄')
    return
  }
  
  // 確認兌換
  if (confirm(`確定要花費 ${gift.points} 積分兌換「${gift.title}」嗎？`)) {
    // 扣除積分
    user.value.points -= gift.points
    alert(`成功兌換「${gift.title}」！剩餘積分：${user.value.points}`)
  }
}

// Blob refs
const blob1 = ref(null)
const blob2 = ref(null)
const blob3 = ref(null)
const blob4 = ref(null)
const blob5 = ref(null)
const blob6 = ref(null)
const blob7 = ref(null)

// Blob animation
const MIN_SPEED = 0.5
const MAX_SPEED = 2

function randomNumber(min, max) {
  return Math.random() * (max - min) + min
}

class Blob {
  constructor(el) {
    this.el = el
    const boundingRect = this.el.getBoundingClientRect()
    this.size = boundingRect.width
    this.initialX = randomNumber(0, window.innerWidth - this.size)
    this.initialY = randomNumber(0, window.innerHeight - this.size)
    this.el.style.top = `${this.initialY}px`
    this.el.style.left = `${this.initialX}px`
    this.vx = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
    this.vy = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
    this.x = this.initialX
    this.y = this.initialY
  }

  update() {
    this.x += this.vx
    this.y += this.vy
    if (this.x >= window.innerWidth - this.size) {
      this.x = window.innerWidth - this.size
      this.vx *= -1
    }
    if (this.y >= window.innerHeight - this.size) {
      this.y = window.innerHeight - this.size
      this.vy *= -1
    }
    if (this.x <= 0) {
      this.x = 0
      this.vx *= -1
    }
    if (this.y <= 0) {
      this.y = 0
      this.vy *= -1
    }
    this.el.style.transform = `translate(${this.x - this.initialX}px, ${this.y - this.initialY}px)`
  }
}

onMounted(() => {
  // Add scroll event listener
  window.addEventListener('scroll', handleScroll)

  const blobEls = [blob1.value, blob2.value, blob3.value, blob4.value, blob5.value, blob6.value, blob7.value].filter(Boolean)
  const blobs = blobEls.map(el => new Blob(el))

  function update() {
    requestAnimationFrame(update)
    blobs.forEach(blob => blob.update())
  }
  requestAnimationFrame(update)
})

onUnmounted(() => {
  // Remove scroll event listener
  window.removeEventListener('scroll', handleScroll)
})

// 滾動展示的禮品 - 使用 mockRewards 中的前12個和後12個
const firstRowGifts = computed(() => {
  return mockRewards.slice(0, 12) // 禮品 1-12
})

const secondRowGifts = computed(() => {
  return mockRewards.slice(12, 24) // 禮品 13-24
})

// Tasks
const selectedTaskCategory = ref('all')
const currentTaskPage = ref(1)
const itemsPerTaskPage = 6

const taskCategories = [
  {
    id: 'all',
    label: '全部任務',
    icon: '📋',
    activeClass: 'bg-gradient-to-br from-blue-600 to-blue-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-blue-200 to-blue-50 dark:from-blue-900/40 dark:to-blue-700/40 text-blue-700 dark:text-blue-300',
    bgClass: 'bg-gradient-to-br from-blue-50 to-indigo-50 dark:from-blue-950/30 dark:to-indigo-950/30 border border-blue-200 dark:border-blue-800',
    description: '查看所有類型的任務'
  },
  {
    id: 'daily',
    label: '日常互動',
    icon: '📱',
    activeClass: 'bg-gradient-to-br from-pink-600 to-pink-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-pink-200 to-pink-50 dark:from-pink-900/40 dark:to-pink-700/40 text-pink-700 dark:text-pink-300',
    bgClass: 'bg-gradient-to-br from-pink-50 to-rose-50 dark:from-pink-950/30 dark:to-rose-950/30 border border-pink-200 dark:border-pink-800',
    description: '每日登入、分享、互動等簡單任務'
  },
  {
    id: 'financial',
    label: '理財學習',
    icon: '💰',
    activeClass: 'bg-gradient-to-br from-indigo-600 to-blue-500 text-white',
    inactiveClass: 'bg-gradient-to-br from-indigo-200 to-blue-100 dark:from-indigo-900/40 dark:to-blue-800/40 text-indigo-700 dark:text-indigo-300',
    bgClass: 'bg-gradient-to-br from-indigo-50 to-blue-50 dark:from-indigo-950/30 dark:to-blue-950/30 border border-indigo-200 dark:border-indigo-800',
    description: '閱讀文章、觀看影片、完成測驗'
  },
  {
    id: 'investment',
    label: '投資實踐',
    icon: '📊',
    activeClass: 'bg-gradient-to-br from-amber-600 to-amber-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-amber-200 to-amber-50 dark:from-amber-900/40 dark:to-amber-700/40 text-amber-700 dark:text-amber-300',
    bgClass: 'bg-gradient-to-br from-amber-50 via-yellow-50 to-slate-100 dark:from-amber-950/30 dark:via-yellow-950/30 dark:to-slate-900/30 border border-amber-200 dark:border-amber-800',
    description: '風險測驗、投資規劃、開戶諮詢'
  },
  {
    id: 'esg',
    label: '永續行動',
    icon: '🌱',
    activeClass: 'bg-gradient-to-br from-green-600 to-green-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-green-200 to-green-50 dark:from-green-900/40 dark:to-green-700/40 text-green-700 dark:text-green-300',
    bgClass: 'bg-gradient-to-br from-green-50 to-teal-50 dark:from-green-950/30 dark:to-teal-950/30 border border-green-200 dark:border-green-800',
    description: 'ESG學習、碳足跡計算、綠色目標'
  },
  {
    id: 'social',
    label: '社群成就',
    icon: '🎁',
    activeClass: 'bg-gradient-to-br from-purple-600 to-purple-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-purple-200 to-purple-50 dark:from-purple-900/40 dark:to-purple-700/40 text-purple-700 dark:text-purple-300',
    bgClass: 'bg-gradient-to-br from-purple-50 via-pink-50 to-purple-100 dark:from-purple-950/30 dark:via-pink-950/30 dark:to-purple-900/30 border border-purple-200 dark:border-purple-800',
    description: '邀請好友、升級獎勵、參加活動'
  }
]

const currentTaskCategoryInfo = computed(() => {
  return taskCategories.find(c => c.id === selectedTaskCategory.value) || taskCategories[0]
})

const filteredTasks = computed(() => {
  if (selectedTaskCategory.value === 'all') {
    return mockTasks
  }
  return mockTasks.filter(t => t.category === selectedTaskCategory.value)
})

const totalTaskPages = computed(() => {
  return Math.ceil(filteredTasks.value.length / itemsPerTaskPage)
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
  if (selectedTaskCategory.value === 'all') {
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

const getTaskIconPath = (category) => {
  const paths = {
    'daily': 'M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z', // 時鐘圖標
    'financial': 'M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253', // 書本圖標
    'investment': 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z', // 圖表圖標
    'esg': 'M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z', // 地球圖標
    'social': 'M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z' // 用戶群組圖標
  }
  return paths[category] || 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2'
}

// Scroll to tasks section
const scrollToTasks = (e) => {
  e.preventDefault()
  const element = document.querySelector('#tasks')
  if (element) {
    element.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }
}

// Gifts Section
const selectedSeries = ref('all')
const currentPage = ref(1)
const itemsPerPage = 8

const giftSeries = [
  {
    id: 'all',
    level: '',
    levelChinese: '全部禮品',
    activeClass: 'bg-gradient-to-br from-cyan-600 to-cyan-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-cyan-200 to-cyan-50 dark:from-cyan-900/40 dark:to-cyan-700/40 text-cyan-700 dark:text-cyan-300',
    bgClass: 'bg-gradient-to-br from-cyan-50 to-blue-50 dark:from-cyan-950/30 dark:to-blue-950/30 border border-cyan-200 dark:border-cyan-800',
    pointRange: '0-5000+分',
    restriction: '包含所有等級禮物'
  },
  {
    id: 'sustainable',
    level: 'EXPLORER',
    levelChinese: '探索者',
    activeClass: 'bg-gradient-to-br from-emerald-600 to-emerald-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-emerald-200 to-emerald-50 dark:from-emerald-900/40 dark:to-emerald-700/40 text-emerald-700 dark:text-emerald-300',
    bgClass: 'bg-gradient-to-br from-emerald-50 to-teal-50 dark:from-emerald-950/30 dark:to-teal-950/30 border border-emerald-200 dark:border-emerald-800',
    pointRange: '0-299分',
    restriction: 'Lv1+ 可兌換'
  },
  {
    id: 'quality',
    level: 'CREATOR',
    levelChinese: '創造者',
    activeClass: 'bg-gradient-to-br from-indigo-600 to-blue-500 text-white',
    inactiveClass: 'bg-gradient-to-br from-indigo-200 to-blue-100 dark:from-indigo-900/40 dark:to-blue-800/40 text-indigo-700 dark:text-indigo-300',
    bgClass: 'bg-gradient-to-br from-indigo-50 to-blue-50 dark:from-indigo-950/30 dark:to-blue-950/30 border border-indigo-200 dark:border-indigo-800',
    pointRange: '300-799分',
    restriction: 'Lv2+ 可兌換'
  },
  {
    id: 'aesthetic',
    level: 'VISIONARY',
    levelChinese: '先行者',
    activeClass: 'bg-gradient-to-br from-amber-600 to-amber-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-amber-200 to-amber-50 dark:from-amber-900/40 dark:to-amber-700/40 text-amber-700 dark:text-amber-300',
    bgClass: 'bg-gradient-to-br from-amber-50 via-yellow-50 to-slate-100 dark:from-amber-950/30 dark:via-yellow-950/30 dark:to-slate-900/30 border border-amber-200 dark:border-amber-800',
    pointRange: '800-1499分',
    restriction: 'Lv3+ 可兌換'
  },
  {
    id: 'premium',
    level: 'LUMINARY',
    levelChinese: '閃耀者',
    activeClass: 'bg-gradient-to-br from-purple-600 to-purple-400 text-white',
    inactiveClass: 'bg-gradient-to-br from-purple-200 to-purple-50 dark:from-purple-900/40 dark:to-purple-700/40 text-purple-700 dark:text-purple-300',
    bgClass: 'bg-gradient-to-br from-purple-50 via-blue-50 to-purple-100 dark:from-purple-950/30 dark:via-blue-950/30 dark:to-purple-900/30 border border-purple-200 dark:border-purple-800',
    pointRange: '1500+分',
    restriction: 'Lv4 專屬頂級禮物'
  }
]

const currentSeriesInfo = computed(() => {
  return giftSeries.find(s => s.id === selectedSeries.value) || giftSeries[0]
})

const filteredGifts = computed(() => {
  if (selectedSeries.value === 'all') {
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

const totalPages = computed(() => {
  return Math.ceil(filteredGifts.value.length / itemsPerPage)
})

const paginatedGifts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filteredGifts.value.slice(start, end)
})

// 檢查禮物是否可以兌換（不可兌換的會暗掉）
const isGiftInSelectedSeries = (gift) => {
  if (selectedSeries.value === 'all') {
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
  console.log('Selecting series:', seriesId, 'from', selectedSeries.value)
  selectedSeries.value = seriesId
  currentPage.value = 1
  console.log('Series changed to:', selectedSeries.value)
}

// 判斷頁碼是否可用
const isPageAvailable = (page) => {
  if (selectedSeries.value === 'all') {
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
    'VISIONARY': 'bg-gradient-to-br from-amber-200/90 to-amber-100/90 dark:from-amber-800/70 dark:to-amber-700/70 text-amber-700 dark:text-amber-300',
    'LUMINARY': 'bg-gradient-to-br from-purple-200/90 to-purple-100/90 dark:from-purple-800/70 dark:to-purple-700/70 text-purple-700 dark:text-purple-300'
  }
  return classes[level] || 'bg-gray-100/90 dark:bg-gray-800/70 text-gray-600 dark:text-gray-300'
}

const getStockDotClass = (stockStatus) => {
  const classes = {
    'sufficient': 'bg-green-500',
    'moderate': 'bg-yellow-500',
    'limited': 'bg-orange-500',
    'rare': 'bg-red-500',
    'sold_out': 'bg-gray-500'
  }
  return classes[stockStatus] || classes['sufficient']
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
    'VISIONARY': 'bg-gradient-to-br from-amber-600 to-amber-400',
    'LUMINARY': 'bg-gradient-to-br from-purple-600 to-purple-400'
  }
  return classes[level] || 'bg-gray-500'
}

const getPointsColorClass = (level) => {
  const classes = {
    'EXPLORER': 'text-emerald-600 dark:text-emerald-400',
    'CREATOR': 'text-indigo-600 dark:text-indigo-400',
    'VISIONARY': 'text-amber-600 dark:text-amber-400',
    'LUMINARY': 'text-purple-600 dark:text-purple-400'
  }
  return classes[level] || 'text-gray-600 dark:text-gray-400'
}

const getSeriesBorderClass = () => {
  const series = selectedSeries.value
  const borderClasses = {
    'all': 'border-cyan-200 dark:border-cyan-800',
    'sustainable': 'border-emerald-200 dark:border-emerald-800',
    'quality': 'border-indigo-200 dark:border-indigo-800',
    'aesthetic': 'border-amber-200 dark:border-amber-800',
    'premium': 'border-purple-200 dark:border-purple-800'
  }
  return borderClasses[series] || 'border-light-border dark:border-dark-border'
}

const getStockText = (gift) => {
  const actualStock = gift.actualStock

  if (actualStock >= 100) {
    return '充足 99+'
  } else if (actualStock >= 50) {
    return '適量'
  } else if (actualStock >= 10) {
    return '有限'
  } else if (actualStock > 0) {
    return '稀少'
  } else {
    return '已售罄'
  }
}
</script>

<style scoped>
/* Liquid Blobs */
.blobs {
  filter: blur(140px);
}

.blob {
  width: max(240px, 28vw);
  aspect-ratio: 1;
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 0;
}

.blob-1 {
  background: #2ac9de;
}

.blob-2 {
  background: #f087f4;
}

.blob-3 {
  background: color-mix(in srgb, #2ac9de, #f087f4 20%);
}

.blob-4 {
  background: color-mix(in srgb, #2ac9de, #f087f4 35%);
}

.blob-5 {
  background: color-mix(in srgb, #2ac9de, #f087f4 50%);
}

.blob-6 {
  background: color-mix(in srgb, #2ac9de, #f087f4 65%);
}

.blob-7 {
  background: color-mix(in srgb, #2ac9de, #f087f4 80%);
}

/* Hero Title Shimmer Animation */
@keyframes shimmer {
  0% {
    background-position: -200% center;
  }
  100% {
    background-position: 200% center;
  }
}

.animate-scale-in {
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0.2) 0%,
    rgba(255, 255, 255, 0.2) 30%,
    rgba(255, 255, 255, 1) 50%,
    rgba(255, 255, 255, 0.2) 70%,
    rgba(255, 255, 255, 0.2) 100%
  );
  background-size: 200% auto;
  -webkit-background-clip: text;
  background-clip: text;
  animation: shimmer 2s linear infinite;
}

/* Sparkle Stars Animation */
@keyframes sparkle {
  0%, 100% {
    opacity: 0;
    transform: scale(0) rotate(0deg);
  }
  50% {
    opacity: 1;
    transform: scale(1) rotate(180deg);
  }
}

.sparkle-container {
  position: absolute;
  width: 100%;
  height: 100%;
}

.sparkle {
  position: absolute;
  width: 4px;
  height: 4px;
  background: white;
  clip-path: polygon(
    50% 0%,
    61% 35%,
    98% 35%,
    68% 57%,
    79% 91%,
    50% 70%,
    21% 91%,
    32% 57%,
    2% 35%,
    39% 35%
  );
  animation: sparkle 3s ease-in-out infinite;
  filter: drop-shadow(0 0 2px white);
}

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

@keyframes glow-slow {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.5;
  }
  25% {
    transform: translate(200px, -150px) scale(1.2);
    opacity: 0.8;
  }
  50% {
    transform: translate(-100px, -200px) scale(0.9);
    opacity: 0.6;
  }
  75% {
    transform: translate(-200px, 150px) scale(1.1);
    opacity: 0.7;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.5;
  }
}

@keyframes glow-medium {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.4;
  }
  33% {
    transform: translate(-250px, 100px) scale(1.25);
    opacity: 0.75;
  }
  66% {
    transform: translate(150px, 200px) scale(0.85);
    opacity: 0.55;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.4;
  }
}

@keyframes glow-fast {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.35;
  }
  20% {
    transform: translate(180px, 120px) scale(1.15);
    opacity: 0.65;
  }
  40% {
    transform: translate(100px, -180px) scale(1.3);
    opacity: 0.75;
  }
  60% {
    transform: translate(-150px, -100px) scale(0.9);
    opacity: 0.5;
  }
  80% {
    transform: translate(-180px, 140px) scale(1.1);
    opacity: 0.6;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.35;
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

.animate-glow-slow {
  animation: glow-slow 20s ease-in-out infinite;
}

.animate-glow-medium {
  animation: glow-medium 15s ease-in-out infinite;
}

.animate-glow-fast {
  animation: glow-fast 12s ease-in-out infinite;
}

/* Hero Text Breathing Animation */
@keyframes text-breathe {
  0%, 100% {
    opacity: 0.85;
  }
  50% {
    opacity: 1;
  }
}

.hero-title,
.hero-subtitle {
  animation: text-breathe 4s ease-in-out infinite;
  filter: drop-shadow(0 0 30px rgba(255, 255, 255, 0.6))
          drop-shadow(0 0 50px rgba(255, 255, 255, 0.5))
          drop-shadow(0 0 80px rgba(255, 255, 255, 0.4));
}

/* 深色模式下移除光暈 */
.dark .hero-title,
.dark .hero-subtitle {
  filter: none;
}
</style>