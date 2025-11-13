<template>
  <div class="w-full">
    <!-- Hero Section -->
    <section class="relative min-h-screen flex flex-col items-center justify-start px-4 sm:px-6 lg:px-8 w-full pt-24" id="top">
      <!-- Animated Background Circles -->
      <div class="absolute inset-0 overflow-hidden pointer-events-none z-0">
        <div class="absolute top-20 left-10 w-96 h-96 bg-purple-400/20 rounded-full blur-[120px] animate-float"></div>
        <div class="absolute bottom-20 right-10 w-[500px] h-[500px] bg-blue-400/20 rounded-full blur-[120px] animate-float" style="animation-delay: 2s;"></div>
      </div>

      <!-- Hero Content -->
      <div class="relative z-10 text-center max-w-4xl mx-auto animate-fade-in w-full">
        <h1 style="background: linear-gradient(135deg, #8b5cf6, #3b82f6, #8b5cf6); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text;" class="text-4xl sm:text-5xl lg:text-6xl font-light mb-6">
          Shine Level Up
        </h1>
        <p class="text-base sm:text-lg text-light-text-secondary dark:text-dark-text-secondary mb-12">
          Keep shining, keep earning.
        </p>
        
        <!-- CTA Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center mb-16">
          <RouterLink to="/tasks" class="px-8 py-4 rounded-full bg-gradient-to-r from-primary-purple to-primary-blue text-white font-semibold hover:shadow-glow-lg transition-all duration-300 transform hover:scale-105">
            開始賺取積分
          </RouterLink>
          <RouterLink to="/rewards" class="px-8 py-4 rounded-full border-2 border-primary-purple text-primary-purple dark:text-primary-light hover:bg-primary-purple/10 font-semibold transition-all duration-300">
            瀏覽禮品
          </RouterLink>
        </div>
      </div>

      <!-- Scrolling Rewards Section -->
      <div class="relative z-10 w-full overflow-hidden mt-8">
        <!-- First Row - Left to Right -->
        <div class="flex gap-4 animate-scroll-left mb-4">
          <div v-for="item in [...rewards, ...rewards]" :key="item.id + '-1'" class="flex-shrink-0 w-72 p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:shadow-glow transition-all duration-300">
            <div class="aspect-square rounded-lg bg-gradient-to-br from-primary-purple/20 to-primary-blue/20 mb-3 flex items-center justify-center text-5xl">
              {{ item.emoji }}
            </div>
            <div class="flex gap-2 mb-2 flex-wrap">
              <span class="px-2 py-1 rounded-full bg-primary-purple/10 text-primary-purple text-xs font-semibold">{{ item.level }}</span>
              <span class="px-2 py-1 rounded-full bg-primary-blue/10 text-primary-blue text-xs">{{ item.category }}</span>
            </div>
            <h4 class="font-semibold text-light-text dark:text-dark-text mb-1">{{ item.name }}</h4>
            <div class="flex items-center justify-between">
              <p class="text-sm text-primary-purple font-bold">{{ item.points }} 積分</p>
              <div class="flex gap-1">
                <span v-for="tag in item.tags.slice(0, 2)" :key="tag" class="text-xs text-light-text-secondary dark:text-dark-text-secondary">{{ tag }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Second Row - Right to Left -->
        <div class="flex gap-4 animate-scroll-right">
          <div v-for="item in [...rewards2, ...rewards2]" :key="item.id + '-2'" class="flex-shrink-0 w-72 p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:shadow-glow transition-all duration-300">
            <div class="aspect-square rounded-lg bg-gradient-to-br from-primary-blue/20 to-primary-purple/20 mb-3 flex items-center justify-center text-5xl">
              {{ item.emoji }}
            </div>
            <div class="flex gap-2 mb-2 flex-wrap">
              <span class="px-2 py-1 rounded-full bg-primary-purple/10 text-primary-purple text-xs font-semibold">{{ item.level }}</span>
              <span class="px-2 py-1 rounded-full bg-primary-blue/10 text-primary-blue text-xs">{{ item.category }}</span>
            </div>
            <h4 class="font-semibold text-light-text dark:text-dark-text mb-1">{{ item.name }}</h4>
            <div class="flex items-center justify-between">
              <p class="text-sm text-primary-blue font-bold">{{ item.points }} 積分</p>
              <div class="flex gap-1">
                <span v-for="tag in item.tags.slice(0, 2)" :key="tag" class="text-xs text-light-text-secondary dark:text-dark-text-secondary">{{ tag }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Stats Section -->
    <section class="w-full py-12 px-4 sm:px-6 lg:px-8">
      <div class="max-w-7xl mx-auto">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
          <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center hover:shadow-glow transition-all duration-300">
            <p class="text-3xl font-bold text-primary-purple mb-2">4</p>
            <p class="text-light-text-secondary dark:text-dark-text-secondary">會員等級</p>
          </div>
          <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center hover:shadow-glow transition-all duration-300">
            <p class="text-3xl font-bold text-primary-blue mb-2">50+</p>
            <p class="text-light-text-secondary dark:text-dark-text-secondary">禮品兌換</p>
          </div>
          <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center hover:shadow-glow transition-all duration-300">
            <p class="text-3xl font-bold text-primary-purple mb-2">∞</p>
            <p class="text-light-text-secondary dark:text-dark-text-secondary">賺取機會</p>
          </div>
          <div class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border text-center hover:shadow-glow transition-all duration-300">
            <p class="text-3xl font-bold text-primary-blue mb-2">24/7</p>
            <p class="text-light-text-secondary dark:text-dark-text-secondary">AI 客服</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Levels Section -->
    <section class="w-full py-12 px-4 sm:px-6 lg:px-8">
      <div class="max-w-7xl mx-auto">
        <h2 class="text-4xl font-bold text-center mb-16 text-light-text dark:text-dark-text">
          會員等級系統
        </h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <div class="p-8 rounded-2xl bg-light-card dark:bg-dark-card border-2 border-green-300 dark:border-green-700 text-center hover:shadow-glow transition-all duration-300">
            <p class="text-4xl mb-2">🌿</p>
            <h3 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">探索者</h3>
            <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm mb-4">0 - 299 分</p>
            <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary">開啟你的閃耀旅程</p>
          </div>

          <div class="p-8 rounded-2xl bg-light-card dark:bg-dark-card border-2 border-blue-300 dark:border-blue-700 text-center hover:shadow-glow transition-all duration-300">
            <p class="text-4xl mb-2">💠</p>
            <h3 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">創造者</h3>
            <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm mb-4">300 - 799 分</p>
            <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary">累積成就，創造價值</p>
          </div>

          <div class="p-8 rounded-2xl bg-light-card dark:bg-dark-card border-2 border-yellow-300 dark:border-yellow-700 text-center hover:shadow-glow transition-all duration-300">
            <p class="text-4xl mb-2">💎</p>
            <h3 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">先行者</h3>
            <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm mb-4">800 - 1499 分</p>
            <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary">前瞻視野，領航方向</p>
          </div>

          <div class="p-8 rounded-2xl bg-light-card dark:bg-dark-card border-2 border-purple-300 dark:border-purple-700 text-center hover:shadow-glow transition-all duration-300">
            <p class="text-4xl mb-2">👑</p>
            <h3 class="text-xl font-bold text-light-text dark:text-dark-text mb-2">閃耀者</h3>
            <p class="text-light-text-secondary dark:text-dark-text-secondary text-sm mb-4">1500+ 分</p>
            <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary">榮耀發光，驕傲登場</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import { ref } from 'vue'

const rewards = ref([
  { id: 1, name: 'LAMY 鋼筆禮盒', emoji: '✒️', points: 3500, category: '🧳 商務奢雅', level: '先行者', tags: ['精緻禮品'] },
  { id: 2, name: 'Jo Malone 香氛蠟燭', emoji: '🕯️', points: 2800, category: '🕯 精品生活', level: '創造者', tags: ['品牌質感'] },
  { id: 3, name: '環保再生後背包', emoji: '🎒', points: 1200, category: '🌿 永續綠生活', level: '探索者', tags: ['ESG', '永續'] },
  { id: 4, name: '公益咖啡禮盒', emoji: '☕', points: 500, category: '☕ 社企手作', level: '全等級', tags: ['CSR', '社企合作'] },
  { id: 5, name: 'Moleskine 筆記本', emoji: '📔', points: 1500, category: '🧳 商務奢雅', level: '創造者', tags: ['精緻禮品'] },
  { id: 6, name: 'KINTO 馬克杯組', emoji: '☕', points: 800, category: '🕯 精品生活', level: '探索者', tags: ['品牌質感'] },
])

const rewards2 = ref([
  { id: 7, name: 'Nespresso 膠囊組', emoji: '☕', points: 1800, category: '🕯 精品生活', level: '創造者', tags: ['品牌質感'] },
  { id: 8, name: '竹製餐具禮盒', emoji: '🥢', points: 600, category: '🌿 永續綠生活', level: '探索者', tags: ['環保', '永續'] },
  { id: 9, name: '社企手作布包', emoji: '👜', points: 450, category: '☕ 社企手作', level: '全等級', tags: ['CSR', '公益'] },
  { id: 10, name: '太陽能行動電源', emoji: '🔋', points: 2200, category: '🌿 永續綠生活', level: '創造者', tags: ['ESG', '綠能'] },
  { id: 11, name: '春節限定禮盒', emoji: '🎁', points: 1500, category: '🎄 節慶限定', level: '全等級', tags: ['限時活動'] },
  { id: 12, name: '精品公事包', emoji: '💼', points: 4500, category: '🧳 商務奢雅', level: '閃耀者', tags: ['精緻禮品'] },
])
</script>

<style scoped>
@keyframes scroll-left {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

@keyframes scroll-right {
  0% {
    transform: translateX(-50%);
  }
  100% {
    transform: translateX(0);
  }
}

.animate-scroll-left {
  animation: scroll-left 30s linear infinite;
}

.animate-scroll-right {
  animation: scroll-right 30s linear infinite;
}

.animate-scroll-left:hover,
.animate-scroll-right:hover {
  animation-play-state: paused;
}
</style>