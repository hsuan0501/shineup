<template>
    <button @click="scrollToTop"
        class="fixed bottom-6 z-50 h-14 w-14 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 hover:scale-105 active:scale-95 transition-all duration-400 ease-out shadow-[0_4px_16px_rgba(0,0,0,0.08)] dark:shadow-[0_4px_16px_rgba(0,0,0,0.3)] hover:shadow-[0_6px_20px_rgba(0,0,0,0.012)] dark:hover:shadow-[0_6px_20px_rgba(0,0,0,0.4)] backdrop-blur-xl backdrop-saturate-150 group"
        style="left: max(1rem, calc((100vw - 72rem) / 2 - 2.5rem))" aria-label="回到頂部">
        <svg class="w-5 h-5 text-zinc-600 dark:text-gray-300 group-hover:text-zinc-800 dark:group-hover:text-white transition-colors duration-300"
            fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
        </svg>
    </button>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const showScrollTop = ref(false)

const handleScroll = () => {
    showScrollTop.value = window.scrollY > 300
}

const scrollToTop = () => {
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    })
    document.documentElement.scrollTop = 0
    document.body.scrollTop = 0
}

onMounted(() => {
    window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
    window.removeEventListener('scroll', handleScroll)
})
</script>
