package com.social.server.service.impl;

import com.social.server.dao.EventRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.EventDto;
import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import com.social.server.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
    public Page<EventDto> findBy(long userId, int page) {
        return eventRepository.findByUserFriendsIdInOrderByDateDesc(userId, PageRequest.of(page, 10)).map(EventDto::of);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createEvent(long userId, long targetId, String targetActionName, EventType type) {
        log.debug("Create new event userId={}; targetId={}; targetActionName={}; type={}", userId, targetId, targetActionName, type);
        Event event = new Event();
        event.setType(type);
        event.setTargetActionName(targetActionName);
        event.setTargetActionId(targetId);
        event.setUser(userRepository.getOne(userId));
        log.debug("Save event");
        eventRepository.save(event);
        log.debug("Creation completed successfully");
    }

    private void validate(long userId, long targetId, EventType type) {
        if (userId == 0 || targetId == 0 || type == null) {
            throw new IllegalArgumentException();
        }
    }
}
