import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { mockUsers } from '../mock.js'

// 設定是否使用後端 API（true = 連後端，false = 純前端 mock）
const USE_BACKEND_API = true

// 同步讀取 localStorage 快取的認證資料，避免頁面閃現
const getInitialAuthState = () => {
  try {
    const saved = localStorage.getItem('auth')
    if (saved) {
      const { isAuthenticated: auth, user } = JSON.parse(saved)
      if (auth && user) {
        return {
          user,
          level: user.level || 'EXPLORER',
          authenticated: true,
          points: {
            upgradePoints: user.levelPoints || user.upgradePoints || 0,
            rewardPoints: user.rewardPoints || 0
          }
        }
      }
    }
  } catch (e) {
    console.error('Failed to read initial auth state', e)
  }
  return {
    user: null,
    level: 'EXPLORER',
    authenticated: false,
    points: { upgradePoints: 0, rewardPoints: 0 }
  }
}

const initialAuth = getInitialAuthState()

export const useStore = defineStore('app', () => {
  const isDark = ref(false)
  const currentUser = ref(initialAuth.user)
  const currentLevel = ref(initialAuth.level)
  const isAuthenticated = ref(initialAuth.authenticated)
  const authLoading = ref(true) // 認證載入狀態
  const userPoints = ref(initialAuth.points)

  // 用戶統計資料
  const userStats = ref({
    tasksCompleted: 0,
    consecutiveDays: 0,
    totalLogins: 0,
    rewardsRedeemed: 0,
    friendsInvited: 0
  })

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

  // 結帳暫存
  const checkoutItems = ref([])

  // 活動紀錄（兌換記錄會即時加入）
  const activityRecords = ref([])

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

  const clearCart = () => {
    cartItems.value = []
  }

  // 設定結帳暫存（從購物車複製）
  const setCheckoutItems = (items) => {
    checkoutItems.value = [...items]
  }

  const clearCheckoutItems = () => {
    checkoutItems.value = []
  }

  // 新增活動紀錄
  const addActivityRecord = (record) => {
    const newRecord = {
      id: Date.now(),
      ...record,
      date: new Date().toISOString()
    }
    activityRecords.value.unshift(newRecord)
    // 只保留最近 50 筆紀錄
    if (activityRecords.value.length > 50) {
      activityRecords.value = activityRecords.value.slice(0, 50)
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
    // Mock 模式：直接用假資料驗證
    if (!USE_BACKEND_API) {
      const user = mockUsers[1]
      if (credentials.email === user.email) {
        localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
        isAuthenticated.value = true
        currentUser.value = user
        currentLevel.value = user.level
        userPoints.value = {
          upgradePoints: user.levelPoints,
          rewardPoints: user.rewardPoints
        }
        return { success: true, user }
      }
      return { success: false, message: '帳號或密碼錯誤' }
    }

    // 後端 API 模式
    try {
      const { authAPI } = await import('../api')
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
    // Mock 模式：直接建立用戶
    if (!USE_BACKEND_API) {
      const newUser = {
        id: Date.now(),
        name: userData.name,
        email: userData.email,
        level: 'EXPLORER',
        levelPoints: 0,
        rewardPoints: 0,
        avatar: `https://api.dicebear.com/9.x/thumbs/svg?seed=${userData.name}`
      }
      localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user: newUser }))
      isAuthenticated.value = true
      currentUser.value = newUser
      currentLevel.value = newUser.level
      userPoints.value = {
        upgradePoints: newUser.levelPoints,
        rewardPoints: newUser.rewardPoints
      }
      return { success: true, user: newUser }
    }

    // 後端 API 模式
    try {
      const { authAPI } = await import('../api')
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
    authLoading.value = true

    // Mock 模式：從 localStorage 讀取
    if (!USE_BACKEND_API) {
      const saved = localStorage.getItem('auth')
      if (saved) {
        try {
          const { isAuthenticated: auth, user } = JSON.parse(saved)
          if (auth && user) {
            isAuthenticated.value = true
            currentUser.value = user
            currentLevel.value = user.level || 'EXPLORER'
            userPoints.value = {
              upgradePoints: user.levelPoints || user.upgradePoints || 0,
              rewardPoints: user.rewardPoints || 0
            }
          }
        } catch (e) {
          console.error('Failed to parse auth', e)
        }
      }
      authLoading.value = false
      return
    }

    // 後端 API 模式
    const token = localStorage.getItem('token')
    if (token) {
      try {
        const { authAPI } = await import('../api')
        const response = await authAPI.getMe()
        const { user } = response.data

        isAuthenticated.value = true
        currentUser.value = user
        currentLevel.value = user.level || 'EXPLORER'
        userPoints.value = {
          upgradePoints: user.upgradePoints || 0,
          rewardPoints: user.rewardPoints || 0
        }
        // 同步更新 localStorage 快取
        localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
      } catch (error) {
        console.error('Token invalid:', error)
        logout()
      }
    }
    authLoading.value = false
  }

  // 完成任務 API
  const completeTask = async (taskId) => {
    if (!currentUser.value) return { success: false, message: '請先登入' }

    // Mock 模式：直接加積分
    if (!USE_BACKEND_API) {
      userPoints.value.upgradePoints += 50
      userPoints.value.rewardPoints += 50
      return { success: true, message: '任務完成！' }
    }

    try {
      const { taskAPI } = await import('../api')
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

    // Mock 模式：直接扣積分
    if (!USE_BACKEND_API) {
      return { success: true, message: '兌換成功！' }
    }

    try {
      const { giftAPI } = await import('../api')
      const response = await giftAPI.redeem(giftId, currentUser.value.id)
      await checkAuth()
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '兌換失敗' }
    }
  }

  // 重置用戶資料（展示用）
  const resetUser = async () => {
    if (!currentUser.value) return { success: false, message: '請先登入' }

    // Mock 模式：重置為預設值
    if (!USE_BACKEND_API) {
      const user = mockUsers[1]
      currentUser.value = user
      currentLevel.value = user.level
      userPoints.value = {
        upgradePoints: user.levelPoints,
        rewardPoints: user.rewardPoints
      }
      localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
      return { success: true, message: '資料已重置' }
    }

    try {
      const { userAPI } = await import('../api')
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

  // 取得用戶統計資料
  const fetchUserStats = async () => {
    if (!currentUser.value) return

    // Mock 模式：使用 mockUsers 的統計
    if (!USE_BACKEND_API) {
      userStats.value = mockUsers[1]?.stats || {
        tasksCompleted: 0,
        consecutiveDays: 0,
        totalLogins: 0,
        rewardsRedeemed: 0,
        friendsInvited: 0
      }
      return
    }

    // 後端 API 模式
    try {
      const { userAPI } = await import('../api')
      const response = await userAPI.getStats(currentUser.value.id)
      userStats.value = response.data
    } catch (error) {
      console.error('Failed to fetch user stats:', error)
    }
  }

  // 取得用戶活動紀錄
  const fetchActivityRecords = async () => {
    if (!currentUser.value) return []

    // Mock 模式：回傳空陣列（Profile.vue 會使用假資料）
    if (!USE_BACKEND_API) {
      return []
    }

    // 後端 API 模式
    try {
      const { activityAPI } = await import('../api')
      const response = await activityAPI.getByUserId(currentUser.value.id)
      // 轉換後端格式
      return response.data.map(record => ({
        id: record.id,
        type: record.type,
        title: record.title,
        points: Math.abs(record.points),
        date: record.createdAt
      }))
    } catch (error) {
      console.error('Failed to fetch activity records:', error)
      return []
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
    authLoading,
    userPoints,
    userStats,
    fetchUserStats,
    cartItems,
    cartItemCount,
    addToCart,
    removeFromCart,
    updateCartItemQuantity,
    clearCart,
    checkoutItems,
    setCheckoutItems,
    clearCheckoutItems,
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
    activityRecords,
    addActivityRecord,
    fetchActivityRecords,
  }
})