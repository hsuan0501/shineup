package com.shineup.backend.config;

import com.shineup.backend.entity.ActivityRecord;
import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserStats;
import com.shineup.backend.repository.ActivityRecordRepository;
import com.shineup.backend.repository.GiftRepository;
import com.shineup.backend.repository.RedemptionOrderRepository;
import com.shineup.backend.repository.UserRepository;
import com.shineup.backend.repository.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 應用程式啟動時初始化資料
 * 自動建立預設管理員帳號和測試會員
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ActivityRecordRepository activityRecordRepository;
    private final UserStatsRepository userStatsRepository;
    private final RedemptionOrderRepository redemptionOrderRepository;
    private final GiftRepository giftRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@shineup.com}")
    private String adminEmail;

    @Value("${admin.password:Admin123}")
    private String adminPassword;

    @Value("${admin.name:系統管理員}")
    private String adminName;

    // 預設測試密碼
    private static final String DEFAULT_PASSWORD = "Qwe1234";

    @Override
    public void run(String... args) {
        createDefaultAdmin();
        createDefaultUsers();
        createMockActivityRecords();
    }

    private void createDefaultAdmin() {
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("管理員帳號已存在: {}", adminEmail);
            return;
        }

        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setName(adminName);
        admin.setAdmin(true);
        admin.setEmailVerified(true);
        admin.setLevel(User.MemberLevel.LUMINARY);
        admin.setUpgradePoints(99999);
        admin.setRewardPoints(99999);

        userRepository.save(admin);
        log.info("預設管理員帳號已建立: {} / {}", adminEmail, adminPassword);
    }

    private void createDefaultUsers() {
        // Hsuan - CREATOR 等級
        createUserIfNotExists(
            "hsuan0501@outlook.com",
            "Hsuan",
            "0912345678",
            "臺北市中山區民生東路三段67號",
            User.MemberLevel.CREATOR,
            700,
            600
        );

        // Matcha - EXPLORER 等級
        createUserIfNotExists(
            "matcha1108@example.com",
            "Matcha",
            "0923456789",
            "新北市板橋區文化路一段100號",
            User.MemberLevel.EXPLORER,
            120,
            80
        );

        // May - EXPLORER 等級
        createUserIfNotExists(
            "may0529@example.com",
            "May",
            "0934567890",
            "台中市西屯區台灣大道三段99號",
            User.MemberLevel.EXPLORER,
            100,
            50
        );
    }

    private void createUserIfNotExists(String email, String name, String phone, String address,
                                        User.MemberLevel level, int upgradePoints, int rewardPoints) {
        var existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            // 重設所有資料（方便測試）
            User user = existingUser.get();
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            user.setPhone(phone);
            user.setAddress(address);
            user.setLevel(level);
            user.setUpgradePoints(upgradePoints);
            user.setRewardPoints(rewardPoints);
            user.setAdmin(false);
            userRepository.save(user);
            log.info("會員帳號已重設: {}", email);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        user.setLevel(level);
        user.setUpgradePoints(upgradePoints);
        user.setRewardPoints(rewardPoints);
        user.setEmailVerified(true);
        user.setAdmin(false);

        userRepository.save(user);
        log.info("預設會員帳號已建立: {} / {}", email, DEFAULT_PASSWORD);
    }

    private void createMockActivityRecords() {
        // 為 Hsuan 建立模擬活動紀錄
        var hsuan = userRepository.findByEmail("hsuan0501@outlook.com");
        if (hsuan.isEmpty()) return;

        User user = hsuan.get();

        // 清除舊的活動紀錄
        activityRecordRepository.deleteAll(activityRecordRepository.findByUserIdOrderByCreatedAtDesc(user.getId()));

        LocalDateTime now = LocalDateTime.now();

        // === 早期任務（30天前開始）===
        // 第一天：首次登入 + 完成個人檔案
        addRecord(user, "login", "完成每日登入", 1, now.minusDays(30).withHour(10).withMinute(15));
        addRecord(user, "task", "完成個人檔案設置", 5, now.minusDays(30).withHour(10).withMinute(30));

        // 第二天：訂閱電子報
        addRecord(user, "login", "完成每日登入", 1, now.minusDays(29).withHour(9).withMinute(20));
        addRecord(user, "task", "訂閱電子報", 10, now.minusDays(29).withHour(14).withMinute(0));

        // 第三天：設定理財目標
        addRecord(user, "login", "完成每日登入", 1, now.minusDays(28).withHour(8).withMinute(45));
        addRecord(user, "task", "設定理財目標", 10, now.minusDays(28).withHour(15).withMinute(30));

        // 第五天：綁定銀行帳戶
        addRecord(user, "login", "完成每日登入", 1, now.minusDays(26).withHour(9).withMinute(10));
        addRecord(user, "task", "綁定銀行帳戶", 15, now.minusDays(26).withHour(11).withMinute(0));

        // 第七天：連續登入獎勵
        addRecord(user, "login", "完成每日登入", 1, now.minusDays(24).withHour(9).withMinute(30));
        addRecord(user, "streak", "連續登入七天", 5, now.minusDays(24).withHour(9).withMinute(31));

        // === 中期任務（20天前）===
        addRecord(user, "task", "完成金融知識測驗", 5, now.minusDays(20).withHour(14).withMinute(0));
        addRecord(user, "task", "觀看線上課程視頻", 10, now.minusDays(19).withHour(20).withMinute(30));
        addRecord(user, "task", "參與線上學習論壇", 20, now.minusDays(18).withHour(16).withMinute(45));
        addRecord(user, "task", "完成風險承受能力評估", 10, now.minusDays(17).withHour(11).withMinute(0));

        // 邀請朋友 x3
        addRecord(user, "invite", "邀請朋友完成註冊", 50, now.minusDays(16).withHour(13).withMinute(20));
        addRecord(user, "invite", "邀請朋友完成註冊", 50, now.minusDays(15).withHour(10).withMinute(15));
        addRecord(user, "invite", "邀請朋友完成註冊", 50, now.minusDays(14).withHour(17).withMinute(40));

        // === 進階任務（12天前）===
        addRecord(user, "task", "建立個人投資日誌", 20, now.minusDays(12).withHour(19).withMinute(0));
        addRecord(user, "task", "建立投資帳戶記錄", 30, now.minusDays(11).withHour(14).withMinute(30));
        addRecord(user, "task", "記錄虛擬投資交易", 40, now.minusDays(10).withHour(16).withMinute(0));
        addRecord(user, "task", "設定投資提醒規則", 30, now.minusDays(9).withHour(11).withMinute(45));

        // === 兌換禮品（8天前）===
        addRecord(user, "reward", "兌換 UiU 環保便攜吸管組", -100, now.minusDays(8).withHour(15).withMinute(0));

        // 同時建立對應的 RedemptionOrder
        createMockRedemptionOrder(user, "UiU 環保便攜吸管組", 100, now.minusDays(8).withHour(15).withMinute(0));

        // === 最近7天連續登入（12/14 ~ 12/20）===
        for (int i = 7; i >= 1; i--) {
            addRecord(user, "login", "完成每日登入", 1, now.minusDays(i).withHour(9).withMinute(30));
        }

        // 12/20 連續登入七天獎勵（昨天）
        addRecord(user, "streak", "連續登入七天", 5, now.minusDays(1).withHour(9).withMinute(31));

        // === 更多任務（補足到 700 點）===
        addRecord(user, "task", "完成ESG價值觀評估", 10, now.minusDays(5).withHour(14).withMinute(20));
        addRecord(user, "task", "記錄永續投資組合", 20, now.minusDays(4).withHour(16).withMinute(0));
        addRecord(user, "task", "觀看線上課程視頻", 10, now.minusDays(3).withHour(21).withMinute(30));
        addRecord(user, "task", "設定投資限制和額度", 50, now.minusDays(2).withHour(10).withMinute(45));
        addRecord(user, "task", "建立定期定額投資計畫", 60, now.minusDays(2).withHour(15).withMinute(0));
        addRecord(user, "invite", "邀請朋友完成註冊", 50, now.minusDays(2).withHour(18).withMinute(30));
        addRecord(user, "task", "建立投資組合記錄", 40, now.minusDays(1).withHour(14).withMinute(0));

        // 注意：今天的登入由實際登入時產生，不在這裡建立

        // 重設 UserStats，讓今天的登入可以正常記錄
        var statsOpt = userStatsRepository.findByUserId(user.getId());
        if (statsOpt.isPresent()) {
            UserStats stats = statsOpt.get();
            stats.setLastLoginDate(LocalDate.now().minusDays(1)); // 設為昨天，讓今天登入算新的一天
            stats.setConsecutiveDays(7); // 已連續7天
            stats.setTotalLogins(12); // 總登入次數
            stats.setTasksCompleted(15); // 已完成任務數
            stats.setRewardsRedeemed(1); // 已兌換1次
            stats.setFriendsInvited(4); // 邀請4位朋友
            userStatsRepository.save(stats);
        }

        log.info("已建立 {} 的模擬活動紀錄（約700升級積分）", user.getName());
    }

    private void addRecord(User user, String type, String title, int points, LocalDateTime createdAt) {
        ActivityRecord record = new ActivityRecord();
        record.setUser(user);
        record.setType(type);
        record.setTitle(title);
        record.setPoints(points);
        record.setCreatedAt(createdAt);
        activityRecordRepository.save(record);
    }

    private void createMockRedemptionOrder(User user, String giftTitle, int points, LocalDateTime createdAt) {
        // 先清除該用戶的舊訂單
        var oldOrders = redemptionOrderRepository.findByUserId(user.getId());
        redemptionOrderRepository.deleteAll(oldOrders);

        // 找到對應的禮品
        var giftOpt = giftRepository.findByTitle(giftTitle);
        if (giftOpt.isEmpty()) {
            log.warn("找不到禮品: {}", giftTitle);
            return;
        }

        Gift gift = giftOpt.get();
        RedemptionOrder order = new RedemptionOrder();
        order.setUser(user);
        order.setGift(gift);
        order.setQuantity(1);
        order.setTotalPoints(points);
        order.setStatus(RedemptionOrder.OrderStatus.COMPLETED);
        order.setCreatedAt(createdAt);

        redemptionOrderRepository.save(order);
        log.info("已建立模擬兌換訂單: {} - {}", user.getName(), giftTitle);
    }
}
