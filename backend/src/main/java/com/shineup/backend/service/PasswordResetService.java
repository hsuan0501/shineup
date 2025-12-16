package com.shineup.backend.service;

import com.shineup.backend.entity.PasswordResetToken;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.PasswordResetTokenRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Token 有效期限（30 分鐘）
    private static final int TOKEN_EXPIRY_MINUTES = 30;

    // 申請重設密碼 - 產生 Token
    @Transactional
    public String requestPasswordReset(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            // 為了安全，不透露 Email 是否存在
            return null;
        }

        User user = userOpt.get();

        // 刪除該用戶舊的 Token
        tokenRepository.deleteByUserId(user.getId());

        // 產生新 Token
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));

        tokenRepository.save(resetToken);

        return token;
    }

    // 驗證 Token 是否有效
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        return tokenOpt.isPresent() && tokenOpt.get().isValid();
    }

    // 重設密碼
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            return false;
        }

        PasswordResetToken resetToken = tokenOpt.get();

        if (!resetToken.isValid()) {
            return false;
        }

        // 更新密碼
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // 標記 Token 已使用
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return true;
    }
}
