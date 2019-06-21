package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.DialogService;
import com.social.server.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{rootUserId}/dialog")
public class DialogController {

    private final DialogService dialogService;
    private final PrivateMessageService privateMessageService;

    @Autowired
    public DialogController(DialogService dialogService, PrivateMessageService privateMessageService) {
        this.dialogService = dialogService;
        this.privateMessageService = privateMessageService;
    }

    @GetMapping
    public Response find(@PathVariable long rootUserId) {
        return Response.ok(dialogService.findBy(rootUserId));
    }

    @GetMapping("/{dialogId}/message")
    public Response list(@PathVariable long dialogId) {
        return Response.ok(privateMessageService.findLastBy(dialogId));
    }
}
