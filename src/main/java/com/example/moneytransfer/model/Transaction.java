package com.example.moneytransfer.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_account_number", nullable = false)
    private User senderAccount;

//    @Column(name = "sender_account_number", nullable = false)
//    private String senderAccountNumber;

    @ManyToOne
    @JoinColumn(name = "receiver_account_number", nullable = false)
    private User receiverAccount;

//    @Column(name = "receiver_account_number", nullable = false)
//    private String receiverAccountNumber;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    public Transaction() {
    }



}