package com.example.demo.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException{

    private final ErrorType errorType;

    public AuthenticationException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
