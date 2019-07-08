package com.social.server.controller;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.http.Response;
import com.social.server.service.PrivateMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(PrivateMessageController.class)
public class PrivateMessageControllerTest extends CommonControllerTest {

    @MockBean
    private PrivateMessageService privateMessageService;

    @Test
    public void successSaveMessage() throws Exception {
        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        Mockito.when(privateMessageService.save(privateMessageDto)).thenReturn(privateMessageDto);
        checkPostRequest("/api/v1/dialog/message/save", privateMessageDto, Response.ok(privateMessageDto));
    }

    @Test
    public void failedAccessToSaveMessage() throws Exception {
        failedAccessToEndpoint("/api/v1/dialog/message/save");
    }
}
