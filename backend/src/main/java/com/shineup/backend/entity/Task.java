package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private String category;

    @Column(name = "level_text")
    private String levelText; // "全等級", "Lv2+", "Lv3+" 等

    @Column(name = "upgrade_points")
    private Integer upgradePoints = 0;

    @Column(name = "reward_points")
    private Integer rewardPoints = 0;

    private String image;

    private String icon;

    private String frequency;

    private Boolean active = true;
}
