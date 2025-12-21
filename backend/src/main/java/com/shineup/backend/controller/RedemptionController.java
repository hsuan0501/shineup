package com.shineup.backend.controller;

import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.service.RedemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RedemptionController {

    private final RedemptionService redemptionService;

    @GetMapping
    public List<RedemptionOrder> getAllOrders() {
        return redemptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RedemptionOrder> getOrderById(@PathVariable Long id) {
        return redemptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<RedemptionOrder> getOrdersByUser(@PathVariable Long userId) {
        return redemptionService.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<RedemptionOrder> createOrder(
            @RequestParam Long userId,
            @RequestParam Long giftId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(redemptionService.createOrder(userId, giftId, quantity));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RedemptionOrder> updateStatus(
            @PathVariable Long id,
            @RequestParam RedemptionOrder.OrderStatus status) {
        return ResponseEntity.ok(redemptionService.updateStatus(id, status));
    }
}
