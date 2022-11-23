package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.controller.exception_controller.AuthExceptionController;
import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.*;
import edu.miu.propertymanagement.entity.dto.response.EmailVerificationResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.service.AuthService;
import edu.miu.propertymanagement.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController extends AuthExceptionController {
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

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
}
