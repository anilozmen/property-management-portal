package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.dto.response.PropertyDto;

import java.util.List;

public interface SavedPropertyService {
    void saveProperty(long propertyId);

    List<PropertyDto> findSavedProperties();

    void deletePropertyFromSavedList(long propertyId);
}
