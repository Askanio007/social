package com.social.server.service.impl;

import com.social.server.service.EmailService;
import com.social.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final MessageService messageService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            SpringTemplateEngine springTemplateEngine,
                            MessageService messageService) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
        this.messageService = messageService;
    }

    @Override
    public void sendRestorePasswordMail(String email, String token, String mirror) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
                .append(mirror)
                .append("/api/v1/forgot-password?token=")
                .append(token);
        params.put("link", urlBuilder.toString());
        send(email, messageService.getMessage("mail.restore.password.subject"), params, "restorePassword");
    }

    private void send(String email, String subject, Map<String, Object> params, String template) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            Context context = new Context();
            context.setVariables(params);
            messageHelper.setText(springTemplateEngine.process(template ,context), true);
        };

        javaMailSender.send(messagePreparator);
    }
}
