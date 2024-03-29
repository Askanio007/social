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
    public Response findAll(@PathVariable long rootUserId, @RequestParam int page) {
        return Response.ok(groupService.findBy(rootUserId, page));
    }

    @GetMapping("/{rootUserId}/groups/count")
    public Response findCount(@PathVariable long rootUserId) {
        return Response.ok(groupService.countBy(rootUserId));
    }

    @GetMapping("/groups/{groupId}")
    public Response findOne(@PathVariable long groupId) {
        return Response.ok(groupService.find(groupId));
    }

    @GetMapping("/groups/{groupId}/count")
    public Response countParticipant(@PathVariable long groupId) {
        return Response.ok(groupService.countParticipant(groupId));
    }

    @GetMapping("/groups/{groupId}/relation/{rootUserId}")
    public Response relation(@PathVariable long groupId, @PathVariable long rootUserId) {
        return Response.ok(groupService.getGroupRelationToUser(groupId, rootUserId));
    }

    @PutMapping("/{rootUserId}/groups")
    public Response create(@PathVariable long rootUserId, @RequestBody @Valid GroupModel groupModel, BindingResult result) {
        return result.hasErrors() ? Response.error(result.getAllErrors()) : Response.ok(groupService.create(rootUserId, groupModel));
    }

    @PostMapping("/groups/{groupId}/edit")
    public Response create(@RequestBody @Valid GroupModel groupModel, BindingResult result) {
        return result.hasErrors() ? Response.error(result.getAllErrors()) : Response.ok(groupService.edit(groupModel));
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
