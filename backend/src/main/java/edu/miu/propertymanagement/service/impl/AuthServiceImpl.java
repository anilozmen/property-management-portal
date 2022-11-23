package edu.miu.propertymanagement.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.*;
import edu.miu.propertymanagement.entity.dto.response.*;
import edu.miu.propertymanagement.repository.*;
import edu.miu.propertymanagement.service.*;
import edu.miu.propertymanagement.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final int MAX_VERIFICATION_ATTEMPTS = 6;
    private final int VERIFICATION_TOKEN_LIFE = 30;
    private final UserRepository userRepository;
    private static final int EXPIRATION = 60 * 24;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerCustomer(RegisterRequest registerRequest) {
        Customer customer = modelMapper.map(registerRequest, Customer.class);
        generateAndSetTokenDetails(customer);
        customerRepository.save(customer);
        sendVerificationEmail(customer);
    }

    @Override
    public void registerOwner(RegisterRequest registerRequest) {
        Owner owner = modelMapper.map(registerRequest, Owner.class);
        generateAndSetTokenDetails(owner);
        ownerRepository.save(owner);
        sendVerificationEmail(owner);
    }

    public void generateAndSetTokenDetails(User user) {
        Random random = new Random();
        String token = String.valueOf(random.nextInt(100000, 1000000));

        user.setEmailVerificationToken(token);
        user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusMinutes(VERIFICATION_TOKEN_LIFE));

        user.setEmailVerificationAttempts(0);
    }

    private void sendVerificationEmail(User user) {

        String mail = "Your verification PIN for the email address: " + user.getEmailVerificationToken();

        emailService.send(user.getEmail(), "Verification Token", mail);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            Random random = new Random();
            String token = String.valueOf(random.nextInt(100000, 1000000));
            createPasswordResetTokenForUser(user, token);
            emailService.send(
                    email,
                    "Reset Password",
                    "http://localhost:8080/api/v1/changePassword?token=" + token
            );

        }
    }

    @Override
    public EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        User user = userRepository.findByEmail(emailVerificationRequest.getEmail());

        if (user.getEmailVerificationAttempts() >= MAX_VERIFICATION_ATTEMPTS)
            return new EmailVerificationResponse(false, "Too many incorrect attempts");

        if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now()))
            return new EmailVerificationResponse(false, "Expired token. Please send a new one");

        boolean tokenMatch = user
                .getEmailVerificationToken()
                .equals(emailVerificationRequest.getToken());

        if (!tokenMatch) {
            user.setEmailVerificationAttempts(user.getEmailVerificationAttempts() + 1);

            userRepository.save(user);
            return new EmailVerificationResponse(false, "Invalid token");
        }

        user.setEmailVerified(true);

        userRepository.save(user);
        return new EmailVerificationResponse(true, "Verification successful");
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        passwordResetToken.setExpiryDate(new Date(cal.getTime().getTime()));
        passwordResetRepository.save(passwordResetToken);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetRepository.findByToken(token).getUser());
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email);

        generateAndSetTokenDetails(user);
        userRepository.save(user);
        sendVerificationEmail(user);
    }
}
