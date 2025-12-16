package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 活動紀錄 Entity
 * 記錄用戶的所有活動：登入、完成任務、兌換禮品等
 */
@Data
@Entity
@Table(name = "activity_records")
public class ActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 關聯用戶
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 活動類型：login, task, reward, streak, invite
    @Column(nullable = false)
    private String type;

    // 活動標題
    @Column(nullable = false)
    private String title;

    // 積分變化（正數=獲得，負數=消耗）
    @Column(nullable = false)
    private Integer points;

    // 活動時間
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
