package com.shineup.backend.config;

import com.shineup.backend.entity.User;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 應用程式啟動時初始化資料
 * 自動建立預設管理員帳號和測試會員
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@shineup.com}")
    private String adminEmail;

    @Value("${admin.password:Admin123}")
    private String adminPassword;

    @Value("${admin.name:系統管理員}")
    private String adminName;

    // 預設測試密碼
    private static final String DEFAULT_PASSWORD = "Qwe1234";

    @Override
    public void run(String... args) {
        createDefaultAdmin();
        createDefaultUsers();
    }

    private void createDefaultAdmin() {
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("管理員帳號已存在: {}", adminEmail);
            return;
        }

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setName(adminName);
        admin.setAdmin(true);
        admin.setEmailVerified(true);
        admin.setLevel(User.MemberLevel.LUMINARY);
        admin.setUpgradePoints(99999);
        admin.setRewardPoints(99999);

        userRepository.save(admin);
        log.info("預設管理員帳號已建立: {} / {}", adminEmail, adminPassword);
    }

    private void createDefaultUsers() {
        // Hsuan - CREATOR 等級
        createUserIfNotExists(
            "hsuan0501@outlook.com",
            "Hsuan",
            "0912345678",
            User.MemberLevel.CREATOR,
            700,
            600
        );

        // Matcha - EXPLORER 等級
        createUserIfNotExists(
            "matcha1108@example.com",
            "Matcha",
            "0923456789",
            User.MemberLevel.EXPLORER,
            120,
            80
        );

        // May - EXPLORER 等級
        createUserIfNotExists(
            "may0529@example.com",
            "May",
            "0934567890",
            User.MemberLevel.EXPLORER,
            100,
            50
        );
    }

    private void createUserIfNotExists(String email, String name, String phone,
                                        User.MemberLevel level, int upgradePoints, int rewardPoints) {
        var existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            // 更新密碼確保格式正確
            User user = existingUser.get();
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            user.setAdmin(false);
            userRepository.save(user);
            log.info("會員帳號已更新密碼: {}", email);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setName(name);
        user.setPhone(phone);
        user.setLevel(level);
        user.setUpgradePoints(upgradePoints);
        user.setRewardPoints(rewardPoints);
        user.setEmailVerified(true);
        user.setAdmin(false);

        userRepository.save(user);
        log.info("預設會員帳號已建立: {} / {}", email, DEFAULT_PASSWORD);
    }
}
