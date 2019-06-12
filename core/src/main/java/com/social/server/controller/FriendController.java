package com.social.server.controller;

import com.social.server.service.FriendService;
import com.social.server.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FriendController {

    private final FriendService friendService;
    private final FriendshipRequestService friendshipRequestService;

    @Autowired
    public FriendController(FriendService friendService,
                            FriendshipRequestService friendshipRequestService) {
        this.friendService = friendService;
        this.friendshipRequestService = friendshipRequestService;
    }

    @RequestMapping("{userId}/friends/request")
    public ResponseEntity findRequests(@PathVariable long userId) {
        return ResponseEntity.ok(friendshipRequestService.find(userId));
    }

    @RequestMapping("{userId}/friends")
    public ResponseEntity find(@PathVariable long userId) {
        return ResponseEntity.ok(friendService.find(userId));
    }
}
