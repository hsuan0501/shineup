package com.shineup.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shineup.backend.dto.AuthResponse;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineAuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${line.channel-id}")
    private String channelId;

    @Value("${line.channel-secret}")
    private String channelSecret;

    @Value("${line.redirect-uri}")
    private String redirectUri;

    // 取得 LINE 授權 URL
    public String getAuthorizationUrl() {
        return "https://access.line.me/oauth2/v2.1/authorize" +
                "?response_type=code" +
                "&client_id=" + channelId +
                "&redirect_uri=" + redirectUri +
                "&state=shineup" +
                "&scope=profile%20openid%20email";
    }

    // 處理 LINE callback，用 code 換取用戶資料並登入/註冊
    public AuthResponse handleCallback(String code) {
        try {
            // 1. 用 code 換取 access token
            String accessToken = getAccessToken(code);
            if (accessToken == null) {
                return AuthResponse.error("無法取得 LINE access token");
            }

            // 2. 用 access token 取得用戶資料
            JsonNode profile = getProfile(accessToken);
            if (profile == null) {
                return AuthResponse.error("無法取得 LINE 用戶資料");
            }

            String lineId = profile.get("userId").asText();
            String name = profile.get("displayName").asText();
            // email 可能沒有（需要用戶授權）
            String email = profile.has("email") ? profile.get("email").asText() : null;

            log.info("LINE login - lineId: {}, name: {}, email: {}", lineId, name, email);

            // 3. 查找或建立用戶
            Optional<User> existingUser = userRepository.findByLineId(lineId);
            User user;

            if (existingUser.isPresent()) {
                // 已有 LINE 綁定的用戶，直接登入
                user = existingUser.get();
                log.info("LINE user found: {}", user.getName());
            } else {
                // 新用戶，自動註冊
                user = new User();
                user.setLineId(lineId);
                user.setName(name);
                user.setEmail(email); // 可能為 null
                user.setPassword(""); // LINE 用戶沒有密碼
                user.setEmailVerified(true); // LINE 登入視為已驗證
                user = userRepository.save(user);
                log.info("New LINE user created: {}", user.getName());
            }

            // 4. 產生 JWT token
            String token = jwtUtil.generateToken(user.getId(), user.getEmail() != null ? user.getEmail() : lineId);

            return AuthResponse.success(token, user);

        } catch (Exception e) {
            log.error("LINE login error", e);
            return AuthResponse.error("LINE 登入失敗：" + e.getMessage());
        }
    }

    // 用 code 換取 access token
    private String getAccessToken(String code) {
        try {
            String tokenUrl = "https://api.line.me/oauth2/v2.1/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("code", code);
            body.add("redirect_uri", redirectUri);
            body.add("client_id", channelId);
            body.add("client_secret", channelSecret);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("access_token").asText();
            }
        } catch (Exception e) {
            log.error("Failed to get LINE access token", e);
        }
        return null;
    }

    // 用 access token 取得用戶資料
    private JsonNode getProfile(String accessToken) {
        try {
            String profileUrl = "https://api.line.me/v2/profile";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    profileUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return objectMapper.readTree(response.getBody());
            }
        } catch (Exception e) {
            log.error("Failed to get LINE profile", e);
        }
        return null;
    }

    // 綁定 LINE 到現有用戶
    public Map<String, Object> bindLineToUser(Long userId, String code) {
        try {
            // 1. 用 code 換取 access token
            String accessToken = getAccessToken(code);
            if (accessToken == null) {
                return Map.of("success", false, "message", "無法取得 LINE access token");
            }

            // 2. 用 access token 取得用戶資料
            JsonNode profile = getProfile(accessToken);
            if (profile == null) {
                return Map.of("success", false, "message", "無法取得 LINE 用戶資料");
            }

            String lineId = profile.get("userId").asText();

            // 3. 檢查此 LINE ID 是否已被其他用戶綁定
            Optional<User> existingLineUser = userRepository.findByLineId(lineId);
            if (existingLineUser.isPresent()) {
                return Map.of("success", false, "message", "此 LINE 帳號已被其他用戶綁定");
            }

            // 4. 綁定到指定用戶
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return Map.of("success", false, "message", "用戶不存在");
            }

            User user = userOpt.get();
            user.setLineId(lineId);
            userRepository.save(user);

            log.info("LINE bound to user: {} -> lineId: {}", user.getName(), lineId);

            return Map.of("success", true, "message", "LINE 綁定成功", "lineId", lineId);

        } catch (Exception e) {
            log.error("LINE bind error", e);
            return Map.of("success", false, "message", "LINE 綁定失敗：" + e.getMessage());
        }
    }

    // 解除 LINE 綁定
    public Map<String, Object> unbindLine(Long userId) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return Map.of("success", false, "message", "用戶不存在");
            }

            User user = userOpt.get();

            if (user.getLineId() == null) {
                return Map.of("success", false, "message", "此帳號未綁定 LINE");
            }

            // 檢查用戶是否有其他登入方式（email + password）
            if (user.getEmail() == null || user.getPassword() == null || user.getPassword().isEmpty()) {
                return Map.of("success", false, "message", "無法解除綁定：此帳號僅使用 LINE 登入，請先設定 Email 和密碼");
            }

            user.setLineId(null);
            userRepository.save(user);

            log.info("LINE unbound from user: {}", user.getName());

            return Map.of("success", true, "message", "已解除 LINE 綁定");

        } catch (Exception e) {
            log.error("LINE unbind error", e);
            return Map.of("success", false, "message", "解除綁定失敗：" + e.getMessage());
        }
    }
}
