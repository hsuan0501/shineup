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
    private boolean success;
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
        private boolean emailVerified;
    }

    public static AuthResponse success(String token, User user) {
        return AuthResponse.builder()
                .token(token)
                .message("success")
                .success(true)
                .user(UserInfo.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .level(user.getLevel().name())
                        .upgradePoints(user.getUpgradePoints())
                        .rewardPoints(user.getRewardPoints())
                        .emailVerified(user.isEmailVerified())
                        .build())
                .build();
    }

    public static AuthResponse error(String message) {
        return AuthResponse.builder()
                .message(message)
                .success(false)
                .build();
    }
}
