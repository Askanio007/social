package com.social.server.controller;

import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.PublicMessageModel;
import com.social.server.service.PublicMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(PublicMessageController.class)
public class PublicMessageControllerTest extends CommonControllerTest{
    private static final String API = "/api/v1/message/public/";

    @MockBean
    private PublicMessageService publicMessageService;

    private PublicMessageModel getCorrectPublicMessageModel() {
        PublicMessageModel model = new PublicMessageModel();
        model.setRecipientId(ID);
        model.setRecipientType(PublicMessageRecipientType.USER);
        model.setMessage("Test");
        model.setSenderId(ID2);
        return model;
    }

    @Test
    public void successFindPublicMessages() throws Exception {
        Page<PublicMessageDto> list = new PageImpl<>(Arrays.asList(new PublicMessageDto(), new PublicMessageDto()));
        Mockito.when(publicMessageService.findByRecipientId(ID, PublicMessageRecipientType.USER, 1)).thenReturn(list);
        checkGetRequest(API + ID + "/" + PublicMessageRecipientType.USER, Response.ok(list));
    }

    @Test
    public void successSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        PublicMessageDto dto = new PublicMessageDto();
        Mockito.when(publicMessageService.create(model)).thenReturn(dto);
        checkPostRequest(API + "/save", model, Response.ok(dto));
    }

    @Test
    public void incorrectSenderIdToSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        model.setSenderId(0);
        checkSavePublicMessageModel(model, ErrorCode.ID_IS_EMPTY);
    }

    @Test
    public void incorrectRecipientIdToSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        model.setRecipientId(0);
        checkSavePublicMessageModel(model, ErrorCode.ID_IS_EMPTY);
    }

    @Test
    public void incorrectRecipientTypeToSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        model.setRecipientType(null);
        checkSavePublicMessageModel(model, ErrorCode.RECIPIENT_TYPE_IS_EMPTY);
    }

    @Test
    public void emptyMessageToSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        model.setMessage("");
        checkSavePublicMessageModel(model, ErrorCode.MESSAGE_IS_EMPTY);
    }

    @Test
    public void incorrectMessageToSavePublicMessages() throws Exception {
        PublicMessageModel model = getCorrectPublicMessageModel();
        model.setMessage(null);
        checkSavePublicMessageModel(model, ErrorCode.MESSAGE_IS_EMPTY);
    }


    @Test
    public void failedAccessToFindPublicMessages() throws Exception {
        failedAccessToEndpoint(API + ID + "/" + PublicMessageRecipientType.USER);
    }

    @Test
    public void failedAccessToSavePublicMessages() throws Exception {
        failedAccessToEndpoint(API + "/save");
    }

    private void checkSavePublicMessageModel(PublicMessageModel model, String code) throws Exception {
        PublicMessageDto dto = new PublicMessageDto();
        Mockito.when(publicMessageService.create(model)).thenReturn(dto);
        checkPostRequest(API + "/save", model, Response.error(code));
    }
}
