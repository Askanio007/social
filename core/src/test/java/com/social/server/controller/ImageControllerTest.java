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
@WebMvcTest(ImageController.class)
public class ImageControllerTest extends CommonControllerTest {
    private final static String API = "/api/v1/image";

    @MockBean
    private GroupService groupService;

    @Test
    public void successUploadFullUserImage() throws Exception {
        Mockito.when(userService.savePhoto( eq(ID), isNull(), eq(false))).thenReturn("TEST");
        checkPostRequest(API + "/user/" + ID + "/upload", null, Response.ok("TEST"));
    }

    @Test
    public void successUploadMiniUserImage() throws Exception {
        Mockito.when(userService.savePhoto( eq(ID), isNull(), eq(true))).thenReturn("TEST");
        checkPostRequest(API + "/user/" + ID + "/mini/upload", null, Response.ok("TEST"));
    }

    @Test
    public void successUploadFullGroupImage() throws Exception {
        Mockito.when(groupService.savePhoto( eq(ID), isNull(), eq(false))).thenReturn("TEST");
        checkPostRequest(API + "/group/" + ID + "/upload", null, Response.ok("TEST"));
    }

    @Test
    public void successUploadMiniGroupImage() throws Exception {
        Mockito.when(groupService.savePhoto( eq(ID), isNull(), eq(true))).thenReturn("TEST");
        checkPostRequest(API + "/group/" + ID + "/mini/upload", null, Response.ok("TEST"));
    }

    @Test
    public void failedAccessToUploadFullUserImage() throws Exception {
        failedAccessToEndpoint(API + "/user/" + ID + "/upload");
    }

    @Test
    public void failedAccessToUploadMiniUserImage() throws Exception {
        failedAccessToEndpoint(API + "/user/" + ID + "/mini/upload");
    }

    @Test
    public void failedAccessToUploadFullGroupImage() throws Exception {
        failedAccessToEndpoint(API + "/group/" + ID + "/upload");
    }

    @Test
    public void failedAccessToUploadMiniGroupImage() throws Exception {
        failedAccessToEndpoint(API + "/group/" + ID + "/mini/upload");
    }
}
