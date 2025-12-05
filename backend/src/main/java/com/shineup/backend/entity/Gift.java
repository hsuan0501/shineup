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

    private String series;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_level", nullable = false)
    private User.MemberLevel requiredLevel = User.MemberLevel.EXPLORER;

    @Column(name = "required_points")
    private Integer requiredPoints = 0;

    private Integer stock = 0;

    private String image;
}
