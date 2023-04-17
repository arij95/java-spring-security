package com.springjwt.security.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
