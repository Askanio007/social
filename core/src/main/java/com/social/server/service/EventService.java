package com.social.server.service;

import com.social.server.dto.EventDto;
import com.social.server.entity.EventType;

import java.util.List;

public interface EventService {
    /**
     * Создать событие для пользователя
     * @param userId - Идентификатор пользователя
     * @param targetId - Идентификатор объекта события (добавленный друг, новая группа)
     * @param targetActionName - Название объекта события (Имя добавленного друга, название группы)
     * @param type - Тип события
     */
    void createEvent(long userId, long targetId, String targetActionName, EventType type);

    List<EventDto> findBy(long userId);
}
