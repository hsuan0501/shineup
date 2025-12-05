package com.shineup.backend.service;

import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.GiftRepository;
import com.shineup.backend.repository.RedemptionOrderRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedemptionService {

    private final RedemptionOrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;

    public List<RedemptionOrder> findAll() {
        return orderRepository.findAll();
    }

    public List<RedemptionOrder> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<RedemptionOrder> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public RedemptionOrder createOrder(Long userId, Long giftId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("Gift not found"));

        // 檢查庫存
        if (gift.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        // 計算總點數
        int totalPoints = gift.getRequiredPoints() * quantity;

        // 檢查使用者點數
        if (user.getRewardPoints() < totalPoints) {
            throw new RuntimeException("Insufficient points");
        }

        // 扣除點數和庫存
        user.setRewardPoints(user.getRewardPoints() - totalPoints);
        gift.setStock(gift.getStock() - quantity);

        userRepository.save(user);
        giftRepository.save(gift);

        // 建立訂單
        RedemptionOrder order = new RedemptionOrder();
        order.setUser(user);
        order.setGift(gift);
        order.setQuantity(quantity);
        order.setTotalPoints(totalPoints);

        return orderRepository.save(order);
    }

    public RedemptionOrder updateStatus(Long id, RedemptionOrder.OrderStatus status) {
        RedemptionOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
