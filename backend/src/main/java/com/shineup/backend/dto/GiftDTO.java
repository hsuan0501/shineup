package com.shineup.backend.dto;

import com.shineup.backend.entity.Gift;
import lombok.Data;

@Data
public class GiftDTO {
    private Long id;
    private String title;
    private String description;
    private String series;
    private String levelText;
    private String levelRestriction;
    private Integer requiredPoints;
    private Integer stock;
    private String stockStatus;
    private String image;

    /**
     * 根據庫存數量計算庫存狀態
     * ≥100: high (充足)
     * 50-99: medium (適中)
     * 10-49: low (偏低)
     * <10: critical (緊張)
     * 0: sold_out (已售罄)
     */
    private static String calculateStockStatus(Integer stock) {
        if (stock == null || stock <= 0) {
            return "sold_out";
        } else if (stock >= 100) {
            return "high";
        } else if (stock >= 50) {
            return "medium";
        } else if (stock >= 10) {
            return "low";
        } else {
            return "critical";
        }
    }

    public static GiftDTO fromEntity(Gift gift) {
        GiftDTO dto = new GiftDTO();
        dto.setId(gift.getId());
        dto.setTitle(gift.getTitle());
        dto.setDescription(gift.getDescription());
        dto.setSeries(gift.getSeries());
        dto.setLevelText(gift.getLevelText());
        dto.setLevelRestriction(gift.getLevelRestriction());
        dto.setRequiredPoints(gift.getRequiredPoints());
        dto.setStock(gift.getStock());
        dto.setStockStatus(calculateStockStatus(gift.getStock()));
        dto.setImage(gift.getImage());
        return dto;
    }
}
