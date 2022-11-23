package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
}
