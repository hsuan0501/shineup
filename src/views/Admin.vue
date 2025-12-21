<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 標題 -->
    <div class="mb-8 flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">管理後台</h1>
        <p class="text-sm text-gray-600 dark:text-gray-400">管理會員、任務、禮品與系統設定</p>
      </div>
      <!-- 展示用重置按鈕 (低調版) -->
      <button
        v-if="store.isAuthenticated"
        @click="handleResetUser"
        :disabled="isResetting"
        class="px-3 py-1.5 text-xs text-gray-400 hover:text-gray-600 dark:hover:text-gray-300 transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-1"
      >
        <svg v-if="isResetting" class="w-3 h-3 animate-spin" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
        </svg>
        <svg v-else class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
        </svg>
        {{ isResetting ? '重置中' : '重置' }}
      </button>
    </div>

    <!-- 統計卡片 -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-sky-400 to-sky-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ users.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">總會員數</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-emerald-400 to-emerald-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ taskList.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">任務數量</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-purple-400 to-purple-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ giftList.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">禮品數量</p>
          </div>
        </div>
      </div>

      <div class="bg-white dark:bg-gray-700/70 rounded-2xl p-5 border dark:border-gray-600/30 hover:scale-[1.02] transition-transform">
        <div class="flex items-center gap-3">
          <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-amber-400 to-amber-500 flex items-center justify-center">
            <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
            </svg>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900 dark:text-white">{{ redemptions.length }}</p>
            <p class="text-xs text-gray-500 dark:text-gray-400">兌換訂單</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 標籤頁 -->
    <div class="flex gap-2 mb-6 overflow-x-auto pb-2">
      <button v-for="tab in tabs" :key="tab.id" @click="activeTab = tab.id"
        :class="[
          'px-5 py-2.5 rounded-full text-sm font-medium whitespace-nowrap transition-all',
          activeTab === tab.id
            ? 'bg-gradient-to-r from-sky-400 to-purple-400 text-white shadow-lg'
            : 'bg-white dark:bg-gray-700 text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600 border border-gray-200 dark:border-gray-600'
        ]">
        {{ tab.name }}
      </button>
    </div>

    <!-- 會員管理 -->
    <div v-if="activeTab === 'users'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">會員列表</h3>
        <div class="relative">
          <input type="text" v-model="searchQuery" placeholder="搜尋會員..."
            class="pl-9 pr-4 py-2 rounded-lg bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-sm focus:outline-none focus:ring-2 focus:ring-sky-500/50">
          <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">等級</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">升級積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">兌換積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">註冊日期</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="user in filteredUsers" :key="user.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <div class="flex items-center gap-3">
                  <img :src="user.avatar" class="w-8 h-8 rounded-full object-cover bg-gray-200">
                  <div>
                    <p class="text-sm font-medium text-gray-900 dark:text-white">{{ user.name }}</p>
                    <p class="text-xs text-gray-500 dark:text-gray-400">{{ user.email }}</p>
                  </div>
                </div>
              </td>
              <td class="px-4 py-3">
                <span :class="getLevelBadgeClass(user.level)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ user.level }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ user.levelPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ user.rewardPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ user.createdAt }}</td>
              <td class="px-4 py-3">
                <button @click="openUserModal(user)" class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 任務管理 -->
    <div v-if="activeTab === 'tasks'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">任務列表</h3>
        <button @click="openTaskModal()" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white text-sm font-medium hover:opacity-90 transition-opacity">
          + 新增任務
        </button>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">任務名稱</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">分類</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">等級限制</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="task in taskList" :key="task.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <p class="text-sm font-medium text-gray-900 dark:text-white">{{ task.title }}</p>
              </td>
              <td class="px-4 py-3">
                <span :class="getCategoryBadgeClass(task.category)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ getCategoryName(task.category) }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">+{{ task.levelPoints }} / +{{ task.rewardPoints }}</td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ task.levelRequired }}</td>
              <td class="px-4 py-3">
                <span :class="task.active !== false ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-gray-100 dark:bg-gray-800 text-gray-500 dark:text-gray-400'" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ task.active !== false ? '啟用' : '停用' }}
                </span>
              </td>
              <td class="px-4 py-3 flex gap-2">
                <button @click="openTaskModal(task)" class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
                <button @click="toggleTaskStatus(task)" class="text-amber-600 dark:text-amber-400 hover:underline text-sm">
                  {{ task.active !== false ? '停用' : '啟用' }}
                </button>
                <button @click="confirmDelete('task', task)" class="text-red-600 dark:text-red-400 hover:underline text-sm">刪除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 禮品管理 -->
    <div v-if="activeTab === 'gifts'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600 flex items-center justify-between">
        <h3 class="font-bold text-gray-900 dark:text-white">禮品列表</h3>
        <button @click="openGiftModal()" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white text-sm font-medium hover:opacity-90 transition-opacity">
          + 新增禮品
        </button>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">系列</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">庫存</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="gift in giftList" :key="gift.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3">
                <div class="flex items-center gap-3">
                  <img :src="gift.image" class="w-10 h-10 rounded-lg object-cover bg-gray-200">
                  <p class="text-sm font-medium text-gray-900 dark:text-white">{{ gift.title }}</p>
                </div>
              </td>
              <td class="px-4 py-3">
                <span :class="getSeriesBadgeClass(gift.series)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ gift.series }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ gift.points }}</td>
              <td class="px-4 py-3 text-sm" :class="gift.stock < 10 ? 'text-red-600 dark:text-red-400 font-bold' : 'text-gray-900 dark:text-white'">
                {{ gift.stock }}
              </td>
              <td class="px-4 py-3">
                <span :class="gift.stock > 0 ? 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400' : 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'"
                  class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ gift.stock > 0 ? '有庫存' : '已售完' }}
                </span>
              </td>
              <td class="px-4 py-3 flex gap-2">
                <button @click="openGiftModal(gift)" class="text-sky-600 dark:text-sky-400 hover:underline text-sm">編輯</button>
                <button @click="openStockModal(gift)" class="text-amber-600 dark:text-amber-400 hover:underline text-sm">調整庫存</button>
                <button @click="confirmDelete('gift', gift)" class="text-red-600 dark:text-red-400 hover:underline text-sm">刪除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 兌換記錄 -->
    <div v-if="activeTab === 'redemptions'" class="bg-white dark:bg-gray-700/70 rounded-2xl border dark:border-gray-600/30 overflow-hidden">
      <div class="p-4 border-b border-gray-200 dark:border-gray-600">
        <h3 class="font-bold text-gray-900 dark:text-white">兌換記錄</h3>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50 dark:bg-gray-800/50">
            <tr>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">訂單編號</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">會員</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">禮品</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">積分</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">狀態</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">日期</th>
              <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-400 uppercase">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 dark:divide-gray-600">
            <tr v-for="order in redemptions" :key="order.id" class="hover:bg-gray-50 dark:hover:bg-gray-700/50">
              <td class="px-4 py-3 text-sm font-mono text-gray-900 dark:text-white">#{{ order.id }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.userName }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.giftName }}</td>
              <td class="px-4 py-3 text-sm text-gray-900 dark:text-white">{{ order.points }}</td>
              <td class="px-4 py-3">
                <span :class="getStatusBadgeClass(order.status)" class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ getStatusText(order.status) }}
                </span>
              </td>
              <td class="px-4 py-3 text-sm text-gray-500 dark:text-gray-400">{{ order.date }}</td>
              <td class="px-4 py-3">
                <select v-model="order.status" @change="updateOrderStatus(order)"
                  class="text-sm bg-transparent border border-gray-200 dark:border-gray-600 rounded-lg px-2 py-1 focus:outline-none focus:ring-2 focus:ring-sky-500/50 dark:text-white">
                  <option value="pending">待處理</option>
                  <option value="completed">已完成</option>
                  <option value="shipped">已出貨</option>
                  <option value="cancelled">已取消</option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
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
          <button @click="saveTask" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white hover:opacity-90 transition-opacity">儲存</button>
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
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">系列</label>
              <select v-model="editingGift.series" class="w-full px-4 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50">
                <option value="永續探索">永續探索</option>
                <option value="質感創造">質感創造</option>
                <option value="美學先鋒">美學先鋒</option>
                <option value="品味閃耀">品味閃耀</option>
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
          <button @click="saveGift" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white hover:opacity-90 transition-opacity">儲存</button>
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
          <button @click="saveUser" class="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-400 to-blue-500 text-white hover:opacity-90 transition-opacity">儲存</button>
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
          <p class="text-sm text-gray-500 dark:text-gray-400 text-center">確定要刪除「{{ deleteTarget?.title }}」嗎？此操作無法復原。</p>
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
import { ref, computed, reactive } from 'vue'
import { mockTasks, mockRewards } from '../mock.js'
import { useStore } from '@/store'

