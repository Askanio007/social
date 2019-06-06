package com.social.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.server.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private boolean enable;
    @JsonIgnore
    private String password;

    public static UserDto of(User user) {
        UserDto dto = new UserDto();
        if (user != null) {
            dto.setEmail(user.getEmail());
            dto.setId(user.getId());
            dto.setEnable(user.isEnable());
            dto.setPassword(user.getPassword());
        }
        return dto;
    }
}
