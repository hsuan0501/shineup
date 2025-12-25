package com.shineup.backend.service;

import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.dto.LoginRequest;
import com.shineup.backend.dto.RegisterRequest;
import com.shineup.backend.entity.LoginHistory;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserStats;
import com.shineup.backend.repository.LoginHistoryRepository;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ActivityRecordService activityRecordService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;
    private final AchievementService achievementService;

    // 註冊
    public AuthResponse register(RegisterRequest request) {
        // 檢查 Email 是否已存在
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.error("此 Email 已被註冊");
        }

        // 驗證推薦碼（如果有提供）
        User referrer = null;
        if (request.getReferralCode() != null && !request.getReferralCode().trim().isEmpty()) {
            Optional<User> referrerOpt = userRepository.findByReferralCode(request.getReferralCode().trim().toUpperCase());
            if (referrerOpt.isEmpty()) {
                return AuthResponse.error("推薦碼無效");
            }
            referrer = referrerOpt.get();
        }

        // 建立新用戶（預設未驗證）
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmailVerified(false);
        user.setReferralCode(generateReferralCode());  // 產生推薦碼
        if (referrer != null) {
            user.setReferredById(referrer.getId());    // 記錄推薦人
        }

        User savedUser = userRepository.save(user);

        // 處理推薦人獎勵
        if (referrer != null) {
            processReferralReward(referrer, savedUser);
        }

        // 產生並發送驗證碼
        String verificationCode = emailVerificationService.createVerificationToken(savedUser);
        try {
            emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getName(), verificationCode);
            log.info("Verification email sent to: {}", savedUser.getEmail());
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", savedUser.getEmail(), e);
        }

        // 回傳用戶 ID（不給 token，因為還沒驗證）
        return AuthResponse.builder()
                .success(true)
                .message("註冊成功，請查收驗證碼信件")
                .user(AuthResponse.UserInfo.builder()
                        .id(savedUser.getId())
                        .name(savedUser.getName())
                        .email(savedUser.getEmail())
                        .emailVerified(false)
                        .build())
                .build();
    }

    // 登入
    public AuthResponse login(LoginRequest request) {
        // 查找用戶
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return AuthResponse.error("Email 或密碼錯誤");
        }

        User user = userOpt.get();

        // 檢查帳號是否被停用
        if (Boolean.FALSE.equals(user.getEnabled())) {
            return AuthResponse.error("此帳號已被停用，請聯繫管理員");
        }

        // 驗證密碼
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Email 或密碼錯誤");
        }

        // 若用戶沒有推薦碼，自動產生一個（相容舊用戶）
        if (user.getReferralCode() == null || user.getReferralCode().isEmpty()) {
            user.setReferralCode(generateReferralCode());
            userRepository.save(user);
        }

        // 產生 Token（包含管理員資訊）
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.isAdmin());

        // 檢查是否為當天第一次登入
        LocalDate today = LocalDate.now();
        LocalDate lastLoginBefore = userService.getOrCreateStats(user.getId()).getLastLoginDate();
        boolean isFirstLoginToday = lastLoginBefore == null || !lastLoginBefore.equals(today);

        // 記錄登入統計（只有第一次登入才會更新）
        UserStats stats = userService.recordLogin(user.getId());

        // 只有當天第一次登入才加積分
        if (isFirstLoginToday) {
            // 每日登入 +1 積分
            activityRecordService.addRecord(user.getId(), "login", "完成每日登入", 1);

            // 連續登入七天 +5 積分（每 7 天觸發一次：7, 14, 21...）
            if (stats.getConsecutiveDays() > 0 && stats.getConsecutiveDays() % 7 == 0) {
                activityRecordService.addRecord(user.getId(), "streak", "連續登入七天", 5);
            }
        }

        return AuthResponse.success(token, user);
    }

    // 記錄登入歷史
    public void recordLoginHistory(Long userId, String ipAddress, String userAgent, String loginType) {
        LoginHistory history = new LoginHistory();
        history.setUserId(userId);
        history.setIpAddress(ipAddress);
        history.setUserAgent(userAgent);
        history.setLoginType(loginType != null ? loginType : "EMAIL");
        loginHistoryRepository.save(history);
    }

    // 透過 Token 取得用戶資料（僅驗證 token，不處理登入獎勵）
    public AuthResponse getUserByToken(String token) {
        try {
            if (!jwtUtil.validateToken(token)) {
                return AuthResponse.error("Token 無效或已過期");
            }

            Long userId = jwtUtil.getUserIdFromToken(token);
            Optional<User> userOpt = userRepository.findById(userId);

            if (userOpt.isEmpty()) {
                return AuthResponse.error("用戶不存在");
            }

            User user = userOpt.get();

            // 只回傳用戶資料，不處理每日登入邏輯
            // 每日登入獎勵已在 login() 方法中處理，避免重複發放
            return AuthResponse.success(token, user);
        } catch (Exception e) {
            return AuthResponse.error("Token 驗證失敗");
        }
    }

    /**
     * 產生唯一的推薦碼（8碼大寫英數）
     */
    private String generateReferralCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        } while (userRepository.findByReferralCode(code).isPresent());
        return code;
    }

    /**
     * 處理推薦人獎勵
     * 推薦成功：推薦人獲得 50 升級積分 + 75 獎勵積分
     */
    private void processReferralReward(User referrer, User newUser) {
        try {
            // 更新推薦人的邀請統計
            var stats = userService.getOrCreateStats(referrer.getId());
            stats.setFriendsInvited(stats.getFriendsInvited() + 1);

            // 給推薦人積分獎勵（任務 25：邀請朋友完成註冊）
            referrer.setUpgradePoints(referrer.getUpgradePoints() + 50);
            referrer.setRewardPoints(referrer.getRewardPoints() + 75);
            userRepository.save(referrer);

            // 記錄活動
            activityRecordService.addRecord(referrer.getId(), "referral",
                    "成功邀請好友 " + newUser.getName() + " 註冊", 50);

            // 檢查邀請成就（5人、10人）
            achievementService.checkInviteAchievements(referrer, stats.getFriendsInvited());

            log.info("用戶 {} 成功邀請 {} 註冊，獲得推薦獎勵", referrer.getName(), newUser.getName());
        } catch (Exception e) {
            log.error("處理推薦獎勵失敗: {}", e.getMessage());
        }
    }
}
