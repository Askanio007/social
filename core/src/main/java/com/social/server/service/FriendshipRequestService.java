package com.social.server.service;

import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.UserRelation;

import java.util.List;

public interface FriendshipRequestService {
    void add(FriendshipRequestDto dto);
    void accept(long friendshipRequestId);
    void decline(long friendshipRequestId);
    List<FriendshipRequestDto> find(long userId);
    boolean isFriendRequest(long rootUserId, long userId);
    UserRelation getRelation(long rootUserId, long userId);
}
