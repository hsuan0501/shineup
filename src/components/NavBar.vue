<template>
  <nav class="fixed top-0 left-0 right-0 z-40 backdrop-blur-md bg-white/70 dark:bg-white/30 border-b-0 transition-all duration-300">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-20">

        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-6 z-10">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <div @click="scrollToTop" class="flex items-center gap-0.5 text-2xl font-bold cursor-pointer bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent">
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
            <a href="/#tasks" @click="scrollToTasks" class="px-4 py-2 rounded-full bg-light-bg dark:bg-black/95 border border-light-border dark:border-transparent hover:bg-light-border dark:hover:bg-black hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent cursor-pointer backdrop-blur-sm">
              任務清單
            </a>
            <RouterLink to="/rewards" class="px-4 py-2 rounded-full bg-light-bg dark:bg-black/95 border border-light-border dark:border-transparent hover:bg-light-border dark:hover:bg-black hover:scale-110 transition-all duration-300 text-sm font-semibold bg-gradient-to-br from-purple-500 to-cyan-400 bg-clip-text text-transparent backdrop-blur-sm">
              禮品總覽
            </RouterLink>
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
            <!-- 等級卡片 (點擊顯示) -->
            <div class="relative hidden md:block">
              <button @click="showLevelCard = !showLevelCard" class="px-4 py-2 rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 text-sm font-semibold text-primary-purple dark:text-white cursor-pointer backdrop-blur-sm">
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
            
            <!-- 個人資料 -->
            <RouterLink to="/profile" class="px-4 py-2 rounded-full bg-primary-purple text-white hover:bg-purple-700 hover:scale-110 transition-all duration-300 text-sm font-semibold">
              個人資料
            </RouterLink>

            <!-- 登出 -->
            <button @click="logout" class="px-4 py-2 rounded-full border-2 border-primary-purple dark:border-white text-primary-purple dark:text-white hover:bg-primary-purple/10 dark:hover:bg-white/10 hover:scale-110 transition-all duration-300 text-sm font-semibold">
              登出
            </button>
          </div>

          <!-- 未登入狀態 -->
          <div v-else class="flex items-center space-x-3">
            <RouterLink to="/auth/login" class="px-4 py-2 rounded-full border-2 border-primary-purple text-primary-purple hover:bg-primary-purple/10 hover:scale-110 transition-all duration-300 text-sm font-semibold">
              登入
            </RouterLink>
            <RouterLink to="/auth/register" class="px-4 py-2 rounded-full bg-primary-purple text-white hover:bg-purple-700 hover:scale-110 transition-all duration-300 text-sm font-semibold">
              註冊
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { RouterLink, useRouter } from 'vue-router'
import { ref } from 'vue'

const router = useRouter()
const isLoggedIn = ref(true)
const showLevelCard = ref(false)

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
</script>