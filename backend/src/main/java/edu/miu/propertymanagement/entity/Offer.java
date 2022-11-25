package edu.miu.propertymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "offer")
@SQLDelete(sql = "UPDATE Offer SET deleted = true WHERE id=?")
@FilterDef(name = "deletedOfferFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedOfferFilter", condition = "deleted = :isDeleted")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotBlank(message = "Message cannot be blank")
    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @OneToOne
    private Customer customer;

    @Column(name = "date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;

    @ManyToOne
    private Property property;
}
