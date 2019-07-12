package com.social.server.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class RegistrationValidationException extends ValidationException {

    public RegistrationValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
