package com.social.server.dto;

import com.social.server.entity.Dialog;
import com.social.server.util.DateFormatterUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DialogDto {
    private long id;
    private List<UserDto> users;
    private String lastMessage;
    private String dateLastMessage;

    public static DialogDto of(Dialog entity) {
        if (entity == null) {
            return null;
        }

        DialogDto dialogDto = new DialogDto();
        dialogDto.setId(entity.getId());
        dialogDto.setLastMessage(entity.getLastMessage());
        dialogDto.setUsers(UserDto.of(entity.getUsers()));
        if (entity.getDateLastMessage() != null) {
            dialogDto.setDateLastMessage(entity.getDateLastMessage().format(DateFormatterUtil.viewMessageFormat));
        }
        return dialogDto;
    }

    public static List<DialogDto> of(List<Dialog> dialogs) {
        return dialogs.stream()
                .map(DialogDto::of)
                .collect(Collectors.toList());
    }
}
