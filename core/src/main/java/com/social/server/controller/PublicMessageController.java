package com.social.server.controller;

import com.social.server.dto.PublicMessageDto;
import com.social.server.service.PublicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity findPublicMessage(@PathVariable Long recipientId, @PathVariable boolean isUser) {
        return ResponseEntity.ok(publicMessageService.findByRecipientId(recipientId, isUser));
    }

    @PostMapping("/save")
    public ResponseEntity saveMessage(@RequestBody PublicMessageDto publicMessageDto) {
        return ResponseEntity.ok(publicMessageService.create(publicMessageDto));
    }
}
