package com.social.server.controller;

import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.Response;
import com.social.server.http.model.PublicMessageModel;
import com.social.server.service.PublicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/messages/public")
public class PublicMessageController {

    private final PublicMessageService publicMessageService;

    @Autowired
    public PublicMessageController(PublicMessageService publicMessageService) {
        this.publicMessageService = publicMessageService;
    }

    @GetMapping("/{recipientId}/{type}")
    public Response findPublicMessage(@PathVariable Long recipientId,
                                      @PathVariable PublicMessageRecipientType type,
                                      @RequestParam int page) {
        return Response.ok(publicMessageService.findByRecipientId(recipientId, type, page));
    }

    @PutMapping
    public Response saveMessage(@RequestBody @Valid PublicMessageModel publicMessageModel, BindingResult result) {
        return result.hasErrors() ? Response.error(result.getAllErrors()) : Response.ok(publicMessageService.create(publicMessageModel));
    }
}
