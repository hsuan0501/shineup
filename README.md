# ShineUp

專為企業客戶設計的積分獎勵與分級制度平台。以任務累積積分、兌換禮品的機制，引導客戶完成風險屬性評估、投資組合建立、虛擬交易模擬等關鍵行為，提升轉換率並降低獲客成本。

**技術：** Vue 3 / Tailwind CSS / Spring Boot / MySQL

## 功能特色

**會員系統**
- 四階等級制度 (Lv1 Explorer → Lv2 Creator → Lv3 Visionary → Lv4 Luminary)
- LINE OAuth 登入與帳號綁定
- 密碼重設與信箱驗證

**任務系統**
- 五大類別：日常互動、理財學習、投資實踐、永續行動、社群成就
- 任務完成自動發放積分與等級升級檢測

**兌換系統**
- 禮品瀏覽與購物車
- 訂單管理與兌換紀錄

**其他**
- 圖形驗證碼 / 庫存狀態標示
- 深色模式 / RWD 響應式設計
- 後台管理頁面

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

### 測試帳號

```
一般會員：hsuan0501@outlook.com / Qwe1234
管理員：admin@shineup.com / Admin123
```

<details>
<summary><strong>API 端點</strong></summary>

- **認證** `/api/auth` - 註冊、登入、取得用戶資訊
- **LINE** `/api/line` - OAuth 回調、帳號綁定
- **會員** `/api/users` - 會員列表、資料重置
- **任務** `/api/tasks` - 任務列表、完成任務
- **禮品** `/api/gifts` - 禮品列表、兌換禮品
- **訂單** `/api/orders` - 兌換紀錄

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
