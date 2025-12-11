# ShineUp

專為企業客戶設計的積分獎勵與分級制度平台。以任務累積積分、兌換禮品的機制，引導客戶完成風險屬性評估、投資組合建立、虛擬交易模擬等關鍵行為，提升轉換率並降低獲客成本。

## 專案架構

```
shineup/
├── src/                    # 前端 (Vue 3)
│   ├── api/                # API 服務層
│   ├── components/         # 共用元件
│   ├── views/              # 頁面
│   ├── router/             # 路由設定
│   ├── store/              # Pinia 狀態管理
│   └── mock.js             # 假資料
├── backend/                # 後端 (Spring Boot)
│   └── src/main/java/com/shineup/backend/
│       ├── controller/     # REST API
│       ├── service/        # 業務邏輯
│       ├── repository/     # 資料存取
│       ├── entity/         # 資料模型
│       ├── dto/            # 資料傳輸物件
│       ├── config/         # 安全設定
│       └── util/           # JWT 工具
│   └── src/main/resources/
│       ├── application.properties  # 設定檔
│       └── data.sql                # 初始資料
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
- 任務系統 (日常互動、理財學習、投資實踐、永續行動、社群成就)
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

Spring Boot 啟動時會自動建立資料表並匯入初始資料 (`data.sql`)。

首次啟動後，可將 `application.properties` 中的 `spring.sql.init.mode` 改為 `never` 避免重複匯入。

## API 端點

| 資源 | 端點 | 說明 |
|------|------|------|
| 認證 | `POST /api/auth/register` | 註冊 |
| 認證 | `POST /api/auth/login` | 登入 |
| 認證 | `GET /api/auth/me` | 取得當前用戶 |
| 會員 | `GET /api/users` | 取得所有會員 |
| 會員 | `POST /api/users/{id}/reset` | 重置用戶資料 (展示用) |
| 任務 | `GET /api/tasks` | 取得所有任務 |
| 任務 | `POST /api/tasks/{id}/complete` | 完成任務 |
| 禮品 | `GET /api/gifts` | 取得所有禮品 |
| 禮品 | `POST /api/gifts/{id}/redeem` | 兌換禮品 |
| 訂單 | `GET /api/orders` | 取得兌換訂單 |

## License

MIT
