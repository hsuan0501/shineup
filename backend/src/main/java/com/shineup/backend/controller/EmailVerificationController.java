package com.shineup.backend.controller;

import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.service.EmailService;
import com.shineup.backend.service.EmailVerificationService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 驗證信箱驗證碼
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        Long userId = Long.parseLong(request.get("userId"));

        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "請輸入驗證碼"
            ));
        }

        boolean success = emailVerificationService.verifyEmail(userId, code);

        if (success) {
            // 取得用戶資料
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // 產生新 Token
                String token = jwtUtil.generateToken(user.getId(), user.getEmail());
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "信箱驗證成功！",
                    "token", token,
                    "user", AuthResponse.success(token, user).getUser()
                ));
            }
        }

        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "驗證碼錯誤或已過期"
        ));
    }

    // 重新發送驗證碼
    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "用戶不存在"
            ));
        }

        User user = userOpt.get();

        if (user.isEmailVerified()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "此信箱已驗證"
            ));
        }

        String code = emailVerificationService.resendVerificationToken(userId);

        if (code != null) {
            try {
                emailService.sendVerificationEmail(user.getEmail(), user.getName(), code);
                log.info("Verification email resent to: {}", user.getEmail());
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "驗證碼已重新發送"
                ));
            } catch (Exception e) {
                log.error("Failed to resend verification email to: {}", user.getEmail(), e);
                return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "信件發送失敗，請稍後再試"
                ));
            }
        }

        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "發送失敗"
        ));
    }
}
