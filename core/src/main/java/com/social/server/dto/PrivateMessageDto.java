package com.social.server.dto;

import com.social.server.entity.PrivateMessage;
import com.social.server.util.DateFormatterUtil;
import com.social.server.util.ImageUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class PrivateMessageDto {
    private long id;
    private long dialogId;
    private String createDateView;
    private LocalDateTime createDate;
    private String message;
    private String senderName;
    private String image64encode;
    private long senderId;
    private boolean delivered;
    private boolean read;

    public static PrivateMessageDto of(PrivateMessage entity) {
        if (entity == null) {
            log.warn("Entity PrivateMessage is null");
            return null;
        }

        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(entity.getId());
        privateMessageDto.setMessage(entity.getMessage());
        privateMessageDto.setSenderId(entity.getSender().getId());
        privateMessageDto.setSenderName(entity.getSender().getFullName());
        privateMessageDto.setDelivered(entity.isDelivered());
        privateMessageDto.setRead(entity.isRead());
        privateMessageDto.setCreateDate(entity.getCreateDate());
        privateMessageDto.setCreateDateView(DateFormatterUtil.withTimeFormat(entity.getCreateDate()));
        privateMessageDto.setDialogId(entity.getDialog().getId());
        privateMessageDto.setImage64encode(ImageUtil.convertImageTo64encode(entity.getSender().getDetails().getImage()));
        return privateMessageDto;
    }

    public static List<PrivateMessageDto> of(List<PrivateMessage> entities) {
        return entities.stream()
                .map(PrivateMessageDto::of)
                .collect(Collectors.toList());
    }
}
