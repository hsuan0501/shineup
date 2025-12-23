import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 請求攔截器 - 自動加上 Token
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 認證 API
export const authAPI = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  getMe: () => api.get('/auth/me')
}

// 用戶 API
export const userAPI = {
  getAll: () => api.get('/users'),
  getById: (id) => api.get(`/users/${id}`),
  reset: (id) => api.post(`/users/${id}/reset`),
  getStats: (id) => api.get(`/users/${id}/stats`),
  recordLogin: (id) => api.post(`/users/${id}/record-login`),
  toggleStatus: (id) => api.put(`/users/${id}/toggle-status`),
  getLoginHistory: (id) => api.get(`/users/${id}/login-history`),
  getAllLoginHistory: () => api.get('/users/login-history')
}

// 任務 API
export const taskAPI = {
  getAll: () => api.get('/tasks'),
  getActive: () => api.get('/tasks/active'),
  getById: (id) => api.get(`/tasks/${id}`),
  complete: (taskId, userId) => api.post(`/tasks/${taskId}/complete`, { userId })
}

// 禮品 API
export const giftAPI = {
  getAll: () => api.get('/gifts'),
  getById: (id) => api.get(`/gifts/${id}`),
  redeem: (giftId, userId) => api.post(`/gifts/${giftId}/redeem`, { userId })
}

// 訂單 API
export const orderAPI = {
  getAll: () => api.get('/orders'),
  getByUserId: (userId) => api.get(`/orders/user/${userId}`),
  createBatch: (userId, items, deliveryInfo) => api.post('/orders/batch', { userId, items, ...deliveryInfo }),
  updateStatus: (orderId, status) => api.put(`/orders/${orderId}/status?status=${status}`)
}

// 活動紀錄 API
export const activityAPI = {
  getByUserId: (userId) => api.get(`/activities/user/${userId}`)
}

// AI 客服 API
export const chatAPI = {
  send: (message) => api.post('/chat', { message }),
  // 管理員用
  getReplies: () => api.get('/chat/replies'),
  saveReply: (reply) => api.post('/chat/replies', reply),
  saveAllReplies: (replies) => api.put('/chat/replies', replies),
  deleteReply: (id) => api.delete(`/chat/replies/${id}`)
}

export default api
