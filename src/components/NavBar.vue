<template>
  <nav
    class="fixed top-0 left-0 right-0 z-40 backdrop-blur-md backdrop-saturate-150 bg-white/40 dark:bg-gray-900/40 transition-all duration-300">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <!-- 第一列：主導航 -->
      <div class="relative flex items-center justify-between h-16 md:h-20">
        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-2 md:space-x-3 lg:space-x-4 xl:space-x-6 z-10">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <div @click="scrollToTop"
              class="flex items-center gap-0.5 text-xl md:text-2xl font-bold cursor-pointer bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent hover:scale-[1.02] transition-all duration-300">
              <span>Shine</span>
              <svg class="w-3.5 h-3.5 md:w-4 md:h-4" viewBox="0 0 24 24">
                <defs>
                  <linearGradient id="star-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#38bdf8" />
                    <stop offset="100%" stop-color="#a855f7" />
                  </linearGradient>
                </defs>
                <path d="M12 0 L14 10 L24 12 L14 14 L12 24 L10 14 L0 12 L10 10 Z" fill="url(#star-gradient)" />
              </svg>
              <span>Up</span>
            </div>
          </div>

          <!-- Nav Links -->
          <div class="flex items-center space-x-1.5 md:space-x-2">
            <a href="/#tasks" @click="scrollToTasks"
              class="p-2 lg:px-4 lg:py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out cursor-pointer group"
              aria-label="任務清單">
              <!-- Icon for mobile/tablet -->
              <svg class="w-4 h-4 lg:hidden" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <defs>
                  <linearGradient id="task-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#38bdf8" />
                    <stop offset="100%" stop-color="#a855f7" />
                  </linearGradient>
                </defs>
                <path stroke="url(#task-gradient)" stroke-linecap="round" stroke-linejoin="round" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />
              </svg>
              <!-- Text for desktop -->
              <span class="hidden lg:inline text-sm font-semibold bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">任務清單</span>
            </a>
            <a href="/#gifts" @click="scrollToGifts"
              class="p-2 lg:px-4 lg:py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out cursor-pointer group"
              aria-label="禮品總覽">
              <!-- Icon for mobile/tablet -->
              <svg class="w-4 h-4 lg:hidden" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <defs>
                  <linearGradient id="gift-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#38bdf8" />
                    <stop offset="100%" stop-color="#a855f7" />
                  </linearGradient>
                </defs>
                <path stroke="url(#gift-gradient)" stroke-linecap="round" stroke-linejoin="round" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
              <!-- Text for desktop -->
              <span class="hidden lg:inline text-sm font-semibold bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">禮品總覽</span>
            </a>
          </div>
        </div>

        <!-- Search Bar (中間 - Desktop only) -->
        <div class="hidden md:flex flex-1 max-w-lg mx-4 lg:mx-8">
          <div class="relative w-full">
            <input type="text" placeholder="搜尋任務或禮品..." :value="store.searchQuery"
              @input="handleSearch"
              @keyup.enter="goToSearch"
              @focus="showSearchSuggestions = true"
              @blur="hideSearchSuggestions"
              class="w-full px-6 py-2.5 rounded-full bg-white/50 dark:bg-white/10 border border-gray-200/30 dark:border-white/10 text-light-text dark:text-white placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none focus:border-purple-300 dark:focus:border-purple-500/50 focus:bg-white/70 dark:focus:bg-white/15 hover:bg-white/60 dark:hover:bg-white/15 transition-all duration-300 ease-out">
            <svg v-if="!store.searchQuery"
              class="absolute right-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400 dark:text-gray-400 pointer-events-none"
              fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
            <button v-else @click="clearSearch"
              class="absolute right-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400 dark:text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
              <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>

            <!-- 搜尋建議下拉 -->
            <div v-if="showSearchSuggestions && store.searchQuery && searchSuggestions.length > 0"
              class="absolute top-full mt-2 w-full bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 shadow-lg overflow-hidden z-50">
              <div v-for="(suggestion, index) in searchSuggestions" :key="index"
                @mousedown="selectSuggestion(suggestion)"
                class="px-4 py-2.5 hover:bg-gray-100 dark:hover:bg-gray-700 cursor-pointer transition-colors flex items-center gap-3">
                <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
                <span class="text-sm text-light-text dark:text-dark-text">{{ suggestion }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Section -->
        <div class="flex items-center space-x-1.5 md:space-x-2.5 lg:space-x-3 xl:space-x-3.5 flex-shrink-0 z-10">
          <!-- 日夜模式切換 -->
          <button @click="toggleDarkMode"
            class="relative w-14 h-7 md:w-16 md:h-8 rounded-full bg-gray-200/50 dark:bg-gray-800/50 shadow-inner border border-gray-200/30 dark:border-white/10 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out"
            aria-label="切換日夜模式">

            <!-- 滑動按鈕 -->
            <div :class="[
              'absolute top-1/2 -translate-y-1/2 w-6 h-6 md:w-7 md:h-7 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 transition-all duration-300 flex items-center justify-center shadow-md',
              isDarkMode ? 'left-[calc(100%-1.5rem)] md:left-[calc(100%-1.75rem)]' : 'left-0.5'
            ]">
              <!-- Sun Icon (亮色模式) -->
              <svg v-if="!isDarkMode" class="w-3.5 h-3.5 md:w-4 md:h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                <path
                  d="M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zM2 13h2a1 1 0 0 0 0-2H2a1 1 0 0 0 0 2zm18 0h2a1 1 0 0 0 0-2h-2a1 1 0 0 0 0 2zM11 2v2a1 1 0 0 0 2 0V2a1 1 0 0 0-2 0zm0 18v2a1 1 0 0 0 2 0v-2a1 1 0 0 0-2 0zM5.99 4.58a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41L5.99 4.58zm12.37 12.37a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41l-1.06-1.06zm1.06-10.96a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06zM7.05 18.36a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06z" />
              </svg>
              <!-- Moon Icon (暗色模式) -->
              <svg v-else class="w-3.5 h-3.5 md:w-4 md:h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                <path
                  d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
              </svg>
            </div>

            <!-- 固定的太陽圖示 (左邊) -->
            <div class="absolute left-1.5 md:left-2 top-1/2 -translate-y-1/2 pointer-events-none">
              <svg class="w-3 h-3 md:w-3.5 md:h-3.5 text-gray-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                <path
                  d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM11 1h2v3h-2V1zm0 19h2v3h-2v-3zM3.515 4.929l1.414-1.414L7.05 5.636 5.636 7.05 3.515 4.93zM16.95 18.364l1.414-1.414 2.121 2.121-1.414 1.414-2.121-2.121zm2.121-14.85l1.414 1.415-2.121 2.121-1.414-1.414 2.121-2.121zM5.636 16.95l1.414 1.414-2.121 2.121-1.414-1.414 2.121-2.121zM23 11v2h-3v-2h3zM4 11v2H1v-2h3z" />
              </svg>
            </div>

            <!-- 固定的月亮圖示 (右邊) -->
            <div class="absolute right-1.5 md:right-2 top-1/2 -translate-y-1/2 pointer-events-none">
              <svg class="w-3 h-3 md:w-3.5 md:h-3.5 text-purple-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                <path
                  d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
              </svg>
            </div>
          </button>

          <!-- Level Card Component (灰色顯示於未登入) - Show on all devices -->
          <div :class="{ 'opacity-50 pointer-events-none grayscale': !isLoggedIn }">
            <LevelCard />
          </div>

          <!-- 購物車按鈕 (灰色顯示於未登入) -->
          <router-link to="/cart" :class="[
            'relative p-2 rounded-full backdrop-blur-sm border hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out group',
            isLoggedIn
              ? 'bg-white/90 dark:bg-gray-900/30 border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40'
              : 'bg-gray-200/50 dark:bg-gray-700/30 border-gray-300/30 dark:border-gray-600/30 opacity-50 pointer-events-none grayscale'
          ]" aria-label="購物車">
            <svg class="w-4 h-4 md:w-5 md:h-5 group-hover:scale-110 transition-transform duration-300" viewBox="0 0 24 24"
              fill="none">
              <defs>
                <linearGradient id="cart-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stop-color="#38bdf8" />
                  <stop offset="100%" stop-color="#a855f7" />
                </linearGradient>
              </defs>
              <path stroke="url(#cart-gradient)" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
            <!-- 購物車数量小圓點 -->
            <span v-if="store.cartItemCount > 0 && isLoggedIn"
              class="absolute -top-1 -right-1 w-4 h-4 md:w-5 md:h-5 bg-pink-400 rounded-full flex items-center justify-center text-[9px] md:text-[10px] text-white font-bold shadow-lg">{{
                store.cartItemCount }}</span>
          </router-link>

          <!-- 會員中心按鈕（已登入） / 登入按鈕（未登入） -->
          <RouterLink v-if="isLoggedIn" to="/profile"
            class="p-2 sm:px-4 sm:py-2 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-95 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)] border border-transparent flex items-center justify-center"
            aria-label="會員中心">
            <!-- Icon for mobile -->
            <svg class="w-4 h-4 sm:hidden" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
            <!-- Text for desktop -->
            <span class="hidden sm:inline text-sm font-semibold whitespace-nowrap">會員中心</span>
          </RouterLink>
          <button v-else @click="openLoginModal"
            class="p-2 sm:px-4 sm:py-2 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-95 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)] border border-transparent flex items-center justify-center"
            aria-label="登入">
            <!-- Icon for mobile -->
            <svg class="w-4 h-4 sm:hidden" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
            <!-- Text for desktop -->
            <span class="hidden sm:inline text-sm font-semibold whitespace-nowrap">登入 / 註冊</span>
          </button>
        </div>
      </div>

      <!-- 第二列：搜尋欄 (Mobile only) -->
      <div class="md:hidden pb-3">
        <div class="relative w-full">
          <input type="text" placeholder="搜尋任務或禮品..." :value="store.searchQuery"
            @input="handleSearch"
            @keyup.enter="goToSearch"
            @focus="showSearchSuggestionsMobile = true"
            @blur="hideSearchSuggestionsMobile"
            class="w-full px-5 py-2 rounded-full bg-white/50 dark:bg-white/10 border border-gray-200/30 dark:border-white/10 text-light-text dark:text-white placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none focus:border-purple-300 dark:focus:border-purple-500/50 focus:bg-white/70 dark:focus:bg-white/15 hover:bg-white/60 dark:hover:bg-white/15 transition-all duration-300 ease-out text-sm">
          <svg v-if="!store.searchQuery"
            class="absolute right-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 dark:text-gray-400 pointer-events-none"
            fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <button v-else @click="clearSearch"
            class="absolute right-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 dark:text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors">
            <svg fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>

          <!-- 搜尋建議下拉 (Mobile) -->
          <div v-if="showSearchSuggestionsMobile && store.searchQuery && searchSuggestions.length > 0"
            class="absolute top-full mt-2 w-full bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 shadow-lg overflow-hidden z-50">
            <div v-for="(suggestion, index) in searchSuggestions" :key="index"
              @mousedown="selectSuggestion(suggestion)"
              class="px-4 py-2.5 hover:bg-gray-100 dark:hover:bg-gray-700 cursor-pointer transition-colors flex items-center gap-3">
              <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
              <span class="text-sm text-light-text dark:text-dark-text">{{ suggestion }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>

  <!-- Login Modal -->
  <LoginModal v-model="showLoginModal" />
</template>

<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { ref, onMounted, computed } from 'vue'
import { useStore } from '../store/app.js'
import LevelCard from './nav/LevelCard.vue'
import LoginModal from './auth/LoginModal.vue'
import { mockTasks, mockRewards } from '../mock'

const router = useRouter()
const store = useStore()
const isDarkMode = ref(false)
const showLoginModal = ref(false)
const showSearchSuggestions = ref(false)
const showSearchSuggestionsMobile = ref(false)

// 使用 store 的認證狀態
const isLoggedIn = computed(() => store.isAuthenticated)

// 打開登入彈窗
const openLoginModal = () => {
  showLoginModal.value = true
}

// 初始化暗色模式狀態和檢查登入狀態
onMounted(() => {
  isDarkMode.value = document.documentElement.classList.contains('dark')
  store.checkAuth() // 檢查是否已登入
})

// 切換日夜模式
const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value
  if (isDarkMode.value) {
    document.documentElement.classList.add('dark')
    localStorage.setItem('theme', 'dark')
  } else {
    document.documentElement.classList.remove('dark')
    localStorage.setItem('theme', 'light')
  }
}

