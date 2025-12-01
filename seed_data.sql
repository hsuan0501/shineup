-- ShineUp History - Seed Data
-- Created: 2024-12-01
-- Sample data for testing and development

-- ============================================================================
-- CLEAR EXISTING DATA (using TRUNCATE to bypass safe mode restrictions)
-- Note: Disable foreign key checks temporarily to allow truncation
-- ============================================================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE reward_redemption;
TRUNCATE TABLE user_task_progress;
TRUNCATE TABLE user_levels;
TRUNCATE TABLE tasks;
TRUNCATE TABLE rewards;
TRUNCATE TABLE users;
TRUNCATE TABLE level_config;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- INSERT level_config DATA (4 levels)
-- ============================================================================
INSERT INTO level_config (id, levelCode, levelName, levelNumber, minPoints, maxPoints, multiplier, color, bgColor, gradientFrom, gradientTo, gradientFromHex, gradientToHex) VALUES
(1, 'EXPLORER', '探索者', 1, 0, 249, 1.0, '#10b981', '#d1fae5', 'from-emerald-300', 'to-emerald-600', '#a7f3d0', '#059669'),
(2, 'CREATOR', '創造者', 2, 250, 749, 1.15, '#06b6d4', '#cffafe', 'from-cyan-300', 'to-cyan-600', '#a5f3fc', '#0891b2'),
(3, 'VISIONARY', '先行者', 3, 750, 1499, 1.4, '#f59e0b', '#fef3c7', 'from-amber-300', 'to-amber-600', '#fcd34d', '#d97706'),
(4, 'LUMINARY', '閃耀者', 4, 1500, 999999, 1.7, '#8b5cf6', '#ede9fe', 'from-purple-300', 'to-purple-600', '#ddd6fe', '#7c3aed');

-- ============================================================================
-- INSERT users DATA
-- ============================================================================
INSERT INTO users (id, name, email, avatar, createdAt, kycVerified, bankAccountLinked) VALUES
(1, 'Matcha', 'matcha@shineup.com', 'https://i.pravatar.cc/150?img=1', '2024-11-01 08:00:00', TRUE, FALSE);

-- ============================================================================
-- INSERT user_levels DATA
-- ============================================================================
INSERT INTO user_levels (id, userId, currentLevel, levelPoints, rewardPoints, lastUpdated) VALUES
(1, 1, 'CREATOR', 700, 600, '2024-12-01 10:00:00');

-- ============================================================================
-- INSERT tasks DATA (30 tasks across 5 categories)
-- ============================================================================
INSERT INTO tasks (id, title, description, details, category, levelRequired, levelPoints, rewardPoints, frequency, icon, image, active) VALUES
-- Daily Tasks (5 tasks)
(1, '每日登入', '每天登入應用程式，領取積分獎勵', '每天打開應用程式登入，即可領取登入獎勵。這是最基礎的日常任務。', 'daily', '全等級', 5, 10, '每日', '📱', NULL, TRUE),
(2, '完成每日閱讀', '閱讀一篇財經文章或教育內容', '花費5分鐘以上閱讀平台上的財經或金融教育文章，開闊視野。', 'daily', '全等級', 8, 15, '每日', '📖', NULL, TRUE),
(3, '設定每日預算', '為今天設定個人預算目標', '透過預算工具設定今天的消費預算上限，養成理財習慣。', 'daily', '全等級', 6, 12, '每日', '💰', NULL, TRUE),
(4, '記錄一筆開支', '在帳本中記錄一項消費', '記錄任何一項日常消費，幫助追蹤開支流向。', 'daily', '全等級', 7, 14, '每日', '📝', NULL, TRUE),
(5, '完成每日簽到', '簽到打卡，保持連續記錄', '每天簽到打卡，維持連續登入記錄。', 'daily', '全等級', 4, 8, '每日', '✅', NULL, TRUE),

-- Financial Education Tasks (6 tasks)
(6, '學習基礎投資', '完成「投資101」課程第一章', '深入學習股票、債券等基本投資知識', 'financial', '全等級', 15, 25, '一次性', '📚', NULL, TRUE),
(7, '理解信用評分', '閱讀並完成信用評分相關測驗', '瞭解什麼影響個人信用評分以及如何改善', 'financial', '全等級', 12, 20, '一次性', '📊', NULL, TRUE),
(8, '掌握保險基礎', '完成保險類型與選擇指南', '學習不同類型保險的用途與重要性', 'financial', 'Lv2+', 18, 30, '一次性', '🛡️', NULL, TRUE),
(9, '稅務規劃初步', '瞭解個人所得稅與節稅方法', '掌握基本稅務知識，規劃合理節稅方案', 'financial', 'Lv2+', 20, 35, '一次性', '📋', NULL, TRUE),
(10, '財務報表解讀', '學習如何閱讀公司財務報表', '掌握如何分析損益表、資產負債表等財務報表', 'financial', 'Lv3+', 25, 40, '一次性', '📈', NULL, TRUE),
(11, '退休計畫基礎', '制定個人退休儲蓄計畫', '規劃長期退休目標與儲蓄策略', 'financial', 'Lv3+', 22, 38, '一次性', '🎯', NULL, TRUE),

