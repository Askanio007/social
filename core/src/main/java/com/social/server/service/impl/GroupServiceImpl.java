package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.*;
import com.social.server.http.model.GroupModel;
import com.social.server.service.EventService;
import com.social.server.service.GroupService;
import com.social.server.service.ImageService;
import com.social.server.service.UserService;
import com.social.server.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class GroupServiceImpl extends CommonServiceImpl<Group, Long, GroupRepository> implements GroupService {

    private final UserService userService;
    private final EventService eventService;
    private final ImageService imageService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserService userService,
                            EventService eventService,
                            ImageService imageService) {
        super(groupRepository);
        this.userService = userService;
        this.eventService = eventService;
        this.imageService = imageService;
    }

    @Override
    public List<GroupDto> findBy(long userId) {
        return GroupDto.of(repository.findByUsersIdIn(userId));
    }

    @Override
    public GroupDto find(long groupId) {
        return GroupDto.of(getById(groupId));
    }

    @Override
    public long countParticipant(long groupId) {
        return repository.countParticipant(groupId);
    }

    @Override
    public GroupDto create(long adminId, GroupModel groupModel) {
        User admin = userService.getById(adminId);
        Group group = new Group();
        group.setName(groupModel.getName());
        group.setDescription(groupModel.getDescription());
        group.setAdmin(admin);
        group.getUsers().add(admin);
        group = repository.save(group);
        eventService.createEvent(admin.getId(), group.getId(), group.getName(), EventType.ENTER_GROUP);
        return GroupDto.of(group);
    }

    @Override
    public boolean isUserHasGroup(long userId, long groupId) {
        return repository.existsByIdAndUsersIdIn(groupId, userId);
    }

    @Override
    public void join(long userId, long groupId) {
        Group group = getById(groupId);
        User user = userService.getById(userId);
        group.getUsers().add(user);
        user.getGroups().add(group);
        repository.save(group);
        userService.save(user);
        eventService.createEvent(userId, group.getId(), group.getName(), EventType.ENTER_GROUP);
    }

    @Override
    public List<GroupDto> search(String name) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return GroupDto.of(repository.searchByName(name.toLowerCase()));
    }

    @Override
    public String savePhoto(long groupId, MultipartFile file, boolean isMini) {

        Group group = getById(groupId);

        if (!isMini) {
            imageService.deleteImage(group.getMiniImage(), group.getImage());
        }

        Image image = ImageUtil.saveImage(file, group.getId(), isMini);

        if (image == null) {
            return null;
        }

        if (isMini) {
            group.setMiniImage(image);
            group = repository.save(group);
            return ImageUtil.convertImageTo64encode(group.getMiniImage());
        }

        group.setImage(image);
        group = repository.save(group);
        return ImageUtil.convertImageTo64encode(group.getImage());
    }

    @Override
    public void exit(long userId, long groupId) {
        Group group = getById(groupId);
        User user = userService.getById(userId);
        group.getUsers().remove(user);
        user.getGroups().remove(group);
        userService.save(user);
        repository.save(group);
    }

    @Override
    public long countBy(long userId) {
        return repository.countAllByUsersIdIn(userId);
    }

    @Override
    public GroupRelation getGroupRelationToUser(long groupId, long rootUserId) {
        if (!isUserHasGroup(rootUserId, groupId)) {
            return GroupRelation.NOT_PARTICIPANT;
        }

        Group group = getById(groupId);
        if (group.getAdmin().getId() == rootUserId) {
            return GroupRelation.ADMIN;
        }

        return GroupRelation.PARTICIPANT;
    }

    @Override
    public GroupDto edit(GroupModel groupModel) {
        Group group = getById(groupModel.getId());
        group.setDescription(groupModel.getDescription());
        group.setName(groupModel.getName());
        return GroupDto.of(repository.save(group));
    }
}
