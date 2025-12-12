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

          <!-- Nav Links + 通知 -->
          <div class="flex items-center space-x-1 md:space-x-1.5 lg:space-x-2.5">
            <!-- 任務清單按鈕 - 手機/平板為小圓形icon，桌面顯示文字 -->
            <a href="/#tasks" @click="scrollToTasks"
              class="h-8 w-8 lg:h-9 lg:w-auto lg:px-4 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out group flex items-center justify-center"
              aria-label="任務清單">
              <svg class="w-4 h-4 lg:hidden" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <defs>
                  <linearGradient id="task-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#38bdf8" />
                    <stop offset="100%" stop-color="#a855f7" />
                  </linearGradient>
                </defs>
                <path stroke="url(#task-gradient)" stroke-linecap="round" stroke-linejoin="round" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />
              </svg>
              <span class="hidden lg:inline text-sm font-semibold bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">任務清單</span>
            </a>
            <!-- 禮品總覽按鈕 - 手機/平板為小圓形icon，桌面顯示文字 -->
            <a href="/#gifts" @click="scrollToGifts"
              class="h-8 w-8 lg:h-9 lg:w-auto lg:px-4 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out group flex items-center justify-center"
              aria-label="禮品總覽">
              <svg class="w-4 h-4 lg:hidden" viewBox="0 0 24 24" fill="none" stroke-width="2">
                <defs>
                  <linearGradient id="gift-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" stop-color="#38bdf8" />
                    <stop offset="100%" stop-color="#a855f7" />
                  </linearGradient>
                </defs>
                <path stroke="url(#gift-gradient)" stroke-linecap="round" stroke-linejoin="round" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
              <span class="hidden lg:inline text-sm font-semibold bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">禮品總覽</span>
            </a>

            <!-- 通知鈴鐺按鈕（左側）- 用震動動畫提示有通知 -->
            <div class="relative" ref="notificationContainer">
              <button @click.stop="isLoggedIn && notifications.length > 0 ? toggleNotifications() : null"
                :disabled="!isLoggedIn || notifications.length === 0"
                :class="[
                  'notification-bell relative h-8 w-8 lg:h-9 lg:w-9 flex items-center justify-center rounded-full border transition-all duration-300 group',
                  isLoggedIn && notifications.length > 0
                    ? 'bg-gray-50 dark:bg-gray-800/50 border-gray-200 dark:border-gray-700 hover:scale-[1.02] active:scale-95 cursor-pointer'
                    : 'bg-gray-100 dark:bg-gray-800/30 border-gray-200/50 dark:border-gray-700/50 opacity-50 cursor-not-allowed'
                ]"
                aria-label="通知">
                <svg :class="['w-[18px] h-[18px] lg:w-5 lg:h-5', isLoggedIn && notifications.length > 0 ? 'bell-shake' : '']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <defs>
                    <linearGradient id="bell-gradient-left" x1="0%" y1="0%" x2="100%" y2="100%">
                      <stop offset="0%" stop-color="#38bdf8" />
                      <stop offset="100%" stop-color="#a855f7" />
                    </linearGradient>
                  </defs>
                  <path stroke="url(#bell-gradient-left)" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                </svg>
              </button>
              <!-- Notification Dropdown -->
              <transition name="fade-slide">
                <div v-if="showNotifications && isLoggedIn && notifications.length > 0"
                  class="absolute top-full left-0 mt-2 w-72 bg-white dark:bg-gray-800 rounded-2xl shadow-2xl border border-gray-200 dark:border-gray-600/40 overflow-hidden z-50">
                  <div class="max-h-64 overflow-y-auto p-2">
                    <router-link v-for="(notif, index) in notifications" :key="index"
                      :to="notif.link"
                      @click="showNotifications = false"
                      class="flex items-start gap-3 p-3 hover:bg-gray-50 dark:hover:bg-gray-700/50 hover:scale-[1.02] transition-all duration-200 rounded-xl">
                      <div :class="['w-10 h-10 rounded-full flex items-center justify-center flex-shrink-0', notif.iconBg]">
                        <svg class="w-5 h-5" :class="notif.iconColor" fill="none" stroke="currentColor" viewBox="0 0 24 24" v-html="notif.icon"></svg>
                      </div>
                      <div class="flex-1 min-w-0">
                        <p class="font-medium text-gray-900 dark:text-white text-sm">{{ notif.title }}</p>
                        <p class="text-xs text-gray-500 dark:text-gray-400 mt-0.5">{{ notif.description }}</p>
                      </div>
                    </router-link>
                  </div>
                </div>
              </transition>
            </div>
          </div>
        </div>

        <!-- Search Bar (中間 - Desktop only) - 絕對定位置中 -->
        <div class="hidden md:block absolute left-1/2 -translate-x-1/2 w-full max-w-xs lg:max-w-sm xl:max-w-md px-4">
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
        <div class="flex items-center space-x-1 md:space-x-1.5 lg:space-x-2 flex-shrink-0 z-10">
          <!-- 日夜模式切換 - 手機/平板縮小 -->
          <button @click="toggleDarkMode"
            class="relative w-14 h-8 lg:w-16 lg:h-9 rounded-full bg-gray-200/50 dark:bg-gray-800/50 shadow-inner border border-gray-200/30 dark:border-white/10 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out"
            aria-label="切換日夜模式">
            <div :class="[
              'absolute top-1/2 -translate-y-1/2 w-6 h-6 lg:w-7 lg:h-7 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 transition-all duration-300 flex items-center justify-center shadow-md',
              isDarkMode ? 'left-[calc(100%-1.625rem)] lg:left-[calc(100%-1.875rem)]' : 'left-0.5'
            ]">
              <svg v-if="!isDarkMode" class="w-3.5 h-3.5 lg:w-4 lg:h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zM2 13h2a1 1 0 0 0 0-2H2a1 1 0 0 0 0 2zm18 0h2a1 1 0 0 0 0-2h-2a1 1 0 0 0 0 2zM11 2v2a1 1 0 0 0 2 0V2a1 1 0 0 0-2 0zm0 18v2a1 1 0 0 0 2 0v-2a1 1 0 0 0-2 0zM5.99 4.58a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41L5.99 4.58zm12.37 12.37a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41l-1.06-1.06zm1.06-10.96a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06zM7.05 18.36a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06z" />
              </svg>
              <svg v-else class="w-3.5 h-3.5 lg:w-4 lg:h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
              </svg>
            </div>
            <div class="absolute left-1 lg:left-1.5 top-1/2 -translate-y-1/2 pointer-events-none">
              <svg class="w-3 h-3 lg:w-3.5 lg:h-3.5 text-gray-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM11 1h2v3h-2V1zm0 19h2v3h-2v-3zM3.515 4.929l1.414-1.414L7.05 5.636 5.636 7.05 3.515 4.93zM16.95 18.364l1.414-1.414 2.121 2.121-1.414 1.414-2.121-2.121zm2.121-14.85l1.414 1.415-2.121 2.121-1.414-1.414 2.121-2.121zM5.636 16.95l1.414 1.414-2.121 2.121-1.414-1.414 2.121-2.121zM23 11v2h-3v-2h3zM4 11v2H1v-2h3z" />
              </svg>
            </div>
            <div class="absolute right-1 lg:right-1.5 top-1/2 -translate-y-1/2 pointer-events-none">
              <svg class="w-3 h-3 lg:w-3.5 lg:h-3.5 text-purple-400 opacity-50" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 3a9 9 0 1 0 9 9c0-.46-.04-.92-.1-1.36a5.389 5.389 0 0 1-4.4 2.26 5.403 5.403 0 0 1-3.14-9.8c-.44-.06-.9-.1-1.36-.1z" />
              </svg>
            </div>
          </button>

          <!-- Level Card Component (灰色顯示於未登入) -->
          <div :class="{ 'opacity-50 pointer-events-none grayscale': !isLoggedIn }">
            <LevelCard />
          </div>

          <!-- 購物車按鈕 -->
          <router-link v-if="isLoggedIn" to="/cart"
            class="relative h-8 w-8 lg:h-9 lg:w-9 flex items-center justify-center rounded-full bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 hover:scale-[1.02] active:scale-95 transition-all duration-300 group"
            aria-label="購物車">
            <span v-if="store.cartItemCount > 0"
              class="absolute -top-0.5 -right-0.5 lg:-top-1 lg:-right-1 min-w-[16px] h-[16px] lg:min-w-[18px] lg:h-[18px] px-1 bg-pink-500 rounded-full text-white text-[10px] lg:text-[11px] font-bold flex items-center justify-center">
              {{ store.cartItemCount }}
            </span>
            <svg class="w-4 h-4 lg:w-[18px] lg:h-[18px] group-hover:scale-110 transition-transform" viewBox="0 0 24 24" fill="none">
              <defs>
                <linearGradient id="cart-gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stop-color="#38bdf8" />
                  <stop offset="100%" stop-color="#a855f7" />
                </linearGradient>
              </defs>
              <path stroke="url(#cart-gradient)" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
            </svg>
          </router-link>

          <!-- 會員中心按鈕（已登入） / 登入按鈕（未登入） -->
          <RouterLink v-if="isLoggedIn" to="/profile"
            class="h-8 w-8 lg:h-9 lg:w-auto lg:px-4 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-95 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)] border border-transparent flex items-center justify-center"
            aria-label="會員中心">
            <!-- Icon for mobile/tablet -->
            <svg class="w-4 h-4 lg:hidden" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
            <!-- Text for desktop -->
            <span class="hidden lg:inline text-sm font-semibold whitespace-nowrap">會員中心</span>
          </RouterLink>
          <button v-else @click="openLoginModal"
            class="h-8 w-8 lg:h-9 lg:w-auto lg:px-4 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-95 hover:scale-[1.02] active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)] border border-transparent flex items-center justify-center"
            aria-label="登入">
            <!-- Icon for mobile -->
            <svg class="w-4.5 h-4.5 sm:hidden" viewBox="0 0 24 24" fill="currentColor">
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
import { ref, onMounted, onUnmounted, computed } from 'vue'
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
const showNotifications = ref(false)
const notificationContainer = ref(null)

