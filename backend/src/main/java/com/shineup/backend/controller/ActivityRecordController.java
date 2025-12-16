package com.shineup.backend.controller;

import com.shineup.backend.entity.ActivityRecord;
import com.shineup.backend.service.ActivityRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 活動紀錄 API
 */
@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ActivityRecordController {

    private final ActivityRecordService activityRecordService;

    // 查詢用戶的活動紀錄
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityRecord>> getUserRecords(@PathVariable Long userId) {
        List<ActivityRecord> records = activityRecordService.getUserRecords(userId);
        return ResponseEntity.ok(records);
    }
}
