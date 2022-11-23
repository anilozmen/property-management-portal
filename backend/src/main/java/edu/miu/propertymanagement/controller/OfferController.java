package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.OfferStatus;
import edu.miu.propertymanagement.entity.dto.request.ChangeOfferStatusRequest;
import edu.miu.propertymanagement.entity.dto.request.CreateOfferRequest;
import edu.miu.propertymanagement.entity.dto.response.GenericActivityResponse;
import edu.miu.propertymanagement.entity.dto.response.OfferDto;
import edu.miu.propertymanagement.service.OfferService;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @GetMapping("/offers")
    public List<OfferDto> findAll() {
        ApplicationUserDetail user = userService.getLoggedInUser();

        if (user.isCustomer())
            return offerService.findByCustomerId(user.getId());
        else if (user.isAdmin())
            return offerService.findAll();

        return new ArrayList<>();
    }

    @PostMapping("/properties/{propertyId}/offers")
    public GenericActivityResponse save(@RequestBody CreateOfferRequest offerRequest, @PathVariable long propertyId) {
        return offerService.create(offerRequest, propertyId);
    }

    @PutMapping("/properties/{property_id}/offers/{id}")
    public GenericActivityResponse changeStatus(@RequestBody ChangeOfferStatusRequest request, @PathVariable int id) {
        return offerService.changeStatus(id, request);
    }
}
