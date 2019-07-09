package com.social.server.http.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.social.server.http.ErrorCode.PASSWORD_EMPTY;
import static com.social.server.http.ErrorCode.PASSWORD_INCORRECT;

@Data
public class RestorePasswordModel {
    private long id;
    @Length(min = 5, message = PASSWORD_INCORRECT)
    @NotNull(message = PASSWORD_EMPTY)
    private String password;
}
