package com.social.server.service;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.entity.Dialog;
import com.social.server.entity.PrivateMessage;

import java.util.List;

/**
 * Сервия приватных сообщений
 * @see PrivateMessage
 */
public interface PrivateMessageService extends CommonService<PrivateMessage, Long> {
    /**
     * Сохранить приватное сообщение
     * @param message - модель приватного сообщения
     */
    PrivateMessageDto save(PrivateMessageDto message);

    /**
     * Найти последние 10 сообщений по идентификатору диалога {@link Dialog}
     * @param dialogId - идентификатор диалога
     */
    List<PrivateMessageDto> findLastBy(long dialogId);

    /**
     * Прочитать сообщение в диалоге
     * @param messageId - идентификатор сообщения
     */
    void readMessage(long rootUserId, long messageId);

    /**
     * Прочитать все сообщения в диалоге
     * @param dialogId - идентификатор диалога
     */
    void readMessagesByDialogId(long rootUserId, long dialogId);

    /**
     * Получить количетсво непрочитанных сообщений во всех диалогах
     * @param userId - иденртификатор пользователя
     */
    long countUnreadMessage(long userId);
}
