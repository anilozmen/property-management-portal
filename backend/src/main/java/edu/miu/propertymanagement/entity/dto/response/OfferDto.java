package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.ListingType;
import edu.miu.propertymanagement.entity.OfferStatus;
import edu.miu.propertymanagement.entity.PropertyStatus;
import edu.miu.propertymanagement.entity.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {
    private int id;
    private int propertyId;

    private ListingType propertyListingType;
    private PropertyType propertyType;
    private String propertyName;
    private PropertyStatus propertyStatus;
    private String propertyImage;
    private BigDecimal propertyPrice;
    private BigDecimal amount;
    private Date date;
    private String message;
    private OfferStatus status;
}
