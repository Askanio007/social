package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageUploadController.class)
public class ImageUploadControllerTest extends CommonControllerTest {
    private final static String API = "/api/v1/images";

    @MockBean
    private GroupService groupService;

    @Test
    public void successUploadFullUserImage() throws Exception {
        Mockito.when(userService.savePhoto( eq(ID), isNull(), eq(false))).thenReturn(ID);
        checkPutRequest(API + "/users/" + ID, null, Response.ok(ID));
    }

    @Test
    public void successUploadMiniUserImage() throws Exception {
        Mockito.when(userService.savePhoto( eq(ID), isNull(), eq(true))).thenReturn(ID);
        checkPutRequest(API + "/users/" + ID + "/mini", null, Response.ok(ID));
    }

    @Test
    public void successUploadFullGroupImage() throws Exception {
        Mockito.when(groupService.savePhoto( eq(ID), isNull(), eq(false))).thenReturn(ID);
        checkPutRequest(API + "/groups/" + ID, null, Response.ok(ID));
    }

    @Test
    public void successUploadMiniGroupImage() throws Exception {
        Mockito.when(groupService.savePhoto( eq(ID), isNull(), eq(true))).thenReturn(ID);
        checkPutRequest(API + "/groups/" + ID + "/mini", null, Response.ok(ID));
    }

    @Test
    public void failedAccessToUploadFullUserImage() throws Exception {
        failedAccessToEndpoint(API + "/users/" + ID);
    }

    @Test
    public void failedAccessToUploadMiniUserImage() throws Exception {
        failedAccessToEndpoint(API + "/users/" + ID + "/mini");
    }

    @Test
    public void failedAccessToUploadFullGroupImage() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID);
    }

    @Test
    public void failedAccessToUploadMiniGroupImage() throws Exception {
        failedAccessToEndpoint(API + "/groups/" + ID + "/mini");
    }
}
