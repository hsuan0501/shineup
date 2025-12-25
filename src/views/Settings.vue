<template>
  <div class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- 標題 + 按鈕 -->
    <div class="mb-8 flex items-end justify-between">
      <div>
        <h1 class="text-2xl font-bold text-light-text dark:text-dark-text mb-2">帳號設定</h1>
        <p class="text-sm text-gray-600 dark:text-gray-400">管理您的個人資料與偏好設定</p>
      </div>
      <div class="flex items-center gap-2">
        <!-- 登出按鈕 (灰色) -->
        <button @click="handleLogout"
          class="px-4 py-2 rounded-full hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl bg-gray-100 dark:bg-gray-700 text-gray-600 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 border border-gray-200 dark:border-gray-600">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          登出
        </button>
        <!-- 會員中心按鈕 (藍色) -->
        <router-link to="/profile"
          class="px-4 py-2 rounded-full hover:scale-105 active:scale-95 transition-all duration-300 flex items-center gap-2 font-medium text-sm backdrop-blur-xl bg-sky-50/80 dark:bg-sky-900/20 text-sky-600 dark:text-sky-400 hover:bg-sky-100 dark:hover:bg-sky-900/30 border border-sky-200 dark:border-sky-800">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
          會員中心
        </router-link>
      </div>
    </div>

    <!-- 設定區塊 -->
    <div class="space-y-6">
      <!-- 個人資料 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-sky-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
          個人資料
        </h3>

        <div class="space-y-4">
          <!-- 姓名 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              姓名 <span class="text-pink-500">*</span>
            </label>
            <input type="text" v-model="form.name"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.name ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入姓名">
            <p v-if="errors.name" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.name }}</p>
          </div>

          <!-- Email -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">電子郵件</label>
            <input type="email" v-model="form.email" disabled
              class="w-full px-4 py-2.5 rounded-xl bg-gray-100 dark:bg-gray-800/80 border border-gray-200 dark:border-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed">
            <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">電子郵件無法修改</p>
          </div>

          <!-- 生日 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              生日 <span class="text-pink-500">*</span>
            </label>
            <input type="date" v-model="form.birthday"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.birthday ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]">
            <p v-if="errors.birthday" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.birthday }}</p>
            <p v-else class="text-xs text-gray-500 dark:text-gray-400 mt-1">生日資料僅限修改一次</p>
          </div>

          <!-- 電話 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              手機號碼 <span class="text-pink-500">*</span>
            </label>
            <input type="tel" v-model="form.phone"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.phone ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入手機號碼">
            <p v-if="errors.phone" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.phone }}</p>
          </div>
        </div>
      </div>

      <!-- 收件地址 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          收件地址
        </h3>

        <div class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <!-- 縣市 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                縣市 <span class="text-pink-500">*</span>
              </label>
              <select v-model="form.city"
                :class="[
                  'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                  errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
                ]">
                <option value="">請選擇</option>
                <option v-for="city in cities" :key="city" :value="city">{{ city }}</option>
              </select>
            </div>
            <!-- 區域 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                區域 <span class="text-pink-500">*</span>
              </label>
              <input type="text" v-model="form.district"
                :class="[
                  'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                  errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
                ]"
                placeholder="請輸入區域">
            </div>
          </div>

          <!-- 詳細地址 -->
          <div>
            <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              詳細地址 <span class="text-pink-500">*</span>
            </label>
            <input type="text" v-model="form.address"
              :class="[
                'w-full px-4 py-2.5 rounded-xl bg-gray-50 dark:bg-gray-800/50 border text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-sky-500/50 focus:border-sky-500 transition-all',
                errors.address ? 'border-pink-500' : 'border-gray-200 dark:border-gray-600'
              ]"
              placeholder="請輸入詳細地址">
            <p v-if="errors.address" class="text-xs text-pink-800 dark:text-pink-300 mt-1">{{ errors.address }}</p>
          </div>
        </div>
      </div>

      <!-- 帳號連結 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.828 10.172a4 4 0 00-5.656 0l-4 4a4 4 0 105.656 5.656l1.102-1.101m-.758-4.899a4 4 0 005.656 0l4-4a4 4 0 00-5.656-5.656l-1.1 1.1" />
          </svg>
          帳號連結
        </h3>

        <div class="space-y-4">
          <!-- LINE 綁定 -->
          <div class="flex items-center justify-between py-3 px-4 rounded-xl bg-gray-50 dark:bg-gray-800/50">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-[#06C755] flex items-center justify-center">
                <svg class="w-6 h-6 text-white" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M19.365 9.863c.349 0 .63.285.63.631 0 .345-.281.63-.63.63H17.61v1.125h1.755c.349 0 .63.283.63.63 0 .344-.281.629-.63.629h-2.386c-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63h2.386c.349 0 .63.285.63.63 0 .349-.281.63-.63.63H17.61v1.125h1.755zm-3.855 3.016c0 .27-.174.51-.432.596-.064.021-.133.031-.199.031-.211 0-.391-.09-.51-.25l-2.443-3.317v2.94c0 .344-.279.629-.631.629-.346 0-.626-.285-.626-.629V8.108c0-.27.173-.51.43-.595.06-.023.136-.033.194-.033.195 0 .375.104.495.254l2.462 3.33V8.108c0-.345.282-.63.63-.63.345 0 .63.285.63.63v4.771zm-5.741 0c0 .344-.282.629-.631.629-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63.349 0 .631.285.631.63v4.771zm-2.466.629H4.917c-.345 0-.63-.285-.63-.629V8.108c0-.345.285-.63.63-.63.348 0 .63.285.63.63v4.141h1.756c.348 0 .629.283.629.63 0 .344-.281.629-.629.629M24 10.314C24 4.943 18.615.572 12 .572S0 4.943 0 10.314c0 4.811 4.27 8.842 10.035 9.608.391.082.923.258 1.058.59.12.301.079.766.038 1.08l-.164 1.02c-.045.301-.24 1.186 1.049.645 1.291-.539 6.916-4.078 9.436-6.975C23.176 14.393 24 12.458 24 10.314"/>
                </svg>
              </div>
              <div>
                <p class="text-sm font-medium text-gray-900 dark:text-white">LINE</p>
                <p class="text-xs text-gray-500 dark:text-gray-400">
                  {{ isLineBound ? '已綁定' : '綁定後可使用 LINE 快速登入' }}
                </p>
              </div>
            </div>
            <button v-if="!isLineBound" @click="handleBindLine"
              class="px-4 py-2 rounded-full bg-[#06C755] text-white text-sm font-medium hover:bg-[#05b34c] transition-all hover:scale-105 active:scale-95">
              綁定
            </button>
            <button v-else @click="handleUnbindLine"
              class="px-4 py-2 rounded-full bg-gray-200 dark:bg-gray-600 text-gray-600 dark:text-gray-300 text-sm font-medium hover:bg-gray-300 dark:hover:bg-gray-500 transition-all">
              解除綁定
            </button>
          </div>
        </div>
      </div>

      <!-- 銀行帳戶 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-bold text-gray-900 dark:text-white flex items-center gap-2">
            <svg class="w-5 h-5 text-emerald-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z" />
            </svg>
            銀行帳戶
          </h3>
          <button @click="showAddBankForm = !showAddBankForm"
            class="px-3 py-1.5 rounded-full text-sm font-medium transition-all hover:scale-105 active:scale-95"
            :class="showAddBankForm
              ? 'bg-gray-200 dark:bg-gray-600 text-gray-600 dark:text-gray-300'
              : 'bg-emerald-500 text-white hover:bg-emerald-600'">
            {{ showAddBankForm ? '取消' : '+ 新增帳戶' }}
          </button>
        </div>

        <!-- 新增帳戶表單 -->
        <div v-if="showAddBankForm" class="mb-4 p-4 rounded-xl bg-emerald-50 dark:bg-emerald-900/20 border border-emerald-200 dark:border-emerald-800">
          <div class="space-y-3">
            <!-- 銀行選擇 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">銀行</label>
              <select v-model="bankForm.bankCode" @change="onBankSelect"
                class="w-full px-4 py-2.5 rounded-xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500/50">
                <option value="">請選擇銀行</option>
                <option v-for="bank in banks" :key="bank.code" :value="bank.code">
                  {{ bank.code }} {{ bank.name }}
                </option>
              </select>
            </div>
            <!-- 帳號 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">帳號</label>
              <input type="text" v-model="bankForm.accountNumber" placeholder="請輸入銀行帳號"
                class="w-full px-4 py-2.5 rounded-xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500/50">
            </div>
            <!-- 戶名 -->
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">戶名</label>
              <input type="text" v-model="bankForm.accountName" placeholder="請輸入戶名"
                class="w-full px-4 py-2.5 rounded-xl bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-600 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-emerald-500/50">
            </div>
            <!-- 錯誤訊息 -->
            <p v-if="bankFormError" class="text-xs text-pink-600 dark:text-pink-400">{{ bankFormError }}</p>
            <!-- 提交按鈕 -->
            <button @click="addBankAccount"
              class="w-full py-2.5 rounded-xl bg-emerald-500 text-white font-medium hover:bg-emerald-600 transition-all hover:scale-[1.02] active:scale-95">
              綁定帳戶（可獲得 15 積分）
            </button>
          </div>
        </div>

        <!-- 已綁定帳戶列表 -->
        <div class="space-y-3">
          <div v-if="bankAccounts.length === 0" class="text-center py-6 text-gray-500 dark:text-gray-400">
            <svg class="w-12 h-12 mx-auto mb-2 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z" />
            </svg>
            <p class="text-sm">尚未綁定任何銀行帳戶</p>
            <p class="text-xs mt-1">每綁定一個帳戶可獲得 15 升級積分 + 15 獎勵積分</p>
          </div>

          <div v-for="account in bankAccounts" :key="account.id"
            class="flex items-center justify-between py-3 px-4 rounded-xl bg-gray-50 dark:bg-gray-800/50">
            <div class="flex items-center gap-3">
              <div class="w-10 h-10 rounded-full bg-emerald-100 dark:bg-emerald-900/30 flex items-center justify-center">
                <span class="text-xs font-bold text-emerald-600 dark:text-emerald-400">{{ account.bankCode }}</span>
              </div>
              <div>
                <p class="text-sm font-medium text-gray-900 dark:text-white">{{ account.bankName }}</p>
                <p class="text-xs text-gray-500 dark:text-gray-400">
                  {{ maskAccountNumber(account.accountNumber) }} · {{ account.accountName }}
                </p>
              </div>
            </div>
            <button @click="deleteBankAccount(account.id)"
              class="p-2 rounded-full text-gray-400 hover:text-pink-500 hover:bg-pink-50 dark:hover:bg-pink-900/20 transition-all">
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- 通知設定 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 dark:shadow-2xl border dark:border-gray-600/30">
        <h3 class="text-lg font-bold text-gray-900 dark:text-white mb-4 flex items-center gap-2">
          <svg class="w-5 h-5 text-amber-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
          </svg>
          通知設定
        </h3>

        <div class="space-y-4">
          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">升級提醒通知</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">距離下一等級的積分進度</p>
            </div>
            <button @click.stop="toggleNotification('levelUp', '升級提醒通知')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notificationSettings.levelUp
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notificationSettings.levelUp ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">活動紀錄通知</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">顯示最新的活動紀錄</p>
            </div>
            <button @click.stop="toggleNotification('points', '活動紀錄通知')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notificationSettings.points
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notificationSettings.points ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <div class="flex items-center justify-between py-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white">出貨進度通知</p>
              <p class="text-xs text-gray-500 dark:text-gray-400">兌換禮品的出貨狀態</p>
            </div>
            <button @click.stop="toggleNotification('shipping', '出貨進度通知')"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border hover:scale-[1.02] active:scale-95 transition-all duration-300',
                notificationSettings.shipping
                  ? 'bg-gradient-to-r from-cyan-400 to-blue-500 border-cyan-300/50'
                  : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                notificationSettings.shipping ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>

          <!-- 訂閱電子報（任務：+10積分） -->
          <div class="flex items-center justify-between py-2 border-t border-gray-100 dark:border-gray-600/30 pt-4 mt-2">
            <div>
              <p class="text-sm font-medium text-gray-900 dark:text-white flex items-center gap-2">
                訂閱電子報
                <span v-if="!form.newsletterSubscribed" class="text-xs text-emerald-500 font-normal">+10 積分</span>
                <span v-else class="text-xs text-gray-400 font-normal">已完成</span>
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-400">每週接收理財週報與平台最新消息</p>
            </div>
            <button @click="form.newsletterSubscribed = !form.newsletterSubscribed"
              :disabled="store.currentUser?.newsletterSubscribed"
              :class="[
                'relative w-14 h-8 rounded-full shadow-inner border transition-all duration-300',
                store.currentUser?.newsletterSubscribed
                  ? 'bg-gray-300 dark:bg-gray-600 border-gray-300/50 cursor-not-allowed'
                  : form.newsletterSubscribed
                    ? 'bg-gradient-to-r from-emerald-400 to-teal-500 border-emerald-300/50 hover:scale-[1.02] active:scale-95'
                    : 'bg-gray-200 dark:bg-gray-700 border-gray-300/30 dark:border-gray-600/30 hover:scale-[1.02] active:scale-95'
              ]">
              <div :class="[
                'absolute top-1 w-6 h-6 rounded-full bg-white shadow-md transition-all duration-300',
                form.newsletterSubscribed ? 'left-[calc(100%-1.75rem)]' : 'left-1'
              ]"></div>
            </button>
          </div>
        </div>
      </div>

      <!-- 儲存按鈕 -->
      <div class="flex justify-end gap-3">
        <router-link to="/profile"
          class="px-6 py-2.5 rounded-full bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-200 dark:hover:bg-gray-600 transition-all font-medium text-sm">
          取消
        </router-link>
        <button @click="saveSettings"
          class="px-6 py-2.5 rounded-full bg-gradient-to-br from-cyan-400 to-blue-500 text-white hover:opacity-90 hover:scale-105 active:scale-95 transition-all font-medium text-sm shadow-lg">
          儲存變更
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useStore, useUIStore } from '@/store'
import { storeToRefs } from 'pinia'
import { userAPI, bankAccountAPI } from '@/api'
import axios from 'axios'

