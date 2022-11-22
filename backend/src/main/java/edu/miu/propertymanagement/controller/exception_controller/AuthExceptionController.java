package edu.miu.propertymanagement.controller.exception_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.ErrorManager;

import javax.naming.AuthenticationException;

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
}
