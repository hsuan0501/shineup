<template>
    <div ref="cursor"
         class="fixed pointer-events-none z-[5] w-5 h-5 rounded-full bg-white/30 backdrop-blur-md border border-white/40 shadow-xl"
         :style="{
             left: cursorX + 'px',
             top: cursorY + 'px',
             transform: 'translate(-50%, -50%)',
             opacity: isVisible ? 1 : 0,
             transition: 'opacity 0.3s ease'
         }">
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const cursor = ref(null)
const isVisible = ref(false)
const cursorX = ref(-100)
const cursorY = ref(-100)
let mouseX = -100
let mouseY = -100
let animationId = null

// 處理鼠標移動
const handleMouseMove = (e) => {
    mouseX = e.clientX
    mouseY = e.clientY

    if (!isVisible.value) {
        isVisible.value = true
    }
}

// 處理鼠標離開窗口
const handleMouseLeave = () => {
    isVisible.value = false
}

// 處理鼠標進入窗口
const handleMouseEnter = (e) => {
    mouseX = e.clientX
    mouseY = e.clientY
    isVisible.value = true
}

// 動畫循環
const animate = () => {
    // 平滑追蹤鼠標位置（緩動效果）
    const easing = 0.15
    cursorX.value += (mouseX - cursorX.value) * easing
    cursorY.value += (mouseY - cursorY.value) * easing

    animationId = requestAnimationFrame(animate)
}

// 檢測是否為移動設備
const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ||
           ('ontouchstart' in window) ||
           (navigator.maxTouchPoints > 0)
}

onMounted(() => {
    // 只在桌面設備上啟用
    if (!isMobile()) {
        window.addEventListener('mousemove', handleMouseMove)
        window.addEventListener('mouseleave', handleMouseLeave)
        window.addEventListener('mouseenter', handleMouseEnter)
        animate()
    }
})

onUnmounted(() => {
    if (animationId) {
        cancelAnimationFrame(animationId)
    }
    window.removeEventListener('mousemove', handleMouseMove)
    window.removeEventListener('mouseleave', handleMouseLeave)
    window.removeEventListener('mouseenter', handleMouseEnter)
})
</script>
