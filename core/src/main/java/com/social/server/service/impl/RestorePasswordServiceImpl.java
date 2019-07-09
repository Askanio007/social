package com.social.server.service.impl;

import com.social.server.dao.PasswordResetTokenRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.PasswordResetToken;
import com.social.server.entity.User;
import com.social.server.service.EmailService;
import com.social.server.service.RestorePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RestorePasswordServiceImpl implements RestorePasswordService {
    private final static int PASSWORD_RESTORE_TOKEN_LIVE_TIME = 30; //days
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Autowired
    public RestorePasswordServiceImpl(UserRepository userRepository,
                                      PasswordResetTokenRepository passwordResetTokenRepository,
                                      EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public boolean sendRestoreLinkTo(String email) {
        if (!userRepository.existsByEmail(email)) {
            return false;
        }
        User user = userRepository.findByEmail(email);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());
        if (passwordResetToken == null) {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
        }
        LocalDateTime time = LocalDateTime.now();
        time = time.plusDays(10);
        passwordResetToken.setExpiredDate(time);
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendRestorePasswordMail(email, passwordResetToken.getToken());
        return true;
    }

    @Override
    public UserDto checkRestorePasswordToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiredDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        passwordResetToken.setExpiredDate(LocalDateTime.now());
        return UserDto.of(passwordResetToken.getUser());
    }
}
