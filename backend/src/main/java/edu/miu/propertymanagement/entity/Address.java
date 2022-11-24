package edu.miu.propertymanagement.entity;

import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id=?")
@FilterDef(name = "deletedAddressFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedAddressFilter", condition = "deleted = :isDeleted")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Address 1 cannot be blank")
    @Column(nullable = false, name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @NotBlank(message = "City cannot be blank")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Zip Code cannot be blank")
    @Column(nullable = false)
    private String zipCode;

    private boolean deleted = Boolean.FALSE;
}
