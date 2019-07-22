package com.social.server.dto;

import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.util.DateFormatterUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class PublicMessageDto {
    private long id;
    private String message;
    private PhotoAndNameDto sender;
    private String recipient;
    private PublicMessageRecipientType recipientType;
    private Long recipientId;
    private String createDate;

    public static PublicMessageDto of(PublicMessage message) {
        if (message == null) {
            log.warn("Entity PublicMessage is null");
            return null;
        }

        PublicMessageDto dto = new PublicMessageDto();
        dto.setCreateDate(DateFormatterUtil.withoutTimeFormat(message.getCreateDate()));
        dto.setId(message.getId());
        dto.setMessage(message.getMessage());
        dto.setSender(PhotoAndNameDto.of(message.getSender()));
        dto.setRecipientId(message.getRecipientId());
        dto.setRecipientType(message.getRecipientType());

        return dto;
    }

    public static List<PublicMessageDto> of(List<PublicMessage> messages) {
        return messages.stream()
                .map(PublicMessageDto::of)
                .collect(Collectors.toList());
    }
}
