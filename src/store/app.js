import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useStore = defineStore('app', () => {
  const isDark = ref(false)
  const currentUser = ref(null)
  const currentLevel = ref('EXPLORER')
  
  // 購物車狀態管理
  const cartItems = ref([
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
      id: 8,
      title: 'SUCCULAND 多肉植物',
      description: '市價NT$590',
      image: '/images/gifts/gift-8.jpg',
      points: 200,
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
    cartItems,
    cartItemCount,
    addToCart,
    removeFromCart,
    updateCartItemQuantity,
    toggleDarkMode,
    initDarkMode,
  }
})