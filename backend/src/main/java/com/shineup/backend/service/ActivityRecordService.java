package com.shineup.backend.service;

import com.shineup.backend.entity.ActivityRecord;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.ActivityRecordRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 活動紀錄 Service
 * 負責新增和查詢用戶活動紀錄
 */
@Service
@RequiredArgsConstructor
public class ActivityRecordService {

    private final ActivityRecordRepository activityRecordRepository;
    private final UserRepository userRepository;

    // 新增活動紀錄
    public ActivityRecord addRecord(Long userId, String type, String title, Integer points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用戶不存在"));

        ActivityRecord record = new ActivityRecord();
        record.setUser(user);
        record.setType(type);
        record.setTitle(title);
        record.setPoints(points);

        return activityRecordRepository.save(record);
    }

    // 查詢用戶的活動紀錄
    public List<ActivityRecord> getUserRecords(Long userId) {
        return activityRecordRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
