package com.myapp.myapp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExists extends Exception{
    public AlreadyExists(String message) {
        super(message);
    }
}
