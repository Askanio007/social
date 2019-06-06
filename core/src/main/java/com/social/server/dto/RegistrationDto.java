package com.social.server.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegistrationDto {
    @Length(min = 4)
    private String email;
    @Length(min = 5)
    private String password;
}
