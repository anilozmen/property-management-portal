package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
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
    public Property getPropertyById(long id) {
        ApplicationUserDetail userDetail = getLoggedInUser();

        boolean isViewer = userDetail == null || userDetail.isCustomer();

        if (isViewer) {
            Property property = propertyRepository.findPropertyIfNotUnpublished(id).orElse(null);

            if (property == null) {
                return null;
            }

            increaseCounterByOne(id);
        }

        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public Long getOwnerByProperty(long propertyId) {
        return propertyRepository.getOwnerByProperty(propertyId);
    }

    @Override
    public PropertyDto getPropertyDetailsById(long id) {
        Property property = getPropertyById(id);

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

    private ApplicationUserDetail getLoggedInUser() {
        try {
            return ((ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
