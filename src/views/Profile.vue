<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 會員中心標題 -->
    <div class="mb-8 flex items-end justify-between">
      <div>
        <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">會員中心</h1>
        <p class="text-sm text-gray-600 dark:text-gray-400">管理您的積分、等級與獎勵</p>
      </div>
      <!-- 設定 & 登出按鈕 -->
      <div class="flex items-center gap-2">
        <!-- 設定按鈕 -->
        <router-link to="/settings"
          class="px-5 py-2.5 rounded-full bg-gray-50/80 dark:bg-gray-800/50 text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700/50 border border-gray-200 dark:border-gray-600 hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          設定
        </router-link>

        <!-- 登出按鈕 -->
        <button @click="handleLogout"
          class="px-5 py-2.5 rounded-full bg-emerald-50/80 dark:bg-emerald-900/20 text-emerald-600 dark:text-emerald-400 hover:bg-emerald-100 dark:hover:bg-emerald-900/30 border border-emerald-200 dark:border-emerald-800 hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          登出
        </button>
      </div>
    </div>

    <!-- 主要內容區域 -->
    <div class="grid grid-cols-1 lg:grid-cols-5 gap-6">
      <!-- 左側：用戶資訊與等級進度 -->
      <div class="lg:col-span-3 space-y-5">
        <!-- 用戶資訊卡片 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <div class="flex items-start gap-6">
            <!-- 頭像 -->
            <div class="relative hover:scale-105 transition-transform duration-300">
              <img :src="user.avatar" :alt="user.name"
                class="w-24 h-24 rounded-full object-cover cursor-pointer hover:opacity-90 transition-opacity bg-white dark:bg-gray-600"
                @click="openAvatarUpload" title="點擊更換頭像">
              <!-- 隱藏的檔案輸入 -->
              <input ref="avatarInput" type="file" accept="image/*" @change="handleAvatarChange" class="hidden">
            </div>

            <!-- 用戶基本資訊 -->
            <div class="flex-1">
              <div class="flex items-center gap-3 mb-2">
                <h2 class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.name }}</h2>
                <span
                  :class="`px-3 py-1 text-sm font-bold rounded-2xl text-white bg-gradient-to-br ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo} hover:scale-105 transition-transform duration-300 cursor-default`">
                  Lv{{ currentLevelConfig.levelNumber }} {{ currentLevelConfig.level }}
                </span>
              </div>

              <p class="text-gray-600 dark:text-gray-400 mb-3">{{ user.email }}</p>

              <!-- 最近成就徽章 -->
              <div class="flex justify-between gap-2 mb-4 md:w-1/2 md:pr-2">
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-green-100 dark:bg-green-900/30 text-green-800 dark:text-green-300 text-xs font-medium rounded-full hover:scale-110 transition-transform duration-300 cursor-default">
                  連續 7 天
                </div>
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-blue-100 dark:bg-blue-900/30 text-blue-800 dark:text-blue-300 text-xs font-medium rounded-full hover:scale-110 transition-transform duration-300 cursor-default">
                  新手達人
                </div>
                <div
                  class="inline-flex items-center px-2.5 py-1 bg-purple-100 dark:bg-purple-900/30 text-purple-800 dark:text-purple-300 text-xs font-medium rounded-full hover:scale-110 transition-transform duration-300 cursor-default">
                  社群達人
                </div>
              </div>

              <!-- 雙軌積分顯示 -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <!-- 升級積分 -->
                <div
                  class="bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 rounded-xl p-4 hover:scale-[1.02] transition-transform duration-300">
                  <div class="flex items-center mb-2">
                    <div class="flex items-center gap-1 text-sky-600 dark:text-sky-400">
                      <svg class="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24">
                        <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z"
                          fill="currentColor" />
                      </svg>
                      <span class="text-sm font-medium">升級積分</span>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-sky-700 dark:text-sky-300 leading-tight">{{ user.levelPoints }}</div>
                  <div class="text-sm text-sky-600 dark:text-sky-400 mt-1">用於提升等級</div>
                </div>

                <!-- 兌換積分 -->
                <div
                  class="bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 rounded-xl p-4 hover:scale-[1.02] transition-transform duration-300">
                  <div class="flex items-center mb-2">
                    <div class="flex items-center gap-1 text-purple-600 dark:text-purple-400">
                      <svg class="w-3.5 h-3.5" fill="currentColor" viewBox="0 0 20 20">
                        <path
                          d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                        <path fill-rule="evenodd"
                          d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z"
                          clip-rule="evenodd" />
                      </svg>
                      <span class="text-sm font-medium">兌換積分</span>
                    </div>
                  </div>
                  <div class="text-2xl font-bold text-purple-700 dark:text-purple-300 leading-tight">{{ user.rewardPoints }}</div>
                  <div class="text-sm text-purple-600 dark:text-purple-400 mt-1">用於兌換禮品</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 等級進度 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">等級進度</h3>

          <!-- 當前等級資訊 -->
          <div class="flex items-center justify-between mb-4">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-xl flex items-center justify-center hover:scale-110 transition-transform duration-300"
                :class="`bg-gradient-to-br ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`">
                <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24">
                  <path d="M12 1 L14.5 10.5 L24 12 L14.5 13.5 L12 23 L9.5 13.5 L0 12 L9.5 10.5 Z" fill="white" />
                </svg>
              </div>
              <div>
                <h4 class="text-lg font-bold text-gray-900 dark:text-white">Lv{{ currentLevelConfig.levelNumber }} {{ currentLevelConfig.level }}</h4>
                <p class="text-sm text-gray-600 dark:text-gray-400">倍率 {{ currentLevelConfig.multiplier }}x</p>
              </div>
            </div>

            <div class="text-right">
              <div class="text-lg font-bold" :class="
                currentLevelConfig.level === 'EXPLORER' ? 'text-emerald-500 dark:text-emerald-400' :
                currentLevelConfig.level === 'CREATOR' ? 'text-blue-500 dark:text-blue-400' :
                currentLevelConfig.level === 'VISIONARY' ? 'text-amber-500 dark:text-amber-400' :
                'text-purple-500 dark:text-purple-400'
              ">
                {{ user.levelPoints }} / {{ nextLevelPoints || '∞' }}
              </div>
              <div class="text-sm text-gray-600 dark:text-gray-400">升級積分</div>
            </div>
          </div>

          <!-- 進度條 -->
          <div class="mb-4">
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3 hover:scale-[1.01] transition-transform duration-300">
              <div class="h-3 rounded-full transition-all duration-500"
                :class="`bg-gradient-to-r ${currentLevelConfig.gradientFrom} ${currentLevelConfig.gradientTo}`"
                :style="`width: ${levelProgress}%`"></div>
            </div>
            <div class="flex justify-between mt-2 text-sm text-gray-600 dark:text-gray-400">
              <span>{{ currentLevelConfig.minPoints }}</span>
              <span>{{ nextLevelPoints || '∞' }}</span>
            </div>
          </div>

          <!-- 升級提醒 -->
          <div v-if="nextLevelPoints && nextLevelPoints !== '∞'" class="mb-4 px-4 py-3 bg-amber-50 dark:bg-amber-900/20 border border-amber-200 dark:border-amber-800/30 rounded-xl hover:scale-[1.02] transition-transform duration-300">
            <p class="text-sm text-amber-800 dark:text-amber-300">
              再獲得 <span class="font-bold" :class="
                currentLevelConfig.level === 'EXPLORER' ? 'text-emerald-500 dark:text-emerald-400' :
                currentLevelConfig.level === 'CREATOR' ? 'text-blue-500 dark:text-blue-400' :
                currentLevelConfig.level === 'VISIONARY' ? 'text-amber-500 dark:text-amber-400' :
                'text-purple-500 dark:text-purple-400'
              ">{{ nextLevelPoints - user.levelPoints }}</span> 積分即可升級為「{{ nextLevelConfig.name }}」！
            </p>
          </div>
          <div v-else-if="!nextLevelPoints || nextLevelPoints === '∞'" class="mb-4 px-4 py-3 bg-gradient-to-r from-purple-50 to-pink-50 dark:from-purple-900/20 dark:to-pink-900/20 border border-purple-200 dark:border-purple-800/30 rounded-xl hover:scale-[1.02] transition-transform duration-300">
            <p class="text-sm text-purple-800 dark:text-purple-300 font-medium">
              🎉 恭喜！您已達到最高等級
            </p>
          </div>

          <!-- 當前等級權益 -->
          <div class="bg-gradient-to-r from-sky-50 to-indigo-50 dark:from-sky-900/20 dark:to-indigo-900/20 border border-sky-200 dark:border-sky-800 rounded-xl px-5 py-4 hover:scale-[1.02] transition-transform duration-300">
            <h5 class="font-bold text-gray-900 dark:text-white mb-2">當前等級權益</h5>
            <div class="space-y-1.5">
              <div class="flex items-center gap-2">
                <svg class="w-4 h-4 text-sky-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
                <span class="text-sm text-gray-700 dark:text-gray-300">任務積分 {{ currentLevelConfig.multiplier }} 倍加成</span>
              </div>
              <div class="flex items-center gap-2">
                <svg class="w-4 h-4 text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
                </svg>
                <span class="text-sm text-gray-700 dark:text-gray-300">可兌換<span v-if="currentLevelConfig.level === 'EXPLORER'">「永續探索」</span><span
                    v-else-if="currentLevelConfig.level === 'CREATOR'">「質感創造」</span><span
                    v-else-if="currentLevelConfig.level === 'VISIONARY'">「美學先鋒」</span><span
                    v-else-if="currentLevelConfig.level === 'LUMINARY'">「品味閃耀」</span>系列禮品</span>
              </div>
            </div>
          </div>
        </div>

      </div>

      <!-- 右側：等級說明 -->
      <div class="lg:col-span-2">
        <!-- 等級說明 -->
        <div
          class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">等級權益說明</h3>

          <div class="space-y-3">
            <div v-for="levelInfo in levelConfig" :key="levelInfo.level"
              class="px-4 py-4 rounded-xl transition-all border hover:scale-[1.02]"
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
                    <h4 class="text-base font-medium text-gray-900 dark:text-white">{{ levelInfo.name }}</h4>
                    <span class="text-sm font-bold"
                      :class="
                        levelInfo.level === 'EXPLORER' ? 'text-emerald-500 dark:text-emerald-400' :
                        levelInfo.level === 'CREATOR' ? 'text-blue-500 dark:text-blue-400' :
                        levelInfo.level === 'VISIONARY' ? 'text-amber-500 dark:text-amber-400' :
                        'text-purple-500 dark:text-purple-400'
                      ">
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
      </div>
    </div>

    <!-- 活動統計與記錄 -->
    <div class="mt-5 bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
      <!-- 活動統計 -->
      <div class="mb-6">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4">活動統計</h3>
        <div class="grid grid-cols-3 md:grid-cols-5 gap-6">
          <div class="text-center">
            <div class="text-2xl font-bold text-indigo-500 dark:text-indigo-400 mb-1">{{ user.stats?.tasksCompleted || 0 }}</div>
            <div class="text-xs text-gray-500 dark:text-gray-400">已完成任務</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-pink-500 dark:text-pink-400 mb-1">{{ user.stats?.consecutiveDays || 0 }}</div>
            <div class="text-xs text-gray-500 dark:text-gray-400">連續登入天數</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-purple-500 dark:text-purple-400 mb-1">{{ user.stats?.totalLogins || 0 }}</div>
            <div class="text-xs text-gray-500 dark:text-gray-400">總登入次數</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-green-500 dark:text-green-400 mb-1">{{ user.stats?.rewardsRedeemed || 0 }}</div>
            <div class="text-xs text-gray-500 dark:text-gray-400">已兌換禮品</div>
          </div>
          <div class="text-center">
            <div class="text-2xl font-bold text-amber-500 dark:text-amber-400 mb-1">{{ user.stats?.friendsInvited || 0 }}</div>
            <div class="text-xs text-gray-500 dark:text-gray-400">邀請好友</div>
          </div>
        </div>
      </div>

      <!-- 分隔線 -->
      <div class="border-t border-gray-200 dark:border-gray-600 mb-6"></div>

      <!-- 活動記錄 -->
      <div>
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">最近活動記錄</h3>
          <button @click="showAllRecords = true"
            class="text-xs text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-300 transition-colors hover:underline">
            查看完整記錄 →
          </button>
        </div>
        <div class="space-y-2">
          <!-- 無活動紀錄時顯示 -->
          <div v-if="recentRecords.length === 0" class="text-center py-8 text-gray-400 dark:text-gray-500">
            <svg class="w-12 h-12 mx-auto mb-2 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"/>
            </svg>
            <p class="text-sm">暫無活動紀錄</p>
          </div>
          <div v-for="record in recentRecords.slice(0, 5)" :key="record.id"
            class="flex items-center justify-between py-2 border-b border-gray-100 dark:border-gray-600/30 last:border-0 hover:bg-gray-50 dark:hover:bg-gray-700/30 transition-colors rounded px-2">
            <div class="flex items-center gap-3 flex-1 min-w-0">
              <!-- 圖標 - 根據類型顯示不同顏色 -->
              <div :class="[
                'w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0',
                record.type === 'task' ? 'bg-indigo-100 dark:bg-indigo-900/30' :
                record.type === 'login' ? 'bg-pink-100 dark:bg-pink-900/30' :
                record.type === 'streak' ? 'bg-purple-100 dark:bg-purple-900/30' :
                record.type === 'reward' ? 'bg-green-100 dark:bg-green-900/30' :
                record.type === 'invite' ? 'bg-amber-100 dark:bg-amber-900/30' :
                'bg-gray-100 dark:bg-gray-900/30'
              ]">
                <!-- 任務類 - 藍色 -->
                <svg v-if="record.type === 'task'" class="w-4 h-4 text-indigo-500 dark:text-indigo-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <!-- 登入類 - 粉紅色 -->
                <svg v-else-if="record.type === 'login'" class="w-4 h-4 text-pink-500 dark:text-pink-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <!-- 連續登入七天 - 紫色 -->
                <svg v-else-if="record.type === 'streak'" class="w-4 h-4 text-purple-500 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
                </svg>
                <!-- 兌換禮品 - 綠色 -->
                <svg v-else-if="record.type === 'reward'" class="w-4 h-4 text-green-500 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
                </svg>
                <!-- 邀請好友 - 黃色 -->
                <svg v-else-if="record.type === 'invite'" class="w-4 h-4 text-amber-500 dark:text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                </svg>
              </div>
              <span class="text-sm text-gray-700 dark:text-gray-300 truncate">{{ record.title }}</span>
            </div>
            <div class="flex items-center gap-4 flex-shrink-0">
              <span class="text-xs text-gray-400 dark:text-gray-500 whitespace-nowrap">{{ formatDate(record.date) }}</span>
              <span class="text-sm font-medium w-16 text-right" :class="record.type === 'reward' ? 'text-red-600 dark:text-red-400' : 'text-green-600 dark:text-green-400'">
                {{ record.type === 'reward' ? '-' + record.points : '+' + record.points }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 完整紀錄彈窗 -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showAllRecords" class="fixed inset-0 z-50 flex items-center justify-center p-4">
          <!-- 背景遮罩 -->
          <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showAllRecords = false"></div>

          <!-- 彈窗內容 -->
          <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-2xl max-h-[80vh] overflow-hidden">
            <!-- 標題列 -->
            <div class="flex items-center justify-between p-5 border-b border-gray-200 dark:border-gray-700">
              <h3 class="text-lg font-bold text-gray-900 dark:text-white">完整活動記錄</h3>
              <button @click="showAllRecords = false"
                class="w-8 h-8 flex items-center justify-center rounded-full hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors">
                <svg class="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>

            <!-- 紀錄列表 -->
            <div class="overflow-y-auto max-h-[60vh] p-5">
              <div class="space-y-2">
                <div v-for="record in recentRecords" :key="record.id"
                  class="flex items-center justify-between py-3 border-b border-gray-100 dark:border-gray-700/50 last:border-0 hover:bg-gray-50 dark:hover:bg-gray-700/30 transition-colors rounded px-3">
                  <div class="flex items-center gap-3 flex-1 min-w-0">
                    <!-- 圖標 - 根據類型顯示不同顏色 -->
                    <div :class="[
                      'w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0',
                      record.type === 'task' ? 'bg-indigo-100 dark:bg-indigo-900/30' :
                      record.type === 'login' ? 'bg-pink-100 dark:bg-pink-900/30' :
                      record.type === 'streak' ? 'bg-purple-100 dark:bg-purple-900/30' :
                      record.type === 'reward' ? 'bg-green-100 dark:bg-green-900/30' :
                      record.type === 'invite' ? 'bg-amber-100 dark:bg-amber-900/30' :
                      'bg-gray-100 dark:bg-gray-900/30'
                    ]">
                      <!-- 任務類 - 藍色 -->
                      <svg v-if="record.type === 'task'" class="w-4 h-4 text-indigo-500 dark:text-indigo-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"/>
                      </svg>
                      <!-- 登入類 - 粉紅色 -->
                      <svg v-else-if="record.type === 'login'" class="w-4 h-4 text-pink-500 dark:text-pink-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                      </svg>
                      <!-- 連續登入七天 - 紫色 -->
                      <svg v-else-if="record.type === 'streak'" class="w-4 h-4 text-purple-500 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/>
                      </svg>
                      <!-- 兌換禮品 - 綠色 -->
                      <svg v-else-if="record.type === 'reward'" class="w-4 h-4 text-green-500 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7"/>
                      </svg>
                      <!-- 邀請好友 - 黃色 -->
                      <svg v-else-if="record.type === 'invite'" class="w-4 h-4 text-amber-500 dark:text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                      </svg>
                    </div>
                    <span class="text-sm text-gray-700 dark:text-gray-300">{{ record.title }}</span>
                  </div>
                  <div class="flex items-center gap-4 flex-shrink-0">
                    <span class="text-xs text-gray-400 dark:text-gray-500 whitespace-nowrap">{{ formatDate(record.date) }}</span>
                    <span class="text-sm font-medium w-16 text-right" :class="record.type === 'reward' ? 'text-red-600 dark:text-red-400' : 'text-green-600 dark:text-green-400'">
                      {{ record.type === 'reward' ? '-' + record.points : '+' + record.points }}
                    </span>
                  </div>
                </div>
              </div>

              <!-- 空狀態 -->
              <div v-if="recentRecords.length === 0" class="text-center py-10 text-gray-500 dark:text-gray-400">
                <svg class="w-12 h-12 mx-auto mb-3 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                </svg>
                <p>目前沒有活動記錄</p>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { levelConfig } from '../mock.js'
