package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Owner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
//    @Query("update users set ")
//    void convertOwnerPropertiesToAvailable(long ownerId);
}
