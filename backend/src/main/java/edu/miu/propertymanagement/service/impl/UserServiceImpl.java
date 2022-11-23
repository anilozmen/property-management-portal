package edu.miu.propertymanagement.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
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
    public User getUserByEmailId(String id) {
        return userRepository.findByEmail(id);
    }

    @Override
    public ApplicationUserDetail getLoggedInUser() {
        try {
            return ((ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
