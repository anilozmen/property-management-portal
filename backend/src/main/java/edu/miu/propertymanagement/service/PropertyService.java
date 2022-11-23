package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> findAll();
    List<PropertyDto> findByOwnerId(long id);
    List<PropertyDto> findListingProperties();
    void save(PropertyCreationDto propertyCreationDto);
    
    Property getPropertyById(long id);
    
    Long getOwnerByProperty(long propertyId);
}