const router = useRouter()
const store = useStore()
const uiStore = useUIStore()
const { notificationSettings } = storeToRefs(uiStore)

// 登出
const handleLogout = () => {
  store.logout()
  store.showToast('已成功登出', 'success')
  router.push('/')
}

// LINE 綁定狀態
const isLineBound = computed(() => !!store.currentUser?.lineId)

// ====== 銀行帳戶相關 ======
const bankAccounts = ref([])
const showAddBankForm = ref(false)
const bankForm = reactive({
  bankCode: '',
  bankName: '',
  accountNumber: '',
  accountName: ''
})
const bankFormError = ref('')

// 台灣常見銀行列表
const banks = [
  { code: '004', name: '臺灣銀行' },
  { code: '005', name: '土地銀行' },
  { code: '006', name: '合作金庫' },
  { code: '007', name: '第一銀行' },
  { code: '008', name: '華南銀行' },
  { code: '009', name: '彰化銀行' },
  { code: '011', name: '上海商銀' },
  { code: '012', name: '台北富邦' },
  { code: '013', name: '國泰世華' },
  { code: '017', name: '兆豐銀行' },
  { code: '021', name: '花旗銀行' },
  { code: '048', name: '王道銀行' },
  { code: '050', name: '臺灣企銀' },
  { code: '052', name: '渣打銀行' },
  { code: '053', name: '台中商銀' },
  { code: '054', name: '京城銀行' },
  { code: '081', name: '匯豐銀行' },
  { code: '102', name: '華泰銀行' },
  { code: '103', name: '臺灣新光' },
  { code: '108', name: '陽信銀行' },
  { code: '118', name: '板信銀行' },
  { code: '147', name: '三信銀行' },
  { code: '700', name: '中華郵政' },
  { code: '803', name: '聯邦銀行' },
  { code: '805', name: '遠東銀行' },
  { code: '806', name: '元大銀行' },
  { code: '807', name: '永豐銀行' },
  { code: '808', name: '玉山銀行' },
  { code: '809', name: '凱基銀行' },
  { code: '810', name: '星展銀行' },
  { code: '812', name: '台新銀行' },
  { code: '816', name: '安泰銀行' },
  { code: '822', name: '中國信託' }
]

