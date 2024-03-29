package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{rootUserId}/friends")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping
    public Response find(@PathVariable long rootUserId, @RequestParam int page) {
        return Response.ok(friendService.find(rootUserId, page));
    }

    @GetMapping("/count")
    public Response findCount(@PathVariable long rootUserId) {
        return Response.ok(friendService.count(rootUserId));
    }

    @GetMapping("/existence/{friendId}")
    public Response isFriends(@PathVariable long rootUserId, @PathVariable long friendId) {
        return Response.ok(friendService.isFriends(rootUserId, friendId));
    }

    @DeleteMapping("/{friendId}")
    public Response removeFromFriends(@PathVariable long rootUserId, @PathVariable long friendId) {
        friendService.remove(rootUserId, friendId);
        return Response.ok();
    }
}
