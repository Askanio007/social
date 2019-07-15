package com.social.server.controller;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.http.Response;
import com.social.server.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dialogs/message")
public class PrivateMessageController {

    private final PrivateMessageService privateMessageService;

    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @PutMapping
    public Response saveMessage(@RequestBody PrivateMessageDto privateMessageDto) {
        return Response.ok(privateMessageService.save(privateMessageDto));
    }

}
