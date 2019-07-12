package com.social.server.exception;

import com.social.server.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    protected ResponseEntity handleEmailNotFoundException(EmailNotFoundException e) {
        return ResponseEntity.status(200).body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(MailException.class)
    protected ResponseEntity handleMailException(MailException e) {
        return ResponseEntity.status(200).body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity handleGroupValidationException(ValidationException e) {
        Response response = Response.error();
        response.setErrors(e.getErrors());
        return ResponseEntity.status(200).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(500).body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handlerOtherException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(500).body(Response.error(e.getMessage()));
    }
}
