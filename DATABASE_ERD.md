# ShineUp History - Database Design (ERD)

## Overview
This database design represents a gamified financial education platform with user profiles, task completion tracking, reward system, and user level management. The database uses 6 main tables to track user engagement, learning progress, and reward redemption.

---

## Table Structure

### 1. **users** - User Account Information
Stores basic user profile and account details.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | Unique user identifier |
| name | VARCHAR(100) | NOT NULL | User display name |
| email | VARCHAR(100) | UNIQUE, NOT NULL | User email address |
| avatar | VARCHAR(255) | | Avatar image URL |
| createdAt | DATETIME | DEFAULT CURRENT_TIMESTAMP | Account creation date |
| kycVerified | BOOLEAN | DEFAULT FALSE | Know-Your-Customer verification status |
| bankAccountLinked | BOOLEAN | DEFAULT FALSE | Whether bank account is linked |

**Purpose**: Store core user profile data. Links to all other tables through user_id.

---

### 2. **user_levels** - User Level Progress Tracking
Tracks each user's current level and accumulated points across two point systems.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | Unique record identifier |
| userId | INT | FOREIGN KEY → users.id | Reference to user |
| currentLevel | VARCHAR(20) | NOT NULL | Current level (EXPLORER/CREATOR/VISIONARY/LUMINARY) |
| levelPoints | INT | DEFAULT 0 | Points accumulated for level progression (cannot decrease) |
| rewardPoints | INT | DEFAULT 0 | Points available for reward redemption (can decrease) |
| lastUpdated | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE | Last modification timestamp |

**Purpose**: Maintain dual-track point system. Level points determine user tier, reward points enable gift exchanges.
**Constraints**:
- levelPoints: 0-249 (Explorer), 250-749 (Creator), 750-1499 (Visionary), 1500+ (Luminary)
- rewardPoints: Used for redemptions, can be deducted

---

### 3. **level_config** - Level Configuration Reference
Stores configuration data for each level tier (read-only reference table).

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY | Level identifier (1-4) |
| levelCode | VARCHAR(20) | UNIQUE, NOT NULL | Level code (EXPLORER/CREATOR/VISIONARY/LUMINARY) |
| levelName | VARCHAR(50) | NOT NULL | Display name in Chinese (探索者/創造者/先行者/閃耀者) |
| levelNumber | INT | NOT NULL | Level tier number (1-4) |
| minPoints | INT | NOT NULL | Minimum level points to achieve this level |
| maxPoints | INT | NOT NULL | Maximum level points for this level |
| multiplier | DECIMAL(3,2) | NOT NULL | Points multiplier for this level (1.0 to 1.7) |
| color | VARCHAR(7) | NOT NULL | Hex color code for UI display |
| bgColor | VARCHAR(7) | NOT NULL | Background color for UI display |
| gradientFrom | VARCHAR(50) | NOT NULL | Tailwind gradient start (e.g., from-emerald-300) |
| gradientTo | VARCHAR(50) | NOT NULL | Tailwind gradient end (e.g., to-emerald-600) |
| gradientFromHex | VARCHAR(7) | NOT NULL | Gradient start hex color |
| gradientToHex | VARCHAR(7) | NOT NULL | Gradient end hex color |

**Purpose**: Reference table for level configuration. Avoids data duplication and allows flexible level updates.

---

### 4. **tasks** - Task Definition Library
Stores all available tasks in the system.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY | Task unique identifier (1-30) |
| title | VARCHAR(100) | NOT NULL | Task display title |
| description | VARCHAR(255) | NOT NULL | Short task description (1-2 sentences) |
| details | TEXT | | Long task description for detail modal |
| category | VARCHAR(20) | NOT NULL | Task category (daily/financial/investment/esg/social) |
| levelRequired | VARCHAR(20) | DEFAULT '全等級' | Minimum level required (全等級/Lv2+/Lv3+) |
| levelPoints | INT | NOT NULL | Points awarded for level progression |
| rewardPoints | INT | NOT NULL | Points awarded for reward system |
| frequency | VARCHAR(50) | NOT NULL | Completion frequency (每日/一次性/可重複/每週最多2個/每月最多3次/無限) |
| icon | VARCHAR(10) | | Emoji icon representation |
| image | VARCHAR(255) | | Task image URL |
| active | BOOLEAN | DEFAULT TRUE | Whether task is active |

**Purpose**: Master task list. All tasks across all users reference these definitions.

---

### 5. **user_task_progress** - Task Completion Tracking
Tracks which tasks each user has completed and when.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | Unique progress record |
| userId | INT | FOREIGN KEY → users.id | Reference to user |
| taskId | INT | FOREIGN KEY → tasks.id | Reference to task |
| completed | BOOLEAN | DEFAULT FALSE | Whether task is completed |
| completedAt | DATETIME | | Completion timestamp |
| completionCount | INT | DEFAULT 0 | Number of times completed (for repeatable tasks) |
| lastCompletedAt | DATETIME | | Last completion date (for tracking frequency) |
| createdAt | DATETIME | DEFAULT CURRENT_TIMESTAMP | Record creation date |

**Purpose**: Track individual user task progress, support repeatable tasks with frequency limits.

---

### 6. **rewards** - Reward/Gift Library
Stores all available gifts that users can redeem with reward points.

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY | Reward unique identifier (1-32) |
| title | VARCHAR(150) | NOT NULL | Gift name |
| description | VARCHAR(255) | | Short description (market price reference) |
| details | TEXT | | Full product details and description |
| points | INT | NOT NULL | Points required to redeem |
| category | VARCHAR(30) | NOT NULL | Category (sustainable/quality/aesthetic/premium) |
| series | VARCHAR(30) | NOT NULL | Product series name |
| level | VARCHAR(20) | NOT NULL | Level restriction (EXPLORER/CREATOR/VISIONARY/LUMINARY) |
| image | VARCHAR(255) | | Product image URL |
| stock | INT | NOT NULL | Current stock quantity |
| marketPrice | VARCHAR(50) | | Actual market price display (NT$XXX) |
| active | BOOLEAN | DEFAULT TRUE | Whether reward is available |

