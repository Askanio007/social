package com.social.server.controller;

import com.social.server.service.ResourcesMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ResourcesMessagesController {

    private final ResourcesMessagesService resourcesMessagesService;

    @Autowired
    public ResourcesMessagesController(ResourcesMessagesService resourcesMessagesService) {
        this.resourcesMessagesService = resourcesMessagesService;
    }

    @GetMapping("/resources")
    public ResponseEntity getAllMessageResources(@RequestParam String lang) {
        return ResponseEntity.ok(resourcesMessagesService.getAllProperties(lang));

    }
}
