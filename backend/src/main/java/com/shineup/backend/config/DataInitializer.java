package com.shineup.backend.config;

import com.shineup.backend.entity.ActivityRecord;
import com.shineup.backend.entity.ChatbotReply;
import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.RedemptionOrder;
import com.shineup.backend.entity.User;
import com.shineup.backend.entity.UserStats;
import com.shineup.backend.repository.ActivityRecordRepository;
import com.shineup.backend.repository.ChatbotReplyRepository;
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
    private final ChatbotReplyRepository chatbotReplyRepository;
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
        createDefaultChatbotReplies();
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
        // Hsuan - CREATOR 等級（宅配）- 11/23 註冊
        createUserIfNotExists(
            "hsuan0501@outlook.com",
            "Hsuan",
            "0912345678",
            "台北市中山區民生東路三段67號",
            User.MemberLevel.CREATOR,
            700,
            600,
            LocalDateTime.of(2024, 11, 23, 10, 30)
        );

        // Matcha - CREATOR 等級（超商取貨：全家 江寧店）- 11/27 註冊
        // 兌換過 250 積分（120+130），目前剩餘 80，表示曾賺過 330 兌換積分
        // 升級積分 280（已達 CREATOR 門檻 250）
        createUserIfNotExists(
            "matcha1108@example.com",
            "Matcha",
            "0912345678",
            null,
            User.MemberLevel.CREATOR,
            280,
            80,
            LocalDateTime.of(2024, 11, 27, 14, 15)
        );

        // May - EXPLORER 等級（超商取貨：7-11 復錦門市）- 12/6 註冊
        // 兌換過 200 積分，目前剩餘 50，表示曾賺過 250 兌換積分
        // 升級積分維持 180（新手狀態）
        createUserIfNotExists(
            "may0529@example.com",
            "May",
            "0912345678",
            null,
            User.MemberLevel.EXPLORER,
            180,
            50,
            LocalDateTime.of(2024, 12, 6, 9, 45)
        );
    }

    private void createUserIfNotExists(String email, String name, String phone, String address,
                                        User.MemberLevel level, int upgradePoints, int rewardPoints,
                                        LocalDateTime createdAt) {
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
            user.setCreatedAt(createdAt);
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
        user.setCreatedAt(createdAt);
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
        // 先清除所有用戶的舊訂單
        redemptionOrderRepository.deleteAll();

        // 取得其他測試用戶
        var matchaUser = userRepository.findByEmail("matcha1108@example.com");
        var mayUser = userRepository.findByEmail("may0529@example.com");

        LocalDateTime now = LocalDateTime.now();

        // === Hsuan 的訂單（#1）- 已完成，宅配 ===
        createOrderWithDelivery(
            user, "UiU 環保便攜吸管組", 100,
            RedemptionOrder.OrderStatus.COMPLETED,
            now.minusDays(5).withHour(10).withMinute(30),
            now.minusDays(4).withHour(14).withMinute(0),
            now.minusDays(2).withHour(16).withMinute(30),
            "Hsuan", "0912345678", "home",
            "台北市中山區民生東路三段67號", null, null
        );

        // === Matcha 的訂單（#2）- 已完成，超商取貨 ===
        if (matchaUser.isPresent()) {
            createOrderWithDelivery(
                matchaUser.get(), "印花樂 實用環保包袋", 120,
                RedemptionOrder.OrderStatus.COMPLETED,
                now.minusDays(4).withHour(15).withMinute(20),
                now.minusDays(3).withHour(10).withMinute(0),
                now.minusDays(1).withHour(18).withMinute(45),
                "Matcha", "0912345678", "store",
                null, "family", "江寧店"
            );
        }

        // === Matcha 的訂單（#3）- 已出貨，超商取貨 ===
        if (matchaUser.isPresent()) {
            createOrderWithDelivery(
                matchaUser.get(), "HappyEarth 回收紙筆記本", 130,
                RedemptionOrder.OrderStatus.SHIPPED,
                now.minusDays(2).withHour(11).withMinute(15),
                now.minusDays(1).withHour(9).withMinute(30),
                null,
                "Matcha", "0912345678", "store",
                null, "family", "江寧店"
            );
        }

        // === May 的訂單（#4）- 待處理，超商取貨 ===
        if (mayUser.isPresent()) {
            createOrderWithDelivery(
                mayUser.get(), "SUCCULAND 多肉植物", 200,
                RedemptionOrder.OrderStatus.PENDING,
                now.minusHours(6),
                null,
                null,
                "May", "0912345678", "store",
                null, "7-11", "復錦門市"
            );
        }

        log.info("已建立模擬兌換訂單（Hsuan #1完成, Matcha #2完成 #3出貨, May #4待處理）");
    }

    private void createOrderWithDelivery(User user, String giftTitle, int points,
            RedemptionOrder.OrderStatus status, LocalDateTime createdAt,
            LocalDateTime shippedAt, LocalDateTime completedAt,
            String recipientName, String recipientPhone, String deliveryMethod,
            String deliveryAddress, String storeBrand, String storeName) {
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
        order.setStatus(status);
        order.setCreatedAt(createdAt);
        order.setShippedAt(shippedAt);
        order.setCompletedAt(completedAt);

        // 收件資訊
        order.setRecipientName(recipientName);
        order.setRecipientPhone(recipientPhone);
        order.setDeliveryMethod(deliveryMethod);
        order.setDeliveryAddress(deliveryAddress);
        order.setStoreBrand(storeBrand);
        order.setStoreName(storeName);

        redemptionOrderRepository.save(order);
    }

    /**
     * 建立預設的客服快速回覆設定
     */
    private void createDefaultChatbotReplies() {
        if (chatbotReplyRepository.count() > 0) {
            log.info("客服快速回覆設定已存在，跳過初始化");
            return;
        }

        // 出貨/物流
        ChatbotReply shipping = new ChatbotReply();
        shipping.setId("shipping");
        shipping.setKeyword("出貨|寄送|物流|配送|多久會到|進度");
        shipping.setReply("您好！禮品兌換後，我們會在 3-5 個工作天內處理出貨。實際到貨時間依配送地區而定，通常為出貨後 1-3 天。\n\n您可以在個人頁面的「兌換紀錄」查看目前的處理狀態喔！");
        chatbotReplyRepository.save(shipping);

        // 積分
        ChatbotReply points = new ChatbotReply();
        points.setId("points");
        points.setKeyword("積分.*怎麼|積分.*如何|積分.*獲得|積分.*賺|怎麼.*積分|如何.*積分");
        points.setReply("獲得積分的方式有：\n\n📅 每日登入、連續登入\n✅ 完成任務獲得對應積分\n\n💡 前往「任務清單」領取任務，完成後即可獲得積分獎勵！\n\n升級積分用於提升等級，獎勵積分用於兌換禮品喔！");
        chatbotReplyRepository.save(points);

        // 等級
        ChatbotReply level = new ChatbotReply();
        level.setId("level");
        level.setKeyword("等級|level");
        level.setReply("ShineUp 共有 4 個等級：\n\n⭐ Lv1 EXPLORER 探索者（0-299 升級積分）\n⭐ Lv2 CREATOR 創造者（300-599 升級積分）\n⭐ Lv3 VISIONARY 遠見者（600-999 升級積分）\n⭐ Lv4 LUMINARY 領航者（1000+ 升級積分）\n\n等級越高，可以兌換的禮品種類越多！");
        chatbotReplyRepository.save(level);

        // 兌換
        ChatbotReply redeem = new ChatbotReply();
        redeem.setId("redeem");
        redeem.setKeyword("兌換.*怎麼|兌換.*如何|怎麼.*兌換|如何.*兌換");
        redeem.setReply("兌換禮品的步驟：\n\n1️⃣ 前往「禮品總覽」頁面瀏覽禮品\n2️⃣ 選擇想要的禮品並加入購物車\n3️⃣ 確認兌換資訊後送出訂單\n4️⃣ 等待處理出貨\n\n📦 您可以在個人頁面的「兌換紀錄」查看訂單狀態喔！\n\n⚠️ 請注意：部分禮品有等級限制，需達到指定等級才能兌換。");
        chatbotReplyRepository.save(redeem);

        // 人工客服
        ChatbotReply support = new ChatbotReply();
        support.setId("support");
        support.setKeyword("真人|人工|客服|聯絡|聯繫|專員");
        support.setReply("如需人工客服協助，請透過以下方式聯繫我們：\n\nEmail：support@shineup.com\n服務專線：(02) 1234-5678\n服務時間：週一至週五 9:00-18:00\n\n我們會盡快回覆您的問題！");
        chatbotReplyRepository.save(support);

        // 訂單查詢
        ChatbotReply order = new ChatbotReply();
        order.setId("order");
        order.setKeyword("訂單.*查|訂單.*哪裡|訂單.*看");
        order.setReply("您可以在個人頁面的「訂單紀錄」區塊查看所有兌換訂單，包含處理中、已出貨、已完成等狀態。");
        chatbotReplyRepository.save(order);

        log.info("已建立預設客服快速回覆設定");
    }
}
