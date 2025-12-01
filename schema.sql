-- ShineUp History Database Schema
-- Created: 2024-12-01
-- A gamified financial education platform with user profiles, task tracking, and reward system

-- ============================================================================
-- 1. USERS TABLE - Core user profile and account information
-- ============================================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    avatar VARCHAR(255),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    kycVerified BOOLEAN DEFAULT FALSE,
    bankAccountLinked BOOLEAN DEFAULT FALSE,
    INDEX idx_email (email),
    INDEX idx_createdAt (createdAt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 2. LEVEL_CONFIG TABLE - Reference table for level configuration
-- ============================================================================
CREATE TABLE IF NOT EXISTS level_config (
    id INT PRIMARY KEY,
    levelCode VARCHAR(20) UNIQUE NOT NULL,
    levelName VARCHAR(50) NOT NULL,
    levelNumber INT NOT NULL UNIQUE,
    minPoints INT NOT NULL,
    maxPoints INT NOT NULL,
    multiplier DECIMAL(3,2) NOT NULL,
    color VARCHAR(7) NOT NULL,
    bgColor VARCHAR(7) NOT NULL,
    gradientFrom VARCHAR(50) NOT NULL,
    gradientTo VARCHAR(50) NOT NULL,
    gradientFromHex VARCHAR(7) NOT NULL,
    gradientToHex VARCHAR(7) NOT NULL,
    INDEX idx_levelCode (levelCode),
    INDEX idx_levelNumber (levelNumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 3. USER_LEVELS TABLE - Tracks user's current level and dual-track points
-- ============================================================================
CREATE TABLE IF NOT EXISTS user_levels (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL UNIQUE,
    currentLevel VARCHAR(20) NOT NULL DEFAULT 'EXPLORER',
    levelPoints INT DEFAULT 0,
    rewardPoints INT DEFAULT 0,
    lastUpdated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (currentLevel) REFERENCES level_config(levelCode),
    INDEX idx_userId (userId),
    INDEX idx_currentLevel (currentLevel),
    INDEX idx_levelPoints (levelPoints),
    CONSTRAINT chk_levelPoints CHECK (levelPoints >= 0),
    CONSTRAINT chk_rewardPoints CHECK (rewardPoints >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 4. TASKS TABLE - Master task library/definitions
-- ============================================================================
CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    details LONGTEXT,
    category VARCHAR(20) NOT NULL,
    levelRequired VARCHAR(20) DEFAULT '全等級',
    levelPoints INT NOT NULL,
    rewardPoints INT NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    icon VARCHAR(10),
    image VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_category (category),
    INDEX idx_levelRequired (levelRequired),
    INDEX idx_active (active),
    CONSTRAINT chk_levelPoints_tasks CHECK (levelPoints > 0),
    CONSTRAINT chk_rewardPoints_tasks CHECK (rewardPoints > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 5. USER_TASK_PROGRESS TABLE - Tracks user completion of each task
-- ============================================================================
CREATE TABLE IF NOT EXISTS user_task_progress (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    taskId INT NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    completedAt DATETIME,
    completionCount INT DEFAULT 0,
    lastCompletedAt DATETIME,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (taskId) REFERENCES tasks(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_task (userId, taskId),
    INDEX idx_userId (userId),
    INDEX idx_taskId (taskId),
    INDEX idx_completed (completed),
    INDEX idx_completedAt (completedAt),
    CONSTRAINT chk_completionCount CHECK (completionCount >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 6. REWARDS TABLE - Master reward/gift catalog
-- ============================================================================
CREATE TABLE IF NOT EXISTS rewards (
    id INT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    details LONGTEXT,
    points INT NOT NULL,
    category VARCHAR(30) NOT NULL,
    series VARCHAR(30) NOT NULL,
    level VARCHAR(20) NOT NULL,
    image VARCHAR(255),
    stock INT NOT NULL DEFAULT 0,
    marketPrice VARCHAR(50),
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_category (category),
    INDEX idx_series (series),
    INDEX idx_level (level),
    INDEX idx_points (points),
    INDEX idx_active (active),
    CONSTRAINT chk_rewardPoints CHECK (points > 0),
    CONSTRAINT chk_stock CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- INDEXES AND CONSTRAINTS SUMMARY
-- ============================================================================
-- Foreign Keys:
--   user_levels.userId → users.id (CASCADE DELETE)
--   user_levels.currentLevel → level_config.levelCode
--   user_task_progress.userId → users.id (CASCADE Delete)
--   user_task_progress.taskId → tasks.id (CASCADE Delete)
--
-- Unique Constraints:
--   users.email (UNIQUE)
--   user_levels.userId (UNIQUE - one record per user)
--   user_task_progress (userId, taskId) - one progress record per user per task
--   level_config.levelCode (UNIQUE)
--   level_config.levelNumber (UNIQUE)
--
-- Check Constraints:
--   levelPoints >= 0
--   rewardPoints >= 0
--   tasks.levelPoints > 0
--   tasks.rewardPoints > 0
--   rewards.points > 0
--   rewards.stock >= 0
--
-- ============================================================================
