package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    @NotBlank(message = "Address 1 cannot be blank")
    @NonNull
    @Column(nullable = false, name = "address_1")
    private String address1;

    @NotBlank(message = "Address 2 cannot be blank")
    @NonNull
    @Column(name = "address_2")
    private String address2;

    @NotBlank(message = "City cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Zip Code cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String zipCode;
}
