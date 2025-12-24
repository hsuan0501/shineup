<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
    <!-- 頂部：統計卡片 + 按鈕 -->
    <div class="mb-4 flex items-center gap-3">
      <!-- 統計卡片 -->
      <div class="flex-1 grid grid-cols-4 gap-3">
        <div class="bg-white dark:bg-gray-700/70 rounded-xl px-4 py-3 border dark:border-gray-600/30 flex items-center gap-3">
          <div class="w-8 h-8 rounded-lg bg-sky-100 dark:bg-sky-900/30 flex items-center justify-center">
            <svg class="w-4 h-4 text-sky-500" fill="currentColor" viewBox="0 0 24 24">
              <path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/>
            </svg>
          </div>
          <div>
            <p class="text-xl font-bold text-gray-900 dark:text-white">{{ users.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">會員</p>
          </div>
        </div>

        <div class="bg-white dark:bg-gray-700/70 rounded-xl px-4 py-3 border dark:border-gray-600/30 flex items-center gap-3">
          <div class="w-8 h-8 rounded-lg bg-emerald-100 dark:bg-emerald-900/30 flex items-center justify-center">
            <svg class="w-4 h-4 text-emerald-500" fill="currentColor" viewBox="0 0 24 24">
              <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-9 14l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/>
            </svg>
          </div>
          <div>
            <p class="text-xl font-bold text-gray-900 dark:text-white">{{ taskList.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">任務</p>
          </div>
        </div>

        <div class="bg-white dark:bg-gray-700/70 rounded-xl px-4 py-3 border dark:border-gray-600/30 flex items-center gap-3">
          <div class="w-8 h-8 rounded-lg bg-purple-100 dark:bg-purple-900/30 flex items-center justify-center">
            <svg class="w-4 h-4 text-purple-500" fill="currentColor" viewBox="0 0 24 24">
              <path d="M20 6h-2.18c.11-.31.18-.65.18-1 0-1.66-1.34-3-3-3-1.05 0-1.96.54-2.5 1.35l-.5.67-.5-.68C10.96 2.54 10.05 2 9 2 7.34 2 6 3.34 6 5c0 .35.07.69.18 1H4c-1.11 0-1.99.89-1.99 2L2 19c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V8c0-1.11-.89-2-2-2zm-5-2c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zM9 4c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm11 15H4v-2h16v2zm0-5H4V8h5.08L7 10.83 8.62 12 11 8.76l1-1.36 1 1.36L15.38 12 17 10.83 14.92 8H20v6z"/>
            </svg>
          </div>
          <div>
            <p class="text-xl font-bold text-gray-900 dark:text-white">{{ giftList.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">禮品</p>
          </div>
        </div>

        <div class="bg-white dark:bg-gray-700/70 rounded-xl px-4 py-3 border dark:border-gray-600/30 flex items-center gap-3">
          <div class="w-8 h-8 rounded-lg bg-amber-100 dark:bg-amber-900/30 flex items-center justify-center">
            <svg class="w-4 h-4 text-amber-500" fill="currentColor" viewBox="0 0 24 24">
              <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/>
            </svg>
          </div>
          <div>
            <p class="text-xl font-bold text-gray-900 dark:text-white">{{ pendingOrderCount }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">待處理</p>
          </div>
        </div>
      </div>

      <!-- 右側按鈕 -->
      <div class="flex items-center gap-2">
        <button @click="refreshAllData"
          class="p-2 text-sky-600 dark:text-sky-400 hover:text-sky-700 dark:hover:text-sky-300 hover:scale-110 active:scale-95 transition-all duration-300">
          <svg class="w-4 h-4" :class="{ 'animate-spin': isRefreshing }" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
          </svg>
        </button>
        <button @click="handleLogout"
          class="px-3 py-2 rounded-lg bg-sky-50 dark:bg-sky-900/20 text-sky-600 dark:text-sky-400 hover:bg-sky-100 dark:hover:bg-sky-900/30 border border-sky-200 dark:border-sky-800 hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          登出
        </button>
      </div>
    </div>

    <!-- 標籤頁 + 內容區塊 -->
    <div class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <!-- 頁籤列 -->
      <div class="p-1.5 border-b border-gray-100 dark:border-gray-600/30">
        <div class="flex">
          <button v-for="tab in tabs" :key="tab.id" @click="activeTab = tab.id"
            :class="[
              'flex-1 py-2.5 rounded-xl text-sm font-semibold transition-all duration-200 flex items-center justify-center gap-2',
              activeTab === tab.id
                ? tab.activeClass
                : tab.inactiveClass
            ]">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" v-html="tab.icon"></svg>
            <span class="hidden sm:inline">{{ tab.name }}</span>
          </button>
        </div>
      </div>

      <!-- 會員管理 -->
      <div v-if="activeTab === 'users'">
        <!-- 篩選工具列 -->
        <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-600/30">
          <div class="flex items-end gap-4">
            <!-- 等級篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">等級</label>
              <select v-model="userFilter.level"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部等級</option>
                <option value="EXPLORER">Lv1 EXPLORER</option>
                <option value="CREATOR">Lv2 CREATOR</option>
                <option value="VISIONARY">Lv3 VISIONARY</option>
                <option value="LUMINARY">Lv4 LUMINARY</option>
              </select>
            </div>
            <!-- 狀態篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">狀態</label>
              <select v-model="userFilter.status"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部狀態</option>
                <option value="enabled">啟用</option>
                <option value="disabled">停用</option>
              </select>
            </div>
            <!-- 彈性空間 -->
            <div class="flex-1"></div>
            <!-- 關鍵字搜尋 -->
            <div class="w-48">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">關鍵字搜尋</label>
              <div class="relative">
                <input type="text" v-model="searchQuery" placeholder="搜尋會員..."
                  class="w-full h-[34px] pl-8 pr-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <svg class="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <!-- 重置按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">操作</label>
              <button @click="resetUserFilter"
                class="h-[34px] px-3 py-1.5 rounded-lg text-sky-600 dark:text-sky-400 text-sm font-medium hover:bg-sky-50 dark:hover:bg-sky-900/20 border border-sky-200 dark:border-sky-700/50 transition-colors flex items-center gap-1.5 whitespace-nowrap">
                <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                重置篩選
              </button>
            </div>
          </div>
        </div>
      <div class="overflow-x-auto">
        <table class="w-full table-fixed">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="w-[5%] pl-4 pr-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">編號</th>
              <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">等級</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">升級積分</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">兌換積分</th>
              <th class="w-[8%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="w-[15%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">註冊日期</th>
              <th class="w-[22%] pl-2 pr-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="(user, index) in paginatedUsers" :key="user.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50 h-12">
              <td class="pl-4 pr-2 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ filteredUsers.length - (userCurrentPage - 1) * 10 - index }}</td>
              <td class="px-2 py-3">
                <div class="flex items-center gap-3">
                  <img :src="user.avatar" class="w-8 h-8 rounded-full object-cover bg-gray-200">
                  <div>
                    <p class="text-sm font-medium text-gray-900 dark:text-white">{{ user.name }}</p>
                    <p class="text-xs text-gray-500 dark:text-gray-400">{{ user.email }}</p>
                  </div>
                </div>
              </td>
              <td class="px-2 py-3">
                <span :class="getLevelBadgeClass(user.level)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ user.level }}
                </span>
              </td>
              <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">{{ user.levelPoints }}</td>
              <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">{{ user.rewardPoints }}</td>
              <td class="px-2 py-3">
                <span :class="user.enabled !== false ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ user.enabled !== false ? '啟用' : '停用' }}
                </span>
              </td>
              <td class="px-2 py-3 text-sm text-gray-500 dark:text-gray-400">{{ user.createdAt }}</td>
              <td class="pl-2 pr-4 py-3">
                <div class="flex items-center gap-2">
                  <button @click="openUserModal(user)" class="px-3 py-1 text-xs font-medium rounded bg-sky-100 dark:bg-sky-900/40 text-sky-700 dark:text-sky-300 hover:bg-sky-200 dark:hover:bg-sky-900/60 transition-colors">編輯</button>
                  <button @click="toggleUserStatus(user)" :class="user.enabled !== false ? 'bg-amber-100 dark:bg-amber-900/40 text-amber-700 dark:text-amber-300 hover:bg-amber-200 dark:hover:bg-amber-900/60' : 'bg-green-100 dark:bg-green-900/40 text-green-700 dark:text-green-300 hover:bg-green-200 dark:hover:bg-green-900/60'" class="px-3 py-1 text-xs font-medium rounded transition-colors">
                    {{ user.enabled !== false ? '停用' : '啟用' }}
                  </button>
                  <button @click="deleteUser(user)" class="px-3 py-1 text-xs font-medium rounded bg-red-100 dark:bg-red-900/40 text-red-700 dark:text-red-300 hover:bg-red-200 dark:hover:bg-red-900/60 transition-colors">刪除</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 會員分頁控制 -->
      <div class="px-4 py-3 border-t border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <div class="text-sm text-gray-500 dark:text-gray-400">
          共 {{ filteredUsers.length }} 筆，第 {{ userCurrentPage }} / {{ userTotalPages }} 頁
        </div>
        <div class="flex items-center gap-2">
          <button @click="userCurrentPage = 1" :disabled="userCurrentPage === 1"
            class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
            首頁
          </button>
          <button @click="userCurrentPage--" :disabled="userCurrentPage === 1"
            class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
            上一頁
          </button>
          <span class="px-3 py-1 text-sm font-medium text-gray-700 dark:text-gray-300">{{ userCurrentPage }}</span>
          <button @click="userCurrentPage++" :disabled="userCurrentPage === userTotalPages"
            class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
            下一頁
          </button>
          <button @click="userCurrentPage = userTotalPages" :disabled="userCurrentPage === userTotalPages"
            class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
            末頁
          </button>
        </div>
      </div>
      </div>

      <!-- 任務管理 -->
      <div v-if="activeTab === 'tasks'">
        <!-- 篩選工具列 -->
        <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-600/30">
          <div class="flex items-end gap-4">
            <!-- 分類篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">分類</label>
              <select v-model="taskFilter.category"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部分類</option>
                <option value="daily">日常互動</option>
                <option value="financial">理財學習</option>
                <option value="investment">投資實踐</option>
                <option value="esg">永續行動</option>
                <option value="social">社群成就</option>
              </select>
            </div>
            <!-- 狀態篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">狀態</label>
              <select v-model="taskFilter.status"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部狀態</option>
                <option value="active">啟用</option>
                <option value="inactive">停用</option>
              </select>
            </div>
            <!-- 彈性空間 -->
            <div class="flex-1"></div>
            <!-- 新增按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">新增</label>
              <button @click="openTaskModal()" class="h-[34px] px-3 py-1.5 rounded-lg bg-emerald-50 dark:bg-emerald-900/30 text-emerald-600 dark:text-emerald-400 text-sm font-medium hover:bg-emerald-100 dark:hover:bg-emerald-900/50 border border-emerald-200 dark:border-emerald-700/50 transition-colors">
                + 新增
              </button>
            </div>
            <!-- 關鍵字搜尋 -->
            <div class="w-48">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">關鍵字搜尋</label>
              <div class="relative">
                <input type="text" v-model="taskSearchQuery" placeholder="搜尋任務..."
                  class="w-full h-[34px] pl-8 pr-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <svg class="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <!-- 重置按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">操作</label>
              <button @click="resetTaskFilter"
                class="h-[34px] px-3 py-1.5 rounded-lg text-sky-600 dark:text-sky-400 text-sm font-medium hover:bg-sky-50 dark:hover:bg-sky-900/20 border border-sky-200 dark:border-sky-700/50 transition-colors flex items-center gap-1.5 whitespace-nowrap">
                <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                重置篩選
              </button>
            </div>
          </div>
        </div>
        <div class="overflow-x-auto ">
          <table class="w-full table-fixed">
            <thead class="bg-gray-50 dark:bg-gray-800/50">
              <tr>
                <th class="w-[5%] pl-4 pr-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">編號</th>
                <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">任務名稱</th>
                <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">分類</th>
                <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
                <th class="w-[23%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
                <th class="w-[22%] pl-2 pr-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
              <tr v-for="(task, index) in paginatedTasks" :key="task.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50 h-12">
                <td class="pl-4 pr-2 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ (taskCurrentPage - 1) * 10 + index + 1 }}</td>
                <td class="px-2 py-3">
                  <p class="text-sm font-medium text-gray-900 dark:text-white truncate">{{ task.title }}</p>
                </td>
                <td class="px-2 py-3">
                  <span :class="getCategoryBadgeClass(task.category)" class="px-2 py-1 text-xs font-medium rounded-full">
                    {{ getCategoryName(task.category) }}
                  </span>
                </td>
                <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">+{{ task.levelPoints }} / +{{ task.rewardPoints }}</td>
                <td class="px-2 py-3">
                  <span :class="task.active !== false ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-gray-100 dark:bg-gray-800 text-gray-500 dark:text-gray-400'" class="px-2 py-1 text-xs font-medium rounded-full">
                    {{ task.active !== false ? '啟用' : '停用' }}
                  </span>
                </td>
                <td class="pl-2 pr-4 py-3">
                  <div class="flex items-center gap-2">
                    <button @click="openTaskModal(task)" class="px-3 py-1 text-xs font-medium rounded bg-sky-100 dark:bg-sky-900/40 text-sky-700 dark:text-sky-300 hover:bg-sky-200 dark:hover:bg-sky-900/60 transition-colors">編輯</button>
                    <button @click="toggleTaskStatus(task)" :class="task.active !== false ? 'bg-amber-100 dark:bg-amber-900/40 text-amber-700 dark:text-amber-300 hover:bg-amber-200 dark:hover:bg-amber-900/60' : 'bg-green-100 dark:bg-green-900/40 text-green-700 dark:text-green-300 hover:bg-green-200 dark:hover:bg-green-900/60'" class="px-3 py-1 text-xs font-medium rounded transition-colors">
                      {{ task.active !== false ? '停用' : '啟用' }}
                    </button>
                    <button @click="confirmDelete('task', task)" class="px-3 py-1 text-xs font-medium rounded bg-red-100 dark:bg-red-900/40 text-red-700 dark:text-red-300 hover:bg-red-200 dark:hover:bg-red-900/60 transition-colors">刪除</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- 任務分頁控制 -->
        <div v-if="taskTotalPages > 1" class="px-4 py-3 border-t border-gray-200 dark:border-gray-600 flex items-center justify-between">
          <div class="text-sm text-gray-500 dark:text-gray-400">
            共 {{ filteredTasks.length }} 筆，第 {{ taskCurrentPage }} / {{ taskTotalPages }} 頁
          </div>
          <div class="flex items-center gap-2">
            <button @click="taskCurrentPage = 1" :disabled="taskCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              首頁
            </button>
            <button @click="taskCurrentPage--" :disabled="taskCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              上一頁
            </button>
            <span class="px-3 py-1 text-sm font-medium text-gray-700 dark:text-gray-300">{{ taskCurrentPage }}</span>
            <button @click="taskCurrentPage++" :disabled="taskCurrentPage === taskTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              下一頁
            </button>
            <button @click="taskCurrentPage = taskTotalPages" :disabled="taskCurrentPage === taskTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              末頁
            </button>
          </div>
        </div>
      </div>

      <!-- 禮品管理 -->
      <div v-if="activeTab === 'gifts'">
        <!-- 篩選工具列 -->
        <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-600/30">
          <div class="flex items-end gap-4">
            <!-- 分類篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">分類</label>
              <select v-model="giftFilter.series"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部分類</option>
                <option value="sustainable">永續探索</option>
                <option value="quality">質感創造</option>
                <option value="aesthetic">美學先鋒</option>
                <option value="premium">品味閃耀</option>
              </select>
            </div>
            <!-- 庫存篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">庫存狀態</label>
              <select v-model="giftFilter.stock"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部庫存</option>
                <option value="inStock">有庫存</option>
                <option value="lowStock">庫存不足</option>
                <option value="outOfStock">已售完</option>
              </select>
            </div>
            <!-- 彈性空間 -->
            <div class="flex-1"></div>
            <!-- 新增按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">新增</label>
              <button @click="openGiftModal()" class="h-[34px] px-3 py-1.5 rounded-lg bg-purple-50 dark:bg-purple-900/30 text-purple-600 dark:text-purple-400 text-sm font-medium hover:bg-purple-100 dark:hover:bg-purple-900/50 border border-purple-200 dark:border-purple-700/50 transition-colors">
                + 新增
              </button>
            </div>
            <!-- 關鍵字搜尋 -->
            <div class="w-48">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">關鍵字搜尋</label>
              <div class="relative">
                <input type="text" v-model="giftSearchQuery" placeholder="搜尋禮品..."
                  class="w-full h-[34px] pl-8 pr-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <svg class="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <!-- 重置按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">操作</label>
              <button @click="resetGiftFilter"
                class="h-[34px] px-3 py-1.5 rounded-lg text-sky-600 dark:text-sky-400 text-sm font-medium hover:bg-sky-50 dark:hover:bg-sky-900/20 border border-sky-200 dark:border-sky-700/50 transition-colors flex items-center gap-1.5 whitespace-nowrap">
                <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                重置篩選
              </button>
            </div>
          </div>
        </div>
        <div class="overflow-x-auto ">
        <table class="w-full table-fixed">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="w-[5%] pl-4 pr-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">編號</th>
              <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">分類</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">庫存</th>
              <th class="w-[23%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="w-[22%] pl-2 pr-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="(gift, index) in paginatedGifts" :key="gift.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50 h-12">
              <td class="pl-4 pr-2 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ (giftCurrentPage - 1) * 10 + index + 1 }}</td>
              <td class="px-2 py-3">
                <div class="flex items-center gap-3">
                  <img :src="gift.image" class="w-10 h-10 rounded-lg object-cover bg-gray-200 flex-shrink-0">
                  <p class="text-sm font-medium text-gray-900 dark:text-white truncate">{{ gift.title }}</p>
                </div>
              </td>
              <td class="px-2 py-3">
                <span :class="getSeriesBadgeClass(gift.series)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ getSeriesName(gift.series) }}
                </span>
              </td>
              <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">{{ gift.points }}</td>
              <td class="px-2 py-3 text-sm" :class="gift.stock < 10 ? 'text-red-600 dark:text-red-400 font-bold' : 'text-gray-900 dark:text-white'">
                {{ gift.stock }}
              </td>
              <td class="px-2 py-3">
                <span :class="gift.stock > 0 ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'"
                  class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ gift.stock > 0 ? '有庫存' : '已售完' }}
                </span>
              </td>
              <td class="pl-2 pr-4 py-3">
                <div class="flex items-center gap-2">
                  <button @click="openGiftModal(gift)" class="px-3 py-1 text-xs font-medium rounded bg-sky-100 dark:bg-sky-900/40 text-sky-700 dark:text-sky-300 hover:bg-sky-200 dark:hover:bg-sky-900/60 transition-colors">編輯</button>
                  <button @click="openStockModal(gift)" class="px-3 py-1 text-xs font-medium rounded bg-amber-100 dark:bg-amber-900/40 text-amber-700 dark:text-amber-300 hover:bg-amber-200 dark:hover:bg-amber-900/60 transition-colors">庫存</button>
                  <button @click="confirmDelete('gift', gift)" class="px-3 py-1 text-xs font-medium rounded bg-red-100 dark:bg-red-900/40 text-red-700 dark:text-red-300 hover:bg-red-200 dark:hover:bg-red-900/60 transition-colors">刪除</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        </div>

        <!-- 禮品分頁控制 -->
        <div v-if="giftTotalPages > 1" class="px-4 py-3 border-t border-gray-200 dark:border-gray-600 flex items-center justify-between">
          <div class="text-sm text-gray-500 dark:text-gray-400">
            共 {{ filteredGifts.length }} 筆，第 {{ giftCurrentPage }} / {{ giftTotalPages }} 頁
          </div>
          <div class="flex items-center gap-2">
            <button @click="giftCurrentPage = 1" :disabled="giftCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              首頁
            </button>
            <button @click="giftCurrentPage--" :disabled="giftCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              上一頁
            </button>
            <span class="px-3 py-1 text-sm font-medium text-gray-700 dark:text-gray-300">{{ giftCurrentPage }}</span>
            <button @click="giftCurrentPage++" :disabled="giftCurrentPage === giftTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              下一頁
            </button>
            <button @click="giftCurrentPage = giftTotalPages" :disabled="giftCurrentPage === giftTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              末頁
            </button>
          </div>
        </div>
      </div>

      <!-- 兌換記錄 -->
      <div v-if="activeTab === 'redemptions'">
        <!-- 篩選工具列 -->
        <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-600/30">
          <div class="flex items-end gap-4">
            <!-- 日期起始 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">開始日期</label>
              <input type="date" v-model="orderFilter.startDate"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
            </div>
            <!-- 日期結束 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">結束日期</label>
              <input type="date" v-model="orderFilter.endDate"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
            </div>
            <!-- 狀態篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">狀態</label>
              <select v-model="orderFilter.status"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部狀態</option>
                <option value="PENDING">訂單確認中</option>
                <option value="SHIPPED">已出貨</option>
                <option value="DELIVERED">已送達</option>
                <option value="COMPLETED">取貨完成</option>
                <option value="CANCELLED">已取消</option>
              </select>
            </div>
            <!-- 彈性空間 -->
            <div class="flex-1"></div>
            <!-- 關鍵字搜尋 -->
            <div class="w-48">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">關鍵字搜尋</label>
              <div class="relative">
                <input type="text" v-model="orderSearchQuery" placeholder="搜尋會員、禮品..."
                  class="w-full h-[34px] pl-8 pr-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <svg class="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <!-- 重置按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">操作</label>
              <button @click="resetOrderFilter"
                class="h-[34px] px-3 py-1.5 rounded-lg text-sky-600 dark:text-sky-400 text-sm font-medium hover:bg-sky-50 dark:hover:bg-sky-900/20 border border-sky-200 dark:border-sky-700/50 transition-colors flex items-center gap-1.5 whitespace-nowrap">
                <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                重置篩選
              </button>
            </div>
          </div>
        </div>
        <div class="overflow-x-auto ">
          <table class="w-full table-fixed">
            <thead class="bg-gray-50 dark:bg-gray-800/50">
              <tr>
                <th class="w-[3%] pl-4 pr-1 py-3"></th>
                <th class="w-[5%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">編號</th>
                <th class="w-[12%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
                <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
                <th class="w-[8%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
                <th class="w-[10%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">進度</th>
                <th class="w-[20%] px-2 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">日期</th>
                <th class="w-[22%] pl-2 pr-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
              <template v-for="(order, index) in paginatedRedemptions" :key="order.id">
                <!-- 主要列 -->
                <tr class="hover:bg-gray-50 dark:hover:bg-gray-700/50 h-12 cursor-pointer" @click="toggleOrderExpand(order.id)">
                  <td class="pl-4 pr-2 py-3 text-center">
                    <svg class="w-4 h-4 text-gray-400 transition-transform duration-200 mx-auto"
                      :class="{ 'rotate-90': expandedOrders.includes(order.id) }"
                      fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                    </svg>
                  </td>
                  <td class="px-2 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ filteredRedemptions.length - (currentPage - 1) * 10 - index }}</td>
                  <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">{{ order.user?.name || '-' }}</td>
                  <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">
                    <div v-if="order.items && order.items.length > 0" class="truncate">
                      <span v-for="(item, idx) in order.items" :key="item.id">
                        {{ item.gift?.title }}<span v-if="item.quantity > 1"> x{{ item.quantity }}</span><span v-if="idx < order.items.length - 1">、</span>
                      </span>
                    </div>
                    <span v-else class="truncate">{{ order.gift?.title || '-' }}</span>
                  </td>
                  <td class="px-2 py-3 text-sm text-gray-900 dark:text-white">{{ order.totalPoints }}</td>
                  <td class="px-2 py-3">
                    <span :class="getStatusBadgeClass(order.status)" class="px-2 py-1 text-xs font-medium rounded-full">
                      {{ getStatusText(order.status) }}
                    </span>
                  </td>
                  <td class="px-2 py-3 text-sm text-gray-500 dark:text-gray-400 whitespace-nowrap">{{ formatDate(order.createdAt) }}</td>
                  <td class="pl-2 pr-4 py-3" @click.stop>
                    <div class="relative">
                      <button @click="toggleStatusDropdown(order.id)"
                        :class="getStatusSelectClass(order.status)"
                        class="w-28 text-xs font-medium rounded px-2 py-1.5 flex items-center justify-between gap-1">
                        <span>{{ getStatusText(order.status) }}</span>
                        <svg class="w-3 h-3 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                        </svg>
                      </button>
                      <div v-if="openStatusDropdown === order.id"
                        class="absolute z-50 mt-1 w-28 rounded-lg shadow-lg overflow-hidden border border-gray-200 dark:border-gray-600 bg-white dark:bg-gray-800">
                        <button v-for="status in orderStatuses" :key="status.value"
                          @click="selectOrderStatus(order, status.value)"
                          :class="getStatusSelectClass(status.value)"
                          class="w-full px-2 py-1.5 text-xs font-medium text-left hover:opacity-80 transition-opacity">
                          {{ status.label }}
                        </button>
                      </div>
                    </div>
                  </td>
                </tr>
                <!-- 展開詳情列 -->
                <tr v-if="expandedOrders.includes(order.id)" class="bg-gray-50 dark:bg-gray-800/30">
                  <td colspan="8" class="px-6 py-4">
                    <div class="grid grid-cols-2 gap-6">
                      <!-- 左側：收件資訊 -->
                      <div class="space-y-3">
                        <h4 class="text-sm font-semibold text-gray-700 dark:text-gray-300 flex items-center gap-2">
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                          </svg>
                          收件資訊
                        </h4>
                        <div class="bg-white dark:bg-gray-700/50 rounded-lg p-3 text-sm space-y-2">
                          <div class="flex">
                            <span class="w-20 text-gray-500 dark:text-gray-400">收件人：</span>
                            <span class="text-gray-900 dark:text-white">{{ order.recipientName || '-' }}</span>
                          </div>
                          <div class="flex">
                            <span class="w-20 text-gray-500 dark:text-gray-400">電話：</span>
                            <span class="text-gray-900 dark:text-white">{{ order.recipientPhone || '-' }}</span>
                          </div>
                          <div class="flex">
                            <span class="w-20 text-gray-500 dark:text-gray-400">寄送方式：</span>
                            <span class="text-gray-900 dark:text-white">{{ order.deliveryMethod === 'store' ? '超商取貨' : '宅配到府' }}</span>
                          </div>
                          <div v-if="order.deliveryMethod === 'home'" class="flex">
                            <span class="w-20 text-gray-500 dark:text-gray-400">地址：</span>
                            <span class="text-gray-900 dark:text-white">{{ order.deliveryAddress || '-' }}</span>
                          </div>
                          <template v-else>
                            <div class="flex">
                              <span class="w-20 text-gray-500 dark:text-gray-400">超商：</span>
                              <span class="text-gray-900 dark:text-white">{{ getStoreBrandName(order.storeBrand) }}</span>
                            </div>
                            <div class="flex">
                              <span class="w-20 text-gray-500 dark:text-gray-400">門市：</span>
                              <span class="text-gray-900 dark:text-white">{{ order.storeName || '-' }}</span>
                            </div>
                          </template>
                        </div>
                      </div>
                      <!-- 右側：訂單明細與時間軸 -->
                      <div class="space-y-3">
                        <h4 class="text-sm font-semibold text-gray-700 dark:text-gray-300 flex items-center gap-2">
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                          </svg>
                          訂單明細
                        </h4>
                        <div class="bg-white dark:bg-gray-700/50 rounded-lg p-3 text-sm space-y-2">
                          <template v-if="order.items && order.items.length > 0">
                            <div v-for="item in order.items" :key="item.id" class="flex justify-between">
                              <span class="text-gray-900 dark:text-white">{{ item.gift?.title }} <span class="text-gray-500">x{{ item.quantity }}</span></span>
                              <span class="text-purple-600 dark:text-purple-400 font-medium">{{ item.subtotalPoints }} 積分</span>
                            </div>
                          </template>
                          <div v-else class="flex justify-between">
                            <span class="text-gray-900 dark:text-white">{{ order.gift?.title }} <span class="text-gray-500">x{{ order.quantity }}</span></span>
                            <span class="text-purple-600 dark:text-purple-400 font-medium">{{ order.totalPoints }} 積分</span>
                          </div>
                          <div class="border-t border-gray-200 dark:border-gray-600 pt-2 mt-2 flex justify-between font-semibold">
                            <span class="text-gray-700 dark:text-gray-300">總計</span>
                            <span class="text-purple-600 dark:text-purple-400">{{ order.totalPoints }} 積分</span>
                          </div>
                        </div>
                        <!-- 時間軸 -->
                        <div class="bg-white dark:bg-gray-700/50 rounded-lg p-3 text-sm space-y-1.5">
                          <div class="flex items-center gap-2">
                            <span class="w-2 h-2 rounded-full bg-green-500"></span>
                            <span class="text-gray-500 dark:text-gray-400 w-16">下單</span>
                            <span class="text-gray-900 dark:text-white">{{ formatDate(order.createdAt) }}</span>
                          </div>
                          <div v-if="order.shippedAt" class="flex items-center gap-2">
                            <span class="w-2 h-2 rounded-full bg-blue-500"></span>
                            <span class="text-gray-500 dark:text-gray-400 w-16">出貨</span>
                            <span class="text-gray-900 dark:text-white">{{ formatDate(order.shippedAt) }}</span>
                          </div>
                          <div v-if="order.completedAt" class="flex items-center gap-2">
                            <span class="w-2 h-2 rounded-full bg-emerald-500"></span>
                            <span class="text-gray-500 dark:text-gray-400 w-16">完成</span>
                            <span class="text-gray-900 dark:text-white">{{ formatDate(order.completedAt) }}</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>

        <!-- 分頁控制 -->
        <div class="px-4 py-3 border-t border-gray-200 dark:border-gray-600 flex items-center justify-between">
          <div class="text-sm text-gray-500 dark:text-gray-400">
            共 {{ filteredRedemptions.length }} 筆，第 {{ currentPage }} / {{ totalPages }} 頁
          </div>
          <div class="flex items-center gap-2">
            <button @click="currentPage = 1" :disabled="currentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              首頁
            </button>
            <button @click="currentPage--" :disabled="currentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              上一頁
            </button>
            <span class="px-3 py-1 text-sm font-medium text-gray-700 dark:text-gray-300">{{ currentPage }}</span>
            <button @click="currentPage++" :disabled="currentPage === totalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              下一頁
            </button>
            <button @click="currentPage = totalPages" :disabled="currentPage === totalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              末頁
            </button>
          </div>
        </div>
      </div>

      <!-- FAQ 設定 -->
      <div v-if="activeTab === 'chatbot'">
        <!-- 篩選工具列 -->
        <div class="px-4 py-3 border-b border-gray-100 dark:border-gray-600/30">
          <div class="flex items-end gap-4">
            <!-- 類型篩選 -->
            <div class="w-40">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">類型</label>
              <select v-model="faqFilter.type"
                class="w-full h-[34px] px-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <option value="">全部類型</option>
                <option v-for="type in faqTypes" :key="type.id" :value="type.id">{{ type.name }}</option>
              </select>
            </div>
            <!-- 彈性空間 -->
            <div class="flex-1"></div>
            <!-- 新增按鈕 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">新增</label>
              <button @click="openReplyModal()" class="h-[34px] px-3 py-1.5 rounded-lg bg-pink-50 dark:bg-pink-900/30 text-pink-600 dark:text-pink-400 text-sm font-medium hover:bg-pink-100 dark:hover:bg-pink-900/50 border border-pink-200 dark:border-pink-700/50 transition-colors">
                + 新增
              </button>
            </div>
            <!-- 關鍵字搜尋 -->
            <div class="w-48">
              <label class="block text-xs font-medium text-gray-500 dark:text-gray-400 mb-1">關鍵字搜尋</label>
              <div class="relative">
                <input type="text" v-model="faqSearchQuery" placeholder="搜尋關鍵字或內容..."
                  class="w-full h-[34px] pl-8 pr-3 py-1.5 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                <svg class="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </div>
            </div>
            <!-- 重設篩選 -->
            <div>
              <label class="block text-xs font-medium text-transparent mb-1">操作</label>
              <button @click="resetFaqFilter"
                class="h-[34px] px-3 py-1.5 rounded-lg text-sky-600 dark:text-sky-400 text-sm font-medium hover:bg-sky-50 dark:hover:bg-sky-900/20 border border-sky-200 dark:border-sky-700/50 transition-colors flex items-center gap-1.5 whitespace-nowrap">
                <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                重設篩選
              </button>
            </div>
          </div>
        </div>
        <!-- FAQ 列表 -->
        <div class="divide-y divide-gray-200 dark:divide-gray-600">
          <div v-for="reply in paginatedFaqs" :key="reply.id" class="p-4 hover:bg-gray-50 dark:hover:bg-gray-700/50 transition-colors">
            <div class="flex items-center gap-4">
              <div class="flex-1 min-w-0">
                <div class="flex items-center gap-2 mb-2">
                  <span class="px-2 py-1 text-xs font-medium rounded-full bg-pink-100 dark:bg-pink-900/30 text-pink-700 dark:text-pink-400">
                    {{ getReplyTypeName(reply.id) }}
                  </span>
                </div>
                <div class="mb-2">
                  <span class="text-xs text-gray-500 dark:text-gray-400">關鍵字：</span>
                  <span class="text-sm text-gray-700 dark:text-gray-300 font-mono">{{ reply.keyword }}</span>
                </div>
                <div class="bg-gray-50 dark:bg-gray-800 rounded-lg p-3">
                  <p class="text-sm text-gray-600 dark:text-gray-400 whitespace-pre-wrap line-clamp-3">{{ reply.reply }}</p>
                </div>
              </div>
              <div class="flex items-center gap-2 flex-shrink-0">
                <button @click="openReplyModal(reply)" class="px-3 py-1 text-xs font-medium rounded bg-sky-100 dark:bg-sky-900/40 text-sky-700 dark:text-sky-300 hover:bg-sky-200 dark:hover:bg-sky-900/60 transition-colors">編輯</button>
                <button @click="confirmDeleteReply(reply)" class="px-3 py-1 text-xs font-medium rounded bg-red-100 dark:bg-red-900/40 text-red-700 dark:text-red-300 hover:bg-red-200 dark:hover:bg-red-900/60 transition-colors">刪除</button>
              </div>
            </div>
          </div>
        </div>
        <!-- FAQ 分頁控制 -->
        <div class="px-4 py-3 border-t border-gray-200 dark:border-gray-600 flex items-center justify-between">
          <div class="text-sm text-gray-500 dark:text-gray-400">
            共 {{ filteredFaqs.length }} 筆，第 {{ faqCurrentPage }} / {{ faqTotalPages }} 頁
          </div>
          <div class="flex items-center gap-2">
            <button @click="faqCurrentPage = 1" :disabled="faqCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              首頁
            </button>
            <button @click="faqCurrentPage--" :disabled="faqCurrentPage === 1"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              上一頁
            </button>
            <span class="px-3 py-1 text-sm font-medium text-gray-700 dark:text-gray-300">{{ faqCurrentPage }}</span>
            <button @click="faqCurrentPage++" :disabled="faqCurrentPage === faqTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              下一頁
            </button>
            <button @click="faqCurrentPage = faqTotalPages" :disabled="faqCurrentPage === faqTotalPages"
              class="px-2 py-1 text-sm rounded border border-gray-200 dark:border-gray-600 disabled:opacity-40 hover:bg-gray-50 dark:hover:bg-gray-600 transition-colors">
              末頁
            </button>
          </div>
        </div>

        <div v-if="chatbotReplies.length === 0" class="p-8 text-center">
          <svg class="w-12 h-12 mx-auto text-gray-300 dark:text-gray-600 mb-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
          </svg>
          <p class="text-gray-500 dark:text-gray-400">尚未設定任何 FAQ</p>
        </div>
      </div>
    </div>

    <!-- FAQ 編輯 Modal -->
    <div v-if="showReplyModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showReplyModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">{{ editingReply._isEdit ? '編輯 FAQ' : '新增 FAQ' }}</h3>
        </div>
        <div class="p-6 space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">類型名稱</label>
            <input v-model="editingReply.id" type="text" placeholder="例如：shipping, points, level"
              class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50"
              :disabled="editingReply._isEdit">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">關鍵字（用 | 分隔多個關鍵字）</label>
            <input v-model="editingReply.keyword" type="text" placeholder="例如：出貨|寄送|物流"
              class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">回覆內容</label>
            <textarea v-model="editingReply.reply" rows="8" placeholder="輸入回覆內容..."
              class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50"></textarea>
          </div>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
          <button @click="showReplyModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="saveReply" class="px-4 py-2 rounded-lg bg-pink-500 dark:bg-pink-600 text-white hover:bg-pink-600 dark:hover:bg-pink-500 transition-colors">儲存</button>
        </div>
      </div>
    </div>

    <!-- 任務編輯 Modal -->
    <div v-if="showTaskModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showTaskModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">{{ editingTask.id ? '編輯任務' : '新增任務' }}</h3>
        </div>
        <div class="p-6 space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">任務名稱</label>
            <input v-model="editingTask.title" type="text" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">描述</label>
            <textarea v-model="editingTask.description" rows="2" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50"></textarea>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">分類</label>
              <select v-model="editingTask.category" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="daily">日常互動</option>
                <option value="financial">理財學習</option>
                <option value="investment">投資實踐</option>
                <option value="esg">永續行動</option>
                <option value="social">社群成就</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">等級限制</label>
              <select v-model="editingTask.levelRequired" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="全等級">全等級</option>
                <option value="Lv2+">Lv2+</option>
                <option value="Lv3+">Lv3+</option>
                <option value="Lv4">Lv4</option>
              </select>
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">升級積分</label>
              <input v-model.number="editingTask.levelPoints" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">兌換積分</label>
              <input v-model.number="editingTask.rewardPoints" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            </div>
          </div>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
          <button @click="showTaskModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="saveTask" class="px-4 py-2 rounded-lg bg-emerald-500 dark:bg-emerald-600 text-white hover:bg-emerald-600 dark:hover:bg-emerald-500 transition-colors">儲存</button>
        </div>
      </div>
    </div>

    <!-- 禮品編輯 Modal -->
    <div v-if="showGiftModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showGiftModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">{{ editingGift.id ? '編輯禮品' : '新增禮品' }}</h3>
        </div>
        <div class="p-6 space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">禮品名稱</label>
            <input v-model="editingGift.title" type="text" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">描述</label>
            <textarea v-model="editingGift.description" rows="2" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50"></textarea>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">分類</label>
              <select v-model="editingGift.series" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="sustainable">永續探索</option>
                <option value="quality">質感創造</option>
                <option value="aesthetic">美學先鋒</option>
                <option value="premium">品味閃耀</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">等級限制</label>
              <select v-model="editingGift.level" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="EXPLORER">Lv1 探索者</option>
                <option value="CREATOR">Lv2 創造者</option>
                <option value="VISIONARY">Lv3 先行者</option>
                <option value="LUMINARY">Lv4 閃耀者</option>
              </select>
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">所需積分</label>
              <input v-model.number="editingGift.points" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">庫存數量</label>
              <input v-model.number="editingGift.stock" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">圖片網址</label>
            <input v-model="editingGift.image" type="text" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50" placeholder="/images/gifts/gift-1.jpg">
          </div>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
          <button @click="showGiftModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="saveGift" class="px-4 py-2 rounded-lg bg-purple-500 dark:bg-purple-600 text-white hover:bg-purple-600 dark:hover:bg-purple-500 transition-colors">儲存</button>
        </div>
      </div>
    </div>

    <!-- 庫存調整 Modal -->
    <div v-if="showStockModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showStockModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-sm">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">調整庫存</h3>
          <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">{{ editingGift.title }}</p>
        </div>
        <div class="p-6">
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">目前庫存: {{ editingGift.stock }}</label>
          <div class="flex items-center gap-3 mt-3">
            <button @click="stockAdjustment -= 10" class="w-10 h-10 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600">-10</button>
            <button @click="stockAdjustment -= 1" class="w-10 h-10 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600">-1</button>
            <input v-model.number="stockAdjustment" type="number" class="flex-1 px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white text-center focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            <button @click="stockAdjustment += 1" class="w-10 h-10 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600">+1</button>
            <button @click="stockAdjustment += 10" class="w-10 h-10 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600">+10</button>
          </div>
          <p class="text-sm text-gray-500 dark:text-gray-400 mt-3 text-center">調整後: {{ editingGift.stock + stockAdjustment }}</p>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
          <button @click="showStockModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="saveStock" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white hover:opacity-90 transition-opacity">確認調整</button>
        </div>
      </div>
    </div>

    <!-- 會員編輯 Modal -->
    <div v-if="showUserModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showUserModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-lg">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">編輯會員</h3>
        </div>
        <div class="p-6 space-y-4">
          <div class="flex items-center gap-4">
            <img :src="editingUser.avatar" class="w-16 h-16 rounded-full object-cover bg-gray-200">
            <div class="flex-1">
              <p class="text-lg font-bold text-gray-900 dark:text-white">{{ editingUser.name }}</p>
              <p class="text-sm text-gray-500 dark:text-gray-400">{{ editingUser.email }}</p>
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">等級</label>
              <select v-model="editingUser.level" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="EXPLORER">Lv1 探索者</option>
                <option value="CREATOR">Lv2 創造者</option>
                <option value="VISIONARY">Lv3 先行者</option>
                <option value="LUMINARY">Lv4 閃耀者</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">升級積分</label>
              <input v-model.number="editingUser.levelPoints" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">兌換積分</label>
            <input v-model.number="editingUser.rewardPoints" type="number" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          </div>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end gap-3">
          <button @click="showUserModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="saveUser" class="px-4 py-2 rounded-lg bg-sky-500 dark:bg-sky-600 text-white hover:bg-sky-600 dark:hover:bg-sky-500 transition-colors">儲存</button>
        </div>
      </div>
    </div>

    <!-- 登入紀錄 Modal -->
    <div v-if="showLoginHistoryModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showLoginHistoryModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-2xl max-h-[80vh] overflow-hidden">
        <div class="p-6 border-b border-gray-200 dark:border-gray-700">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white">{{ loginHistoryUser?.name }} 的登入紀錄</h3>
        </div>
        <div class="p-6 overflow-y-auto max-h-[50vh]">
          <div v-if="loginHistoryLoading" class="text-center py-8 text-gray-500">載入中...</div>
          <div v-else-if="loginHistoryList.length === 0" class="text-center py-8 text-gray-500">暫無登入紀錄</div>
          <table v-else class="w-full">
            <thead class="bg-gray-50 dark:bg-gray-700/50">
              <tr>
                <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-400">登入時間</th>
                <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-400">IP 位址</th>
                <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-400">登入方式</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
              <tr v-for="record in loginHistoryList" :key="record.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
                <td class="px-4 py-2 text-sm text-gray-900 dark:text-white whitespace-nowrap">{{ formatDateTime(record.loginTime) }}</td>
                <td class="px-4 py-2 text-sm text-gray-500 dark:text-gray-400 font-mono">{{ record.ipAddress || '-' }}</td>
                <td class="px-4 py-2 text-sm">
                  <span :class="record.loginType === 'LINE' ? 'bg-green-100 text-green-700' : 'bg-blue-100 text-blue-700'" class="px-2 py-0.5 rounded text-xs font-medium">
                    {{ record.loginType || 'EMAIL' }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-end">
          <button @click="showLoginHistoryModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">關閉</button>
        </div>
      </div>
    </div>

    <!-- 刪除確認 Modal -->
    <div v-if="showDeleteModal" class="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="showDeleteModal = false"></div>
      <div class="relative bg-white dark:bg-gray-800 rounded-2xl shadow-2xl w-full max-w-sm">
        <div class="p-6">
          <div class="w-12 h-12 rounded-full bg-red-100 dark:bg-red-900/30 flex items-center justify-center mx-auto mb-4">
            <svg class="w-6 h-6 text-red-600 dark:text-red-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
          </div>
          <h3 class="text-lg font-bold text-gray-900 dark:text-white text-center mb-2">確認刪除</h3>
          <p class="text-sm text-gray-500 dark:text-gray-400 text-center">確定要刪除「{{ deleteTarget?.name || deleteTarget?.title }}」嗎？此操作無法復原。</p>
        </div>
        <div class="p-6 border-t border-gray-200 dark:border-gray-700 flex justify-center gap-3">
          <button @click="showDeleteModal = false" class="px-4 py-2 rounded-lg bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors">取消</button>
          <button @click="executeDelete" class="px-4 py-2 rounded-lg bg-red-500 text-white hover:bg-red-600 transition-colors">確認刪除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted, onUnmounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { mockTasks, mockRewards } from '../mock.js'
import { useStore } from '@/store'
import { orderAPI, chatAPI, userAPI } from '@/api'

const router = useRouter()
const store = useStore()

// 登出功能
const handleLogout = () => {
  if (confirm('確定要登出嗎？')) {
    store.logout()
    router.push('/')
    store.showToast('已成功登出', 'info')
  }
}

// 重新整理功能
const isRefreshing = ref(false)

const refreshAllData = async () => {
  if (isRefreshing.value) return
  isRefreshing.value = true
  try {
    await Promise.all([
      fetchOrders(),
      fetchChatbotReplies(),
      fetchUsers()
    ])
  } catch (error) {
    console.error('Refresh failed:', error)
  } finally {
    isRefreshing.value = false
  }
}

// 標籤頁（彩色樣式 + icon）
const tabs = [
  {
    id: 'users',
    name: '會員管理',
    icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z" />',
    activeClass: 'bg-gradient-to-br from-sky-500 to-blue-400 text-white shadow-lg',
    inactiveClass: 'bg-gradient-to-br from-sky-100 to-blue-50 dark:from-sky-900/40 dark:to-blue-800/40 text-sky-700 dark:text-sky-300'
  },
  {
    id: 'tasks',
    name: '任務管理',
    icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />',
    activeClass: 'bg-gradient-to-br from-emerald-500 to-teal-400 text-white shadow-lg',
    inactiveClass: 'bg-gradient-to-br from-emerald-100 to-teal-50 dark:from-emerald-900/40 dark:to-teal-800/40 text-emerald-700 dark:text-emerald-300'
  },
  {
    id: 'gifts',
    name: '禮品管理',
    icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />',
    activeClass: 'bg-gradient-to-br from-purple-500 to-violet-400 text-white shadow-lg',
    inactiveClass: 'bg-gradient-to-br from-purple-100 to-violet-50 dark:from-purple-900/40 dark:to-violet-800/40 text-purple-700 dark:text-purple-300'
  },
  {
    id: 'redemptions',
    name: '兌換記錄',
    icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01" />',
    activeClass: 'bg-gradient-to-br from-amber-500 to-orange-400 text-white shadow-lg',
    inactiveClass: 'bg-gradient-to-br from-amber-100 to-orange-50 dark:from-amber-900/40 dark:to-orange-800/40 text-amber-700 dark:text-amber-300'
  },
  {
    id: 'chatbot',
    name: 'FAQ 設定',
    icon: '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />',
    activeClass: 'bg-gradient-to-br from-pink-500 to-rose-400 text-white shadow-lg',
    inactiveClass: 'bg-gradient-to-br from-pink-100 to-rose-50 dark:from-pink-900/40 dark:to-rose-800/40 text-pink-700 dark:text-pink-300'
  }
]
const activeTab = ref('users')

// 搜尋
const searchQuery = ref('')

// 會員篩選條件
const userFilter = reactive({
  level: '',
  status: ''
})

// 重置會員篩選
const resetUserFilter = () => {
  userFilter.level = ''
  userFilter.status = ''
  searchQuery.value = ''
}

// Modal 狀態
const showTaskModal = ref(false)
const showGiftModal = ref(false)
const showStockModal = ref(false)
const showUserModal = ref(false)
const showDeleteModal = ref(false)
const showLoginHistoryModal = ref(false)

// 編輯中的資料
const editingTask = reactive({})
const editingGift = reactive({})
const editingUser = reactive({})
const stockAdjustment = ref(0)
const deleteTarget = ref(null)
const deleteType = ref('')

// 登入紀錄
const loginHistoryUser = ref(null)
const loginHistoryList = ref([])
const loginHistoryLoading = ref(false)

// 使用者資料 - 統一使用設計好的預設頭貼
const defaultAvatar = 'https://api.dicebear.com/9.x/thumbs/svg?seed=Sarah&radius=50&backgroundColor=69d2e7&translateX=-10'

const users = ref([
  { id: 1, name: 'Hsuan', email: 'hsuan0501@outlook.com', avatar: defaultAvatar, level: 'CREATOR', levelPoints: 700, rewardPoints: 600, createdAt: '2025-12-15' },
  { id: 2, name: 'Matcha', email: 'matcha1108@example.com', avatar: defaultAvatar, level: 'EXPLORER', levelPoints: 120, rewardPoints: 80, createdAt: '2025-12-18' },
  { id: 3, name: 'May', email: 'may0529@example.com', avatar: defaultAvatar, level: 'EXPLORER', levelPoints: 100, rewardPoints: 50, createdAt: '2025-12-20' }
])

const filteredUsers = computed(() => {
  let result = users.value

  // 關鍵字搜尋
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(user =>
      user.name.toLowerCase().includes(query) ||
      user.email.toLowerCase().includes(query)
    )
  }

  // 等級篩選
  if (userFilter.level) {
    result = result.filter(user => user.level === userFilter.level)
  }

  // 狀態篩選
  if (userFilter.status) {
    if (userFilter.status === 'enabled') {
      result = result.filter(user => user.enabled !== false)
    } else if (userFilter.status === 'disabled') {
      result = result.filter(user => user.enabled === false)
    }
  }

  // 按 id 倒序排列（最新會員在最上面）
  return [...result].sort((a, b) => b.id - a.id)
})

// 分頁設定
const pageSize = 10

// 會員分頁
const userCurrentPage = ref(1)
const userTotalPages = computed(() => Math.ceil(filteredUsers.value.length / pageSize) || 1)
const paginatedUsers = computed(() => {
  const start = (userCurrentPage.value - 1) * pageSize
  return filteredUsers.value.slice(start, start + pageSize)
})

// 會員篩選變化時重置分頁
watch([searchQuery, () => userFilter.level, () => userFilter.status], () => { userCurrentPage.value = 1 })

// 任務資料 (使用完整的 30 個任務)
const taskList = ref([...mockTasks])
const taskSearchQuery = ref('')

// 任務篩選條件
const taskFilter = reactive({
  category: '',
  status: ''
})

// 重置任務篩選
const resetTaskFilter = () => {
  taskFilter.category = ''
  taskFilter.status = ''
  taskSearchQuery.value = ''
  taskCurrentPage.value = 1
}

// 任務過濾
const filteredTasks = computed(() => {
  let result = taskList.value

  // 關鍵字搜尋
  if (taskSearchQuery.value.trim()) {
    const query = taskSearchQuery.value.toLowerCase()
    result = result.filter(task =>
      task.title.toLowerCase().includes(query) ||
      task.category.toLowerCase().includes(query)
    )
  }

  // 分類篩選
  if (taskFilter.category) {
    result = result.filter(task => task.category === taskFilter.category)
  }

  // 狀態篩選
  if (taskFilter.status) {
    if (taskFilter.status === 'active') {
      result = result.filter(task => task.active !== false)
    } else if (taskFilter.status === 'inactive') {
      result = result.filter(task => task.active === false)
    }
  }

  return result
})

// 任務分頁
const taskCurrentPage = ref(1)
const taskTotalPages = computed(() => Math.ceil(filteredTasks.value.length / pageSize) || 1)
const paginatedTasks = computed(() => {
  const start = (taskCurrentPage.value - 1) * pageSize
  return filteredTasks.value.slice(start, start + pageSize)
})

// 篩選條件變化時重置分頁
watch([taskSearchQuery, () => taskFilter.category, () => taskFilter.status], () => { taskCurrentPage.value = 1 })

// 禮品資料 (使用完整的 32 個禮品)
const giftList = ref([...mockRewards])
const giftSearchQuery = ref('')

// 禮品篩選條件
const giftFilter = reactive({
  series: '',
  stock: ''
})

// 重置禮品篩選
const resetGiftFilter = () => {
  giftFilter.series = ''
  giftFilter.stock = ''
  giftSearchQuery.value = ''
  giftCurrentPage.value = 1
}

// 禮品過濾
const filteredGifts = computed(() => {
  let result = giftList.value

  // 關鍵字搜尋
  if (giftSearchQuery.value.trim()) {
    const query = giftSearchQuery.value.toLowerCase()
    result = result.filter(gift =>
      gift.title.toLowerCase().includes(query) ||
      gift.series?.toLowerCase().includes(query)
    )
  }

  // 系列篩選
  if (giftFilter.series) {
    result = result.filter(gift => gift.series === giftFilter.series)
  }

  // 庫存篩選
  if (giftFilter.stock) {
    if (giftFilter.stock === 'inStock') {
      result = result.filter(gift => gift.stock >= 10)
    } else if (giftFilter.stock === 'lowStock') {
      result = result.filter(gift => gift.stock > 0 && gift.stock < 10)
    } else if (giftFilter.stock === 'outOfStock') {
      result = result.filter(gift => gift.stock === 0)
    }
  }

  return result
})

// 禮品分頁
const giftCurrentPage = ref(1)
const giftTotalPages = computed(() => Math.ceil(filteredGifts.value.length / pageSize) || 1)
const paginatedGifts = computed(() => {
  const start = (giftCurrentPage.value - 1) * pageSize
  return filteredGifts.value.slice(start, start + pageSize)
})

// 篩選條件變化時重置分頁
watch([giftSearchQuery, () => giftFilter.series, () => giftFilter.stock], () => { giftCurrentPage.value = 1 })

// 兌換記錄
const redemptions = ref([])
const orderSearchQuery = ref('')
const expandedOrders = ref([])
const openStatusDropdown = ref(null)

const orderStatuses = [
  { value: 'PENDING', label: '訂單確認中' },
  { value: 'SHIPPED', label: '已出貨' },
  { value: 'DELIVERED', label: '已送達' },
  { value: 'COMPLETED', label: '取貨完成' },
  { value: 'CANCELLED', label: '已取消' }
]

// 展開/收合訂單詳情
const toggleOrderExpand = (orderId) => {
  const index = expandedOrders.value.indexOf(orderId)
  if (index > -1) {
    expandedOrders.value.splice(index, 1)
  } else {
    expandedOrders.value.push(orderId)
  }
}

// 狀態下拉選單控制
const toggleStatusDropdown = (orderId) => {
  openStatusDropdown.value = openStatusDropdown.value === orderId ? null : orderId
}

const selectOrderStatus = async (order, status) => {
  openStatusDropdown.value = null
  if (order.status !== status) {
    await updateOrderStatus(order, status)
  }
}

// 點擊外部關閉下拉選單
const closeStatusDropdown = (e) => {
  if (!e.target.closest('.relative')) {
    openStatusDropdown.value = null
  }
}

// 取得超商品牌名稱
const getStoreBrandName = (brand) => {
  const brands = {
    '7-11': '7-ELEVEN',
    'family': '全家',
    'hilife': '萊爾富',
    'okmart': 'OK超商'
  }
  return brands[brand] || brand || '-'
}

// 訂單篩選條件
const orderFilter = reactive({
  startDate: '',
  endDate: '',
  status: ''
})

// 重置篩選
const resetOrderFilter = () => {
  orderFilter.startDate = ''
  orderFilter.endDate = ''
  orderFilter.status = ''
  orderSearchQuery.value = ''
  currentPage.value = 1
}

// 過濾後的訂單（根據搜尋、日期、狀態）
const filteredRedemptions = computed(() => {
  let result = redemptions.value

  // 關鍵字搜尋
  if (orderSearchQuery.value.trim()) {
    const query = orderSearchQuery.value.toLowerCase()
    result = result.filter(order => {
      const userName = order.user?.name?.toLowerCase() || ''
      const giftTitle = order.items?.[0]?.gift?.title?.toLowerCase() || order.gift?.title?.toLowerCase() || ''
      const orderId = String(order.id)
      return userName.includes(query) || giftTitle.includes(query) || orderId.includes(query)
    })
  }

  // 狀態篩選
  if (orderFilter.status) {
    result = result.filter(order => order.status === orderFilter.status)
  }

  // 日期範圍篩選
  if (orderFilter.startDate) {
    const startDate = new Date(orderFilter.startDate)
    startDate.setHours(0, 0, 0, 0)
    result = result.filter(order => {
      const orderDate = new Date(order.createdAt)
      return orderDate >= startDate
    })
  }

  if (orderFilter.endDate) {
    const endDate = new Date(orderFilter.endDate)
    endDate.setHours(23, 59, 59, 999)
    result = result.filter(order => {
      const orderDate = new Date(order.createdAt)
      return orderDate <= endDate
    })
  }

  // 按 id 倒序排列（最新訂單在最上面）
  return result.sort((a, b) => b.id - a.id)
})

// 訂單分頁
const currentPage = ref(1)

// 總頁數
const totalPages = computed(() => {
  return Math.ceil(filteredRedemptions.value.length / pageSize) || 1
})

// 當前頁面的訂單
const paginatedRedemptions = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  const end = start + pageSize
  return filteredRedemptions.value.slice(start, end)
})

// 訂單確認中數量
const pendingOrderCount = computed(() => {
  return redemptions.value.filter(o => o.status === 'PENDING').length
})

// 篩選條件變化時重置分頁
watch([orderSearchQuery, () => orderFilter.status, () => orderFilter.startDate, () => orderFilter.endDate], () => {
  currentPage.value = 1
})

// 客服快速回覆設定（從後端載入）
const chatbotReplies = ref([])
const faqSearchQuery = ref('')

// FAQ 篩選器
const faqFilter = reactive({
  type: ''
})

// FAQ 類型選項
const faqTypes = [
  { id: 'points', name: '如何獲得積分？' },
  { id: 'redeem', name: '如何兌換禮品？' },
  { id: 'level', name: '等級制度說明' },
  { id: 'shipping', name: '出貨進度' },
  { id: 'support', name: '人工客服' }
]

// 重設 FAQ 篩選
const resetFaqFilter = () => {
  faqFilter.type = ''
  faqSearchQuery.value = ''
  faqCurrentPage.value = 1
}

// FAQ 固定順序（對應前台智能客服按鈕順序）
const faqOrder = ['points', 'redeem', 'level', 'shipping', 'support']

// FAQ 過濾與排序
const filteredFaqs = computed(() => {
  let faqs = chatbotReplies.value

  // 類型篩選
  if (faqFilter.type) {
    faqs = faqs.filter(faq => faq.id === faqFilter.type)
  }

  // 搜尋過濾
  if (faqSearchQuery.value.trim()) {
    const query = faqSearchQuery.value.toLowerCase()
    faqs = faqs.filter(faq =>
      faq.keyword?.toLowerCase().includes(query) ||
      faq.reply?.toLowerCase().includes(query) ||
      faq.id?.toLowerCase().includes(query)
    )
  }

  // 按固定順序排序
  return [...faqs].sort((a, b) => {
    const indexA = faqOrder.indexOf(a.id)
    const indexB = faqOrder.indexOf(b.id)
    // 不在列表中的排到最後
    if (indexA === -1 && indexB === -1) return 0
    if (indexA === -1) return 1
    if (indexB === -1) return -1
    return indexA - indexB
  })
})

// FAQ 分頁
const faqCurrentPage = ref(1)
const faqTotalPages = computed(() => Math.ceil(filteredFaqs.value.length / pageSize) || 1)
const paginatedFaqs = computed(() => {
  const start = (faqCurrentPage.value - 1) * pageSize
  return filteredFaqs.value.slice(start, start + pageSize)
})

// FAQ 篩選變化時重置分頁
watch([faqSearchQuery, () => faqFilter.type], () => { faqCurrentPage.value = 1 })

// 編輯中的客服回覆
const editingReply = reactive({})
const showReplyModal = ref(false)

// 載入訂單資料
const fetchOrders = async () => {
  try {
    const response = await orderAPI.getAll()
    redemptions.value = (response.data || []).sort((a, b) =>
      new Date(b.createdAt) - new Date(a.createdAt)
    )
  } catch (error) {
    console.error('Failed to fetch orders:', error)
  }
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 載入客服回覆設定
const fetchChatbotReplies = async () => {
  try {
    const response = await chatAPI.getReplies()
    chatbotReplies.value = response.data || []
  } catch (error) {
    console.error('Failed to fetch chatbot replies:', error)
  }
}

// 載入會員列表
const fetchUsers = async () => {
  try {
    const response = await userAPI.getAll()
    // 過濾掉管理員
    users.value = (response.data || [])
      .filter(u => !u.admin)
      .map(u => ({
        id: u.id,
        name: u.name,
        email: u.email,
        avatar: u.avatar || 'https://api.dicebear.com/9.x/thumbs/svg?seed=Sarah&radius=50&backgroundColor=69d2e7&translateX=-10',
        level: u.level,
        levelPoints: u.upgradePoints || 0,
        rewardPoints: u.rewardPoints || 0,
        enabled: u.enabled !== false,
        createdAt: u.createdAt ? new Date(u.createdAt).toLocaleDateString('zh-TW') : '-'
      }))
  } catch (error) {
    console.error('Failed to fetch users:', error)
  }
}

onMounted(() => {
  fetchOrders()
  fetchChatbotReplies()
  fetchUsers()
  document.addEventListener('click', closeStatusDropdown)
})

onUnmounted(() => {
  document.removeEventListener('click', closeStatusDropdown)
})

// 任務 Modal
const openTaskModal = (task = null) => {
  if (task) {
    Object.assign(editingTask, { ...task })
  } else {
    Object.assign(editingTask, {
      id: null,
      title: '',
      description: '',
      category: 'daily',
      levelRequired: '全等級',
      levelPoints: 10,
      rewardPoints: 10,
      active: true
    })
  }
  showTaskModal.value = true
}

const saveTask = () => {
  if (!editingTask.title) {
    store.showToast('請填寫任務名稱', 'error')
    return
  }

  if (editingTask.id) {
    // 編輯
    const index = taskList.value.findIndex(t => t.id === editingTask.id)
    if (index !== -1) {
      taskList.value[index] = { ...editingTask }
    }
    store.showToast('任務已更新', 'success')
  } else {
    // 新增
    const newTask = {
      ...editingTask,
      id: Date.now(),
      image: '/images/tasks/task-1.jpg'
    }
    taskList.value.unshift(newTask)
    store.showToast('任務已新增', 'success')
  }
  showTaskModal.value = false
}

const toggleTaskStatus = (task) => {
  task.active = task.active === false ? true : false
  store.showToast(`任務已${task.active ? '啟用' : '停用'}`, 'success')
}

// 禮品 Modal
const openGiftModal = (gift = null) => {
  if (gift) {
    Object.assign(editingGift, { ...gift })
  } else {
    Object.assign(editingGift, {
      id: null,
      title: '',
      description: '',
      series: 'sustainable',
      level: 'EXPLORER',
      points: 100,
      stock: 50,
      image: '/images/gifts/gift-1.jpg'
    })
  }
  showGiftModal.value = true
}

const saveGift = () => {
  if (!editingGift.title) {
    store.showToast('請填寫禮品名稱', 'error')
    return
  }

  if (editingGift.id) {
    // 編輯
    const index = giftList.value.findIndex(g => g.id === editingGift.id)
    if (index !== -1) {
      giftList.value[index] = { ...editingGift }
    }
    store.showToast('禮品已更新', 'success')
  } else {
    // 新增
    const newGift = {
      ...editingGift,
      id: Date.now()
    }
    giftList.value.unshift(newGift)
    store.showToast('禮品已新增', 'success')
  }
  showGiftModal.value = false
}

// 庫存調整 Modal
const openStockModal = (gift) => {
  Object.assign(editingGift, { ...gift })
  stockAdjustment.value = 0
  showStockModal.value = true
}

const saveStock = () => {
  const index = giftList.value.findIndex(g => g.id === editingGift.id)
  if (index !== -1) {
    giftList.value[index].stock += stockAdjustment.value
    if (giftList.value[index].stock < 0) giftList.value[index].stock = 0
  }
  store.showToast('庫存已調整', 'success')
  showStockModal.value = false
}

// 會員 Modal
const openUserModal = (user) => {
  Object.assign(editingUser, { ...user })
  showUserModal.value = true
}

const saveUser = () => {
  const index = users.value.findIndex(u => u.id === editingUser.id)
  if (index !== -1) {
    users.value[index] = { ...editingUser }
  }
  store.showToast('會員資料已更新', 'success')
  showUserModal.value = false
}

// 格式化日期時間
const formatDateTime = (dateTime) => {
  if (!dateTime) return '-'
  const date = new Date(dateTime)
  return date.toLocaleString('zh-TW', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 停用/啟用帳號
const toggleUserStatus = async (user) => {
  try {
    const response = await userAPI.toggleStatus(user.id)
    // 更新本地資料
    const index = users.value.findIndex(u => u.id === user.id)
    if (index !== -1) {
      users.value[index] = { ...users.value[index], enabled: response.data.user.enabled }
    }
    const statusText = response.data.user.enabled ? '啟用' : '停用'
    store.showToast(`帳號已${statusText}`, 'success')
  } catch (error) {
    console.error('Failed to toggle user status:', error)
    store.showToast('操作失敗，請稍後再試', 'error')
  }
}

// 刪除會員
const deleteUser = (user) => {
  confirmDelete('user', user)
}

// 刪除確認
const confirmDelete = (type, item) => {
  deleteType.value = type
  deleteTarget.value = item
  showDeleteModal.value = true
}

const executeDelete = async () => {
  if (deleteType.value === 'user') {
    try {
      await userAPI.delete(deleteTarget.value.id)
      users.value = users.value.filter(u => u.id !== deleteTarget.value.id)
      store.showToast('會員已刪除', 'success')
    } catch (error) {
      console.error('Failed to delete user:', error)
      store.showToast('刪除失敗，請稍後再試', 'error')
    }
  } else if (deleteType.value === 'task') {
    taskList.value = taskList.value.filter(t => t.id !== deleteTarget.value.id)
    store.showToast('任務已刪除', 'success')
  } else if (deleteType.value === 'gift') {
    giftList.value = giftList.value.filter(g => g.id !== deleteTarget.value.id)
    store.showToast('禮品已刪除', 'success')
  }
  showDeleteModal.value = false
}

// 訂單狀態更新
const updateOrderStatus = async (order, newStatus) => {
  const oldStatus = order.status
  try {
    await orderAPI.updateStatus(order.id, newStatus)
    // 找到並更新 redemptions 陣列中的對應訂單
    const index = redemptions.value.findIndex(r => r.id === order.id)
    if (index !== -1) {
      redemptions.value[index] = { ...redemptions.value[index], status: newStatus }
    }
    store.showToast(`訂單 #${order.id} 狀態已更新為「${getStatusText(newStatus)}」`, 'success')
  } catch (error) {
    console.error('Failed to update order status:', error)
    store.showToast('更新失敗，請稍後再試', 'error')
  }
}

// 樣式輔助函數
const getLevelBadgeClass = (level) => {
  const classes = {
    EXPLORER: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    CREATOR: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    VISIONARY: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    LUMINARY: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[level] || classes.EXPLORER
}

const getCategoryBadgeClass = (category) => {
  const classes = {
    daily: 'bg-pink-100 dark:bg-pink-900/30 text-pink-700 dark:text-pink-400',
    financial: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    investment: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    esg: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    social: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[category] || 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-400'
}

const getCategoryName = (category) => {
  const names = {
    daily: '日常互動',
    financial: '理財學習',
    investment: '投資實踐',
    esg: '永續行動',
    social: '社群成就'
  }
  return names[category] || category
}

const getSeriesBadgeClass = (series) => {
  const classes = {
    sustainable: 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    quality: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    aesthetic: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    premium: 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400',
    '永續探索': 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    '質感創造': 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    '美學先鋒': 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    '品味閃耀': 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[series] || 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-400'
}

const getSeriesName = (series) => {
  const names = {
    sustainable: '永續探索',
    quality: '質感創造',
    aesthetic: '美學先鋒',
    premium: '品味閃耀',
    '永續探索': '永續探索',
    '質感創造': '質感創造',
    '美學先鋒': '美學先鋒',
    '品味閃耀': '品味閃耀'
  }
  return names[series] || series
}

const getStatusBadgeClass = (status) => {
  const classes = {
    PENDING: 'bg-pink-100 dark:bg-pink-900/30 text-pink-700 dark:text-pink-400',
    SHIPPED: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    DELIVERED: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    COMPLETED: 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400',
    CANCELLED: 'bg-gray-100 dark:bg-gray-900/30 text-gray-700 dark:text-gray-400'
  }
  return classes[status] || classes.PENDING
}

const getStatusSelectClass = (status) => {
  const classes = {
    PENDING: 'bg-pink-100 dark:bg-pink-900/40 text-pink-700 dark:text-pink-300 border border-pink-200 dark:border-pink-700/50',
    SHIPPED: 'bg-blue-100 dark:bg-blue-900/40 text-blue-700 dark:text-blue-300 border border-blue-200 dark:border-blue-700/50',
    DELIVERED: 'bg-amber-100 dark:bg-amber-900/40 text-amber-700 dark:text-amber-300 border border-amber-200 dark:border-amber-700/50',
    COMPLETED: 'bg-green-100 dark:bg-green-900/40 text-green-700 dark:text-green-300 border border-green-200 dark:border-green-700/50',
    CANCELLED: 'bg-gray-100 dark:bg-gray-700 text-gray-600 dark:text-gray-300 border border-gray-200 dark:border-gray-600'
  }
  return classes[status] || classes.PENDING
}

const getStatusText = (status) => {
  const texts = {
    PENDING: '訂單確認中',
    SHIPPED: '已出貨',
    DELIVERED: '已送達',
    COMPLETED: '取貨完成',
    CANCELLED: '已取消'
  }
  return texts[status] || status
}

// 客服回覆類型名稱
const getReplyTypeName = (id) => {
  const names = {
    points: '如何獲得積分？',
    redeem: '如何兌換禮品？',
    level: '等級制度說明',
    shipping: '出貨進度',
    support: '人工客服',
    order: '訂單查詢',
    greeting: '打招呼',
    thanks: '感謝'
  }
  return names[id] || id
}

// 打開客服回覆編輯 Modal
const openReplyModal = (reply = null) => {
  if (reply) {
    Object.assign(editingReply, { ...reply, _isEdit: true })
  } else {
    Object.assign(editingReply, {
      id: '',
      keyword: '',
      reply: '',
      _isEdit: false
    })
  }
  showReplyModal.value = true
}

// 儲存客服回覆
const saveReply = async () => {
  if (!editingReply.id || !editingReply.keyword || !editingReply.reply) {
    store.showToast('請填寫完整資料', 'error')
    return
  }

  try {
    // 呼叫後端 API 保存
    await chatAPI.saveReply({
      id: editingReply.id,
      keyword: editingReply.keyword,
      reply: editingReply.reply
    })

    if (editingReply._isEdit) {
      // 編輯現有回覆
      const index = chatbotReplies.value.findIndex(r => r.id === editingReply.id)
      if (index !== -1) {
        chatbotReplies.value[index] = {
          id: editingReply.id,
          keyword: editingReply.keyword,
          reply: editingReply.reply
        }
      }
      store.showToast('快速回覆已更新', 'success')
    } else {
      // 新增回覆
      if (chatbotReplies.value.some(r => r.id === editingReply.id)) {
        store.showToast('此類型名稱已存在', 'error')
        return
      }
      chatbotReplies.value.push({
        id: editingReply.id,
        keyword: editingReply.keyword,
        reply: editingReply.reply
      })
      store.showToast('快速回覆已新增', 'success')
    }

    showReplyModal.value = false
  } catch (error) {
    console.error('Failed to save reply:', error)
    store.showToast('儲存失敗，請稍後再試', 'error')
  }
}

// 刪除客服回覆
const confirmDeleteReply = async (reply) => {
  if (confirm(`確定要刪除「${getReplyTypeName(reply.id)}」的快速回覆嗎？`)) {
    try {
      await chatAPI.deleteReply(reply.id)
      chatbotReplies.value = chatbotReplies.value.filter(r => r.id !== reply.id)
      store.showToast('快速回覆已刪除', 'success')
    } catch (error) {
      console.error('Failed to delete reply:', error)
      store.showToast('刪除失敗，請稍後再試', 'error')
    }
  }
}
</script>
