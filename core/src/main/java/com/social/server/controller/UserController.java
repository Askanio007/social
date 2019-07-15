package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.http.model.RestorePasswordModel;
import com.social.server.service.FriendshipRequestService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @GetMapping("/users/{userId}")
    public Response getUser(@PathVariable Long userId) {
        return Response.ok(userService.findBy(userId));
    }

    @GetMapping("/{rootUserId}/friends/relation/{userId}")
    public Response getRelation(@PathVariable long rootUserId, @PathVariable long userId) {
        return Response.ok(friendshipRequestService.getRelation(rootUserId, userId));
    }

    @PostMapping("/change-password")
    public Response changePassword(@RequestBody @Valid RestorePasswordModel model, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(result.getAllErrors());
        }
        userService.changePassword(model);
        return Response.ok();
    }
}
