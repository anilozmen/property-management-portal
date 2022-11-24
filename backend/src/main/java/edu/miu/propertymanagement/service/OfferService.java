package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.dto.request.ChangeOfferStatusRequest;
import edu.miu.propertymanagement.entity.dto.request.CreateOfferRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.OfferDto;

import java.util.List;

public interface OfferService {
    List<OfferDto> findByCustomerId(long id);

    List<OfferDto> findAll();

    GenericActivityResponse create(CreateOfferRequest offerRequest, long propertyId);

    GenericActivityResponse changeStatus(long id, ChangeOfferStatusRequest status);

    List<OfferDto> findByPropertyId(long propertyId);

    List<OfferDto> findCustomerOffersByPropertyId(long propertyId);
}
