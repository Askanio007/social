package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{rootUserId}/friends")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    public Response find(@PathVariable long rootUserId) {
        return Response.ok(friendService.find(rootUserId));
    }

    @GetMapping("/existence/{userId}")
    public Response isFriends(@PathVariable long rootUserId, @PathVariable long userId) {
        return Response.ok(friendService.isFriends(rootUserId, userId));
    }
}
