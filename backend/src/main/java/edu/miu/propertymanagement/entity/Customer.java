package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("3")
public class Customer extends User {
}
