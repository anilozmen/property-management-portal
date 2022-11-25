package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;

import java.util.List;

public interface PropertyService {
    List<ListingPropertyDto> findAll(PropertyFilterRequest propertyFilterRequest);

    List<ListingPropertyDto> findByOwnerId(long id, PropertyFilterRequest propertyFilterRequest);

    List<ListingPropertyDto> findListingProperties(PropertyFilterRequest propertyFilterRequest);

    void save(PropertyCreationDto propertyCreationDto);

    Property getPropertyByIdAndIncrementView(long id, boolean incrementView);

    Long getOwnerByProperty(long propertyId);

    PropertyDto getPropertyDetailsById(long id);

    List<ListingPropertyDto> findRentedPropertiesBySize();

    void increaseCounterByOne(long id);

    void convertOwnerPropertiesToAvailable(long userId);

    GenericActivityResponse complete(long id);

    GenericActivityResponse cancelContingency(long id);
    void convertOwnerPropertiesToUnpublishedWhereNotCompleted(long userId);

    void updatePropertyDetailsById(ApplicationUserDetail ownerDetail, long propertyId, PropertyCreationDto propertyCreationDto);
    
    boolean isPropertyUnpublished(long propertyId);

    boolean isPropertyStatusComplete(long propertyId);
    
    Property findById(long propertyId);

    GenericActivityResponse unpublish(long id);
}
