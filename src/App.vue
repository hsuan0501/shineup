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