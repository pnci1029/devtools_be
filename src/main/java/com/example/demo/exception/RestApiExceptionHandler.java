package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalExeption(IllegalArgumentException illegalArgumentException) {
        RestApiException restApi = new RestApiException();
        restApi.setErrorMessage(illegalArgumentException.getMessage());
        restApi.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(restApi.getErrorMessage(), restApi.getHttpStatus());
    }

}
