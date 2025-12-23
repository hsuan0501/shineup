package com.shineup.backend.controller;

import com.shineup.backend.dto.UserStatsDTO;
import com.shineup.backend.entity.LoginHistory;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.LoginHistoryRepository;
import com.shineup.backend.service.UserService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final LoginHistoryRepository loginHistoryRepository;

    // 從 Authorization header 取得當前用戶 ID
    private Long getCurrentUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return jwtUtil.getUserIdFromToken(token);
    }

    // 檢查是否為本人或管理員
    private boolean isOwnerOrAdmin(String authHeader, Long targetUserId) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) return false;

        // 檢查是否為本人
        if (currentUserId.equals(targetUserId)) return true;

        // 檢查是否為管理員
        return userService.findById(currentUserId)
                .map(User::isAdmin)
                .orElse(false);
    }

    // 檢查是否為管理員
    private boolean isAdmin(String authHeader) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) return false;

        return userService.findById(currentUserId)
                .map(User::isAdmin)
                .orElse(false);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以列出所有用戶
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可存取"));
        }
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只能查看自己或管理員可查看任何人
        if (!isOwnerOrAdmin(authHeader, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的資料"));
        }
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody User user,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以直接建立用戶
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可建立用戶"));
        }
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User user,
            @RequestHeader("Authorization") String authHeader) {
        // 只能修改自己或管理員可修改任何人
        if (!isOwnerOrAdmin(authHeader, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能修改自己的資料"));
        }
        return userService.findById(id)
                .map(existingUser -> {
                    user.setId(id);
                    return ResponseEntity.ok(userService.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以刪除用戶
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可刪除用戶"));
        }
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/add-points")
    public ResponseEntity<?> addPoints(
            @PathVariable Long id,
            @RequestParam int upgradePoints,
            @RequestParam int rewardPoints,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以手動加積分
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可加積分"));
        }
        return ResponseEntity.ok(userService.addPoints(id, upgradePoints, rewardPoints));
    }

    // 重置用戶資料（展示用）
    @PostMapping("/{id}/reset")
    public ResponseEntity<?> resetUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以重置用戶
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可重置用戶"));
        }
        return ResponseEntity.ok(userService.resetUser(id));
    }

    // 取得用戶統計資料
    @GetMapping("/{id}/stats")
    public ResponseEntity<?> getUserStats(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只能查看自己或管理員可查看任何人
        if (!isOwnerOrAdmin(authHeader, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的資料"));
        }
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getUserStats(id));
    }

    // 停用/啟用帳號
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleUserStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只有管理員可以停用/啟用帳號
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可停用/啟用帳號"));
        }
        return userService.findById(id)
                .map(user -> {
                    user.setEnabled(!user.isEnabled());
                    userService.save(user);
                    String status = user.isEnabled() ? "啟用" : "停用";
                    return ResponseEntity.ok(Map.of(
                            "message", "帳號已" + status,
                            "user", user
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 記錄登入（用於更新連續登入天數）
    @PostMapping("/{id}/record-login")
    public ResponseEntity<?> recordLogin(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只能記錄自己的登入
        if (!isOwnerOrAdmin(authHeader, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限"));
        }
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.recordLogin(id);
        return ResponseEntity.ok(userService.getUserStats(id));
    }

    // 取得用戶登入紀錄
    @GetMapping("/{id}/login-history")
    public ResponseEntity<?> getLoginHistory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        // 只能查看自己或管理員可查看任何人
        if (!isOwnerOrAdmin(authHeader, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的登入紀錄"));
        }
        List<LoginHistory> history = loginHistoryRepository.findByUserIdOrderByLoginTimeDesc(id);
        return ResponseEntity.ok(history);
    }

    // 取得所有登入紀錄（管理員用）
    @GetMapping("/login-history")
    public ResponseEntity<?> getAllLoginHistory(@RequestHeader("Authorization") String authHeader) {
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：僅管理員可存取"));
        }
        List<LoginHistory> history = loginHistoryRepository.findAllByOrderByLoginTimeDesc();
        return ResponseEntity.ok(history);
    }
}
