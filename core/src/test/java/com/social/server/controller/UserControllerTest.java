package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.entity.UserRelation;
import com.social.server.http.Response;
import com.social.server.service.FriendshipRequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest extends CommonControllerTest {
    private final String API = "/api/v1/";

    @MockBean
    private FriendshipRequestService friendshipRequestService;

    @Test
    public void successGetUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.findBy(ID)).thenReturn(userDto);
        checkGetRequest(API + "user/" + ID, Response.ok(userDto));
    }

    @Test
    public void checkMeUserRelation() throws Exception {
        checkUserRelations(UserRelation.ME);
    }

    @Test
    public void checkFriendUserRelation() throws Exception {
        checkUserRelations(UserRelation.FRIEND);
    }

    @Test
    public void checkFriendRequestUserRelation() throws Exception {
        checkUserRelations(UserRelation.REQUEST_FRIEND);
    }

    @Test
    public void checkNotFriendUserRelation() throws Exception {
        checkUserRelations(UserRelation.NOT_FRIEND);
    }

    private void checkUserRelations(UserRelation expectedRelation) throws Exception {
        when(friendshipRequestService.getRelation(ID, ID2)).thenReturn(expectedRelation);
        checkGetRequest(API + ID + "/friends/relation/" + ID2, Response.ok(expectedRelation));
    }
}
