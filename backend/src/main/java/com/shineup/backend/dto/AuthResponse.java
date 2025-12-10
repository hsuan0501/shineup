package com.shineup.backend.dto;

import com.shineup.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String message;
    private UserInfo user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String level;
        private Integer upgradePoints;
        private Integer rewardPoints;
    }

    public static AuthResponse success(String token, User user) {
        return AuthResponse.builder()
                .token(token)
                .message("success")
                .user(UserInfo.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .level(user.getLevel().name())
                        .upgradePoints(user.getUpgradePoints())
                        .rewardPoints(user.getRewardPoints())
                        .build())
                .build();
    }

    public static AuthResponse error(String message) {
        return AuthResponse.builder()
                .message(message)
                .build();
    }
}
