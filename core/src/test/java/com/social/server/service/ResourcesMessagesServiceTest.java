package com.social.server.service;

import com.social.server.service.impl.ResourcesMessagesServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class ResourcesMessagesServiceTest {
    private final static String EXPECTED_RESOURCES_COMMON_WELCOME_MESSAGE_RU = "Добро пожаловать в лучшую социальную сеть!";
    private final static String EXPECTED_RESOURCES_COMMON_WELCOME_MESSAGE_EN = "Welcome to best of the best social network!";

    private ResourcesMessagesService resourcesMessagesService = new ResourcesMessagesServiceImpl();

    @Test
    public void getEnResources() {
        Properties properties = resourcesMessagesService.getAllProperties("en");
        Assert.assertEquals(properties.get("common.welcome.message"), EXPECTED_RESOURCES_COMMON_WELCOME_MESSAGE_EN);
    }

    @Test
    public void getRuResources() {
        Properties properties = resourcesMessagesService.getAllProperties("ru");
        Assert.assertEquals(properties.get("common.welcome.message"), EXPECTED_RESOURCES_COMMON_WELCOME_MESSAGE_RU);
    }

    @Test
    public void getDefaultLocaleResources() {
        Properties properties = resourcesMessagesService.getAllProperties("");
        Assert.assertEquals(properties.get("common.welcome.message"), EXPECTED_RESOURCES_COMMON_WELCOME_MESSAGE_RU);
    }
}