// 載入銀行帳戶
const loadBankAccounts = async () => {
  if (!store.currentUser?.id) return
  try {
    const response = await bankAccountAPI.getByUserId(store.currentUser.id)
    bankAccounts.value = response.data
  } catch (error) {
    console.error('Load bank accounts error:', error)
  }
}

// 選擇銀行時自動填入銀行名稱
const onBankSelect = () => {
  const selected = banks.find(b => b.code === bankForm.bankCode)
  if (selected) {
    bankForm.bankName = selected.name
  }
}

// 新增銀行帳戶
const addBankAccount = async () => {
  bankFormError.value = ''

  // 驗證
  if (!bankForm.bankCode || !bankForm.accountNumber || !bankForm.accountName) {
    bankFormError.value = '請填寫所有必填欄位'
    return
  }

  if (!/^\d{10,16}$/.test(bankForm.accountNumber)) {
    bankFormError.value = '請輸入有效的帳號（10-16位數字）'
    return
  }

  try {
    const response = await bankAccountAPI.add({
      bankCode: bankForm.bankCode,
      bankName: bankForm.bankName,
      accountNumber: bankForm.accountNumber,
      accountName: bankForm.accountName
    })

    // 重新載入帳戶列表
    await loadBankAccounts()

    // 重置表單
    bankForm.bankCode = ''
    bankForm.bankName = ''
    bankForm.accountNumber = ''
    bankForm.accountName = ''
    showAddBankForm.value = false

    // 顯示成功訊息
    store.showToast(response.data.message || '銀行帳戶綁定成功！', 'success')
  } catch (error) {
    console.error('Add bank account error:', error)
    bankFormError.value = error.response?.data?.message || '新增失敗，請稍後再試'
  }
}

