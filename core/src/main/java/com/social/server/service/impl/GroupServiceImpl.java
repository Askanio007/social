package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.Group;
import com.social.server.entity.User;
import com.social.server.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GroupDto> findBy(long userId) {
        return GroupDto.of(groupRepository.findByUsersIdIn(userId));
    }

    @Override
    public GroupDto find(long groupId) {
        return GroupDto.of(groupRepository.findById(groupId));
    }

    @Override
    public GroupDto create(long adminId, GroupDto groupDto) {
        User admin = userRepository.findById(adminId).get();
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setAdmin(admin);
        group.getUsers().add(admin);
        return GroupDto.of(groupRepository.save(group));
    }

    @Override
    public boolean isUserHasGroup(long userId, long groupId) {
        return groupRepository.existsByIdAndUsersIdIn(groupId, userId);
    }

    @Override
    public void join(long userId, long groupId) {
        Group group = groupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        group.getUsers().add(user);
        user.getGroups().add(group);
        groupRepository.save(group);
        userRepository.save(user);
    }

    @Override
    public List<GroupDto> search(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return GroupDto.of(groupRepository.searchByName(name.toLowerCase()));
    }
}
