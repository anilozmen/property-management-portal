package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {
    List<Property> findAll();

    List<Property> findByOwnerId(long id);

    List<Property> findByPropertyStatusIn(String ...statuses);
}
