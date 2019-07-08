package com.social.server.controller;

import com.social.server.service.ResourcesMessagesService;
import com.social.server.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ResourcesMessagesController.class)
public class ResourcesMessagesControllerTest extends CommonControllerTest {
    private final String API = "/api/v1/resources?lang=";
    private final String LOCALE = "ru";

    @MockBean
    private ResourcesMessagesService resourcesMessagesService;

    @Test
    public void successGetResources() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("test", "Test");
        when(resourcesMessagesService.getAllProperties(LOCALE)).thenReturn(properties);
        mockMvc.perform(get(API + LOCALE).header("Authorization" , API_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JsonUtil.toJson(properties)));
    }
}
