package com.social.server.service;

import com.social.server.dto.EventDto;
import com.social.server.entity.EventType;

import java.util.List;

public interface EventService {
    /**
     * Создать событие для пользователя
     * @param userId - Идентификатор пользователя
     * @param type - Тип события
     * @param targetId - Идентификатор объекта события (добавленный друг, новая группа)
     * @param targetActionName - Название объекта события (Имя добавленного друга, название группы)
     */
    void createEvent(long userId, EventType type, long targetId, String targetActionName);

    List<EventDto> findBy(long userId);
}
