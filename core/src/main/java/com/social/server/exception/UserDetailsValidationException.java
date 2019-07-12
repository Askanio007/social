package com.social.server.exception;

import java.util.List;

public class UserDetailsValidationException extends ValidationException {
    public UserDetailsValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
