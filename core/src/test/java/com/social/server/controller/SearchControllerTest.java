package com.social.server.controller;

import com.social.server.dto.GroupDto;
import com.social.server.dto.UserDto;
import com.social.server.http.Response;
import com.social.server.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest extends CommonControllerTest {
    private final String API = "/api/v1/";

    @MockBean
    private GroupService groupService;;

    @Test
    public void successUserSearch() throws Exception {
        String testSearch = "TEST";
        List<UserDto> list = Arrays.asList(new UserDto(), new UserDto());
        Mockito.when(userService.search(ID, testSearch)).thenReturn(list);
        checkGetRequest(API + "user/search?userName=" + testSearch + "&rootUserId=" + ID, Response.ok(list));
    }

    @Test
    public void successGroupSearch() throws Exception {
        String testSearch = "TEST";
        List<GroupDto> list = Arrays.asList(new GroupDto(), new GroupDto());
        Mockito.when(groupService.search(testSearch)).thenReturn(list);
        checkGetRequest(API + "group/search?groupName=" + testSearch, Response.ok(list));
    }

    @Test
    public void failedAccessToSearchGroup() throws Exception {
        failedAccessToEndpoint(API + "group/search");
    }

    @Test
    public void failedAccessToSearchUser() throws Exception {
        failedAccessToEndpoint(API + "user/search");
    }
}
