package com.xming.gymclubsystem.auth.oauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class LoginFailureHandler {

    @ExceptionHandler(LoginFailureExcepiton.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleLoginFailureException(LoginFailureExcepiton ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", ex.getMessage());
        result.put("error type", "Login failure");
        return result;
    }

}
