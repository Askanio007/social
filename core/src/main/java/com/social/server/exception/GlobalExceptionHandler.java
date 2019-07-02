package com.social.server.exception;

import com.social.server.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected Response handleEntityNotFound(EntityNotFoundException e) {
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected Response handlerOtherException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error(e.getMessage());
    }
}
