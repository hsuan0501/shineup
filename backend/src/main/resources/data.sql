-- ShineUp 資料庫初始資料
-- Spring Boot 會在啟動時自動執行此檔案

-- ============================================
-- 1. 等級配置 (level_config)
-- ============================================
INSERT INTO level_config (level_code, level_name, level_number, min_points, max_points, multiplier) VALUES
('EXPLORER', '探索者', 1, 0, 499, 1.0),
('CREATOR', '創造者', 2, 500, 1999, 1.15),
('VISIONARY', '先行者', 3, 2000, 4999, 1.4),
('LUMINARY', '閃耀者', 4, 5000, 999999, 1.7)
ON DUPLICATE KEY UPDATE level_name = VALUES(level_name);

-- ============================================
-- 2. 測試用戶 (users)
-- ============================================
-- 密碼都是 123456 (BCrypt 加密後)
INSERT INTO users (email, password, name, phone, level, upgrade_points, reward_points, created_at, updated_at) VALUES
('matcha@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.Y8j7r5F6O7e/Z6e.YO', 'Matcha', '0912345678', 'CREATOR', 1200, 3500, NOW(), NOW()),
('alice@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.Y8j7r5F6O7e/Z6e.YO', 'Alice', '0923456789', 'EXPLORER', 120, 80, NOW(), NOW()),
('bob@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.Y8j7r5F6O7e/Z6e.YO', 'Bob', '0934567890', 'VISIONARY', 2500, 1800, NOW(), NOW()),
('carol@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.Y8j7r5F6O7e/Z6e.YO', 'Carol', '0945678901', 'LUMINARY', 5500, 4200, NOW(), NOW())
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- ============================================
-- 3. 任務資料 (tasks)
-- ============================================
INSERT INTO tasks (id, title, description, category, required_level, upgrade_points, reward_points, image, active) VALUES
-- 📱 日常互動任務
(1, '每日登入', '每日登入一次即可完成', 'daily', 'EXPLORER', 5, 10, '/images/tasks/task-1.jpg', true),
(2, '完善身份驗證資料', '完整填寫身份基本資訊', 'daily', 'EXPLORER', 40, 60, '/images/tasks/task-2.jpg', true),
(3, '綁定銀行帳戶', '關聯銀行帳戶資訊', 'daily', 'EXPLORER', 50, 75, '/images/tasks/task-3.jpg', true),
(4, '設定理財目標', '建立個人理財目標', 'daily', 'EXPLORER', 30, 45, '/images/tasks/task-4.jpg', true),
(5, '邀請好友開戶', '透過邀請碼邀請朋友註冊', 'daily', 'EXPLORER', 60, 90, '/images/tasks/task-5.jpg', true),
(6, '完成個人檔案設置', '設定個人檔案完整性', 'daily', 'EXPLORER', 25, 38, '/images/tasks/task-6.jpg', true),

-- 💰 理財學習任務
(7, '完成金融知識測驗', '通過基礎理財知識測試', 'financial', 'EXPLORER', 35, 52, '/images/tasks/task-7.jpg', true),
(8, '觀看線上課程視頻', '完成理財教育課程學習', 'financial', 'EXPLORER', 45, 68, '/images/tasks/task-8.jpg', true),
(9, '建立借貸需求檔案', '填寫貸款需求基本資訊', 'financial', 'CREATOR', 80, 120, '/images/tasks/task-9.jpg', true),
(10, '建立投資組合記錄', '記錄您的首個虛擬投資組合', 'financial', 'CREATOR', 150, 225, '/images/tasks/task-10.jpg', true),
(11, '設定投資提醒規則', '配置投資市場提醒', 'financial', 'CREATOR', 120, 180, '/images/tasks/task-11.jpg', true),
(12, '參與線上學習論壇', '在討論區發表理財心得', 'financial', 'EXPLORER', 70, 105, '/images/tasks/task-12.jpg', true),

-- 📊 投資實踐任務
(13, '完成風險承受能力評估', '填寫投資風險問卷', 'investment', 'EXPLORER', 60, 90, '/images/tasks/task-13.jpg', true),
(14, '建立定期定額投資計畫', '設定月定投計畫參數', 'investment', 'CREATOR', 100, 150, '/images/tasks/task-14.jpg', true),
(15, '建立投資帳戶記錄', '在系統中註冊投資帳戶', 'investment', 'CREATOR', 120, 180, '/images/tasks/task-15.jpg', true),
(16, '記錄虛擬投資交易', '記錄一筆虛擬投資交易', 'investment', 'CREATOR', 200, 300, '/images/tasks/task-16.jpg', true),
(17, '設定投資限制和額度', '配置個人投資風險限制', 'investment', 'VISIONARY', 180, 270, '/images/tasks/task-17.jpg', true),
(18, '建立個人投資日誌', '記錄投資心得和分析', 'investment', 'VISIONARY', 200, 300, '/images/tasks/task-18.jpg', true),

