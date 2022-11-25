package edu.miu.propertymanagement.entity;

import org.hibernate.annotations.ColumnDefault;

import lombok.*;

import javax.persistence.*;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {

    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    @ColumnDefault(value = "false")
    private boolean isActivated;
}
