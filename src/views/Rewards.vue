<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <h1 class="text-4xl font-bold mb-12 text-light-text dark:text-dark-text">禮品兌換</h1>

    <!-- Filter & Sort -->
    <div class="flex flex-col sm:flex-row gap-4 mb-8">
      <input type="text" placeholder="搜尋禮品..." 
        v-model="searchQuery"
        class="flex-1 px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border text-light-text dark:text-dark-text placeholder-light-text-secondary focus:outline-none focus:ring-2 focus:ring-primary-purple">
      
      <select v-model="selectedCategory"
        class="px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border text-light-text dark:text-dark-text focus:outline-none focus:ring-2 focus:ring-primary-purple">
        <option value="">全部分類</option>
        <option value="🧳 商務奢雅">🧳 商務奢雅</option>
        <option value="🕯 精品生活">🕯 精品生活</option>
        <option value="🌿 永續綠生活">🌿 永續綠生活</option>
        <option value="☕ 社企手作">☕ 社企手作</option>
        <option value="🎄 節慶限定">🎄 節慶限定</option>
      </select>

      <select v-model="selectedLevel"
        class="px-4 py-2 rounded-full bg-light-bg dark:bg-dark-bg border border-light-border dark:border-dark-border text-light-text dark:text-dark-text focus:outline-none focus:ring-2 focus:ring-primary-purple">
        <option value="">全部等級</option>
        <option value="探索者">🌿 探索者</option>
        <option value="創造者">💠 創造者</option>
        <option value="先行者">💎 先行者</option>
        <option value="閃耀者">👑 閃耀者</option>
        <option value="全等級">全等級</option>
      </select>
    </div>

    <!-- Rewards Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="reward in filteredRewards" :key="reward.id"
        class="rounded-2xl bg-light-card dark:bg-dark-card border border-light-border dark:border-dark-border overflow-hidden hover:shadow-glow transition-all duration-300 group">
        
        <!-- Emoji Display -->
        <div class="relative overflow-hidden h-48 bg-gradient-to-br from-primary-purple/10 to-primary-blue/10 flex items-center justify-center">
          <div class="text-8xl group-hover:scale-110 transition-transform duration-300">
            {{ reward.emoji }}
          </div>
          <div class="absolute top-3 right-3 px-3 py-1 rounded-full bg-primary-purple text-white text-xs font-semibold">
            {{ reward.level }}
          </div>
        </div>

        <!-- Content -->
        <div class="p-6">
          <div class="flex gap-2 mb-3 flex-wrap">
            <span class="px-2 py-1 rounded-full bg-primary-blue/10 text-primary-blue text-xs">{{ reward.category }}</span>
            <span v-for="tag in reward.tags.slice(0, 2)" :key="tag" 
              class="px-2 py-1 rounded-full bg-light-bg dark:bg-dark-bg text-light-text-secondary dark:text-dark-text-secondary text-xs">
              {{ tag }}
            </span>
          </div>

          <h3 class="text-lg font-bold text-light-text dark:text-dark-text mb-2">{{ reward.name }}</h3>
          <p class="text-sm text-light-text-secondary dark:text-dark-text-secondary mb-4">{{ reward.description }}</p>

          <div class="flex items-center justify-between mb-4">
            <span class="text-2xl font-bold text-primary-purple">{{ reward.points }} 積分</span>
            <span class="text-xs text-light-text-secondary dark:text-dark-text-secondary">剩餘: {{ reward.stock }}</span>
          </div>

          <button class="w-full px-4 py-2 rounded-full bg-gradient-to-r from-primary-purple to-primary-blue text-white font-semibold hover:shadow-glow-lg transition-all duration-300 transform hover:scale-105">
            立即兌換
          </button>
        </div>
      </div>
    </div>

    <!-- Empty State -->
    <div v-if="filteredRewards.length === 0" class="text-center py-20">
      <p class="text-2xl text-light-text-secondary dark:text-dark-text-secondary mb-4">😢</p>
      <p class="text-light-text-secondary dark:text-dark-text-secondary">找不到符合條件的禮品</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const searchQuery = ref('')
const selectedCategory = ref('')
const selectedLevel = ref('')

