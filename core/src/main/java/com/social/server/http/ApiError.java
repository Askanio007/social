package com.social.server.http;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ApiError {
    List<String> errors;

    private ApiError(List<String> errors) {
        this.errors = errors;
    }

    public static ApiError of(List<ObjectError> objectErrors) {
        List<String> errors = objectErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiError(errors);
    }

    public static ApiError of(String errorCode) {
        return new ApiError(Arrays.asList(errorCode));
    }
}
