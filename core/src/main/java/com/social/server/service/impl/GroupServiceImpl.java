package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.EventType;
import com.social.server.entity.Group;
import com.social.server.entity.Image;
import com.social.server.entity.User;
import com.social.server.http.model.GroupModel;
import com.social.server.service.EventService;
import com.social.server.service.GroupService;
import com.social.server.service.UserService;
import com.social.server.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class GroupServiceImpl extends CommonServiceImpl<Group, Long, GroupRepository> implements GroupService {

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserService userService,
                            EventService eventService) {
        super(groupRepository);
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public List<GroupDto> findBy(long userId) {
        return GroupDto.of(repository.findByUsersIdIn(userId));
    }

    @Override
    public GroupDto find(long groupId) {
        return GroupDto.of(findById(groupId));
    }

    @Override
    public long countParticipant(long groupId) {
        return repository.countParticipant(groupId);
    }

    @Override
    public GroupDto create(long adminId, GroupModel groupModel) {
        User admin = userService.findById(adminId);
        Group group = new Group();
        group.setName(groupModel.getName());
        group.setDescription(groupModel.getDescription());
        group.setAdmin(admin);
        group.getUsers().add(admin);
        group = repository.save(group);
        eventService.createEvent(admin.getId(), EventType.ENTER_GROUP, group.getId(), group.getName());
        return GroupDto.of(group);
    }

    @Override
    public boolean isUserHasGroup(long userId, long groupId) {
        return repository.existsByIdAndUsersIdIn(groupId, userId);
    }

    @Override
    public void join(long userId, long groupId) {
        Group group = findById(groupId);
        User user = userService.findById(userId);
        group.getUsers().add(user);
        user.getGroups().add(group);
        repository.save(group);
        userService.save(user);
        eventService.createEvent(userId, EventType.ENTER_GROUP, group.getId(), group.getName());
    }

    @Override
    public List<GroupDto> search(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return GroupDto.of(repository.searchByName(name.toLowerCase()));
    }

    @Override
    public void savePhoto(long groupId, MultipartFile file) {
        Group group = findById(groupId);
        Path filePath = FileUtil.writeFile(file);
        if (filePath != null) {
            group.setImage(Image.of(file.getName(), filePath));
            repository.save(group);
        }
    }

    @Override
    public void exit(long userId, long groupId) {
        Group group = findById(groupId);
        User user = userService.findById(userId);
        group.getUsers().remove(user);
        user.getGroups().remove(group);
        userService.save(user);
        repository.save(group);
    }

    @Override
    public long count(long userId) {
        return repository.countAllByUsersIdIn(userId);
    }
}
