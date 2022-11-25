package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingPropertyDto {
    private long id;
    private String name;
    private BigDecimal price;
    private PropertyType propertyType;
    private ListingType listingType;
    private PropertyStatus propertyStatus;
    private PropertyAttributesBasicDto propertyAttributesBasicDto;
    private List<PropertyImages> propertyImages;
}
