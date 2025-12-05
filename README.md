# ShineUp

專為企業客戶設計的積分獎勵與分級制度平台。以任務累積積分、兌換禮品的機制，引導客戶完成風險屬性評估、投資組合建立、虛擬交易模擬等關鍵行為，提升轉換率並降低獲客成本。

## 專案架構

```
shineup/
├── src/                    # 前端 (Vue 3)
│   ├── components/         # 共用元件
│   ├── views/              # 頁面
│   ├── router/             # 路由設定
│   ├── store/              # Pinia 狀態管理
│   └── utils/              # 工具函式
├── backend/                # 後端 (Spring Boot)
│   └── src/main/java/com/shineup/backend/
│       ├── controller/     # REST API
│       ├── service/        # 業務邏輯
│       ├── repository/     # 資料存取
│       └── entity/         # 資料模型
├── database/               # 資料庫
│   ├── schema.sql          # 資料表結構
│   └── seed_data.sql       # 初始資料
└── public/                 # 靜態資源
```

## 技術棧

### 前端
- Vue 3 (Composition API)
- Vite
- Tailwind CSS
- Pinia
- Vue Router

### 後端
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- MySQL 8.0

## 功能特色

- 會員等級制度 (Explorer → Creator → Visionary → Luminary)
- 任務系統 (每日任務、理財學習、投資實踐、永續行動)
- 積分兌換禮品
- 深色模式支援
- RWD 響應式設計

## 快速開始

### 前端

```bash
# 安裝依賴
npm install

# 啟動開發伺服器
npm run dev
```

### 後端

1. 確保 MySQL 服務已啟動
2. 建立資料庫：
```sql
CREATE DATABASE shineup;
```
3. 設定 `backend/src/main/resources/application.properties` 中的資料庫連線
4. 使用 IDE 執行 `BackendApplication.java`

### 資料庫

```bash
# 匯入資料表結構
mysql -u root -p shineup < database/schema.sql

# 匯入初始資料
mysql -u root -p shineup < database/seed_data.sql
```

## API 端點

| 資源 | 端點 | 說明 |
|------|------|------|
| 會員 | `GET /api/users` | 取得所有會員 |
| 任務 | `GET /api/tasks` | 取得所有任務 |
| 禮品 | `GET /api/gifts` | 取得所有禮品 |
| 訂單 | `GET /api/orders` | 取得兌換訂單 |

## License

MIT
