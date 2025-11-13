<template>
  <div :class="isDark ? 'dark' : ''" class="w-screen min-h-screen overflow-x-hidden" style="margin: 0; padding: 0;">
    <div class="flex flex-col min-h-screen bg-light-gradient dark:bg-dark-gradient">
      <!-- NavBar -->
      <NavBar />

      <!-- Main Content -->
      <main class="flex-1 pt-24">
        <RouterView />
      </main>

      <!-- Footer (放在這裡，讓背景連貫) -->
      <Footer />
    </div>

    <!-- FloatBar (固定在下方) -->
    <FloatBar />
  </div>
</template>

<script setup>
import { useStore } from './store/app'
import { computed, onMounted } from 'vue'
import NavBar from './components/NavBar.vue'
import FloatBar from './components/FloatBar.vue'
import Footer from './components/Footer.vue'
import { RouterView } from 'vue-router'

const store = useStore()
const isDark = computed(() => store.isDark)

onMounted(() => {
  store.initDarkMode()
})
</script>

<style scoped>
.stars-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 0;
  overflow: hidden;
}

.star {
  position: absolute;
  background: radial-gradient(circle, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0.8) 50%, transparent 100%);
  border-radius: 50%;
  animation: twinkle infinite ease-in-out;
  opacity: 0;
  box-shadow: 0 0 8px 2px rgba(255, 255, 255, 0.8),
              0 0 12px 4px rgba(139, 92, 246, 0.4);
}

@keyframes twinkle {
  0%, 100% {
    opacity: 0;
    transform: scale(0.8);
  }
  50% {
    opacity: 1;
    transform: scale(1.2);
  }
}

/* 深色模式下星星更明顯 */
.dark .star {
  background: radial-gradient(circle, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0.9) 50%, transparent 100%);
  box-shadow: 0 0 12px 3px rgba(255, 255, 255, 0.9),
              0 0 16px 5px rgba(139, 92, 246, 0.6),
              0 0 20px 7px rgba(59, 130, 246, 0.4);
}

/* 淺色模式下星星稍微柔和 */
.star {
  background: radial-gradient(circle, rgba(139, 92, 246, 1) 0%, rgba(139, 92, 246, 0.7) 50%, transparent 100%);
  box-shadow: 0 0 8px 2px rgba(139, 92, 246, 0.7),
              0 0 12px 4px rgba(139, 92, 246, 0.5);
}

.dark .star {
  background: radial-gradient(circle, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0.9) 50%, transparent 100%);
  box-shadow: 0 0 12px 3px rgba(255, 255, 255, 0.9),
              0 0 16px 5px rgba(139, 92, 246, 0.6),
              0 0 20px 7px rgba(59, 130, 246, 0.4);
}
</style>