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

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_level", nullable = false)
    private User.MemberLevel requiredLevel = User.MemberLevel.EXPLORER;

    @Column(name = "upgrade_points")
    private Integer upgradePoints = 0;

    @Column(name = "reward_points")
    private Integer rewardPoints = 0;

    private String image;

    private Boolean active = true;
}
