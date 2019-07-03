package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.GroupService;
import com.social.server.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final UserService userService;
    private final GroupService groupService;

    public ImageController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostMapping("/user/{rootUserId}/upload")
    public Response uploadUserImage(@PathVariable long rootUserId, @RequestBody MultipartFile file) {
        return Response.ok(userService.savePhoto(rootUserId, file, false));
    }

    @PostMapping("/user/{rootUserId}/mini/upload")
    public Response uploadUserMiniImage(@PathVariable long rootUserId, @RequestBody MultipartFile file) {
        return Response.ok(userService.savePhoto(rootUserId, file, true));
    }

    @PostMapping("/group/{groupId}/upload")
    public Response uploadGroupImage(@PathVariable long groupId, @RequestBody MultipartFile file) {
        return Response.ok(groupService.savePhoto(groupId, file, false));
    }

    @PostMapping("/group/{groupId}/mini/upload")
    public Response uploadGroupMiniImage(@PathVariable long groupId, @RequestBody MultipartFile file) {
        return Response.ok(groupService.savePhoto(groupId, file, true));
    }
}
