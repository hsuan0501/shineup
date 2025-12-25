package com.shineup.backend.controller;

import com.shineup.backend.entity.LoginHistory;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserTaskProgress;
import com.shineup.backend.repository.LoginHistoryRepository;
import com.shineup.backend.repository.UserTaskProgressRepository;
import com.shineup.backend.service.TaskService;
import com.shineup.backend.service.UserService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Long PROFILE_TASK_ID = 3L; // 完成個人檔案設置任務 ID
    private static final Long NEWSLETTER_TASK_ID = 4L; // 訂閱電子報任務 ID

    private final UserService userService;
    private final TaskService taskService;
    private final UserTaskProgressRepository userTaskProgressRepository;
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
                    // 記錄更新前的訂閱狀態
                    boolean wasSubscribed = Boolean.TRUE.equals(existingUser.getNewsletterSubscribed());

                    user.setId(id);
                    User savedUser = userService.save(user);

                    // 檢查是否完成個人檔案設置任務
                    boolean profileTaskCompleted = checkAndCompleteProfileTask(savedUser);

                    // 檢查是否完成訂閱電子報任務（從未訂閱變成已訂閱）
                    boolean newsletterTaskCompleted = checkAndCompleteNewsletterTask(savedUser, wasSubscribed);

                    Map<String, Object> response = new HashMap<>();
                    response.put("user", savedUser);

                    // 回傳完成的任務資訊
                    if (profileTaskCompleted) {
                        response.put("taskCompleted", Map.of(
                                "taskId", PROFILE_TASK_ID,
                                "taskTitle", "完成個人檔案設置",
                                "message", "恭喜！您已完成個人檔案設置任務，獲得 5 升級積分 + 5 獎勵積分！"
                        ));
                    } else if (newsletterTaskCompleted) {
                        response.put("taskCompleted", Map.of(
                                "taskId", NEWSLETTER_TASK_ID,
                                "taskTitle", "訂閱電子報",
                                "message", "恭喜！您已完成訂閱電子報任務，獲得 10 升級積分 + 10 獎勵積分！"
                        ));
                    }

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 檢查並完成個人檔案設置任務
     * 條件：name、phone、address 都已填寫，且任務尚未完成
     */
    private boolean checkAndCompleteProfileTask(User user) {
        // 檢查必填欄位是否都已填寫
        if (!isProfileComplete(user)) {
            return false;
        }

        // 檢查任務是否已經完成
        Optional<UserTaskProgress> progress = userTaskProgressRepository
                .findByUserIdAndTaskId(user.getId(), PROFILE_TASK_ID);
        if (progress.isPresent() && Boolean.TRUE.equals(progress.get().getCompleted())) {
            return false; // 已完成過，不重複給積分
        }

        // 完成任務
        try {
            taskService.completeTask(PROFILE_TASK_ID, user.getId());
            log.info("用戶 {} 完成個人檔案設置任務", user.getId());
            return true;
        } catch (Exception e) {
            log.error("完成個人檔案設置任務失敗: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 檢查個人檔案是否完整
     * 需要填寫：姓名、手機、地址、生日
     */
    private boolean isProfileComplete(User user) {
        return user.getName() != null && !user.getName().trim().isEmpty()
                && user.getPhone() != null && !user.getPhone().trim().isEmpty()
                && user.getAddress() != null && !user.getAddress().trim().isEmpty()
                && user.getBirthday() != null;
    }

    /**
     * 檢查並完成訂閱電子報任務
     * 條件：從未訂閱變成已訂閱，且任務尚未完成
     */
    private boolean checkAndCompleteNewsletterTask(User user, boolean wasSubscribed) {
        // 檢查是否為新訂閱（之前未訂閱，現在已訂閱）
        if (wasSubscribed || !Boolean.TRUE.equals(user.getNewsletterSubscribed())) {
            return false;
        }

        // 檢查任務是否已經完成
        Optional<UserTaskProgress> progress = userTaskProgressRepository
                .findByUserIdAndTaskId(user.getId(), NEWSLETTER_TASK_ID);
        if (progress.isPresent() && Boolean.TRUE.equals(progress.get().getCompleted())) {
            return false; // 已完成過，不重複給積分
        }

        // 完成任務
        try {
            taskService.completeTask(NEWSLETTER_TASK_ID, user.getId());
            log.info("用戶 {} 完成訂閱電子報任務", user.getId());
            return true;
        } catch (Exception e) {
            log.error("完成訂閱電子報任務失敗: {}", e.getMessage());
            return false;
        }
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
                    boolean currentEnabled = Boolean.TRUE.equals(user.getEnabled());
                    user.setEnabled(!currentEnabled);
                    userService.save(user);
                    String status = user.getEnabled() ? "啟用" : "停用";
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
