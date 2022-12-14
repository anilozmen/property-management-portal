package edu.miu.propertymanagement.repository;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.SavedProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedPropertyRepo extends CrudRepository<SavedProperty, Long> {
    List<SavedProperty> findSavedPropertiesByCustomer(Customer customer);

    void deleteSavedPropertyByCustomerIdAndPropertyId(long customerId, long propertyId);

    @Query("SELECT property.id FROM SavedProperty WHERE customer.id=:customerId")
    List<Long> findSavedPropertiesByCustomerId(long customerId);
}