// 使用 store 的認證狀態
const isLoggedIn = computed(() => store.isAuthenticated)

// 等級門檻
const levelThresholds = {
  EXPLORER: { min: 0, max: 249, next: 'CREATOR', nextMin: 250 },
  CREATOR: { min: 250, max: 749, next: 'VISIONARY', nextMin: 750 },
  VISIONARY: { min: 750, max: 1499, next: 'LUMINARY', nextMin: 1500 },
  LUMINARY: { min: 1500, max: Infinity, next: null, nextMin: null }
}

// 通知列表
const notifications = computed(() => {
  if (!store.currentUser) return []
  const user = store.currentUser
  const list = []

  // 升級提醒（固定在最上面，只有非最高等級才顯示）
  const currentLevel = store.currentLevel || 'EXPLORER'
  const threshold = levelThresholds[currentLevel]
  if (threshold && threshold.next) {
    const upgradePoints = store.userPoints?.upgradePoints || 0
    const pointsNeeded = threshold.nextMin - upgradePoints
    if (pointsNeeded > 0) {
      list.push({
        title: '升級提醒',
        description: `再累積 ${pointsNeeded} 積分即可升級至 ${threshold.next}`,
        link: '/profile',
        icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />',
        iconBg: 'bg-purple-100 dark:bg-purple-900/30',
        iconColor: 'text-purple-500'
      })
    }
  }

  // 個人資料未完成通知（合併為一個）
  if (!user.birthday || !user.phone || !user.address) {
    const missing = []
    if (!user.birthday) missing.push('生日')
    if (!user.phone) missing.push('手機')
    if (!user.address) missing.push('地址')
    list.push({
      title: '完善個人資料',
      description: `請填寫${missing.join('、')}以享受完整服務`,
      link: '/settings',
      icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />',
      iconBg: 'bg-sky-100 dark:bg-sky-900/30',
      iconColor: 'text-sky-500'
    })
  }

  return list
})

