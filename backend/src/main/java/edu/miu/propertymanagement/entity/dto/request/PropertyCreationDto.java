package edu.miu.propertymanagement.entity.dto.request;

import edu.miu.propertymanagement.entity.Address;
import edu.miu.propertymanagement.entity.ListingType;
import edu.miu.propertymanagement.entity.PropertyType;
import edu.miu.propertymanagement.entity.dto.response.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreationDto {
    String name;
    String description;
    BigDecimal price;
    Address address;
    ListingType listingType;
    PropertyType propertyType;
}
