package com.shineup.backend.repository;

import com.shineup.backend.entity.LevelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelConfigRepository extends JpaRepository<LevelConfig, Long> {
    Optional<LevelConfig> findByLevelCode(String levelCode);
    Optional<LevelConfig> findByLevelNumber(Integer levelNumber);
}
