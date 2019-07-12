package com.social.server.exception;

public class RootUserNotFoundException extends RuntimeException {

    public RootUserNotFoundException(String message) {
        super(message);
    }
}
