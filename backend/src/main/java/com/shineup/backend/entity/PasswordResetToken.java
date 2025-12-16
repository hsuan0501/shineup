package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean used = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 檢查是否過期
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    // 檢查是否有效（未使用且未過期）
    public boolean isValid() {
        return !used && !isExpired();
    }
}
