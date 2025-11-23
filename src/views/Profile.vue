<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header Section -->
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text">會員中心</h1>
      <button @click="toggleLogin"
        class="px-6 py-2 rounded-full border border-gray-300 dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors text-sm font-medium text-gray-600 dark:text-gray-300">
        {{ isLoggedIn ? '登出' : '登入' }}
      </button>
    </div>

    <div v-if="isLoggedIn">
      <!-- Tabs -->
      <div class="flex gap-2 mb-8 border-b border-gray-200 dark:border-gray-700">
        <button @click="activeTab = 'overview'"
          :class="[
            'px-6 py-3 font-semibold transition-all relative',
            activeTab === 'overview'
              ? 'text-primary-purple'
              : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300'
          ]">
          總覽
          <div v-if="activeTab === 'overview'"
            class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-purple-500 to-pink-500"></div>
        </button>
        <button @click="activeTab = 'levels'"
          :class="[
            'px-6 py-3 font-semibold transition-all relative',
            activeTab === 'levels'
              ? 'text-primary-purple'
              : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300'
          ]">
          等級系統
          <div v-if="activeTab === 'levels'"
            class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-purple-500 to-pink-500"></div>
        </button>
        <button @click="activeTab = 'history'"
          :class="[
            'px-6 py-3 font-semibold transition-all relative',
            activeTab === 'history'
              ? 'text-primary-purple'
              : 'text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-300'
          ]">
          歷史紀錄
          <div v-if="activeTab === 'history'"
            class="absolute bottom-0 left-0 right-0 h-0.5 bg-gradient-to-r from-purple-500 to-pink-500"></div>
        </button>
      </div>

      <!-- Tab Content -->
      <div v-show="activeTab === 'overview'" class="space-y-8">
        <!-- Current Level Card -->
        <div class="p-8 rounded-3xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 shadow-sm relative overflow-hidden">
          <!-- Background Glow -->
          <div class="absolute top-0 right-0 w-64 h-64 bg-gradient-to-br from-purple-500/10 to-pink-500/10 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>
          
          <div class="relative z-10 flex justify-between items-end">
            <div>
              <div class="text-sm text-gray-500 dark:text-gray-400 mb-1">目前等級</div>
              <div class="flex items-center gap-3 mb-2">
                <h2 class="text-3xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-purple-600 to-pink-600">
                  {{ user.level }}
                </h2>
                <span class="px-3 py-1 rounded-full bg-purple-100 dark:bg-purple-900/30 text-purple-600 dark:text-purple-400 text-sm font-bold">
                  Lv.{{ user.level === '探索者' ? 1 : user.level === '創造者' ? 2 : user.level === '先行者' ? 3 : 4 }}
                </span>
              </div>
              <p class="text-gray-600 dark:text-gray-400">
                {{ user.level === '探索者' ? '開啟永續旅程的起點' : '展現創意的進階階段' }}
              </p>
            </div>

            <div class="text-right">
              <div class="text-sm text-gray-500 dark:text-gray-400 mb-1">目前積分</div>
              <div class="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-emerald-400 to-teal-600">
                {{ user.points }}
              </div>
            </div>
          </div>

          <!-- Progress Bar -->
          <div class="mt-8">
            <div class="flex justify-between text-sm mb-2 text-gray-500 dark:text-gray-400">
              <span>進度</span>
              <span>{{ user.points }} / 800</span>
            </div>
            <div class="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3 overflow-hidden">
              <div class="bg-gradient-to-r from-emerald-400 to-teal-600 h-full rounded-full transition-all duration-1000 ease-out"
                :style="{ width: `${(user.points / 800) * 100}%` }"></div>
            </div>
            <div class="mt-2 text-sm text-gray-500 dark:text-gray-400 text-right">
              距離下一等級「創造者」還差 {{ 800 - user.points }} 分
            </div>
          </div>
        </div>

        <!-- User Info Grid -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <!-- Personal Info -->
          <div class="p-6 rounded-2xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 shadow-sm">
            <h3 class="text-xl font-bold mb-4 text-light-text dark:text-dark-text">個人資料</h3>
            <div class="space-y-4">
              <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
                <span class="text-gray-600 dark:text-gray-400">暱稱</span>
                <span class="font-medium text-light-text dark:text-dark-text">{{ user.name }}</span>
              </div>
              <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
                <span class="text-gray-600 dark:text-gray-400">Email</span>
                <span class="font-medium text-light-text dark:text-dark-text">{{ user.email }}</span>
              </div>
              <div class="flex justify-between py-2 border-b border-gray-100 dark:border-gray-700">
                <span class="text-gray-600 dark:text-gray-400">加入時間</span>
                <span class="font-medium text-light-text dark:text-dark-text">2025-05-29</span>
              </div>
            </div>
          </div>

          <!-- Stats -->
          <div class="p-6 rounded-2xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 shadow-sm">
            <h3 class="text-xl font-bold mb-4 text-light-text dark:text-dark-text">活動統計</h3>
            <div class="grid grid-cols-2 lg:grid-cols-4 gap-4">
              <div class="flex flex-col items-center justify-center p-4 rounded-2xl bg-violet-50 dark:bg-violet-900/10 border border-violet-100 dark:border-violet-800/20 group hover:scale-105 transition-transform duration-300">
                <div class="w-10 h-10 mb-2 rounded-full bg-violet-100 dark:bg-violet-800/30 flex items-center justify-center text-violet-500 dark:text-violet-400">
                  <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />
                  </svg>
                </div>
                <div class="text-2xl font-bold text-violet-600 dark:text-violet-300 mb-0.5">12</div>
                <div class="text-xs font-medium text-gray-500 dark:text-gray-400">完成任務</div>
              </div>

              <div class="flex flex-col items-center justify-center p-4 rounded-2xl bg-blue-50 dark:bg-blue-900/10 border border-blue-100 dark:border-blue-800/20 group hover:scale-105 transition-transform duration-300">
                <div class="w-10 h-10 mb-2 rounded-full bg-blue-100 dark:bg-blue-800/30 flex items-center justify-center text-blue-500 dark:text-blue-400">
                  <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>
                <div class="text-2xl font-bold text-blue-600 dark:text-blue-300 mb-0.5">3</div>
                <div class="text-xs font-medium text-gray-500 dark:text-gray-400">兌換禮品</div>
              </div>

              <div class="flex flex-col items-center justify-center p-4 rounded-2xl bg-emerald-50 dark:bg-emerald-900/10 border border-emerald-100 dark:border-emerald-800/20 group hover:scale-105 transition-transform duration-300">
                <div class="w-10 h-10 mb-2 rounded-full bg-emerald-100 dark:bg-emerald-800/30 flex items-center justify-center text-emerald-500 dark:text-emerald-400">
                  <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                </div>
                <div class="text-2xl font-bold text-emerald-600 dark:text-emerald-300 mb-0.5">5</div>
                <div class="text-xs font-medium text-gray-500 dark:text-gray-400">連續登入</div>
              </div>

              <div class="flex flex-col items-center justify-center p-4 rounded-2xl bg-orange-50 dark:bg-orange-900/10 border border-orange-100 dark:border-orange-800/20 group hover:scale-105 transition-transform duration-300">
                <div class="w-10 h-10 mb-2 rounded-full bg-orange-100 dark:bg-orange-800/30 flex items-center justify-center text-orange-500 dark:text-orange-400">
                  <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                  </svg>
                </div>
                <div class="text-2xl font-bold text-orange-600 dark:text-orange-300 mb-0.5">Top 10%</div>
                <div class="text-xs font-medium text-gray-500 dark:text-gray-400">排名</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Levels Tab -->
      <div v-show="activeTab === 'levels'" class="space-y-6">
        <div class="mb-6">
          <h2 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">等級權益系統</h2>
          <p class="text-gray-500 dark:text-gray-400">累積積分，解鎖專屬尊榮禮遇</p>
        </div>

        <!-- Level Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <!-- Explorer -->
          <div :class="[
            'relative overflow-hidden p-6 rounded-2xl transition-all duration-300 group',
            user.points >= 0
              ? 'bg-white dark:bg-gray-800 border-2 border-emerald-100 dark:border-emerald-900/30 shadow-lg shadow-emerald-100/50 dark:shadow-none'
              : 'bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 opacity-70 grayscale-[0.5]'
          ]">
            <div class="absolute top-0 right-0 w-32 h-32 bg-emerald-500/5 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>
            
            <div class="relative z-10 flex items-start gap-4">
              <div class="w-12 h-12 rounded-xl bg-emerald-50 dark:bg-emerald-900/20 flex items-center justify-center text-emerald-500 dark:text-emerald-400 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-7 h-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div class="flex-1">
                <div class="flex justify-between items-start mb-1">
                  <h3 class="text-lg font-bold text-gray-900 dark:text-white">探索者 Explorer</h3>
                  <span class="text-xs font-bold px-2 py-1 rounded-full bg-emerald-100 dark:bg-emerald-900/30 text-emerald-600 dark:text-emerald-400">Lv.1</span>
                </div>
                <p class="text-sm text-emerald-600/80 dark:text-emerald-400/80 font-medium mb-3">0 - 799 分</p>
                <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
                  開啟永續旅程的起點。可兌換基礎「永續探索」系列禮品，享受生日專屬祝福。
                </p>
              </div>
            </div>
          </div>

          <!-- Creator -->
          <div :class="[
            'relative overflow-hidden p-6 rounded-2xl transition-all duration-300 group',
            user.points >= 800
              ? 'bg-white dark:bg-gray-800 border-2 border-indigo-100 dark:border-indigo-900/30 shadow-lg shadow-indigo-100/50 dark:shadow-none'
              : 'bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 opacity-70 grayscale-[0.5]'
          ]">
            <div class="absolute top-0 right-0 w-32 h-32 bg-indigo-500/5 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>

            <div class="relative z-10 flex items-start gap-4">
              <div class="w-12 h-12 rounded-xl bg-indigo-50 dark:bg-indigo-900/20 flex items-center justify-center text-indigo-500 dark:text-indigo-400 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-7 h-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M19.428 15.428a2 2 0 00-1.022-.547l-2.384-.477a6 6 0 00-3.86.517l-.318.158a6 6 0 01-3.86.517L6.05 15.21a2 2 0 00-1.806.547M8 4h8l-1 1v5.172a2 2 0 00.586 1.414l5 5c1.26 1.26.367 3.414-1.415 3.414H4.828c-1.782 0-2.674-2.154-1.414-3.414l5-5A2 2 0 009 10.172V5L8 4z" />
                </svg>
              </div>
              <div class="flex-1">
                <div class="flex justify-between items-start mb-1">
                  <h3 class="text-lg font-bold text-gray-900 dark:text-white">創造者 Creator</h3>
                  <span class="text-xs font-bold px-2 py-1 rounded-full bg-indigo-100 dark:bg-indigo-900/30 text-indigo-600 dark:text-indigo-400">Lv.2</span>
                </div>
                <p class="text-sm text-indigo-600/80 dark:text-indigo-400/80 font-medium mb-3">800 - 1,999 分</p>
                <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
                  展現創意的進階階段。解鎖「質感創造」系列禮品，任務積分獲得 1.1 倍加成。
                </p>
              </div>
            </div>
          </div>

          <!-- Visionary -->
          <div :class="[
            'relative overflow-hidden p-6 rounded-2xl transition-all duration-300 group',
            user.points >= 2000
              ? 'bg-white dark:bg-gray-800 border-2 border-orange-100 dark:border-orange-900/30 shadow-lg shadow-orange-100/50 dark:shadow-none'
              : 'bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 opacity-70 grayscale-[0.5]'
          ]">
            <div class="absolute top-0 right-0 w-32 h-32 bg-orange-500/5 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>

            <div class="relative z-10 flex items-start gap-4">
              <div class="w-12 h-12 rounded-xl bg-orange-50 dark:bg-orange-900/20 flex items-center justify-center text-orange-500 dark:text-orange-400 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-7 h-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                </svg>
              </div>
              <div class="flex-1">
                <div class="flex justify-between items-start mb-1">
                  <h3 class="text-lg font-bold text-gray-900 dark:text-white">先行者 Visionary</h3>
                  <span class="text-xs font-bold px-2 py-1 rounded-full bg-orange-100 dark:bg-orange-900/30 text-orange-600 dark:text-orange-400">Lv.3</span>
                </div>
                <p class="text-sm text-orange-600/80 dark:text-orange-400/80 font-medium mb-3">2,000 - 4,999 分</p>
                <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
                  洞見未來的領袖。解鎖「美學先鋒」系列禮品，享有新品優先體驗權與專屬諮詢。
                </p>
              </div>
            </div>
          </div>

          <!-- Luminary -->
          <div :class="[
            'relative overflow-hidden p-6 rounded-2xl transition-all duration-300 group',
            user.points >= 5000
              ? 'bg-white dark:bg-gray-800 border-2 border-violet-100 dark:border-violet-900/30 shadow-lg shadow-violet-100/50 dark:shadow-none'
              : 'bg-gray-50 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 opacity-70 grayscale-[0.5]'
          ]">
            <div class="absolute top-0 right-0 w-32 h-32 bg-violet-500/5 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>

            <div class="relative z-10 flex items-start gap-4">
              <div class="w-12 h-12 rounded-xl bg-violet-50 dark:bg-violet-900/20 flex items-center justify-center text-violet-500 dark:text-violet-400 group-hover:scale-110 transition-transform duration-300">
                <svg class="w-7 h-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
                </svg>
              </div>
              <div class="flex-1">
                <div class="flex justify-between items-start mb-1">
                  <h3 class="text-lg font-bold text-gray-900 dark:text-white">閃耀者 Luminary</h3>
                  <span class="text-xs font-bold px-2 py-1 rounded-full bg-violet-100 dark:bg-violet-900/30 text-violet-600 dark:text-violet-400">Lv.4</span>
                </div>
                <p class="text-sm text-violet-600/80 dark:text-violet-400/80 font-medium mb-3">5,000+ 分</p>
                <p class="text-sm text-gray-600 dark:text-gray-400 leading-relaxed">
                  極致尊榮的象徵。解鎖「品味閃耀」頂級禮品，年度尊榮禮盒與 VIP 席位。
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- History Tab -->
      <div v-show="activeTab === 'history'" class="space-y-6">
        <div class="mb-6">
          <h2 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">歷史紀錄</h2>
          <p class="text-gray-600 dark:text-gray-400">查看您的任務完成與禮品兌換記錄</p>
        </div>

        <!-- History List -->
        <div class="space-y-4">
          <div v-for="record in history" :key="record.id"
            class="p-5 rounded-xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 shadow-sm hover:shadow-md transition-shadow">
            <div class="flex items-start justify-between">
              <div class="flex items-start gap-4">
                <div :class="[
                  'w-12 h-12 rounded-lg flex items-center justify-center text-2xl',
                  record.type === 'task' ? 'text-purple-500' : 'text-orange-500'
                ]">
                  <svg v-if="record.type === 'task'" class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  <svg v-else class="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>
                <div>
                  <h4 class="font-semibold text-light-text dark:text-dark-text mb-1">{{ record.title }}</h4>
                  <p class="text-sm text-gray-600 dark:text-gray-400">{{ record.description }}</p>
                  <p class="text-xs text-gray-500 dark:text-gray-500 mt-2">{{ record.date }}</p>
                </div>
              </div>
              <div :class="[
                'px-3 py-1 rounded-full text-sm font-semibold',
                record.points > 0
                  ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400'
                  : 'bg-orange-100 dark:bg-orange-900/30 text-orange-700 dark:text-orange-400'
              ]">
                {{ record.points > 0 ? '+' : '' }}{{ record.points }}
              </div>
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
      <p class="text-gray-600 dark:text-gray-400 mb-8">登入後查看您的等級與積分</p>
      <button @click="toggleLogin"
        class="inline-block px-8 py-3 rounded-full bg-gradient-to-br from-purple-500 to-pink-500 text-white font-semibold hover:opacity-90 transition-opacity">
        立即登入
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const isLoggedIn = ref(true)
const activeTab = ref('overview')

