<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Header -->
    <div class="flex items-center gap-4 mb-8">
      <router-link to="/profile" class="p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors">
        <svg class="w-6 h-6 text-gray-600 dark:text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
      </router-link>
      <h1 class="text-3xl font-bold text-light-text dark:text-dark-text">等級總覽</h1>
    </div>

    <!-- Level Cards -->
    <div class="space-y-6 relative">
      <!-- Connecting Line -->
      <div class="absolute left-8 top-8 bottom-8 w-0.5 bg-gray-200 dark:bg-gray-700 hidden sm:block"></div>

      <div v-for="(level, index) in levelConfig" :key="level.level"
        class="relative flex flex-col sm:flex-row gap-6 p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border transition-all duration-300 hover:shadow-lg"
        :class="{ 'ring-2 ring-primary-purple ring-offset-2 dark:ring-offset-gray-900': currentLevel === level.level }">
        
        <!-- Level Icon/Badge -->
        <div class="flex-shrink-0 relative z-10">
          <div class="w-16 h-16 rounded-2xl flex items-center justify-center shadow-lg transition-transform duration-300 hover:scale-110"
            :style="{ backgroundColor: level.bgColor, color: level.color }">
            <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24" v-html="getLevelIconPath(level.level)">
            </svg>
          </div>
        </div>

        <!-- Content -->
        <div class="flex-grow">
          <div class="flex flex-wrap items-center justify-between gap-2 mb-2">
            <h3 class="text-xl font-bold text-light-text dark:text-dark-text">{{ level.name }} <span class="text-sm font-normal text-gray-500">({{ level.level }})</span></h3>
            <span class="px-3 py-1 rounded-full text-xs font-bold bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-300">
              {{ level.minPoints }}+ 積分
            </span>
          </div>
          
          <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary mb-4">
            {{ getLevelDescription(level.level) }}
          </p>

          <!-- Benefits -->
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div v-for="benefit in getLevelBenefits(level.level)" :key="benefit" class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300">
              <svg class="w-4 h-4 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
              </svg>
              {{ benefit }}
            </div>
          </div>
        </div>

        <!-- Current Level Indicator -->
        <div v-if="currentLevel === level.level" class="absolute -top-2 -right-2">
          <span class="flex h-4 w-4">
            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-primary-purple opacity-75"></span>
            <span class="relative inline-flex rounded-full h-4 w-4 bg-primary-purple"></span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { levelConfig } from '../mock'

const currentLevel = ref('CREATOR') // 假設當前等級

const getLevelIconPath = (level) => {
  const map = {
    'EXPLORER': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />', // Earth/Globe
    'CREATOR': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />', // Pen tool
    'VISIONARY': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />', // Lightbulb
    'LUMINARY': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />' // Star
  }
  return map[level] || '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />'
}

const getLevelDescription = (level) => {
  const map = {
    'EXPLORER': '開啟永續旅程，探索綠色生活的無限可能。',
    'CREATOR': '展現獨特品味，創造屬於你的質感生活。',
    'VISIONARY': '引領美學潮流，洞見未來生活的美好願景。',
    'LUMINARY': '成就非凡榮耀，享受極致尊榮的頂級禮遇。'
  }
  return map[level] || ''
}

const getLevelBenefits = (level) => {
  const map = {
    'EXPLORER': [
      '解鎖基礎任務',
      '兌換永續探索系列禮品',
      '生日專屬祝福'
    ],
    'CREATOR': [
      '包含所有 Explorer 權益',
      '兌換質感創造系列禮品',
      '任務積分 1.1 倍加成',
      '每月專屬優惠券'
    ],
    'VISIONARY': [
      '包含所有 Creator 權益',
      '兌換美學先鋒系列禮品',
      '任務積分 1.2 倍加成',
      '新品優先體驗權',
      '專屬理財諮詢服務'
    ],
    'LUMINARY': [
      '包含所有 Visionary 權益',
      '兌換品味閃耀系列禮品',
      '任務積分 1.5 倍加成',
      '年度尊榮禮盒',
      '線下活動 VIP 席位',
      '一對一專屬客服'
    ]
  }
  return map[level] || []
}
</script>
