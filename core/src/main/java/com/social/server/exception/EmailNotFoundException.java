package com.social.server.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String message, String... params) {
        super(message);
    }
}
