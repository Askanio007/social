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
}
