# 🎯 30項任務 - 可行性評估 & 後端實現指南

## 📱 日常互動任務 (6項)

### Task 1: 每日登入
- **簡述**：記錄用戶登入時間
- **後端實現**：
  - [ ] 建立 `activity_logs` 表（user_id, login_time, ip_address）
  - [ ] 登入時插入日誌記錄
  - [ ] 計算「連續登入天數」
- **可行性**：⭐⭐⭐⭐⭐ 最簡單，大多數應用都有
- **加分點**：可與 Google Analytics 或自定義統計串接

---

### Task 2: 完善身份驗證資料
- **簡述**：用戶填寫 KYC 資訊
- **後端實現**：
  - [ ] 建立 `user_profiles` 表（id_number, birth_date, address, kyo_verified_at）
  - [ ] 驗證身份證字號格式（Taiwan ID regex）
  - [ ] 加密存儲敏感資訊
  - [ ] 更新 `users.kyo_status` 為 'verified'
- **可行性**：⭐⭐⭐⭐⭐ 常見需求
- **加分點**：可串接真實身份驗證 API（如 TrustMark、GSLB）

---

### Task 3: 綁定銀行帳戶
- **簡述**：用戶填寫銀行帳戶資訊
- **後端實現**：
  - [ ] 建立 `user_bank_accounts` 表（bank_name, account_number, account_holder, is_verified）
  - [ ] 驗證帳戶號碼長度（16-17位）和格式
  - [ ] 加密存儲帳戶號碼
  - [ ] 後續可串接真實銀行轉帳驗證（小額驗證）
- **可行性**：⭐⭐⭐⭐ 有難度但實用
- **加分點**：✨ **串接台灣銀行 API**（中華銀行、玉山銀行等都有開放 API）
  - 使用銀行提供的帳戶驗證 API
  - 或透過第三方服務（如 TapPay、綠界）驗證帳戶真實性
  - 進階：小額驗證（匯入 $1，用戶確認金額）

---

### Task 4: 設定理財目標
- **簡述**：用戶建立儲蓄目標
- **後端實現**：
  - [ ] 建立 `financial_goals` 表（user_id, goal_name, target_amount, deadline, created_at, progress）
  - [ ] 自動計算「每月需存入額度」
  - [ ] 定期更新進度百分比
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：可與 Task 16（投資交易）整合，自動計算進度

---

### Task 5: 邀請好友開戶
- **簡述**：記錄推薦關係並獎勵
- **後端實現**：
  - [ ] 建立 `referral_relationships` 表（referrer_id, referred_user_id, referred_at, bonus_awarded）
  - [ ] 生成唯一邀請碼
  - [ ] 被邀請者完成身份驗證後，自動增加雙方積分
- **可行性**：⭐⭐⭐⭐⭐ 標準推薦系統
- **加分點**：✨ **追蹤推薦轉化**
  - 記錄：誰邀請誰、何時完成驗證、是否進行首筆交易
  - 可與 Task 30（社群大使）整合

---

### Task 6: 完成個人檔案設置
- **簡述**：用戶完善檔案資訊
- **後端實現**：
  - [ ] 建立 `user_profiles` 表（avatar_url, nickname, bio, risk_tolerance, investment_experience）
  - [ ] 計算「檔案完整度」(0-100%)
  - [ ] 當完整度 ≥ 80% 時發放成就
- **可行性**：⭐⭐⭐⭐⭐ 最簡單
- **加分點**：可與推薦系統整合

---

## 💰 理財學習任務 (6項)

### Task 7: 完成金融知識測驗
- **簡述**：用戶答題並計分
- **後端實現**：
  - [ ] 建立 `quizzes` 表（id, title, questions_json）
  - [ ] 建立 `quiz_attempts` 表（user_id, quiz_id, score, passed, attempted_at）
  - [ ] 計算分數（70分及格）
  - [ ] 記錄「每月參加次數」限制
