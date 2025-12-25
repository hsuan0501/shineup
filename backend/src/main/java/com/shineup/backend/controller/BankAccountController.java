package com.shineup.backend.controller;

import com.shineup.backend.entity.BankAccount;
import com.shineup.backend.service.BankAccountService;
import com.shineup.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final JwtUtil jwtUtil;

    // 從 Authorization header 取得當前用戶 ID
    private Long getCurrentUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return jwtUtil.getUserIdFromToken(token);
    }

    /**
     * 取得用戶的銀行帳戶列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBankAccounts(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String authHeader) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "無權限：只能查看自己的銀行帳戶"));
        }

        List<BankAccount> accounts = bankAccountService.getByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    /**
     * 新增銀行帳戶
     */
    @PostMapping
    public ResponseEntity<?> addBankAccount(
            @RequestBody Map<String, Object> request,
            @RequestHeader("Authorization") String authHeader) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "請先登入"));
        }

        try {
            String bankCode = (String) request.get("bankCode");
            String bankName = (String) request.get("bankName");
            String accountNumber = (String) request.get("accountNumber");
            String accountName = (String) request.get("accountName");

            // 驗證必填欄位
            if (bankCode == null || bankName == null || accountNumber == null || accountName == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "請填寫所有必填欄位"));
            }

            BankAccount account = bankAccountService.addBankAccount(
                    currentUserId, bankCode, bankName, accountNumber, accountName);

            return ResponseEntity.ok(Map.of(
                    "message", "銀行帳戶綁定成功！獲得 15 升級積分 + 15 獎勵積分",
                    "account", account,
                    "taskCompleted", true
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 刪除銀行帳戶
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteBankAccount(
            @PathVariable Long accountId,
            @RequestHeader("Authorization") String authHeader) {
        Long currentUserId = getCurrentUserId(authHeader);
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "請先登入"));
        }

        try {
            bankAccountService.deleteBankAccount(accountId, currentUserId);
            return ResponseEntity.ok(Map.of("message", "銀行帳戶已刪除"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
