package com.springboot.helper.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Primary
@Slf4j
public class TemplateEmailSendable implements EmailSendable {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Context context;

    public TemplateEmailSendable(JavaMailSender javaMailSender,
                                 TemplateEngine templateEngine,
                                 Context context) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.context = context;
    }

    @Override
    public void send(String[] to, String subject, String message, String templateName) {
        // 템플릿을 사용한 이메일을 보낼 수 있습니다.
        try {
            context.setVariable("message", message);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setFrom("tjsk2222@gmail.com", "쿡킹 🍳");

            javaMailSender.send(mimeMessage);
            log.info("Sent Template email!");
        } catch (Exception e) {
            log.error("email send error: ", e);
        }

    }
}