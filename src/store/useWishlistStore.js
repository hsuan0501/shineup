import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useWishlistStore = defineStore('wishlist', () => {
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

  const addToWishlist = (gift) => {
    const exists = wishlistItems.value.find(item => item.id === gift.id)
    if (!exists) {
      wishlistItems.value.push(gift)
      localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
    }
  }

  const removeFromWishlist = (giftId) => {
    wishlistItems.value = wishlistItems.value.filter(item => item.id !== giftId)
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
        if (parsed && parsed.length > 0) {
          wishlistItems.value = parsed
        } else {
          localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
        }
      } catch (e) {
        console.error('Failed to load wishlist', e)
        localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
      }
    } else {
      localStorage.setItem('wishlist', JSON.stringify(wishlistItems.value))
    }
  }

  return {
    wishlistItems,
    addToWishlist,
    removeFromWishlist,
    isInWishlist,
    toggleWishlist,
    loadWishlist
  }
})
