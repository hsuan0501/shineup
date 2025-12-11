package com.shineup.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_task_progress", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "task_id"})
})
public class UserTaskProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "completion_count")
    private Integer completionCount = 0;

    @Column(name = "last_completed_at")
    private LocalDateTime lastCompletedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