-- Investment Tasks (6 tasks)
(12, '研究一支股票', '深度分析一家公司的股票', '選擇一家感興趣的公司，進行基本面分析', 'investment', 'Lv2+', 20, 30, '可重複', '📊', NULL, TRUE),
(13, '追蹤投資組合', '每週更新投資組合表現', '監控現有投資的績效與配置', 'investment', 'Lv2+', 12, 20, '每週最多2個', '💼', NULL, TRUE),
(14, '學習技術分析', '完成K線與技術指標課程', '掌握基本的技術分析工具與方法', 'investment', 'Lv2+', 18, 28, '一次性', '📉', NULL, TRUE),
(15, '評估風險承受度', '完成風險評估問卷', '瞭解自己的風險承受能力與投資風格', 'investment', '全等級', 10, 18, '一次性', '⚖️', NULL, TRUE),
(16, '分散投資策略', '設計一個投資組合分散計畫', '根據自己的目標設計合理的資產配置', 'investment', 'Lv3+', 25, 40, '一次性', '🎲', NULL, TRUE),
(17, '參加投資討論', '在社區討論區分享投資見解', '與其他使用者討論投資策略與經驗', 'investment', '全等級', 8, 15, '每月最多3次', '💬', NULL, TRUE),

-- ESG Tasks (6 tasks)
(18, '瞭解ESG概念', '完成永續投資基礎課程', '認識環境、社會、治理(ESG)的重要性', 'esg', '全等級', 12, 22, '一次性', '🌍', NULL, TRUE),
(19, '評估公司ESG', '分析一家企業的ESG表現', '學習如何評估公司在永續發展的表現', 'esg', 'Lv2+', 15, 25, '可重複', '🌿', NULL, TRUE),
(20, '參與環保倡議', '完成一項環保行動記錄', '進行減碳、回收、省水等環保行動', 'esg', '全等級', 10, 18, '可重複', '♻️', NULL, TRUE),
(21, '支持社會責任', '選擇一家重視社會責任的企業', '研究並支持在社會責任上有貢獻的公司', 'esg', 'Lv2+', 14, 24, '一次性', '🤝', NULL, TRUE),
(22, '綠色消費實踐', '記錄綠色消費行為', '購買環保產品或採取綠色消費方式', 'esg', '全等級', 8, 15, '每月最多3次', '🌱', NULL, TRUE),
(23, '永續投資組合', '建立兼顧獲利與永續的投資組合', '在投資收益和永續目標之間找到平衡', 'esg', 'Lv3+', 22, 36, '一次性', '🌐', NULL, TRUE),

-- Social Tasks (7 tasks)
(24, '邀請朋友加入', '成功邀請1位好友註冊', '透過邀請碼邀請朋友加入ShineUp', 'social', '全等級', 20, 35, '可重複', '👥', NULL, TRUE),
(25, '分享學習成果', '在社交媒體分享你的進度', '分享你的等級提升或任務完成', 'social', '全等級', 10, 18, '可重複', '📢', NULL, TRUE),
(26, '參與社區討論', '在討論版回覆3個提問', '積極參與社區，幫助其他使用者', 'social', '全等級', 12, 20, '每週最多2個', '💬', NULL, TRUE),
(27, '完成小組挑戰', '與其他使用者組成小組完成挑戰', '與志同道合的人一起達成目標', 'social', 'Lv2+', 25, 40, '可重複', '🏆', NULL, TRUE),
(28, '分享理財心得', '發表一篇理財經驗分享文章', '與社區分享你的理財故事與心得', 'social', 'Lv2+', 18, 30, '每月最多3次', '✍️', NULL, TRUE),
(29, '組織讀書會', '發起或主持一場線上讀書會', '邀請社區成員討論理財相關書籍', 'social', 'Lv3+', 30, 50, '可重複', '📚', NULL, TRUE),
(30, '成為社區導師', '幫助至少5位新手使用者', '作為導師指導新加入的用戶', 'social', 'Lv3+', 35, 60, '可重複', '👨‍🏫', NULL, TRUE);

