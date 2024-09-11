package com.example.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AccountDetailsResponse {

    private String name;
    private String email;
    private String accountNumber;
    private String country;
    private Date dateOfBirth;

}
