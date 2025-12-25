import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useCartStore = defineStore('cart', () => {
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

  const checkoutItems = ref([])

  const cartItemCount = computed(() => {
    return cartItems.value.reduce((total, item) => total + item.quantity, 0)
  })

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

  const setCheckoutItems = (items) => {
    checkoutItems.value = [...items]
  }

  const clearCheckoutItems = () => {
    checkoutItems.value = []
  }

  return {
    cartItems,
    checkoutItems,
    cartItemCount,
    addToCart,
    removeFromCart,
    updateCartItemQuantity,
    clearCart,
    setCheckoutItems,
    clearCheckoutItems
  }
})
