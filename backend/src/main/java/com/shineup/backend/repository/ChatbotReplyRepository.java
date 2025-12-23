package com.shineup.backend.repository;

import com.shineup.backend.entity.ChatbotReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotReplyRepository extends JpaRepository<ChatbotReply, String> {
}
