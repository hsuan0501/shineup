package com.shineup.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
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

    @Column(name = "line_id", unique = true)
    private String lineId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum MemberLevel {
        EXPLORER, CREATOR, VISIONARY, LUMINARY
    }
}
