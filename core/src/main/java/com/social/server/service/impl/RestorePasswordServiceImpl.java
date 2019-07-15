package com.social.server.service.impl;

import com.social.server.dao.PasswordResetTokenRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.PasswordResetToken;
import com.social.server.entity.User;
import com.social.server.exception.EmailNotFoundException;
import com.social.server.service.EmailService;
import com.social.server.service.RestorePasswordService;
import com.social.server.service.transactional.ReadTransactional;
import com.social.server.service.transactional.WriteTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class RestorePasswordServiceImpl implements RestorePasswordService {
    private final static int PASSWORD_RESTORE_TOKEN_LIVE_TIME = 10; //days
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
    @WriteTransactional
    public void sendRestoreLinkTo(String email, String mirror) {
        log.debug("Send restore password link to email {}", email);
        if (!userRepository.existsByEmail(email)) {
            log.debug("Email {} not exist in system", email);
            throw new EmailNotFoundException("Email {} not exist in system", email);
        }

        User user = userRepository.findByEmail(email);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());
        if (passwordResetToken == null) {
            log.debug("PasswordResetToken is null, create new");
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
            log.debug("save PasswordResetToken to db");
            passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
        }
        LocalDateTime time = LocalDateTime.now();
        time = time.plusDays(PASSWORD_RESTORE_TOKEN_LIVE_TIME);
        passwordResetToken.setExpiredDate(time);
        passwordResetToken.setToken(UUID.randomUUID().toString());

        log.debug("Send message");
        emailService.sendRestorePasswordMail(email, passwordResetToken.getToken(), mirror);
    }

    @Override
    @ReadTransactional
    public UserDto checkRestorePasswordToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiredDate().isBefore(LocalDateTime.now())) {
            log.debug("Token is expired; token={}", token);
            return null;
        }
        passwordResetToken.setExpiredDate(LocalDateTime.now());
        return UserDto.of(passwordResetToken.getUser());
    }
}
