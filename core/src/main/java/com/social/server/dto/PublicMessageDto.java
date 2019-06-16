package com.social.server.dto;

import com.social.server.entity.PublicMessage;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static util.DateFormatterUtil.viewFormat;

@Data
public class PublicMessageDto {
    private long id;
    private String message;
    private Long senderId;
    private String sender;
    private String recipient;
    private Long recipientId;
    private String createDate;
    private boolean recipientUser;

    public static PublicMessageDto of(PublicMessage message) {
        if (message != null) {
            PublicMessageDto dto = new PublicMessageDto();
            dto.setCreateDate(message.getCreateDate().format(viewFormat));
            dto.setId(message.getId());
            dto.setMessage(message.getMessage());
            dto.setSender(message.getSender().getFullName());
            dto.setRecipientUser(message.getUser() != null);
            if (dto.isRecipientUser()) {
                dto.setRecipientId(message.getUser().getId());
                dto.setRecipient(message.getUser().getName());
            } else {
                dto.setRecipientId(message.getGroup().getId());
                dto.setRecipient(message.getGroup().getName());
            }
            return dto;
        }
        return null;

    }

    public static List<PublicMessageDto> of(List<PublicMessage> messages) {
        return messages.stream()
                .map(PublicMessageDto::of)
                .collect(Collectors.toList());
    }
}