package com.shineup.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 訂單明細 - 一筆訂單可以包含多個禮品
 */
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private RedemptionOrder order;

    @ManyToOne
    @JoinColumn(name = "gift_id", nullable = false)
    private Gift gift;

    private Integer quantity = 1;

    // 該品項的積分小計
    @Column(name = "subtotal_points")
    private Integer subtotalPoints;
}
