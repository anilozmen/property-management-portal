package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.repository.CustomerRepository;
import edu.miu.propertymanagement.repository.OwnerRepository;
import edu.miu.propertymanagement.service.AuthService;
import edu.miu.propertymanagement.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;
    private final AuthenticationManager authenticationManager;
    private final  UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Override
    public void registerCustomer(RegisterRequest registerRequest) {
        Customer cust = modelMapper.map(registerRequest, Customer.class);
        customerRepository.save(cust);
    }

    @Override
    public void registerOwner(RegisterRequest registerRequest) {
        Owner owner = modelMapper.map(registerRequest, Owner.class);
        ownerRepository.save(owner);
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
        System.out.println(email);
    }
}
