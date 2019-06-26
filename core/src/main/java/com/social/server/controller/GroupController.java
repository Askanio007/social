package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.http.model.GroupModel;
import com.social.server.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{rootUserId}/groups")
    public Response findAll(@PathVariable long rootUserId) {
        return Response.ok(groupService.findBy(rootUserId));
    }

    @GetMapping("/{rootUserId}/groups/count")
    public Response findCount(@PathVariable long rootUserId) {
        return Response.ok(groupService.count(rootUserId));
    }

    @GetMapping("/group/{groupId}")
    public Response findOne(@PathVariable long groupId) {
        return Response.ok(groupService.find(groupId));
    }

    @GetMapping("/group/{groupId}/count")
    public Response countParticipant(@PathVariable long groupId) {
        return Response.ok(groupService.countParticipant(groupId));
    }

    @PostMapping("/{rootUserId}/groups/create")
    public Response create(@PathVariable long rootUserId, @RequestBody @Valid GroupModel groupModel, BindingResult result) {
        return result.hasErrors() ? Response.error(result.getAllErrors()) : Response.ok(groupService.create(rootUserId, groupModel));
    }

    @GetMapping("/{rootUserId}/groups/existence/{groupId}")
    public Response isUserJoinGroup(@PathVariable long rootUserId, @PathVariable long groupId) {
        return Response.ok(groupService.isUserHasGroup(rootUserId, groupId));
    }

    @PostMapping("/{rootUserId}/groups/{groupId}/join")
    public Response join(@PathVariable long rootUserId, @PathVariable long groupId) {
        groupService.join(rootUserId, groupId);
        return Response.ok();
    }

    @PostMapping("/{rootUserId}/groups/{groupId}/exit")
    public Response exit(@PathVariable long rootUserId, @PathVariable long groupId) {
        groupService.exit(rootUserId, groupId);
        return Response.ok();
    }
}
