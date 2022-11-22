package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

