package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.RegistrationModel;
import com.social.server.service.RestorePasswordService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/forgot-password")
public class ForgotPasswordController {

    private final RestorePasswordService restorePasswordService;

    public ForgotPasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @PostMapping
    public Response sendRestoreLink(@RequestBody RegistrationModel model, HttpServletRequest request) {
        restorePasswordService.sendRestoreLinkTo(model.getEmail(), request.getRemoteHost());
        return Response.ok();
    }

    @GetMapping
    public Response getUserByRestoreToken(@RequestParam String token) {
        UserDto user = restorePasswordService.checkRestorePasswordToken(token);
        return user == null ? Response.error(ErrorCode.RESTORE_TOKEN_NOT_CORRECT) : Response.ok(user);
    }

}