const scrollToTop = () => {
  // 先立即滾動
  window.scrollTo({ top: 0 })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0

  // 如果不在首頁,再導航
  const currentPath = router.currentRoute.value.path
  if (currentPath !== '/') {
    router.push('/')
  }
}

const scrollToTasks = async (e) => {
  e.preventDefault()

  // 如果不在首頁,先導航到首頁
  if (router.currentRoute.value.path !== '/') {
    await router.push('/')
    // 等待頁面渲染
    setTimeout(() => {
      const element = document.getElementById('tasks')
      if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' })
      }
    }, 100)
  } else {
    // 已經在首頁,直接滾動
    const element = document.getElementById('tasks')
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  }
}

const scrollToGifts = async (e) => {
  e.preventDefault()

  // 如果不在首頁,先導航到首頁
  if (router.currentRoute.value.path !== '/') {
    await router.push('/')
    // 等待頁面渲染
    setTimeout(() => {
      const element = document.getElementById('gifts')
      if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' })
      }
    }, 100)
  } else {
    // 已經在首頁,直接滾動
    const element = document.getElementById('gifts')
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  }
}

// 搜尋建議
const searchSuggestions = computed(() => {
  if (!store.searchQuery || store.searchQuery.trim() === '') {
    return []
  }

  const query = store.searchQuery.toLowerCase().trim()
  const suggestions = new Set()

  // 從任務中找建議
  mockTasks.forEach(task => {
    if (task.title.toLowerCase().includes(query)) {
      suggestions.add(task.title)
    }
  })

  // 從禮品中找建議
  mockRewards.forEach(gift => {
    if (gift.title.toLowerCase().includes(query)) {
      suggestions.add(gift.title)
    }
  })

  // 最多顯示 5 個建議
  return Array.from(suggestions).slice(0, 5)
})

// 搜尋功能
const handleSearch = (event) => {
  const value = event.target.value
  store.searchQuery = value
}

const goToSearch = () => {
  if (store.searchQuery && store.searchQuery.trim() !== '') {
    showSearchSuggestions.value = false
    showSearchSuggestionsMobile.value = false
    router.push('/search')
  }
}

const selectSuggestion = (suggestion) => {
  store.searchQuery = suggestion
  showSearchSuggestions.value = false
  showSearchSuggestionsMobile.value = false
  router.push('/search')
}

const hideSearchSuggestions = () => {
  setTimeout(() => {
    showSearchSuggestions.value = false
  }, 200)
}

const hideSearchSuggestionsMobile = () => {
  setTimeout(() => {
    showSearchSuggestionsMobile.value = false
  }, 200)
}

const clearSearch = () => {
  store.searchQuery = ''
  showSearchSuggestions.value = false
  showSearchSuggestionsMobile.value = false
}
</script>