const store = useStore()

// 重置功能
const isResetting = ref(false)

const handleResetUser = async () => {
  if (isResetting.value) return

  isResetting.value = true
  try {
    const result = await store.resetUser()
    if (result.success) {
      store.showToast('展示資料已重置', 'success')
    } else {
      store.showToast(result.message || '重置失敗', 'error')
    }
  } catch (error) {
    store.showToast('重置失敗，請稍後再試', 'error')
  } finally {
    isResetting.value = false
  }
}

// 標籤頁
const tabs = [
  { id: 'users', name: '會員管理' },
  { id: 'tasks', name: '任務管理' },
  { id: 'gifts', name: '禮品管理' },
  { id: 'redemptions', name: '兌換記錄' }
]
const activeTab = ref('users')

// 搜尋
const searchQuery = ref('')

// Modal 狀態
const showTaskModal = ref(false)
const showGiftModal = ref(false)
const showStockModal = ref(false)
const showUserModal = ref(false)
const showDeleteModal = ref(false)

// 編輯中的資料
const editingTask = reactive({})
const editingGift = reactive({})
const editingUser = reactive({})
const stockAdjustment = ref(0)
const deleteTarget = ref(null)
const deleteType = ref('')

// 使用者資料
const users = ref([
  { id: 1, name: 'Matcha', email: 'matcha@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Matcha', level: 'CREATOR', levelPoints: 700, rewardPoints: 600, createdAt: '2024-10-15' },
  { id: 2, name: 'Alice', email: 'alice@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Alice', level: 'EXPLORER', levelPoints: 120, rewardPoints: 80, createdAt: '2024-11-01' },
  { id: 3, name: 'Bob', email: 'bob@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Bob', level: 'VISIONARY', levelPoints: 980, rewardPoints: 450, createdAt: '2024-09-20' },
  { id: 4, name: 'Carol', email: 'carol@example.com', avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Carol', level: 'LUMINARY', levelPoints: 1850, rewardPoints: 1200, createdAt: '2024-08-05' }
])

const filteredUsers = computed(() => {
  if (!searchQuery.value) return users.value
  const query = searchQuery.value.toLowerCase()
  return users.value.filter(user =>
    user.name.toLowerCase().includes(query) ||
    user.email.toLowerCase().includes(query)
  )
})

// 任務資料 (使用 ref 讓它可修改)
const taskList = ref([...mockTasks.slice(0, 10)])

// 禮品資料 (使用 ref 讓它可修改)
const giftList = ref([...mockRewards.slice(0, 10)])

// 兌換記錄
const redemptions = ref([
  { id: 1001, userName: 'Matcha', giftName: '環保便攜吸管組', points: 100, status: 'completed', date: '2024-11-24' },
  { id: 1002, userName: 'Alice', giftName: '雲朵筆電包', points: 350, status: 'shipped', date: '2024-11-23' },
  { id: 1003, userName: 'Bob', giftName: '香氛蠟燭暖燈', points: 800, status: 'pending', date: '2024-11-22' },
  { id: 1004, userName: 'Carol', giftName: '星級饗宴餐券', points: 2500, status: 'completed', date: '2024-11-21' }
])

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
      series: '永續探索',
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

// 刪除確認
const confirmDelete = (type, item) => {
  deleteType.value = type
  deleteTarget.value = item
  showDeleteModal.value = true
}

const executeDelete = () => {
  if (deleteType.value === 'task') {
    taskList.value = taskList.value.filter(t => t.id !== deleteTarget.value.id)
    store.showToast('任務已刪除', 'success')
  } else if (deleteType.value === 'gift') {
    giftList.value = giftList.value.filter(g => g.id !== deleteTarget.value.id)
    store.showToast('禮品已刪除', 'success')
  }
  showDeleteModal.value = false
}

// 訂單狀態更新
const updateOrderStatus = (order) => {
  store.showToast(`訂單 #${order.id} 狀態已更新為「${getStatusText(order.status)}」`, 'success')
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
    '永續探索': 'bg-emerald-100 dark:bg-emerald-900/30 text-emerald-700 dark:text-emerald-400',
    '質感創造': 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    '美學先鋒': 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    '品味閃耀': 'bg-purple-100 dark:bg-purple-900/30 text-purple-700 dark:text-purple-400'
  }
  return classes[series] || 'bg-gray-100 dark:bg-gray-800 text-gray-700 dark:text-gray-400'
}

const getStatusBadgeClass = (status) => {
  const classes = {
    pending: 'bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-400',
    completed: 'bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400',
    shipped: 'bg-blue-100 dark:bg-blue-900/30 text-blue-700 dark:text-blue-400',
    cancelled: 'bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-400'
  }
  return classes[status] || classes.pending
}

const getStatusText = (status) => {
  const texts = {
    pending: '待處理',
    completed: '已完成',
    shipped: '已出貨',
    cancelled: '已取消'
  }
  return texts[status] || status
}
</script>
