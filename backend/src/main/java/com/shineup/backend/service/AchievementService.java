package com.shineup.backend.service;

import com.shineup.backend.entity.Task;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserTaskProgress;
import com.shineup.backend.repository.TaskRepository;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.repository.UserTaskProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 成就任務自動完成服務
 * 處理等級相關、積分相關的成就任務自動完成
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserTaskProgressRepository userTaskProgressRepository;
    private final ActivityRecordService activityRecordService;

    // 任務 ID 常數
    private static final Long TASK_LEVEL_VISIONARY = 28L;  // 達成 Lv3 Visionary 等級升級
    private static final Long TASK_LEVEL_LUMINARY = 29L;   // 達成 Lv4 Luminary 等級升級
    private static final Long TASK_POINTS_5000 = 30L;      // 累積達到5,000積分

    /**
     * 檢查並自動完成等級/積分相關的成就任務
     * 在用戶積分變動時呼叫此方法
     * 會自動檢查兩輪，確保成就獎勵積分觸發的新成就也能被完成
     */
    @Transactional
    public void checkAndCompleteAchievements(User user) {
        // 執行兩輪檢查（成就獎勵積分可能觸發新成就）
        for (int round = 0; round < 2; round++) {
            boolean anyCompleted = doCheckAchievements(user);
            if (!anyCompleted) {
                break; // 沒有新完成的成就，不需要再檢查
            }
            // 重新更新等級（因為積分可能增加）
            updateUserLevel(user);
        }
    }

    /**
     * 執行一輪成就檢查
     * @return 是否有任何成就被完成
     */
    private boolean doCheckAchievements(User user) {
        boolean anyCompleted = false;
        int points = user.getUpgradePoints();
        User.MemberLevel level = user.getLevel();

        // 檢查 Lv3 成就（750 點以上，達到 VISIONARY）
        if (level.ordinal() >= User.MemberLevel.VISIONARY.ordinal()) {
            if (autoCompleteTaskIfNotDone(user, TASK_LEVEL_VISIONARY)) {
                anyCompleted = true;
            }
        }

        // 檢查 Lv4 成就（1500 點以上，達到 LUMINARY）
        if (level == User.MemberLevel.LUMINARY) {
            if (autoCompleteTaskIfNotDone(user, TASK_LEVEL_LUMINARY)) {
                anyCompleted = true;
            }
        }

        // 檢查 5000 積分成就
        if (points >= 5000) {
            if (autoCompleteTaskIfNotDone(user, TASK_POINTS_5000)) {
                anyCompleted = true;
            }
        }

        return anyCompleted;
    }

    /**
     * 更新用戶等級
     */
    private void updateUserLevel(User user) {
        int points = user.getUpgradePoints();
        if (points >= 1500) {
            user.setLevel(User.MemberLevel.LUMINARY);
        } else if (points >= 750) {
            user.setLevel(User.MemberLevel.VISIONARY);
        } else if (points >= 250) {
            user.setLevel(User.MemberLevel.CREATOR);
        } else {
            user.setLevel(User.MemberLevel.EXPLORER);
        }
    }

    /**
     * 自動完成指定任務（如果尚未完成）
     * 不會重複給予積分獎勵
     * @return true 如果成功完成任務，false 如果已完成或任務不存在
     */
    private boolean autoCompleteTaskIfNotDone(User user, Long taskId) {
        // 檢查是否已經完成過
        Optional<UserTaskProgress> existingProgress =
            userTaskProgressRepository.findByUserIdAndTaskId(user.getId(), taskId);

        if (existingProgress.isPresent() && Boolean.TRUE.equals(existingProgress.get().getCompleted())) {
            // 已完成，不重複處理
            return false;
        }

        // 取得任務資料
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            log.warn("找不到任務 ID: {}", taskId);
            return false;
        }

        Task task = taskOpt.get();

        // 建立或更新任務進度
        UserTaskProgress progress = existingProgress.orElseGet(() -> {
            UserTaskProgress newProgress = new UserTaskProgress();
            newProgress.setUser(user);
            newProgress.setTask(task);
            return newProgress;
        });

        // 標記完成
        progress.setCompleted(true);
        progress.setCompletionCount(progress.getCompletionCount() + 1);
        progress.setLastCompletedAt(LocalDateTime.now());
        if (progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        userTaskProgressRepository.save(progress);

        // 給予積分獎勵
        user.setUpgradePoints(user.getUpgradePoints() + task.getUpgradePoints());
        user.setRewardPoints(user.getRewardPoints() + task.getRewardPoints());
        userRepository.save(user);

        // 記錄活動
        activityRecordService.addRecord(user.getId(), "achievement", task.getTitle(), task.getUpgradePoints());

        log.info("用戶 {} 自動完成成就任務: {}", user.getName(), task.getTitle());
        return true;
    }
}
