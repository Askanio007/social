package com.social.server.exception;

import java.util.List;

public class FriendshipRequestValidationException extends ValidationException {
    public FriendshipRequestValidationException(String message, List<String> errors) {
        super(message, errors);
    }
}
