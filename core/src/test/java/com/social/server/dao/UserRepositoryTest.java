package com.social.server.dao;

import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
public class UserRepositoryTest extends CommonTestRepository {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @Before
    public void setUp() {
        user = createUser();
    }

    @Test
    public void existByEmailTest() {
        Assert.assertTrue(userRepository.existsByEmail("TEST"));
        Assert.assertFalse(userRepository.existsByEmail("TEST2"));
    }

    @Test
    public void findByEmailTest() {
        Assert.assertNotNull(userRepository.findByEmail("TEST"));
        Assert.assertNull(userRepository.findByEmail("TEST1"));
    }

    @Test
    public void countFriendsTest() {
        addFriend(3);
        Assert.assertEquals(userRepository.countAllByFriendsIdIn(user.getId()), 3);
    }

    @Test
    public void existFriendsTest() {
        User user2 = createUser();
        entityManager.persist(user2);
        user.getFriends().add(user2);
        user2.getFriends().add(user);
        entityManager.persist(user);
        Assert.assertTrue(userRepository.existsByIdAndFriends(user.getId(), user2));
        Assert.assertFalse(userRepository.existsByIdAndFriends(user.getId(), createUser()));
    }

    @Test
    public void findFriendsTest() {
        addFriend(5);
        Page<User> friends = userRepository.findFriendsBy(user.getId(), pagination);
        Assert.assertEquals(friends.getContent().size(), 5);
    }

    @Test
    public void searchByName() {
        createUser();
        Set<User> users = userRepository.searchByFullName(user.getId(), "TEST", "TEST");
        Assert.assertEquals(users.size(), 1);
    }

    private void addFriend(int count) {
        for (int i = 0; i < count; i++) {
            User friend = createUser();
            friend.getFriends().add(user);
            user.getFriends().add(friend);
            entityManager.persist(friend);
            entityManager.persist(user);
        }
    }

}
