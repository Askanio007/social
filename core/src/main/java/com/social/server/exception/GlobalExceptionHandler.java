package com.social.server.exception;

import com.social.server.http.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected Response handleEntityNotFound(EntityNotFoundException e) {
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected Response handlerOtherException(Exception e) {
        return Response.error(e.getMessage());
    }
}