-- ============================================================================
-- INSERT user_task_progress DATA (30 records - 10 completed, 20 incomplete)
-- ============================================================================
INSERT INTO user_task_progress (userId, taskId, completed, completedAt, completionCount, lastCompletedAt, createdAt) VALUES
-- Completed tasks (10)
(1, 1, TRUE, '2024-12-01 08:30:00', 15, '2024-12-01 08:30:00', '2024-11-01 08:00:00'),
(1, 2, TRUE, '2024-11-28 09:15:00', 4, '2024-11-28 09:15:00', '2024-11-01 08:00:00'),
(1, 3, TRUE, '2024-11-30 10:45:00', 8, '2024-11-30 10:45:00', '2024-11-01 08:00:00'),
(1, 4, TRUE, '2024-11-25 14:20:00', 12, '2024-11-25 14:20:00', '2024-11-01 08:00:00'),
(1, 6, TRUE, '2024-11-20 16:00:00', 1, '2024-11-20 16:00:00', '2024-11-01 08:00:00'),
(1, 7, TRUE, '2024-11-22 11:30:00', 1, '2024-11-22 11:30:00', '2024-11-01 08:00:00'),
(1, 12, TRUE, '2024-11-18 13:45:00', 3, '2024-11-26 13:45:00', '2024-11-01 08:00:00'),
(1, 18, TRUE, '2024-11-15 10:00:00', 1, '2024-11-15 10:00:00', '2024-11-01 08:00:00'),
(1, 24, TRUE, '2024-11-12 15:30:00', 2, '2024-11-12 15:30:00', '2024-11-01 08:00:00'),
(1, 25, TRUE, '2024-11-10 12:00:00', 1, '2024-11-10 12:00:00', '2024-11-01 08:00:00'),

