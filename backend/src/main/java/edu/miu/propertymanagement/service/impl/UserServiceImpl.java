package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.response.UserDetailDto;
import edu.miu.propertymanagement.entity.dto.response.UserDto;
import edu.miu.propertymanagement.repository.UserRepository;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.util.ListMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final ListMapper listMapper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserByEmailId(String id) {
        return userRepository.findByEmail(id);
    }

    @Override
    public ApplicationUserDetail getLoggedInUser() {
        try {
            return ((ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserDto> getMostRecentUsersByTypeAndSize(String type) {
        Pageable lastTen = PageRequest.of(0, 10, Sort.by("id").descending());
        return listMapper.map(userRepository.getUsersByUserType("CUSTOMER", lastTen), UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return listMapper.map(users, UserDto.class);
    }

    @Override
    public UserDetailDto getUserById(long id) {
        return modelMapper.map(userRepository.findById(id), UserDetailDto.class);
    }

    @Override
    public void updateUserById(boolean isDeleted, long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeleted(isDeleted);
            userRepository.save(user);
        }
    }
}