**Purpose**: Master reward/gift catalog. Reference table for redemptions.

---

## Entity Relationship Diagram (Text Representation)

```
┌─────────────────────┐
│      users          │
├─────────────────────┤
│ id (PK)             │
│ name                │
│ email (UNIQUE)      │
│ avatar              │
│ createdAt           │
│ kycVerified         │
│ bankAccountLinked   │
└──────────┬──────────┘
           │
     ┌─────┴─────┬──────────────┬──────────────┐
     │            │              │              │
     ▼            ▼              ▼              ▼
┌──────────────┐ ┌─────────────────┐ ┌────────────────────┐
│user_levels   │ │user_task_progress│ │reward_redemptions  │
├──────────────┤ ├─────────────────┤ └────────────────────┘
│id (PK)       │ │id (PK)          │ (Future table)
│userId (FK)──┼─│userId (FK)──────┼────┐
│currentLevel  │ │taskId (FK)      │    │
│levelPoints   │ │completed        │    │
│rewardPoints  │ │completedAt      │    │
│lastUpdated   │ │completionCount  │    │
└──────────────┘ └─────────────────┘    │
     │                   │                │
     │                   ▼                │
     │            ┌────────────┐         │
     │            │   tasks    │         │
     │            ├────────────┤         │
     │            │ id (PK)    │         │
     │            │ title      │         │
     │            │ description│         │
     │            │ category   │         │
     │            │ levelPoints│         │
     │            │ rewardPoints         │
     │            │ frequency  │         │
     │            └────────────┘         │
     │                                   │
     ▼                                   ▼
┌─────────────────┐             ┌──────────────┐
│ level_config    │             │  rewards     │
├─────────────────┤             ├──────────────┤
│ id (PK)         │             │ id (PK)      │
│ levelCode       │             │ title        │
│ levelName       │             │ description  │
│ levelNumber     │             │ points       │
│ minPoints       │             │ category     │
│ maxPoints       │             │ series       │
│ multiplier      │             │ level        │
│ colors/gradients│             │ image        │
└─────────────────┘             │ stock        │
                                └──────────────┘
```

---

## Relationships Explained

### 1. **users → user_levels (1:1)**
Each user has exactly one current level record. When a user gains levelPoints, the currentLevel is updated based on point thresholds.

### 2. **users → user_task_progress (1:Many)**
Each user can have multiple task progress records (one per task). Tracks completion status and frequency.

### 3. **tasks → user_task_progress (1:Many)**
Each task can be tracked for multiple users. Common task definitions avoid duplication.

### 4. **level_config (Reference)**
Never directly linked to users; instead, values in user_levels.currentLevel are matched against level_config.levelCode for lookups.

### 5. **rewards (Reference)**
Independent table; future reward_redemptions table will link users to rewards they've claimed.

---

## Data Flow Examples

### Scenario 1: User Completes a Task
1. User completes task ID 1 ("每日登入")
2. `user_task_progress` record created/updated with `completed: true`, `completedAt: NOW()`
3. Application adds 5 levelPoints + 10 rewardPoints to `user_levels` record
4. Application checks if levelPoints now ≥ 250 and updates `currentLevel` to 'CREATOR'

### Scenario 2: User Redeems a Gift
1. User selects reward ID 9 (ekax 雲朵筆電包) costing 220 points
2. Application checks `user_levels.rewardPoints >= 220` (must be true)
3. Deduct 220 from rewardPoints: `rewardPoints = rewardPoints - 220`
4. Create redemption record (future enhancement) or send to order processing
5. Decrease `rewards.stock` by 1

### Scenario 3: Level Configuration Update
1. Admin wants to change Level 2 multiplier from 1.15 to 1.2
2. Update `level_config` record where `levelCode = 'CREATOR'`
3. Going forward, tasks completed by Creator-level users grant 1.2x multiplier
4. Existing user_levels records not affected (multiplier applied at task completion time)

---

## Key Design Decisions

### Why 6 Tables?
- **users**: Core profile
- **user_levels**: Dual-point system tracking
- **level_config**: Level definitions (reference/configuration)
- **tasks**: Task library (reference)
- **user_task_progress**: Completion tracking (transactional)
- **rewards**: Gift catalog (reference)

### Why Separate level_config?
Avoids storing level data in user_levels row, allowing flexible level configuration without affecting individual user records.

### Why Store Both levelPoints and rewardPoints?
- **levelPoints**: Cumulative, never decreases, determines user tier
- **rewardPoints**: Can decrease through redemptions, represents current balance
- This dual-track system matches the business logic (不能減 vs 可扣除)

### Image Storage Strategy
- Store image URLs (strings) in database, not binary files
- Keep actual images in `/public/images/tasks/` and `/public/images/gifts/`
- This allows fast database operations and easy image replacement

---

## Data Volumes (Current)
- Tasks: 30 records
- Rewards: 32 records
- Levels: 4 records
- Mock Users: 1 record (can extend)

## Future Enhancements
1. **reward_redemptions table**: Track redemption history with order status
2. **task_categories reference table**: Normalize task categories
3. **user_stats table**: Cache computed statistics (tasksCompleted, consecutiveDays, etc.)
4. **points_history table**: Audit trail of all point changes
5. **notifications table**: Toast notifications and achievement notifications
