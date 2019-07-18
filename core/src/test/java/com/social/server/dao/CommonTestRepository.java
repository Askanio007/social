package com.social.server.dao;

import com.social.server.WebConfig;
import com.social.server.entity.Sex;
import com.social.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = {WebConfig.class})
public class CommonTestRepository {
    final static Pageable pagination = PageRequest.of(0, 10);

    @Autowired
    TestEntityManager entityManager;

    public User createUser() {
        User user = User.builder()
                .email("TEST")
                .surname("TEST")
                .name("TEST")
                .password("TEST")
                .sex(Sex.MALE)
                .create();
        user.getDetails().setUser(user);
        return entityManager.persist(user);
    }

}
