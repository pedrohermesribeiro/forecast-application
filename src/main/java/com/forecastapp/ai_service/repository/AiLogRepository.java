package com.forecastapp.ai_service.repository;

import com.forecastapp.ai_service.model.AiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiLogRepository extends JpaRepository<AiLog, Long> {
}


