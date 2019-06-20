package com.social.server.http;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Response<T> {
    private boolean success;
    private T data;
    private List<String> errors;

    public static <T> Response ok(T data) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> Response ok() {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        return response;
    }

    public static Response error(List<ObjectError> objectErrors) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrors(objectErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()));
        return response;
    }

    public static Response error(String errorCode) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrors(Arrays.asList(errorCode));
        return response;
    }
}
