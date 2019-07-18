package com.social.server.dao;

import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import com.social.server.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class EventRepositoryTest extends CommonTestRepository {

    @Autowired
    private EventRepository eventRepository;
    private User user3;
    private LocalDateTime expectedTime;

    @Before
    public void setUp() {
        expectedTime = LocalDateTime.now().plusDays(2);
        User user1 = createUser();
        User user2 = createUser();
        user3 = createUser();

        createEvent(user1, expectedTime);
        createEvent(user1, LocalDateTime.now());
        createEvent(user1, LocalDateTime.now());

        createEvent(user2, LocalDateTime.now());
        createEvent(user2, LocalDateTime.now());

        user1.getFriends().add(user3);
        user2.getFriends().add(user3);

        entityManager.persist(user1);
        entityManager.persist(user2);
    }

    @Test
    public void findByUserFriendsIdTest() {
        Page<Event> events = eventRepository.findByUserFriendsIdInOrderByDateDesc(user3.getId(), pagination);
        Assert.assertEquals(events.getContent().size(), 5);
        Assert.assertEquals(events.getContent().get(0).getDate(), expectedTime);
    }

    private void createEvent(User user, LocalDateTime date) {
        Event event = new Event();
        event.setTargetActionId(1L);
        event.setTargetActionName("TEST");
        event.setType(EventType.ENTER_GROUP);
        event.setUser(user);
        event.setDate(date);
        entityManager.persist(event);

    }
}
