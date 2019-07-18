package com.social.server.controller;

import com.social.server.dto.GroupDto;
import com.social.server.entity.GroupRelation;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.GroupModel;
import com.social.server.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest extends CommonControllerTest {
    private final static String API = "/api/v1";

    @MockBean
    private GroupService groupService;

    private List<GroupDto> getListGroups() {
        ArrayList<GroupDto> dd = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GroupDto group = new GroupDto();
            group.setAdminName("Test admin name ");
            group.setDescription("test descr");
            group.setName("test name");
            dd.add(group);
        }
        return dd;
    }

    private GroupDto getGroupDto() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("test name");
        groupDto.setDescription("test descr");
        return groupDto;
    }

    @Test
    public void successFindUserGroup() throws Exception {
        when(groupService.findBy(ID, 1)).thenReturn(new PageImpl<>(getListGroups()));
        checkGetRequest(API + "/" + ID + "/groups"  + "?page=1", Response.ok(new PageImpl<>(getListGroups())));
    }

    @Test
    public void successFindCountUserGroup() throws Exception {
        when(groupService.countBy(ID)).thenReturn(45L);
        checkGetRequest(API + "/" + ID + "/groups/count", Response.ok(45L));
    }

    @Test
    public void successFindGroupById() throws Exception {
        GroupDto groupDto = getGroupDto();
        when(groupService.find(ID2)).thenReturn(groupDto);
        checkGetRequest(API + "/groups/" + ID2, Response.ok(groupDto));
    }

    @Test
    public void successFindCountParticipantGroup() throws Exception {
        when(groupService.countParticipant(ID2)).thenReturn(5L);
        checkGetRequest(API + "/groups/" + ID2 + "/count", Response.ok(5L));
    }

    @Test
    public void successGetGroupRelationToUser() throws Exception {
        when(groupService.getGroupRelationToUser(ID2, ID)).thenReturn(GroupRelation.ADMIN);
        checkGetRequest(API + "/groups/" + ID2 + "/relation/" + ID, Response.ok(GroupRelation.ADMIN));
    }

    @Test
    public void successCreateGroup() throws Exception {
        GroupModel model = new GroupModel();
        model.setName("Test");
        model.setDescription("Test");
        GroupDto groupDto = getGroupDto();
        when(groupService.create(ID, model)).thenReturn(groupDto);
        checkPutRequest(API + "/" + ID + "/groups", model, Response.ok(groupDto));
    }

    @Test
    public void failedCreateGroup() throws Exception {
        GroupModel model = new GroupModel();
        model.setDescription("ssdasdas");
        model.setName("w");
        GroupDto groupDto = getGroupDto();
        when(groupService.create(ID, model)).thenReturn(groupDto);
        checkPutRequest(API + "/" + ID + "/groups", model, Response.error(ErrorCode.GROUP_NAME_INCORRECT));
    }

    @Test
    public void successEditGroup() throws Exception {
        GroupModel model = new GroupModel();
        model.setName("Test");
        model.setDescription("Test");
        GroupDto groupDto = getGroupDto();
        when(groupService.edit(model)).thenReturn(groupDto);
        checkPostRequest(API + "/groups/" + ID + "/edit/", model, Response.ok(groupDto));
    }

    @Test
    public void failedEditGroup() throws Exception {
        GroupModel model = new GroupModel();
        model.setDescription("d");
        model.setName("wsadasd");
        GroupDto groupDto = getGroupDto();
        when(groupService.edit(model)).thenReturn(groupDto);
        checkPostRequest(API + "/groups/" + ID + "/edit/", model, Response.error(ErrorCode.GROUP_DESCRIPTION_INCORRECT));
    }

    @Test
    public void successCJoinToGroup() throws Exception {
        checkPostRequest(API + "/" + ID + "/groups/" + ID2 + "/join", null, Response.ok());
        verify(groupService, times(1)).join(eq(ID), eq(ID2));
    }

    @Test
    public void successExitFromGroup() throws Exception {
        checkPostRequest(API + "/" + ID + "/groups/" + ID2 + "/exit", null, Response.ok());
        verify(groupService, times(1)).exit(eq(ID), eq(ID2));
    }

    @Test
    public void failedAccessToFindUserGroup() throws Exception {
        failedAccessToEndpoint(API + "/" + ID + "/groups");
    }

    @Test
    public void failedAccessToFindCountUserGroup() throws Exception {
        failedAccessToEndpoint(API + "/" + ID + "/groups/count");
    }

    @Test
    public void failedAccessToFindGroupById() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID2);
    }

    @Test
    public void failedAccessToFindCountParticipantGroup() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID2 + "/count");
    }

    @Test
    public void failedAccessToGetGroupRelationToUser() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID2 + "/relation/" + ID);
    }

    @Test
    public void failedAccessToCreateGroup() throws Exception {
        failedAccessToEndpoint(API + "/" + ID + "/groups/create/");
    }

    @Test
    public void failedAccessToEditGroup() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID + "/edit/");
    }

    @Test
    public void failedAccessToJoinToGroup() throws Exception {
        failedAccessToEndpoint(API + "/" + ID + "/groups/" + ID2 + "/join");
    }

    @Test
    public void failedAccessToExitFromGroup() throws Exception {
        failedAccessToEndpoint(API + "/" + ID + "/groups/" + ID2 + "/exit");
    }


}
