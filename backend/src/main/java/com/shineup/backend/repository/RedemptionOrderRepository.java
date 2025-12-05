package com.shineup.backend.repository;

import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.RedemptionOrder.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RedemptionOrderRepository extends JpaRepository<RedemptionOrder, Long> {
    List<RedemptionOrder> findByUserId(Long userId);
    List<RedemptionOrder> findByStatus(OrderStatus status);
}
