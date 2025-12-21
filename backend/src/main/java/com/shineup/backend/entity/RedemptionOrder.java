package com.shineup.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "redemption_orders")
public class RedemptionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 保留原本的單一禮品欄位（向後兼容舊訂單）
    @ManyToOne
    @JoinColumn(name = "gift_id")
    private Gift gift;

    // 新增：訂單明細（多個禮品）
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    private Integer quantity = 1;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED, COMPLETED, CANCELLED
    }

    // 便利方法：新增訂單明細
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
