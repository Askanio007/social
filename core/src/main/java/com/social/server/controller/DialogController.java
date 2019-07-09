package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.DialogService;
import com.social.server.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/{rootUserId}/dialog")
public class DialogController {

    private final DialogService dialogService;
    private final PrivateMessageService privateMessageService;

    @Autowired
    public DialogController(DialogService dialogService,
                            PrivateMessageService privateMessageService) {
        this.dialogService = dialogService;
        this.privateMessageService = privateMessageService;
    }

    @GetMapping
    public Response find(@PathVariable long rootUserId, @RequestParam int page) {
        return Response.ok(dialogService.findBy(rootUserId, page));
    }

    @GetMapping("/{dialogId}/message")
    public Response list(@PathVariable long dialogId) {
        return Response.ok(privateMessageService.findLastBy(dialogId));
    }

    @GetMapping("/messages/unread/count")
    public Response countUnreadMessages(@PathVariable long rootUserId) {
        return Response.ok(dialogService.countUnreadMessage(rootUserId));
    }

    @GetMapping("/{friendId}")
    public Response getDialog(@PathVariable long rootUserId, @PathVariable long friendId) {
        return Response.ok(dialogService.getDialogBy(Arrays.asList(friendId,rootUserId)));
    }
}
