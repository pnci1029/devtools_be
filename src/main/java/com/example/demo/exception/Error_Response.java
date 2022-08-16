package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Error_Response {

    private int code = HttpStatus.BAD_REQUEST.value();
    private Object error;
}
