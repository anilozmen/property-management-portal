package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Owner;
import edu.miu.propertymanagement.entity.dto.response.OwnerDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.repository.OwnerRepository;
import edu.miu.propertymanagement.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OwnerDto> findOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(owner -> modelMapper.map(owner, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto findOwnerById(long id) {
        ApplicationUserDetail loggedInOwner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(loggedInOwner.getId() != id)
            return null; // Throw forbidden error

        Owner owner = ownerRepository.findById(id).orElse(null);

        if(owner == null)
            return null; // Throw not found error

        return modelMapper.map(owner, OwnerDto.class);
    }

    @Override
    public void deleteOwnerById(long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<PropertyDto> findOwnerProperties() {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        return propertyService.findPropertiesByOwnerId(owner.getId());

        return null;
    }

    @Override
    public void saveOwnerProperty(Object propertyDto) {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // to do
    }

    @Override
    public List<String> findOwnerPropertyMessages(long propertyId) {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;
    }

    @Override
    public List<String> findOwnerPropertyOffers(long propertyId) {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;
    }

    @Override
    public void updateOwnerPropertyOfferById(long propertyId, long offerId, Object updateBody) {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // to do
    }
}
