import { defineStore } from 'pinia'
import { ref } from 'vue'
import { mockUsers } from '../mock.js'
import { useAuthStore, USE_BACKEND_API } from './useAuthStore'
import { useUIStore } from './useUIStore'

export const useActivityStore = defineStore('activity', () => {
  const activityRecords = ref([])

  const userStats = ref({
    tasksCompleted: 0,
    consecutiveDays: 0,
    totalLogins: 0,
    rewardsRedeemed: 0,
    friendsInvited: 0
  })

  const addActivityRecord = (record) => {
    const newRecord = {
      id: Date.now(),
      ...record,
      date: new Date().toISOString()
    }
    activityRecords.value.unshift(newRecord)
    if (activityRecords.value.length > 50) {
      activityRecords.value = activityRecords.value.slice(0, 50)
    }
  }

  const fetchUserStats = async () => {
    const authStore = useAuthStore()
    if (!authStore.currentUser) return

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

    try {
      const { userAPI } = await import('../api')
      const response = await userAPI.getStats(authStore.currentUser.id)
      userStats.value = response.data
    } catch (error) {
      console.error('Failed to fetch user stats:', error)
    }
  }

  const fetchActivityRecords = async () => {
    const authStore = useAuthStore()
    if (!authStore.currentUser) return []

    if (!USE_BACKEND_API) {
      return []
    }

    try {
      const { activityAPI } = await import('../api')
      const response = await activityAPI.getByUserId(authStore.currentUser.id)
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

  const completeTask = async (taskId, taskTitle = '任務', points = 0) => {
    const authStore = useAuthStore()
    const uiStore = useUIStore()
    if (!authStore.currentUser) return { success: false, message: '請先登入' }

    if (!USE_BACKEND_API) {
      authStore.userPoints.upgradePoints += 50
      authStore.userPoints.rewardPoints += 50
      if (uiStore.notificationSettings.points) {
        uiStore.addNotification({
          type: 'points',
          title: '積分獲得',
          description: `完成「${taskTitle}」獲得 +50 積分`,
          link: '/profile'
        })
      }
      return { success: true, message: '任務完成！' }
    }

    try {
      const { taskAPI } = await import('../api')
      const response = await taskAPI.complete(taskId, authStore.currentUser.id)
      const oldPoints = authStore.userPoints.upgradePoints
      const oldLevel = authStore.currentLevel

      // 更新積分
      authStore.userPoints = {
        upgradePoints: response.data.upgradePoints,
        rewardPoints: response.data.rewardPoints
      }

      // 檢查是否有升級
      const newLevel = response.data.level || response.data.newLevel
      if (newLevel && newLevel !== oldLevel) {
        authStore.currentLevel = newLevel
        // 更新 localStorage
        const saved = localStorage.getItem('auth')
        if (saved) {
          try {
            const authData = JSON.parse(saved)
            authData.user.level = newLevel
            localStorage.setItem('auth', JSON.stringify(authData))
          } catch (e) {
            console.error('Failed to update auth localStorage', e)
          }
        }
        // 添加升級通知
        if (uiStore.notificationSettings.levelUp) {
          const levelNames = {
            EXPLORER: 'Lv1 Explorer 探索者',
            CREATOR: 'Lv2 Creator 創造者',
            VISIONARY: 'Lv3 Visionary 先驅者',
            LUMINARY: 'Lv4 Luminary 閃耀者'
          }
          uiStore.addNotification({
            type: 'levelUp',
            title: '等級提升！',
            description: `恭喜您升級至 ${levelNames[newLevel] || newLevel}`,
            link: '/profile'
          })
        }
      }

      // 積分通知
      const earnedPoints = response.data.upgradePoints - oldPoints
      if (uiStore.notificationSettings.points && earnedPoints > 0) {
        uiStore.addNotification({
          type: 'points',
          title: '積分獲得',
          description: `完成「${taskTitle}」獲得 +${earnedPoints} 積分`,
          link: '/profile'
        })
      }
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '完成任務失敗' }
    }
  }

  const redeemGift = async (giftId, giftName = '禮品') => {
    const authStore = useAuthStore()
    const uiStore = useUIStore()
    if (!authStore.currentUser) return { success: false, message: '請先登入' }

    if (!USE_BACKEND_API) {
      if (uiStore.notificationSettings.shipping) {
        uiStore.addNotification({
          type: 'shipping',
          title: '出貨進度',
          description: `「${giftName}」訂單確認中`,
          link: '/history?tab=orders'
        })
      }
      return { success: true, message: '兌換成功！' }
    }

    try {
      const { giftAPI } = await import('../api')
      const response = await giftAPI.redeem(giftId, authStore.currentUser.id)
      await authStore.checkAuth()
      if (uiStore.notificationSettings.shipping) {
        uiStore.addNotification({
          type: 'shipping',
          title: '出貨進度',
          description: `「${giftName}」訂單確認中`,
          link: '/history?tab=orders'
        })
      }
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '兌換失敗' }
    }
  }

  const resetUser = async () => {
    const authStore = useAuthStore()
    if (!authStore.currentUser) return { success: false, message: '請先登入' }

    if (!USE_BACKEND_API) {
      const user = mockUsers[1]
      authStore.currentUser = user
      authStore.currentLevel = user.level
      authStore.userPoints = {
        upgradePoints: user.levelPoints,
        rewardPoints: user.rewardPoints
      }
      localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
      return { success: true, message: '資料已重置' }
    }

    try {
      const { userAPI } = await import('../api')
      const response = await userAPI.reset(authStore.currentUser.id)
      const user = response.data
      authStore.currentUser = user
      authStore.currentLevel = user.level || 'EXPLORER'
      authStore.userPoints = {
        upgradePoints: user.upgradePoints || 0,
        rewardPoints: user.rewardPoints || 0
      }
      return { success: true, message: '資料已重置' }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '重置失敗' }
    }
  }

  return {
    activityRecords,
    userStats,
    addActivityRecord,
    fetchUserStats,
    fetchActivityRecords,
    completeTask,
    redeemGift,
    resetUser
  }
})
