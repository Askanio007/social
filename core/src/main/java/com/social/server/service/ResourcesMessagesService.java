package com.social.server.service;

import java.util.Properties;

/**
 * Сервия для получения текстовых ресурсов приложения
 */
public interface ResourcesMessagesService {

    /**
     * Получть все текстовые ресурсы по языку
     * @param locale - язык ресусров
     */
    Properties getAllProperties(String locale);
}
