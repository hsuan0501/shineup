package com.shineup.backend.repository;

import com.shineup.backend.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByUser_Id(Long userId);
    List<BankAccount> findByUser_IdOrderByCreatedAtDesc(Long userId);
    boolean existsByUser_IdAndAccountNumber(Long userId, String accountNumber);
    long countByUser_Id(Long userId);
}
