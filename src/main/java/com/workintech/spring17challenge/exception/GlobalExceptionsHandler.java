package com.workintech.spring17challenge.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception) {
        log.error("ApiException occurred! Exception details: {}", exception.getMessage());

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(exception.getHttpStatus().value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception exception) {
        log.error("Exception occurred", exception);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
