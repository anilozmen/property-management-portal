package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.dto.request.LoginRequest;
import edu.miu.propertymanagement.entity.dto.request.RegisterRequest;
import edu.miu.propertymanagement.entity.dto.response.LoginResponse;
import edu.miu.propertymanagement.repository.CustomerRepository;
import edu.miu.propertymanagement.repository.OwnerRepository;
import edu.miu.propertymanagement.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private ModelMapper modelMapper;
    private CustomerRepository customerRepository;
    private OwnerRepository ownerRepository;

    public AuthServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository, OwnerRepository ownerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.ownerRepository = ownerRepository;
    }

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
        return new LoginResponse(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @Override
    public void resetPassword(String email) {
        System.out.println(email);
    }
}
