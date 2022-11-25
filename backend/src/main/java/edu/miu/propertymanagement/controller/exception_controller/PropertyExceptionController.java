package edu.miu.propertymanagement.controller.exception_controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.miu.propertymanagement.entity.dto.response.ErrorResponse;
import edu.miu.propertymanagement.exceptions.PropertyNotListedException;

@RestControllerAdvice
class PropertyExceptionController {

    @ExceptionHandler(value = PropertyNotListedException.class)
    public ErrorResponse errorResponse(PropertyNotListedException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
