package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.*;
import edu.miu.propertymanagement.entity.dto.response.*;
import edu.miu.propertymanagement.repository.*;
import edu.miu.propertymanagement.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final int MAX_VERIFICATION_ATTEMPTS = 6;
    private final int VERIFICATION_TOKEN_LIFE = 30;

    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private OwnerRepository ownerRepository;
    private EmailService emailService;

    public AuthServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository, UserRepository userRepository, OwnerRepository ownerRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.emailService = emailService;
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
        System.out.println(email);
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
    public void resendVerificationToken(String email) {
        User user = userRepository.findByEmail(email);

        generateAndSetTokenDetails(user);
        userRepository.save(user);
        sendVerificationEmail(user);
    }
}
