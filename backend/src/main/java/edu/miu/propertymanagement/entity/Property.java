package edu.miu.propertymanagement.entity;

import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE property SET deleted = true WHERE id=?")
@FilterDef(name = "deletedPropertyFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedPropertyFilter", condition = "deleted = :isDeleted")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private boolean deleted = Boolean.FALSE;

}
