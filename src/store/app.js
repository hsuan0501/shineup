import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI, userAPI, taskAPI, giftAPI } from '../api'

export const useStore = defineStore('app', () => {
  const isDark = ref(false)
  const currentUser = ref(null)
  const currentLevel = ref('EXPLORER')
  const isAuthenticated = ref(false)
  const userPoints = ref({ upgradePoints: 0, rewardPoints: 0 })

  // 搜尋狀態
  const searchQuery = ref('')

  // Toast 通知狀態
  const toasts = ref([])
  let toastId = 0

  // 購物車狀態管理
  const cartItems = ref([
    {
      id: 1,
      title: 'LIFE PEN 種子鉛筆組',
      description: '市價NT$280',
      image: '/images/gifts/gift-1.jpg',
      points: 100,
      level: 'EXPLORER',
      series: 'sustainable',
      quantity: 1
    },
    {
      id: 2,
      title: 'UiU 環保便攜吸管組',
      description: '市價NT$300',
      image: '/images/gifts/gift-2.jpg',
      points: 100,
      level: 'EXPLORER',
      series: 'sustainable',
      quantity: 1
    },
    {
      id: 5,
      title: '沐muhair 無塑固態洗髮精',
      description: '市價NT$400',
      image: '/images/gifts/gift-5.jpg',
      points: 130,
      level: 'EXPLORER',
      series: 'sustainable',
      quantity: 1
    },
    {
      id: 9,
      title: 'ekax 雲朵筆電包',
      description: '市價NT$650',
      image: '/images/gifts/gift-9.jpg',
      points: 220,
      level: 'CREATOR',
      series: 'quality',
      quantity: 1
    }
  ])

  // 心願清單狀態管理 - 預設收藏項目
  const wishlistItems = ref([
    {
      id: 2,
      title: 'UiU 環保便攜吸管組',
      points: 100,
      image: '/images/gifts/gift-2.jpg',
      level: 'EXPLORER',
      series: 'sustainable'
    },
    {
      id: 8,
      title: 'SUCCULAND 多肉植物',
      points: 200,
      image: '/images/gifts/gift-8.jpg',
      level: 'EXPLORER',
      series: 'sustainable'
    },
    {
      id: 16,
      title: 'MOMOCONCEPT 保溫杯',
      points: 500,
      image: '/images/gifts/gift-16.jpg',
      level: 'CREATOR',
      series: 'quality'
    },
    {
      id: 17,
      title: 'mordeco 轉轉零錢筒',
      points: 530,
      image: '/images/gifts/gift-17.jpg',
      level: 'VISIONARY',
      series: 'aesthetic'
    },
    {
      id: 24,
      title: 'Wanu 銜月床頭燈',
      points: 1200,
      image: '/images/gifts/gift-24.jpg',
      level: 'VISIONARY',
      series: 'aesthetic'
    },
    {
      id: 28,
      title: 'AirPods Pro 3',
      points: 2500,
      image: '/images/gifts/gift-28.jpg',
      level: 'LUMINARY',
      series: 'premium'
    }
  ])

  // 計算購物車總数量
  const cartItemCount = computed(() => {
    return cartItems.value.reduce((total, item) => total + item.quantity, 0)
  })

  // 購物車操作函數
  const addToCart = (item) => {
    const existingItem = cartItems.value.find(cartItem => cartItem.id === item.id)
    if (existingItem) {
      existingItem.quantity += 1
    } else {
      cartItems.value.push({ ...item, quantity: 1 })
    }
  }

  const removeFromCart = (itemId) => {
    cartItems.value = cartItems.value.filter(item => item.id !== itemId)
  }

  const updateCartItemQuantity = (itemId, change) => {
    const item = cartItems.value.find(item => item.id === itemId)
    if (item) {
      item.quantity += change
      if (item.quantity <= 0) {
        removeFromCart(itemId)
      }
    }
  }

  // 心願清單操作函數
  const addToWishlist = (gift) => {
    const exists = wishlistItems.value.find(item => item.id === gift.id)
    if (!exists) {
      wishlistItems.value.push(gift)
      // 儲存到 localStorage
      localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
    }
  }

  const removeFromWishlist = (giftId) => {
    wishlistItems.value = wishlistItems.value.filter(item => item.id !== giftId)
    // 更新 localStorage
    localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
  }

  const isInWishlist = (giftId) => {
    return wishlistItems.value.some(item => item.id === giftId)
  }

  const toggleWishlist = (gift) => {
    if (isInWishlist(gift.id)) {
      removeFromWishlist(gift.id)
    } else {
      addToWishlist(gift)
    }
  }

  const loadWishlist = () => {
    const saved = localStorage.getItem('wishlist')
    if (saved) {
      try {
        const parsed = JSON.parse(saved)
        // 如果 localStorage 有資料，使用它
        if (parsed && parsed.length > 0) {
          wishlistItems.value = parsed
        } else {
          // 如果是空陣列，使用預設值並儲存
          localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
        }
      } catch (e) {
        console.error('Failed to load wishlist', e)
        // 如果讀取失敗，保持預設收藏並儲存
        localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
      }
    } else {
      // 如果沒有 saved，儲存預設值到 localStorage
      localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
    }
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

  // 認證相關函數
  const login = async (credentials) => {
    try {
      const response = await authAPI.login(credentials)
      const { token, user } = response.data

      localStorage.setItem('token', token)
      localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))

      isAuthenticated.value = true
      currentUser.value = user
      currentLevel.value = user.level || 'EXPLORER'
      userPoints.value = {
        upgradePoints: user.upgradePoints || 0,
        rewardPoints: user.rewardPoints || 0
      }

      return { success: true, user }
    } catch (error) {
      console.error('Login failed:', error)
      return { success: false, message: error.response?.data?.message || '登入失敗' }
    }
  }

  const register = async (userData) => {
    try {
      const response = await authAPI.register(userData)
      const { token, user } = response.data

      localStorage.setItem('token', token)
      localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))

      isAuthenticated.value = true
      currentUser.value = user
      currentLevel.value = user.level || 'EXPLORER'
      userPoints.value = {
        upgradePoints: user.upgradePoints || 0,
        rewardPoints: user.rewardPoints || 0
      }

      return { success: true, user }
    } catch (error) {
      console.error('Register failed:', error)
      return { success: false, message: error.response?.data?.message || '註冊失敗' }
    }
  }

  const logout = () => {
    isAuthenticated.value = false
    currentUser.value = null
    currentLevel.value = 'EXPLORER'
    userPoints.value = { upgradePoints: 0, rewardPoints: 0 }
    localStorage.removeItem('auth')
    localStorage.removeItem('token')
  }

  const checkAuth = async () => {
    const token = localStorage.getItem('token')
    if (token) {
      try {
        const response = await authAPI.getMe()
        const { user } = response.data

        isAuthenticated.value = true
        currentUser.value = user
        currentLevel.value = user.level || 'EXPLORER'
        userPoints.value = {
          upgradePoints: user.upgradePoints || 0,
          rewardPoints: user.rewardPoints || 0
        }
      } catch (error) {
        console.error('Token invalid:', error)
        logout()
      }
    }
  }

  // 完成任務 API
  const completeTask = async (taskId) => {
    if (!currentUser.value) return { success: false, message: '請先登入' }

    try {
      const response = await taskAPI.complete(taskId, currentUser.value.id)
      userPoints.value = {
        upgradePoints: response.data.upgradePoints,
        rewardPoints: response.data.rewardPoints
      }
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '完成任務失敗' }
    }
  }

  // 兌換禮品 API
  const redeemGift = async (giftId) => {
    if (!currentUser.value) return { success: false, message: '請先登入' }

    try {
      const response = await giftAPI.redeem(giftId, currentUser.value.id)
      // 重新取得用戶資料更新積分
      await checkAuth()
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '兌換失敗' }
    }
  }

  // 重置用戶資料（展示用）
  const resetUser = async () => {
    if (!currentUser.value) return { success: false, message: '請先登入' }

    try {
      const response = await userAPI.reset(currentUser.value.id)
      const user = response.data
      currentUser.value = user
      currentLevel.value = user.level || 'EXPLORER'
      userPoints.value = {
        upgradePoints: user.upgradePoints || 0,
        rewardPoints: user.rewardPoints || 0
      }
      return { success: true, message: '資料已重置' }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '重置失敗' }
    }
  }

  // 搜尋功能
  const setSearchQuery = (query) => {
    searchQuery.value = query
  }

  const clearSearch = () => {
    searchQuery.value = ''
  }

  // Toast 通知功能
  const showToast = (message, type = 'info', duration = 3000) => {
    const id = ++toastId
    const toast = { id, message, type }
    toasts.value.push(toast)

    // 自動移除 toast
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
    currentUser,
    currentLevel,
    isAuthenticated,
    userPoints,
    cartItems,
    cartItemCount,
    addToCart,
    removeFromCart,
    updateCartItemQuantity,
    wishlistItems,
    addToWishlist,
    removeFromWishlist,
    isInWishlist,
    toggleWishlist,
    loadWishlist,
    toggleDarkMode,
    initDarkMode,
    login,
    register,
    logout,
    checkAuth,
    completeTask,
    redeemGift,
    resetUser,
    searchQuery,
    setSearchQuery,
    clearSearch,
    toasts,
    showToast,
    removeToast,
  }
})