package com.social.server.controller;

import com.social.server.dto.PublicMessageDto;
import com.social.server.http.Response;
import com.social.server.service.PublicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message/public")
public class PublicMessageController {

    private final PublicMessageService publicMessageService;

    @Autowired
    public PublicMessageController(PublicMessageService publicMessageService) {
        this.publicMessageService = publicMessageService;
    }

    @GetMapping("/{recipientId}/{isUser}")
    public Response findPublicMessage(@PathVariable Long recipientId, @PathVariable boolean isUser) {
        return Response.ok(publicMessageService.findByRecipientId(recipientId, isUser));
    }

    @PostMapping("/save")
    public Response saveMessage(@RequestBody PublicMessageDto publicMessageDto) {
        return Response.ok(publicMessageService.create(publicMessageDto));
    }
}
