package edu.miu.propertymanagement.service;


import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.EmailVerificationRequest;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.entity.dto.response.PasswordResetResponse;

import java.util.Optional;

public interface AuthService {
    void registerCustomer(RegisterRequest registerRequest);
    void registerOwner(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    PasswordResetResponse resetPassword(String email);

    GenericActivityResponse verifyEmail(EmailVerificationRequest emailVerificationRequest);

    void createPasswordResetTokenForUser(User user, String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changeUserPassword(User user, String password);
    void resendVerificationToken(String email);

}
