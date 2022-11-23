package edu.miu.propertymanagement.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "messages")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    Property property;

    @OneToOne(fetch = FetchType.LAZY)
    User sender;

    @OneToOne(fetch = FetchType.LAZY)
    User receiver;

    String message;
    
    String reply;

    LocalDateTime createdDate;
}
