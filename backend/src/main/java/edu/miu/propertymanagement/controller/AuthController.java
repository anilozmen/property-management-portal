package edu.miu.propertymanagement.controller;

import org.springframework.http.HttpStatus;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController extends AuthExceptionController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest registerRequest, @RequestParam(defaultValue = "false") boolean owner) {
        if (owner) {
            authService.registerOwner(registerRequest);
        }
        authService.registerCustomer(registerRequest);
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
    @GetMapping("/verify-email")
    public void verifyEmail(@RequestParam String email) {
        authService.resendVerificationToken(email);
    }
}
