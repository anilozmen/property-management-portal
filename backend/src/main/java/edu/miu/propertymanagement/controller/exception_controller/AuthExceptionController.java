package edu.miu.propertymanagement.controller.exception_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.logging.ErrorManager;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;

import edu.miu.propertymanagement.entity.dto.response.ErrorDto;
import edu.miu.propertymanagement.entity.dto.response.ErrorResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.exceptions.ErrorException;

@ControllerAdvice
public class AuthExceptionController {
    @ExceptionHandler(value = ErrorException.class)
    public ResponseEntity<ErrorResponse> exception(ErrorException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> exceptionHttpMessage(HttpMessageNotReadableException exception) {
        ErrorResponse error = new ErrorResponse(exception.getLocalizedMessage());
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionValidationHandler(ConstraintViolationException exception) {
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return ResponseEntity.ok(errorResponse);
    }
    
}
