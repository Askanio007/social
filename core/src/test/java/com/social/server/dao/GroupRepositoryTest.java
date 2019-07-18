package com.social.server.dao;

import com.social.server.entity.Group;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
public class GroupRepositoryTest extends CommonTestRepository {

    @Autowired
    private GroupRepository groupRepository;
    private User user1;

    @Before
    public void setUp() {
        user1 = createUser();
    }

    @Test
    public void findGroupByParticipantTest() {
        createGroup(3, user1);
        Page<Group> groups = groupRepository.findByUsersIdIn(user1.getId(), pagination);
        Assert.assertEquals(groups.getContent().size(), 3);
    }

    @Test
    public void countGroupByParticipantTest() {
        createGroup(4, user1);
        Assert.assertEquals(groupRepository.countAllByUsersIdIn(user1.getId()), 4);
    }

    @Test
    public void existByParticipant() {
        Group group = createGroup(user1);
        Assert.assertTrue(groupRepository.existsByIdAndUsersIdIn(group.getId(), user1.getId()));
        Assert.assertFalse(groupRepository.existsByIdAndUsersIdIn(group.getId(), 12L));
    }

    @Test
    public void searchByNameTest() {
        createGroup(4, user1);

        List<Group> groups = groupRepository.searchByName("TEST");
        Assert.assertEquals(groups.size(), 4);

        groups = groupRepository.searchByName("sad");
        Assert.assertEquals(groups.size(), 0);

        groups = groupRepository.searchByName("ES");
        Assert.assertEquals(groups.size(), 4);
    }

    @Test
    public void countParticipantTest() {
        Group group = createGroup(user1, createUser(), createUser());
        Assert.assertEquals(groupRepository.countParticipant(group.getId()), 3);
    }


    private void createGroup(int count, User... participant) {
        for (int i = 0; i < count; i++) {
            createGroup(participant);
        }
    }

    private Group createGroup(User... participant) {
        Group group = new Group();
        group.setDescription("TEST");
        group.setName("TEST");
        group.setAdmin(user1);
        group.setUsers(new HashSet<>(Arrays.asList(participant)));
        return entityManager.persist(group);
    }
}
