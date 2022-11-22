package edu.miu.propertymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import edu.miu.propertymanagement.controller.exception_controller.AuthExceptionController;
import edu.miu.propertymanagement.entity.dto.request.EmailVerificationRequest;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.EmailVerificationResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.entity.dto.response.SuccessDto;
import edu.miu.propertymanagement.exceptions.ErrorException;
import edu.miu.propertymanagement.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (registerRequest.getAccountType() == null) {
            throw new ErrorException("Account type value is required.");
        }

        if (registerRequest.getAccountType().equals("owner")) {
            authService.registerOwner(registerRequest);
        } else if (registerRequest.getAccountType().equals("customer")) {
            authService.registerCustomer(registerRequest);
        } else {
            throw new ErrorException("Account type not recognized. Only customer / owner field are accepted");
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody String email) {
        authService.resetPassword(email);
    }

    @PostMapping("/verify-email")
    public EmailVerificationResponse verifyEmail(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        return authService.verifyEmail(emailVerificationRequest);
    }
}