// 刪除銀行帳戶
const deleteBankAccount = async (accountId) => {
  if (!confirm('確定要刪除此銀行帳戶嗎？')) return

  try {
    await bankAccountAPI.delete(accountId)
    await loadBankAccounts()
    store.showToast('銀行帳戶已刪除', 'success')
  } catch (error) {
    console.error('Delete bank account error:', error)
    store.showToast(error.response?.data?.message || '刪除失敗', 'error')
  }
}

// 帳號遮罩顯示
const maskAccountNumber = (accountNumber) => {
  if (!accountNumber || accountNumber.length < 4) return '****'
  const len = accountNumber.length
  return '*'.repeat(len - 4) + accountNumber.slice(-4)
}

// 錯誤訊息
const errors = reactive({
  name: '',
  birthday: '',
  phone: '',
  address: ''
})

// 默認值（不包含生日，生日必須由用戶自行填寫）
const defaults = {
  phone: '0912345678',
  city: '台北市',
  district: '中山區',
  address: '民生東路三段67號'
}

// 表單資料 - 從 store.currentUser 載入
const form = reactive({
  name: '',
  email: '',
  birthday: '',
  phone: '',
  city: '',
  district: '',
  address: '',
  newsletterSubscribed: false
})

// 載入現有資料
onMounted(async () => {
  if (store.currentUser) {
    form.name = store.currentUser.name || ''
    form.email = store.currentUser.email || ''
    form.birthday = store.currentUser.birthday || ''  // 生日不使用預設值
    form.phone = store.currentUser.phone || defaults.phone
    // 地址解析或使用默認值
    if (store.currentUser.address) {
      // 嘗試解析地址（縣市+區域+詳細地址）
      const addr = store.currentUser.address
      const cityMatch = cities.find(c => addr.startsWith(c))
      if (cityMatch) {
        form.city = cityMatch
        const rest = addr.substring(cityMatch.length)
        // 嘗試解析區域（找到第一個「區」、「市」、「鄉」、「鎮」）
        const districtMatch = rest.match(/^(.+?[區市鄉鎮])/)
        if (districtMatch) {
          form.district = districtMatch[1]
          form.address = rest.substring(districtMatch[1].length)
        } else {
          form.address = rest
        }
      } else {
        form.address = addr
      }
    } else {
      // 使用默認值
      form.city = defaults.city
      form.district = defaults.district
      form.address = defaults.address
    }

    // 載入電子報訂閱狀態
    form.newsletterSubscribed = store.currentUser.newsletterSubscribed || false

    // 載入銀行帳戶
    await loadBankAccounts()
  }
})

