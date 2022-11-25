package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.PasswordResetToken;
import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.EmailVerificationRequest;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.entity.dto.response.PasswordResetResponse;
import edu.miu.propertymanagement.exceptions.UserNotExistsException;
import edu.miu.propertymanagement.exceptions.UserNotVerifiedException;
import edu.miu.propertymanagement.repository.CustomerRepository;
import edu.miu.propertymanagement.repository.OwnerRepository;
import edu.miu.propertymanagement.repository.PasswordResetRepository;
import edu.miu.propertymanagement.repository.UserRepository;
import edu.miu.propertymanagement.service.AuthService;
import edu.miu.propertymanagement.service.EmailService;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    private final UserService userService;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void registerCustomer(RegisterRequest registerRequest) {
        registerInternal(registerRequest, customerRepository, Customer.class);
    }

    @Override
    public void registerOwner(RegisterRequest registerRequest) {
        registerInternal(registerRequest, ownerRepository, Owner.class);
    }

    private void registerInternal(RegisterRequest registerRequest, CrudRepository repository, Class<? extends User> type) {
        registerRequest.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        User user = modelMapper.map(registerRequest, type);

        generateAndSetTokenDetails(user);
        repository.save(user);
        sendVerificationEmail(user);
    }

    public void generateAndSetTokenDetails(User user) {
        Random random = new Random();
        String token = String.valueOf(random.nextInt(100000, 1000000));

        user.setEmailVerificationToken(token);
        user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusMinutes(VERIFICATION_TOKEN_LIFE));

        user.setEmailVerificationAttempts(0);
    }

    private void sendVerificationEmail(User user) {

        String html = "<html><body><p>" + "Your verification PIN for the email address: " + user.getEmailVerificationToken() + "</p>";
        String html2 = "<p>You can verify your account by using this <a href= 'http://localhost:3000/verify-email'>URL</a></p></body></html>";
        String html3 = html + html2;
        emailService.sendWithHTMLBody(user.getEmail(), "Verification Token", html3);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String email = userDetails.getUsername();

        User user = userService.getUserByEmailId(email);

        if (user == null || user.isDeleted()) {
            throw new UserNotExistsException();
        }

        if (!user.isEmailVerified()) {
            throw new UserNotVerifiedException();
        }

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new LoginResponse(accessToken, refreshToken, user.getUserType());
    }

    @Override
    public PasswordResetResponse resetPassword(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UserNotExistsException();

        if (!user.isDeleted() && user.isEmailVerified()) {
            Random random = new Random();
            String token = String.valueOf(random.nextInt(100000, 1000000));
            createPasswordResetTokenForUser(user, token);

            String html = "<p>You can change your password using the <a href='http://localhost:3000/change-password?changeToken=" + token + "'>URL</a><p>";

            emailService.sendWithHTMLBody(
                    email,
                    "Reset Password",
                    html
            );

            return new PasswordResetResponse(token);
        }

        throw new UserNotVerifiedException();
    }

    @Override
    public GenericActivityResponse verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        User user = userRepository.findByEmail(emailVerificationRequest.getEmail());

        if (user.getEmailVerificationAttempts() >= MAX_VERIFICATION_ATTEMPTS)
            return new GenericActivityResponse(false, "Too many incorrect attempts");

        if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now()))
            return new GenericActivityResponse(false, "Expired token. Please send a new one");

        boolean tokenMatch = user
                .getEmailVerificationToken()
                .equals(emailVerificationRequest.getToken());

        if (!tokenMatch) {
            user.setEmailVerificationAttempts(user.getEmailVerificationAttempts() + 1);

            userRepository.save(user);
            return new GenericActivityResponse(false, "Invalid token");
        }

        user.setEmailVerified(true);

        userRepository.save(user);
        return new GenericActivityResponse(true, "Verification successful");
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
