package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;

import java.util.List;

public interface PropertyService {
    List<ListingPropertyDto> findAll(PropertyFilterRequest propertyFilterRequest);
    List<ListingPropertyDto> findByOwnerId(long id, PropertyFilterRequest propertyFilterRequest);
    List<ListingPropertyDto> findListingProperties(PropertyFilterRequest propertyFilterRequest);
    void save(PropertyCreationDto propertyCreationDto);
    
    Property getPropertyById(long id);
    
    Long getOwnerByProperty(long propertyId);

    PropertyDto getPropertyDetailsById(long id);

    List<ListingPropertyDto> findRentedPropertiesBySize();
    
    void increaseCounterByOne(long id);

    void convertOwnerPropertiesToAvailable(long userId);
    
    void convertOwnerPropertiesToUnpublishedWhereNotCompleted(long userId);
}
