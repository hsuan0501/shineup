package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 客服快速回覆設定
 */
@Data
@Entity
@Table(name = "chatbot_replies")
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotReply {

    @Id
    private String id; // 類型名稱，如 shipping, points, level

    @Column(nullable = false)
    private String keyword; // 關鍵字，用 | 分隔

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reply; // 回覆內容

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
