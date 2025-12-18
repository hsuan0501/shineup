package com.shineup.backend.controller;

import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.service.LineAuthService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth/line")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LineAuthController {

    private final LineAuthService lineAuthService;
    private final JwtUtil jwtUtil;

    // 取得 LINE 授權 URL
    @GetMapping("/auth-url")
    public ResponseEntity<?> getAuthUrl() {
        String authUrl = lineAuthService.getAuthorizationUrl();
        return ResponseEntity.ok(Map.of("url", authUrl));
    }

    // 處理 LINE callback（登入或註冊）
    @PostMapping("/callback")
    public ResponseEntity<AuthResponse> handleCallback(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body(AuthResponse.error("缺少授權碼"));
        }

        log.info("LINE callback received with code: {}", code.substring(0, Math.min(10, code.length())) + "...");

        AuthResponse response = lineAuthService.handleCallback(code);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 綁定 LINE 帳號到現有用戶
    @PostMapping("/bind")
    public ResponseEntity<?> bindLine(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> request) {

        String code = request.get("code");
        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "缺少授權碼"));
        }

        // 從 token 取得用戶 ID
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "無效的 token"));
        }

        log.info("LINE bind request for userId: {}", userId);

        var result = lineAuthService.bindLineToUser(userId, code);
        return ResponseEntity.ok(result);
    }

    // 解除 LINE 綁定
    @PostMapping("/unbind")
    public ResponseEntity<?> unbindLine(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "無效的 token"));
        }

        log.info("LINE unbind request for userId: {}", userId);

        var result = lineAuthService.unbindLine(userId);
        return ResponseEntity.ok(result);
    }
}