// 切換通知面板
const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

// 點擊外部關閉通知
const handleNotificationClickOutside = (event) => {
  if (notificationContainer.value && !notificationContainer.value.contains(event.target)) {
    showNotifications.value = false
  }
}

// 打開登入彈窗
const openLoginModal = () => {
  showLoginModal.value = true
}

// 初始化暗色模式狀態和檢查登入狀態
onMounted(() => {
  isDarkMode.value = document.documentElement.classList.contains('dark')
  store.checkAuth() // 檢查是否已登入
  document.addEventListener('click', handleNotificationClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleNotificationClickOutside)
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

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.3s ease;
}

.fade-slide-enter-from,
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

/* 通知鈴鐺 - 震動動畫效果 */
@keyframes bell-shake {
  0%, 100% { transform: rotate(0deg) scale(1); }
  10% { transform: rotate(-8deg) scale(1.1); }
  20% { transform: rotate(8deg) scale(1.1); }
  30% { transform: rotate(-6deg) scale(1.08); }
  40% { transform: rotate(6deg) scale(1.08); }
  50% { transform: rotate(-4deg) scale(1.05); }
  60% { transform: rotate(4deg) scale(1.05); }
  70% { transform: rotate(-2deg) scale(1.02); }
  80% { transform: rotate(2deg) scale(1.02); }
  90% { transform: rotate(0deg) scale(1); }
}

.bell-shake {
  animation: bell-shake 1.5s ease-in-out infinite;
  animation-delay: 2s;
}
</style>
