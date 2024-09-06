package com.example.moneytransfer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteRecipientDto {
    private String recipientName;

    private String recipientAccountNumber;
}