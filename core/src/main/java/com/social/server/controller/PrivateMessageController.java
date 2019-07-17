package com.social.server.controller;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.http.Response;
import com.social.server.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PrivateMessageController {

    private final PrivateMessageService privateMessageService;

    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @MessageMapping("/send")
    @SendTo("/dialog/message")
    public Response saveSockMessage(PrivateMessageDto privateMessageDto) {
        return Response.ok(privateMessageService.save(privateMessageDto));
    }
}