// 台灣縣市
const cities = [
  '台北市', '新北市', '桃園市', '台中市', '台南市', '高雄市',
  '基隆市', '新竹市', '新竹縣', '苗栗縣', '彰化縣', '南投縣',
  '雲林縣', '嘉義市', '嘉義縣', '屏東縣', '宜蘭縣', '花蓮縣',
  '台東縣', '澎湖縣', '金門縣', '連江縣'
]

// 驗證表單
const validateForm = () => {
  let isValid = true

  // 清除錯誤
  errors.name = ''
  errors.birthday = ''
  errors.phone = ''
  errors.address = ''

  // 驗證姓名
  if (!form.name || !form.name.trim()) {
    errors.name = '請輸入姓名'
    isValid = false
  }

  // 驗證生日
  if (!form.birthday) {
    errors.birthday = '請選擇生日'
    isValid = false
  }

  // 驗證手機
  if (!form.phone) {
    errors.phone = '請輸入手機號碼'
    isValid = false
  } else if (!/^09\d{8}$/.test(form.phone)) {
    errors.phone = '請輸入有效的手機號碼（09開頭共10碼）'
    isValid = false
  }

  // 驗證地址（縣市 + 區域 + 詳細地址）
  if (!form.city || !form.district || !form.address) {
    errors.address = '請填寫完整收件地址（縣市、區域及詳細地址）'
    isValid = false
  }

  return isValid
}

