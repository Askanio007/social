package com.social.server.controller;

import com.social.server.dto.DialogDto;
import com.social.server.dto.PrivateMessageDto;
import com.social.server.http.Response;
import com.social.server.service.DialogService;
import com.social.server.service.PrivateMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DialogController.class)
public class DialogControllerTest extends CommonControllerTest {

    @MockBean
    private DialogService dialogService;
    @MockBean
    private PrivateMessageService privateMessageService;

    @Test
    public void successFindDialogList() throws Exception {
        DialogDto d = new DialogDto();
        d.setLastMessage("test");
        d.setDateLastMessage("20.10.2013");
        Mockito.when(dialogService.findBy(ID)).thenReturn(Arrays.asList(d));
        checkGetRequest("/api/v1/" + ID + "/dialog", Response.ok(Arrays.asList(d)));
    }

    @Test
    public void successFindDialogMessages() throws Exception {
        PrivateMessageDto d = new PrivateMessageDto();
        d.setMessage("test");
        d.setDialogId(ID);
        Mockito.when(privateMessageService.findLastBy(ID)).thenReturn(Arrays.asList(d));
        checkGetRequest("/api/v1/1/dialog/" + ID + "/message", Response.ok(Arrays.asList(d)));
    }

    @Test
    public void successGettingDialog() throws Exception {
        DialogDto dto = new DialogDto();
        Mockito.when(dialogService.getDialogBy(Arrays.asList(ID2, ID))).thenReturn(dto);
        checkGetRequest("/api/v1/" + ID + "/dialog/" + ID2, Response.ok(dto));
    }

    @Test
    public void failedAccessToFindDialogList() throws Exception {
        failedAccessToEndpoint("/api/v1/1/dialog");
    }

    @Test
    public void failedAccessToFindDialogMessages() throws Exception {
        failedAccessToEndpoint("/api/v1/1/dialog/3/message");
    }

    @Test
    public void failedAccessToFindDialog() throws Exception {
        failedAccessToEndpoint("/api/v1/1/dialog/3");
    }
}
