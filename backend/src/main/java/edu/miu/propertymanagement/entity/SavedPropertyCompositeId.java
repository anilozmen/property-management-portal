package edu.miu.propertymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedPropertyCompositeId implements Serializable {
    private long propertyId;
    private long customerId;
}
