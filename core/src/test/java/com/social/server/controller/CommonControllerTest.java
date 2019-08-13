package com.social.server.controller;

import com.social.server.entity.User;
import com.social.server.http.Response;
import com.social.server.service.UserService;
import com.social.server.util.JsonUtil;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
public class CommonControllerTest {
    protected final static String API_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjB9.CwQD06FnA7HQkxrKozxEw87_yLzvJRfrsYCuo2vkuPYNeA6BduBQFh8m1UR7kaRyT9BxwlHrvFsNmyRXUE7cuQ";
    protected final static long ID = 1;
    protected final static long ID2 = 2;

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserService userService;

    @Before
    public void setUp() {
        User user = new User();
        user.setEnable(true);
        Mockito.when(userService.getById(0L)).thenReturn(user);
    }

    public void failedAccessToEndpoint(String endpoint) throws Exception {
        mockMvc.perform(get(endpoint))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    public void checkGetRequest(String endpoint, Response expectedResponse) throws Exception {
        mockMvc.perform(get(endpoint).header("Authorization" , API_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtil.toJson(expectedResponse)));
    }

    public void checkPostRequest(String endpoint, Object body, Response expectedResponse) throws Exception {
        checkRequest(post(endpoint), body, expectedResponse);
    }

    private void checkRequest(MockHttpServletRequestBuilder builder, Object body, Response expectedResponse) throws Exception {
        builder = builder
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization" , API_TOKEN);
        String bodyString = JsonUtil.toJson(body);
        if (bodyString != null) {
            builder = builder.content(bodyString);
        }
        mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JsonUtil.toJson(expectedResponse)));
    }

    public void checkPutRequest(String endpoint, Object body, Response expectedResponse) throws Exception {
        checkRequest(put(endpoint), body, expectedResponse);
    }

    public void checkDeleteRequest(String endpoint, Object body, Response expectedResponse) throws Exception {
        checkRequest(delete(endpoint), body, expectedResponse);
    }
}
