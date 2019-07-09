package com.social.server.service;

import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.model.PublicMessageModel;
import org.springframework.data.domain.Page;

/**
 * Сервис публичных сообщений
 * @see PublicMessage
 */
public interface PublicMessageService {
    /**
     * Создать публичное сообщение
     * @param messageDto - модель публичного сообщения
     */
    PublicMessageDto create(PublicMessageModel messageDto);

    /**
     * Найти все публичные сообщения по странично, отправленные получателю
     * @param recipientId - идентификатор получателя
     * @param type - тип получателя {@link PublicMessageRecipientType}
     * @param page - номер страницы
     */
    Page<PublicMessageDto> findByRecipientId(Long recipientId, PublicMessageRecipientType type, int page);
}
