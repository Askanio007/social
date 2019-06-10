package com.social.server.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class RegistrationDto {
    @Length(min = 4)
    @NotNull
    private String email;
    @Length(min = 5)
    @NotNull
    private String password;
    @Length(min = 1)
    @NotNull
    private String name;
    @Length(min = 1)
    @NotNull
    private String surname;
}
