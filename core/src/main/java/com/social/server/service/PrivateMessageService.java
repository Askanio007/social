package com.social.server.service;

import com.social.server.dto.PrivateMessageDto;
import com.social.server.entity.PrivateMessage;

import java.util.List;

public interface PrivateMessageService extends CommonService<PrivateMessage, Long> {
    PrivateMessageDto save(PrivateMessageDto message);
    List<PrivateMessageDto> findLastBy(long dialogId);
}
