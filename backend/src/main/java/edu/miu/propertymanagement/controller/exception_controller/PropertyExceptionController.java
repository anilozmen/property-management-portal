package edu.miu.propertymanagement.controller.exception_controller;

import com.sun.jdi.request.InvalidRequestStateException;

import edu.miu.propertymanagement.exceptions.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(value = InvalidRequestStateException.class)
    public ResponseEntity<ErrorResponse> errorResponseInvalidRequest() {
        return ResponseEntity.status(403).body(new ErrorResponse(
                "Bad request"
        ));
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedActionException(ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("You are not allowed to perform this action on the property!"));
    }
}
