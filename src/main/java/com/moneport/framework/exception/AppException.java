package com.moneport.framework.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final String code;
    private final String message;

    public AppException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
