package com.social.server.controller;

import com.social.server.service.GroupService;
import com.social.server.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity uploadUserImage(@PathVariable long rootUserId,
                                          @RequestBody MultipartFile file) {
        userService.savePhoto(rootUserId, file);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/group/{groupId}/upload")
    public ResponseEntity uploadGroupImage(@PathVariable long groupId,
                                           @RequestBody MultipartFile file) {
        groupService.savePhoto(groupId, file);
        return ResponseEntity.ok("ok");
    }
}
