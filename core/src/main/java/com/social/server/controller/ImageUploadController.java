package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.GroupService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageUploadController {

    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public ImageUploadController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @PutMapping("/users/{rootUserId}")
    public Response uploadUserImage(@PathVariable long rootUserId, @RequestBody MultipartFile file) {
        return Response.ok(userService.savePhoto(rootUserId, file, false));
    }

    @PutMapping("/users/{rootUserId}/mini")
    public Response uploadUserMiniImage(@PathVariable long rootUserId, @RequestBody MultipartFile file) {
        return Response.ok(userService.savePhoto(rootUserId, file, true));
    }

    @PutMapping("/groups/{groupId}")
    public Response uploadGroupImage(@PathVariable long groupId, @RequestBody MultipartFile file) {
        return Response.ok(groupService.savePhoto(groupId, file, false));
    }

    @PutMapping("/groups/{groupId}/mini")
    public Response uploadGroupMiniImage(@PathVariable long groupId, @RequestBody MultipartFile file) {
        return Response.ok(groupService.savePhoto(groupId, file, true));
    }
}
