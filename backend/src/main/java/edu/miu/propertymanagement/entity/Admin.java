package edu.miu.propertymanagement.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue("1")
public class Admin extends User {

}