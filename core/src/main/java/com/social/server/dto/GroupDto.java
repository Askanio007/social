package com.social.server.dto;

import com.social.server.entity.Group;
import com.social.server.util.ImageUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class GroupDto {
    private long id;
    private String name;
    private String description;
    private String adminName;
    private String avatar64code;
    private long adminId;
    private List<PhotoAndNameDto> participant;

    public static GroupDto of(Group entity) {
        if (entity == null) {
            log.warn("Entity User is null");
            return null;
        }

        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        dto.setAdminId(entity.getAdmin().getId());
        dto.setAdminName(entity.getAdmin().getName());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAvatar64code(ImageUtil.convertImageTo64encode(entity.getImage()));
        dto.setParticipant(entity.getUsers().stream()
                .limit(6)
                .map(PhotoAndNameDto::of)
                .collect(Collectors.toList()));
        return dto;

    }

    public static List<GroupDto> of(List<Group> groups) {
        return groups.stream()
                .map(GroupDto::of)
                .collect(Collectors.toList());
    }

}