const user = ref({
  name: 'Matcha',
  email: 'matcha@example.com',
  points: 450,
  level: '探索者'
})

const history = ref([
  {
    id: 1,
    type: 'task',
    title: '完成每日任務：閱讀理財文章',
    description: '閱讀「小資族存錢秘訣」文章並分享心得',
    date: '2025-05-28 14:30',
    points: 50
  },
  {
    id: 2,
    type: 'gift',
    title: '兌換禮品：環保購物袋',
    description: 'ESG 永續系列 - 有機棉環保購物袋',
    date: '2025-05-27 10:15',
    points: -200
  },
  {
    id: 3,
    type: 'task',
    title: '完成挑戰：連續登入 7 天',
    description: '恭喜達成連續登入成就！',
    date: '2025-05-26 09:00',
    points: 100
  },
  {
    id: 4,
    type: 'task',
    title: '完成每日任務：記帳練習',
    description: '記錄一日消費並設定預算',
    date: '2025-05-25 16:45',
    points: 50
  },
  {
    id: 5,
    type: 'gift',
    title: '兌換禮品：理財筆記本',
    description: '日常互動系列 - 質感理財手帳',
    date: '2025-05-24 11:20',
    points: -150
  },
  {
    id: 6,
    type: 'task',
    title: '完成學習任務：投資基礎課程',
    description: '觀看「認識基金投資」影片課程',
    date: '2025-05-23 15:30',
    points: 80
  }
])

const toggleLogin = () => {
  isLoggedIn.value = !isLoggedIn.value
}
</script>
