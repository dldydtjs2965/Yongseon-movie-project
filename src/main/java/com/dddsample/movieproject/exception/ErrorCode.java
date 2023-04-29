package com.dddsample.movieproject.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getDetail();
    HttpStatus getHttpStatus();

    String name();
}