- **可行性**：⭐⭐⭐⭐⭐ 很常見
- **加分點**：✨ **可串接教育平台**
  - 使用 Kahoot API 或自建題庫
  - 與 LMS（Learning Management System）整合

---

### Task 8: 觀看線上課程視頻
- **簡述**：追蹤用戶學習進度
- **後端實現**：
  - [ ] 建立 `courses` 表（title, video_url, duration, difficulty）
  - [ ] 建立 `course_progress` 表（user_id, course_id, watched_seconds, completion_percentage）
  - [ ] Webhook 監聽視頻進度（完成率 ≥ 90% 時標記為完成）
- **可行性**：⭐⭐⭐⭐ 需要視頻服務
- **加分點**：✨ **串接真實視頻平台**
  - YouTube API：追蹤觀看進度和完成率
  - Vimeo API：同樣支援進度追蹤
  - 或使用 Mux、Cloudflare Stream 等視頻平台

---

### Task 9: 建立借貸需求檔案
- **簡述**：用戶填寫財務資訊，系統評估風險
- **後端實現**：
  - [ ] 建立 `loan_applications` 表（user_id, annual_income, total_debt, purpose, created_at）
  - [ ] 實現風險評分模型（邏輯很簡單）
    ```
    風險評分 = (負債 / 年收入) * 100
    低風險：0-30分
    中風險：31-70分
    高風險：71-100分
    ```
  - [ ] 存儲評分結果
- **可行性**：⭐⭐⭐⭐ 有邏輯但不難
- **加分點**：✨ **串接真實信用評分服務**
  - 台灣聯徵中心（JCIC）API：查詢真實信用分數
  - 金管會開放資料：貸款利率基準
  - Google Cloud 的 AutoML 做機器學習風險評分

---

### Task 10: 建立投資組合記錄
- **簡述**：用戶建立虛擬投資組合
- **後端實現**：
  - [ ] 建立 `portfolios` 表（user_id, portfolio_name, created_at）
  - [ ] 建立 `portfolio_holdings` 表（portfolio_id, fund_id, quantity, purchase_price）
  - [ ] 計算組合的虛擬淨值（quantity × current_price）
- **可行性**：⭐⭐⭐⭐ 需要基金價格數據
- **加分點**：✨✨ **串接真實基金/股票數據** ⭐加分大戶
  - **台灣**：
    - TWSE（台灣證交所）API：免費股票行情
    - 基智網 Open API：基金淨值
    - 群益 API：基金報價
  - **國際**：
    - Alpha Vantage：免費股票 API
    - IEX Cloud：美股實時行情
    - Yahoo Finance API（非官方但穩定）
  - **推薦做法**：
    - 使用免費的 Alpha Vantage 或 TWSE API
    - 每日定時更新基金/股票淨值
    - 用戶組合會自動計算漲跌幅

---

### Task 11: 設定投資提醒規則
- **簡述**：用戶設定價格提醒條件
- **後端實現**：
  - [ ] 建立 `alert_rules` 表（user_id, symbol, condition, threshold, is_active）
    ```
    例：symbol='2330', condition='下跌', threshold=5 (%)
    ```
  - [ ] 建立定時任務（cronjob）每日檢查
  - [ ] 觸發時生成通知記錄
- **可行性**：⭐⭐⭐⭐ 需要定時任務
- **加分點**：✨ **串接真實行情和通知**
  - 使用 TWSE API 或 Alpha Vantage 實時行情
  - 串接 Firebase Cloud Messaging (FCM) 或 Twilio 發送真實通知
  - 或簡單做法：Email 提醒

---

### Task 12: 參與線上學習論壇
- **簡述**：用戶發表評論，系統記錄互動
- **後端實現**：
  - [ ] 建立 `forum_posts` 表（user_id, title, content, created_at, view_count）
  - [ ] 建立 `forum_comments` 表（user_id, post_id, content, created_at, like_count）
  - [ ] 內容審核（檢查敏感詞）
  - [ ] 互動數據統計
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：可串接審核 API（Google Perspective API 檢測言論不當）

