package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointException(NullPointerException nullPointerException) {
        RestApiException restApiException = new RestApiException();
        restApiException.setErrorMessage(nullPointerException.getMessage());
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(restApiException.getErrorMessage(), restApiException.getHttpStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Error_Response> handleAuthenticationException(AuthenticationException ex){
        Error_Response errorResponse = new Error_Response(ex.getErrorType().getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
