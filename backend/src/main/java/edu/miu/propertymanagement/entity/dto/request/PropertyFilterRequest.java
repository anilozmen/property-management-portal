package edu.miu.propertymanagement.entity.dto.request;

import java.math.BigDecimal;

import edu.miu.propertymanagement.entity.dto.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyFilterRequest {
    String listingType;
    String propertyType;
    Double priceGreaterThan;
    Double priceLessThan;
}
