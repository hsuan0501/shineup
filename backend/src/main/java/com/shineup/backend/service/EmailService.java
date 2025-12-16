package com.shineup.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // 寄送重設密碼信件
    public void sendPasswordResetEmail(String toEmail, String resetUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail, "ShineUp");
        helper.setTo(toEmail);
        helper.setSubject("【ShineUp】重設密碼");

        String htmlContent = """
            <div style="font-family: 'Microsoft JhengHei', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
                <div style="background: linear-gradient(135deg, #06b6d4, #3b82f6); padding: 30px; border-radius: 16px 16px 0 0; text-align: center;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">ShineUp</h1>
                    <p style="color: rgba(255,255,255,0.9); margin: 10px 0 0 0;">重設您的密碼</p>
                </div>

                <div style="background: #ffffff; padding: 30px; border: 1px solid #e5e7eb; border-top: none; border-radius: 0 0 16px 16px;">
                    <p style="color: #374151; font-size: 16px; line-height: 1.6;">您好，</p>
                    <p style="color: #374151; font-size: 16px; line-height: 1.6;">
                        我們收到了您重設密碼的請求。請點擊下方按鈕來設定新密碼：
                    </p>

                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="display: inline-block; background: linear-gradient(135deg, #06b6d4, #3b82f6); color: white; padding: 14px 32px; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 16px;">
                            重設密碼
                        </a>
                    </div>

                    <p style="color: #6b7280; font-size: 14px; line-height: 1.6;">
                        此連結將在 <strong>30 分鐘</strong>後失效。
                    </p>
                    <p style="color: #6b7280; font-size: 14px; line-height: 1.6;">
                        如果您沒有請求重設密碼，請忽略此信件，您的帳號將保持安全。
                    </p>

                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 25px 0;">

                    <p style="color: #9ca3af; font-size: 12px; text-align: center;">
                        此為系統自動發送的信件，請勿直接回覆。
                    </p>
                </div>
            </div>
            """.formatted(resetUrl);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
