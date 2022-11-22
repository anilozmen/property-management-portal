package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    @NotBlank(message = "Property Name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Owner owner;

    @NotBlank(message = "Property Description cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String description;

    @OneToOne
    private Address address;

    @NotBlank(message = "Property Price cannot be blank")
    @NonNull
    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne
    private PropertyType propertyType;

    @OneToOne
    private PropertyStatus propertyStatus;

    @OneToOne
    private ListingType listingType;

    private long viewCount;

    @OneToMany
    @JoinColumn(name = "id_property")
    private List<PropertyImages> propertyImages;


}
