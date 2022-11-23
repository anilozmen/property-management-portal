package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.dto.response.OwnerDto;
import edu.miu.propertymanagement.service.OwnerService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    OwnerService ownerService;

    @GetMapping("/owners")
    public List<OwnerDto> getOwners() {
        return ownerService.findOwners();
    }

    @DeleteMapping("/owners/{id}")
    public void deleteOwnerById(@PathVariable("id") long id) {
        ownerService.deleteOwnerById(id);
    }
}
