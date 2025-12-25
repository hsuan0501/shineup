package com.shineup.backend.service;

import com.shineup.backend.entity.BankAccount;
import com.shineup.backend.entity.User;
import com.shineup.backend.repository.BankAccountRepository;
import com.shineup.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {

    private static final Long BIND_BANK_TASK_ID = 6L; // 綁定銀行帳戶任務 ID

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    /**
     * 取得用戶的銀行帳戶列表
     */
    public List<BankAccount> getByUserId(Long userId) {
        return bankAccountRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    /**
     * 新增銀行帳戶
     * 每新增一個帳戶，完成一次任務獲得積分
     */
    @Transactional
    public BankAccount addBankAccount(Long userId, String bankCode, String bankName,
                                       String accountNumber, String accountName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用戶不存在"));

        // 檢查是否已經綁定過相同帳號
        if (bankAccountRepository.existsByUser_IdAndAccountNumber(userId, accountNumber)) {
            throw new RuntimeException("此帳號已經綁定過了");
        }

        // 建立銀行帳戶
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBankCode(bankCode);
        bankAccount.setBankName(bankName);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setAccountName(accountName);

        BankAccount saved = bankAccountRepository.save(bankAccount);

        // 完成綁定銀行帳戶任務（無限次，每次都給積分）
        try {
            taskService.completeTask(BIND_BANK_TASK_ID, userId);
            log.info("用戶 {} 完成綁定銀行帳戶任務", userId);
        } catch (Exception e) {
            log.error("完成綁定銀行帳戶任務失敗: {}", e.getMessage());
        }

        return saved;
    }

    /**
     * 刪除銀行帳戶
     */
    @Transactional
    public void deleteBankAccount(Long accountId, Long userId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("帳戶不存在"));

        // 確認是該用戶的帳戶
        if (!account.getUser().getId().equals(userId)) {
            throw new RuntimeException("無權限刪除此帳戶");
        }

        bankAccountRepository.delete(account);
        log.info("用戶 {} 刪除銀行帳戶 {}", userId, accountId);
    }

    /**
     * 取得用戶綁定的銀行帳戶數量
     */
    public int getAccountCount(Long userId) {
        return bankAccountRepository.findByUser_Id(userId).size();
    }
}
