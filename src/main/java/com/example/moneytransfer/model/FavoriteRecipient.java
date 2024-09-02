package com.example.moneytransfer.model;

import jakarta.persistence.*;

@Entity
public class FavoriteRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
}