-- 🌱 永續行動任務
(19, '完成ESG價值觀評估', '填寫永續投資偏好問卷', 'esg', 'EXPLORER', 45, 68, '/images/tasks/task-19.jpg', true),
(20, '記錄永續投資組合', '建立永續主題投資組合記錄', 'esg', 'CREATOR', 140, 210, '/images/tasks/task-20.jpg', true),
(21, '設定永續投資目標', '建立個人永續投資目標', 'esg', 'CREATOR', 120, 180, '/images/tasks/task-21.jpg', true),
(22, '建立慈善捐款記錄', '記錄個人慈善捐款計畫', 'esg', 'CREATOR', 130, 195, '/images/tasks/task-22.jpg', true),
(23, '撰寫永續投資文章', '發表ESG投資心得文章', 'esg', 'CREATOR', 80, 120, '/images/tasks/task-23.jpg', true),
(24, '參與永續投資討論', '在論壇參與ESG投資討論', 'esg', 'VISIONARY', 150, 225, '/images/tasks/task-24.jpg', true),

-- 🎁 社群成就任務
(25, '邀請3位朋友完成認證', '成功推薦朋友註冊帳戶', 'social', 'EXPLORER', 100, 150, '/images/tasks/task-25.jpg', true),
(26, '達成 Creator 等級升級', '累積升級點數至500點', 'social', 'EXPLORER', 100, 150, '/images/tasks/task-26.jpg', true),
(27, '達成 Visionary 等級升級', '累積升級點數至2000點', 'social', 'EXPLORER', 200, 300, '/images/tasks/task-27.jpg', true),
(28, '達成 Luminary 等級升級', '累積升級點數至5000點', 'social', 'EXPLORER', 300, 450, '/images/tasks/task-28.jpg', true),
(29, '累積月度交易額到100萬', '單月虛擬投資成交額突破', 'social', 'VISIONARY', 250, 375, '/images/tasks/task-29.jpg', true),
(30, '累積社群貢獻成為大使', '累積社群互動和推薦成就', 'social', 'VISIONARY', 350, 525, '/images/tasks/task-30.jpg', true)
ON DUPLICATE KEY UPDATE title = VALUES(title);

-- ============================================
-- 4. 禮品資料 (gifts)
-- ============================================
INSERT INTO gifts (id, title, description, series, required_level, required_points, stock, image) VALUES
-- 🌱 永續探索系列 - Lv1 Explorer
(1, 'LIFE PEN 種子鉛筆組', '市價NT$280', 'sustainable', 'EXPLORER', 100, 99, '/images/gifts/gift-1.jpg'),
(2, 'UiU 環保便攜吸管組', '市價NT$300', 'sustainable', 'EXPLORER', 100, 73, '/images/gifts/gift-2.jpg'),
(3, '印花樂 實用環保包袋', '市價NT$390', 'sustainable', 'EXPLORER', 120, 65, '/images/gifts/gift-3.jpg'),
(4, 'HappyEarth 回收紙筆記本', '市價NT$400', 'sustainable', 'EXPLORER', 130, 35, '/images/gifts/gift-4.jpg'),
(5, '沐muhair 無塑固態洗髮精', '市價NT$400', 'sustainable', 'EXPLORER', 130, 85, '/images/gifts/gift-5.jpg'),
(6, '簡約托特包', '市價NT$450', 'sustainable', 'EXPLORER', 150, 99, '/images/gifts/gift-6.jpg'),
(7, 'Re-ing 天然竹纖維便當盒', '市價NT$450', 'sustainable', 'EXPLORER', 150, 58, '/images/gifts/gift-7.jpg'),
(8, 'SUCCULAND 多肉植物', '市價NT$590', 'sustainable', 'EXPLORER', 200, 23, '/images/gifts/gift-8.jpg'),

