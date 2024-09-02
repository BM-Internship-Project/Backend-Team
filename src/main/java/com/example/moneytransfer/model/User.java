package com.example.moneytransfer.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.*;
import java.util.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10)
    private String accountNumber;
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;


    private String country;

    @NotNull(message = "Date of Birth is required")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal balance;



    public User() {
    }

    public User(String name, String email, String password, String country, Date dateOfBirth, String accountNumber,BigDecimal balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }
}

