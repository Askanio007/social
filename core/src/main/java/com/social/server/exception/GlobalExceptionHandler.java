package com.social.server.exception;

import com.social.server.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(500).body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handlerOtherException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(500).body(Response.error(e.getMessage()));
    }
}
