package com.social.server.service;

import com.social.server.dto.FriendshipRequestDto;
import com.social.server.entity.FriendshipRequest;
import com.social.server.entity.UserRelation;
import com.social.server.http.model.FriendshipRequestModel;

import java.util.List;

/**
 * Сервия запросов на дружбу
 * @see FriendshipRequest
 */
public interface FriendshipRequestService extends CommonService<FriendshipRequest, Long> {
    /**
     * Создать запрос
     * @param dto - модель запроса
     */
    void create(FriendshipRequestModel dto);

    /**
     * Принять запрос
     * @param friendshipRequestId - идентификатор запроса
     */
    void accept(long friendshipRequestId);

    /**
     * Отклонить запрос
     * @param friendshipRequestId - идентификатор запроса
     */
    void decline(long friendshipRequestId);

    /**
     * Список запросов
     * @param userId - идентификатор пользователя, которому направлены запросы
     */
    List<FriendshipRequestDto> find(long userId);

    /**
     * Проверить, есть ли запрос в друзья от одного пользователя к другому
     * @param rootUserId - от которого идёт запрос
     * @param userId - которому идёт запрос
     */
    boolean isFriendRequest(long rootUserId, long userId);

    /**
     * Получить отношение пользователей с точки зрения {@param rootUserId}
     * @return {@link UserRelation}
     */
    UserRelation getRelation(long rootUserId, long userId);
}
