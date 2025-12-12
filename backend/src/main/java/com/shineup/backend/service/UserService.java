package com.shineup.backend.service;

import com.shineup.backend.dto.UserStatsDTO;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserStats;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.repository.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatsRepository userStatsRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User addPoints(Long userId, int upgradePoints, int rewardPoints) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUpgradePoints(user.getUpgradePoints() + upgradePoints);
        user.setRewardPoints(user.getRewardPoints() + rewardPoints);
        checkLevelUp(user);
        return userRepository.save(user);
    }

    private void checkLevelUp(User user) {
        int points = user.getUpgradePoints();
        // 等級門檻：0-249 EXPLORER, 250-749 CREATOR, 750-1499 VISIONARY, 1500+ LUMINARY
        if (points >= 1500) {
            user.setLevel(User.MemberLevel.LUMINARY);
        } else if (points >= 750) {
            user.setLevel(User.MemberLevel.VISIONARY);
        } else if (points >= 250) {
            user.setLevel(User.MemberLevel.CREATOR);
        } else {
            user.setLevel(User.MemberLevel.EXPLORER);
        }
    }

    // 重置用戶資料（展示用）- 重置為 matcha 初始狀態
    @Transactional
    public User resetUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // matcha 初始值：Lv2 CREATOR, 升級積分 700, 兌換積分 600
        user.setUpgradePoints(700);
        user.setRewardPoints(600);
        user.setLevel(User.MemberLevel.CREATOR);

        // 同時重置統計數據
        UserStats stats = getOrCreateStats(userId);
        stats.setTasksCompleted(9);
        stats.setConsecutiveDays(7);
        stats.setTotalLogins(7);
        stats.setRewardsRedeemed(1);
        stats.setFriendsInvited(0);
        stats.setLastLoginDate(LocalDate.now());
        userStatsRepository.save(stats);

        return userRepository.save(user);
    }

    // ============================================
    // 統計相關方法
    // ============================================

    // 取得用戶統計資料
    public UserStatsDTO getUserStats(Long userId) {
        return userStatsRepository.findByUserId(userId)
                .map(UserStatsDTO::fromEntity)
                .orElse(UserStatsDTO.empty());
    }

    // 取得或建立用戶統計
    private UserStats getOrCreateStats(Long userId) {
        return userStatsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    UserStats newStats = new UserStats();
                    newStats.setUser(user);
                    return userStatsRepository.save(newStats);
                });
    }

    // 記錄登入（更新連續登入天數和總登入次數）
    @Transactional
    public UserStats recordLogin(Long userId) {
        UserStats stats = getOrCreateStats(userId);
        LocalDate today = LocalDate.now();
        LocalDate lastLogin = stats.getLastLoginDate();

        // 更新總登入次數
        stats.setTotalLogins(stats.getTotalLogins() + 1);

        // 計算連續登入
        if (lastLogin == null) {
            // 首次登入
            stats.setConsecutiveDays(1);
        } else if (lastLogin.equals(today.minusDays(1))) {
            // 連續登入（昨天有登入）
            stats.setConsecutiveDays(stats.getConsecutiveDays() + 1);
        } else if (!lastLogin.equals(today)) {
            // 不連續，重置為 1
            stats.setConsecutiveDays(1);
        }
        // 如果是同一天重複登入，不改變連續天數

        stats.setLastLoginDate(today);
        return userStatsRepository.save(stats);
    }

    // 增加任務完成數
    @Transactional
    public UserStats incrementTasksCompleted(Long userId) {
        UserStats stats = getOrCreateStats(userId);
        stats.setTasksCompleted(stats.getTasksCompleted() + 1);
        return userStatsRepository.save(stats);
    }

    // 增加兌換數
    @Transactional
    public UserStats incrementRewardsRedeemed(Long userId) {
        UserStats stats = getOrCreateStats(userId);
        stats.setRewardsRedeemed(stats.getRewardsRedeemed() + 1);
        return userStatsRepository.save(stats);
    }

    // 增加邀請好友數
    @Transactional
    public UserStats incrementFriendsInvited(Long userId) {
        UserStats stats = getOrCreateStats(userId);
        stats.setFriendsInvited(stats.getFriendsInvited() + 1);
        return userStatsRepository.save(stats);
    }
}
