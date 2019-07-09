package com.social.server.dao;

import com.social.server.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUserId(long userId);
    PasswordResetToken findByToken(String token);
}
