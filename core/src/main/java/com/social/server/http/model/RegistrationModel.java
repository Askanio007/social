package com.social.server.http.model;

import com.social.server.entity.Sex;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.social.server.http.ErrorCode.*;

@Data
public class RegistrationModel {

    @Length(min = 4, message = EMAIL_INCORRECT)
    @NotNull(message = EMAIL_EMPTY)
    private String email;

    @Length(min = 5, message = PASSWORD_INCORRECT)
    @NotNull(message = PASSWORD_EMPTY)
    private String password;

    @Length(min = 3, message = NAME_INCORRECT)
    @NotNull(message = NAME_EMPTY)
    private String name;

    @Length(min = 3, message = SURNAME_INCORRECT)
    @NotNull(message = SURNAME_EMPTY)
    private String surname;

    @NotNull(message = SEX_EMPTY)
    private Sex sex;
}