-- Incomplete tasks (20)
(1, 5, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 8, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 9, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 10, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 11, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 13, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 14, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 15, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 16, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 17, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 19, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 20, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 21, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 22, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 23, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 26, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 27, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 28, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 29, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00'),
(1, 30, FALSE, NULL, 0, NULL, '2024-11-01 08:00:00');

-- ============================================================================
-- INSERT rewards DATA (32 gifts across 4 series)
-- ============================================================================
INSERT INTO rewards (id, title, description, details, points, category, series, level, image, stock, marketPrice, active) VALUES
-- Sustainable Series (8 items)
(1, '竹製餐具組', '環保竹製餐具五件組', '包含竹筷、湯匙、叉子、牙籤各1支及收納袋', 150, 'sustainable', '永續探索', 'EXPLORER', NULL, 50, 'NT$280', TRUE),
(2, 'ECO帆布購物袋', '天然棉帆布購物袋', '耐用環保帆布材質，適合日常購物', 180, 'sustainable', '永續探索', 'EXPLORER', NULL, 40, 'NT$320', TRUE),
(3, '植物性香皂', '天然草本香皂三入組', '使用天然植物油製造，不含化學物質', 120, 'sustainable', '永續探索', 'EXPLORER', NULL, 60, 'NT$250', TRUE),
(4, '玻璃吸管組', '食品級玻璃吸管8支組', '可重複使用的環保吸管，附清潔刷', 140, 'sustainable', '永續探索', 'CREATOR', NULL, 35, 'NT$380', TRUE),
(5, '竹纖維毛巾', '有機竹纖維浴巾兩條', '吸水性強，柔軟舒適', 200, 'sustainable', '永續探索', 'CREATOR', NULL, 25, 'NT$450', TRUE),
(6, '環保餐盒', '不鏽鋼環保便當盒', '304不鏽鋼材質，可微波加熱', 220, 'sustainable', '永續探索', 'VISIONARY', NULL, 20, 'NT$580', TRUE),
(7, '天然沐浴球', '麻布天然沐浴球三入', '天然纖維製成，促進血液循環', 130, 'sustainable', '永續探索', 'EXPLORER', NULL, 45, 'NT$290', TRUE),
(8, '竹製牙刷組', '有機竹製牙刷三支', '100%可分解，環保無污染', 110, 'sustainable', '永續探索', 'EXPLORER', NULL, 55, 'NT$220', TRUE),

-- Quality Series (8 items)
(9, 'ekax雲朵筆電包', '高品質羊毛筆電包13吋', '輕量防撞，適合13吋筆電', 220, 'quality', '質感創造', 'CREATOR', NULL, 15, 'NT$880', TRUE),
(10, 'Bellroy卡片夾', '超薄真皮卡片夾', '日本進口牛皮，容納16張卡片', 280, 'quality', '質感創造', 'CREATOR', NULL, 12, 'NT$1200', TRUE),
(11, 'Moleskine筆記本', '經典黑色Moleskine筆記本', '進口本皮筆記本，適合手帳愛好者', 250, 'quality', '質感創造', 'CREATOR', NULL, 18, 'NT$1100', TRUE),
(12, 'Kaweco鋼筆', '經典德國製鋼筆', '精緻工業設計，書寫流暢', 320, 'quality', '質感創造', 'VISIONARY', NULL, 8, 'NT$1800', TRUE),
(13, '日本玻璃水瓶', '耐熱高硼玻璃水瓶600ml', '瓶身透明，環保無污染', 160, 'quality', '質感創造', 'CREATOR', NULL, 25, 'NT$520', TRUE),
(14, '頸掛式眼鏡盒', '真皮頸掛眼鏡盒', '意大利進口牛皮，質感十足', 240, 'quality', '質感創造', 'CREATOR', NULL, 10, 'NT$950', TRUE),
(15, '書籤套裝', '五款精美書籤組合', '附送精美禮盒包裝', 95, 'quality', '質感創造', 'CREATOR', NULL, 40, 'NT$380', TRUE),
(16, '黃銅筆架', '北歐風黃銅筆架', '極簡設計，適合書桌擺飾', 180, 'quality', '質感創造', 'CREATOR', NULL, 22, 'NT$650', TRUE),

-- Aesthetic Series (8 items)
(17, '手工香薰蠟燭', '天然大豆蠟香薰蠟燭', '職人手工製作，香氣持久', 200, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 20, 'NT$680', TRUE),
(18, '藝術版畫套組', '五款高質感版畫作品組', '適合裝飾牆面，提升居家美感', 280, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 12, 'NT$1200', TRUE),
(19, '陶瓷馬克杯', '手作陶瓷咖啡杯', '獨特釉色，每個都是獨一無二', 150, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 30, 'NT$580', TRUE),
(20, '植物盆栽組', '三件精緻陶土盆栽', '搭配空氣淨化植物，美化空間', 240, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 18, 'NT$850', TRUE),
(21, '琺瑯徽章組', '十款琺瑯徽章收藏組', '設計獨特，送禮或自用都適合', 170, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 25, 'NT$650', TRUE),
(22, '手帳貼紙組', '300張裝飾貼紙', '適合日記、手帳裝飾', 85, 'aesthetic', '美學先鋒', 'CREATOR', NULL, 50, 'NT$280', TRUE),
(23, '布藝掛飾', '手工布藝壁掛裝飾', '日本布料製作，色彩豐富', 190, 'aesthetic', '美學先鋒', 'VISIONARY', NULL, 16, 'NT$720', TRUE),
(24, '牆面佈置包', '簡約黑白牆貼組', '易貼易撕，無傷牆面', 120, 'aesthetic', '美學先鋒', 'CREATOR', NULL, 35, 'NT$420', TRUE),

-- Premium Series (8 items)
(25, 'Apple AirTag', '蘋果官方AirTag追蹤器', '不怕丟失貴重物品', 450, 'premium', '品味閃耀', 'LUMINARY', NULL, 5, 'NT$1990', TRUE),
(26, 'Sony降噪藍牙耳機', 'Sony WH-CH720S耳機', '主動降噪，續航50小時', 550, 'premium', '品味閃耀', 'LUMINARY', NULL, 3, 'NT$2490', TRUE),
(27, 'Kindle電子書閱讀器', 'Kindle Paperwhite', '6.8吋電子墨水屏，續航10週', 600, 'premium', '品味閃耀', 'LUMINARY', NULL, 2, 'NT$2990', TRUE),
(28, '機械鍵盤', 'Keychron K8機械鍵盤', '無線機械鍵盤，青軸聲音清脆', 500, 'premium', '品味閃耀', 'LUMINARY', NULL, 4, 'NT$2280', TRUE),
(29, '智能手錶', 'Garmin Epix運動手錶', 'AMOLED屏幕，運動追蹤功能', 700, 'premium', '品味閃耀', 'LUMINARY', NULL, 2, 'NT$3990', TRUE),
(30, '無線充電盤', 'Anker三線圈無線充電盤', '快速充電，支持15W功率', 350, 'premium', '品味閃耀', 'LUMINARY', NULL, 8, 'NT$1290', TRUE),
(31, '攝影打光組', '柔光燈組+支架', 'YouTube創作者必備', 580, 'premium', '品味閃耀', 'LUMINARY', NULL, 3, 'NT$2580', TRUE),
(32, '高級咖啡機', 'Nespresso膠囊咖啡機', '意大利品牌，快速出杯', 650, 'premium', '品味閃耀', 'LUMINARY', NULL, 1, 'NT$3850', TRUE);

-- ============================================================================
-- INSERT reward_redemption DATA (3 sample redemptions)
-- ============================================================================
INSERT INTO reward_redemption (userId, rewardId, pointsSpent, status, redeemedAt, notes) VALUES
(1, 1, 150, 'completed', '2024-11-15 14:30:00', '用戶已收到商品'),
(1, 9, 220, 'shipped', '2024-11-20 10:15:00', '已出貨，追蹤號碼：ZTO2024112001'),
(1, 17, 200, 'pending', '2024-12-01 09:45:00', '待發貨');
