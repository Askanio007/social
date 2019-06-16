package com.social.server.controller;

import com.social.server.dto.GroupDto;
import com.social.server.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{rootUserId}/groups")
    public ResponseEntity findAll(@PathVariable long rootUserId) {
        return ResponseEntity.ok(groupService.findBy(rootUserId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity findOne(@PathVariable long groupId) {
        return ResponseEntity.ok(groupService.find(groupId));
    }

    @PostMapping("/{rootUserId}/groups/create")
    public ResponseEntity create(@PathVariable long rootUserId, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.create(rootUserId, groupDto));
    }

    @GetMapping("/{rootUserId}/groups/existence/{groupId}")
    public ResponseEntity isUserJoinGroup(@PathVariable long rootUserId, @PathVariable long groupId) {
        return ResponseEntity.ok(groupService.isUserHasGroup(rootUserId, groupId));
    }

    @PostMapping("/{rootUserId}/groups/{groupId}/join")
    public ResponseEntity join(@PathVariable long rootUserId, @PathVariable long groupId) {
        groupService.join(rootUserId, groupId);
        return ResponseEntity.ok("ok");
    }
}