---

## 📊 投資實踐任務 (6項)

### Task 13: 完成風險承受能力評估
- **簡述**：用戶答題，系統計算風險等級
- **後端實現**：
  - [ ] 建立 `risk_assessments` 表（user_id, answers_json, risk_level, created_at）
  - [ ] 計分邏輯：根據答案計算風險值
  - [ ] 分類：低/中/高風險
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 14: 建立定期定額投資計畫
- **簡述**：用戶設定自動投資計畫
- **後端實現**：
  - [ ] 建立 `investment_plans` 表（user_id, monthly_amount, start_date, end_date, status）
  - [ ] 建立 `plan_schedules` 表（plan_id, execution_date, executed, executed_at）
  - [ ] 建立 cronjob 在每個月執行日期自動"執行"投資
  - [ ] 記錄每次執行的審計日誌
- **可行性**：⭐⭐⭐⭐ 需要定時任務
- **加分點**：✨ **串接真實銀行自動扣款**
  - 使用銀行 API（如玉山銀行 Open API）設定自動轉帳
  - 或使用第三方服務（TapPay、綠界）

---

### Task 15: 建立投資帳戶記錄
- **簡述**：用戶註冊虛擬投資帳戶
- **後端實現**：
  - [ ] 建立 `trading_accounts` 表（user_id, account_type, account_number, financial_institution, verified_at）
  - [ ] 驗證帳戶號碼格式
  - [ ] 生成唯一帳戶 ID
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 16: 記錄虛擬投資交易
- **簡述**：用戶記錄買賣交易，計算損益
- **後端實現**：
  - [ ] 建立 `trading_records` 表（user_id, symbol, quantity, buy_price, trade_date, trade_type）
  - [ ] 建立 `position_performance` 表（user_id, symbol, current_value, gain_loss, gain_loss_pct）
  - [ ] 自動計算：
    ```
    損益 = (當前價格 - 買入價格) × 數量
    損益率 = 損益 / (買入價格 × 數量) × 100%
    ```
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：✨✨ **串接真實股票/基金價格**（與 Task 10 相同）

---

### Task 17: 設定投資限制和額度
- **簡述**：用戶設定風險控制規則
- **後端實現**：
  - [ ] 建立 `risk_control_rules` 表（user_id, rule_name, rule_condition, is_active）
    ```
    例：single_position_max = 30% （單個持股不超過投資組合 30%）
    例：max_single_trade = 100,000 （單筆交易上限 10 萬）
    ```
  - [ ] 交易時進行規則檢查
  - [ ] 違反時拒絕交易並返回原因
- **可行性**：⭐⭐⭐⭐ 需要交易驗證邏輯
- **加分點**：無

---

### Task 18: 建立個人投資日誌
- **簡述**：用戶撰寫交易心得
- **後端實現**：
  - [ ] 建立 `investment_journals` 表（user_id, title, content, related_trades, created_at, tags）
  - [ ] 支援標籤和搜尋
  - [ ] 記錄建立/編輯時間
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：可與 Task 16 整合，自動關聯相關交易

---

## 🌱 永續行動任務 (6項)

### Task 19: 完成ESG價值觀評估
- **簡述**：用戶答題，評估 ESG 偏好
- **後端實現**：
  - [ ] 建立 `esg_assessments` 表（user_id, environment_score, social_score, governance_score）
  - [ ] 計算綜合 ESG 分數
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 20: 記錄永續投資組合
- **簡述**：用戶建立 ESG 主題組合
- **後端實現**：
  - [ ] `portfolios` 表增加 `portfolio_type = 'ESG'` 標籤
  - [ ] 建立 `esg_portfolio_holdings` 表（portfolio_id, esg_fund_id, quantity）
  - [ ] 計算組合績效
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：✨ **串接真實 ESG 基金數據**
  - Morningstar API：ESG 評級
  - Bloomberg Terminal（需付費）
  - 台灣投信公會的 ESG 基金清單

