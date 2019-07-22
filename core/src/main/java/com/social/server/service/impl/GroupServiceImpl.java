package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.EventType;
import com.social.server.entity.Group;
import com.social.server.entity.GroupRelation;
import com.social.server.entity.User;
import com.social.server.http.model.GroupModel;
import com.social.server.service.EventService;
import com.social.server.service.GroupService;
import com.social.server.service.PhotoSaver;
import com.social.server.service.UserService;
import com.social.server.service.transactional.ReadTransactional;
import com.social.server.service.transactional.WriteTransactional;
import com.social.server.validator.GroupModelValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GroupServiceImpl extends CommonServiceImpl<Group, Long, GroupRepository> implements GroupService {

    private final UserService userService;
    private final EventService eventService;
    private final PhotoSaver<Group> photoSaver;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            UserService userService,
                            EventService eventService,
                            PhotoSaver<Group> photoSaver) {
        super(groupRepository);
        this.userService = userService;
        this.eventService = eventService;
        this.photoSaver = photoSaver;
    }

    @Override
    public Page<GroupDto> findBy(long userId, int page) {
        return repository.findByUsersIdIn(userId, PageRequest.of(page, 10)).map(GroupDto::of);
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
    @WriteTransactional
    public GroupDto create(long adminId, GroupModel groupModel) {
        validateEmptyEntityId(adminId);
        log.debug("Creating group start. Admin={}", adminId);
        User admin = userService.getById(adminId);
        Group group = new Group();
        group.setName(groupModel.getName());
        group.setDescription(groupModel.getDescription());
        group.setAdmin(admin);
        group.getUsers().add(admin);
        log.debug("Save new group");
        group = repository.save(group);
        eventService.createEvent(admin.getId(), group.getId(), group.getName(), EventType.ENTER_GROUP);
        log.debug("Creating group completed successfully");
        return GroupDto.of(group);
    }

    @Override
    public boolean isUserHasGroup(long userId, long groupId) {
        validateEmptyEntityId(userId, groupId);
        return repository.existsByIdAndUsersIdIn(groupId, userId);
    }

    @Override
    @WriteTransactional
    public void join(long userId, long groupId) {
        log.debug("Join user to group; userId={}, groupId={}", userId, groupId);
        validateEmptyEntityId(userId, groupId);
        Group group = getById(groupId);
        User user = userService.getById(userId);
        group.getUsers().add(user);
        eventService.createEvent(userId, group.getId(), group.getName(), EventType.ENTER_GROUP);
        log.debug("Join user to group completed successfully");
    }

    @Override
    public List<GroupDto> search(String name) {
        log.debug("Search group by name={}", name);
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        return GroupDto.of(repository.searchByName(name.toLowerCase()));
    }

    @Override
    @WriteTransactional
    public long savePhoto(long groupId, MultipartFile file, boolean isMini) {
        validateEmptyEntityId(groupId);
        return photoSaver.savePhoto(getById(groupId), file, isMini);
    }

    @Override
    @WriteTransactional
    public void exit(long userId, long groupId) {
        log.debug("Exit from group; userId={}, groupId={}", userId, groupId);
        validateEmptyEntityId(userId, groupId);
        Group group = getById(groupId);
        User user = userService.getById(userId);
        group.getUsers().remove(user);
        log.debug("Exit from group completed successfully");
    }

    @Override
    public long countBy(long userId) {
        return repository.countAllByUsersIdIn(userId);
    }

    @Override
    @ReadTransactional
    public GroupRelation getGroupRelationToUser(long groupId, long rootUserId) {
        validateEmptyEntityId(rootUserId, groupId);
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
    @WriteTransactional
    public GroupDto edit(GroupModel groupModel) {
        GroupModelValidator.validate(groupModel);
        log.debug("Edit group; groupId={}", groupModel.getId());
        Group group = getById(groupModel.getId());
        group.setDescription(groupModel.getDescription());
        group.setName(groupModel.getName());
        return GroupDto.of(group);
    }
}
