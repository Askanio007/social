package com.social.server.http;

import com.social.server.dto.UserDto;
import com.social.server.util.JsonUtil;
import com.social.server.util.TokenUtil;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Response<T> {
    private boolean success;
    private String token;
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

    public static Response authorized(UserDto userDto) {
        Response response = Response.ok(userDto);
        response.setToken(TokenUtil.generateToken(userDto));
        return response;
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}
