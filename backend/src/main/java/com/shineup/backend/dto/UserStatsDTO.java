package com.shineup.backend.dto;

import com.shineup.backend.entity.UserStats;
import lombok.Data;

@Data
public class UserStatsDTO {
    private Integer tasksCompleted;
    private Integer consecutiveDays;
    private Integer totalLogins;
    private Integer rewardsRedeemed;
    private Integer friendsInvited;

    public static UserStatsDTO fromEntity(UserStats stats) {
        UserStatsDTO dto = new UserStatsDTO();
        dto.setTasksCompleted(stats.getTasksCompleted());
        dto.setConsecutiveDays(stats.getConsecutiveDays());
        dto.setTotalLogins(stats.getTotalLogins());
        dto.setRewardsRedeemed(stats.getRewardsRedeemed());
        dto.setFriendsInvited(stats.getFriendsInvited());
        return dto;
    }

    // 預設空統計（用於尚未建立統計的用戶）
    public static UserStatsDTO empty() {
        UserStatsDTO dto = new UserStatsDTO();
        dto.setTasksCompleted(0);
        dto.setConsecutiveDays(0);
        dto.setTotalLogins(0);
        dto.setRewardsRedeemed(0);
        dto.setFriendsInvited(0);
        return dto;
    }
}
