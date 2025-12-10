# 後端開發安全檢查清單

串接後端時需注意的安全事項，避免常見漏洞。

---

## 1. 敏感數據處理

### 禁止事項
- [ ] 不要在前端寫帳密驗證邏輯
- [ ] 不要在前端程式碼中放 API Key
- [ ] 不要在前端暴露資料庫連線字串

### 正確做法
- [ ] 所有驗證邏輯放後端
- [ ] API Key 存放於後端環境變數 (.env)
- [ ] 使用後端代理呼叫第三方 API

---

## 2. 資料儲存

### 禁止事項
- [ ] 不要用前端變數當永久儲存
- [ ] 不要依賴 localStorage 存重要資料
- [ ] 不要用 Redis 當主資料庫（僅作快取用）

### 正確做法
- [ ] 使用正式資料庫（PostgreSQL / MySQL）
- [ ] 重要資料一定要寫入資料庫
- [ ] Redis 僅用於快取、Session

---

## 3. 頁面權限控管

### 禁止事項
- [ ] 不要讓任何人都能透過 URL 進入管理介面

### 正確做法
- [ ] 前端路由守衛（Vue Router beforeEach）
- [ ] 後端 API 必須驗證使用者身份與角色
- [ ] 管理頁面需登入 + 角色檢查

```javascript
// Vue Router 範例
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAdmin && !userStore.isAdmin) {
    next('/unauthorized')
  } else {
    next()
  }
})
```

---

## 4. API 權限設計

### 禁止事項
- [ ] 不要讓登入後的使用者能存取所有 API
- [ ] 不要只靠前端隱藏按鈕來限制功能

### 正確做法
- [ ] 每個 API 都要驗證 JWT/Session
- [ ] 實作角色權限（RBAC）
- [ ] 檢查資源擁有者（用戶只能改自己的資料）

```javascript
// API 權限檢查範例
app.put('/api/orders/:id', authenticate, async (req, res) => {
  const order = await Order.findById(req.params.id)

  // 檢查是否為本人或管理員
  if (order.userId !== req.user.id && !req.user.isAdmin) {
    return res.status(403).json({ error: '無權限' })
  }
  // ...
})
```

---

## 5. 審計日誌（Audit Log）

### 必須記錄
- [ ] 使用者登入/登出時間
- [ ] 重要操作（建立、修改、刪除）
- [ ] 操作者 ID、IP、時間戳
- [ ] 操作前後的資料變化

### 日誌欄位建議
```sql
CREATE TABLE audit_logs (
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  action VARCHAR(50),      -- 'CREATE', 'UPDATE', 'DELETE'
  entity VARCHAR(50),      -- 'order', 'user', 'product'
  entity_id INTEGER,
  old_value JSONB,
  new_value JSONB,
  ip_address VARCHAR(45),
  created_at TIMESTAMP DEFAULT NOW()
);
```

---

## 6. API 呼叫次數限制（Rate Limiting）

### 高風險 API 必須限制
- [ ] 登入 API（防暴力破解）
- [ ] 註冊 API（防機器人）
- [ ] 密碼重設 API
- [ ] 付款相關 API

### 建議限制
| API 類型 | 建議限制 |
|---------|---------|
| 登入 | 5 次/分鐘 |
| 註冊 | 3 次/小時 |
| 一般 API | 100 次/分鐘 |
| 密碼重設 | 3 次/小時 |

```javascript
// Express rate-limit 範例
const rateLimit = require('express-rate-limit')

const loginLimiter = rateLimit({
  windowMs: 60 * 1000, // 1 分鐘
  max: 5,
  message: '登入嘗試次數過多，請稍後再試'
})

app.post('/api/login', loginLimiter, loginController)
```

---

## 7. 密碼安全儲存

### 禁止事項
- [ ] **絕對不要**明文儲存密碼
- [ ] 不要用 MD5、SHA1（已不安全）

### 正確做法
- [ ] 使用 bcrypt 或 Argon2 雜湊
- [ ] 設定適當的 salt rounds（建議 10-12）

