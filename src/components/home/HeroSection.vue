<template>
    <section class="relative flex flex-col items-center justify-center px-4 sm:px-6 lg:px-8 w-full pt-24 pb-8 overflow-hidden">
        <!-- Hero Content -->
        <div class="relative flex flex-col items-center justify-center text-center z-10 max-w-5xl mx-auto w-full mb-16">
            <!-- Twinkling Stars (Natural Diffusion) -->
            <!-- Left Side (2 stars) -->
            <div class="absolute -top-8 left-[23%] w-3.5 h-3.5 animate-twinkle-2 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute top-8 left-[18%] w-2.5 h-2.5 animate-twinkle-4 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <div class="absolute bottom-16 left-[26%] w-3 h-3 animate-twinkle-5 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <!-- Right Side (3 stars) -->
            <div class="absolute -top-8 right-[23%] w-3.5 h-3.5 animate-twinkle-2 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute top-2 right-[19%] w-2.5 h-2.5 animate-twinkle-4 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute bottom-20 right-[24%] w-3 h-3 animate-twinkle-1 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <h1 class="hero-title text-3xl xs:text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-light mb-2 sm:mb-3 relative z-10 text-white drop-shadow-lg">
                Shine Level Up
            </h1>
            <p class="hero-subtitle text-sm xs:text-base sm:text-lg md:text-xl lg:text-2xl font-light mb-6 sm:mb-8 relative z-10 text-white drop-shadow-lg">
                Keep shining, keep earning.
            </p>

            <!-- CTA Buttons -->
            <div class="relative z-20 flex flex-col sm:flex-row gap-3 sm:gap-4 justify-center mb-4 sm:mb-6">
                <a href="#tasks" @click="scrollToTasks"
                    class="px-5 sm:px-6 md:px-8 py-2 sm:py-2.5 md:py-3 rounded-full bg-white/20 backdrop-blur-sm border border-white/30 text-white font-medium hover:scale-105 hover:bg-white/30 active:scale-95 transition-all duration-300 ease-out text-sm sm:text-base md:text-lg">
                    開始探險
                </a>
            </div>
        </div>

        <!-- Scrolling Rewards Section -->
        <div class="w-full relative z-10">
            <RewardCarousel />
        </div>
    </section>
</template>

<script setup>
import { useRouter } from 'vue-router'
import RewardCarousel from './RewardCarousel.vue'

const router = useRouter()

const scrollToTasks = async (e) => {
    e.preventDefault()

    // 如果不在首頁,先導航到首頁
    if (router.currentRoute.value.path !== '/') {
        await router.push('/')
        // 等待頁面渲染
        setTimeout(() => {
            const element = document.getElementById('tasks')
            if (element) {
                element.scrollIntoView({ behavior: 'smooth', block: 'start' })
            }
        }, 100)
    } else {
        // 已經在首頁,直接滾動
        const element = document.getElementById('tasks')
        if (element) {
            element.scrollIntoView({ behavior: 'smooth', block: 'start' })
        }
    }
}
</script>

<style scoped>
/* Twinkling Stars Animation */
@keyframes twinkle {

    0%,
    100% {
        opacity: 0.5;
        transform: scale(0.9);
    }

    50% {
        opacity: 1;
        transform: scale(1.1) rotate(10deg);
    }
}

.animate-twinkle-1 {
    animation: twinkle 3s ease-in-out infinite 0s;
}

.animate-twinkle-2 {
    animation: twinkle 3s ease-in-out infinite 0.6s;
}

.animate-twinkle-3 {
    animation: twinkle 3s ease-in-out infinite 1.2s;
}

.animate-twinkle-4 {
    animation: twinkle 3s ease-in-out infinite 1.8s;
}

.animate-twinkle-5 {
    animation: twinkle 3s ease-in-out infinite 2.4s;
}

/* 深色模式下移除光暈 */
.dark .hero-title,
.dark .hero-subtitle {
    filter: none;
}
</style>
