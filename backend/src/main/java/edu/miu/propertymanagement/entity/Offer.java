package edu.miu.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    @ManyToOne
    private Property property;
}
