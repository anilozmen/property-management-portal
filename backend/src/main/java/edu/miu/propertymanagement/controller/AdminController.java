package edu.miu.propertymanagement.controller;

import edu.miu.propertymanagement.entity.dto.request.PropertyFilterRequest;
import edu.miu.propertymanagement.entity.dto.request.UserRequestDto;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import edu.miu.propertymanagement.entity.dto.response.UserDetailDto;
import edu.miu.propertymanagement.entity.dto.response.UserDto;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final PropertyService propertyService;
    private final UserService userService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getDashboard() {

        List<ListingPropertyDto> properties = propertyService.findRentedPropertiesBySize();
        List<UserDto> users = userService.getMostRecentUsersByTypeAndSize("CUSTOMER");

        Map<String, Object> data = new HashMap<>();

        data.put("users", users);
        data.put("properties", properties);

        return data;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailDto getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUserById(@Valid @RequestBody UserRequestDto userDto, @PathVariable long id) {
        userService.updateUserById(userDto, id);
    }


    @GetMapping("/users/{id}/properties")
    @ResponseStatus(HttpStatus.OK)
    public List<ListingPropertyDto> getUserPropertiesById(@PathVariable long id) {
        return propertyService.findByOwnerId(id, new PropertyFilterRequest());
    }
}
