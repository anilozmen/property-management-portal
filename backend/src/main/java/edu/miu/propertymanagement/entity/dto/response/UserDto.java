package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String phoneNumber;
}
