<template>
    <div class="w-full relative" id="home-top">
        <!-- Liquid Blobs Background -->
        <div class="blobs">
            <div class="blob blob-blue" ref="blob1"></div>
            <div class="blob blob-purple" ref="blob2"></div>
        </div>

        <!-- Hero Section -->
        <HeroSection />

        <!-- Tasks Section -->
        <TaskGrid />

        <!-- Gifts Section -->
        <GiftGrid />
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import HeroSection from '../components/home/HeroSection.vue'
import TaskGrid from '../components/home/TaskGrid.vue'
import GiftGrid from '../components/home/GiftGrid.vue'

// Blob animation
const blob1 = ref(null)
const blob2 = ref(null)

const MIN_SPEED = 0.2
const MAX_SPEED = 0.5

function randomNumber(min, max) {
    return Math.random() * (max - min) + min
}

class Blob {
    constructor(el, index) {
        this.el = el
        this.index = index

        const rangeX = 80
        const rangeY = 50

        this.x = 0
        this.y = 0

        this.minX = -rangeX
        this.maxX = rangeX
        this.minY = -rangeY
        this.maxY = rangeY

        this.vx = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
        this.vy = randomNumber(MIN_SPEED, MAX_SPEED) * (Math.random() > 0.5 ? 1 : -1)
    }

    update() {
        this.x += this.vx
        this.y += this.vy

        if (this.x >= this.maxX) {
            this.x = this.maxX
            this.vx *= -1
        }
        if (this.y >= this.maxY) {
            this.y = this.maxY
            this.vy *= -1
        }
        if (this.x <= this.minX) {
            this.x = this.minX
            this.vx *= -1
        }
        if (this.y <= this.minY) {
            this.y = this.minY
            this.vy *= -1
        }
        this.el.style.transform = `translate(${this.x}px, ${this.y}px)`
    }
}

onMounted(() => {
    // Force scroll to top on mount
    window.scrollTo(0, 0)

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

    // Initialize blob animation
    const blobEls = [blob1.value, blob2.value].filter(Boolean)
    const blobs = blobEls.map((el, index) => new Blob(el, index))

    function update() {
        requestAnimationFrame(update)
        blobs.forEach(blob => blob.update())
    }
    requestAnimationFrame(update)
})
</script>

<style scoped>
/* Liquid Blobs Background */
.blobs {
    position: absolute;
    z-index: 0;
    top: -96px; /* 向上延伸到導覽列底下 (pt-24 = 96px) */
    left: 0;
    width: 100%;
    height: 900px;
    pointer-events: none;
    filter: blur(140px);
    overflow: hidden;
    /* 添加底部漸層淡出效果 */
    mask-image: linear-gradient(to bottom,
        rgba(0, 0, 0, 1) 0%,
        rgba(0, 0, 0, 1) 50%,
        rgba(0, 0, 0, 0.7) 75%,
        rgba(0, 0, 0, 0) 100%);
    -webkit-mask-image: linear-gradient(to bottom,
        rgba(0, 0, 0, 1) 0%,
        rgba(0, 0, 0, 1) 50%,
        rgba(0, 0, 0, 0.7) 75%,
        rgba(0, 0, 0, 0) 100%);
}

.blob {
    border-radius: 50%;
    position: absolute;
    transform-origin: center;
}

.blob-blue {
    background: #2ac9de;
    width: 700px;
    height: 700px;
    top: 146px; /* 50px + 96px (offset for navbar) */
    left: calc(50% - 350px);
}

.blob-purple {
    background: #f087f4;
    width: 550px;
    height: 550px;
    top: 196px; /* 100px + 96px (offset for navbar) */
    left: calc(50% - 50px);
}
</style>
