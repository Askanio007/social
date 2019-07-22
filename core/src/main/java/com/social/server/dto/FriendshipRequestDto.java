package com.social.server.dto;

import com.social.server.entity.FriendshipRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class FriendshipRequestDto {
    private long id;
    private PhotoAndNameDto fromUser;
    private PhotoAndNameDto toUser;
    private boolean accept;

    public static FriendshipRequestDto of(FriendshipRequest entity) {
        if (entity == null) {
            log.warn("Entity Dialog is null");
            return null;
        }
        FriendshipRequestDto dto = new FriendshipRequestDto();
        dto.setId(entity.getId());
        dto.setAccept(entity.isAccept());
        dto.setToUser(PhotoAndNameDto.of(entity.getRequestTo()));
        dto.setFromUser(PhotoAndNameDto.of(entity.getRequestFrom()));
        return dto;
    }

    public static List<FriendshipRequestDto> of(List<FriendshipRequest> entities) {
        return entities.stream()
                .map(FriendshipRequestDto::of)
                .collect(Collectors.toList());
    }
}
