package com.social.server.controller;

import com.social.server.dto.RegistrationDto;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody @Valid RegistrationDto registrationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.ok(result.getAllErrors());
        }
        if (userService.isEmailExist(registrationDto.getEmail())) {
            return ResponseEntity.ok("Email already registered");
        }
        return ResponseEntity.ok(userService.registerUser(registrationDto));
    }
}
