package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.GroupService;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class SearchController {

    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public SearchController(UserService userService,
                            GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/user/search")
    public Response searchUser(@RequestParam String userName,
                               @RequestParam long rootUserId) {
        return Response.ok(userService.search(rootUserId, userName));
    }

    @GetMapping("/group/search")
    public Response searchGroup(@RequestParam String groupName) {
        return Response.ok(groupService.search(groupName));
    }
}
