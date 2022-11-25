package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.ChangeOfferStatusRequest;
import edu.miu.propertymanagement.entity.dto.request.CreateOfferRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.OfferDto;
import edu.miu.propertymanagement.exceptions.ErrorException;
import edu.miu.propertymanagement.repository.OfferRepository;
import edu.miu.propertymanagement.repository.PropertyRepository;
import edu.miu.propertymanagement.service.NotificationService;
import edu.miu.propertymanagement.service.OfferService;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.util.ListMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;
    private final ListMapper listMapper;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final NotificationService notificationService;


    @Override
    public List<OfferDto> findByCustomerId(long id) {
        return listMapper.map(offerRepository.findByCustomerId(id), OfferDto.class);
    }

    @Override
    public List<OfferDto> findAll() {
        return listMapper.map(offerRepository.findAll(), OfferDto.class);
    }


    @Override
    public List<OfferDto> findByPropertyId(long propertyId) {
        Property property = propertyRepository.findById(propertyId).get();

        verifyLoggedInOwnerAccess(property);
        return listMapper.map(property.getOffers(), OfferDto.class);
    }

    @Override
    public List<OfferDto> findCustomerOffersByPropertyId(long propertyId) {
        ApplicationUserDetail user = userService.getLoggedInUser();

        return listMapper.map(offerRepository.findByCustomerIdAndPropertyId(user.getId(), propertyId), OfferDto.class);
    }

    private void verifyLoggedInOwnerAccess(Property property) {
        long userId = userService.getLoggedInUser().getId();

        if (property.getOwner().getId() != userId)
            throw new ErrorException("Could not find property");
    }

    @Override
    public GenericActivityResponse create(CreateOfferRequest offerRequest, long propertyId) {
        Offer offer = modelMapper.map(offerRequest, Offer.class);
        ApplicationUserDetail user = userService.getLoggedInUser();

        Property property = propertyRepository.findById(propertyId).get();

        try {
            validateOfferCreate(property);
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }

        offer.setStatus(OfferStatus.CREATED);

        offer.setProperty(property);
        offer.setCustomer(new Customer() {{
            setId(user.getId());
        }});

        syncPropertyStatusOnCreate(property);

        offerRepository.save(offer);
        notificationService.sendNotificationForOffer(offer);
        return new GenericActivityResponse(true, "Offer created.");
    }

    private void syncPropertyStatusOnCreate(Property property) {
        property.setPropertyStatus(PropertyStatus.PENDING);

        propertyRepository.save(property);
    }

    private void validateOfferCreate(Property property) {
        List<PropertyStatus> allowedStatus = Arrays.asList(PropertyStatus.AVAILABLE, PropertyStatus.PENDING);

        if (property == null || !allowedStatus.contains(property.getPropertyStatus()))
            throw new ErrorException("Not Allowed");
    }

    @Override
    public GenericActivityResponse changeStatus(long id, ChangeOfferStatusRequest request) {
        Offer offer = offerRepository.findById(id).get();

        if (offer == null) return new GenericActivityResponse(false, "No offer found.");

        try {
            validateStatusChange(offer, request.getStatus());
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }
        offer.setStatus(request.getStatus());
        syncPropertyStatusOnEdit(offer);
        offerRepository.save(offer);
        notificationService.sendNotificationForOfferStatusChange(offer);

        return new GenericActivityResponse(true, "Status updated");
    }

    private void syncPropertyStatusOnEdit(Offer offer) {
        Property property = offer.getProperty();
        List<Offer> allPropertyOffers = property.getOffers();

        if (offer.getStatus() == OfferStatus.REJECTED || offer.getStatus() == OfferStatus.CANCELLED) {
            boolean hasOtherActiveOffers = allPropertyOffers
                    .stream()
                    .anyMatch(o -> o.getStatus() == OfferStatus.CREATED);

            property.setPropertyStatus(hasOtherActiveOffers ? PropertyStatus.PENDING : PropertyStatus.AVAILABLE);
        } else if (offer.getStatus() == OfferStatus.APPROVED) {
            property.setPropertyStatus(PropertyStatus.CONTINGENT);
        }

        propertyRepository.save(property);
    }

    private void validateStatusChange(Offer offer, OfferStatus status) {
        ApplicationUserDetail user = userService.getLoggedInUser();

        List<OfferStatus> allowedForOwner =Arrays.asList(OfferStatus.APPROVED, OfferStatus.REJECTED);
        List<OfferStatus> allowedForCustomer =Arrays.asList(OfferStatus.CANCELLED);

        boolean isCurrentUsersProperty = offer.getProperty().getOwner().getId() == user.getId();
        boolean isCurrentUsersOffer = offer.getProperty().getOwner().getId() != user.getId();

        boolean isAllowed = (user.isOwner() && isCurrentUsersProperty && allowedForOwner.contains(status) && offer.getProperty().getPropertyStatus() == PropertyStatus.PENDING)
                ||
                (user.isCustomer() && isCurrentUsersOffer && allowedForCustomer.contains(status) && offer.getStatus() != OfferStatus.APPROVED && offer.getProperty().getPropertyStatus() != PropertyStatus.UNPUBLISHED);

        if (!isAllowed)
            throw new ErrorException("Cannot perform given status change");
    }
}
