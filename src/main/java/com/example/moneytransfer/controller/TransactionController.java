package com.example.moneytransfer.controller;

import com.example.moneytransfer.dto.TransferRequestDto;
import com.example.moneytransfer.service.TransactionService;
import com.example.moneytransfer.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;

    @Autowired
    public TransactionController(TransactionService transactionService, JwtUtil jwtUtil) {
        this.transactionService = transactionService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody @Valid TransferRequestDto transferRequest) {
        try {
            String token = authorizationHeader.substring(7);
            String toAccount = transferRequest.getToAccount();
            BigDecimal amount = transferRequest.getAmount();
            transactionService.transferMoney(toAccount, amount, token);

            return ResponseEntity.ok("Transfer completed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the transfer.");
        }
    }
}
