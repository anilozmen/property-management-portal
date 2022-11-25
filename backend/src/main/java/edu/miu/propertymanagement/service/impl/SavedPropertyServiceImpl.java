package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Customer;
import edu.miu.propertymanagement.entity.SavedProperty;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.repository.SavedPropertyRepo;
import edu.miu.propertymanagement.service.SavedPropertyService;
import edu.miu.propertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedPropertyServiceImpl implements SavedPropertyService {
    private final UserService userService;
    private final SavedPropertyRepo savedPropertyRepo;
    private final ModelMapper modelMapper;

    @Override
    public void saveProperty(long propertyId) {
        long customerId = userService.getLoggedInUser().getId();

        SavedProperty savedProperty = new SavedProperty();
        savedProperty.setPropertyId(propertyId);
        savedProperty.setCustomerId(customerId);

        savedPropertyRepo.save(savedProperty);
    }

    @Override
    public List<PropertyDto> findSavedProperties() {
        long customerId = userService.getLoggedInUser().getId();
        Customer customer = new Customer();
        customer.setId(customerId);

        return savedPropertyRepo.findSavedPropertiesByCustomer(customer)
                .stream()
                .map(savedProperty -> modelMapper.map(savedProperty.getProperty(), PropertyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePropertyFromSavedList(long propertyId) {
        long customerId = userService.getLoggedInUser().getId();

        savedPropertyRepo.deleteSavedPropertyByCustomerIdAndPropertyId(customerId, propertyId);
    }

    @Override
    public List<Long> findSavedPropertyIds() {
        long customerId = userService.getLoggedInUser().getId();

        return savedPropertyRepo.findSavedPropertiesByCustomerId(customerId);
    }
}
