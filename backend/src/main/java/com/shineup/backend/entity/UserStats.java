package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_stats")
public class UserStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 已完成任務數
    @Column(name = "tasks_completed")
    private Integer tasksCompleted = 0;

    // 連續登入天數
    @Column(name = "consecutive_days")
    private Integer consecutiveDays = 0;

    // 總登入次數
    @Column(name = "total_logins")
    private Integer totalLogins = 0;

    // 已兌換禮品數
    @Column(name = "rewards_redeemed")
    private Integer rewardsRedeemed = 0;

    // 邀請好友數
    @Column(name = "friends_invited")
    private Integer friendsInvited = 0;

    // 最後登入日期（用於計算連續登入）
    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    // 建立時間
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // 更新時間
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
