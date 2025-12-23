package com.shineup.backend.controller;

import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.User;
import com.shineup.backend.service.RedemptionService;
import com.shineup.backend.service.UserService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class RedemptionController {

    private final RedemptionService redemptionService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 從 Authorization header 取得當前用戶 ID
    private Long getCurrentUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return jwtUtil.getUserIdFromToken(token);
    }

    // 檢查是否為管理員
    private boolean isAdmin(String authHeader) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) return false;

        return userService.findById(currentUserId)
                .map(User::isAdmin)
                .orElse(false);
    }

    // 檢查是否為本人或管理員
    private boolean isOwnerOrAdmin(String authHeader, Long targetUserId) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) return false;

        if (currentUserId.equals(targetUserId)) return true;

        return userService.findById(currentUserId)
                .map(User::isAdmin)
                .orElse(false);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以列出所有訂單
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可查看所有訂單"));
        }
        return ResponseEntity.ok(redemptionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 先取得訂單，檢查是否為本人的訂單
        var orderOpt = redemptionService.findById(id);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RedemptionOrder order = orderOpt.get();
        if (!isOwnerOrAdmin(authHeader, order.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的訂單"));
        }

        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String authHeader) {
        // 只能查看自己的訂單或管理員可查看任何人
        if (!isOwnerOrAdmin(authHeader, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的訂單"));
        }
        return ResponseEntity.ok(redemptionService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestParam Long userId,
            @RequestParam Long giftId,
            @RequestParam(defaultValue = "1") int quantity,
            @RequestHeader("Authorization") String authHeader) {
        // 只能為自己建立訂單或管理員可代建
        if (!isOwnerOrAdmin(authHeader, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能為自己兌換禮品"));
        }
        return ResponseEntity.ok(redemptionService.createOrder(userId, giftId, quantity));
    }

    /**
     * 批次兌換多個禮品（合併成一筆訂單）
     * Request body: { "userId": 1, "items": [{ "giftId": 1, "quantity": 2 }, ...], "recipientName": "...", ... }
     */
    @PostMapping("/batch")
    public ResponseEntity<?> createBatchOrder(
            @RequestBody Map<String, Object> request,
            @RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = ((Number) request.get("userId")).longValue();

            // 只能為自己建立訂單或管理員可代建
            if (!isOwnerOrAdmin(authHeader, userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "無權限：只能為自己兌換禮品"));
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

            // 取得收件資訊
            String recipientName = (String) request.get("recipientName");
            String recipientPhone = (String) request.get("recipientPhone");
            String deliveryMethod = (String) request.get("deliveryMethod");
            String deliveryAddress = (String) request.get("deliveryAddress");
            String storeBrand = (String) request.get("storeBrand");
            String storeName = (String) request.get("storeName");

            RedemptionOrder order = redemptionService.createBatchOrder(
                    userId, items, recipientName, recipientPhone,
                    deliveryMethod, deliveryAddress, storeBrand, storeName);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam RedemptionOrder.OrderStatus status,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以更新訂單狀態
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可更新訂單狀態"));
        }
        return ResponseEntity.ok(redemptionService.updateStatus(id, status));
    }
}
