package com.shineup.backend.controller;

import com.shineup.backend.entity.Task;
import com.shineup.backend.entity.User;
import com.shineup.backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/active")
    public List<Task> getActiveTasks() {
        return taskService.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Task> getTasksByCategory(@PathVariable String category) {
        return taskService.findByCategory(category);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.findById(id)
                .map(existingTask -> {
                    task.setId(id);
                    return ResponseEntity.ok(taskService.save(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.findById(id).isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.toggleActive(id));
    }

    // 取得所有任務（帶用戶完成狀態）
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTasksWithProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findAllWithUserProgress(userId));
    }

    // 完成任務
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        if (userId == null) {
            throw new IllegalArgumentException("請提供 userId");
        }
        User user = taskService.completeTask(id, userId);
        return ResponseEntity.ok(Map.of(
            "message", "任務完成！",
            "upgradePoints", user.getUpgradePoints(),
            "rewardPoints", user.getRewardPoints()
        ));
    }

    // 重置任務進度（用於測試或修正資料）
    @DeleteMapping("/{id}/reset")
    public ResponseEntity<?> resetTaskProgress(@PathVariable Long id, @RequestParam Long userId) {
        taskService.resetTaskProgress(id, userId);
        return ResponseEntity.ok(Map.of(
            "message", "任務進度已重置",
            "taskId", id,
            "userId", userId
        ));
    }
}
