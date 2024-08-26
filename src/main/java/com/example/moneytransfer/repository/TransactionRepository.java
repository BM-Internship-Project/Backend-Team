package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Transaction;
import com.example.moneytransfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountOrReceiverAccount(Account sender, Account receiver);
}
