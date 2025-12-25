package com.shineup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String address;

    private LocalDate birthday;  // 生日

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberLevel level = MemberLevel.EXPLORER;

    @Column(name = "upgrade_points")
    private Integer upgradePoints = 0;

    @Column(name = "reward_points")
    private Integer rewardPoints = 0;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "is_admin")
    private boolean admin = false;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled = true;

    @Column(name = "line_id", unique = true)
    private String lineId;

    @Column(name = "referral_code", unique = true)
    private String referralCode;  // 自己的推薦碼

    @Column(name = "referred_by_id")
    private Long referredById;    // 推薦人的用戶 ID

    @Column(name = "newsletter_subscribed")
    private Boolean newsletterSubscribed = false;  // 是否訂閱電子報

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum MemberLevel {
        EXPLORER, CREATOR, VISIONARY, LUMINARY
    }
}
