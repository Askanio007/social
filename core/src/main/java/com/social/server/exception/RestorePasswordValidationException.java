package com.social.server.exception;

import java.util.List;

public class RestorePasswordValidationException extends ValidationException {
    public RestorePasswordValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
