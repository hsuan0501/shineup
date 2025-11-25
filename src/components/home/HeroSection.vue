<template>
    <section class="relative flex flex-col items-center justify-center px-4 sm:px-6 lg:px-8 w-full py-24 bg-gradient-to-br from-sky-400 via-purple-400 to-pink-400">

        <!-- Hero Content -->
        <div class="relative flex flex-col items-center justify-center text-center z-10 max-w-5xl mx-auto w-full mb-16">

            <!-- Subtle Gradient Background for text visibility (behind everything) -->
            <div
                class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[150%] h-[150%] bg-gradient-to-br from-blue-400/20 to-purple-400/20 blur-3xl rounded-full -z-10 pointer-events-none">
            </div>

            <!-- Twinkling Stars (Natural Diffusion) -->
            <!-- Left Side (4 stars) -->
            <div class="absolute -top-8 left-36 w-3.5 h-3.5 animate-twinkle-2 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute top-8 left-28 w-2.5 h-2.5 animate-twinkle-4 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <div class="absolute -bottom-4 left-44 w-3 h-3 animate-twinkle-5 text-white scale-y-[-1]">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <!-- Right Side (3 stars) -->
            <div class="absolute -top-4 right-36 w-3.5 h-3.5 animate-twinkle-2 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute top-6 right-28 w-2.5 h-2.5 animate-twinkle-4 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>
            <div class="absolute bottom-2 right-40 w-3 h-3 animate-twinkle-1 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <!-- Top Center -->
            <div class="absolute -top-12 left-1/4 w-1.5 h-1.5 animate-twinkle-3 text-white">
                <svg viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 0L14 10L24 12L14 14L12 24L10 14L0 12L10 10Z" />
                </svg>
            </div>

            <h1 class="hero-title text-3xl xs:text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-light mb-2 sm:mb-3 relative z-10 text-white">
                Shine Level Up
            </h1>
            <p class="hero-subtitle text-sm xs:text-base sm:text-lg md:text-xl lg:text-2xl font-light mb-6 sm:mb-8 relative z-10 text-white">
                Keep shining, keep earning.
            </p>

            <!-- CTA Buttons -->
            <div class="relative z-20 flex flex-col sm:flex-row gap-3 sm:gap-4 justify-center mb-4 sm:mb-6">
                <a href="#tasks" @click="scrollToTasks"
                    class="px-5 sm:px-6 md:px-8 py-2 sm:py-2.5 md:py-3 rounded-full bg-gradient-to-br from-sky-400 to-purple-400 text-white font-medium hover:scale-105 active:scale-95 transition-all duration-300 ease-out text-sm sm:text-base md:text-lg">
                    開始探險
                </a>
            </div>
        </div>

        <!-- Scrolling Rewards Section -->
        <div class="w-full">
            <RewardCarousel />
        </div>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
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
    const blobEls = [blob1.value, blob2.value, blob3.value, blob4.value, blob5.value, blob6.value, blob7.value].filter(Boolean)
    const blobs = blobEls.map(el => new Blob(el))

    function update() {
        requestAnimationFrame(update)
        blobs.forEach(blob => blob.update())
    }
    requestAnimationFrame(update)
})
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
    background: radial-gradient(circle, rgba(42, 201, 222, 0.8) 0%, rgba(42, 201, 222, 0.4) 100%);
}

.blob-2 {
    background: radial-gradient(circle, rgba(240, 135, 244, 0.8) 0%, rgba(240, 135, 244, 0.4) 100%);
}

.blob-3 {
    background: radial-gradient(circle, rgba(42, 201, 222, 0.7) 0%, rgba(42, 201, 222, 0.3) 100%);
}

.blob-4 {
    background: radial-gradient(circle, rgba(141, 168, 233, 0.7) 0%, rgba(141, 168, 233, 0.3) 100%);
}

.blob-5 {
    background: radial-gradient(circle, rgba(191, 135, 238, 0.7) 0%, rgba(191, 135, 238, 0.3) 100%);
}

.blob-6 {
    background: radial-gradient(circle, rgba(215, 135, 241, 0.7) 0%, rgba(215, 135, 241, 0.3) 100%);
}

.blob-7 {
    background: radial-gradient(circle, rgba(227, 151, 243, 0.7) 0%, rgba(227, 151, 243, 0.3) 100%);
}

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
