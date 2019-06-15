package com.social.server.controller;

import com.social.server.dto.FriendshipRequestDto;
import com.social.server.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{rootUserId}/friends/request")
public class FriendRequestController {

    private final FriendshipRequestService friendshipRequestService;

    @Autowired
    public FriendRequestController(FriendshipRequestService friendshipRequestService) {
        this.friendshipRequestService = friendshipRequestService;
    }

    @GetMapping
    public ResponseEntity findRequests(@PathVariable long rootUserId) {
        return ResponseEntity.ok(friendshipRequestService.find(rootUserId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity isFriendRequests(@PathVariable long rootUserId, @PathVariable long userId) {
        return ResponseEntity.ok(friendshipRequestService.isFriendRequest(rootUserId, userId));
    }

    @PostMapping("/add")
    public ResponseEntity addFriendRequest(@RequestBody FriendshipRequestDto friendshipRequestDto) {
        friendshipRequestService.add(friendshipRequestDto);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/accept")
    public ResponseEntity acceptRequest(@RequestBody long friendshipRequestId) {
        friendshipRequestService.accept(friendshipRequestId);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/decline")
    public ResponseEntity declineRequest(@RequestBody long friendshipRequestId) {
        friendshipRequestService.decline(friendshipRequestId);
        return ResponseEntity.ok("ok");
    }
}
