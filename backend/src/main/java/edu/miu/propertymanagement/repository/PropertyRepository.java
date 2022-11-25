package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.ListingType;
import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.PropertyStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query("select p from property p where p.propertyStatus <> 'UNPUBLISHED'")
    List<Property> findExceptUnpublished();

    List<Property> findAll();

    List<Property> findByOwnerId(long id);

    List<Property> findByPropertyStatusIn(PropertyStatus... statuses);

    @Query("select p.owner.id from property p where p.id=:id")
    Long getOwnerByProperty(long id);

    List<Property> findPropertiesByListingTypeAndPropertyStatus(ListingType listingType,
                                                                PropertyStatus propertyStatus,
                                                                Pageable pageable);

    @Transactional
    @Modifying
    @Query("update property p set p.propertyStatus ='AVAILABLE' where p.owner.id = ?1 and p.propertyStatus='UNPUBLISHED'")
    void convertOwnerPropertiesToAvailable(long ownerId);

    @Transactional
    @Modifying
    @Query("update property p set p.propertyStatus='UNPUBLISHED' where p.owner.id= ?1 and p.propertyStatus <> 'COMPLETED'")
    void convertOwnerPropertiesToUnpublishedWhereNotCompleted(long userId);
}
