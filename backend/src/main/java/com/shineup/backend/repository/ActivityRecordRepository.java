package com.shineup.backend.repository;

import com.shineup.backend.entity.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Long> {

    // 查詢用戶的活動紀錄（按時間倒序）
    List<ActivityRecord> findByUserIdOrderByCreatedAtDesc(Long userId);
}
