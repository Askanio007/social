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
import java.util.List;

@Slf4j
@Service
public class FriendServiceImpl extends CommonServiceImpl<User, Long, UserRepository> implements FriendService {

    private final EventService eventService;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository,
                             EventService eventService) {
        super(userRepository);
        this.eventService = eventService;
    }

    @Override
    public void addFriend(long rootUserId, long friendId) {
        User user = findById(rootUserId);
        User friend = findById(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        repository.saveAll(Arrays.asList(user, friend));
        eventService.createEvent(rootUserId, EventType.ADD_FRIEND, friend.getId(), friend.getFullName());
    }

    @Override
    public List<UserDto> find(long userId) {
        return UserDto.of(findById(userId).getFriends());
    }

    @Override
    public boolean isFriends(long rootUserId, long friendId) {
        return repository.existsByIdAndFriends(rootUserId, repository.getOne(friendId));
    }

    @Override
    public void remove(long rootUserId, long friendId) {
        User rootUser = findById(rootUserId);
        User friend = findById(friendId);
        rootUser.getFriends().remove(friend);
        friend.getFriends().remove(rootUser);
        repository.saveAll(Arrays.asList(rootUser, friend));
    }
}
