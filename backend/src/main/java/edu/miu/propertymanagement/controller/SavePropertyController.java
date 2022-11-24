package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.dto.request.SavedPropertyRequestDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.service.SavedPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SavePropertyController {
    private final SavedPropertyService savedPropertyService;

    @GetMapping("/properties/saved")
    public List<PropertyDto> getSavedProperties() {
        return savedPropertyService.findSavedProperties();
    }

    @PostMapping("/properties/saved")
    public void addToSavedList(@RequestBody SavedPropertyRequestDto savedPropertyRequestDto) {
        savedPropertyService.saveProperty(savedPropertyRequestDto.getPropertyId());
    }

    @DeleteMapping("/properties/saved/{propertyId}")
    public void deleteFromSavedList(@PathVariable("propertyId") long propertyId) {
        savedPropertyService.deletePropertyFromSavedList(propertyId);
    }
}
