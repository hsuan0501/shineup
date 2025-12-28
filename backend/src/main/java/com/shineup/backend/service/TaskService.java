package com.shineup.backend.service;

import com.shineup.backend.entity.LevelConfig;
import com.shineup.backend.entity.Task;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserTaskProgress;
import com.shineup.backend.repository.LevelConfigRepository;
import com.shineup.backend.repository.TaskRepository;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.repository.UserTaskProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserTaskProgressRepository userTaskProgressRepository;
    private final LevelConfigRepository levelConfigRepository;
    private final UserService userService;
    private final ActivityRecordService activityRecordService;
    private final AchievementService achievementService;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findActive() {
        return taskRepository.findByActiveTrue();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByCategory(String category) {
        return taskRepository.findByCategory(category);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task toggleActive(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setActive(!task.getActive());
        return taskRepository.save(task);
    }

    // 完成任務 - 幫用戶加積分並記錄
    @Transactional
    public User completeTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("任務不存在"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用戶不存在"));

        // 檢查用戶等級是否符合任務要求
        if (!isUserLevelSufficient(user.getLevel(), task.getLevelText())) {
            throw new RuntimeException("等級不足，無法完成此任務");
        }

        // 查找或建立任務進度紀錄
        UserTaskProgress progress = userTaskProgressRepository
                .findByUserIdAndTaskId(userId, taskId)
                .orElseGet(() -> {
                    UserTaskProgress newProgress = new UserTaskProgress();
                    newProgress.setUser(user);
                    newProgress.setTask(task);
                    return newProgress;
                });

        // 更新完成紀錄
        progress.markCompleted();
        userTaskProgressRepository.save(progress);

        // 取得用戶等級倍率
        double multiplier = levelConfigRepository.findByLevelCode(user.getLevel().name())
                .map(LevelConfig::getMultiplier)
                .orElse(1.0);

        // 計算實際獲得的積分（套用倍率）
        int earnedUpgradePoints = (int) Math.round(task.getUpgradePoints() * multiplier);
        int earnedRewardPoints = (int) Math.round(task.getRewardPoints() * multiplier);

        // 加積分
        user.setUpgradePoints(user.getUpgradePoints() + earnedUpgradePoints);
        user.setRewardPoints(user.getRewardPoints() + earnedRewardPoints);

        // 檢查等級升級
        user.updateLevelFromPoints();

        // 更新統計：任務完成數 +1
        userService.incrementTasksCompleted(userId);

        // 新增活動紀錄（記錄實際獲得的積分）
        activityRecordService.addRecord(userId, "task", task.getTitle(), earnedUpgradePoints);

        // 儲存用戶
        User savedUser = userRepository.save(user);

        // 檢查並完成等級/積分相關成就任務
        achievementService.checkAndCompleteAchievements(savedUser);

        return savedUser;
    }

    // 檢查用戶等級是否符合要求
    private boolean isUserLevelSufficient(User.MemberLevel userLevel, String levelText) {
        if (levelText == null || levelText.equals("全等級")) {
            return true; // 全等級都可以
        }
        int requiredLevel = 0;
        if (levelText.contains("Lv2") || levelText.contains("LV2")) {
            requiredLevel = 1; // CREATOR
        } else if (levelText.contains("Lv3") || levelText.contains("LV3")) {
            requiredLevel = 2; // VISIONARY
        } else if (levelText.contains("Lv4") || levelText.contains("LV4")) {
            requiredLevel = 3; // LUMINARY
        }
        return userLevel.ordinal() >= requiredLevel;
    }

    // 取得用戶已完成的任務
    public List<UserTaskProgress> getCompletedTasks(Long userId) {
        return userTaskProgressRepository.findByUserIdAndCompletedTrue(userId);
    }

    // 重置用戶的特定任務進度
    @Transactional
    public void resetTaskProgress(Long taskId, Long userId) {
        userTaskProgressRepository.findByUserIdAndTaskId(userId, taskId)
                .ifPresent(userTaskProgressRepository::delete);
    }

    // 取得所有任務並附上用戶的完成狀態
    public List<Map<String, Object>> findAllWithUserProgress(Long userId) {
        List<Task> allTasks = taskRepository.findByActiveTrue();

        // 取得用戶已完成的任務 ID 集合
        Set<Long> completedTaskIds = userTaskProgressRepository
                .findByUserIdAndCompletedTrue(userId)
                .stream()
                .map(progress -> progress.getTask().getId())
                .collect(Collectors.toSet());

        // 將任務轉換為帶有 completed 狀態的 Map
        return allTasks.stream().map(task -> {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("id", task.getId());
            taskMap.put("title", task.getTitle());
            taskMap.put("description", task.getDescription());
            taskMap.put("details", task.getDetails());
            taskMap.put("category", task.getCategory());
            taskMap.put("upgradePoints", task.getUpgradePoints());
            taskMap.put("rewardPoints", task.getRewardPoints());
            taskMap.put("image", task.getImage());
            taskMap.put("icon", task.getIcon());
            taskMap.put("frequency", task.getFrequency());
            taskMap.put("levelText", task.getLevelText());
            taskMap.put("active", task.getActive());
            taskMap.put("completed", completedTaskIds.contains(task.getId()));
            return taskMap;
        }).collect(Collectors.toList());
    }
}
