package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.response.UserDetailDto;
import edu.miu.propertymanagement.entity.dto.response.UserDto;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;

import java.util.List;

public interface UserService {
    User getUserByEmailId(String id);

    ApplicationUserDetail getLoggedInUser();

    User findById(long id);

    List<UserDto> getMostRecentUsersByTypeAndSize(String type);

    List<UserDto> getUsers();

    UserDetailDto getUserById(long id);

    void updateUserById(boolean isDeleted, long id);
}
