package com.social.server.service;

import com.social.server.dto.PublicMessageDto;

import java.util.List;

public interface PublicMessageService {
    PublicMessageDto create(PublicMessageDto messageDto);
    List<PublicMessageDto> findByRecipientId(Long recipientId, boolean isRecipientUser);
}
