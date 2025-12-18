package com.shineup.backend.service;

import com.shineup.backend.entity.EmailVerificationToken;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.EmailVerificationTokenRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    // 驗證碼有效期限（10 分鐘）
    private static final int TOKEN_EXPIRY_MINUTES = 10;

    // 產生 6 位數驗證碼
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // 建立驗證碼
    @Transactional
    public String createVerificationToken(User user) {
        // 刪除該用戶舊的驗證碼
        tokenRepository.deleteByUserId(user.getId());

        // 產生新驗證碼
        String code = generateVerificationCode();

        EmailVerificationToken token = new EmailVerificationToken();
        token.setToken(code);
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));

        tokenRepository.save(token);

        return code;
    }

    // 驗證驗證碼
    @Transactional
    public boolean verifyEmail(Long userId, String code) {
        Optional<EmailVerificationToken> tokenOpt = tokenRepository.findByUserIdAndUsedFalse(userId);

        if (tokenOpt.isEmpty()) {
            return false;
        }

        EmailVerificationToken token = tokenOpt.get();

        // 檢查驗證碼是否正確且有效
        if (!token.getToken().equals(code) || !token.isValid()) {
            return false;
        }

        // 標記驗證碼已使用
        token.setUsed(true);
        tokenRepository.save(token);

        // 更新用戶為已驗證
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    // 重新發送驗證碼
    @Transactional
    public String resendVerificationToken(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        if (user.isEmailVerified()) {
            return null; // 已驗證，不需要重發
        }

        return createVerificationToken(user);
    }
}
