<template>
  <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
    <!-- 成功圖示 -->
    <div class="text-center">
      <div class="w-24 h-24 mx-auto mb-6 bg-gradient-to-br from-emerald-400 to-green-500 rounded-full flex items-center justify-center shadow-lg">
        <svg class="w-12 h-12 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5" d="M5 13l4 4L19 7" />
        </svg>
      </div>

      <!-- 標題 -->
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">兌換完成！</h1>

      <!-- 說明文字 -->
      <p class="text-lg text-gray-600 dark:text-gray-400 mb-8">
        禮品將於 <span class="font-bold text-emerald-600 dark:text-emerald-400">3-5 個工作日</span> 內寄送。
      </p>

      <!-- 提示卡片 -->
      <div class="bg-white dark:bg-gray-700/70 dark:backdrop-blur-xl rounded-2xl p-6 mb-8 dark:shadow-2xl border dark:border-gray-600/30 text-left">
        <h3 class="font-bold text-gray-900 dark:text-white mb-3 flex items-center gap-2">
          <svg class="w-5 h-5 text-sky-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          溫馨提醒
        </h3>
        <ul class="space-y-2 text-gray-600 dark:text-gray-400">
          <li class="flex items-start gap-2">
            <span class="text-emerald-500 mt-0.5">•</span>
            <span>您可以在「會員中心」查看兌換紀錄</span>
          </li>
          <li class="flex items-start gap-2">
            <span class="text-emerald-500 mt-0.5">•</span>
            <span>禮品出貨時會發送通知給您</span>
          </li>
          <li class="flex items-start gap-2">
            <span class="text-emerald-500 mt-0.5">•</span>
            <span>如有問題請聯繫客服</span>
          </li>
        </ul>
      </div>

      <!-- 按鈕 -->
      <div class="flex flex-col sm:flex-row gap-3 justify-center">
        <router-link to="/"
          class="px-8 py-3 rounded-full bg-gradient-to-br from-cyan-400 to-blue-500 text-white font-bold hover:opacity-90 hover:scale-105 active:scale-95 transition-all shadow-lg">
          返回首頁
        </router-link>
        <router-link to="/profile"
          class="px-8 py-3 rounded-full bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 font-medium hover:bg-gray-200 dark:hover:bg-gray-600 transition-all">
          查看會員中心
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
// 簡單的完成頁面，不需要太多邏輯
</script>
