package com.social.server.dto;

import com.social.server.entity.FriendshipRequest;
import lombok.Data;
import util.ImageUtil;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FriendshipRequestDto {
    private long id;
    private long fromUserId;
    private long toUserId;
    private String fromUserName;
    private String toUserName;
    private String fromUserAvatar64code;
    private boolean accept;

    public static FriendshipRequestDto of(FriendshipRequest entity) {
        if (entity != null) {
            FriendshipRequestDto dto = new FriendshipRequestDto();
            dto.setId(entity.getId());
            dto.setAccept(entity.isAccept());
            dto.setFromUserId(entity.getRequestFrom().getId());
            dto.setFromUserName(entity.getRequestFrom().getFullName());
            dto.setFromUserAvatar64code(ImageUtil.convertImageTo64encode(entity.getRequestFrom().getDetails().getImage()));
            dto.setToUserId(entity.getRequestTo().getId());
            dto.setToUserName(entity.getRequestTo().getFullName());
            return dto;
        }
        return null;
    }

    public static List<FriendshipRequestDto> of(List<FriendshipRequest> entities) {
        return entities.stream()
                .map(FriendshipRequestDto::of)
                .collect(Collectors.toList());
    }
}
