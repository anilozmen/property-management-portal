package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    @NotBlank(message = "Property Status Name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String name;

}
