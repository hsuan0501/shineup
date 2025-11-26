<template>
    <canvas ref="canvas" class="fixed inset-0 pointer-events-none z-50" :class="{ 'hidden': isMobile }"></canvas>
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

// 繪製帶光暈的四芒星
const drawStar = (x, y, size, opacity, rotation, withGlow = false) => {
    // 如果有光暈效果（主星星）
    if (withGlow) {
        ctx.save()
        // 繪製圓形光暈底座
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

    // 繪製四芒星（確保置中）
    ctx.save()
    ctx.translate(x, y)
    ctx.rotate((rotation * Math.PI) / 180)
    ctx.globalAlpha = opacity

    ctx.fillStyle = 'white'
    ctx.beginPath()

    // 使用與 Hero SVG 相同的路徑，但要置中
    const scale = size / 24
    const offset = -12 * scale // 置中偏移
    ctx.moveTo(12 * scale + offset, 0 + offset)
    ctx.lineTo(14 * scale + offset, 10 * scale + offset)
    ctx.lineTo(24 * scale + offset, 12 * scale + offset)
    ctx.lineTo(14 * scale + offset, 14 * scale + offset)
    ctx.lineTo(12 * scale + offset, 24 * scale + offset)
    ctx.lineTo(10 * scale + offset, 14 * scale + offset)
    ctx.lineTo(0 + offset, 12 * scale + offset)
    ctx.lineTo(10 * scale + offset, 10 * scale + offset)
    ctx.closePath()

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

    // 計算當前位置與目標位置的距離
    const dx = mouseX - currentX
    const dy = mouseY - currentY

    // 根據距離動態生成殘影位置（5個殘影點）
    const trailCount = 5
    for (let i = 1; i <= trailCount; i++) {
        const ratio = i / trailCount
        const trailX = currentX - dx * ratio * 0.3 // 殘影在後方
        const trailY = currentY - dy * ratio * 0.3

        const progress = 1 - ratio
        const opacity = progress * 0.3 // 漸弱透明度
        const size = 18 + progress * 6 // 18 ~ 24px

        drawStar(trailX, trailY, size, opacity, 0, false)
    }

    // 繪製主星星（帶光暈效果）
    if (mouseX !== 0 || mouseY !== 0) {
        drawStar(currentX, currentY, 24, 0.9, 0, true) // 更大尺寸 + 光暈
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
