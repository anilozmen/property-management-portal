package edu.miu.propertymanagement.entity;

import lombok.*;
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
@SQLDelete(sql = "UPDATE property_type SET deleted = true WHERE id=?")
@FilterDef(name = "deletedPropertyTypeFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedPropertyTypeFilter", condition = "deleted = :isDeleted")
public class PropertyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Property Type Name cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String name;

    private boolean deleted = Boolean.FALSE;
}
