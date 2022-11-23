package edu.miu.propertymanagement.service.impl;

import org.springframework.stereotype.Service;

import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.repository.UserRepository;
import edu.miu.propertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public User getUserById(String id) {
        return userRepository.findByEmail(id);
    }
}
