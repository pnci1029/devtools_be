package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    /**
     **** 로그인시 아이디 또는 비밀번호가 일치하지 않는 예외가 발생했을 때
     **/
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Error_Response> handleAuthenticationException(AuthenticationException ex){
        Error_Response errorResponse = new Error_Response(ex.getErrorType().getCode(), ex.getErrorType().getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
