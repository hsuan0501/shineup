<template>
  <nav
    class="fixed top-0 left-0 right-0 z-40 backdrop-blur-xl backdrop-saturate-150 bg-white/92 dark:bg-gray-900/88 border-b border-gray-200/20 dark:border-white/5 transition-all duration-300 shadow-[0_1px_12px_rgba(0,0,0,0.04)] dark:shadow-[0_1px_12px_rgba(0,0,0,0.2)]">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-20">

        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-6 z-10">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <div @click="scrollToTop"
              class="flex items-center gap-0.5 text-2xl font-bold cursor-pointer bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent hover:scale-110 transition-all duration-300">
              <span>Shine</span>
              <svg class="w-4 h-4" viewBox="0 0 24 24">
                <defs>
                  <linearGradient id="star-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#a855f7" />
                    <stop offset="100%" stop-color="#22d3ee" />
                  </linearGradient>
                </defs>
                <path d="M12 0 L14 10 L24 12 L14 14 L12 24 L10 14 L0 12 L10 10 Z" fill="url(#star-gradient)" />
              </svg>
              <span>Up</span>
            </div>
          </div>

          <!-- Nav Links -->
          <div class="flex items-center space-x-2">
            <a href="/#tasks" @click="scrollToTasks"
              class="px-4 py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold cursor-pointer shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)]">
              <span
                class="bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white">任務清單</span>
            </a>
            <a href="/#gifts" @click="scrollToGifts"
              class="px-4 py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold cursor-pointer shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)]">
              <span
                class="bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white">禮品總覽</span>
            </a>
          </div>
        </div>

        <!-- Search Bar (中間 - 絕對置中) -->
        <div class="absolute left-1/2 transform -translate-x-1/2 w-full max-w-lg px-4">
          <div class="relative">
            <input type="text" placeholder="搜尋任務或禮品..."
              class="w-full px-6 py-2.5 rounded-full bg-white/50 dark:bg-white/10 border border-gray-200/30 dark:border-white/10 text-light-text dark:text-white placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none focus:border-purple-300 dark:focus:border-purple-500/50 focus:bg-white/70 dark:focus:bg-white/15 hover:bg-white/60 dark:hover:bg-white/15 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] focus:shadow-[0_4px_16px_rgba(168,85,247,0.15)] dark:focus:shadow-[0_4px_16px_rgba(168,85,247,0.3)]">
            <svg
              class="absolute right-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400 dark:text-gray-400 pointer-events-none"
              fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
        </div>

        <!-- Right Section (登入狀態) -->
        <div class="flex items-center space-x-3 flex-shrink-0 z-10">
          <!-- 登入狀態 -->
          <div v-if="isLoggedIn" class="flex items-center space-x-3">
            <!-- 日夜模式切換 -->
            <button @click="toggleDarkMode"
              class="relative w-16 h-8 rounded-full bg-white/90 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:scale-105 active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)]"
              aria-label="切換日夜模式">

              <!-- 滑動按鈕 -->
              <div :class="[
                'absolute top-1/2 -translate-y-1/2 w-7 h-7 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 transition-all duration-300 flex items-center justify-center shadow-lg',
                isDarkMode ? 'left-[calc(100%-1.75rem)]' : 'left-0.5'
              ]">
                <!-- Sun Icon (亮色模式) -->
                <svg v-if="!isDarkMode" class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                  <path
                    d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM11 1h2v3h-2V1zm0 19h2v3h-2v-3zM3.515 4.929l1.414-1.414L7.05 5.636 5.636 7.05 3.515 4.93zM16.95 18.364l1.414-1.414 2.121 2.121-1.414 1.414-2.121-2.121zm2.121-14.85l1.414 1.415-2.121 2.121-1.414-1.414 2.121-2.121zM5.636 16.95l1.414 1.414-2.121 2.121-1.414-1.414 2.121-2.121zM23 11v2h-3v-2h3zM4 11v2H1v-2h3z" />
                </svg>
                <!-- Moon Icon (暗色模式) -->
                <svg v-else class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                  <path
                    d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
                </svg>
              </div>

              <!-- 固定的太陽圖示 (左邊) -->
              <div class="absolute left-2 top-1/2 -translate-y-1/2 pointer-events-none">
                <svg class="w-3.5 h-3.5 text-gray-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8z" />
                </svg>
              </div>

              <!-- 固定的月亮圖示 (右邊) -->
              <div class="absolute right-2 top-1/2 -translate-y-1/2 pointer-events-none">
                <svg class="w-3.5 h-3.5 text-purple-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                  <path
                    d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
                </svg>
              </div>
            </button>

            <!-- 購物車按鈕 -->
            <router-link to="/cart"
              class="relative p-2 rounded-full bg-white/90 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] group"
              aria-label="購物車">
              <svg
                class="w-5 h-5 text-gray-600 dark:text-gray-300 group-hover:text-gray-900 dark:group-hover:text-white transition-colors"
                fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
              <!-- 購物車數量小圓點 -->
              <span
                class="absolute -top-1 -right-1 w-5 h-5 bg-gradient-to-br from-purple-500 to-cyan-400 rounded-full flex items-center justify-center text-[10px] text-white font-bold shadow-lg">3</span>
            </router-link>

            <!-- Level Card Dropdown Container -->
            <div ref="levelCardContainer" class="relative">
              <!-- Level Card Trigger -->
              <div @click="toggleLevelCard"
                class="flex items-center gap-3 p-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 hover:opacity-100 opacity-90 cursor-pointer hover:scale-[1.02] transition-all active:scale-95">
                <div class="w-10 h-10 rounded-lg flex items-center justify-center flex-shrink-0 bg-gradient-to-br from-purple-500/10 to-pink-500/10 dark:from-purple-500/20 dark:to-pink-500/20">
                  <svg class="w-6 h-6" fill="none" viewBox="0 0 24 24">
                    <defs>
                      <linearGradient id="luminary-mini" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" stop-color="#a855f7" />
                        <stop offset="100%" stop-color="#ec4899" />
                      </linearGradient>
                    </defs>
                    <path d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" stroke="url(#luminary-mini)" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-sm font-bold text-gray-900 dark:text-white flex items-center gap-1">
                    閃耀者
                    <svg class="w-3 h-3 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                    </svg>
                  </p>
                  <p class="text-xs text-gray-600 dark:text-gray-400">6,000+ 積分</p>
                </div>
              </div>

              <!-- Dropdown Menu -->
              <transition
                enter-active-class="transition duration-200 ease-out"
                enter-from-class="transform scale-95 opacity-0"
                enter-to-class="transform scale-100 opacity-100"
                leave-active-class="transition duration-75 ease-in"
                leave-from-class="transform scale-100 opacity-100"
                leave-to-class="transform scale-95 opacity-0"
              >
                <div v-if="showLevelCard"
                  class="absolute top-full right-0 mt-2 w-80 bg-white dark:bg-gray-900 rounded-2xl shadow-xl border border-gray-200 dark:border-gray-700 overflow-hidden z-50">
                  <!-- Header -->
                  <div class="p-4 bg-gradient-to-br from-purple-500/5 to-pink-500/5 border-b border-gray-100 dark:border-gray-800">
                    <div class="flex items-center justify-between mb-2">
                      <span class="text-xs font-semibold text-purple-500 uppercase tracking-wider">Current Level</span>
                      <span class="text-xs text-gray-500">Level 4</span>
                    </div>
                    <div class="flex items-center gap-4">
                      <div class="w-16 h-16 rounded-2xl bg-gradient-to-br from-purple-500 to-pink-500 flex items-center justify-center shadow-lg shadow-purple-500/20">
                        <svg class="w-10 h-10 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" />
                        </svg>
                      </div>
                      <div>
                        <h3 class="text-lg font-bold text-gray-900 dark:text-white">閃耀者</h3>
                        <p class="text-sm text-gray-500 dark:text-gray-400">Luminary</p>
                      </div>
                    </div>
                  </div>

                  <!-- Progress -->
                  <div class="p-4 border-b border-gray-100 dark:border-gray-800">
                    <div class="flex justify-between text-xs mb-1.5">
                      <span class="text-gray-600 dark:text-gray-400">積分進度</span>
                      <span class="font-medium text-purple-500">6,000 / 10,000</span>
                    </div>
                    <div class="h-2 bg-gray-100 dark:bg-gray-800 rounded-full overflow-hidden">
                      <div class="h-full bg-gradient-to-r from-purple-500 to-pink-500 w-[60%] rounded-full"></div>
                    </div>
                    <p class="mt-2 text-xs text-gray-500 text-center">
                      再獲得 <span class="text-purple-500 font-medium">4,000</span> 積分即可升級
                    </p>
                  </div>

                  <!-- Benefits Preview -->
                  <div class="p-4 bg-gray-50/50 dark:bg-gray-800/30">
                    <h4 class="text-xs font-semibold text-gray-900 dark:text-white mb-3">下個等級權益</h4>
                    <ul class="space-y-2">
                      <li class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400">
                        <svg class="w-4 h-4 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
                        <span>專屬傳奇勳章</span>
                      </li>
                      <li class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400">
                        <svg class="w-4 h-4 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
                        <span>積分加成 1.5x</span>
                      </li>
                    </ul>
                    <RouterLink to="/profile" @click="showLevelCard = false" class="mt-4 block w-full py-2 text-center text-sm font-medium text-purple-600 hover:text-purple-700 dark:text-purple-400 dark:hover:text-purple-300 hover:bg-purple-50 dark:hover:bg-purple-500/10 rounded-lg transition-colors">
                      查看完整權益 →
                    </RouterLink>
                  </div>
                </div>
              </transition>
            </div>

            <!-- 會員中心 -->
            <RouterLink to="/profile"
              class="px-4 py-2 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white hover:opacity-95 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold shadow-[0_4px_12px_rgba(168,85,247,0.3)] hover:shadow-[0_6px_20px_rgba(168,85,247,0.4)]">
              會員中心
            </RouterLink>
          </div>

          <!-- 未登入狀態 -->
          <div v-else class="flex items-center space-x-2">
            <RouterLink to="/auth/login"
              class="px-4 py-2 rounded-full border-2 border-purple-400/50 dark:border-white/30 hover:bg-purple-500/10 dark:hover:bg-white/10 hover:border-purple-500 dark:hover:border-white/50 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)]">
              登入
            </RouterLink>
            <RouterLink to="/auth/register"
              class="px-4 py-2 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white hover:opacity-95 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold shadow-[0_4px_12px_rgba(168,85,247,0.3)] hover:shadow-[0_6px_20px_rgba(168,85,247,0.4)]">
              開始探險
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { ref, onMounted, onUnmounted } from 'vue'

const router = useRouter()
const isLoggedIn = ref(true)
const showLevelCard = ref(false)
const isDarkMode = ref(false)
const levelCardContainer = ref(null)

// 切換等級卡片
const toggleLevelCard = () => {
  showLevelCard.value = !showLevelCard.value
}

// 點擊外部關閉等級卡片
const handleClickOutside = (event) => {
  if (levelCardContainer.value && !levelCardContainer.value.contains(event.target)) {
    showLevelCard.value = false
  }
}

// 初始化暗色模式狀態和事件監聽
onMounted(() => {
  isDarkMode.value = document.documentElement.classList.contains('dark')
  document.addEventListener('click', handleClickOutside)
})

// 清理事件監聽
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
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

const logout = () => {
  isLoggedIn.value = false
  showLevelCard.value = false
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
</script>