package edu.miu.propertymanagement.entity.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private Address address;
    private String phoneNumber;
}