---

### Task 21: 設定永續投資目標
- **簡述**：用戶設定 ESG 投資目標
- **後端實現**：
  - [ ] `financial_goals` 表增加 `goal_type = 'ESG'`
  - [ ] 定期計算目標進度
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 22: 建立慈善捐款記錄
- **簡述**：用戶記錄捐款
- **後端實現**：
  - [ ] 建立 `donations` 表（user_id, charity_org_id, amount, donation_date, receipt_number）
  - [ ] 自動生成收據編號
  - [ ] 累計年度捐款統計
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：✨ **串接真實慈善機構**
  - 國際：GlobalGiving API
  - 台灣：捐款平台 API（如 GoodIDEAS）

---

### Task 23: 撰寫永續投資文章
- **簡述**：用戶發表 ESG 文章
- **後端實現**：
  - [ ] `forum_posts` 表增加 `tag = 'ESG'`
  - [ ] 記錄互動指標（瀏覽、點讚、評論）
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 24: 參與永續投資討論
- **簡述**：用戶參與社群討論
- **後端實現**：
  - [ ] 建立 `discussion_threads` 表（user_id, topic, replies_count, created_at）
  - [ ] 記錄互動評分
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

## 🎁 社群成就任務 (6項)

### Task 25: 邀請3位朋友完成認證
- **簡述**：達成推薦目標
- **後端實現**：
  - [ ] 建立檢查邏輯：`COUNT(referral_relationships WHERE referrer_id = user_id AND verified = true) >= 3`
  - [ ] 解鎖成就
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 26-28: 等級升級系統
**Task 26: Creator (250點) | Task 27: Visionary (750點) | Task 28: Luminary (1500點)**

- **簡述**：自動檢測升級點數並更新等級
- **後端實現**：
  - [ ] 建立 `user_levels` 表（user_id, level_name, levelPoints, unlocked_at）
  - [ ] 建立 trigger 或定時任務檢查升級條件
  - [ ] 升級時更新等級並發送通知
  - [ ] 不同等級解鎖不同功能
- **可行性**：⭐⭐⭐⭐ 需要權限控制邏輯
- **加分點**：✨ **可與實際功能整合**
  - Visionary 等級：解鎖進階投資功能
  - Luminary 等級：解鎖 VIP API 端點、更高的交易額度

---

### Task 29: 月度交易100萬
- **簡述**：累計月度交易額
- **後端實現**：
  - [ ] 建立查詢：計算本月所有交易總額
    ```sql
    SUM(quantity * trade_price)
    WHERE user_id = ?
    AND MONTH(trade_date) = MONTH(NOW())
    ```
  - [ ] 達到 1,000,000 時記錄成就
- **可行性**：⭐⭐⭐⭐⭐ 很簡單
- **加分點**：無

---

### Task 30: 社群大使成就
- **簡述**：綜合統計用戶貢獻值
- **後端實現**：
  - [ ] 建立 `contribution_scores` 表（user_id, month, referral_score, forum_score, engagement_score, total_score）
  - [ ] 計算：
    ```
    推薦分 = 成功邀請人數 × 20
    論壇分 = 發文數 × 10 + 評論點讚數 × 2
    互動分 = 登入天數 × 1
    總分 = 推薦分 + 論壇分 + 互動分
    ```
  - [ ] 達到 100 分時解鎖成就
- **可行性**：⭐⭐⭐⭐ 複雜邏輯但可行
- **加分點**：無

---

---

## 🚀 加分機會排行（如何脫穎而出）

### 第一檔（必做 - 直接串接真實 API）

