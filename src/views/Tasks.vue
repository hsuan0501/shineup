<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <h1 class="text-4xl font-bold mb-12 text-light-text dark:text-dark-text">任務中心</h1>

    <!-- Filter Tabs -->
    <div class="flex gap-2 mb-8 overflow-x-auto pb-2">
      <button v-for="cat in categories" :key="cat" 
        @click="selectedCategory = cat"
        :class="[
          'px-6 py-2 rounded-full whitespace-nowrap font-semibold transition-all duration-300',
          selectedCategory === cat
            ? 'bg-primary-purple text-white shadow-glow'
            : 'bg-light-bg dark:bg-dark-bg text-light-text dark:text-dark-text hover:bg-primary-purple/20'
        ]">
        {{ cat }}
      </button>
    </div>

    <!-- Tasks Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="task in filteredTasks" :key="task.id"
        class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:shadow-glow transition-all duration-300 group cursor-pointer">
        
        <div class="flex items-start justify-between mb-4">
          <span class="text-4xl">{{ task.icon }}</span>
          <span v-if="task.completed" class="px-3 py-1 rounded-full bg-green-500/20 text-green-600 text-xs font-semibold">
            ✓ 已完成
          </span>
          <span v-else class="px-3 py-1 rounded-full bg-yellow-500/20 text-yellow-600 text-xs font-semibold">
            待完成
          </span>
        </div>

        <h3 class="text-lg font-bold text-light-text dark:text-dark-text mb-2">{{ task.title }}</h3>
        <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary mb-4">{{ task.description }}</p>

        <div class="flex items-center justify-between">
          <span class="text-lg font-bold text-primary-purple">+{{ task.points }} 分</span>
          <button :disabled="task.completed"
            :class="[
              'px-4 py-2 rounded-full font-semibold transition-all duration-300',
              task.completed
                ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                : 'bg-primary-blue text-white hover:shadow-glow'
            ]">
            {{ task.completed ? '已完成' : '完成' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { mockTasks } from '../mock'

const selectedCategory = ref('全部')
const categories = ['全部', 'daily', 'banking', 'esg', 'social']

const filteredTasks = computed(() => {
  if (selectedCategory.value === '全部') return mockTasks
  return mockTasks.filter(t => t.category === selectedCategory.value)
})
</script>
