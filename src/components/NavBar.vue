<template>
  <nav class="fixed top-0 left-0 right-0 z-40 backdrop-blur-md bg-white/70 dark:bg-white/30 border-b-0 transition-all duration-300">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-20">

        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-6 z-10">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <div @click="scrollToTop" class="flex items-center gap-0.5 text-2xl font-bold cursor-pointer bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent hover:scale-110 transition-all duration-300">
              <span>Shine</span>
              <svg class="w-4 h-4" viewBox="0 0 24 24">
                <defs>
                  <linearGradient id="star-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#a855f7" />
                    <stop offset="100%" stop-color="#22d3ee" />
                  </linearGradient>
                </defs>
                <path d="M12 0 L14 10 L24 12 L14 14 L12 24 L10 14 L0 12 L10 10 Z" fill="url(#star-gradient)"/>
              </svg>
              <span>Up</span>
            </div>
          </div>

          <!-- Nav Links -->
          <div class="flex items-center space-x-3">
            <a href="/#tasks" @click="scrollToTasks" class="px-4 py-2 rounded-full bg-light-bg dark:bg-black/95 border border-light-border dark:border-transparent hover:bg-light-border dark:hover:bg-black hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none cursor-pointer backdrop-blur-sm">
              任務清單
            </a>
            <a href="/#gifts" @click="scrollToGifts" class="px-4 py-2 rounded-full bg-light-bg dark:bg-black/95 border border-light-border dark:border-transparent hover:bg-light-border dark:hover:bg-black hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none cursor-pointer backdrop-blur-sm">
              禮品總覽
            </a>
          </div>
        </div>

        <!-- Search Bar (中間 - 絕對置中) -->
        <div class="absolute left-1/2 transform -translate-x-1/2 w-full max-w-lg px-4">
          <div class="relative">
            <input type="text" placeholder="搜尋任務或禮品..."
              class="w-full px-6 py-2 rounded-full bg-light-bg dark:bg-gray-800/60 border border-light-border dark:border-gray-700/50 text-light-text dark:text-white placeholder-gray-400 dark:placeholder-gray-400 focus:outline-none hover:scale-105 transition-all duration-300 backdrop-blur-sm">
            <svg class="absolute right-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400 dark:text-gray-400 pointer-events-none" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
        </div>

        <!-- Right Section (登入狀態) -->
        <div class="flex items-center space-x-3 flex-shrink-0 z-10">
          <!-- 登入狀態 -->
          <div v-if="isLoggedIn" class="flex items-center space-x-3">
            <!-- 日夜模式切換 -->
            <button @click="toggleDarkMode" class="relative w-16 h-8 rounded-full bg-light-bg dark:bg-gray-800/80 border-2 border-light-border dark:border-gray-700/70 hover:scale-110 transition-all duration-300 backdrop-blur-sm" aria-label="切換日夜模式">

              <!-- 滑動按鈕 -->
              <div :class="[
                'absolute top-1/2 -translate-y-1/2 w-7 h-7 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 transition-all duration-300 flex items-center justify-center shadow-lg',
                isDarkMode ? 'left-[calc(100%-1.75rem)]' : 'left-0.5'
              ]">
                <!-- Sun Icon (亮色模式) -->
                <svg v-if="!isDarkMode" class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM11 1h2v3h-2V1zm0 19h2v3h-2v-3zM3.515 4.929l1.414-1.414L7.05 5.636 5.636 7.05 3.515 4.93zM16.95 18.364l1.414-1.414 2.121 2.121-1.414 1.414-2.121-2.121zm2.121-14.85l1.414 1.415-2.121 2.121-1.414-1.414 2.121-2.121zM5.636 16.95l1.414 1.414-2.121 2.121-1.414-1.414 2.121-2.121zM23 11v2h-3v-2h3zM4 11v2H1v-2h3z"/>
                </svg>
                <!-- Moon Icon (暗色模式) -->
                <svg v-else class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z"/>
                </svg>
              </div>

              <!-- 固定的太陽圖示 (左邊) -->
              <div class="absolute left-2 top-1/2 -translate-y-1/2 pointer-events-none">
                <svg class="w-3.5 h-3.5 text-gray-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8z"/>
                </svg>
              </div>

              <!-- 固定的月亮圖示 (右邊) -->
              <div class="absolute right-2 top-1/2 -translate-y-1/2 pointer-events-none">
                <svg class="w-3.5 h-3.5 text-purple-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z"/>
                </svg>
              </div>
            </button>

            <!-- 等級卡片 (點擊顯示) -->
            <div class="relative hidden md:block">
              <button @click="showLevelCard = !showLevelCard" class="px-4 py-2 rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none cursor-pointer backdrop-blur-sm">
                🌿 探索者
              </button>

              <!-- Dropdown Card -->
              <div v-if="showLevelCard" class="absolute right-0 mt-2 w-72 bg-light-card dark:bg-dark-card rounded-2xl shadow-glow-lg border border-light-border dark:border-dark-border p-6 z-50">
                <!-- 會員卡 -->
                <div class="text-center">
                  <div class="mb-4 text-5xl">🌿</div>
                  <h3 class="text-2xl font-bold text-light-text dark:text-dark-text mb-1">探索者</h3>
                  <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm mb-4">開啟你的閃耀旅程</p>
                  
                  <!-- 積分進度 -->
                  <div class="mb-4">
                    <div class="flex justify-between text-sm mb-2">
                      <span class="text-light-text-secondary dark:text-dark-text-secondary">目前積分</span>
                      <span class="font-bold text-primary-purple">450 / 300</span>
                    </div>
                    <div class="w-full bg-light-bg dark:bg-dark-bg rounded-full h-2">
                      <div class="bg-gradient-to-r from-primary-purple to-primary-blue h-2 rounded-full" style="width: 100%;"></div>
                    </div>
                  </div>

                  <!-- 下一等級 -->
                  <div class="p-3 rounded-lg bg-light-bg dark:bg-dark-bg mb-4 text-sm">
                    <p class="text-light-text-secondary dark:text-dark-text-secondary mb-1">距離下一等級</p>
                    <p class="text-2xl font-bold text-primary-blue">350 分</p>
                  </div>

                  <!-- 權益 -->
                  <div class="text-left text-sm">
                    <p class="font-semibold text-light-text dark:text-dark-text mb-2">權益：</p>
                    <ul class="text-light-text-secondary dark:text-dark-text-secondary space-y-1">
                      <li>✓ 基本兌換功能</li>
                      <li>✓ 入門任務</li>
                      <li>✓ ESG 公益任務</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
            
            <!-- 會員中心 -->
            <RouterLink to="/profile" class="px-4 py-2 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white hover:opacity-90 hover:scale-110 transition-all duration-300 text-sm font-semibold">
              會員中心
            </RouterLink>

            <!-- 登出 -->
            <button @click="logout" class="relative px-4 py-2 rounded-full hover:scale-110 transition-all duration-300 text-sm font-semibold group">
              <div class="absolute inset-0 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 p-[2px]">
                <div class="h-full w-full rounded-full bg-white dark:bg-gray-900 group-hover:bg-purple-500/10 dark:group-hover:bg-white/10 transition-colors"></div>
              </div>
              <span class="relative bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none">登出</span>
            </button>
          </div>

          <!-- 未登入狀態 -->
          <div v-else class="flex items-center space-x-3">
            <RouterLink to="/auth/login" class="px-4 py-2 rounded-full border-2 border-purple-500 dark:border-white hover:bg-purple-500/10 dark:hover:bg-white/10 hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent dark:text-white dark:bg-none">
              登入
            </RouterLink>
            <RouterLink to="/auth/register" class="px-4 py-2 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white hover:opacity-90 hover:scale-110 transition-all duration-300 text-sm font-semibold">
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
import { ref, onMounted } from 'vue'

const router = useRouter()
const isLoggedIn = ref(true)
const showLevelCard = ref(false)
const isDarkMode = ref(false)

// 初始化暗色模式狀態
onMounted(() => {
  isDarkMode.value = document.documentElement.classList.contains('dark')
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

  // 如果不在首頁，再導航
  const currentPath = router.currentRoute.value.path
  if (currentPath !== '/') {
    router.push('/')
  }
}

const scrollToTasks = async (e) => {
  e.preventDefault()

  // 如果不在首頁，先導航到首頁
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
    // 已經在首頁，直接滾動
    const element = document.getElementById('tasks')
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  }
}

const scrollToGifts = async (e) => {
  e.preventDefault()

  // 如果不在首頁，先導航到首頁
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
    // 已經在首頁，直接滾動
    const element = document.getElementById('gifts')
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  }
}
</script>