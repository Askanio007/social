package com.social.server.controller;

import com.social.server.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity find(@PathVariable long rootUserId) {
        return ResponseEntity.ok(friendService.find(rootUserId));
    }

    @GetMapping("/existence/{userId}")
    public ResponseEntity isFriends(@PathVariable long rootUserId, @PathVariable long userId) {
        return ResponseEntity.ok(friendService.isFriends(rootUserId, userId));
    }
}