```javascript
const bcrypt = require('bcrypt')

// 註冊時雜湊密碼
const hashedPassword = await bcrypt.hash(password, 12)

// 登入時驗證
const isValid = await bcrypt.compare(inputPassword, hashedPassword)
```

---

## 8. 資料庫選擇

### 用途對照
| 用途 | 推薦資料庫 |
|-----|-----------|
| 主資料庫 | PostgreSQL / MySQL |
| 快取 | Redis |
| 搜尋 | Elasticsearch |
| 文件儲存 | MongoDB（非必要不用） |

### Redis 注意事項
- 僅作為快取層
- 設定持久化（RDB/AOF）作為備份
- 重要資料必須同步寫入主資料庫

---

## 9. Git 安全

### 絕對不要上傳
- [ ] `.env` 環境變數檔
- [ ] API Keys / Secrets
- [ ] 資料庫密碼
- [ ] SSL 憑證私鑰

### .gitignore 必須包含
```gitignore
# 環境變數
.env
.env.local
.env.production

# 依賴套件
node_modules/

# 建置產出
dist/
build/

# 日誌
*.log
logs/

# 系統檔案
.DS_Store
Thumbs.db

# IDE
.idea/
.vscode/

# 測試覆蓋率
coverage/
```

---

## 10. 其他安全措施

### SQL Injection 防護
- [ ] 使用 ORM（Prisma / Sequelize）
- [ ] 使用參數化查詢，不要字串拼接

### XSS 防護
- [ ] 前端輸出時做 HTML escape
- [ ] 設定 Content-Security-Policy header

### CSRF 防護
- [ ] 使用 CSRF Token
- [ ] 檢查 Origin / Referer header

### HTTPS
- [ ] 正式環境必須使用 HTTPS
- [ ] 設定 HSTS header

---

## 11. 環境與部署

### 環境分離
- [ ] 開發/測試/正式環境使用不同資料庫
- [ ] 各環境使用不同 API Key
- [ ] 正式環境的 .env 不要跟開發環境混用

### CORS 設定
```javascript
// 錯誤：允許所有來源
app.use(cors({ origin: '*' }))

// 正確：只允許自己的網域
app.use(cors({
  origin: ['https://yourdomain.com', 'https://admin.yourdomain.com'],
  credentials: true
}))
```

### 錯誤訊息處理
- [ ] 正式環境不回傳 stack trace
- [ ] 只回傳通用錯誤訊息給使用者
- [ ] 詳細錯誤記錄到日誌（不是回傳給前端）

```javascript
// 正式環境錯誤處理
app.use((err, req, res, next) => {
  console.error(err.stack) // 記錄到日誌

  if (process.env.NODE_ENV === 'production') {
    res.status(500).json({ error: '系統錯誤，請稍後再試' })
  } else {
    res.status(500).json({ error: err.message, stack: err.stack })
  }
})
```

---

## 12. 資料驗證

### 後端驗證（不要相信前端）
- [ ] 所有輸入都要在後端再驗證一次
- [ ] 檢查資料型別、長度、格式
- [ ] 使用驗證套件（Joi / Zod / class-validator）

```javascript
// Zod 驗證範例
const createUserSchema = z.object({
  email: z.string().email(),
  password: z.string().min(8).max(100),
  name: z.string().min(1).max(50)
})

app.post('/api/users', async (req, res) => {
  const result = createUserSchema.safeParse(req.body)
  if (!result.success) {
    return res.status(400).json({ errors: result.error.issues })
  }
  // ...
})
```

### 檔案上傳安全
- [ ] 限制允許的檔案類型（白名單）
- [ ] 限制檔案大小
- [ ] 重新命名檔案（不要用使用者提供的檔名）
- [ ] 存到獨立目錄，不要放在程式碼目錄