// 通知面板自動收起的計時器
let notificationTimer = null

// 切換通知設定 - 使用 store 方法
const toggleNotification = async (key, label) => {
  // 先清除之前的計時器（避免舊計時器干擾）
  if (notificationTimer) {
    clearTimeout(notificationTimer)
    notificationTimer = null
  }

  const newValue = store.toggleNotificationSetting(key)
  const status = newValue ? '開啟' : '關閉'
  const type = newValue ? 'success' : 'error'
  store.showToast(`${label}通知已${status}`, type)

  // 自動展開鈴鐺通知面板，讓用戶看到變化
  uiStore.showNotificationPanel = true

  // 3秒後自動收起
  notificationTimer = setTimeout(() => {
    uiStore.showNotificationPanel = false
    notificationTimer = null
  }, 3000)
}

// 儲存設定
const saveSettings = async () => {
  if (!validateForm()) {
    store.showToast('請填寫所有必填欄位', 'error')
    return
  }

  // 組合完整地址
  const fullAddress = `${form.city}${form.district}${form.address}`

  try {
    // 準備更新資料
    const userData = {
      ...store.currentUser,
      name: form.name,
      phone: form.phone,
      address: fullAddress,
      birthday: form.birthday || null,
      newsletterSubscribed: form.newsletterSubscribed
    }

    // 呼叫後端 API 更新用戶資料
    const response = await userAPI.update(store.currentUser.id, userData)
    const data = response.data

    // 更新 store.currentUser
    if (data.user) {
      Object.assign(store.currentUser, data.user)
    }

    // 檢查是否有完成任務
    if (data.taskCompleted) {
      // 先顯示設定儲存成功
      store.showToast('設定已儲存', 'success')
      // 延遲顯示任務完成通知
      setTimeout(() => {
        store.showToast(data.taskCompleted.message, 'success')
      }, 1500)
    } else {
      store.showToast('設定已儲存', 'success')
    }
  } catch (error) {
    console.error('Update user error:', error)
    store.showToast('儲存失敗，請稍後再試', 'error')
  }
}

// LINE 綁定
const handleBindLine = async () => {
  try {
    // 導向 LINE 授權頁面，帶上 bind 參數表示是綁定操作
    const response = await axios.get('http://localhost:8080/api/auth/line/auth-url')
    if (response.data.url) {
      // 在 URL 加上 state 參數標記這是綁定操作
      const bindUrl = response.data.url.replace('state=shineup', 'state=bind')
      window.location.href = bindUrl
    }
  } catch (error) {
    console.error('LINE bind error:', error)
    store.showToast('LINE 綁定失敗，請稍後再試', 'error')
  }
}

// LINE 解除綁定
const handleUnbindLine = async () => {
  if (!confirm('確定要解除 LINE 綁定嗎？')) return

  try {
    const token = localStorage.getItem('token')
    const response = await axios.post('http://localhost:8080/api/auth/line/unbind', {}, {
      headers: { Authorization: `Bearer ${token}` }
    })

    if (response.data.success) {
      store.currentUser.lineId = null
      store.showToast('已解除 LINE 綁定', 'success')
    } else {
      store.showToast(response.data.message || '解除綁定失敗', 'error')
    }
  } catch (error) {
    console.error('LINE unbind error:', error)
    store.showToast('解除綁定失敗，請稍後再試', 'error')
  }
}
</script>
