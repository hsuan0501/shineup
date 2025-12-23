package com.shineup.backend.service;

import com.shineup.backend.entity.ChatbotReply;
import com.shineup.backend.repository.ChatbotReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * FAQ 客服服務 - 使用資料庫快速回覆設定
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatbotReplyRepository chatbotReplyRepository;

    /**
     * 常見問題快速回覆 - 從資料庫讀取設定
     */
    private String getQuickReply(String message) {
        String msg = message.toLowerCase();

        // 從資料庫讀取所有快速回覆設定
        List<ChatbotReply> replies = chatbotReplyRepository.findAll();

        for (ChatbotReply reply : replies) {
            String keyword = reply.getKeyword();
            // 將關鍵字用 | 分隔成多個，然後用正則表達式匹配
            try {
                Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
                if (pattern.matcher(msg).find()) {
                    return reply.getReply();
                }
            } catch (Exception e) {
                // 如果正則有問題，使用簡單的 contains 匹配
                String[] keywords = keyword.split("\\|");
                for (String kw : keywords) {
                    if (msg.contains(kw.trim().toLowerCase())) {
                        return reply.getReply();
                    }
                }
            }
        }

        // 預設回覆：打招呼
        if (msg.matches(".*(你好|嗨|哈囉|hello|hi|hey).*") && msg.length() < 10) {
            return "您好！我是 ShineUp 智能客服，很高興為您服務！請問有什麼可以幫助您的嗎？\n\n您可以問我關於：\n• 積分獲得方式\n• 等級制度說明\n• 禮品兌換流程\n• 訂單出貨進度";
        }

        // 預設回覆：感謝
        if (msg.contains("謝謝") || msg.contains("感謝") || msg.contains("thank")) {
            return "不客氣！很高興能幫助到您。如果還有其他問題，隨時歡迎詢問！祝您使用愉快 ✨";
        }

        // 沒有匹配到，返回預設回覆
        return "抱歉，我無法理解您的問題。您可以試試點擊下方的快速問題按鈕，或聯繫人工客服：support@shineup.com";
    }

    /**
     * 取得所有快速回覆設定
     */
    public List<ChatbotReply> getAllReplies() {
        return chatbotReplyRepository.findAll();
    }

    /**
     * 儲存或更新快速回覆
     */
    public ChatbotReply saveReply(ChatbotReply reply) {
        return chatbotReplyRepository.save(reply);
    }

    /**
     * 批量儲存快速回覆
     */
    public List<ChatbotReply> saveAllReplies(List<ChatbotReply> replies) {
        return chatbotReplyRepository.saveAll(replies);
    }

    /**
     * 刪除快速回覆
     */
    public void deleteReply(String id) {
        chatbotReplyRepository.deleteById(id);
    }

    /**
     * 發送訊息給客服（純 FAQ 模式）
     */
    public String chat(String userMessage) {
        return getQuickReply(userMessage);
    }
}
