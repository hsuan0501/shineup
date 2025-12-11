package com.shineup.backend.repository;

import com.shineup.backend.entity.UserTaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskProgressRepository extends JpaRepository<UserTaskProgress, Long> {
    Optional<UserTaskProgress> findByUserIdAndTaskId(Long userId, Long taskId);
    List<UserTaskProgress> findByUserId(Long userId);
    List<UserTaskProgress> findByUserIdAndCompletedTrue(Long userId);
}
