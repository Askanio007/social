package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.findById(userId);
    }
}
