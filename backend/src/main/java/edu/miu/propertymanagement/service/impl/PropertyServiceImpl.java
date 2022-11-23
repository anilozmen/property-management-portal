package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.repository.PropertyRepository;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.util.ListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final ListMapper listMapper;

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
        return listMapper.mapList(propertyRepository.findByPropertyStatusNameIn("Available", "Pending"), PropertyDto.class);
    }
}
