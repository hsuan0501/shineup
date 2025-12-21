package com.shineup.backend.controller;

import com.shineup.backend.service.EmailService;
import com.shineup.backend.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final EmailService emailService;

    // 申請重設密碼（忘記密碼）
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "請輸入電子信箱"
            ));
        }

        // 驗證 Email 格式
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "電子信箱格式不正確"
            ));
        }

        var result = passwordResetService.requestPasswordReset(email);

        // 不管 Email 存不存在，都回傳成功（安全考量）
        if (result != null) {
            String resetUrl = "http://localhost:5173/reset-password?token=" + result.token();

            try {
                // 寄送重設密碼信件（帶入用戶名稱）
                emailService.sendPasswordResetEmail(email, result.userName(), resetUrl);
                log.info("Password reset email sent to: {}", email);
            } catch (Exception e) {
                log.error("Failed to send password reset email to: {}", email, e);
                return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "信件發送失敗，請稍後再試"
                ));
            }
        }

        // 不管 Email 存不存在，都回傳相同訊息（不透露是否存在）
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "若該信箱已註冊，將會收到重設連結"
        ));
    }

    // 驗證 Token 是否有效
    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean valid = passwordResetService.validateToken(token);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "valid", valid,
            "message", valid ? "Token 有效" : "Token 無效或已過期"
        ));
    }

    // 重設密碼
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "缺少 Token"
            ));
        }

        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "密碼至少需要 6 個字元"
            ));
        }

        boolean success = passwordResetService.resetPassword(token, newPassword);

        if (success) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "密碼重設成功，請使用新密碼登入"
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Token 無效或已過期，請重新申請"
            ));
        }
    }
}
