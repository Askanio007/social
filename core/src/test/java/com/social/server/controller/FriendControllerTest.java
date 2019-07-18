package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.http.Response;
import com.social.server.service.FriendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FriendController.class)
public class FriendControllerTest extends CommonControllerTest {
    private final String api = "/api/v1/" + ID + "/friends";

    @MockBean
    private FriendService friendService;

    @Test
    public void successFindFriends() throws Exception {
        UserDto u = new UserDto();
        u.setEmail("test");
        u.setName("desr");
        when(friendService.find(ID, 1)).thenReturn(new PageImpl<>(Arrays.asList(u)));
        checkGetRequest(api + "?page=1", Response.ok(new PageImpl<>(Arrays.asList(u))));
    }

    @Test
    public void successFindCountFriends() throws Exception {
        when(friendService.count(ID)).thenReturn(10L);
        checkGetRequest(api + "/count", Response.ok(10L));
    }

    @Test
    public void successCheckExistFriend() throws Exception {
        when(friendService.isFriends(ID, ID2)).thenReturn(true);
        checkGetRequest(api + "/existence/" + ID2, Response.ok(true));
    }

    @Test
    public void successRemoveFriend() throws Exception {
        checkDeleteRequest(api + "/" + ID2, null, Response.ok());
        verify(friendService, times(1)).remove(eq(ID), eq(ID2));
    }

    @Test
    public void failedAccessToRemoveFriend() throws Exception {
        failedAccessToEndpoint(api + "/remove/" + ID2);
    }

    @Test
    public void failedAccessToCheckExistFriend() throws Exception {
        failedAccessToEndpoint(api + "/existence/" + ID2);
    }

    @Test
    public void failedAccessToFindFriendCount() throws Exception {
        failedAccessToEndpoint(api + "/count");
    }

    @Test
    public void failedAccessToFindFixFriend() throws Exception {
        failedAccessToEndpoint(api + "/3");
    }

    @Test
    public void failedAccessToFindFriend() throws Exception {
        failedAccessToEndpoint(api);
    }


}
