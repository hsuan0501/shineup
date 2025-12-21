import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUIStore = defineStore('ui', () => {
  const isDark = ref(false)
  const searchQuery = ref('')
  const toasts = ref([])
  let toastId = 0

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

  const setSearchQuery = (query) => {
    searchQuery.value = query
  }

  const clearSearch = () => {
    searchQuery.value = ''
  }

  const showToast = (message, type = 'info', duration = 3000) => {
    const id = ++toastId
    const toast = { id, message, type }
    toasts.value.push(toast)

    setTimeout(() => {
      removeToast(id)
    }, duration)

    return id
  }

  const removeToast = (id) => {
    const index = toasts.value.findIndex(t => t.id === id)
    if (index > -1) {
      toasts.value.splice(index, 1)
    }
  }

  return {
    isDark,
    searchQuery,
    toasts,
    toggleDarkMode,
    initDarkMode,
    setSearchQuery,
    clearSearch,
    showToast,
    removeToast
  }
})
