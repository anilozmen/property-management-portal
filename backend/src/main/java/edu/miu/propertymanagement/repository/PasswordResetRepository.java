package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.PasswordResetToken;
import edu.miu.propertymanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
