package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.*;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
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
    public List<PropertyDto> findAll() {
        return listMapper.mapList(propertyRepository.findAll(), PropertyDto.class);
    }

    @Override
    public List<PropertyDto> findByOwnerId(long id) {
        return listMapper.mapList(propertyRepository.findByOwnerId(id), PropertyDto.class);
    }

    @Override
    public List<PropertyDto> findListingProperties() {
        return listMapper.mapList(propertyRepository.findByPropertyStatusIn(PropertyStatus.AVAILABLE, PropertyStatus.PENDING), PropertyDto.class);
    }

    @Override
    public void save(PropertyCreationDto propertyCreationDto) {
        ApplicationUserDetail loggedInOwnerDetail = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Owner owner = new Owner();
        Property property = new Property();

        owner.setId(loggedInOwnerDetail.getId());
        property.setOwner(owner);
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        property.setName(propertyCreationDto.getName());
        property.setDescription(propertyCreationDto.getDescription());
        property.setPrice(propertyCreationDto.getPrice());
        property.setAddress(propertyCreationDto.getAddress());
        property.setListingType(propertyCreationDto.getListingType());
        property.setPropertyType(propertyCreationDto.getPropertyType());

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
}
