package com.social.server.service.impl;

import com.social.server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRestorePasswordMail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hello! Token to restore: " + token);
        message.setTo(email);
        message.setSubject("Restore password");
        javaMailSender.send(message);
    }
}
