package edu.miu.propertymanagement.controller.exception_controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import edu.miu.propertymanagement.entity.dto.response.ErrorResponse;
import edu.miu.propertymanagement.exceptions.PropertyNotFoundException;

@RestControllerAdvice
class PropertyExceptionController {

    @ExceptionHandler(value = PropertyNotFoundException.class)
    public ResponseEntity<ErrorResponse> errorResponse(PropertyNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                exception.getMessage()
        ));
    }
}
