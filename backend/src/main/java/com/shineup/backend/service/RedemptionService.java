package com.shineup.backend.service;

import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.OrderItem;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.GiftRepository;
import com.shineup.backend.repository.RedemptionOrderRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedemptionService {

    private final RedemptionOrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;
    private final ActivityRecordService activityRecordService;

    public List<RedemptionOrder> findAll() {
        return orderRepository.findAll();
    }

    public List<RedemptionOrder> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<RedemptionOrder> findById(Long id) {
        return orderRepository.findById(id);
    }

    // 原本的單一禮品兌換（保留向後兼容）
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

        RedemptionOrder savedOrder = orderRepository.save(order);

        // 新增活動紀錄（兌換禮品）
        activityRecordService.addRecord(userId, "reward", "兌換 " + gift.getTitle(), -totalPoints);

        return savedOrder;
    }

    /**
     * 批次兌換多個禮品（合併成一筆訂單）- 無收件資訊版本（向後兼容）
     */
    @Transactional
    public RedemptionOrder createBatchOrder(Long userId, List<Map<String, Object>> items) {
        return createBatchOrder(userId, items, null, null, null, null, null, null);
    }

    /**
     * 批次兌換多個禮品（合併成一筆訂單）
     * @param userId 用戶 ID
     * @param items 禮品清單，格式為 [{giftId, quantity}, ...]
     * @param recipientName 收件人姓名
     * @param recipientPhone 收件人電話
     * @param deliveryMethod 寄送方式 (home/store)
     * @param deliveryAddress 收件地址
     * @param storeBrand 超商品牌
     * @param storeName 門市名稱
     * @return 建立的訂單
     */
    @Transactional
    public RedemptionOrder createBatchOrder(Long userId, List<Map<String, Object>> items,
            String recipientName, String recipientPhone, String deliveryMethod,
            String deliveryAddress, String storeBrand, String storeName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 預先載入所有禮品（避免 N+1 查詢）
        Map<Long, Gift> giftCache = new HashMap<>();
        for (Map<String, Object> item : items) {
            Long giftId = ((Number) item.get("giftId")).longValue();
            Gift gift = giftRepository.findById(giftId)
                    .orElseThrow(() -> new RuntimeException("Gift not found: " + giftId));
            giftCache.put(giftId, gift);
        }

        // 計算總積分並驗證庫存
        int totalPoints = 0;
        for (Map<String, Object> item : items) {
            Long giftId = ((Number) item.get("giftId")).longValue();
            int quantity = ((Number) item.get("quantity")).intValue();
            Gift gift = giftCache.get(giftId);

            if (gift.getStock() < quantity) {
                throw new RuntimeException("庫存不足: " + gift.getTitle());
            }

            totalPoints += gift.getRequiredPoints() * quantity;
        }

        // 檢查使用者點數
        if (user.getRewardPoints() < totalPoints) {
            throw new RuntimeException("積分不足");
        }

        // 建立訂單
        RedemptionOrder order = new RedemptionOrder();
        order.setUser(user);
        order.setTotalPoints(totalPoints);

        // 設定收件資訊
        order.setRecipientName(recipientName);
        order.setRecipientPhone(recipientPhone);
        order.setDeliveryMethod(deliveryMethod);
        order.setDeliveryAddress(deliveryAddress);
        order.setStoreBrand(storeBrand);
        order.setStoreName(storeName);

        // 計算總數量
        int totalQuantity = 0;

        // 建立訂單明細並扣除庫存
        for (Map<String, Object> item : items) {
            Long giftId = ((Number) item.get("giftId")).longValue();
            int quantity = ((Number) item.get("quantity")).intValue();
            Gift gift = giftCache.get(giftId);

            // 扣除庫存
            gift.setStock(gift.getStock() - quantity);
            giftRepository.save(gift);

            // 建立訂單明細
            OrderItem orderItem = new OrderItem();
            orderItem.setGift(gift);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotalPoints(gift.getRequiredPoints() * quantity);
            order.addItem(orderItem);

            totalQuantity += quantity;

            // 設定第一個禮品為主要禮品（資料庫相容）
            if (order.getGift() == null) {
                order.setGift(gift);
            }
        }

        order.setQuantity(totalQuantity);

        // 扣除用戶積分
        user.setRewardPoints(user.getRewardPoints() - totalPoints);
        userRepository.save(user);

        RedemptionOrder savedOrder = orderRepository.save(order);

        // 新增活動紀錄（每個禮品一條）
        for (Map<String, Object> item : items) {
            Long giftId = ((Number) item.get("giftId")).longValue();
            int quantity = ((Number) item.get("quantity")).intValue();
            Gift gift = giftCache.get(giftId);
            int itemPoints = gift.getRequiredPoints() * quantity;
            String title = "兌換 " + gift.getTitle();
            if (quantity > 1) {
                title += " x" + quantity;
            }
            activityRecordService.addRecord(userId, "reward", title, -itemPoints);
        }

        return savedOrder;
    }

    public RedemptionOrder updateStatus(Long id, RedemptionOrder.OrderStatus status) {
        RedemptionOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
