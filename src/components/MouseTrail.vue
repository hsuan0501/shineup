<template>
    <canvas ref="canvas" class="fixed inset-0 pointer-events-none z-10" :class="{ 'hidden': isMobile }"></canvas>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const canvas = ref(null)
const isMobile = ref(false)
let ctx = null
let mouseX = 0
let mouseY = 0
let currentX = 0 // 當前星星位置
let currentY = 0
let animationId = null

// 初始化 Canvas
const initCanvas = () => {
    if (!canvas.value) return

    ctx = canvas.value.getContext('2d')
    resizeCanvas()
}

// 調整 Canvas 大小
const resizeCanvas = () => {
    if (!canvas.value) return
    canvas.value.width = window.innerWidth
    canvas.value.height = window.innerHeight
}

// 繪製光暈
const drawGlow = (x, y, size, opacity) => {
    ctx.save()
    const gradient = ctx.createRadialGradient(x, y, 0, x, y, size * 2)
    gradient.addColorStop(0, `rgba(255, 255, 255, ${opacity * 0.3})`)
    gradient.addColorStop(0.5, `rgba(255, 255, 255, ${opacity * 0.15})`)
    gradient.addColorStop(1, 'rgba(255, 255, 255, 0)')

    ctx.fillStyle = gradient
    ctx.beginPath()
    ctx.arc(x, y, size * 2, 0, Math.PI * 2)
    ctx.fill()
    ctx.restore()
}

// 處理鼠標移動
const handleMouseMove = (e) => {
    mouseX = e.clientX
    mouseY = e.clientY
}

// 動畫循環
const animate = () => {
    if (!ctx || !canvas.value) return

    // 清空 Canvas
    ctx.clearRect(0, 0, canvas.value.width, canvas.value.height)

    // 平滑追蹤鼠標位置（緩動效果）
    const easing = 0.15 // 緩動係數，越小越平滑
    currentX += (mouseX - currentX) * easing
    currentY += (mouseY - currentY) * easing

    // 繪製主光暈（只繪製光暈，不繪製星星和殘影）
    if (mouseX !== 0 || mouseY !== 0) {
        drawGlow(currentX, currentY, 20, 0.9)
    }

    animationId = requestAnimationFrame(animate)
}

// 檢測是否為移動設備
const checkMobile = () => {
    isMobile.value = /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) || window.innerWidth < 768
}

onMounted(() => {
    checkMobile()

    if (!isMobile.value) {
        initCanvas()
        window.addEventListener('mousemove', handleMouseMove)
        window.addEventListener('resize', resizeCanvas)
        animate()
    }
})

onUnmounted(() => {
    if (animationId) {
        cancelAnimationFrame(animationId)
    }
    window.removeEventListener('mousemove', handleMouseMove)
    window.removeEventListener('resize', resizeCanvas)
})
</script>
