package com.social.server.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class GroupValidationException extends ValidationException {
    public GroupValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
