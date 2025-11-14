<template>
  <div class="w-full" id="home-top">
    <!-- Scroll to Top Button -->
    <button
      @click="scrollToTop"
      class="fixed bottom-8 left-8 sm:left-12 lg:left-16 z-50 h-16 w-16 flex items-center justify-center rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 backdrop-blur-sm"
      aria-label="回到頂部">
      <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
      </svg>
    </button>

    <!-- Customer Service Button -->
    <button
      class="fixed bottom-8 right-8 sm:right-12 lg:right-16 z-50 h-16 w-16 flex items-center justify-center rounded-full bg-light-bg dark:bg-gray-800/80 border border-light-border dark:border-gray-700/70 hover:bg-light-border dark:hover:bg-gray-700/90 hover:scale-110 transition-all duration-300 backdrop-blur-sm"
      aria-label="客服">
      <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
      </svg>
    </button>

    <!-- Hero Section -->
    <section class="relative min-h-screen flex flex-col items-center justify-start px-4 sm:px-6 lg:px-8 w-full pt-24">
      <!-- Animated Background with Blobs -->
      <div class="blobs absolute inset-0 overflow-hidden pointer-events-none z-0">
        <div ref="blob1" class="blob blob-1"></div>
        <div ref="blob2" class="blob blob-2"></div>
        <div ref="blob3" class="blob blob-3"></div>
        <div ref="blob4" class="blob blob-4"></div>
        <div ref="blob5" class="blob blob-5"></div>
        <div ref="blob6" class="blob blob-6"></div>
        <div ref="blob7" class="blob blob-7"></div>
      </div>

      <!-- Sparkle Stars -->
      <div class="sparkle-container absolute inset-0 pointer-events-none z-0">
        <div v-for="n in 20" :key="n" class="sparkle" :style="{
          top: `${Math.random() * 100}%`,
          left: `${Math.random() * 100}%`,
          animationDelay: `${Math.random() * 3}s`,
          animationDuration: `${2 + Math.random() * 2}s`
        }"></div>
      </div>

      <!-- Hero Content -->
      <div class="relative z-10 text-center max-w-4xl mx-auto animate-fade-in w-full">
        <div class="text-white drop-shadow-[0_12px_48px_rgba(34,211,238,0.8)] dark:drop-shadow-none">
          <h1 class="hero-title text-5xl sm:text-10xl lg:text-7xl font-light mb-3">
            Shine Level Up
          </h1>
          <p class="hero-subtitle text-lg sm:text-3xl font-light mb-8">
            Keep shining, keep earning.
          </p>
        </div>

        <!-- CTA Buttons -->
        <div class="flex flex-col sm:flex-row gap-4 justify-center mb-16">
          <a href="#tasks" @click="scrollToTasks" class="px-8 py-3 rounded-full bg-gradient-to-br from-purple-500 to-cyan-400 text-white text-lg font-normal transition-all duration-300 hover:scale-110 cursor-pointer">
            開始探險
          </a>
        </div>
      </div>

      <!-- Scrolling Rewards Section -->
      <div class="relative z-10 w-full overflow-hidden mt-8">
        <!-- First Row - Left to Right -->
        <div class="flex gap-4 animate-scroll-left mb-4">
          <div v-for="item in [...rewards, ...rewards]" :key="item.id + '-1'" class="flex-shrink-0 w-72 p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 transition-all duration-300">
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
          <div v-for="item in [...rewards2, ...rewards2]" :key="item.id + '-2'" class="flex-shrink-0 w-72 p-4 rounded-xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 transition-all duration-300">
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

    <!-- Tasks Section -->
    <section id="tasks" class="w-full py-16 px-4 sm:px-6 lg:px-8">
      <div class="max-w-7xl mx-auto">
        <!-- Filter Tabs -->
        <div class="flex gap-2 mb-8 overflow-x-auto pb-2 justify-center">
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
            class="p-6 rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border hover:scale-105 transition-all duration-300 group cursor-pointer">

            <div class="flex items-start justify-between mb-4">
              <span class="text-4xl">{{ task.icon }}</span>
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
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { mockTasks } from '../mock'

// Scroll to Top
const showScrollTop = ref(false)

const handleScroll = () => {
  showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
  // 立即滾動到頂部
  window.scrollTo({ top: 0, behavior: 'smooth' })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
}

// Blob refs
const blob1 = ref(null)
const blob2 = ref(null)
const blob3 = ref(null)
const blob4 = ref(null)
const blob5 = ref(null)
const blob6 = ref(null)
const blob7 = ref(null)

// Blob animation
const MIN_SPEED = 0.5
const MAX_SPEED = 2

function randomNumber(min, max) {
  return Math.random() * (max - min) + min
}

class Blob {
  constructor(el) {
    this.el = el
    const boundingRect = this.el.getBoundingClientRect()
    this.size = boundingRect.width
    this.initialX = randomNumber(0, window.innerWidth - this.size)
    this.initialY = randomNumber(0, window.innerHeight - this.size)
    this.el.style.top = `${this.initialY}px`
    this.el.style.left = `${this.initialX}px`
    this.vx = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
    this.vy = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
    this.x = this.initialX
    this.y = this.initialY
  }

  update() {
    this.x += this.vx
    this.y += this.vy
    if (this.x >= window.innerWidth - this.size) {
      this.x = window.innerWidth - this.size
      this.vx *= -1
    }
    if (this.y >= window.innerHeight - this.size) {
      this.y = window.innerHeight - this.size
      this.vy *= -1
    }
    if (this.x <= 0) {
      this.x = 0
      this.vx *= -1
    }
    if (this.y <= 0) {
      this.y = 0
      this.vy *= -1
    }
    this.el.style.transform = `translate(${this.x - this.initialX}px, ${this.y - this.initialY}px)`
  }
}

