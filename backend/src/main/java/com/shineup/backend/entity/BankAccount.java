package com.shineup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "bank_code", nullable = false)
    private String bankCode;      // 銀行代碼（如 004, 812）

    @Column(name = "bank_name", nullable = false)
    private String bankName;      // 銀行名稱（如 台灣銀行）

    @Column(name = "account_number", nullable = false)
    private String accountNumber; // 完整帳號

    @Column(name = "account_name", nullable = false)
    private String accountName;   // 戶名

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // 用於返回給前端的用戶 ID
    @Transient
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    // 帳號遮罩顯示（只顯示末四碼）
    @Transient
    public String getMaskedAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        int len = accountNumber.length();
        return "*".repeat(len - 4) + accountNumber.substring(len - 4);
    }
}
