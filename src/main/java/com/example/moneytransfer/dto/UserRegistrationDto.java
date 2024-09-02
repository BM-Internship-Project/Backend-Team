package com.example.moneytransfer.dto;
import jakarta.validation.constraints.*;
import java.util.*;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRegistrationDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String confirmPassword;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Date of Birth is required")
    private Date dateOfBirth;


    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String name, String email, String password, String confirmPassword ,String country, Date dateOfBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }
}
