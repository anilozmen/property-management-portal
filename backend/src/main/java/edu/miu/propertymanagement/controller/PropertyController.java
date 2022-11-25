package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;

    @GetMapping
    public List<ListingPropertyDto> findAll(@RequestParam(name = "listing_type", required = false) String listingType,
                                            @RequestParam(name = "property_type", required = false) String propertyType,
                                            @RequestParam(name = "price_lt", required = false) Double priceLt,
                                            @RequestParam(name = "price_gt", required = false) Double priceGt
                                            ) {
        ApplicationUserDetail user = userService.getLoggedInUser();

        PropertyFilterRequest propertyFilterRequest = new PropertyFilterRequest();
        propertyFilterRequest.setPropertyType(propertyType);
        propertyFilterRequest.setListingType(listingType);
        propertyFilterRequest.setPriceGreaterThan(priceGt);
        propertyFilterRequest.setPriceLessThan(priceLt);

        if (user != null) {
            if (user.isOwner())
                return propertyService.findByOwnerId(user.getId(), propertyFilterRequest);
            if (user.isAdmin())
                return propertyService.findAll(propertyFilterRequest);
        }

        return propertyService.findListingProperties(propertyFilterRequest);
    }

    @PostMapping
    public void createProperty(@RequestBody PropertyCreationDto propertyCreationDto) {
        propertyService.save(propertyCreationDto);
    }

    @GetMapping("/{id}")
    public PropertyDto getPropertyById(@PathVariable("id") long propertyId) {
        return propertyService.getPropertyDetailsById(propertyId);
    }

    @PutMapping("/{id}")
    public void updatePropertyById(@PathVariable("id") long propertyId, @RequestBody PropertyCreationDto propertyCreationDto) {
        ApplicationUserDetail ownerDetail = userService.getLoggedInUser();

        propertyService.updatePropertyDetailsById(ownerDetail, propertyId, propertyCreationDto);
    }
}
