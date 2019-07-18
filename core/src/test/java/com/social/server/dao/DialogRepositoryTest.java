package com.social.server.dao;

import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
public class DialogRepositoryTest extends CommonTestRepository {

    @Autowired
    private DialogRepository dialogRepository;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    @Before
    public void setUp() {
        user1 = createUser();
        user2 = createUser();
        user3 = createUser();
        user4 = createUser();
        user5 = createUser();

        createExpectedDialog("EXPECTED", user1, user2);
        createExpectedDialog("EXPECTED", user1, user4);
        createExpectedDialog("FAKE", user3, user4);
        createExpectedDialog("FAKE", user4, user5);
    }

    @Test
    public void dialogListByUserIdTest() {
        Page<Dialog> dialogs = dialogRepository.findByUsersIdInOrderByDateLastMessageDesc(user1.getId(), pagination);
        Assert.assertEquals(dialogs.getContent().size(), 2);
        dialogs.getContent().forEach((d) -> Assert.assertEquals(d.getLastMessage(), "EXPECTED"));
    }

    @Test
    public void dialogByUsersIdTest() {
        Dialog dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user1.getId(), user2.getId())));
        Assert.assertNotNull(dialog);

        dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user1.getId(), user4.getId())));
        Assert.assertNotNull(dialog);

        dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user3.getId(), user4.getId())));
        Assert.assertNotNull(dialog);

        dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user4.getId(), user5.getId())));
        Assert.assertNotNull(dialog);

        dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user2.getId(), user5.getId())));
        Assert.assertNull(dialog);

        dialog = dialogRepository.findOneByUsersId(new HashSet<>(Arrays.asList(user1.getId(), user3.getId())));
        Assert.assertNull(dialog);
    }

    private void createExpectedDialog(String lastMessage, User... users) {
        Dialog dialog = new Dialog();
        dialog.setDateLastMessage(LocalDateTime.now());
        dialog.setLastMessage(lastMessage);
        dialog.setUsers(new HashSet<>(Arrays.asList(users)));
        entityManager.persist(dialog);
    }
}
