package com.social.server.service;

import com.social.server.dto.EventDto;
import com.social.server.entity.Event;
import com.social.server.entity.EventType;
import org.springframework.data.domain.Page;

/**
 * Сервис событий
 * @see Event
 */
public interface EventService {
    /**
     * Создать событие для пользователя
     * @param userId - Идентификатор пользователя
     * @param targetId - Идентификатор объекта события (добавленный друг, новая группа)
     * @param targetActionName - Название объекта события (Имя добавленного друга, название группы)
     * @param type - Тип события
     */
    void createEvent(long userId, long targetId, String targetActionName, EventType type);

    /**
     * Найти события друзей пользователя
     * @param userId - идентификатор пользователя
     */
    Page<EventDto> findBy(long userId, int page);
}
