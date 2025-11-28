import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useStore = defineStore('app', () => {
  const isDark = ref(false)
  const currentUser = ref(null)
  const currentLevel = ref('EXPLORER')
  const isAuthenticated = ref(false)

  // 搜尋狀態
  const searchQuery = ref('')

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
  const login = (user) => {
    isAuthenticated.value = true
    currentUser.value = user
    currentLevel.value = user.level || 'EXPLORER'
  }

  const logout = () => {
    isAuthenticated.value = false
    currentUser.value = null
    currentLevel.value = 'EXPLORER'
    localStorage.removeItem('auth')
  }

  const checkAuth = () => {
    const authData = localStorage.getItem('auth')
    if (authData) {
      try {
        const parsed = JSON.parse(authData)
        if (parsed.isAuthenticated && parsed.user) {
          login(parsed.user)
        }
      } catch (e) {
        console.error('Failed to parse auth data', e)
        localStorage.removeItem('auth')
      }
    }
  }

  // 搜尋功能
  const setSearchQuery = (query) => {
    searchQuery.value = query
  }

  const clearSearch = () => {
    searchQuery.value = ''
  }

  return {
    isDark,
    currentUser,
    currentLevel,
    isAuthenticated,
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
    logout,
    checkAuth,
    searchQuery,
    setSearchQuery,
    clearSearch,
  }
})