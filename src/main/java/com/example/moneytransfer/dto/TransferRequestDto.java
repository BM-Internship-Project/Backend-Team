package com.example.moneytransfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {

    @NotBlank(message = "Recipient account is required")
    private String toAccount;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
