package com.social.server.dao;

import com.social.server.entity.Dialog;
import com.social.server.entity.PrivateMessage;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
public class PrivateMessageRepositoryTest extends CommonTestRepository {

    @Autowired
    private PrivateMessageRepository repository;
    private User user;
    private Dialog dialog;

    @Before
    public void setUp() {
        user = createUser();
        dialog = createDialog();
    }

    @Test
    public void readMessageTest() {
        PrivateMessage privateMessage = createPrivateMessage(dialog);
        int i = repository.readMessage(user.getId(), privateMessage.getId());
        Assert.assertEquals(i, 1);
    }

    @Test
    public void readMessageInDialogTest() {
        createPrivateMessage(dialog);
        int i = repository.readMessageByDialogId(user.getId(), dialog.getId());
        Assert.assertEquals(i, 1);
    }

    @Test
    public void findMessagesByDialogIdTest() {
        createPrivateMessage(5, dialog);
        List<PrivateMessage> list = repository.findByDialogIdOrderByCreateDateDesc(dialog.getId(), pagination);
        Assert.assertEquals(list.size(), 5);
    }

    @Test
    public void findNotReadMessageTest() {
        Dialog dialog1 = createDialog();
        createPrivateMessage(10, dialog1);
        Assert.assertEquals(repository.countNotReadMessages(user.getId()), 10);
    }

    private Dialog createDialog() {
        dialog = new Dialog();
        dialog.setUsers(new HashSet<>(Arrays.asList(user)));
        return entityManager.persist(dialog);
    }

    private void createPrivateMessage(int countMessage, Dialog dialog) {
        for (int i = 0; i < countMessage; i++) {
            createPrivateMessage(dialog);
        }
    }

    private PrivateMessage createPrivateMessage(Dialog dialog) {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setDialog(dialog);
        privateMessage.setMessage("TEST");
        privateMessage.setCreateDate(LocalDateTime.now());
        privateMessage.setRead(false);
        privateMessage.setSender(createUser());
        return entityManager.persist(privateMessage);
    }
}
