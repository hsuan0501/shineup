package com.shineup.backend.exception;

/**
 * 業務邏輯異常
 * 用於處理如積分不足、庫存不足、用戶不存在等業務錯誤
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
