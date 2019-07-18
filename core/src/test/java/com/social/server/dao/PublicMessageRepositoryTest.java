package com.social.server.dao;

import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class PublicMessageRepositoryTest extends CommonTestRepository {
    private final static Long USER_ID = 1L;

    @Autowired
    private PublicMessageRepository repository;

    @Before
    public void setUp() {
        createPublicMessage();
    }

    @Test
    public void findByRecipientIdAndRecipientTypeTest() {
        Assert.assertEquals(repository.findAllByRecipientIdAndRecipientType(USER_ID, PublicMessageRecipientType.USER, pagination).getContent().size(), 1);
        Assert.assertEquals(repository.findAllByRecipientIdAndRecipientType(2L, PublicMessageRecipientType.USER, pagination).getContent().size(), 0);
        Assert.assertEquals(repository.findAllByRecipientIdAndRecipientType(1L, PublicMessageRecipientType.GROUP, pagination).getContent().size(), 0);
    }

    private void createPublicMessage() {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setMessage("TEST");
        publicMessage.setRecipientType(PublicMessageRecipientType.USER);
        publicMessage.setRecipientId(USER_ID);
        publicMessage.setSender(createUser());
        publicMessage.setCreateDate(LocalDateTime.now());
        entityManager.persist(publicMessage);
    }
}
