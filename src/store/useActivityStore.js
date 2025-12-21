import { defineStore } from 'pinia'
import { ref } from 'vue'
import { mockUsers } from '../mock.js'
import { useAuthStore, USE_BACKEND_API } from './useAuthStore'

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

  const completeTask = async (taskId) => {
    const authStore = useAuthStore()
    if (!authStore.currentUser) return { success: false, message: '請先登入' }

    if (!USE_BACKEND_API) {
      authStore.userPoints.upgradePoints += 50
      authStore.userPoints.rewardPoints += 50
      return { success: true, message: '任務完成！' }
    }

    try {
      const { taskAPI } = await import('../api')
      const response = await taskAPI.complete(taskId, authStore.currentUser.id)
      authStore.userPoints = {
        upgradePoints: response.data.upgradePoints,
        rewardPoints: response.data.rewardPoints
      }
      return { success: true, message: response.data.message }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '完成任務失敗' }
    }
  }

  const redeemGift = async (giftId) => {
    const authStore = useAuthStore()
    if (!authStore.currentUser) return { success: false, message: '請先登入' }

    if (!USE_BACKEND_API) {
      return { success: true, message: '兌換成功！' }
    }

    try {
      const { giftAPI } = await import('../api')
      const response = await giftAPI.redeem(giftId, authStore.currentUser.id)
      await authStore.checkAuth()
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
