package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {

    @OneToMany(mappedBy = "owner")
    private List<Property> properties;
}
