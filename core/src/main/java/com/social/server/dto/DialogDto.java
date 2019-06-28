package com.social.server.dto;

import com.social.server.entity.Dialog;
import com.social.server.util.DateFormatterUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class DialogDto {
    private long id;
    private List<UserDto> users;
    private String lastMessage;
    private String dateLastMessage;

    public static DialogDto of(Dialog entity) {
        if (entity == null) {
            log.warn("Entity Dialog is null");
            return null;
        }
        DialogDto dialogDto = new DialogDto();
        dialogDto.setId(entity.getId());
        dialogDto.setLastMessage(entity.getLastMessage());
        dialogDto.setUsers(UserDto.of(entity.getUsers()));
        dialogDto.setDateLastMessage(DateFormatterUtil.withTimeFormat(entity.getDateLastMessage()));
        return dialogDto;
    }

    public static List<DialogDto> of(List<Dialog> dialogs) {
        return dialogs.stream()
                .map(DialogDto::of)
                .collect(Collectors.toList());
    }
}