| 優先級 | 任務 | API 建議 | 難度 | ROI |
|--------|-----|--------|------|-----|
| ⭐⭐⭐ | Task 3: 銀行帳戶 | 台灣銀行 Open API | 中 | 超高 |
| ⭐⭐⭐ | Task 10: 投資組合 | TWSE API + Alpha Vantage | 中 | 超高 |
| ⭐⭐⭐ | Task 11: 提醒規則 | TWSE API + FCM 推播 | 中 | 超高 |
| ⭐⭐⭐ | Task 16: 交易記錄 | 同 Task 10 | 中 | 超高 |
| ⭐⭐ | Task 8: 課程進度 | YouTube API | 簡 | 中 |
| ⭐⭐ | Task 20: ESG 基金 | Morningstar API | 簡 | 中 |

### 實施建議

**最簡單的加分方案（推薦先做）：**
1. **Task 10 + Task 16**：使用 `Alpha Vantage`（免費）
   - 每天定時更新股票/基金價格
   - 自動計算用戶組合漲跌幅
   - 展示實時行情截圖在簡報上

2. **Task 11**：基於真實行情發送提醒
   - 用免費 FCM 或 Email 發送通知
   - 展示「提醒被正確觸發」的日誌

3. **Task 3**：銀行帳戶驗證
   - 使用綠界或 TapPay 的小額驗證
   - 或聯絡銀行取得 API key

---

## 📋 實施檢查清單

```
Daily 任務：
[ ] Task 1 - 登入日誌表 + 連續登入計算
[ ] Task 2 - KYC 驗證表 + 身份證驗證 regex
[ ] Task 3 - 銀行帳戶表 + 串接銀行/第三方 API ⭐
[ ] Task 4 - 理財目標表 + 進度計算
[ ] Task 5 - 推薦關係表 + 邀請碼生成
[ ] Task 6 - 檔案完整度計算

Financial 任務：
[ ] Task 7 - 測驗表 + 計分邏輯
[ ] Task 8 - 課程進度表 + YouTube API ⭐
[ ] Task 9 - 貸款評估表 + 風險評分邏輯
[ ] Task 10 - 組合表 + 串接真實股票 API ⭐⭐
[ ] Task 11 - 提醒規則表 + 定時檢查 + 推播通知 ⭐
[ ] Task 12 - 論壇表 + 內容審核

Investment 任務：
[ ] Task 13 - 風險評估表
[ ] Task 14 - 定投計畫表 + cronjob
[ ] Task 15 - 交易帳戶表
[ ] Task 16 - 交易紀錄表 + 自動計算損益 ⭐
[ ] Task 17 - 風控規則表 + 交易驗證邏輯
[ ] Task 18 - 投資日誌表

ESG 任務：
[ ] Task 19 - ESG 評估表
[ ] Task 20 - ESG 組合表
[ ] Task 21 - ESG 目標表
[ ] Task 22 - 捐款表 + 收據生成
[ ] Task 23 - 論壇 ESG 標籤
[ ] Task 24 - 討論線程表

Social 任務：
[ ] Task 25 - 推薦達成檢查
[ ] Task 26-28 - 等級升級系統 + 權限控制 ⭐
[ ] Task 29 - 月度交易額統計
[ ] Task 30 - 貢獻值計算
```

---

## 💡 重點提醒

1. **不要只做 Mock 數據** - 教授會問為什麼沒有真實數據
2. **最少做 3 個 API 串接** - 才能顯示出全棧能力
3. **推薦組合**：
   - Task 3（銀行 API）+ Task 10/16（股票 API）+ Task 11（推播）= 完整全棧展示
   - 或 Task 8（YouTube）+ Task 10（股票）+ Task 30（複雜統計邏輯）

4. **簡報時強調**：
   - 「使用真實的 TWSE API 獲取台灣股票行情」
   - 「每日定時任務自動更新用戶組合淨值」
   - 「推播通知使用 Firebase Cloud Messaging」
   - 「銀行帳戶驗證透過第三方支付平台」

這樣教授一看就知道你是認真做的，不是只有前端切圖。