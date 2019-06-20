package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.http.model.UserDetailsModel;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/{rootUserId}/profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Response saveProfile(@RequestBody @Valid UserDetailsModel userDetailsModel, BindingResult result) {
        return result.hasErrors() ? Response.error(result.getAllErrors()) : Response.ok(userService.updateProfile(userDetailsModel));
    }
}
