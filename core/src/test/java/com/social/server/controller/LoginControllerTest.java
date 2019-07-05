package com.social.server.controller;

import com.social.server.dto.UserDto;
import com.social.server.http.ErrorCode;
import com.social.server.http.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest extends CommonControllerTest {

    @Test
    public void successLogin() throws Exception {
        UserDto user = new UserDto();
        user.setEmail("test@test.com");
        Mockito.when(userService.findBy("test@test.com", "123")).thenReturn(user);
        checkGetRequest("/api/v1/login?email=test@test.com&password=123", Response.authorized(user));
    }

    @Test
    public void failedLogin() throws Exception {
        Mockito.when(userService.findBy("test@test.com", "123")).thenReturn(null);
        checkGetRequest("/api/v1/login?email=test@test.com&password=123", Response.error(ErrorCode.LOGIN_CREDENTIALS_ERROR));
    }
}
