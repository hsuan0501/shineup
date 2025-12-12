package com.shineup.backend.service;

import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.dto.LoginRequest;
import com.shineup.backend.dto.RegisterRequest;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

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

        // 記錄登入統計
        userService.recordLogin(user.getId());

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
