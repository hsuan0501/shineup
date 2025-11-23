<template>
  <nav
    class="fixed top-0 left-0 right-0 z-40 backdrop-blur-xl backdrop-saturate-150 bg-white/92 dark:bg-gray-900/88 border-b border-gray-200/20 dark:border-white/5 transition-all duration-300 shadow-[0_1px_12px_rgba(0,0,0,0.04)] dark:shadow-[0_1px_12px_rgba(0,0,0,0.2)]">
    <div class="w-full px-4 sm:px-6 lg:px-8">
      <div class="relative flex items-center justify-between h-20">

        <!-- Logo + Nav Links (左邊群組) -->
        <div class="flex items-center space-x-3 lg:space-x-4 xl:space-x-6 z-10">
          <!-- Logo -->
          <div class="flex-shrink-0">
            <div @click="scrollToTop"
              class="flex items-center gap-0.5 text-2xl font-bold cursor-pointer bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent hover:scale-110 transition-all duration-300">
              <span>Shine</span>
              <svg class="w-4 h-4" viewBox="0 0 24 24">
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
          <div class="flex items-center space-x-2">
            <a href="/#tasks" @click="scrollToTasks"
              class="px-4 py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold cursor-pointer shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)]">
              <span
                class="bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">任務清單</span>
            </a>
            <a href="/#gifts" @click="scrollToGifts"
              class="px-4 py-2 rounded-full bg-white/80 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold cursor-pointer shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)]">
              <span
                class="bg-gradient-to-br from-sky-400 to-purple-400 bg-clip-text text-transparent">禮品總覽</span>
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
          <div v-if="isLoggedIn" class="flex items-center space-x-2.5 lg:space-x-3 xl:space-x-3.5">
            <!-- 日夜模式切換 -->
            <button @click="toggleDarkMode"
              class="relative w-16 h-8 rounded-full bg-white/90 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:scale-105 active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)]"
              aria-label="切換日夜模式">

              <!-- 滑動按鈕 -->
              <div :class="[
                'absolute top-1/2 -translate-y-1/2 w-7 h-7 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 transition-all duration-300 flex items-center justify-center shadow-lg',
                isDarkMode ? 'left-[calc(100%-1.75rem)]' : 'left-0.5'
              ]">
                <!-- Sun Icon (亮色模式) -->
                <svg v-if="!isDarkMode" class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12 7a5 5 0 1 0 0 10 5 5 0 0 0 0-10zM2 13h2a1 1 0 0 0 0-2H2a1 1 0 0 0 0 2zm18 0h2a1 1 0 0 0 0-2h-2a1 1 0 0 0 0 2zM11 2v2a1 1 0 0 0 2 0V2a1 1 0 0 0-2 0zm0 18v2a1 1 0 0 0 2 0v-2a1 1 0 0 0-2 0zM5.99 4.58a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41L5.99 4.58zm12.37 12.37a1 1 0 0 0-1.41 1.41l1.06 1.06a1 1 0 0 0 1.41-1.41l-1.06-1.06zm1.06-10.96a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06zM7.05 18.36a1 1 0 0 0-1.41-1.41l-1.06 1.06a1 1 0 0 0 1.41 1.41l1.06-1.06z"/>
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
                  <path d="M12 18a6 6 0 1 1 0-12 6 6 0 0 1 0 12zm0-2a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM11 1h2v3h-2V1zm0 19h2v3h-2v-3zM3.515 4.929l1.414-1.414L7.05 5.636 5.636 7.05 3.515 4.93zM16.95 18.364l1.414-1.414 2.121 2.121-1.414 1.414-2.121-2.121zm2.121-14.85l1.414 1.415-2.121 2.121-1.414-1.414 2.121-2.121zM5.636 16.95l1.414 1.414-2.121 2.121-1.414-1.414 2.121-2.121zM23 11v2h-3v-2h3zM4 11v2H1v-2h3z" />
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



            <!-- Level Card Dropdown Container -->
            <div ref="levelCardContainer" class="relative">
              <!-- Level Card Trigger -->
              <div @click="toggleLevelCard"
                class="flex items-center gap-2 px-4 py-2 rounded-3xl bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 hover:opacity-100 opacity-90 cursor-pointer hover:scale-[1.02] transition-all active:scale-95">
                <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <defs>
                    <linearGradient :id="`level-star-${currentLevel.levelNumber}`" x1="0%" y1="0%" x2="100%" y2="100%">
                      <stop offset="0%" :stop-color="currentLevel.gradientFrom.includes('emerald') ? '#6ee7b7' : currentLevel.gradientFrom.includes('cyan') ? '#67e8f9' : currentLevel.gradientFrom.includes('amber') ? '#fcd34d' : '#c4b5fd'" />
                      <stop offset="100%" :stop-color="currentLevel.gradientTo.includes('emerald') ? '#059669' : currentLevel.gradientTo.includes('blue') ? '#1d4ed8' : currentLevel.gradientTo.includes('amber') ? '#d97706' : '#7c3aed'" />
                    </linearGradient>
                  </defs>
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" :fill="`url(#level-star-${currentLevel.levelNumber})`"/>
                </svg>
                <div class="flex items-center gap-2">
                  <span class="text-sm font-bold text-gray-900 dark:text-white">{{ currentLevel.name }}</span>
                  <div class="flex items-center gap-1">
                    <!-- 升級積分 -->
                    <div class="flex items-center gap-1 px-2 py-1 bg-sky-100 dark:bg-sky-900/30 rounded-full">
                      <svg class="w-3 h-3 text-sky-500" fill="none" viewBox="0 0 24 24">
                        <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="currentColor"/>
                      </svg>
                      <span class="text-xs font-medium text-sky-600 dark:text-sky-400">{{ userLevelPoints.toLocaleString() }}</span>
                    </div>
                    <!-- 兌換積分 -->  
                    <div class="flex items-center gap-1 px-2 py-1 bg-purple-100 dark:bg-purple-900/30 rounded-full">
                      <svg class="w-3 h-3 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z"/>
                        <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clip-rule="evenodd"/>
                      </svg>
                      <span class="text-xs font-medium text-purple-600 dark:text-purple-400">{{ userRewardPoints.toLocaleString() }}</span>
                    </div>
                  </div>
                  <svg class="w-3 h-3 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                  </svg>
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
                  <div :class="[
                    'p-4 border-b border-gray-100 dark:border-gray-800',
                    `bg-gradient-to-br ${currentLevel.gradientFrom}/5 ${currentLevel.gradientTo}/5`
                  ]">
                    <div class="flex items-center justify-between mb-2">
                      <span :class="`text-xs font-semibold uppercase tracking-wider`" :style="`color: ${currentLevel.color}`">Current Level</span>
                      <span class="text-xs text-gray-500">Lv.{{ currentLevel.levelNumber }}</span>
                    </div>
                    <div class="flex items-center gap-4 mb-4">
                      <div :class="[
                        'w-16 h-16 rounded-2xl flex items-center justify-center shadow-lg',
                        `bg-gradient-to-br ${currentLevel.gradientFrom} ${currentLevel.gradientTo}`
                      ]">
                        <svg class="w-8 h-8" fill="none" viewBox="0 0 24 24">
                          <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white"/>
                        </svg>
                      </div>
                      <div>
                        <h3 class="text-lg font-bold text-gray-900 dark:text-white">{{ currentLevel.name }}</h3>
                        <p class="text-sm text-gray-500 dark:text-gray-400">{{ currentLevel.multiplier }}x 積分倍率</p>
                      </div>
                    </div>
                    
                    <!-- 雙軌積分顯示 -->
                    <div class="grid grid-cols-2 gap-3">
                      <!-- 升級積分 -->
                      <div class="bg-sky-50 dark:bg-sky-900/20 rounded-lg p-3">
                        <div class="flex items-center gap-2 mb-1">
                          <svg class="w-4 h-4 text-sky-500" fill="none" viewBox="0 0 24 24">
                            <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="currentColor"/>
                          </svg>
                          <span class="text-xs font-medium text-sky-600 dark:text-sky-400">升級積分</span>
                        </div>
                        <div class="text-lg font-bold text-sky-700 dark:text-sky-300">{{ userLevelPoints.toLocaleString() }}</div>
                        <div class="text-xs text-sky-600 dark:text-sky-400">用於提升等級</div>
                      </div>
                      
                      <!-- 兌換積分 -->
                      <div class="bg-purple-50 dark:bg-purple-900/20 rounded-lg p-3">
                        <div class="flex items-center gap-2 mb-1">
                          <svg class="w-4 h-4 text-purple-500" fill="currentColor" viewBox="0 0 20 20">
                            <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z"/>
                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clip-rule="evenodd"/>
                          </svg>
                          <span class="text-xs font-medium text-purple-600 dark:text-purple-400">兌換積分</span>
                        </div>
                        <div class="text-lg font-bold text-purple-700 dark:text-purple-300">{{ userRewardPoints.toLocaleString() }}</div>
                        <div class="text-xs text-purple-600 dark:text-purple-400">用於兌換禮品</div>
                      </div>
                    </div>
                  </div>

                  <!-- Progress -->
                  <div v-if="nextLevel" class="p-4 border-b border-gray-100 dark:border-gray-800">
                    <div class="flex justify-between text-xs mb-1.5">
                      <span class="text-gray-600 dark:text-gray-400">升級進度</span>
                      <span class="font-medium" :style="`color: ${currentLevel.color}`">{{ userLevelPoints.toLocaleString() }} / {{ nextLevel.minPoints.toLocaleString() }}</span>
                    </div>
                    <div class="h-2 bg-gray-100 dark:bg-gray-800 rounded-full overflow-hidden">
                      <div :class="`h-full bg-gradient-to-r ${currentLevel.gradientFrom} ${currentLevel.gradientTo} rounded-full`" :style="`width: ${progressPercentage}%`"></div>
                    </div>
                    <p class="mt-2 text-xs text-gray-500 text-center">
                      再獲得 <span :style="`color: ${currentLevel.color}`" class="font-medium">{{ (nextLevel.minPoints - userLevelPoints).toLocaleString() }}</span> 升級積分即可升級
                    </p>
                  </div>
                  <div v-else class="p-4 border-b border-gray-100 dark:border-gray-800">
                    <div class="text-center">
                      <span :class="`text-sm font-medium`" :style="`color: ${currentLevel.color}`">🎉 已達最高等級！</span>
                      <p class="text-xs text-gray-500 mt-1">享受所有專屬權益</p>
                    </div>
                  </div>

                  <!-- Benefits Preview -->
                  <div class="p-4 bg-gray-50/50 dark:bg-gray-800/30">
                    <h4 class="text-xs font-semibold text-gray-900 dark:text-white mb-3">當前等級權益</h4>
                    <ul class="space-y-2">
                      <li class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400">
                        <div :class="`w-2 h-2 rounded-full ${currentLevel.gradientFrom.includes('emerald') ? 'bg-emerald-400' : currentLevel.gradientFrom.includes('cyan') ? 'bg-cyan-400' : currentLevel.gradientFrom.includes('amber') ? 'bg-amber-400' : 'bg-purple-400'}`"></div>
                        <span>任務積分 {{ currentLevel.multiplier }} 倍加成</span>
                      </li>
                      <li class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400">
                        <div :class="`w-2 h-2 rounded-full ${currentLevel.gradientFrom.includes('emerald') ? 'bg-emerald-400' : currentLevel.gradientFrom.includes('cyan') ? 'bg-cyan-400' : currentLevel.gradientFrom.includes('amber') ? 'bg-amber-400' : 'bg-purple-400'}`"></div>
                        <span>可兌換<span v-if="currentLevel.level === 'EXPLORER'">「永續探索」</span><span v-else-if="currentLevel.level === 'CREATOR'">「質感創造」</span><span v-else-if="currentLevel.level === 'VISIONARY'">「美學先鋒」</span><span v-else-if="currentLevel.level === 'LUMINARY'">「品味閃耀」</span>系列禮品</span>
                      </li>
                    </ul>
                    <RouterLink to="/profile" @click="showLevelCard = false" :class="`mt-4 block w-full py-2 text-center text-sm font-medium hover:bg-opacity-10 rounded-lg transition-colors`" :style="`color: ${currentLevel.color}; background-color: ${currentLevel.color}15`">
                      查看完整等級權益 →
                    </RouterLink>
                  </div>
                </div>
              </transition>
            </div>

            <!-- 購物車按鈕 -->
            <router-link to="/cart"
              class="relative p-2 rounded-full bg-white/90 dark:bg-gray-900/30 backdrop-blur-sm border border-gray-200/30 dark:border-white/10 hover:bg-white dark:hover:bg-gray-900/40 hover:scale-105 active:scale-95 transition-all duration-300 ease-out shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] group mr-3 lg:mr-4 xl:mr-5"
              aria-label="購物車">
              <svg
                class="w-5 h-5 text-gray-600 dark:text-gray-300 group-hover:text-gray-900 dark:group-hover:text-white transition-colors"
                fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
              <!-- 購物車數量小圓點 -->
              <span
                class="absolute -top-1 -right-1 w-5 h-5 bg-pink-400 rounded-full flex items-center justify-center text-[10px] text-white font-bold shadow-lg">3</span>
            </router-link>

            <!-- 會員中心 -->
            <RouterLink to="/profile"
              class="px-4 py-2 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white hover:opacity-95 hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm font-semibold shadow-[0_2px_8px_rgba(0,0,0,0.04)] dark:shadow-[0_2px_8px_rgba(0,0,0,0.2)] hover:shadow-[0_4px_12px_rgba(0,0,0,0.08)] dark:hover:shadow-[0_4px_12px_rgba(0,0,0,0.3)]">
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
import { ref, onMounted, onUnmounted, computed } from 'vue'
import { levelConfig, mockUsers } from '../mock.js'

const router = useRouter()
const isLoggedIn = ref(true)
const showLevelCard = ref(false)
const isDarkMode = ref(false)
const levelCardContainer = ref(null)

// 雙軌積分系統
const userLevelPoints = computed(() => mockUsers[1]?.levelPoints || 450)
const userRewardPoints = computed(() => mockUsers[1]?.rewardPoints || 680)
const userPoints = computed(() => userLevelPoints.value) // 向後兼容

// 計算當前等級
const currentLevel = computed(() => {
  return levelConfig.find(level => 
    userLevelPoints.value >= level.minPoints && userLevelPoints.value <= level.maxPoints
  ) || levelConfig[0]
})

// 計算下一等級
const nextLevel = computed(() => {
  const currentIndex = levelConfig.findIndex(level => level.level === currentLevel.value.level)
  return currentIndex < levelConfig.length - 1 ? levelConfig[currentIndex + 1] : null
})

// 計算進度百分比
const progressPercentage = computed(() => {
  if (!nextLevel.value) return 100
  const current = userLevelPoints.value - currentLevel.value.minPoints
  const total = nextLevel.value.minPoints - currentLevel.value.minPoints
  return Math.min((current / total) * 100, 100)
})

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