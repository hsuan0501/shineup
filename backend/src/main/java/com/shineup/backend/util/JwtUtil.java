package com.shineup.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration; // 預設 24 小時

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 產生 JWT Token
    public String generateToken(Long userId, String email) {
        return generateToken(userId, email, false);
    }

    // 產生 JWT Token（含管理員資訊）
    public String generateToken(Long userId, String email, boolean isAdmin) {
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("isAdmin", isAdmin)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // 從 Token 取得 Email
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // 從 Token 取得 UserId
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // 從 Token 取得是否為管理員
    public boolean isAdminFromToken(String token) {
        Boolean isAdmin = getClaims(token).get("isAdmin", Boolean.class);
        return isAdmin != null && isAdmin;
    }

    // 驗證 Token 是否有效
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 檢查 Token 是否過期
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
