package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.EventType;
import com.social.server.entity.User;
import com.social.server.service.EventService;
import com.social.server.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final EventService eventService;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository,
                             EventService eventService) {
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    @Override
    public void addFriend(long userId, long friendId) {
        User user = userRepository.findById(userId).get();
        User friend = userRepository.findById(friendId).get();
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.saveAll(Arrays.asList(user, friend));
        eventService.createEvent(userId, EventType.ADD_FRIEND, friend.getId(), friend.getFullName());
    }

    @Override
    public List<UserDto> find(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            log.error("user not found, id = " + userId);
            return Collections.emptyList();
        }
        return UserDto.of(userOptional.get().getFriends());
    }

    @Override
    public boolean isFriends(long rootUserId, long userId) {
        return userRepository.existsByIdAndFriends(rootUserId, userRepository.getOne(userId));
    }
}
