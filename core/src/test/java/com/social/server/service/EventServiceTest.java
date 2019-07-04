package com.social.server.service;

import com.social.server.dao.EventRepository;
import com.social.server.dao.UserRepository;
import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import com.social.server.entity.User;
import com.social.server.service.impl.EventServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class EventServiceTest {
    private final long USER_ID = 1;
    private final Long TARGET_ID = 1L;
    private final EventType TYPE = EventType.ENTER_GROUP;
    private final String TARGET_NAME = "TEST";

    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private EventServiceImpl eventService = new EventServiceImpl(eventRepository, userRepository);

    @Before
    public void setUp() {
        User user = new User();
        user.setName(TARGET_NAME);
        Mockito.when(userRepository.getOne(USER_ID)).thenReturn(user);
    }

    @Test
    public void successCreateEvent() {
        eventService.createEvent(USER_ID, TARGET_ID, TARGET_NAME, TYPE);
        ArgumentCaptor<Event> arg = ArgumentCaptor.forClass(Event.class);
        Mockito.verify(eventRepository).save(arg.capture());
        Mockito.verify(userRepository).getOne(USER_ID);

        Assert.assertEquals(TARGET_NAME, arg.getValue().getUser().getName());
        Assert.assertEquals(TYPE, arg.getValue().getType());
        Assert.assertEquals(TARGET_ID, arg.getValue().getTargetActionId());
    }
}
