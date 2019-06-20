package com.social.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.server.entity.User;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    private String email;
    private boolean enable;
    private String name;
    private String surname;
    private String fullName;
    private UserDetailsDto details;
    @JsonIgnore
    private String password;
    private List<PhotoAndNameDto> friends;
    private List<PhotoAndNameDto> groups;

    public static UserDto of(User user) {
        if (user != null) {
            UserDto dto = new UserDto();
            dto.setEmail(user.getEmail());
            dto.setId(user.getId());
            dto.setEnable(user.isEnable());
            dto.setPassword(user.getPassword());
            dto.setName(user.getName());
            dto.setSurname(user.getSurname());
            dto.setDetails(UserDetailsDto.of(user.getDetails()));
            dto.setFriends(user.getFriends().stream()
                    .limit(6)
                    .map(PhotoAndNameDto::of)
                    .collect(Collectors.toList()));
            dto.setGroups(user.getGroups().stream()
                    .limit(3)
                    .map(PhotoAndNameDto::of)
                    .collect(Collectors.toList()));
            dto.setFullName(user.getFullName());
            return dto;
        }
        return null;
    }

    public static List<UserDto> of(Set<User> users) {
        return users.stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }
}
