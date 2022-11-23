package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.PropertyStatus;
import edu.miu.propertymanagement.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {
    List<Property> findAll();

    List<Property> findByOwnerId(long id);

    List<Property> findByPropertyStatusIn(PropertyStatus...statuses);
    
    @Query("select p.owner.id from property p where p.id=:id")
    Long getOwnerByProperty(long id);
}
