package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.EventType;
import com.social.server.entity.Group;
import com.social.server.entity.Image;
import com.social.server.entity.User;
import com.social.server.service.EventService;
import com.social.server.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtil;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserRepository userRepository,
                            EventService eventService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
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
        group = groupRepository.save(group);
        eventService.createEvent(admin.getId(), EventType.ENTER_GROUP, group.getId(), group.getName());
        savePhoto(group.getId(), groupDto.getFile());
        return GroupDto.of(group);
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
        eventService.createEvent(userId, EventType.ENTER_GROUP, group.getId(), group.getName());
    }

    @Override
    public List<GroupDto> search(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return GroupDto.of(groupRepository.searchByName(name.toLowerCase()));
    }

    @Override
    public void savePhoto(long groupId, MultipartFile file) {
        Group group = groupRepository.findById(groupId).get();
        Path filePath = FileUtil.writeFile(file);
        if (filePath != null) {
            group.setImage(Image.of(file.getName(), filePath));
            groupRepository.save(group);
        }
    }
}
