package edu.miu.propertymanagement.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {

    @OneToMany(mappedBy = "owner")
    private List<Property> properties;
}
