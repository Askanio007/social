package com.social.server.service;

import com.social.server.dto.UserDto;

import java.util.List;

public interface FriendService {
    void addFriend(long userId, long friendId);
    List<UserDto> find(long userId);
    boolean isFriends(long rootUserId, long userId);
}

