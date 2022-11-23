package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.dto.response.OwnerDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;

import java.util.List;

public interface OwnerService {
    List<OwnerDto> findOwners();

    OwnerDto findOwnerById(long id);

    void deleteOwnerById(long id);

    List<PropertyDto> findOwnerProperties();

    void saveOwnerProperty(Object propertyDto);

    List<String> findOwnerPropertyMessages(long propertyId);

    List<String> findOwnerPropertyOffers(long propertyId);

    void updateOwnerPropertyOfferById(long propertyId, long offerId, Object updateBody);
}
