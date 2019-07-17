package com.social.server.controller;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.http.Response;
import com.social.server.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{rootUserId}/messages")
public class PrivateMessageController {

    private final PrivateMessageService privateMessageService;

    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @PostMapping("/{messageId}/read")
    public Response readMessage(@PathVariable long messageId, @PathVariable long rootUserId) {
        privateMessageService.readMessage(rootUserId, messageId);
        return Response.ok();
    }


    @MessageMapping("/send/{dialogId}")
    @SendTo("/dialog/message/{dialogId}")
    public Response saveSockMessage(@DestinationVariable long dialogId, PrivateMessageDto privateMessageDto) {
        return Response.ok(privateMessageService.save(privateMessageDto));
    }
}
