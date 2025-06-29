package com.reimbursement.approval_service.infrastructure.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    private static <Ex extends Exception> ResponseEntity<Object> mapResponse(Ex exception, HttpStatus status) {

        Map<Object, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status);
        response.put("errors", exception.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> genericErrorHandler(
            Exception ex) {

        return mapResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFound.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFound ex) {

        return mapResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceDuplicated.class)
    protected ResponseEntity<Object> handleResourceDuplicated(ResourceDuplicated ex) {
        return mapResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> errorHandler(
            MethodArgumentNotValidException ex) {

        Map<Object, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });

        Map<Object, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST);
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
