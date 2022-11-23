package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.dto.response.OwnerDto;
import edu.miu.propertymanagement.entity.dto.response.PropertyDto;
import edu.miu.propertymanagement.service.OwnerService;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping()
    public OwnerDto getOwnerById() {
        ApplicationUserDetail owner = (ApplicationUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ownerService.findOwnerById(owner.getId());
    }

    @GetMapping("properties")
    public List<PropertyDto> getOwnerProperties() {
        return ownerService.findOwnerProperties();
    }

    @PostMapping("properties")
    public void createOwnerProperty(@RequestBody Object propertyDto) {
        ownerService.saveOwnerProperty(propertyDto);
    }

    @GetMapping("/properties/{propertyId}/messages")
    public List<String> getPropertyMessages(@PathVariable("propertyId") long propertyId) {
        return ownerService.findOwnerPropertyMessages(propertyId);
    }

    @GetMapping("/properties/{propertyId}/offers")
    public List<String> getPropertyOffers(@PathVariable("propertyId") long propertyId) {
        return ownerService.findOwnerPropertyOffers(propertyId);
    }

    @PutMapping("/properties/{propertyId}/offers/{offerId}")
    public void updatePropertyOffers(@PathVariable("propertyId") long propertyId, @PathVariable("offerId") long offerId, @RequestBody Object body) {
        ownerService.updateOwnerPropertyOfferById(propertyId, offerId, body);
    }
}
