# ShineUp

專為企業客戶設計的積分獎勵與分級制度平台。以任務累積積分、兌換禮品的機制，引導客戶完成風險屬性評估、投資組合建立、虛擬交易模擬等關鍵行為，提升轉換率並降低獲客成本。

**技術：** Vue 3 / Tailwind CSS / Spring Boot / MySQL

## 功能特色

**會員系統**
- 四階等級制度 (Lv1 Explorer → Lv2 Creator → Lv3 Visionary → Lv4 Luminary)
- LINE OAuth 登入與帳號綁定
- 密碼重設與信箱驗證
- 推薦碼系統（邀請好友獲得積分獎勵）
- 用戶統計追蹤（登入天數、任務完成數、兌換次數）
- 多銀行帳戶綁定

**任務系統**
- 五大類別：日常互動、理財學習、投資實踐、永續行動、社群成就
- 任務完成自動發放積分與等級升級檢測

**兌換系統**
- 禮品瀏覽與購物車
- 訂單管理與兌換紀錄
- 禮品等級限制（不同等級可兌換不同禮品）
- 完整訂單狀態追蹤（確認中 → 已出貨 → 已送達 → 取貨完成）
- 多收貨地址管理

**其他**
- 圖形驗證碼 / 庫存狀態標示
- 深色模式 / RWD 響應式設計
- 後台管理頁面
- FAQ 客服機器人（關鍵字自動回覆）
- 通知系統（系統公告、訂單更新通知）

## 快速開始

### 環境變數

```properties
MYSQL_USER=your_username
MYSQL_PASSWORD=your_password

# LINE Login (選填)
LINE_CHANNEL_ID=your_channel_id
LINE_CHANNEL_SECRET=your_channel_secret
```

### 啟動專案

```bash
# 前端
npm install && npm run dev

# 後端
cd backend && ./mvnw spring-boot:run
```

### 體驗帳號

| 角色 | 帳號 | 密碼 |
|------|------|------|
| 一般會員 | hsuan0501@outlook.com | Qwe1234 |
| 管理員 | admin@shineup.com | Admin123 |

<details>
<summary><strong>API 端點</strong></summary>

| 模組 | 端點 | 說明 |
|------|------|------|
| 認證 | `/api/auth` | 註冊、登入、密碼重設 |
| LINE | `/api/line` | OAuth 回調、帳號綁定 |
| 會員 | `/api/users` | 會員管理、推薦碼 |
| 任務 | `/api/tasks` | 任務列表、完成任務 |
| 禮品 | `/api/gifts` | 禮品瀏覽、兌換 |
| 訂單 | `/api/orders` | 訂單管理、狀態追蹤 |
| 銀行 | `/api/bank-accounts` | 帳戶綁定 |
| 通知 | `/api/notifications` | 系統通知 |
| 客服 | `/api/chat` | FAQ 機器人 |

</details>

<details>
<summary><strong>專案架構</strong></summary>

```
shineup/
├── src/                    # 前端 (Vue 3 + Pinia)
│   ├── api/                # API 服務層
│   ├── components/         # 共用元件
│   ├── views/              # 頁面
│   └── store/              # 狀態管理
├── backend/                # 後端 (Spring Boot)
│   └── src/main/java/.../
│       ├── controller/     # REST API
│       ├── service/        # 業務邏輯
│       ├── repository/     # 資料存取
│       └── entity/         # 資料模型
└── public/                 # 靜態資源
```

</details>

## License

Copyright © 2025 May Yang. All rights reserved.
