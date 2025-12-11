package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_levels")
public class UserLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "current_level", nullable = false)
    private String currentLevel = "EXPLORER";

    @Column(name = "level_points")
    private Integer levelPoints = 0;

    @Column(name = "reward_points")
    private Integer rewardPoints = 0;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
