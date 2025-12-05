package com.shineup.backend.service;

import com.shineup.backend.entity.Gift;
import com.shineup.backend.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;

    public List<Gift> findAll() {
        return giftRepository.findAll();
    }

    public Optional<Gift> findById(Long id) {
        return giftRepository.findById(id);
    }

    public List<Gift> findBySeries(String series) {
        return giftRepository.findBySeries(series);
    }

    public List<Gift> findInStock() {
        return giftRepository.findByStockGreaterThan(0);
    }

    public Gift save(Gift gift) {
        return giftRepository.save(gift);
    }

    public void deleteById(Long id) {
        giftRepository.deleteById(id);
    }

    public Gift adjustStock(Long id, int adjustment) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gift not found"));
        int newStock = gift.getStock() + adjustment;
        gift.setStock(Math.max(0, newStock));
        return giftRepository.save(gift);
    }
}
