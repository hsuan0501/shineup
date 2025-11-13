import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useStore = defineStore('app', () => {
  const isDark = ref(false)
  const currentUser = ref(null)
  const currentLevel = ref('EXPLORER')

  const toggleDarkMode = () => {
    isDark.value = !isDark.value
    document.documentElement.classList.toggle('dark', isDark.value)
    localStorage.setItem('shineup-dark', isDark.value)
  }

  const initDarkMode = () => {
    const saved = localStorage.getItem('shineup-dark')
    if (saved !== null) {
      isDark.value = saved === 'true'
      document.documentElement.classList.toggle('dark', isDark.value)
    } else {
      isDark.value = false
    }
  }

  return {
    isDark,
    currentUser,
    currentLevel,
    toggleDarkMode,
    initDarkMode,
  }
})