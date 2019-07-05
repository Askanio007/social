package com.social.server.service.impl;

import com.social.server.dao.EventRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.EventDto;
import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import com.social.server.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createEvent(long userId, long targetId, String targetActionName, EventType type) {
        Event event = new Event();
        event.setType(type);
        event.setTargetActionName(targetActionName);
        event.setTargetActionId(targetId);
        event.setUser(userRepository.getOne(userId));
        eventRepository.save(event);
    }
}
