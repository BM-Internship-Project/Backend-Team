package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Transaction;
import com.example.moneytransfer.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.senderAccount.accountNumber = :accountNumber OR t.receiverAccount.accountNumber = :accountNumber")
    List<Transaction> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
