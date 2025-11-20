<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header Section -->
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text">會員中心</h1>
      <button @click="toggleLogin"
        class="px-6 py-2 rounded-full border border-gray-300 dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors text-sm font-medium text-gray-600 dark:text-gray-300">
        {{ isLoggedIn ? '登出' : '登入' }}
      </button>
    </div>

    <div v-if="isLoggedIn" class="space-y-8">
      <!-- Level Card -->
      <div
        class="relative overflow-hidden rounded-3xl bg-gradient-to-br from-gray-900 to-gray-800 text-white p-8 shadow-2xl">
        <!-- Background Decoration -->
        <div
          class="absolute top-0 right-0 w-64 h-64 bg-primary-purple/20 rounded-full blur-3xl -translate-y-1/2 translate-x-1/3">
        </div>
        <div
          class="absolute bottom-0 left-0 w-64 h-64 bg-primary-blue/20 rounded-full blur-3xl translate-y-1/2 -translate-x-1/3">
        </div>

        <div class="relative z-10 flex flex-col md:flex-row justify-between items-start md:items-center gap-6">
          <div>
            <div class="flex items-center gap-3 mb-2">
              <span class="text-4xl">🌿</span>
              <h2 class="text-3xl font-bold">探索者</h2>
            </div>
            <p class="text-gray-400">開啟你的閃耀旅程</p>
          </div>

          <div class="text-right">
            <div class="text-sm text-gray-400 mb-1">目前積分</div>
            <div
              class="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-primary-purple to-primary-blue">
              {{ user.points }}
            </div>
          </div>
        </div>

        <!-- Progress Bar -->
        <div class="mt-8 relative z-10">
          <div class="flex justify-between text-sm mb-2 text-gray-400">
            <span>進度</span>
            <span>{{ user.points }} / 800</span>
          </div>
          <div class="w-full bg-white/10 rounded-full h-3 overflow-hidden">
            <div
              class="bg-gradient-to-r from-primary-purple to-primary-blue h-full rounded-full transition-all duration-1000 ease-out"
              :style="{ width: `${(user.points / 800) * 100}%` }"></div>
          </div>
          <div class="mt-2 text-sm text-gray-400 text-right">
            距離下一等級「守護者」還差 {{ 800 - user.points }} 分
          </div>
        </div>
      </div>

      <!-- User Info Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Personal Info -->
        <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
          <h3 class="text-xl font-bold mb-4 text-light-text dark:text-dark-text">個人資料</h3>
          <div class="space-y-4">
            <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
              <span class="text-light-text-secondary dark:text-dark-text-secondary">暱稱</span>
              <span class="font-medium text-light-text dark:text-dark-text">{{ user.name }}</span>
            </div>
            <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
              <span class="text-light-text-secondary dark:text-dark-text-secondary">Email</span>
              <span class="font-medium text-light-text dark:text-dark-text">{{ user.email }}</span>
            </div>
            <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
              <span class="text-light-text-secondary dark:text-dark-text-secondary">加入時間</span>
              <span class="font-medium text-light-text dark:text-dark-text">2023-10-24</span>
            </div>
          </div>
        </div>

        <!-- Stats -->
        <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
          <h3 class="text-xl font-bold mb-4 text-light-text dark:text-dark-text">活動統計</h3>
          <div class="grid grid-cols-2 gap-4">
            <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-800/50 text-center">
              <div class="text-2xl font-bold text-primary-purple mb-1">12</div>
              <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">完成任務</div>
            </div>
            <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-800/50 text-center">
              <div class="text-2xl font-bold text-primary-blue mb-1">3</div>
              <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">兌換禮品</div>
            </div>
            <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-800/50 text-center">
              <div class="text-2xl font-bold text-green-500 mb-1">5</div>
              <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">連續登入</div>
            </div>
            <div class="p-4 rounded-xl bg-gray-50 dark:bg-gray-800/50 text-center">
              <div class="text-2xl font-bold text-orange-500 mb-1">Top 10%</div>
              <div class="text-sm text-light-text-secondary dark:text-dark-text-secondary">排名</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Guest State -->
    <div v-else class="text-center py-24">
      <div class="w-24 h-24 mx-auto mb-6 bg-gray-100 dark:bg-gray-800 rounded-full flex items-center justify-center">
        <svg class="w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
        </svg>
      </div>
      <h2 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">尚未登入</h2>
      <p class="text-light-text-secondary dark:text-dark-text-secondary mb-8">登入後查看您的等級與積分</p>
      <button @click="toggleLogin"
        class="inline-block px-8 py-3 rounded-full bg-primary-purple text-white font-semibold hover:bg-primary-purple/90 transition-colors">
        立即登入
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const isLoggedIn = ref(true)

const user = ref({
  name: 'Matcha',
  email: 'matcha@example.com',
  points: 450,
  level: '探索者'
})

const toggleLogin = () => {
  isLoggedIn.value = !isLoggedIn.value
}
</script>