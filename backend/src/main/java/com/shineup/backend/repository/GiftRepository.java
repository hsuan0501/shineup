package com.shineup.backend.repository;

import com.shineup.backend.entity.Gift;
import com.shineup.backend.entity.User.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findBySeries(String series);
    List<Gift> findByRequiredLevel(MemberLevel level);
    List<Gift> findByStockGreaterThan(Integer stock);
    Optional<Gift> findByTitle(String title);
}
