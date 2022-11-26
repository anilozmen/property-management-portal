package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.exceptions.ErrorException;
import edu.miu.propertymanagement.repository.OfferRepository;
import edu.miu.propertymanagement.exceptions.ForbiddenException;
import edu.miu.propertymanagement.exceptions.PropertyNotFoundException;
import edu.miu.propertymanagement.repository.PropertyRepository;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.util.ListMapper;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;
    private final ListMapper listMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<ListingPropertyDto> findAll(PropertyFilterRequest propertyFilterRequest) {
        ApplicationUserDetail userDetail = getLoggedInUser();

        boolean isCustomer = (userDetail == null || userDetail.isCustomer());

        return listMapper.map(filterPropertyBuilder(isCustomer ? propertyRepository.findExceptUnpublished() : propertyRepository.findAll(), propertyFilterRequest), ListingPropertyDto.class);
    }

    @Override
    public List<ListingPropertyDto> findByOwnerId(long id, PropertyFilterRequest propertyFilterRequest) {
        return listMapper.map(filterPropertyBuilder(propertyRepository.findByOwnerId(id), propertyFilterRequest), ListingPropertyDto.class);
    }

    @Override
    public List<ListingPropertyDto> findListingProperties(PropertyFilterRequest propertyFilterRequest) {
        return listMapper.map(filterPropertyBuilder(propertyRepository.findByPropertyStatusIn(PropertyStatus.AVAILABLE, PropertyStatus.PENDING), propertyFilterRequest), ListingPropertyDto.class);
    }

    @Override
    public void save(PropertyCreationDto propertyCreationDto) {
        ApplicationUserDetail loggedInOwnerDetail = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner owner = new Owner();
        Property property = new Property();
        PropertyAttributes propertyAttributes = propertyCreationDto.getPropertyAttributes();

        if (propertyAttributes == null) propertyAttributes = new PropertyAttributes();

        owner.setId(loggedInOwnerDetail.getId());
        property.setOwner(owner);

        property.setPropertyStatus(owner.isActivated() ? PropertyStatus.AVAILABLE : PropertyStatus.UNPUBLISHED);
        property.setName(propertyCreationDto.getName());
        property.setDescription(propertyCreationDto.getDescription());
        property.setPrice(propertyCreationDto.getPrice());
        property.setAddress(propertyCreationDto.getAddress());
        property.setListingType(propertyCreationDto.getListingType());
        property.setPropertyType(propertyCreationDto.getPropertyType());
        property.setPropertyAttributes(propertyAttributes);

        propertyRepository.save(property);
    }

    @Override
    public Property getPropertyByIdAndIncrementView(long id, boolean incrementCounter) {
        ApplicationUserDetail userDetail = getLoggedInUser();

        boolean isViewer = userDetail == null || userDetail.isCustomer();

        if (isViewer) {
            Property property = propertyRepository.findPropertyIfNotUnpublished(id).orElse(null);

            if (property == null) {
                return null;
            }

            if(incrementCounter) {
                increaseCounterByOne(id);
            }
        }

        return propertyRepository.findById(id).orElse(null);
    }
    

    @Override
    public Long getOwnerByProperty(long propertyId) {
        return propertyRepository.getOwnerByProperty(propertyId);
    }

    @Override
    public PropertyDto getPropertyDetailsById(long id) {
        Property property = getPropertyByIdAndIncrementView(id, true);

        if (property == null) {
            throw new PropertyNotFoundException();
        }

        return modelMapper.map(property, PropertyDto.class);
    }

    @Override
    public List<ListingPropertyDto> findRentedPropertiesBySize() {
        Pageable lastTen = PageRequest.of(0, 10, Sort.by("id").descending());

        List<Property> properties = propertyRepository.findPropertiesByPropertyStatus(PropertyStatus.COMPLETED, lastTen);
        return listMapper.map(properties, ListingPropertyDto.class);
    }

    @Override
    public void increaseCounterByOne(long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        property.setViewCount(property.getViewCount() + 1);
        propertyRepository.save(property);
    }

    private List<Property> filterPropertyBuilder(List<Property> properties, PropertyFilterRequest propertyFilterRequest) {
        Stream<Property> propertiesStream = properties.stream();

        if (propertyFilterRequest.getListingType() != null) {
            propertiesStream = propertiesStream.filter(property -> property.getListingType().toString().equals(propertyFilterRequest.getListingType()));
        }

        if (propertyFilterRequest.getPropertyType() != null) {
            propertiesStream = propertiesStream.filter(property -> property.getPropertyType().toString().equals(propertyFilterRequest.getPropertyType()));
        }

        if (propertyFilterRequest.getPriceGreaterThan() != null) {
            propertiesStream = propertiesStream.filter(property -> {
                return property.getPrice().compareTo(BigDecimal.valueOf(propertyFilterRequest.getPriceGreaterThan())) >= 0;
            });
        }

        if (propertyFilterRequest.getPriceLessThan() != null) {
            propertiesStream = propertiesStream.filter(property -> {
                return property.getPrice().compareTo(BigDecimal.valueOf(propertyFilterRequest.getPriceLessThan())) < 1;
            });
        }

        return propertiesStream.collect(Collectors.toList());
    }

    @Override
    public void convertOwnerPropertiesToAvailable(long userId) {
        propertyRepository.convertOwnerPropertiesToAvailable(userId);
    }

    @Override
    public GenericActivityResponse complete(long id) {
        Property property = propertyRepository.findById(id).get();

        try {
            validateStatusChange(property);
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }

        property.setPropertyStatus(PropertyStatus.COMPLETED);
        propertyRepository.save(property);
        return new GenericActivityResponse(true, "Completed");
        //@TODO add notification
    }

    @Override
    public GenericActivityResponse cancelContingency(long id) {
        Property property = propertyRepository.findById(id).get();

        try {
            validateStatusChange(property);
        } catch (ErrorException e) {
            return new GenericActivityResponse(false, e.getMessage());
        }

        Optional<Offer> cancelledOffer = property
                .getOffers()
                .stream()
                .filter(o -> o.getStatus() == OfferStatus.APPROVED)
                .findFirst();

        if (cancelledOffer.isPresent()) {
            Offer offer = cancelledOffer.get();
            offer.setStatus(OfferStatus.REJECTED);

            offerRepository.save(offer);
        }

        if (property.getOffers().stream().anyMatch(o -> o.getStatus() == OfferStatus.CREATED))
            property.setPropertyStatus(PropertyStatus.PENDING);
         else
             property.setPropertyStatus(PropertyStatus.AVAILABLE);

        propertyRepository.save(property);
        return new GenericActivityResponse(true, "Cancelled");
        //@TODO add notification
    }

    private void validateStatusChange(Property property) {
        if (!isLoggedInUserOwned(property) || property.getPropertyStatus() != PropertyStatus.CONTINGENT)
            throw new ErrorException("Action not allowed in this property");
    }

    private boolean isLoggedInUserOwned(Property property) {
        if (property.getOwner().getId() != getLoggedInUser().getId()) return false;

        return true;

    }

    public void convertOwnerPropertiesToUnpublishedWhereNotCompleted(long userId) {
        propertyRepository.convertOwnerPropertiesToUnpublishedWhereNotCompleted(userId);
    }

    @Override
    public boolean isPropertyUnpublished(long propertyId) {
        return propertyRepository.getPropertyStatus(propertyId).equals(PropertyStatus.UNPUBLISHED.toString());
    }

    @Override
    public boolean isPropertyStatusComplete(long propertyId) {
        return propertyRepository.getPropertyStatus(propertyId).equals(PropertyStatus.COMPLETED.toString());
    }

    @Override
    public Property findById(long propertyId) {
        return propertyRepository.findById(propertyId).orElse(null);
    }

    private ApplicationUserDetail getLoggedInUser() {
        try {
            return ((ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updatePropertyDetailsById(ApplicationUserDetail ownerDetail, long propertyId, PropertyCreationDto propertyCreationDto) {
        Property property = findById(propertyId);

        if(property == null)
            throw new PropertyNotFoundException();
        if(property.getOwner().getId() != ownerDetail.getId())
            throw new ForbiddenException();

        PropertyAttributes propertyAttributes = propertyCreationDto.getPropertyAttributes();
        if (propertyAttributes != null) {
            PropertyAttributes existingAttr = property.getPropertyAttributes();

            if(existingAttr != null)
                propertyAttributes.setId(existingAttr.getId());

            property.setPropertyAttributes(propertyAttributes);
        }

        propertyCreationDto.getAddress().setId(property.getAddress().getId());

        property.setName(propertyCreationDto.getName());
        property.setDescription(propertyCreationDto.getDescription());
        property.setPrice(propertyCreationDto.getPrice());
        property.setAddress(propertyCreationDto.getAddress());
        property.setListingType(propertyCreationDto.getListingType());
        property.setPropertyType(propertyCreationDto.getPropertyType());

        propertyRepository.save(property);
    }
}
