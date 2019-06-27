package com.social.server.dto;

import com.social.server.entity.PublicMessage;
import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.util.ImageUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static com.social.server.util.DateFormatterUtil.viewFormat;

@Data
public class PublicMessageDto {
    private long id;
    private String message;
    private Long senderId;
    private String avatarSender;
    private String sender;
    private String recipient;
    private PublicMessageRecipientType recipientType;
    private Long recipientId;
    private String createDate;

    public static PublicMessageDto of(PublicMessage message) {
        if (message != null) {
            PublicMessageDto dto = new PublicMessageDto();
            dto.setCreateDate(message.getCreateDate().format(viewFormat));
            dto.setId(message.getId());
            dto.setMessage(message.getMessage());
            dto.setSender(message.getSender().getFullName());
            dto.setSenderId(message.getSender().getId());
            dto.setRecipientId(message.getRecipientId());
            dto.setRecipientType(message.getRecipientType());
            dto.setAvatarSender(ImageUtil.convertImageTo64encode(message.getSender().getDetails().getImage()));
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
