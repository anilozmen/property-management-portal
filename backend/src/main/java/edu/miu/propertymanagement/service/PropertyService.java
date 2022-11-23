package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.dto.response.PropertyDto;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> findAll();
    List<PropertyDto> findByOwnerId(long id);
    List<PropertyDto> findListingProperties();
}
