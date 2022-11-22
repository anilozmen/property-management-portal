package edu.miu.propertymanagement.service;


import edu.miu.propertymanagement.entity.dto.request.EmailVerificationRequest;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.EmailVerificationResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;

public interface AuthService {
    void registerCustomer(RegisterRequest registerRequest);
    void registerOwner(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    void resetPassword(String email);

    EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest);

    void resendVerificationToken(String email);
}
