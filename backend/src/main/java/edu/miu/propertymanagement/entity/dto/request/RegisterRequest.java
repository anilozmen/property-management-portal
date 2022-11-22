package edu.miu.propertymanagement.entity.dto.request;

import javax.persistence.Transient;

import edu.miu.propertymanagement.entity.Address;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Address address;
    private String phoneNumber;
    private String accountType;
}
