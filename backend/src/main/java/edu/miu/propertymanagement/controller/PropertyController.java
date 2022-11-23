package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.dto.request.PropertyCreationDto;
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
    public List<PropertyDto> findAll() {
        ApplicationUserDetail user = userService.getLoggedInUser();
        if (user.isOwner())
            return propertyService.findByOwnerId(user.getId());
        if (user.isAdmin())
            return propertyService.findAll();

        return propertyService.findListingProperties();
    }

    @PostMapping
    public void createProperty(@RequestBody PropertyCreationDto propertyCreationDto) {
        propertyService.save(propertyCreationDto);
    }
}
