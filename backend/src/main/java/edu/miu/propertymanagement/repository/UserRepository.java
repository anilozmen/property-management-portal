package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> getUsersByUserType(String userType, Pageable pageable);
    List<User> findAllByOrderByIdDesc();
    List<User> findAllByUserTypeIsNotOrderByIdDesc(String userType);
}
