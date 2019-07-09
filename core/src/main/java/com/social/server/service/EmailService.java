package com.social.server.service;

public interface EmailService {
    void sendRestorePasswordMail(String email, String token);
}
