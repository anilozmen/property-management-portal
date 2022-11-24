package edu.miu.propertymanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PropertyAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int noOfBedRooms;
    private int noOfFloors;
    private boolean hasGarage;
    private boolean hasParking;
    private int noOfRestrooms;
    private boolean hasLaundry;
    private BigDecimal area;
}
