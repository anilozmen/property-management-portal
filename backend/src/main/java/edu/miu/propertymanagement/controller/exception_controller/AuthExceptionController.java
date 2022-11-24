package edu.miu.propertymanagement.controller.exception_controller;

import edu.miu.propertymanagement.entity.dto.response.ErrorResponse;
import edu.miu.propertymanagement.entity.dto.response.ValidationErrorResponse;
import edu.miu.propertymanagement.exceptions.ErrorException;
import edu.miu.propertymanagement.exceptions.UserDeactivatedException;
import edu.miu.propertymanagement.exceptions.UserNotExistsException;
import edu.miu.propertymanagement.exceptions.UserNotVerifiedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AuthExceptionController {
    @ExceptionHandler(value = ErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exception(ErrorException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        System.out.println("In handleMethodArgumentNotValidException");
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        String errorMessages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
        return new ErrorResponse(errorMessages);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> exceptionValidationHandler(ConstraintViolationException exception) {
        List<String> errorMessages = new ArrayList<ConstraintViolation>(exception.getConstraintViolations()).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(errorMessages);
        return ResponseEntity.status(400).body(validationErrorResponse);
    }

    @ExceptionHandler(value = UserNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> exceptionUserNotVerifiedExceptionHandler(UserNotVerifiedException exception) {

        ErrorResponse errorResponse = new ErrorResponse("User is not verified. Please check your email for verification link");
        return ResponseEntity.status(400).body(errorResponse);
    }
    

    @ExceptionHandler(value = UserNotExistsException.class)
    public ResponseEntity<ErrorResponse> exceptionUserNotExistsExceptionHandler(UserNotExistsException exception) {

        ErrorResponse errorResponse = new ErrorResponse("User doesnt exists.. please create ");
        return ResponseEntity.status(400).body(errorResponse);
    }

    public ResponseEntity<ErrorResponse> exceptionUserDeactivatedExceptionHandler(UserDeactivatedException exception) {
        ErrorResponse errorResponse = new ErrorResponse("The Account is disabled. You are not allow to access it.");
        return ResponseEntity.status(401).body(errorResponse);
    }
    

}
