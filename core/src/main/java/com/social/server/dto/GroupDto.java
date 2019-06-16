package com.social.server.dto;

import com.social.server.entity.Group;
import com.social.server.entity.User;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class GroupDto {
    private long id;
    private String name;
    private String description;
    private String adminName;
    private String avatarPath;
    private long adminId;
    private List<PublicMessageDto> wall;
    private List<String> participant;

    public static GroupDto of(Optional<Group> optionalGroup) {
        return optionalGroup.map(GroupDto::of).orElse(null);
    }

    public static GroupDto of(Group entity) {
        if (entity != null) {
            GroupDto dto = new GroupDto();
            dto.setId(entity.getId());
            dto.setAdminId(entity.getAdmin().getId());
            dto.setAdminName(entity.getAdmin().getName());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setAvatarPath(entity.getAvatarPath());
            dto.setParticipant(entity.getUsers().stream()
                    .limit(6)
                    .map(User::getFullName)
                    .collect(Collectors.toList()));
            dto.setWall(PublicMessageDto.of(entity.getMessages()));
            return dto;
        }
        return null;
    }

    public static List<GroupDto> of(List<Group> groups) {
        return groups.stream()
                .map(GroupDto::of)
                .collect(Collectors.toList());
    }

}
