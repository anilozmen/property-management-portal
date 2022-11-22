package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

}
