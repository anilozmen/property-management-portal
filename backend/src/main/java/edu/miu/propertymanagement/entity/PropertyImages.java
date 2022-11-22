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
@SQLDelete(sql = "UPDATE property_images SET deleted = true WHERE id=?")
@FilterDef(name = "deletedPropertyImagesFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedPropertyImagesFilter", condition = "deleted = :isDeleted")
public class PropertyImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "URL cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String url;

    private boolean deleted = Boolean.FALSE;
}