import { useStore } from '../store/app.js'

const store = useStore()
const router = useRouter()

// 頭像上傳相關
const avatarInput = ref(null)

// 完整紀錄彈窗
const showAllRecords = ref(false)

// 後端活動紀錄
const backendRecords = ref([])

// 載入資料
onMounted(async () => {
  store.fetchUserStats()
  // 從後端取得活動紀錄
  backendRecords.value = await store.fetchActivityRecords()
})

// 用戶資料 - 使用 store 即時數據
const user = computed(() => ({
  ...store.currentUser,
  levelPoints: store.userPoints.upgradePoints,
  rewardPoints: store.userPoints.rewardPoints,
  stats: store.userStats
}))

// 當前等級配置 - 使用 userPoints 計算以避免閃現問題
const currentLevelConfig = computed(() => {
  // 優先使用 userPoints 計算等級，比 currentLevel 更即時
  const upgradePoints = store.userPoints.upgradePoints || 0
  const levelByPoints = levelConfig.find(level =>
    upgradePoints >= level.minPoints && upgradePoints <= level.maxPoints
  )
  return levelByPoints || levelConfig.find(level => level.level === user.value.level) || levelConfig[0]
})

// 下一等級配置
const nextLevelConfig = computed(() => {
  const currentIndex = levelConfig.findIndex(level => level.level === currentLevelConfig.value.level)
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

// 假資料（當後端沒資料時 Demo 用）
const mockRecords = [
  { id: 1, type: 'streak', title: '連續登入七天', points: 10, date: '2024-12-12T09:16:00' },
  { id: 2, type: 'login', title: '完成每日登入', points: 5, date: '2024-12-12T09:15:00' },
  { id: 3, type: 'reward', title: '兌換 UiU 環保便攜吸管組', points: 100, date: '2024-12-11T16:30:00' },
  { id: 4, type: 'invite', title: '邀請好友註冊成功', points: 20, date: '2024-12-11T14:00:00' },
  { id: 5, type: 'task', title: '達成 Creator 等級升級', points: 100, date: '2024-12-11T10:00:00' },
  { id: 6, type: 'login', title: '完成每日登入', points: 5, date: '2024-12-11T08:30:00' },
  { id: 7, type: 'task', title: '建立借貸需求檔案', points: 80, date: '2024-12-10T15:45:00' },
  { id: 8, type: 'task', title: '完成個人檔案設置', points: 25, date: '2024-12-05T14:00:00' }
]

// 活動紀錄 - 使用後端資料（新用戶沒有活動紀錄是正常的）
const recentRecords = computed(() => {
  return backendRecords.value.sort((a, b) => new Date(b.date) - new Date(a.date))
})

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString)
  const month = date.getMonth() + 1
  const day = date.getDate()
  const minutes = date.getMinutes().toString().padStart(2, '0')
  const period = date.getHours() < 12 ? '上午' : '下午'
  const displayHours = date.getHours() % 12 || 12
  const displayHoursStr = displayHours.toString().padStart(2, '0')

  return `${month}月${day}日 ${period}${displayHoursStr}:${minutes}`
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
    store.showToast('請選擇圖片檔案', 'error')
    return
  }

  // 檢查檔案大小 (5MB 限制)
  if (file.size > 5 * 1024 * 1024) {
    store.showToast('圖片大小不能超過 5MB', 'error')
    return
  }

  // 讀取檔案為 Data URL
  const reader = new FileReader()
  reader.onload = (e) => {
    // 這裡可以更新用戶頭像
    // 如果有後端 API，可以在這裡上傳到伺服器

    // 暫時更新本地顯示
    if (store.currentUser) {
      store.currentUser.avatar = e.target?.result
    }

    store.showToast('頭像更新成功！', 'success')
  }
  reader.readAsDataURL(file)
}

// 登出功能
const handleLogout = () => {
  if (confirm('確定要登出嗎？')) {
    store.logout()
    router.push('/')
    // 延遲顯示登入彈窗，讓路由先導航
    setTimeout(() => {
      // 觸發登入彈窗需要通過事件或全域狀態
      // 這裡直接跳轉到首頁後，用戶會看到「登入 / 註冊」按鈕
      store.showToast('已成功登出', 'info')
    }, 100)
  }
}
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .relative,
.modal-leave-to .relative {
  transform: scale(0.95) translateY(10px);
}
</style>