<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Scroll to Top Button -->
    <button @click="scrollToTop"
      class="fixed bottom-6 left-4 sm:left-6 lg:left-8 z-50 h-12 w-12 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 hover:scale-105 active:scale-95 transition-all duration-400 ease-out shadow-[0_4px_16px_rgba(0,0,0,0.08)] dark:shadow-[0_4px_16px_rgba(0,0,0,0.3)] hover:shadow-[0_6px_20px_rgba(0,0,0,0.12)] dark:hover:shadow-[0_6px_20px_rgba(0,0,0,0.4)] backdrop-blur-xl backdrop-saturate-150 group"
      aria-label="回到頂部">
      <svg
        class="w-5 h-5 text-zinc-600 dark:text-gray-300 group-hover:text-zinc-800 dark:group-hover:text-white transition-colors duration-300"
        fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
      </svg>
    </button>
    <!-- 會員中心標題 -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text mb-2">會員中心</h1>
      <p class="text-gray-600 dark:text-gray-400">管理您的積分、等級與獎勵</p>
    </div>

    <!-- 主要內容區域 -->
    <div class="grid grid-cols-1 lg:grid-cols-5 gap-6">
      <!-- 左側：用戶資訊與等級進度 -->
      <div class="lg:col-span-3 space-y-6">
        <!-- 用戶資訊卡片 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30 hover:scale-[1.01] transition-transform duration-300">
          <div class="flex items-start gap-6">
            <!-- 頭像 -->
            <div class="relative">
              <!-- 淺色系漸層邊框 -->
              <div class="w-24 h-24 rounded-full p-[3px] bg-gradient-to-br from-sky-300 to-blue-400">
                <img :src="user.avatar" :alt="user.name"
                  class="w-full h-full rounded-full object-cover cursor-pointer hover:opacity-90 transition-opacity bg-white dark:bg-gray-600"
                  @click="openAvatarUpload" title="點擊更換頭像">
              </div>
              <!-- 隱藏的檔案輸入 -->
              <input ref="avatarInput" type="file" accept="image/*" @change="handleAvatarChange" class="hidden">
            </div>

            <!-- 用戶基本資訊 -->
            <div class="flex-1">
              <div class="flex items-center gap-3 mb-2">
                <h2 class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.name }}</h2>
                <span
                  :class="`px-3 py-1 text-sm font-bold rounded-2xl text-white bg-gradient-to-br ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`">
                  {{ currentLevelConfig.name }} Lv.{{ currentLevelConfig.levelNumber }}
                </span>
              </div>

              <p class="text-gray-600 dark:text-gray-400 mb-3">{{ user.email }}</p>

              <!-- 最近成就徽章 -->
              <div class="flex flex-wrap gap-2 mb-4">
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-green-100 dark:bg-green-900/30 text-green-800 dark:text-green-300 text-xs font-medium rounded-full">
                  連續 7 天
                </div>
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-blue-100 dark:bg-blue-900/30 text-blue-800 dark:text-blue-300 text-xs font-medium rounded-full">
                  新手達人
                </div>
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-purple-100 dark:bg-purple-900/30 text-purple-800 dark:text-purple-300 text-xs font-medium rounded-full">
                  社群達人
                </div>
              </div>

              <!-- 雙軌積分顯示 -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <!-- 升級積分 -->
                <div
                  class="bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 rounded-xl p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-sky-600 dark:text-sky-400">升級積分</span>
                    <div class="flex items-center gap-1 text-sky-600 dark:text-sky-400">
                      <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                        <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z"
                          fill="currentColor" />
                      </svg>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-sky-700 dark:text-sky-300">{{ user.levelPoints }}</div>
                  <div class="text-sm text-sky-600 dark:text-sky-400">用於提升等級</div>
                </div>

                <!-- 兌換積分 -->
                <div
                  class="bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 rounded-xl p-4">
                  <div class="flex items-center justify-between mb-2">
                    <span class="text-sm font-medium text-purple-600 dark:text-purple-400">兌換積分</span>
                    <div class="flex items-center gap-1 text-purple-600 dark:text-purple-400">
                      <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                        <path
                          d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                        <path fill-rule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z"
                          clip-rule="evenodd" />
                      </svg>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-purple-700 dark:text-purple-300">{{ user.rewardPoints }}</div>
                  <div class="text-sm text-purple-600 dark:text-purple-400">用於兌換禮品</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 等級進度 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl px-6 dark:shadow-2xl border dark:border-gray-600/30 hover:scale-[1.01] transition-transform duration-300"
          style="padding-top: 1.875rem; padding-bottom: 1.875rem;">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-4">等級進度</h3>

          <!-- 當前等級資訊 -->
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-xl flex items-center justify-center"
                :class="`bg-gradient-to-br ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`">
                <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white" />
                </svg>
              </div>
              <div>
                <h4 class="font-bold text-gray-900 dark:text-white">{{ currentLevelConfig.name }}</h4>
                <p class="text-sm text-gray-600 dark:text-gray-400">倍率 {{ currentLevelConfig.multiplier }}x</p>
              </div>
            </div>

            <div class="text-right">
              <div class="text-lg font-bold text-gray-900 dark:text-white">{{ user.levelPoints }} / {{ nextLevelPoints
                || '∞' }}</div>
              <div class="text-sm text-gray-600 dark:text-gray-400">升級積分</div>
            </div>
          </div>

          <!-- 進度條 -->
          <div class="mb-4">
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3">
              <div class="h-3 rounded-full transition-all duration-500"
                :class="`bg-gradient-to-r ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`"
                :style="`width: ${levelProgress}%`"></div>
            </div>
            <div class="flex justify-between mt-2 text-sm text-gray-600 dark:text-gray-400">
              <span>{{ currentLevelConfig.minPoints }}</span>
              <span v-if="nextLevelPoints">還需 {{ nextLevelPoints - user.levelPoints }} 分升級</span>
              <span v-else>已達最高等級</span>
              <span>{{ nextLevelPoints || '∞' }}</span>
            </div>
          </div>

          <!-- 當前等級權益 -->
          <div
            class="bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 rounded-xl p-4 mb-4">
            <h5 class="font-bold text-gray-900 dark:text-white mb-3">當前等級權益</h5>
            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <svg class="w-4 h-4 text-sky-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
                <span class="text-sm text-gray-700 dark:text-gray-300">任務積分 {{ currentLevelConfig.multiplier }}
                  倍加成</span>
              </div>
              <div class="flex items-center gap-2">
                <svg class="w-4 h-4 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
                </svg>
                <span class="text-sm text-gray-700 dark:text-gray-300">可兌換<span
                    v-if="user.level === 'EXPLORER'">「永續探索」</span><span
                    v-else-if="user.level === 'CREATOR'">「質感創造」</span><span
                    v-else-if="user.level === 'VISIONARY'">「美學先鋒」</span><span
                    v-else-if="user.level === 'LUMINARY'">「品味閃耀」</span>系列禮品</span>
              </div>
            </div>
          </div>

          <!-- 等級獎勵預覽 -->
          <div v-if="nextLevelConfig" class="bg-gray-50 dark:bg-gray-700/50 rounded-xl p-4">
            <div class="flex items-center gap-3 mb-2">
              <div class="w-8 h-8 rounded-lg flex items-center justify-center"
                :class="`bg-gradient-to-br ${nextLevelConfig.gradientFrom} ${nextLevelConfig.gradientTo}`">
                <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white" />
                </svg>
              </div>
              <div>
                <h5 class="font-bold text-gray-900 dark:text-white">下一等級：{{ nextLevelConfig.name }}</h5>
                <p class="text-sm text-gray-600 dark:text-gray-400">任務積分倍率提升至 {{ nextLevelConfig.multiplier }}x</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 活動統計 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-5 dark:shadow-2xl border dark:border-gray-600/30 flex flex-col h-80 hover:scale-[1.01] transition-transform duration-300">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-3">活動統計</h3>

          <div class="grid grid-cols-2 md:grid-cols-3 gap-3 flex-1 pb-2">
            <!-- 任務完成 -->
            <div class="text-center px-3 py-3 bg-blue-50 dark:bg-blue-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                stroke-width="1.5">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <div class="text-xl font-bold text-blue-600 dark:text-blue-400 mb-1">{{ user.stats?.tasksCompleted || 0 }}
              </div>
              <div class="text-xs text-blue-600/80 dark:text-blue-400/80">已完成任務</div>
            </div>

            <!-- 連續登入 -->
            <div class="text-center px-4 py-3.5 bg-pink-50 dark:bg-pink-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-pink-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <div class="text-xl font-bold text-pink-600 dark:text-pink-400 mb-1">{{ user.stats?.consecutiveDays || 0
              }}</div>
              <div class="text-xs text-pink-600/80 dark:text-pink-400/80">連續登入</div>
            </div>

            <!-- 總登入次數 -->
            <div class="text-center px-4 py-3.5 bg-green-50 dark:bg-green-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                stroke-width="1.5">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
              </svg>
              <div class="text-xl font-bold text-green-600 dark:text-green-400 mb-1">{{ user.stats?.totalLogins || 0 }}
              </div>
              <div class="text-xs text-green-600/80 dark:text-green-400/80">總登入次數</div>
            </div>

            <!-- 已兌換禮品 -->
            <div class="text-center px-4 py-3.5 bg-purple-50 dark:bg-purple-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                stroke-width="1.5">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
              <div class="text-xl font-bold text-purple-600 dark:text-purple-400 mb-1">{{ user.stats?.rewardsRedeemed ||
                0 }}</div>
              <div class="text-xs text-purple-600/80 dark:text-purple-400/80">已兌換禮品</div>
            </div>

            <!-- 邀請好友 -->
            <div class="text-center px-4 py-3.5 bg-orange-50 dark:bg-orange-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                stroke-width="1.5">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
              </svg>
              <div class="text-xl font-bold text-orange-600 dark:text-orange-400 mb-1">{{ user.stats?.friendsInvited ||
                0 }}</div>
              <div class="text-xs text-orange-600/80 dark:text-orange-400/80">邀請好友</div>
            </div>

            <!-- 會員天數 -->
            <div class="text-center px-4 py-3.5 bg-indigo-50 dark:bg-indigo-900/20 rounded-xl">
              <svg class="w-7 h-7 mx-auto mb-2 text-indigo-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                stroke-width="1.5">
                <path stroke-linecap="round" stroke-linejoin="round"
                  d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <div class="text-xl font-bold text-indigo-600 dark:text-indigo-400 mb-1">{{ memberDays }}</div>
              <div class="text-xs text-indigo-600/80 dark:text-indigo-400/80">會員天數</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右側：等級說明與歷史紀錄 -->
      <div class="lg:col-span-2 space-y-6">
        <!-- 等級說明 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30 hover:scale-[1.01] transition-transform duration-300">
          <h3 class="text-xl font-bold text-gray-900 dark:text-white mb-5">等級權益說明</h3>

          <div class="space-y-4">
            <div v-for="levelInfo in levelConfig" :key="levelInfo.level"
              class="px-4 py-5 rounded-xl transition-all border"
              :class="levelInfo.level === user.level ? 'bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 border-sky-200 dark:border-sky-800' : 'bg-gray-50 dark:bg-gray-700/50 border-gray-200 dark:border-gray-600'">
              <div class="flex items-start gap-3">
                <div class="w-10 h-10 rounded-xl flex items-center justify-center flex-shrink-0"
                  :class="`bg-gradient-to-br ${levelInfo.gradientFrom} ${levelInfo.gradientTo}`">
                  <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24">
                    <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white" />
                  </svg>
                </div>
                <div class="flex-1 min-w-0">
                  <div class="flex items-center justify-between mb-2">
                    <h4 class="text-lg font-bold text-gray-900 dark:text-white">{{ levelInfo.name }}</h4>
                    <span class="text-sm font-bold px-2 py-1 rounded-full"
                      :class="`bg-gradient-to-r ${levelInfo.gradientFrom}/20 ${levelInfo.gradientTo}/20 text-gray-700 dark:text-gray-300`">
                      {{ levelInfo.multiplier }}x
                    </span>
                  </div>
                  <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">
                    {{ levelInfo.minPoints }}{{ levelInfo.maxPoints < 999999 ? ' - ' + levelInfo.maxPoints : '+' }} 升級積分
                      </p>
                      <div class="text-xs text-gray-500 dark:text-gray-400 leading-relaxed space-y-1">
                        <div class="flex items-center gap-2">
                          <svg class="w-3 h-3 text-sky-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M13 10V3L4 14h7v7l9-11h-7z" />
                          </svg>
                          <span>任務積分獲得 {{ levelInfo.multiplier }} 倍加成</span>
                        </div>
                        <div class="flex items-center gap-2">
                          <svg class="w-3 h-3 text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
                          </svg>
                          <span>可兌換對應等級系列禮品<span v-if="levelInfo.level === 'EXPLORER'">「永續探索」</span><span
                              v-else-if="levelInfo.level === 'CREATOR'">「質感創造」</span><span
                              v-else-if="levelInfo.level === 'VISIONARY'">「美學先鋒」</span><span
                              v-else-if="levelInfo.level === 'LUMINARY'">「品味閃耀」</span>系列</span>
                        </div>
                      </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 活動紀錄 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-5 dark:shadow-2xl border dark:border-gray-600/30 flex flex-col h-80 hover:scale-[1.01] transition-transform duration-300">
          <div class="flex justify-between items-center mb-3">
            <h3 class="text-xl font-bold text-gray-900 dark:text-white">活動記錄</h3>
            <button
              class="text-sm font-medium text-gray-500 dark:text-gray-500 hover:text-gray-700 dark:hover:text-gray-300 transition-colors">
              查看完整活動紀錄 →
            </button>
          </div>

          <div class="space-y-2.5 flex-1 pb-1">
            <div v-for="record in recentRecords.slice(0, 4)" :key="record.id"
              class="flex items-center gap-3 p-2 bg-gray-50 dark:bg-gray-700/50 rounded-xl">
              <div class="w-9 h-9 rounded-lg flex items-center justify-center flex-shrink-0"
                :class="getRecordStyle(record).bg">
                <div v-html="getRecordIcon(getRecordStyle(record).icon)" :class="getRecordStyle(record).text"></div>
              </div>
              <div class="flex-1 min-w-0">
                <p class="text-sm font-medium text-gray-900 dark:text-white truncate leading-tight">{{ record.title }}
                </p>
                <p class="text-xs text-gray-600 dark:text-gray-400 leading-tight">{{ formatDate(record.date) }}</p>
              </div>
              <div class="text-sm font-bold flex-shrink-0" :class="getRecordStyle(record).text">
                {{ record.type === 'task' ? '+' + record.points : '-' + record.points }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { mockUsers, levelConfig } from '../mock.js'

// 頭像上傳相關
const avatarInput = ref(null)

// 用戶資料
const user = computed(() => mockUsers[1])

// 當前等級配置
const currentLevelConfig = computed(() => {
  return levelConfig.find(level => level.level === user.value.level) || levelConfig[0]
})

// 下一等級配置
const nextLevelConfig = computed(() => {
  const currentIndex = levelConfig.findIndex(level => level.level === user.value.level)
  return currentIndex < levelConfig.length - 1 ? levelConfig[currentIndex + 1] : null
})

// 下一等級所需積分
const nextLevelPoints = computed(() => {
  return nextLevelConfig.value?.minPoints || null
})

// 等級進度百分比
const levelProgress = computed(() => {
  if (!nextLevelPoints.value) return 100
  const current = user.value.levelPoints - currentLevelConfig.value.minPoints
  const total = nextLevelPoints.value - currentLevelConfig.value.minPoints
  return Math.min((current / total) * 100, 100)
})

// 會員天數
const memberDays = computed(() => {
  const joinDate = new Date(user.value.createdAt)
  const now = new Date()
  const diffTime = Math.abs(now - joinDate)
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24))
})

