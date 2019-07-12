package com.social.server.exception;

import java.util.List;

public class PublicMessageValidationException extends ValidationException {
    public PublicMessageValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
