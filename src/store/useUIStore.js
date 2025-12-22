import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUIStore = defineStore('ui', () => {
  const isDark = ref(false)
  const searchQuery = ref('')
  const toasts = ref([])
  const showNotificationPanel = ref(false)
  let toastId = 0

  // 通知設定永遠保持全開（每次重新載入都重置為全開）
  const notificationSettings = ref({ levelUp: true, points: true, shipping: true })

  const getInitialNotifications = () => {
    try {
      const saved = localStorage.getItem('shineup-notification-list')
      if (saved) return JSON.parse(saved)
    } catch (e) {
      console.error('Failed to parse notifications', e)
    }
    return []
  }
  const notificationList = ref(getInitialNotifications())

  const addNotification = (notification) => {
    const newNotif = {
      id: Date.now(),
      timestamp: new Date().toISOString(),
      read: false,
      ...notification
    }
    notificationList.value.unshift(newNotif)
    if (notificationList.value.length > 20) {
      notificationList.value = notificationList.value.slice(0, 20)
    }
    localStorage.setItem('shineup-notification-list', JSON.stringify(notificationList.value))
    return newNotif
  }

  const markNotificationRead = (id) => {
    const notif = notificationList.value.find(n => n.id === id)
    if (notif) {
      notif.read = true
      localStorage.setItem('shineup-notification-list', JSON.stringify(notificationList.value))
    }
  }

  const clearNotifications = () => {
    notificationList.value = []
    localStorage.setItem('shineup-notification-list', JSON.stringify([]))
  }

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

  const toggleNotificationSetting = (key) => {
    notificationSettings.value[key] = !notificationSettings.value[key]
    localStorage.setItem('shineup-notifications', JSON.stringify(notificationSettings.value))
    return notificationSettings.value[key]
  }

  return {
    isDark,
    searchQuery,
    toasts,
    showNotificationPanel,
    notificationSettings,
    notificationList,
    toggleDarkMode,
    initDarkMode,
    setSearchQuery,
    clearSearch,
    showToast,
    removeToast,
    toggleNotificationSetting,
    addNotification,
    markNotificationRead,
    clearNotifications
  }
})
