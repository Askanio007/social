package com.social.server.dto;

import com.social.server.entity.PrivateMessage;
import com.social.server.util.DateFormatterUtil;
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
    private PhotoAndNameDto sender;
    private boolean read;

    public static PrivateMessageDto of(PrivateMessage entity) {
        if (entity == null) {
            log.warn("Entity PrivateMessage is null");
            return null;
        }

        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(entity.getId());
        privateMessageDto.setMessage(entity.getMessage());
        privateMessageDto.setSender(PhotoAndNameDto.of(entity.getSender()));
        privateMessageDto.setRead(entity.isRead());
        privateMessageDto.setCreateDate(entity.getCreateDate());
        privateMessageDto.setCreateDateView(DateFormatterUtil.withTimeFormat(entity.getCreateDate()));
        privateMessageDto.setDialogId(entity.getDialog().getId());
        return privateMessageDto;
    }

    public static List<PrivateMessageDto> of(List<PrivateMessage> entities) {
        return entities.stream()
                .map(PrivateMessageDto::of)
                .collect(Collectors.toList());
    }
}
