package com.social.server.controller;

import com.social.server.dto.EventDto;
import com.social.server.http.Response;
import com.social.server.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EventController.class)
public class EventControllerTest extends CommonControllerTest {

    @MockBean
    private EventService eventService;

    @Test
    public void failedAccessToFindEventList() throws Exception {
        failedAccessToEndpoint("/api/v1/1/events");
    }

    @Test
    public void successFindEventList() throws Exception {
        EventDto eventDto = new EventDto();
        eventDto.setDescription("test");
        eventDto.setTargetActionName("test");
        when(eventService.findBy(ID, 1)).thenReturn(new PageImpl<>(Arrays.asList(eventDto)));
        checkGetRequest("/api/v1/" + ID + "/events" + "?page=1", Response.ok(new PageImpl<>(Arrays.asList(eventDto))));
    }
}
