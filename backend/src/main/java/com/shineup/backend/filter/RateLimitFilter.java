package com.shineup.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate Limiting Filter
 * 限制登入/註冊 API 的請求次數，防止暴力破解
 */
@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // 儲存每個 IP 的請求次數（key: IP + endpoint, value: 請求記錄）
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    // 限制設定
    private static final int LOGIN_LIMIT = 5;          // 登入：每分鐘 5 次
    private static final int REGISTER_LIMIT = 3;       // 註冊：每小時 3 次
    private static final int PASSWORD_RESET_LIMIT = 3; // 密碼重設：每小時 3 次

    private static final long ONE_MINUTE = 60 * 1000;
    private static final long ONE_HOUR = 60 * 60 * 1000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = getClientIp(request);

        // 只對特定 API 做 Rate Limiting
        if ("POST".equals(method)) {
            RateLimitConfig config = getRateLimitConfig(path);

            if (config != null) {
                String key = clientIp + ":" + config.endpoint;

                if (isRateLimited(key, config.limit, config.windowMs)) {
                    log.warn("Rate limit exceeded for IP: {} on endpoint: {}", clientIp, path);
                    response.setStatus(429);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"message\":\"請求次數過多，請稍後再試\"}");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private RateLimitConfig getRateLimitConfig(String path) {
        if (path.equals("/api/auth/login")) {
            return new RateLimitConfig("login", LOGIN_LIMIT, ONE_MINUTE);
        } else if (path.equals("/api/auth/register")) {
            return new RateLimitConfig("register", REGISTER_LIMIT, ONE_HOUR);
        } else if (path.equals("/api/auth/forgot-password") || path.equals("/api/auth/reset-password")) {
            return new RateLimitConfig("password-reset", PASSWORD_RESET_LIMIT, ONE_HOUR);
        } else if (path.equals("/api/auth/resend-verification")) {
            return new RateLimitConfig("resend-verification", 3, ONE_HOUR);
        }
        return null; // 不限制
    }

    private boolean isRateLimited(String key, int limit, long windowMs) {
        long now = System.currentTimeMillis();

        requestCounts.compute(key, (k, counter) -> {
            if (counter == null || now - counter.windowStart > windowMs) {
                // 新的時間窗口
                return new RequestCounter(now, 1);
            } else {
                // 同一時間窗口，增加計數
                counter.count.incrementAndGet();
                return counter;
            }
        });

        RequestCounter counter = requestCounts.get(key);
        return counter != null && counter.count.get() > limit;
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }

    // 定期清理過期的計數器（避免記憶體洩漏）
    @jakarta.annotation.PostConstruct
    public void startCleanupTask() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5 * 60 * 1000); // 每 5 分鐘清理一次
                    long now = System.currentTimeMillis();
                    requestCounts.entrySet().removeIf(entry ->
                            now - entry.getValue().windowStart > ONE_HOUR);
                    log.debug("Rate limit cache cleaned, remaining entries: {}", requestCounts.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.setName("RateLimitCleanup");
        cleanupThread.start();
    }

    // 內部類別：請求計數器
    private static class RequestCounter {
        final long windowStart;
        final AtomicInteger count;

        RequestCounter(long windowStart, int initialCount) {
            this.windowStart = windowStart;
            this.count = new AtomicInteger(initialCount);
        }
    }

    // 內部類別：Rate Limit 設定
    private static class RateLimitConfig {
        final String endpoint;
        final int limit;
        final long windowMs;

        RateLimitConfig(String endpoint, int limit, long windowMs) {
            this.endpoint = endpoint;
            this.limit = limit;
            this.windowMs = windowMs;
        }
    }
}
