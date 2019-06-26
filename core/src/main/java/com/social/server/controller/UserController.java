package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.FriendshipRequestService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final FriendshipRequestService friendshipRequestService;

    @Autowired
    public UserController(UserService userService, FriendshipRequestService friendshipRequestService) {
        this.userService = userService;
        this.friendshipRequestService = friendshipRequestService;
    }


    @GetMapping("/user/{userId}")
    public Response getUser(@PathVariable Long userId) {
        return Response.ok(userService.findDtoById(userId));
    }

    @GetMapping("/{rootUserId}/friends/relation/{userId}")
    public Response getRelation(@PathVariable long rootUserId, @PathVariable long userId) {
        return Response.ok(friendshipRequestService.getRelation(rootUserId, userId));
    }
}
