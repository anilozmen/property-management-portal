package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.repository.PropertyRepository;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.util.ListMapper;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final ListMapper listMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<ListingPropertyDto> findAll() {
        return listMapper.mapList(propertyRepository.findAll(), ListingPropertyDto.class);
    }

    @Override
    public List<ListingPropertyDto> findByOwnerId(long id) {
        return listMapper.mapList(propertyRepository.findByOwnerId(id), ListingPropertyDto.class);
    }

    @Override
    public List<ListingPropertyDto> findListingProperties() {
        return listMapper.mapList(propertyRepository.findByPropertyStatusIn(PropertyStatus.AVAILABLE, PropertyStatus.PENDING), ListingPropertyDto.class);
    }

    @Override
    public void save(PropertyCreationDto propertyCreationDto) {
        ApplicationUserDetail loggedInOwnerDetail = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner owner = new Owner();
        Property property = new Property();
        PropertyAttributes propertyAttributes = propertyCreationDto.getPropertyAttributes();

        if(propertyAttributes == null)
            propertyAttributes = new PropertyAttributes();

        owner.setId(loggedInOwnerDetail.getId());
        property.setOwner(owner);
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
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
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public Long getOwnerByProperty(long propertyId) {
        return propertyRepository.getOwnerByProperty(propertyId);
    }

    @Override
    public PropertyDto getPropertyDetailsById(long id) {
        return modelMapper.map(getPropertyById(id), PropertyDto.class);
    }
}
