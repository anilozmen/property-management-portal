package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.ListingType;
import edu.miu.propertymanagement.entity.OfferStatus;
import edu.miu.propertymanagement.entity.PropertyStatus;
import edu.miu.propertymanagement.entity.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private int id;

    private ListingType propertyListingType;
    private PropertyType propertyType;
    private PropertyStatus propertyStatus;
    private String propertyImage;
    private BigDecimal propertyPrice;
    private BigDecimal amount;
    private String message;
    private OfferStatus status;
}
