package com.shineup.backend.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {

    // 儲存驗證碼 (captchaId -> code)
    private final Map<String, CaptchaData> captchaStore = new ConcurrentHashMap<>();

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 5;
    private static final long EXPIRE_TIME = 5 * 60 * 1000; // 5 分鐘過期

    // 驗證碼資料
    private static class CaptchaData {
        String code;
        long createTime;

        CaptchaData(String code) {
            this.code = code;
            this.createTime = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createTime > EXPIRE_TIME;
        }
    }

    // 產生驗證碼
    public Map<String, String> generateCaptcha() {
        // 產生隨機驗證碼
        String code = generateRandomCode();
        String captchaId = generateCaptchaId();

        // 儲存驗證碼
        captchaStore.put(captchaId, new CaptchaData(code));

        // 產生圖片
        String imageBase64 = generateCaptchaImage(code);

        // 清理過期的驗證碼
        cleanExpiredCaptcha();

        return Map.of(
            "captchaId", captchaId,
            "image", "data:image/png;base64," + imageBase64
        );
    }

    // 驗證驗證碼
    public boolean verifyCaptcha(String captchaId, String code) {
        if (captchaId == null || code == null) {
            return false;
        }

        CaptchaData data = captchaStore.remove(captchaId);
        if (data == null || data.isExpired()) {
            return false;
        }

        return data.code.equalsIgnoreCase(code);
    }

    // 產生隨機驗證碼
    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    // 產生驗證碼 ID
    private String generateCaptchaId() {
        return java.util.UUID.randomUUID().toString();
    }

    // 產生驗證碼圖片
    private String generateCaptchaImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 設定抗鋸齒
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景
        g.setColor(new Color(245, 245, 245));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Random random = new Random();

        // 干擾線
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 干擾點
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g.fillOval(x, y, 2, 2);
        }

        // 繪製文字
        g.setFont(new Font("Arial", Font.BOLD, 28));
        for (int i = 0; i < code.length(); i++) {
            g.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
            // 隨機旋轉角度
            double theta = (random.nextDouble() - 0.5) * 0.5;
            g.rotate(theta, 20 + i * 20, 25);
            g.drawString(String.valueOf(code.charAt(i)), 12 + i * 20, 30);
            g.rotate(-theta, 20 + i * 20, 25);
        }

        g.dispose();

        // 轉成 Base64
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate captcha image", e);
        }
    }

    // 清理過期的驗證碼
    private void cleanExpiredCaptcha() {
        captchaStore.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
