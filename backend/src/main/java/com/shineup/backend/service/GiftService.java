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
public class GiftService {

    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final RedemptionOrderRepository redemptionOrderRepository;
    private final UserService userService;
    private final ActivityRecordService activityRecordService;

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

    // 兌換禮品 - 扣積分、扣庫存、建立訂單
    @Transactional
    public RedemptionOrder redeemGift(Long giftId, Long userId) {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("禮品不存在"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用戶不存在"));

        // 檢查庫存
        if (gift.getStock() <= 0) {
            throw new RuntimeException("禮品已售完");
        }

        // 檢查用戶等級
        if (!isUserLevelSufficient(user.getLevel(), gift.getLevelText(), gift.getLevelRestriction())) {
            throw new RuntimeException("等級不足，無法兌換此禮品");
        }

        // 檢查積分
        if (user.getRewardPoints() < gift.getRequiredPoints()) {
            throw new RuntimeException("積分不足");
        }

        // 扣積分
        user.setRewardPoints(user.getRewardPoints() - gift.getRequiredPoints());
        userRepository.save(user);

        // 扣庫存
        gift.setStock(gift.getStock() - 1);
        giftRepository.save(gift);

        // 建立兌換訂單
        RedemptionOrder order = new RedemptionOrder();
        order.setUser(user);
        order.setGift(gift);
        order.setTotalPoints(gift.getRequiredPoints());
        order.setStatus(RedemptionOrder.OrderStatus.COMPLETED);

        // 更新統計：兌換數 +1
        userService.incrementRewardsRedeemed(userId);

        // 新增活動紀錄（負數表示消耗積分）
        activityRecordService.addRecord(userId, "reward", "兌換 " + gift.getTitle(), -gift.getRequiredPoints());

        return redemptionOrderRepository.save(order);
    }

    // 檢查用戶等級是否符合禮品要求
    private boolean isUserLevelSufficient(User.MemberLevel userLevel, String levelText, String levelRestriction) {
        // 先檢查 levelRestriction (如 "lv1_only", "lv2_plus", "lv3_plus", "lv4_only")
        if (levelRestriction != null && !levelRestriction.isEmpty()) {
            int userOrdinal = userLevel.ordinal();
            if (levelRestriction.equals("lv1_only")) {
                return userOrdinal == 0; // 只限 EXPLORER
            } else if (levelRestriction.equals("lv2_plus")) {
                return userOrdinal >= 1; // CREATOR 以上
            } else if (levelRestriction.equals("lv3_plus")) {
                return userOrdinal >= 2; // VISIONARY 以上
            } else if (levelRestriction.equals("lv4_only")) {
                return userOrdinal == 3; // 只限 LUMINARY
            }
        }

        // 備用：根據 levelText 判斷 (如 "EXPLORER", "CREATOR", "VISIONARY", "LUMINARY")
        if (levelText == null || levelText.isEmpty()) {
            return true;
        }
        int requiredLevel = 0;
        if (levelText.equals("CREATOR")) {
            requiredLevel = 1;
        } else if (levelText.equals("VISIONARY")) {
            requiredLevel = 2;
        } else if (levelText.equals("LUMINARY")) {
            requiredLevel = 3;
        }
        return userLevel.ordinal() >= requiredLevel;
    }
}
