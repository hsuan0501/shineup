<template>
  <nav class="fixed top-0 left-0 right-0 z-40 backdrop-blur-md bg-white/70 dark:bg-dark-card/70 border-b-0 transition-all duration-300">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between h-20">
        
        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-6">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <RouterLink to="/" class="text-2xl font-bold bg-gradient-to-r from-purple-500 to-blue-500 bg-clip-text text-transparent">
              ✨ ShineUp
            </RouterLink>
          </div>

          <!-- Nav Links -->
          <div class="flex items-center space-x-3">
            <RouterLink to="/tasks" class="px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border hover:bg-light-border dark:hover:bg-dark-border transition-colors text-sm font-semibold bg-gradient-to-r from-purple-500 to-blue-500 bg-clip-text text-transparent">
              任務清單
            </RouterLink>
            <RouterLink to="/rewards" class="px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border hover:bg-light-border dark:hover:bg-dark-border transition-colors text-sm font-semibold bg-gradient-to-r from-purple-500 to-blue-500 bg-clip-text text-transparent">
              禮品總覽
            </RouterLink>
          </div>
        </div>

        <!-- Search Bar (中間) -->
        <div class="flex-1 max-w-xl mx-8">
          <div class="relative">
            <input type="text" placeholder="搜尋任務或禮品..." 
              class="w-full px-6 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border text-light-text dark:text-dark-text placeholder-light-text-secondary dark:placeholder-dark-text-secondary focus:outline-none focus:ring-2 focus:ring-primary-purple transition-all">
            <svg class="absolute right-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-light-text-secondary dark:text-dark-text-secondary pointer-events-none" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
        </div>

        <!-- Right Section (登入狀態) -->
        <div class="flex items-center space-x-3 flex-shrink-0">
          <!-- 登入狀態 -->
          <div v-if="isLoggedIn" class="flex items-center space-x-3">
            <!-- 等級卡片 (點擊顯示) -->
            <div class="relative hidden md:block">
              <button @click="showLevelCard = !showLevelCard" class="px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border hover:bg-light-border dark:hover:bg-dark-border transition-colors text-sm font-semibold text-primary-purple cursor-pointer">
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
            <RouterLink to="/profile" class="px-4 py-2 rounded-full bg-primary-purple text-white hover:bg-purple-700 transition-colors text-sm font-semibold">
              個人資料
            </RouterLink>

            <!-- 登出 -->
            <button @click="logout" class="px-4 py-2 rounded-full border-2 border-primary-purple text-primary-purple hover:bg-primary-purple/10 transition-colors text-sm font-semibold">
              登出
            </button>
          </div>

          <!-- 未登入狀態 -->
          <div v-else class="flex items-center space-x-3">
            <RouterLink to="/auth/login" class="px-4 py-2 rounded-full border-2 border-primary-purple text-primary-purple hover:bg-primary-purple/10 transition-colors text-sm font-semibold">
              登入
            </RouterLink>
            <RouterLink to="/auth/register" class="px-4 py-2 rounded-full bg-primary-purple text-white hover:bg-purple-700 transition-colors text-sm font-semibold">
              註冊
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import { ref } from 'vue'

const isLoggedIn = ref(true)
const showLevelCard = ref(false)

const logout = () => {
  isLoggedIn.value = false
  showLevelCard.value = false
}
</script>