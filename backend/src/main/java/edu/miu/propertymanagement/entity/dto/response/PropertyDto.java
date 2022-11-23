package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {

    private long id;
    private String name;
//    private Owner owner;
    private String description;
    private Address address;
    private BigDecimal price;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private ListingType listingType;
    private long viewCount;
    private List<PropertyImages> propertyImages;
}
