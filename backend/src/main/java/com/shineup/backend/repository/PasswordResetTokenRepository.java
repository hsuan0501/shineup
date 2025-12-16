package com.shineup.backend.repository;

import com.shineup.backend.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    // 刪除某用戶的所有重設 Token（申請新的時清除舊的）
    void deleteByUserId(Long userId);
}
