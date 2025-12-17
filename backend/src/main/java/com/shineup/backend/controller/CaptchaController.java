package com.shineup.backend.controller;

import com.shineup.backend.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    // 產生驗證碼圖片
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCaptcha() {
        return ResponseEntity.ok(captchaService.generateCaptcha());
    }

    // 驗證驗證碼
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyCaptcha(@RequestBody Map<String, String> request) {
        String captchaId = request.get("captchaId");
        String captchaCode = request.get("captchaCode");
        boolean valid = captchaService.verifyCaptcha(captchaId, captchaCode);
        return ResponseEntity.ok(Map.of("valid", valid));
    }
}
