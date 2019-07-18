package com.social.server.dao;

import com.social.server.entity.PasswordResetToken;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class PasswordResetTokenRepositoryTest extends CommonTestRepository {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private User user;

    @Before
    public void setUp() {
        user = createUser();
        createPasswordResetToken();
    }

    @Test
    public void findByUserIdTest() {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());
        Assert.assertNotNull(passwordResetToken);
    }

    @Test
    public void findByTokenTest() {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken("TEST");
        Assert.assertNotNull(passwordResetToken);
    }

    private void createPasswordResetToken() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpiredDate(LocalDateTime.now());
        token.setToken("TEST");
        token.setUser(user);
        entityManager.persist(token);
    }
}
