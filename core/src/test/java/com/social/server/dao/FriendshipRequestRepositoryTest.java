package com.social.server.dao;

import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FriendshipRequestRepositoryTest extends CommonTestRepository {

    @Autowired
    private FriendshipRequestRepository friendshipRequestRepository;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        user1 = createUser();
        user2 = createUser();
    }

    @Test
    public void findAllNotAcceptRequestTest() {
        createRequests(5, false);
        createRequests(3, true);
        Page<FriendshipRequest> list = friendshipRequestRepository.findAllByAcceptIsFalseAndRequestToId(user1.getId(), pagination);
        Assert.assertEquals(list.getContent().size(), 5);
    }

    @Test
    public void countNotAcceptRequestTest() {
        createRequests(6, false);
        createRequests(2, true);
        long count = friendshipRequestRepository.countByRequestToIdAndAcceptIsFalse(user1.getId());
        Assert.assertEquals(count, 6);
    }

    @Test
    public void existRequestTest() {
        createRequests(1, false);
        Assert.assertTrue(friendshipRequestRepository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(user2.getId(), user1.getId()));
        Assert.assertFalse(friendshipRequestRepository.existsByRequestFromIdAndRequestToIdAndAcceptIsFalse(user1.getId(), createUser().getId()));
    }

    private void createRequests(int count, boolean accept) {
        for (int i = 0; i < count; i++) {
            FriendshipRequest request = new FriendshipRequest();
            request.setRequestTo(user1);
            request.setRequestFrom(user2);
            request.setAccept(accept);
            entityManager.persist(request);
        }
    }
}
