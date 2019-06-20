package com.social.server.http.model;

import com.social.server.http.ErrorCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class GroupModel {

    @Length(min = 2, message = ErrorCode.GROUP_NAME_INCORRECT)
    @NotNull(message = ErrorCode.GROUP_NAME_EMPTY)
    private String name;

    @Length(min = 2, message = ErrorCode.GROUP_DESCRIPTION_INCORRECT)
    @NotNull(message = ErrorCode.GROUP_DESCRIPTION_EMPTY)
    private String description;
}