// 模擬最近紀錄
const recentRecords = computed(() => [
  { id: 1, type: 'task', title: '完成每日登入', points: 5, date: '2024-11-24T09:30:00' },
  { id: 2, type: 'reward', title: '兌換環保便攜吸管組', points: 100, date: '2024-11-23T14:25:00' },
  { id: 3, type: 'task', title: '閱讀理財文章', points: 10, date: '2024-11-23T10:15:00' },
  { id: 4, type: 'task', title: '分享到社群', points: 15, date: '2024-11-22T16:45:00' },
  { id: 5, type: 'reward', title: '兌換種子鉛筆組', points: 100, date: '2024-11-21T11:20:00' }
])

// 獲取紀錄樣式
const getRecordStyle = (record) => {
  if (record.type === 'task') {
    // 根據任務類型返回不同顏色
    if (record.title.includes('登入')) {
      return {
        bg: 'bg-pink-100 dark:bg-pink-900/30',
        text: 'text-pink-600 dark:text-pink-400',
        icon: 'login'
      }
    } else if (record.title.includes('理財') || record.title.includes('文章')) {
      return {
        bg: 'bg-amber-100 dark:bg-amber-900/30',
        text: 'text-amber-600 dark:text-amber-400',
        icon: 'article'
      }
    } else if (record.title.includes('分享')) {
      return {
        bg: 'bg-blue-100 dark:bg-blue-900/30',
        text: 'text-blue-600 dark:text-blue-400',
        icon: 'share'
      }
    } else {
      return {
        bg: 'bg-green-100 dark:bg-green-900/30',
        text: 'text-green-600 dark:text-green-400',
        icon: 'task'
      }
    }
  } else {
    // 根據禮品類型返回不同顏色
    if (record.title.includes('環保') || record.title.includes('種子')) {
      return {
        bg: 'bg-green-100 dark:bg-green-900/30',
        text: 'text-green-600 dark:text-green-400',
        icon: 'gift'
      }
    } else {
      return {
        bg: 'bg-purple-100 dark:bg-purple-900/30',
        text: 'text-purple-600 dark:text-purple-400',
        icon: 'gift'
      }
    }
  }
}

