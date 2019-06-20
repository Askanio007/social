package com.social.server.service.impl;

import com.social.server.dao.EventRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.EventDto;
import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import com.social.server.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EventDto> findBy(long userId) {
        return EventDto.of(eventRepository.findByUserFriendsIdInOrderByDateDesc(userId));
    }

    @Override
    public void createEvent(long userId, EventType type, long targetId, String targetActionName) {
        switch (type) {
            case ADD_FRIEND: {
                createEvent(userId, targetId, targetActionName, EventType.ADD_FRIEND);
                return;
            }
            case ENTER_GROUP: {
                createEvent(userId, targetId, targetActionName, EventType.ENTER_GROUP);
                return;
            }
            case ADD_PUBLIC_MESSAGE: {
                createEvent(userId, targetId, targetActionName, EventType.ADD_PUBLIC_MESSAGE);
            }
        }

    }

    private void createEvent(long userId, long targetId, String targetActionName, EventType type) {
        Event event = new Event();
        event.setType(type);
        event.setTargetActionName(targetActionName);
        event.setTargetActionId(targetId);
        event.setUser(userRepository.getOne(userId));
        event.setDate(LocalDateTime.now());
        eventRepository.save(event);
    }
}