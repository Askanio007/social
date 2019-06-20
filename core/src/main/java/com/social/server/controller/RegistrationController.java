package com.social.server.controller;

import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.RegistrationModel;
import com.social.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public Response registration(@RequestBody @Valid RegistrationModel registrationModel, BindingResult result) {
        if (result.hasErrors()) {
            return Response.error(result.getAllErrors());
        }
        if (userService.isEmailExist(registrationModel.getEmail())) {
            return Response.error(ErrorCode.EMAIL_IS_USED);
        }
        return Response.ok(userService.registerUser(registrationModel));
    }
}
