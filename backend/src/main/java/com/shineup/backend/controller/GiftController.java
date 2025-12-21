package com.shineup.backend.controller;

import com.shineup.backend.dto.GiftDTO;
import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GiftController {

    private final GiftService giftService;

    @GetMapping
    public List<GiftDTO> getAllGifts() {
        return giftService.findAll().stream()
                .map(GiftDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-stock")
    public List<GiftDTO> getGiftsInStock() {
        return giftService.findInStock().stream()
                .map(GiftDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftDTO> getGiftById(@PathVariable Long id) {
        return giftService.findById(id)
                .map(GiftDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/series/{series}")
    public List<GiftDTO> getGiftsBySeries(@PathVariable String series) {
        return giftService.findBySeries(series).stream()
                .map(GiftDTO::fromEntity)
                .collect(Collectors.toList());
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
        Long userId = request.get("userId");
        if (userId == null) {
            throw new IllegalArgumentException("請提供 userId");
        }
        RedemptionOrder order = giftService.redeemGift(id, userId);
        return ResponseEntity.ok(Map.of(
            "message", "兌換成功！",
            "orderId", order.getId(),
            "pointsSpent", order.getTotalPoints()
        ));
    }
}
