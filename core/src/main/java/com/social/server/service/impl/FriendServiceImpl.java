package com.social.server.service.impl;

import com.social.server.dao.UserRepository;
import com.social.server.dto.UserDto;
import com.social.server.entity.EventType;
import com.social.server.entity.User;
import com.social.server.service.DialogService;
import com.social.server.service.EventService;
import com.social.server.service.FriendService;
import com.social.server.service.transactional.WriteTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class FriendServiceImpl extends CommonServiceImpl<User, Long, UserRepository> implements FriendService {

    private final EventService eventService;
    private final DialogService dialogService;

    @Autowired
    public FriendServiceImpl(UserRepository repository, EventService eventService, DialogService dialogService) {
        super(repository);
        this.eventService = eventService;
        this.dialogService = dialogService;
    }

    @Override
    @WriteTransactional
    public void addFriend(long rootUserId, long friendId) {
        validateEmptyEntityId(rootUserId, friendId);
        log.debug("Adding to friends. rootUserId={}; friendId={}", rootUserId, friendId);
        User user = getById(rootUserId);
        User friend = getById(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        dialogService.create(Arrays.asList(user, friend));
        eventService.createEvent(rootUserId, friend.getId(), friend.getFullName(), EventType.ADD_FRIEND);
        log.debug("Adding to friends completed successfully");
    }

    @Override
    public Page<UserDto> find(long userId, int page) {
        validateEmptyEntityId(userId);
        return repository.findFriendsBy(userId, PageRequest.of(page, 10)).map(UserDto::of);
    }

    @Override
    public boolean isFriends(long rootUserId, long friendId) {
        validateEmptyEntityId(rootUserId, friendId);
        return repository.existsByIdAndFriends(rootUserId, repository.getOne(friendId));
    }

    @Override
    @WriteTransactional
    public void remove(long rootUserId, long friendId) {
        validateEmptyEntityId(rootUserId, friendId);
        log.debug("Deleting from friends. rootUserId={}; friendId={}", rootUserId, friendId);
        User rootUser = getById(rootUserId);
        User friend = getById(friendId);
        rootUser.getFriends().remove(friend);
        log.debug("Deleting from friends completed successfully");
    }

    @Override
    public long count(long userId) {
        return repository.countAllByFriendsIdIn(userId);
    }
}
