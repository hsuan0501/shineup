<template>
  <div class="fixed top-24 right-6 z-[100] flex flex-col gap-3 pointer-events-none">
    <TransitionGroup name="toast">
      <div v-for="toast in store.toasts" :key="toast.id"
        :class="[
          'pointer-events-auto px-5 py-3.5 rounded-lg shadow-2xl backdrop-blur-xl border',
          'flex items-center gap-3 min-w-[320px] max-w-md',
          'transform transition-all duration-300',
          getToastClass(toast.type)
        ]">
        <!-- Icon -->
        <div class="flex-shrink-0">
          <svg v-if="toast.type === 'success'" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
              clip-rule="evenodd" />
          </svg>
          <svg v-else-if="toast.type === 'error'" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
              clip-rule="evenodd" />
          </svg>
          <svg v-else-if="toast.type === 'info'" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
              d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
              clip-rule="evenodd" />
          </svg>
          <svg v-else class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd"
              d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
              clip-rule="evenodd" />
          </svg>
        </div>

        <!-- Message -->
        <div class="flex-1 text-sm font-medium">
          {{ toast.message }}
        </div>

        <!-- Close Button -->
        <button @click="store.removeToast(toast.id)"
          class="flex-shrink-0 w-5 h-5 flex items-center justify-center rounded-full hover:bg-black/10 dark:hover:bg-white/10 transition-colors">
          <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </TransitionGroup>
  </div>
</template>

<script setup>
import { useStore } from '../store/app'

const store = useStore()

const getToastClass = (type) => {
  const classes = {
    'success': 'bg-emerald-50/95 dark:bg-emerald-900/95 border-emerald-200/50 dark:border-emerald-700/50 text-emerald-800 dark:text-emerald-100',
    'error': 'bg-red-50/95 dark:bg-red-900/95 border-red-200/50 dark:border-red-700/50 text-red-800 dark:text-red-100',
    'info': 'bg-blue-50/95 dark:bg-blue-900/95 border-blue-200/50 dark:border-blue-700/50 text-blue-800 dark:text-blue-100',
    'wishlist-add': 'bg-amber-50/95 dark:bg-amber-900/95 border-amber-200/50 dark:border-amber-700/50 text-amber-800 dark:text-amber-100',
    'wishlist-remove': 'bg-purple-50/95 dark:bg-purple-900/95 border-purple-200/50 dark:border-purple-700/50 text-purple-800 dark:text-purple-100'
  }
  return classes[type] || classes['info']
}
</script>

<style scoped>
.toast-enter-active,
.toast-leave-active {
  transition: all 0.3s ease;
}

.toast-enter-from {
  opacity: 0;
  transform: translateX(100px) scale(0.9);
}

.toast-leave-to {
  opacity: 0;
  transform: translateX(50px) scale(0.95);
}

.toast-move {
  transition: transform 0.3s ease;
}
</style>
