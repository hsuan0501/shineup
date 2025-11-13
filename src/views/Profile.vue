<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
    <h1 class="text-4xl font-bold mb-12 text-light-text dark:text-dark-text">個人資料</h1>

    <!-- User Card -->
    <div class="mb-12 p-8 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border">
      <div class="flex flex-col sm:flex-row items-center gap-8">
        <img :src="user.avatar" :alt="user.name" class="w-24 h-24 rounded-full border-4 border-primary-purple">
        
        <div class="flex-1 text-center sm:text-left">
          <h2 class="text-3xl font-bold text-light-text dark:text-dark-text mb-2">{{ user.name }}</h2>
          <p class="text-light-text-secondary dark:text-dark-text-secondary mb-4">{{ user.email }}</p>
          
          <!-- Level Badge -->
          <div class="flex items-center gap-2 justify-center sm:justify-start mb-4">
            <span class="px-4 py-2 rounded-full bg-primary-purple text-white font-semibold text-sm">
              {{ user.level }}
            </span>
            <span class="text-xl font-bold text-primary-purple">{{ user.points }} 分</span>
          </div>
        </div>

        <button class="px-6 py-2 rounded-full border-2 border-primary-purple text-primary-purple hover:bg-primary-purple/10 font-semibold transition-all duration-300">
          編輯資料
        </button>
      </div>
    </div>

    <!-- Quick Stats -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
      <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center">
        <p class="text-3xl font-bold text-primary-purple mb-2">{{ user.points }}</p>
        <p class="text-light-text-secondary dark:text-dark-text-secondary">目前積分</p>
      </div>
      <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center">
        <p class="text-3xl font-bold text-primary-blue mb-2">12</p>
        <p class="text-light-text-secondary dark:text-dark-text-secondary">已兌換禮品</p>
      </div>
      <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center">
        <p class="text-3xl font-bold text-primary-purple mb-2">{{ 1500 - user.points }}</p>
        <p class="text-light-text-secondary dark:text-dark-text-secondary">距離下一等級</p>
      </div>
    </div>

    <!-- Action Buttons -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <button class="px-6 py-4 rounded-2xl bg-primary-purple text-white font-semibold hover:shadow-glow transition-all duration-300">
        📋 查看兌換紀錄
      </button>
      <button class="px-6 py-4 rounded-2xl bg-primary-blue text-white font-semibold hover:shadow-glow transition-all duration-300">
        📬 查看通知
      </button>
    </div>
  </div>
</template>

<script setup>
import { mockUsers } from '../mock'

const user = mockUsers[1]
</script>