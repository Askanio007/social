package com.social.server.service;

import com.social.server.dto.UserDto;
import com.social.server.entity.User;

import java.util.List;

public interface FriendService extends CommonService<User, Long> {
    void addFriend(long rootUserId, long friendId);
    List<UserDto> find(long userId);
    boolean isFriends(long rootUserId, long friendId);
    void remove(long rootUserId, long friendId);
}

