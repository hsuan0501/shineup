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

    // 寄送信箱驗證碼
    public void sendVerificationEmail(String toEmail, String userName, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail, "ShineUp");
        helper.setTo(toEmail);
        helper.setSubject("【ShineUp】信箱驗證碼");

        String htmlContent = """
            <div style="font-family: 'Microsoft JhengHei', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 40px 20px; background: #ffffff;">
                <p style="margin: 0 0 30px 0; font-size: 32px; font-weight: bold; text-align: center; color: #38bdf8;">
                    ShineUp
                </p>

                <p style="color: #374151; font-size: 15px; line-height: 1.8; text-align: center;">
                    %s 您好，感謝您註冊 ShineUp！<br>
                    請輸入以下驗證碼來完成註冊：
                </p>

                <table cellpadding="0" cellspacing="0" border="0" style="margin: 30px auto;">
                    <tr>
                        <td bgcolor="#38bdf8" style="border-radius: 50px; padding: 15px 40px;">
                            <span style="color: #ffffff; font-size: 28px; font-weight: bold; letter-spacing: 8px;">
                                %s
                            </span>
                        </td>
                    </tr>
                </table>

                <p style="color: #9ca3af; font-size: 13px; line-height: 1.6; text-align: center;">
                    此驗證碼將在 10 分鐘後失效。<br>
                    如果您沒有註冊 ShineUp，請忽略此信件。
                </p>

                <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 25px 0;">

                <p style="color: #9ca3af; font-size: 12px; text-align: center;">
                    此為系統自動發送的信件，請勿直接回覆。
                </p>
            </div>
            """.formatted(userName, code);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    // 寄送重設密碼信件
    public void sendPasswordResetEmail(String toEmail, String userName, String resetUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail, "ShineUp");
        helper.setTo(toEmail);
        helper.setSubject("【ShineUp】重設密碼");

        String htmlContent = """
            <div style="font-family: 'Microsoft JhengHei', Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 40px 20px; background: #ffffff;">
                <p style="margin: 0 0 30px 0; font-size: 32px; font-weight: bold; text-align: center; color: #38bdf8;">
                    ShineUp
                </p>

                <p style="color: #374151; font-size: 15px; line-height: 1.8; text-align: center;">
                    %s 您好，收到您重設密碼的請求，<br>
                    請點擊以下按鈕設定新密碼。
                </p>

                <table cellpadding="0" cellspacing="0" border="0" style="margin: 30px auto;">
                    <tr>
                        <td bgcolor="#38bdf8" style="border-radius: 50px;">
                            <a href="%s" style="display: inline-block; color: #ffffff; padding: 12px 40px; text-decoration: none; font-weight: bold; font-size: 15px;">
                                重設密碼
                            </a>
                        </td>
                    </tr>
                </table>

                <p style="color: #9ca3af; font-size: 13px; line-height: 1.6; text-align: center;">
                    此連結將在 30 分鐘後失效。<br>
                    如果您沒有請求重設密碼，請忽略此信件。
                </p>

                <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 25px 0;">

                <p style="color: #9ca3af; font-size: 12px; text-align: center;">
                    此為系統自動發送的信件，請勿直接回覆。
                </p>
            </div>
            """.formatted(userName, resetUrl);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
