package edu.miu.propertymanagement.entity.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private long id;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
}