// 禮品資料
const rewards = ref([
  { 
    id: 1, 
    name: 'LAMY 鋼筆禮盒', 
    emoji: '✒️', 
    points: 3500, 
    category: '🧳 商務奢雅', 
    level: '先行者', 
    tags: ['精緻禮品', '商務'],
    description: '德國工藝，書寫流暢，專業商務人士首選',
    stock: 15
  },
  { 
    id: 2, 
    name: 'Jo Malone 香氛蠟燭', 
    emoji: '🕯️', 
    points: 2800, 
    category: '🕯 精品生活', 
    level: '創造者', 
    tags: ['品牌質感', '香氛'],
    description: '英倫經典香氛，為空間增添優雅氣息',
    stock: 20
  },
  { 
    id: 3, 
    name: '環保再生後背包', 
    emoji: '🎒', 
    points: 1200, 
    category: '🌿 永續綠生活', 
    level: '探索者', 
    tags: ['ESG', '永續'],
    description: '100% 回收材質，時尚與環保兼具',
    stock: 50
  },
  { 
    id: 4, 
    name: '公益咖啡禮盒', 
    emoji: '☕', 
    points: 500, 
    category: '☕ 社企手作', 
    level: '全等級', 
    tags: ['CSR', '社企合作'],
    description: '支持弱勢團體，每份銷售回饋社會',
    stock: 100
  },
  { 
    id: 5, 
    name: 'Moleskine 筆記本', 
    emoji: '📔', 
    points: 1500, 
    category: '🧳 商務奢雅', 
    level: '創造者', 
    tags: ['精緻禮品', '文具'],
    description: '義大利經典筆記本，創意工作者最愛',
    stock: 30
  },
  { 
    id: 6, 
    name: 'KINTO 馬克杯組', 
    emoji: '☕', 
    points: 800, 
    category: '🕯 精品生活', 
    level: '探索者', 
    tags: ['品牌質感', '生活'],
    description: '日本設計美學，提升品味生活',
    stock: 45
  },
  { 
    id: 7, 
    name: 'Nespresso 膠囊組', 
    emoji: '☕', 
    points: 1800, 
    category: '🕯 精品生活', 
    level: '創造者', 
    tags: ['品牌質感', '咖啡'],
    description: '精選咖啡風味，在家享受咖啡館品質',
    stock: 35
  },
  { 
    id: 8, 
    name: '竹製餐具禮盒', 
    emoji: '🥢', 
    points: 600, 
    category: '🌿 永續綠生活', 
    level: '探索者', 
    tags: ['環保', '永續'],
    description: '天然竹材製作，減塑愛地球',
    stock: 60
  },
  { 
    id: 9, 
    name: '社企手作布包', 
    emoji: '👜', 
    points: 450, 
    category: '☕ 社企手作', 
    level: '全等級', 
    tags: ['CSR', '公益'],
    description: '手工縫製，支持身心障礙者就業',
    stock: 80
  },
  { 
    id: 10, 
    name: '太陽能行動電源', 
    emoji: '🔋', 
    points: 2200, 
    category: '🌿 永續綠生活', 
    level: '創造者', 
    tags: ['ESG', '綠能'],
    description: '綠能科技，隨時隨地充電',
    stock: 25
  },
  { 
    id: 11, 
    name: '春節限定禮盒', 
    emoji: '🎁', 
    points: 1500, 
    category: '🎄 節慶限定', 
    level: '全等級', 
    tags: ['限時活動', '節慶'],
    description: '新春特別企劃，送禮自用兩相宜',
    stock: 40
  },
  { 
    id: 12, 
    name: '精品公事包', 
    emoji: '💼', 
    points: 4500, 
    category: '🧳 商務奢雅', 
    level: '閃耀者', 
    tags: ['精緻禮品', '商務'],
    description: '義大利真皮，展現專業風範',
    stock: 10
  },
  { 
    id: 13, 
    name: '有機茶葉禮盒', 
    emoji: '🍵', 
    points: 900, 
    category: '🕯 精品生活', 
    level: '探索者', 
    tags: ['品牌質感', '茶飲'],
    description: '台灣高山茶，健康養生首選',
    stock: 55
  },
  { 
    id: 14, 
    name: '環保購物袋組', 
    emoji: '🛍️', 
    points: 350, 
    category: '🌿 永續綠生活', 
    level: '探索者', 
    tags: ['環保', '永續'],
    description: '可重複使用，時尚又環保',
    stock: 120
  },
  { 
    id: 15, 
    name: '聖誕香氛組合', 
    emoji: '🎄', 
    points: 2000, 
    category: '🎄 節慶限定', 
    level: '全等級', 
    tags: ['限時活動', '香氛'],
    description: '聖誕特調香氛，營造溫馨氛圍',
    stock: 30
  },
])

const filteredRewards = computed(() => {
  return rewards.value.filter(r => {
    const matchSearch = r.name.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchCategory = !selectedCategory.value || r.category === selectedCategory.value
    const matchLevel = !selectedLevel.value || r.level === selectedLevel.value
    return matchSearch && matchCategory && matchLevel
  })
})
</script>