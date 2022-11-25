package edu.miu.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "property")
@SQLDelete(sql = "UPDATE property SET deleted = true WHERE id=?")
@FilterDef(name = "deletedPropertyFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedPropertyFilter", condition = "deleted = :isDeleted")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Property Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Owner owner;

    @NotBlank(message = "Property Description cannot be blank")
    @Column(nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @NotNull(message = "Property price cannot be null")
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    @ColumnDefault("0")
    private long viewCount;

    @OneToMany
    @JoinColumn(name = "id_property")
    private List<PropertyImages> propertyImages;

    @OneToMany(mappedBy = "property")
    private List<Offer> offers;

    private boolean deleted = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL)
    private PropertyAttributes propertyAttributes;
}
