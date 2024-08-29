package com.example.moneytransfer.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.*;
import java.util.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

