package edu.miu.propertymanagement.controller;


import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.*;
import edu.miu.propertymanagement.entity.dto.response.EmailVerificationResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.exceptions.ErrorException;
import edu.miu.propertymanagement.service.AuthService;
import edu.miu.propertymanagement.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

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
    public void resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        authService.resetPassword(passwordResetRequest.getEmail());
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String result = passwordResetService.validatePasswordResetToken(changePasswordRequest.getToken());

        if (result == null) {

            Optional user = authService.getUserByPasswordResetToken(changePasswordRequest.getToken());

            if (user.isPresent()) {
                User mUser = (User) user.get();
                authService.changeUserPassword(mUser, changePasswordRequest.getNewPassword());
            }

        }
    }

    @PostMapping("/verify-email")
    public EmailVerificationResponse verifyEmail(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        return authService.verifyEmail(emailVerificationRequest);
    }
    @GetMapping("/verify-email")
    public void verifyEmail(@RequestParam String email) {
        authService.resendVerificationToken(email);
    }
}