// 獲取紀錄圖標
const getRecordIcon = (iconType) => {
  const icons = {
    login: `<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>`,
    article: `<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"/>
              </svg>`,
    share: `<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.368 2.684 3 3 0 00-5.368-2.684z"/>
            </svg>`,
    task: `<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
             <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
           </svg>`,
    gift: `<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
             <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
           </svg>`
  }
  return icons[iconType] || icons.task
}

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = now - date
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24))
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60))
  const diffMinutes = Math.floor(diffTime / (1000 * 60))

  if (diffMinutes < 60) return `${diffMinutes}分鐘前`
  if (diffHours < 24) return `${diffHours}小時前`
  if (diffDays === 0) return '今天 ' + date.toLocaleTimeString('zh-TW', { hour: '2-digit', minute: '2-digit' })
  if (diffDays === 1) return '昨天 ' + date.toLocaleTimeString('zh-TW', { hour: '2-digit', minute: '2-digit' })
  if (diffDays < 7) return `${diffDays}天前`
  return date.toLocaleDateString('zh-TW', { month: 'short', day: 'numeric' }) + ' ' + date.toLocaleTimeString('zh-TW', { hour: '2-digit', minute: '2-digit' })
}

// 打開頭像上傳
const openAvatarUpload = () => {
  avatarInput.value?.click()
}

// 處理頭像更換
const handleAvatarChange = (event) => {
  const file = event.target.files?.[0]
  if (!file) return

  // 檢查檔案類型
  if (!file.type.startsWith('image/')) {
    alert('請選擇圖片檔案')
    return
  }

  // 檢查檔案大小 (5MB 限制)
  if (file.size > 5 * 1024 * 1024) {
    alert('圖片大小不能超過 5MB')
    return
  }

  // 讀取檔案為 Data URL
  const reader = new FileReader()
  reader.onload = (e) => {
    // 這裡可以更新用戶頭像
    // 如果有後端 API，可以在這裡上傳到伺服器
    console.log('新頭像:', e.target?.result)

    // 暫時更新本地顯示 (實際使用時需要更新 mockUsers 或呼叫 API)
    if (mockUsers[1]) {
      mockUsers[1].avatar = e.target?.result
    }

    alert('頭像更新成功！')
  }
  reader.readAsDataURL(file)
}

// Scroll to Top
const showScrollTop = ref(false)

const handleScroll = () => {
  showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    left: 0,
    behavior: 'smooth'
  })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
}
</script>