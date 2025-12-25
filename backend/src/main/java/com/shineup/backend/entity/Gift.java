package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gifts")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String series;

    @Column(name = "level_text")
    private String levelText; // "EXPLORER", "CREATOR", "VISIONARY", "LUMINARY"

    @Column(name = "required_points")
    private Integer requiredPoints = 0;

    private Integer stock = 0;

    private String image;

    @Column(name = "market_price")
    private String marketPrice;

    @Column(name = "level_restriction")
    private String levelRestriction; // "lv1_only", "lv2_plus", "lv3_plus", "lv4_only"
}
