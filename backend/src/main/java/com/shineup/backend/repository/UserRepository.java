package com.shineup.backend.repository;

import com.shineup.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByLineId(String lineId);
    Optional<User> findByReferralCode(String referralCode);
    long countByReferredById(Long referrerId);
}
