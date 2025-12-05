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
        if (points >= 5000) {
            user.setLevel(User.MemberLevel.LUMINARY);
        } else if (points >= 2000) {
            user.setLevel(User.MemberLevel.VISIONARY);
        } else if (points >= 500) {
            user.setLevel(User.MemberLevel.CREATOR);
        } else {
            user.setLevel(User.MemberLevel.EXPLORER);
        }
    }
}
