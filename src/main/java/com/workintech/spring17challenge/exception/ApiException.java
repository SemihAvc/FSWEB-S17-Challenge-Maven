package com.workintech.spring17challenge.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException  extends  RuntimeException{
    private HttpStatus httpStatus;
    public ApiException(String message, HttpStatus notFound) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
