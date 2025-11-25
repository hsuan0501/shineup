<template>
    <div class="w-full" id="home-top">
        <!-- Hero Section -->
        <HeroSection />

        <!-- Tasks Section -->
        <TaskGrid />

        <!-- Gifts Section -->
        <GiftGrid />
    </div>
</template>

<script setup>
import { onMounted } from 'vue'
import HeroSection from '../components/home/HeroSection.vue'
import TaskGrid from '../components/home/TaskGrid.vue'
import GiftGrid from '../components/home/GiftGrid.vue'

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
})
</script>
