package com.example.demo.exception.EveryExceptions;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.ErrorType;
import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{

    private final ErrorType errorType;

    public AuthenticationException(com.example.demo.exception.ErrorType errorType) {
        this.errorType = errorType;
    }
}
