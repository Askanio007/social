package com.social.server.service.impl;

import com.social.server.dao.PublicMessageRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.EventType;
import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.model.PublicMessageModel;
import com.social.server.service.EventService;
import com.social.server.service.PublicMessageService;
import com.social.server.service.transactional.WriteTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicMessageServiceImpl implements PublicMessageService {

    private final PublicMessageRepository publicMessageRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Autowired
    public PublicMessageServiceImpl(PublicMessageRepository publicMessageRepository,
                                    UserRepository userRepository,
                                    EventService eventService) {
        this.publicMessageRepository = publicMessageRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    @Override
    @WriteTransactional
    public PublicMessageDto create(PublicMessageModel messageDto) {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setMessage(messageDto.getMessage());
        publicMessage.setSender(userRepository.getOne(messageDto.getSenderId()));
        publicMessage.setRecipientId(messageDto.getRecipientId());
        publicMessage.setRecipientType(messageDto.getRecipientType());

        publicMessage = publicMessageRepository.save(publicMessage);

        eventService.createEvent(messageDto.getSenderId(), publicMessage.getId(), null, EventType.ADD_PUBLIC_MESSAGE);
        return PublicMessageDto.of(publicMessage);
    }

    @Override
    public List<PublicMessageDto> findByRecipientId(Long recipientId, PublicMessageRecipientType type) {
        return PublicMessageDto.of(publicMessageRepository.findByRecipientIdAndRecipientTypeOrderByCreateDateDesc(recipientId, type));
    }
}
