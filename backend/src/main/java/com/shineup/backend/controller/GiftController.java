package com.shineup.backend.controller;

import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GiftController {

    private final GiftService giftService;

    @GetMapping
    public List<Gift> getAllGifts() {
        return giftService.findAll();
    }

    @GetMapping("/in-stock")
    public List<Gift> getGiftsInStock() {
        return giftService.findInStock();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gift> getGiftById(@PathVariable Long id) {
        return giftService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/series/{series}")
    public List<Gift> getGiftsBySeries(@PathVariable String series) {
        return giftService.findBySeries(series);
    }

    @PostMapping
    public Gift createGift(@RequestBody Gift gift) {
        return giftService.save(gift);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gift> updateGift(@PathVariable Long id, @RequestBody Gift gift) {
        return giftService.findById(id)
                .map(existingGift -> {
                    gift.setId(id);
                    return ResponseEntity.ok(giftService.save(gift));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGift(@PathVariable Long id) {
        if (giftService.findById(id).isPresent()) {
            giftService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/adjust-stock")
    public ResponseEntity<Gift> adjustStock(@PathVariable Long id, @RequestParam int adjustment) {
        return ResponseEntity.ok(giftService.adjustStock(id, adjustment));
    }

    // 兌換禮品
    @PostMapping("/{id}/redeem")
    public ResponseEntity<?> redeemGift(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "請提供 userId"));
            }
            RedemptionOrder order = giftService.redeemGift(id, userId);
            return ResponseEntity.ok(Map.of(
                "message", "兌換成功！",
                "orderId", order.getId(),
                "pointsSpent", order.getTotalPoints()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
