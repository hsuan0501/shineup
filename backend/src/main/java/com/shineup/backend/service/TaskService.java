package com.shineup.backend.service;

import com.shineup.backend.entity.Task;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserTaskProgress;
import com.shineup.backend.repository.TaskRepository;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.repository.UserTaskProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserTaskProgressRepository userTaskProgressRepository;
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
        if (user.getLevel().ordinal() < task.getRequiredLevel().ordinal()) {
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
        progress.setCompleted(true);
        progress.setCompletionCount(progress.getCompletionCount() + 1);
        progress.setLastCompletedAt(LocalDateTime.now());
        if (progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        userTaskProgressRepository.save(progress);

        // 加積分
        user.setUpgradePoints(user.getUpgradePoints() + task.getUpgradePoints());
        user.setRewardPoints(user.getRewardPoints() + task.getRewardPoints());

        // 檢查等級升級
        checkLevelUp(user);

        // 更新統計：任務完成數 +1
        userService.incrementTasksCompleted(userId);

        // 新增活動紀錄
        activityRecordService.addRecord(userId, "task", task.getTitle(), task.getUpgradePoints());

        // 儲存用戶
        User savedUser = userRepository.save(user);

        // 檢查並完成等級/積分相關成就任務
        achievementService.checkAndCompleteAchievements(savedUser);

        return savedUser;
    }

    // 檢查等級升級
    private void checkLevelUp(User user) {
        int points = user.getUpgradePoints();
        // 等級門檻：0-249 EXPLORER, 250-749 CREATOR, 750-1499 VISIONARY, 1500+ LUMINARY
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

    // 取得用戶已完成的任務
    public List<UserTaskProgress> getCompletedTasks(Long userId) {
        return userTaskProgressRepository.findByUserIdAndCompletedTrue(userId);
    }
}
