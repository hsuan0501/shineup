import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { mockUsers } from '../mock.js'
import { useUIStore } from './useUIStore'

// 設定是否使用後端 API（true = 連後端，false = 純前端 mock）
export const USE_BACKEND_API = true

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

export const useAuthStore = defineStore('auth', () => {
  const currentUser = ref(initialAuth.user)
  const currentLevel = ref(initialAuth.level)
  const isAuthenticated = ref(initialAuth.authenticated)
  const authLoading = ref(true)
  const userPoints = ref(initialAuth.points)

  // 是否為管理員
  const isAdmin = computed(() => currentUser.value?.admin === true)

  const login = async (credentials) => {
    const uiStore = useUIStore()

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
        // Mock 模式下模擬每日登入獎勵通知
        if (uiStore.notificationSettings.points) {
          uiStore.addNotification({
            type: 'points',
            title: '每日登入獎勵',
            description: '完成「每日登入」獲得 +10 積分',
            link: '/profile'
          })
        }
        return { success: true, user }
      }
      return { success: false, message: '帳號或密碼錯誤' }
    }

    try {
      const { authAPI, userAPI } = await import('../api')
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

      // 記錄登入並獲取積分
      const loginTime = new Date().toISOString()
      try {
        const loginResult = await userAPI.recordLogin(user.id)
        if (loginResult.data) {
          const earnedPoints = loginResult.data.earnedPoints || 0
          // 更新積分
          userPoints.value = {
            upgradePoints: loginResult.data.upgradePoints || userPoints.value.upgradePoints,
            rewardPoints: loginResult.data.rewardPoints || userPoints.value.rewardPoints
          }
          // 記錄登入時間到 currentUser
          currentUser.value = { ...currentUser.value, lastLoginAt: loginTime }
          // 更新 localStorage
          const saved = localStorage.getItem('auth')
          if (saved) {
            try {
              const authData = JSON.parse(saved)
              authData.user.lastLoginAt = loginTime
              localStorage.setItem('auth', JSON.stringify(authData))
            } catch (e) {
              console.error('Failed to update lastLoginAt in localStorage', e)
            }
          }
          // 只有獲得積分時才顯示通知
          const settings = uiStore.notificationSettings || {}
          if (earnedPoints > 0 && settings.points !== false) {
            uiStore.addNotification({
              type: 'points',
              title: '每日登入獎勵',
              description: `完成「每日登入」獲得 +${earnedPoints} 積分`,
              link: '/profile'
            })
          }
        }
      } catch (loginError) {
        console.error('Record login failed:', loginError)
      }

      return { success: true, user }
    } catch (error) {
      console.error('Login failed:', error)
      return { success: false, message: error.response?.data?.message || '登入失敗' }
    }
  }

  const register = async (userData) => {
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
        localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
      } catch (error) {
        console.error('Token invalid:', error)
        logout()
      }
    }
    authLoading.value = false
  }

  return {
    currentUser,
    currentLevel,
    isAuthenticated,
    isAdmin,
    authLoading,
    userPoints,
    login,
    register,
    logout,
    checkAuth
  }
})
