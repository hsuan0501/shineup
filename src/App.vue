<template>
  <div :class="isDark ? 'dark' : ''" class="w-full min-h-screen overflow-x-hidden" style="margin: 0; padding: 0;">
    <!-- Whitesmoke background for blob effect -->
    <div class="fixed inset-0 -z-50 bg-[whitesmoke] dark:bg-gray-900"></div>

    <div class="flex flex-col min-h-screen w-full bg-transparent dark:bg-transparent bg-cover bg-fixed bg-no-repeat">
      <!-- NavBar -->
      <NavBar />

      <!-- Main Content -->
      <main class="flex-1 pt-24">
        <RouterView />
      </main>

      <!-- Footer (放在這裡,讓背景連貫) -->
      <Footer />

      <!-- Global Scroll to Top Button -->
      <button @click="scrollToTop" :class="[
        'fixed bottom-6 left-6 z-40 h-14 w-14 flex items-center justify-center rounded-full bg-zinc-100/90 dark:bg-gray-800/90 border border-zinc-200/50 dark:border-gray-600/50 hover:bg-zinc-200 dark:hover:bg-gray-700 hover:scale-105 active:scale-95 transition-all duration-400 ease-out shadow-[0_4px_16px_rgba(0,0,0,0.08)] dark:shadow-[0_4px_16px_rgba(0,0,0,0.3)] hover:shadow-[0_6px_20px_rgba(0,0,0,0.012)] dark:hover:shadow-[0_6px_20px_rgba(0,0,0,0.4)] backdrop-blur-xl backdrop-saturate-150 group',
        { 'blur-sm': isLoginModalOpen }
      ]"
        aria-label="回到頂部">
        <svg class="w-5 h-5 text-zinc-600 dark:text-gray-300 group-hover:text-zinc-800 dark:group-hover:text-white transition-colors duration-300"
          fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
        </svg>
      </button>

      <!-- Global ChatBox -->
      <ChatBox />

      <!-- Global Toast Notifications -->
      <Toast />

      <!-- Reset Password Modal -->
      <ResetPasswordModal v-model="showResetPasswordModal" :token="resetToken" />
    </div>
  </div>
</template>

<script setup>
import { useStore } from '@/store'
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import NavBar from './components/NavBar.vue'
import Footer from './components/Footer.vue'
import ChatBox from './components/ChatBox.vue'
import Toast from './components/Toast.vue'
import ResetPasswordModal from './components/auth/ResetPasswordModal.vue'
import { RouterView } from 'vue-router'

const store = useStore()
const route = useRoute()
const router = useRouter()
const isDark = computed(() => store.isDark)

// Reset Password Modal
const showResetPasswordModal = ref(false)
const resetToken = ref('')

// 監聽路由，當進入 /reset-password 時顯示 Modal
watch(() => route.path, (newPath) => {
  if (newPath === '/reset-password') {
    resetToken.value = route.query.token || ''
    showResetPasswordModal.value = true
    // 導回首頁但保持 Modal 開啟
    router.replace('/')
  }
}, { immediate: true })

// 檢測登入modal是否開啟
const isLoginModalOpen = ref(false)

// 監聽 document 上的 modal 變化
if (typeof document !== 'undefined') {
  const observer = new MutationObserver(() => {
    // 找所有 fixed + inset-0 + z-50 的元素（排除 canvas 和 pointer-events-none）
    const modals = document.querySelectorAll('.fixed')
    let hasModal = false
    modals.forEach(el => {
      if (el.classList.contains('inset-0') &&
          el.classList.contains('z-50') &&
          el.tagName !== 'CANVAS' &&
          !el.classList.contains('pointer-events-none')) {
        hasModal = true
      }
    })
    isLoginModalOpen.value = hasModal
  })
  observer.observe(document.body, { childList: true, subtree: true })
}

// Scroll to Top
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    left: 0,
    behavior: 'smooth'
  })
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
}

onMounted(() => {
  store.initDarkMode()
  store.loadWishlist()
})
</script>
