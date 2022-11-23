package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.repository.PropertyRepository;
import edu.miu.propertymanagement.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PropertyDto> findAll() {
        return propertyRepository.findAll().stream().map(p -> modelMapper.map(p, PropertyDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PropertyDto> findByOwnerId(long id) {
        return null;
    }

    @Override
    public List<PropertyDto> findListingProperties() {
        return null;
    }
}
