package com.springjwt.security.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public BadRequestException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
