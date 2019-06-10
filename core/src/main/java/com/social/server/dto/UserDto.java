package com.social.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.server.entity.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    private String email;
    private boolean enable;
    private String name;
    private String surname;
    private UserDetailsDto details;
    @JsonIgnore
    private String password;
    private List<String> friends;

    public static UserDto of(User user) {
        UserDto dto = new UserDto();
        if (user != null) {
            dto.setEmail(user.getEmail());
            dto.setId(user.getId());
            dto.setEnable(user.isEnable());
            dto.setPassword(user.getPassword());
            dto.setName(user.getName());
            dto.setSurname(user.getSurname());
            dto.setDetails(UserDetailsDto.of(user.getDetails()));
            dto.setFriends(user.getFriends().stream()
                    .limit(6)
                    .map(User::getFullName)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
