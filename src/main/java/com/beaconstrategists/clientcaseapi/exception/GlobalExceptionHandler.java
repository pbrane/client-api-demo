package com.beaconstrategists.clientcaseapi.exception;

import com.beaconstrategists.clientcaseapi.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFound(ResourceNotFoundException ex) {
        Error error = new Error();
        error.setReason("Resource Not Found");
        error.setCode("404");
        error.setMessage(ex.getMessage());
        error.setStatus("404");
        error.setReferenceError(URI.create("https://openapi-generator.tech/errors/404"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Error error = new Error();
        error.setReason("Validation Failed");
        error.setCode("400");
        error.setMessage(ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
        error.setStatus("400");
        error.setReferenceError(URI.create("https://openapi-generator.tech/errors/400"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Error error = new Error();
        error.setReason("Type Mismatch");
        error.setCode("400");
        error.setMessage("Invalid value for parameter '" + ex.getName() + "'");
        error.setStatus("400");
        error.setReferenceError(URI.create("https://openapi-generator.tech/errors/400"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle other exceptions...

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(Exception ex) {
        Error error = new Error();
        error.setReason("Internal Server Error");
        error.setCode("500");
        error.setMessage("An unexpected error occurred.");
        error.setStatus("500");
        error.setReferenceError(URI.create("https://openapi-generator.tech/errors/500"));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
