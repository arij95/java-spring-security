package com.springjwt.security.exception.advice;

import com.springjwt.security.exception.BadRequestException;
import com.springjwt.security.exception.IllegalArgumentException;
import com.springjwt.security.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        HttpStatus status = HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(status).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.valueOf(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(status).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.valueOf(HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(status).body(e.getMessage());
    }

}
