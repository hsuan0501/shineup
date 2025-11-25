<template>
    <div class="w-full" id="home-top">
        <!-- Scroll to Top Button -->
        <button @click="scrollToTop" :class="[
            'fixed bottom-6 z-50 h-14 w-14 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 hover:scale-105 active:scale-95 transition-all duration-400 ease-out shadow-[0_4px_16px_rgba(0,0,0,0.08)] dark:shadow-[0_4px_16px_rgba(0,0,0,0.3)] hover:shadow-[0_6px_20px_rgba(0,0,0,0.012)] dark:hover:shadow-[0_6px_20px_rgba(0,0,0,0.4)] backdrop-blur-xl backdrop-saturate-150 group',
            { 'blur-sm': isLoginModalOpen }
        ]"
            style="left: max(1rem, calc((100vw - 72rem) / 2 + 2rem))"
            aria-label="回到頂部">
            <svg class="w-5 h-5 text-zinc-600 dark:text-gray-300 group-hover:text-zinc-800 dark:group-hover:text-white transition-colors duration-300"
                fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
            </svg>
        </button>

        <!-- Hero Section -->
        <HeroSection />

        <!-- Tasks Section -->
        <TaskGrid />

        <!-- Gifts Section -->
        <GiftGrid />
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import HeroSection from '../components/home/HeroSection.vue'
import TaskGrid from '../components/home/TaskGrid.vue'
import GiftGrid from '../components/home/GiftGrid.vue'

// 檢測登入modal是否開啟
const isLoginModalOpen = ref(false)

// 監聽 document 上的 modal 變化
if (typeof document !== 'undefined') {
  const observer = new MutationObserver(() => {
    const modal = document.querySelector('.fixed.inset-0.z-50')
    isLoginModalOpen.value = !!modal
  })
  observer.observe(document.body, { childList: true, subtree: true })
}

// Scroll to Top
const showScrollTop = ref(false)

const handleScroll = () => {
    showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
    // 滾動到頁面最頂部
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    })
    // 備用方案
    document.documentElement.scrollTop = 0
    document.body.scrollTop = 0
}

onMounted(() => {
    // Force scroll to top on mount
    window.scrollTo(0, 0)

    // Add scroll event listener
    window.addEventListener('scroll', handleScroll)

    // Check if URL has hash and scroll to it if needed, but default to top
    if (window.location.hash) {
        const element = document.querySelector(window.location.hash)
        if (element) {
            // Small delay to ensure DOM is ready
            setTimeout(() => {
                element.scrollIntoView({ behavior: 'smooth', block: 'start' })
            }, 100)
        }
    }
})

onUnmounted(() => {
    // Remove scroll event listener
    window.removeEventListener('scroll', handleScroll)
})
</script>
