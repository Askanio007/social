package com.social.server.service;

import com.social.server.dao.GroupRepository;
import com.social.server.dto.GroupDto;
import com.social.server.entity.EventType;
import com.social.server.entity.Group;
import com.social.server.entity.GroupRelation;
import com.social.server.entity.User;
import com.social.server.http.model.GroupModel;
import com.social.server.service.impl.GroupServiceImpl;
import com.social.server.service.impl.PhotoSaverImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupServiceTest {
    private final long USER_ID = 1;
    private final String USER_NAME = "MAIN";
    private final long GROUP_ID = 1;
    private final String GROUP_NAME = "GROUP_NAME";
    private final String GROUP_DESCR = "GROUP_DESCR";

    private final UserService userService = Mockito.mock(UserService.class);
    private final EventService eventService = Mockito.mock(EventService.class);
    private final GroupRepository groupRepository = Mockito.mock(GroupRepository.class);
    private final PhotoSaverImpl photoSaver = Mockito.mock(PhotoSaverImpl.class);
    private GroupService groupService = new GroupServiceImpl(groupRepository, userService, eventService, photoSaver);

    @Before
    public void setUp() {
        when(groupRepository.save(any(Group.class))).then(invocation -> invocation.getArgument(0));
    }

    private void prepareData(boolean userHaveGroup) {
        User user = new User();
        user.setName(USER_NAME);

        Group group = new Group();
        group.setName(GROUP_NAME);
        group.setDescription(GROUP_DESCR);

        group.setAdmin(user);

        if (userHaveGroup) {
            user.getGroups().add(group);
            group.getUsers().add(user);
        }

        when(userService.getById(USER_ID)).thenReturn(user);
        when(groupRepository.findById(GROUP_ID)).thenReturn(Optional.of(group));
    }

    @Test
    public void successCreateGroup() {
        User admin = new User();
        admin.setName(USER_NAME);
        when(userService.getById(USER_ID)).thenReturn(admin);

        GroupModel groupModel = new GroupModel();
        groupModel.setDescription(GROUP_DESCR);
        groupModel.setName(GROUP_NAME);

        GroupDto dto = groupService.create(USER_ID, groupModel);

        verify(eventService).createEvent(eq(0L), eq(0L), eq(GROUP_NAME), eq(EventType.ENTER_GROUP));
        Assert.assertEquals(dto.getName(), GROUP_NAME);
        Assert.assertEquals(dto.getDescription(), GROUP_DESCR);
        Assert.assertEquals(dto.getAdminName(), USER_NAME);
        Assert.assertEquals(dto.getParticipant().size(), 1);
    }

    @Test
    public void successJoinGroup() {
        prepareData(false);

        groupService.join(USER_ID, GROUP_ID);
        verify(eventService).createEvent(eq(USER_ID), eq(0L), eq(GROUP_NAME), eq(EventType.ENTER_GROUP));
        verify(userService).getById(eq(USER_ID));
        verify(groupRepository).findById(eq(GROUP_ID));
    }

    @Test
    public void successExitGroup() {
        prepareData(true);

        groupService.exit(USER_ID, GROUP_ID);
        verify(userService).getById(eq(USER_ID));
        verify(groupRepository).findById(eq(GROUP_ID));
    }

    @Test
    public void checkGroupRelationParticipant() {
        prepareData(false);
        when(groupRepository.existsByIdAndUsersIdIn(GROUP_ID, USER_ID)).thenReturn(true);
        GroupRelation relation = groupService.getGroupRelationToUser(GROUP_ID, USER_ID);
        Assert.assertEquals(relation, GroupRelation.PARTICIPANT);
    }

    @Test(expected = EntityNotFoundException.class)
    public void failedCheckGroupRelationAdmin() {
        groupService.getGroupRelationToUser(0L, 0L);
    }

    @Test
    public void checkGroupRelationNotParticipant() {
        when(groupRepository.existsByIdAndUsersIdIn(GROUP_ID, USER_ID)).thenReturn(false);
        GroupRelation relation = groupService.getGroupRelationToUser(GROUP_ID, USER_ID);
        Assert.assertEquals(relation, GroupRelation.NOT_PARTICIPANT);
    }

    @Test
    public void successEditGroup() {
        final String GROUP_NAME_EDIT = "GROUP_NAME_EDIT";
        final String GROUP_DESCR_EDIT = "GROUP_DESCR_EDIT";
        prepareData(false);
        GroupModel groupModel = new GroupModel();
        groupModel.setDescription(GROUP_DESCR_EDIT);
        groupModel.setName(GROUP_NAME_EDIT);
        groupModel.setId(GROUP_ID);

        GroupDto dto = groupService.edit(groupModel);

        Assert.assertEquals(dto.getName(), GROUP_NAME_EDIT);
        Assert.assertEquals(dto.getDescription(), GROUP_DESCR_EDIT);
    }
}
