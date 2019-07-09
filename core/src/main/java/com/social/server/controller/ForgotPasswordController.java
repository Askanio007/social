package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.RegistrationModel;
import com.social.server.service.RestorePasswordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgot-password")
public class ForgotPasswordController {

    private final RestorePasswordService restorePasswordService;

    public ForgotPasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @PostMapping
    public Response sendRestoreLink(@RequestBody RegistrationModel model) {
        return restorePasswordService.sendRestoreLinkTo(model.getEmail()) ? Response.ok() : Response.error("Error");
    }

    @GetMapping
    public Response getUserByRestoreToken(@RequestParam String token) {
        UserDto user = restorePasswordService.checkRestorePasswordToken(token);
        return user == null ? Response.error(ErrorCode.RESTORE_TOKEN_NOT_CORRECT) : Response.ok(user);
    }

}
