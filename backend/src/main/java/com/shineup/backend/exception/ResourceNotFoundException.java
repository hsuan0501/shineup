package com.shineup.backend.exception;

/**
 * 資源未找到異常
 * 用於處理找不到用戶、任務、禮品等情況
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " 不存在 (ID: " + id + ")");
    }
}
