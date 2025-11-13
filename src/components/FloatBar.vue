<template>
  <div class="fixed bottom-6 left-1/2 transform -translate-x-1/2 z-50">
    <div class="flex items-center justify-center gap-4 bg-light-card dark:bg-white/30 rounded-full px-6 py-4 shadow-lg border-2 border-light-border dark:border-transparent backdrop-blur-md">

      <!-- 篩選 -->
      <button class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-primary-purple dark:hover:bg-primary-purple hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z" />
        </svg>
      </button>

      <!-- 購物車 -->
      <button class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-primary-blue dark:hover:bg-primary-blue hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
        </svg>
      </button>

      <!-- 收藏 (愛心) -->
      <button class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-red-500 dark:hover:bg-red-500 hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
      </button>

      <!-- AI 客服 -->
      <button class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-emerald-500 dark:hover:bg-emerald-500 hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
        </svg>
      </button>

      <!-- 日夜模式 -->
      <button @click="toggleDarkMode" class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-yellow-400 dark:hover:bg-blue-500 hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg v-if="!isDark" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <circle cx="12" cy="12" r="5" stroke-width="2"/>
          <line x1="12" y1="1" x2="12" y2="3" stroke-width="2" stroke-linecap="round"/>
          <line x1="12" y1="21" x2="12" y2="23" stroke-width="2" stroke-linecap="round"/>
          <line x1="4.22" y1="4.22" x2="5.64" y2="5.64" stroke-width="2" stroke-linecap="round"/>
          <line x1="18.36" y1="18.36" x2="19.78" y2="19.78" stroke-width="2" stroke-linecap="round"/>
          <line x1="1" y1="12" x2="3" y2="12" stroke-width="2" stroke-linecap="round"/>
          <line x1="21" y1="12" x2="23" y2="12" stroke-width="2" stroke-linecap="round"/>
          <line x1="4.22" y1="19.78" x2="5.64" y2="18.36" stroke-width="2" stroke-linecap="round"/>
          <line x1="18.36" y1="5.64" x2="19.78" y2="4.22" stroke-width="2" stroke-linecap="round"/>
        </svg>
        <svg v-else class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
          <path d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
        </svg>
      </button>

      <!-- 分隔線 -->
      <div class="w-px h-8 bg-gray-300 dark:bg-white/20 mx-2"></div>

      <!-- 回到最上 -->
      <button @click="scrollToTop" class="flex items-center justify-center w-12 h-12 rounded-full bg-light-bg dark:bg-white/10 hover:bg-primary-purple dark:hover:bg-primary-purple hover:text-white transition-all duration-300 text-light-text dark:text-white">
        <svg class="w-6 h-6" fill="none" viewBox="0 0 24 24">
          <path stroke="url(#grad2)" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19V5m0 0l-7 7m7-7l7 7" />
          <defs>
            <linearGradient id="grad2" x1="0%" y1="100%" x2="0%" y2="0%">
              <stop offset="0%" style="stop-color:#8b5cf6;stop-opacity:1" />
              <stop offset="100%" style="stop-color:#3b82f6;stop-opacity:1" />
            </linearGradient>
          </defs>
        </svg>
      </button>
    </div>
  </div>
</template>

<script setup>
import { useStore } from '../store/app'
import { computed } from 'vue'

const store = useStore()
const isDark = computed(() => store.isDark)

const toggleDarkMode = () => {
  store.toggleDarkMode()
}

const scrollToTop = () => {
  window.scrollTo(0, 0)
}
</script>