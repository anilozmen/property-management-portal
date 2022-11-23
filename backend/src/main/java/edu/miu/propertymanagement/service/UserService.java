package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;

public interface UserService {
    User getUserByEmailId(String id);
    ApplicationUserDetail getLoggedInUser();
    
    User findById(long id);
}
