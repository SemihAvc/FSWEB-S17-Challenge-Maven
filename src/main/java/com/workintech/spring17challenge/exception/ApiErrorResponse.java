package com.workintech.spring17challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private  Integer status;
    private  String message;
    private  Long timestamp;


}
