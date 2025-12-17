package com.shineup.backend.service;

import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.dto.LoginRequest;
import com.shineup.backend.dto.RegisterRequest;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserStats;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ActivityRecordService activityRecordService;

    // 註冊
    public AuthResponse register(RegisterRequest request) {
        // 檢查 Email 是否已存在
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.error("此 Email 已被註冊");
        }

        // 建立新用戶
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());

        User savedUser = userRepository.save(user);

        // 產生 Token
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail());

        return AuthResponse.success(token, savedUser);
    }

    // 登入
    public AuthResponse login(LoginRequest request) {
        // 查找用戶
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return AuthResponse.error("Email 或密碼錯誤");
        }

        User user = userOpt.get();

        // 驗證密碼
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Email 或密碼錯誤");
        }

        // 產生 Token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        // 檢查是否為當天第一次登入
        LocalDate today = LocalDate.now();
        LocalDate lastLoginBefore = userService.getOrCreateStats(user.getId()).getLastLoginDate();
        boolean isFirstLoginToday = lastLoginBefore == null || !lastLoginBefore.equals(today);

        // 記錄登入統計（只有第一次登入才會更新）
        UserStats stats = userService.recordLogin(user.getId());

        // 只有當天第一次登入才加積分
        if (isFirstLoginToday) {
            // 每日登入 +5 積分
            activityRecordService.addRecord(user.getId(), "login", "完成每日登入", 5);

            // 連續登入七天 +10 積分（每 7 天觸發一次：7, 14, 21...）
            if (stats.getConsecutiveDays() > 0 && stats.getConsecutiveDays() % 7 == 0) {
                activityRecordService.addRecord(user.getId(), "streak", "連續登入七天", 10);
            }
        }

        return AuthResponse.success(token, user);
    }

    // 透過 Token 取得用戶資料
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

            return AuthResponse.success(token, userOpt.get());
        } catch (Exception e) {
            return AuthResponse.error("Token 驗證失敗");
        }
    }
}
