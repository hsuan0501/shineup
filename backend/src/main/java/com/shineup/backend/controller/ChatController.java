package com.shineup.backend.controller;

import com.shineup.backend.entity.ChatbotReply;
import com.shineup.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AI 客服 API
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 發送訊息給客服
     */
    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");

        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "請輸入訊息"
            ));
        }

        String reply = chatService.chat(message);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "reply", reply
        ));
    }

    /**
     * 取得所有快速回覆設定（管理員用）
     */
    @GetMapping("/replies")
    public ResponseEntity<List<ChatbotReply>> getAllReplies() {
        return ResponseEntity.ok(chatService.getAllReplies());
    }

    /**
     * 儲存快速回覆設定（管理員用）
     */
    @PostMapping("/replies")
    public ResponseEntity<ChatbotReply> saveReply(@RequestBody ChatbotReply reply) {
        return ResponseEntity.ok(chatService.saveReply(reply));
    }

    /**
     * 批量儲存快速回覆設定（管理員用）
     */
    @PutMapping("/replies")
    public ResponseEntity<List<ChatbotReply>> saveAllReplies(@RequestBody List<ChatbotReply> replies) {
        return ResponseEntity.ok(chatService.saveAllReplies(replies));
    }

    /**
     * 刪除快速回覆設定（管理員用）
     */
    @DeleteMapping("/replies/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable String id) {
        chatService.deleteReply(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "已刪除"));
    }
}
