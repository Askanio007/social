package com.social.server.controller;

import com.social.server.dto.FriendshipRequestDto;
import com.social.server.http.Response;
import com.social.server.http.model.FriendshipRequestModel;
import com.social.server.service.FriendshipRequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FriendRequestController.class)
public class FriendRequestControllerTest extends CommonControllerTest {
    private final static String API = "/api/v1/" + ID + "/friends/request";

    @MockBean
    private FriendshipRequestService friendshipRequestService;

    @Test
    public void successFindRequests() throws Exception {
        FriendshipRequestDto dto = new FriendshipRequestDto();
        dto.setAccept(true);
        dto.setFromUserName("test");
        dto.setToUserName("Test");
        when(friendshipRequestService.find(ID, 1)).thenReturn(new PageImpl<>(Arrays.asList(dto)));
        checkGetRequest(API, Response.ok(new PageImpl<>(Arrays.asList(dto))));
    }

    @Test
    public void successCheckFriendRequest() throws Exception {
        when(friendshipRequestService.isFriendRequest(ID, ID2)).thenReturn(true);
        checkGetRequest(API + "/" + ID2, Response.ok(true));
    }

    @Test
    public void successAddFriendRequest() throws Exception {
        FriendshipRequestModel model = new FriendshipRequestModel();
        model.setToUserId(ID);
        model.setFromUserId(ID2);
        checkPostRequest(API + "/add", model, Response.ok());
        verify(friendshipRequestService, times(1)).create(model);
    }

    @Test
    public void successAcceptRequest() throws Exception {
        checkPostRequest(API + "/accept/" + ID2, null, Response.ok());
        verify(friendshipRequestService, times(1)).accept(ID2);
    }

    @Test
    public void successDeclineRequest() throws Exception {
        checkPostRequest(API + "/decline/" + ID2, null, Response.ok());
        verify(friendshipRequestService, times(1)).decline(ID2);
    }

    @Test
    public void failedAccessToAcceptRequest() throws Exception {
        failedAccessToEndpoint(API + "/accept/" + ID2);
    }

    @Test
    public void failedAccessToDeclineRequest() throws Exception {
        failedAccessToEndpoint(API + "/decline/" + ID2);
    }

    @Test
    public void failedAccessToAddRequest() throws Exception {
        failedAccessToEndpoint(API + "/add");
    }

    @Test
    public void failedAccessToCheckFriendRequest() throws Exception {
        failedAccessToEndpoint(API + "/" + ID2);
    }

    @Test
    public void failedAccessToFindRequest() throws Exception {
        failedAccessToEndpoint(API);
    }
}
