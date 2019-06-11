package com.social.server.service.impl;

import com.social.server.dao.GroupRepository;
import com.social.server.dao.PublicMessageRepository;
import com.social.server.dao.UserRepository;
import com.social.server.dto.PublicMessageDto;
import com.social.server.entity.PublicMessage;
import com.social.server.service.PublicMessageService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicMessageServiceImpl implements PublicMessageService {

    private final PublicMessageRepository publicMessageRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public PublicMessageServiceImpl(PublicMessageRepository publicMessageRepository,
                                    UserRepository userRepository,
                                    GroupRepository groupRepository) {
        this.publicMessageRepository = publicMessageRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public PublicMessageDto create(PublicMessageDto messageDto) {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setMessage(messageDto.getMessage());
        publicMessage.setCreateDate(LocalDateTime.now());
        publicMessage.setSender(userRepository.getOne(messageDto.getSenderId()));
        if (messageDto.isRecipientUser()) {
            publicMessage.setUser(userRepository.getOne(messageDto.getRecipientId()));
        } else {
            publicMessage.setGroup(groupRepository.getOne(messageDto.getRecipientId()));
        }
        return PublicMessageDto.of(publicMessageRepository.save(publicMessage));
    }

    @Override
    public List<PublicMessageDto> findByRecipientId(Long recipientId, boolean isRecipientUser) {
        val list = isRecipientUser
                ? publicMessageRepository.findByUserIdOrderByCreateDateDesc(recipientId)
                : publicMessageRepository.findByGroupIdOrderByCreateDateDesc(recipientId);
        return PublicMessageDto.of(list);
    }
}
