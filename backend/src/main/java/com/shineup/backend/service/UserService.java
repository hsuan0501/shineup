package com.shineup.backend.service;

import com.shineup.backend.entity.User;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
    public User resetUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // matcha 初始值：Lv2 CREATOR, 升級積分 480, 兌換積分 600
        user.setUpgradePoints(480);
        user.setRewardPoints(600);
        user.setLevel(User.MemberLevel.CREATOR);
        return userRepository.save(user);
    }
}
