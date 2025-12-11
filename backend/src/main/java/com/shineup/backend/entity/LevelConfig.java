package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "level_config")
public class LevelConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level_code", nullable = false, unique = true)
    private String levelCode;

    @Column(name = "level_name", nullable = false)
    private String levelName;

    @Column(name = "level_number", nullable = false)
    private Integer levelNumber;

    @Column(name = "min_points", nullable = false)
    private Integer minPoints;

    @Column(name = "max_points", nullable = false)
    private Integer maxPoints;

    @Column(nullable = false)
    private Double multiplier = 1.0;
}
