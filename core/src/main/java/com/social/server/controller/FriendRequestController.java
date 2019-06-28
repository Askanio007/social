package com.social.server.controller;

import com.social.server.dto.FriendshipRequestDto;
import com.social.server.http.Response;
import com.social.server.service.FriendshipRequestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response findRequests(@PathVariable long rootUserId) {
        return Response.ok(friendshipRequestService.find(rootUserId));
    }

    @GetMapping("/{userId}")
    public Response isFriendRequests(@PathVariable long rootUserId, @PathVariable long userId) {
        return Response.ok(friendshipRequestService.isFriendRequest(rootUserId, userId));
    }

    @PostMapping("/add")
    public Response addFriendRequest(@RequestBody FriendshipRequestDto friendshipRequestDto) {
        friendshipRequestService.create(friendshipRequestDto);
        return Response.ok();
    }

    @PostMapping("/accept/{friendshipRequestId}")
    public Response acceptRequest(@PathVariable long friendshipRequestId) {
        friendshipRequestService.accept(friendshipRequestId);
        return Response.ok();
    }

    @PostMapping("/decline/{friendshipRequestId}")
    public Response declineRequest(@PathVariable long friendshipRequestId) {
        friendshipRequestService.decline(friendshipRequestId);
        return Response.ok();
    }
}
