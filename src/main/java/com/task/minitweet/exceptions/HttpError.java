package com.task.minitweet.exceptions;

import org.springframework.http.HttpStatus;

public class HttpError extends RuntimeException {

    private final HttpStatus httpStatus;

    public HttpError(HttpStatus httpStatus,  String message){
        super(message);
        this.httpStatus = httpStatus;
    }
}
