package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.entity.Sex;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import com.social.server.http.model.RegistrationModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest extends CommonControllerTest {

    private RegistrationModel getRegistrationModel() {
        RegistrationModel model = new RegistrationModel();
        model.setSex(Sex.MALE);
        model.setPassword("123456");
        model.setSurname("TEST");
        model.setEmail("test@test.com");
        model.setName("TEST");
        return model;
    }

    @Test
    public void successRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        UserDto dto = new UserDto();
        when(userService.isEmailExist(model.getEmail())).thenReturn(false);
        when(userService.registerUser(model)).thenReturn(dto);
        checkPutRequest("/api/v1/registration", model, Response.authorized(dto));
    }

    @Test
    public void emailAlreadyExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        when(userService.isEmailExist(model.getEmail())).thenReturn(true);
        checkRegistrationModel(model, ErrorCode.EMAIL_IS_USED);
    }

    @Test
    public void emptyEmailFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setEmail(null);
        checkRegistrationModel(model, ErrorCode.EMAIL_INCORRECT);
    }

    @Test
    public void incorrectEmailFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setEmail("21");
        checkRegistrationModel(model, ErrorCode.EMAIL_INCORRECT);
    }

    @Test
    public void incorrectEmailExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setEmail("2");
        checkRegistrationModel(model, ErrorCode.EMAIL_INCORRECT);
    }

    @Test
    public void incorrectNameExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setName("ed");
        checkRegistrationModel(model, ErrorCode.NAME_INCORRECT);
    }

    @Test
    public void emptyNameExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setName(null);
        checkRegistrationModel(model, ErrorCode.NAME_EMPTY);
    }

    @Test
    public void emptySurnameExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setSurname(null);
        checkRegistrationModel(model, ErrorCode.SURNAME_EMPTY);
    }

    @Test
    public void incorrectSurnameExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setSurname("2");
        checkRegistrationModel(model, ErrorCode.SURNAME_INCORRECT);
    }

    @Test
    public void emptyPasswordExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setPassword(null);
        checkRegistrationModel(model, ErrorCode.PASSWORD_EMPTY);
    }

    @Test
    public void incorrectPasswordExistFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setPassword("f2");
        checkRegistrationModel(model, ErrorCode.PASSWORD_INCORRECT);
    }

    @Test
    public void emptySexFailedRegistration() throws Exception {
        RegistrationModel model = getRegistrationModel();
        model.setSex(null);
        checkRegistrationModel(model, ErrorCode.SEX_EMPTY);
    }

    private void checkRegistrationModel(RegistrationModel model, String error)  throws Exception {
        checkPutRequest("/api/v1/registration", model, Response.error(error));
    }
}
