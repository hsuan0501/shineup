export { useAuthStore, USE_BACKEND_API } from './useAuthStore'
export { useCartStore } from './useCartStore'
export { useWishlistStore } from './useWishlistStore'
export { useActivityStore } from './useActivityStore'
export { useUIStore } from './useUIStore'

// 向後兼容：組合所有 store 的 useStore
import { reactive } from 'vue'
import { useAuthStore } from './useAuthStore'
import { useCartStore } from './useCartStore'
import { useWishlistStore } from './useWishlistStore'
import { useActivityStore } from './useActivityStore'
import { useUIStore } from './useUIStore'

export const useStore = () => {
  const auth = useAuthStore()
  const cart = useCartStore()
  const wishlist = useWishlistStore()
  const activity = useActivityStore()
  const ui = useUIStore()

  // 創建代理物件，保持響應式讀寫
  return reactive({
    // Auth - getter/setter
    get currentUser() { return auth.currentUser },
    set currentUser(v) { auth.currentUser = v },
    get currentLevel() { return auth.currentLevel },
    set currentLevel(v) { auth.currentLevel = v },
    get isAuthenticated() { return auth.isAuthenticated },
    set isAuthenticated(v) { auth.isAuthenticated = v },
    get authLoading() { return auth.authLoading },
    get isAdmin() { return auth.isAdmin },
    get userPoints() { return auth.userPoints },
    set userPoints(v) { auth.userPoints = v },

    // Cart
    get cartItems() { return cart.cartItems },
    get checkoutItems() { return cart.checkoutItems },
    get cartItemCount() { return cart.cartItemCount },

    // Wishlist
    get wishlistItems() { return wishlist.wishlistItems },

    // Activity
    get activityRecords() { return activity.activityRecords },
    get userStats() { return activity.userStats },

    // UI
    get isDark() { return ui.isDark },
    get searchQuery() { return ui.searchQuery },
    set searchQuery(v) { ui.searchQuery = v },
    get toasts() { return ui.toasts },
    get notificationSettings() { return ui.notificationSettings.value },
    get notificationList() { return ui.notificationList.value },
    get showNotificationPanel() { return ui.showNotificationPanel },
    set showNotificationPanel(v) { ui.showNotificationPanel = v },
    get orderVersion() { return ui.orderVersion },

    // Auth methods
    login: auth.login,
    register: auth.register,
    logout: auth.logout,
    checkAuth: auth.checkAuth,

    // Cart methods
    addToCart: cart.addToCart,
    removeFromCart: cart.removeFromCart,
    updateCartItemQuantity: cart.updateCartItemQuantity,
    clearCart: cart.clearCart,
    setCheckoutItems: cart.setCheckoutItems,
    clearCheckoutItems: cart.clearCheckoutItems,

    // Wishlist methods
    addToWishlist: wishlist.addToWishlist,
    removeFromWishlist: wishlist.removeFromWishlist,
    isInWishlist: wishlist.isInWishlist,
    toggleWishlist: wishlist.toggleWishlist,
    loadWishlist: wishlist.loadWishlist,

    // Activity methods
    addActivityRecord: activity.addActivityRecord,
    fetchActivityRecords: activity.fetchActivityRecords,
    fetchUserStats: activity.fetchUserStats,
    completeTask: activity.completeTask,
    redeemGift: activity.redeemGift,
    resetUser: activity.resetUser,

    // UI methods
    toggleDarkMode: ui.toggleDarkMode,
    initDarkMode: ui.initDarkMode,
    setSearchQuery: ui.setSearchQuery,
    clearSearch: ui.clearSearch,
    showToast: ui.showToast,
    removeToast: ui.removeToast,
    toggleNotificationSetting: ui.toggleNotificationSetting,
    addNotification: ui.addNotification,
    markNotificationRead: ui.markNotificationRead,
    clearNotifications: ui.clearNotifications,
    triggerOrderRefresh: ui.triggerOrderRefresh
  })
}