onMounted(() => {
  // Add scroll event listener
  window.addEventListener('scroll', handleScroll)

  const blobEls = [blob1.value, blob2.value, blob3.value, blob4.value, blob5.value, blob6.value, blob7.value].filter(Boolean)
  const blobs = blobEls.map(el => new Blob(el))

  function update() {
    requestAnimationFrame(update)
    blobs.forEach(blob => blob.update())
  }
  requestAnimationFrame(update)
})

onUnmounted(() => {
  // Remove scroll event listener
  window.removeEventListener('scroll', handleScroll)
})

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

// Tasks
const selectedCategory = ref('全部')
const categories = ['全部', 'daily', 'banking', 'esg', 'social']

const filteredTasks = computed(() => {
  if (selectedCategory.value === '全部') return mockTasks
  return mockTasks.filter(t => t.category === selectedCategory.value)
})

// Scroll to tasks section
const scrollToTasks = (e) => {
  e.preventDefault()
  const element = document.getElementById('tasks')
  if (element) {
    element.scrollIntoView({ behavior: 'smooth', block: 'start' })
  }
}
</script>

<style scoped>
/* Liquid Blobs */
.blobs {
  filter: blur(140px);
}

.blob {
  width: max(240px, 28vw);
  aspect-ratio: 1;
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 0;
}

.blob-1 {
  background: #2ac9de;
}

.blob-2 {
  background: #f087f4;
}

.blob-3 {
  background: color-mix(in srgb, #2ac9de, #f087f4 20%);
}

.blob-4 {
  background: color-mix(in srgb, #2ac9de, #f087f4 35%);
}

.blob-5 {
  background: color-mix(in srgb, #2ac9de, #f087f4 50%);
}

.blob-6 {
  background: color-mix(in srgb, #2ac9de, #f087f4 65%);
}

.blob-7 {
  background: color-mix(in srgb, #2ac9de, #f087f4 80%);
}

/* Hero Title Shimmer Animation */
@keyframes shimmer {
  0% {
    background-position: -200% center;
  }
  100% {
    background-position: 200% center;
  }
}

.animate-scale-in {
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0.2) 0%,
    rgba(255, 255, 255, 0.2) 30%,
    rgba(255, 255, 255, 1) 50%,
    rgba(255, 255, 255, 0.2) 70%,
    rgba(255, 255, 255, 0.2) 100%
  );
  background-size: 200% auto;
  -webkit-background-clip: text;
  background-clip: text;
  animation: shimmer 2s linear infinite;
}

/* Sparkle Stars Animation */
@keyframes sparkle {
  0%, 100% {
    opacity: 0;
    transform: scale(0) rotate(0deg);
  }
  50% {
    opacity: 1;
    transform: scale(1) rotate(180deg);
  }
}

.sparkle-container {
  position: absolute;
  width: 100%;
  height: 100%;
}

.sparkle {
  position: absolute;
  width: 4px;
  height: 4px;
  background: white;
  clip-path: polygon(
    50% 0%,
    61% 35%,
    98% 35%,
    68% 57%,
    79% 91%,
    50% 70%,
    21% 91%,
    32% 57%,
    2% 35%,
    39% 35%
  );
  animation: sparkle 3s ease-in-out infinite;
  filter: drop-shadow(0 0 2px white);
}

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

@keyframes glow-slow {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.5;
  }
  25% {
    transform: translate(200px, -150px) scale(1.2);
    opacity: 0.8;
  }
  50% {
    transform: translate(-100px, -200px) scale(0.9);
    opacity: 0.6;
  }
  75% {
    transform: translate(-200px, 150px) scale(1.1);
    opacity: 0.7;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.5;
  }
}

@keyframes glow-medium {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.4;
  }
  33% {
    transform: translate(-250px, 100px) scale(1.25);
    opacity: 0.75;
  }
  66% {
    transform: translate(150px, 200px) scale(0.85);
    opacity: 0.55;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.4;
  }
}

@keyframes glow-fast {
  0% {
    transform: translate(0, 0) scale(1);
    opacity: 0.35;
  }
  20% {
    transform: translate(180px, 120px) scale(1.15);
    opacity: 0.65;
  }
  40% {
    transform: translate(100px, -180px) scale(1.3);
    opacity: 0.75;
  }
  60% {
    transform: translate(-150px, -100px) scale(0.9);
    opacity: 0.5;
  }
  80% {
    transform: translate(-180px, 140px) scale(1.1);
    opacity: 0.6;
  }
  100% {
    transform: translate(0, 0) scale(1);
    opacity: 0.35;
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

.animate-glow-slow {
  animation: glow-slow 20s ease-in-out infinite;
}

.animate-glow-medium {
  animation: glow-medium 15s ease-in-out infinite;
}

.animate-glow-fast {
  animation: glow-fast 12s ease-in-out infinite;
}

/* Hero Text Breathing Animation */
@keyframes text-breathe {
  0%, 100% {
    opacity: 0.85;
  }
  50% {
    opacity: 1;
  }
}

.hero-title,
.hero-subtitle {
  animation: text-breathe 4s ease-in-out infinite;
  filter: drop-shadow(0 0 30px rgba(255, 255, 255, 0.6))
          drop-shadow(0 0 50px rgba(255, 255, 255, 0.5))
          drop-shadow(0 0 80px rgba(255, 255, 255, 0.4));
}

/* 深色模式下移除光暈 */
.dark .hero-title,
.dark .hero-subtitle {
  filter: none;
}
</style>