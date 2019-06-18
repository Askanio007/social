package com.social.server.service;

import com.social.server.dto.GroupDto;

import java.util.List;

public interface GroupService {
    List<GroupDto> findBy(long userId);
    GroupDto find(long groupId);
    GroupDto create(long adminId, GroupDto groupDto);
    boolean isUserHasGroup(long userId, long groupId);
    void join(long userId, long groupId);
    List<GroupDto> search(String name);
}
