package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.PasswordResetToken;
import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.EmailVerificationRequest;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.EmailVerificationResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.repository.CustomerRepository;
import edu.miu.propertymanagement.repository.OwnerRepository;
import edu.miu.propertymanagement.repository.PasswordResetRepository;
import edu.miu.propertymanagement.repository.UserRepository;
import edu.miu.propertymanagement.service.AuthService;
import edu.miu.propertymanagement.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final int MAX_VERIFICATION_ATTEMPTS = 6;
    private final int VERIFICATION_TOKEN_LIFE = 30;

    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private OwnerRepository ownerRepository;
    private EmailService emailService;
    private PasswordResetRepository passwordResetRepository;
    private PasswordEncoder passwordEncoder;
    private static final int EXPIRATION = 60 * 24;

    public AuthServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository,
                           UserRepository userRepository, OwnerRepository ownerRepository,
                           EmailService emailService, PasswordResetRepository passwordResetRepository,
                           PasswordEncoder passwordEncoder) {

        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.emailService = emailService;
        this.passwordResetRepository = passwordResetRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        return new LoginResponse(loginRequest.getEmail(), loginRequest.getPassword());
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


        System.out.println("createPasswordResetTokenForUser");
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
}
