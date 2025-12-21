<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
        <div class="text-center">
            <div class="w-16 h-16 mx-auto mb-4 bg-[#06C755] rounded-full flex items-center justify-center animate-pulse">
                <svg class="w-8 h-8 text-white" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M19.365 9.863c.349 0 .63.285.63.631 0 .345-.281.63-.63.63H17.61v1.125h1.755c.349 0 .63.283.63.63 0 .344-.281.629-.63.629h-2.386c-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63h2.386c.349 0 .63.285.63.63 0 .349-.281.63-.63.63H17.61v1.125h1.755zm-3.855 3.016c0 .27-.174.51-.432.596-.064.021-.133.031-.199.031-.211 0-.391-.09-.51-.25l-2.443-3.317v2.94c0 .344-.279.629-.631.629-.346 0-.626-.285-.626-.629V8.108c0-.27.173-.51.43-.595.06-.023.136-.033.194-.033.195 0 .375.104.495.254l2.462 3.33V8.108c0-.345.282-.63.63-.63.345 0 .63.285.63.63v4.771zm-5.741 0c0 .344-.282.629-.631.629-.345 0-.627-.285-.627-.629V8.108c0-.345.282-.63.627-.63.349 0 .631.285.631.63v4.771zm-2.466.629H4.917c-.345 0-.63-.285-.63-.629V8.108c0-.345.285-.63.63-.63.348 0 .63.285.63.63v4.141h1.756c.348 0 .629.283.629.63 0 .344-.281.629-.629.629M24 10.314C24 4.943 18.615.572 12 .572S0 4.943 0 10.314c0 4.811 4.27 8.842 10.035 9.608.391.082.923.258 1.058.59.12.301.079.766.038 1.08l-.164 1.02c-.045.301-.24 1.186 1.049.645 1.291-.539 6.916-4.078 9.436-6.975C23.176 14.393 24 12.458 24 10.314"/>
                </svg>
            </div>
            <h2 class="text-xl font-bold text-gray-800 dark:text-white mb-2">{{ statusMessage }}</h2>
            <p class="text-gray-500 dark:text-gray-400">{{ statusDetail }}</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from '@/store'
import axios from 'axios'

const router = useRouter()
const route = useRoute()
const store = useStore()

const statusMessage = ref('LINE 處理中...')
const statusDetail = ref('請稍候')

onMounted(async () => {
    const code = route.query.code
    const state = route.query.state
    const error = route.query.error

    if (error) {
        statusMessage.value = '操作已取消'
        statusDetail.value = '您已取消 LINE 授權'
        setTimeout(() => router.push('/'), 2000)
        return
    }

    if (!code) {
        statusMessage.value = '操作失敗'
        statusDetail.value = '缺少授權碼'
        setTimeout(() => router.push('/'), 2000)
        return
    }

    // 判斷是綁定還是登入
    const isBind = state === 'bind'

    if (isBind) {
        // 綁定模式
        statusMessage.value = 'LINE 綁定中...'
        await handleBind(code)
    } else {
        // 登入模式
        statusMessage.value = 'LINE 登入中...'
        await handleLogin(code)
    }
})

// 處理登入
const handleLogin = async (code) => {
    try {
        const response = await axios.post('http://localhost:8080/api/auth/line/callback', { code })

        if (response.data.success) {
            const user = response.data.user
            localStorage.setItem('token', response.data.token)
            localStorage.setItem('auth', JSON.stringify({ isAuthenticated: true, user }))
            store.currentUser = user
            store.isAuthenticated = true
            store.currentLevel = user.level || 'EXPLORER'
            store.userPoints = {
                upgradePoints: user.upgradePoints || 0,
                rewardPoints: user.rewardPoints || 0
            }

            statusMessage.value = '登入成功！'
            statusDetail.value = `歡迎，${user.name}`
            store.showToast('LINE 登入成功！', 'success')

            setTimeout(() => router.push('/'), 1500)
        } else {
            statusMessage.value = '登入失敗'
            statusDetail.value = response.data.message || '請稍後再試'
            setTimeout(() => router.push('/'), 2000)
        }
    } catch (err) {
        console.error('LINE login error:', err)
        statusMessage.value = '登入失敗'
        statusDetail.value = err.response?.data?.message || '請稍後再試'
        setTimeout(() => router.push('/'), 2000)
    }
}

// 處理綁定
const handleBind = async (code) => {
    try {
        const token = localStorage.getItem('token')
        if (!token) {
            statusMessage.value = '綁定失敗'
            statusDetail.value = '請先登入'
            setTimeout(() => router.push('/'), 2000)
            return
        }

        const response = await axios.post('http://localhost:8080/api/auth/line/bind', { code }, {
            headers: { Authorization: `Bearer ${token}` }
        })

        if (response.data.success) {
            // 更新 store 中的 lineId
            if (store.currentUser) {
                store.currentUser.lineId = response.data.lineId
            }

            statusMessage.value = '綁定成功！'
            statusDetail.value = 'LINE 帳號已成功綁定'
            store.showToast('LINE 綁定成功！', 'success')

            setTimeout(() => router.push('/settings'), 1500)
        } else {
            statusMessage.value = '綁定失敗'
            statusDetail.value = response.data.message || '請稍後再試'
            setTimeout(() => router.push('/settings'), 2000)
        }
    } catch (err) {
        console.error('LINE bind error:', err)
        statusMessage.value = '綁定失敗'
        statusDetail.value = err.response?.data?.message || '請稍後再試'
        setTimeout(() => router.push('/settings'), 2000)
    }
}
</script>
