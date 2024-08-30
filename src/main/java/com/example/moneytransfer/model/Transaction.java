package com.example.moneytransfer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String description;
    private LocalDateTime timestamp;

    @ManyToOne
    private User senderAccount;

    @ManyToOne
    private User receiverAccount;

    // Add getters and setters here
}
