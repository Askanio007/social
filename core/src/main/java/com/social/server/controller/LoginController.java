package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email,
                                @RequestParam String password) {
        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) {
            return ResponseEntity.ok("User not found");
        }
        if (!userDto.getPassword().equals(password)) {
            return ResponseEntity.ok("Password incorrect");
        }
        return ResponseEntity.ok("Password incorrect");
    }
}
