package com.social.server.service;

import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.PublicMessageRecipientType;

import java.util.List;

public interface PublicMessageService {
    PublicMessageDto create(PublicMessageDto messageDto);
    List<PublicMessageDto> findByRecipientId(Long recipientId, PublicMessageRecipientType type);
}
