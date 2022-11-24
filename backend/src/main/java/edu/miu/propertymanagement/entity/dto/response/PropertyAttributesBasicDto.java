package edu.miu.propertymanagement.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyAttributesBasicDto {
    private int noOfBedRooms;
    private boolean hasGarage;
    private boolean hasParking;
    private BigDecimal area;
}