-- 🪴 質感創造系列 - Lv2 Creator
(9, 'ekax 雲朵筆電包', '市價NT$650', 'quality', 'CREATOR', 220, 99, '/images/gifts/gift-9.jpg'),
(10, 'smellscape 擴香石香氛禮盒', '市價NT$700', 'quality', 'CREATOR', 230, 53, '/images/gifts/gift-10.jpg'),
(11, 'MOFT 磁吸感應卡包支架', '市價NT$890', 'quality', 'CREATOR', 300, 24, '/images/gifts/gift-11.jpg'),
(12, '手工皮革名片夾', '市價NT$930', 'quality', 'CREATOR', 310, 9, '/images/gifts/gift-12.jpg'),
(13, 'Umbra 臘腸狗戒指收納座', '市價NT$930', 'quality', 'CREATOR', 310, 88, '/images/gifts/gift-13.jpg'),
(14, 'ROOMMI 充電感應垃圾桶', '市價NT$959', 'quality', 'CREATOR', 320, 28, '/images/gifts/gift-14.jpg'),
(15, 'KINTO 提式輕巧保溫瓶', '市價NT$1,148', 'quality', 'CREATOR', 400, 99, '/images/gifts/gift-15.jpg'),
(16, 'MOMOCONCEPT 保溫杯', '市價NT$1,501', 'quality', 'CREATOR', 500, 55, '/images/gifts/gift-16.jpg'),

-- 🕯️ 美學先鋒系列 - Lv3 Visionary
(17, 'mordeco 轉轉零錢筒', '市價NT$1,580', 'aesthetic', 'VISIONARY', 530, 36, '/images/gifts/gift-17.jpg'),
(18, 'Vana 香氛蠟燭暖燈', '市價NT$1,790', 'aesthetic', 'VISIONARY', 600, 19, '/images/gifts/gift-18.jpg'),
(19, 'MUJI 超音波芬香噴霧器', '市價NT$1,790', 'aesthetic', 'VISIONARY', 600, 16, '/images/gifts/gift-19.jpg'),
(20, 'MOMAX 無線充電行動電源', '市價NT$1,980', 'aesthetic', 'VISIONARY', 660, 18, '/images/gifts/gift-20.jpg'),
(21, 'JWAY 砧板刀具烘乾消毒機', '市價NT$2,480', 'aesthetic', 'VISIONARY', 830, 13, '/images/gifts/gift-21.jpg'),
(22, 'Oakywood 無線充電盤', '市價NT$3,200', 'aesthetic', 'VISIONARY', 1100, 49, '/images/gifts/gift-22.jpg'),
(23, 'Bellwood 雨傘收納架', '市價NT$3,385', 'aesthetic', 'VISIONARY', 1100, 22, '/images/gifts/gift-23.jpg'),
(24, 'Wanu 銜月床頭燈', '市價NT$3,500', 'aesthetic', 'VISIONARY', 1200, 7, '/images/gifts/gift-24.jpg'),

-- 💼 品味閃耀系列 - Lv4 Luminary
(25, '夏慕尼星級饗宴餐券', '市價NT$4,818', 'premium', 'LUMINARY', 1600, 14, '/images/gifts/gift-25.jpg'),
(26, 'Porter 真皮公事包', '市價NT$5,850', 'premium', 'LUMINARY', 1950, 18, '/images/gifts/gift-26.jpg'),
(27, 'LAMY 2000 鋼筆', '市價NT$7,020', 'premium', 'LUMINARY', 2300, 26, '/images/gifts/gift-27.jpg'),
(28, 'AirPods Pro 3', '市價NT$7,490', 'premium', 'LUMINARY', 2500, 21, '/images/gifts/gift-28.jpg'),
(29, 'Dyson Supersonic Nural™ 吹風機', '市價NT$9,999', 'premium', 'LUMINARY', 3300, 5, '/images/gifts/gift-29.jpg'),
(30, 'Apple Watch Series 11', '市價NT$12,900', 'premium', 'LUMINARY', 4300, 4, '/images/gifts/gift-30.jpg'),
(31, 'LOJEL 30吋前開式行李箱', '市價NT$15,800', 'premium', 'LUMINARY', 5300, 3, '/images/gifts/gift-31.jpg'),
(32, '雲品尊榮湖景客房住宿券', '市價NT$18,888', 'premium', 'LUMINARY', 6300, 3, '/images/gifts/gift-32.jpg')
ON DUPLICATE KEY UPDATE title = VALUES(title);

-- ============================================
-- 5. 範例兌換訂單 (redemption_orders)
-- ============================================
INSERT INTO redemption_orders (user_id, gift_id, quantity, total_points, status, created_at) VALUES
(1, 2, 1, 100, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(1, 9, 1, 220, 'SHIPPED', DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 1, 1, 100, 'PENDING', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 18, 1, 600, 'COMPLETED', DATE_SUB(NOW(), INTERVAL 7 DAY))
ON DUPLICATE KEY UPDATE status = VALUES(status);
