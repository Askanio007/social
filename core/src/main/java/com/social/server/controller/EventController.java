package com.social.server.controller;

import com.social.server.http.Response;
import com.social.server.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{rootUserId}/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Response find(@PathVariable long rootUserId, @RequestParam int page) {
        return Response.ok(eventService.findBy(rootUserId, page));
    }
}
