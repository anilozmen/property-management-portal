package edu.miu.propertymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SavedPropertyCompositeId.class)
public class SavedProperty {
    @Id
    @Column(name = "customer_id")
    private long customerId;

    @Id
    @Column(name = "property_id")
    private long propertyId;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "property_id", insertable = false, updatable = false)
    private Property property;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date savedAt;
}
