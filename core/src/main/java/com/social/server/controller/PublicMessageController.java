package com.social.server.controller;

import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.Response;
import com.social.server.http.model.PublicMessageModel;
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

    @GetMapping("/{recipientId}/{type}")
    public Response findPublicMessage(@PathVariable Long recipientId, @PathVariable PublicMessageRecipientType type) {
        return Response.ok(publicMessageService.findByRecipientId(recipientId, type));
    }

    @PostMapping("/save")
    public Response saveMessage(@RequestBody PublicMessageModel publicMessageModel) {
        return Response.ok(publicMessageService.create(publicMessageModel));
    }
}
