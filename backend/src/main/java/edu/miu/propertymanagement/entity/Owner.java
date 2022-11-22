package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("2")
public class Owner extends User {

    @OneToMany
    private List<Property> properties;
}