```javascript
const multer = require('multer')

const upload = multer({
  limits: { fileSize: 5 * 1024 * 1024 }, // 5MB
  fileFilter: (req, file, cb) => {
    const allowed = ['image/jpeg', 'image/png', 'image/webp']
    if (allowed.includes(file.mimetype)) {
      cb(null, true)
    } else {
      cb(new Error('不支援的檔案格式'))
    }
  }
})
```

---

## 13. 資料庫維護

### 備份機制
- [ ] 設定每日自動備份
- [ ] 備份存放到不同位置（雲端/異地）
- [ ] 定期測試還原流程（備份沒測過等於沒備份）

### 軟刪除
- [ ] 重要資料使用軟刪除，不要真的刪
- [ ] 加上 `deleted_at` 欄位

```sql
-- 軟刪除欄位
ALTER TABLE users ADD COLUMN deleted_at TIMESTAMP NULL;

-- 查詢時排除已刪除
SELECT * FROM users WHERE deleted_at IS NULL;

-- 刪除改為更新
UPDATE users SET deleted_at = NOW() WHERE id = 1;
```

---

## 14. Token 與 Session 安全

### JWT 設計
- [ ] Access Token 短效期（15-30 分鐘）
- [ ] Refresh Token 長效期（7-30 天）
- [ ] Refresh Token 存資料庫，可主動撤銷

### 登出機制
- [ ] 登出時將 Token 加入黑名單
- [ ] 或使用 Token 版本號，登出時遞增版本

```javascript
// Token 黑名單（存 Redis）
async function logout(req, res) {
  const token = req.headers.authorization?.split(' ')[1]
  const decoded = jwt.decode(token)
  const ttl = decoded.exp - Math.floor(Date.now() / 1000)

  await redis.setex(`blacklist:${token}`, ttl, '1')
  res.json({ message: '登出成功' })
}

// 驗證時檢查黑名單
async function authenticate(req, res, next) {
  const token = req.headers.authorization?.split(' ')[1]

  if (await redis.get(`blacklist:${token}`)) {
    return res.status(401).json({ error: 'Token 已失效' })
  }
  // ...
}
```

---

## 15. 監控與通知

### 健康檢查 API
```javascript
app.get('/health', async (req, res) => {
  try {
    await db.query('SELECT 1') // 檢查資料庫連線
    await redis.ping()         // 檢查 Redis 連線

    res.json({
      status: 'ok',
      timestamp: new Date().toISOString()
    })
  } catch (error) {
    res.status(503).json({
      status: 'error',
      message: error.message
    })
  }
})
```

### 錯誤通知
- [ ] 後端錯誤自動通知（Slack / Email / Line）
- [ ] 設定錯誤頻率警報（短時間大量錯誤）
- [ ] 監控 API 回應時間

```javascript
// 簡易 Slack 通知
async function notifyError(error, req) {
  await fetch(process.env.SLACK_WEBHOOK_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      text: `🚨 錯誤發生\n路徑: ${req.path}\n訊息: ${error.message}`
    })
  })
}
```

---

## 檢查清單總覽

開發完成後，逐項確認：

### 基本安全
- [ ] 前端沒有敏感資料
- [ ] 資料有正確存入資料庫
- [ ] 頁面有權限控管
- [ ] API 有驗證身份與角色
- [ ] 有審計日誌
- [ ] 高風險 API 有次數限制
- [ ] 密碼有雜湊處理
- [ ] 選用正確的資料庫
- [ ] .gitignore 設定完整
- [ ] 機密檔案沒有上傳 GitHub

### 進階安全
- [ ] 環境分離（開發/測試/正式）
- [ ] CORS 只允許自己的網域
- [ ] 正式環境不回傳詳細錯誤
- [ ] 後端有做資料驗證
- [ ] 檔案上傳有限制類型和大小
- [ ] 有資料庫備份機制
- [ ] 重要資料使用軟刪除
- [ ] JWT 有適當的過期時間
- [ ] 登出機制有效（Token 失效）
- [ ] 有健康檢查 API
- [ ] 錯誤有通知機制
