package com.social.server.service;

import com.social.server.dto.FriendshipRequestDto;

import java.util.List;

public interface FriendshipRequestService {
    void add(FriendshipRequestDto dto);
    void accept(long friendshipRequestId);
    void decline(long friendshipRequestId);
    List<FriendshipRequestDto> find(long userId);
}
