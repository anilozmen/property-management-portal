package edu.miu.propertymanagement.entity;

import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First Name cannot be blank")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Email Address cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    private String phoneNumber;

    private boolean deleted = Boolean.FALSE;

    @Column(name="user_type", insertable = false, updatable = false)
    private String userType;

    @Column(columnDefinition = "boolean default false")
    private boolean isEmailVerified;

    private String emailVerificationToken;

    private LocalDateTime emailVerificationTokenExpiry;

    @Column(columnDefinition = "int default 0")
    private Integer emailVerificationAttempts;
}
