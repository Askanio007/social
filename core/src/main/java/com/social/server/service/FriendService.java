package com.social.server.service;

import com.social.server.dto.UserDto;
import com.social.server.entity.User;
import org.springframework.data.domain.Page;

/**
 * Сервис работы с друзьями пользователя {@param rootUserId}
 * @see User
 */
public interface FriendService extends CommonService<User, Long> {
    /**
     * Добавить пользователя в друзья
     * @param friendId - идентификатор добавляемого в друзья пользователя
     */
    void addFriend(long rootUserId, long friendId);

    /**
     * Список друзей пользователя
     */
    Page<UserDto> find(long rootUserId, int page);

    /**
     * Получить количество друзей
     */
    long count(long rootUserId);

    /**
     * Проверить, есть ли пользователь в друзьях
     * @param friendId - идентификатор пользователя, которого ищем в друзьях
     */
    boolean isFriends(long rootUserId, long friendId);

    /**
     * Удалить пользователя из друзей
     * @param friendId - идентификатор удаляемого пользователя
     */
    void remove(long rootUserId, long friendId);
}

