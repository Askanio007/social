package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Response login(@RequestParam String email, @RequestParam String password) {
        UserDto userDto = userService.findBy(email, password);

        if (userDto != null) {
            return Response.authorized(userDto);
        }

        return Response.error(ErrorCode.LOGIN_CREDENTIALS_ERROR);
    }
}
