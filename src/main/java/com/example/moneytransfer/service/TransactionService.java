package com.example.moneytransfer.service;

import com.example.moneytransfer.model.Transaction;
import com.example.moneytransfer.model.User;
import com.example.moneytransfer.repository.TransactionRepository;
import com.example.moneytransfer.repository.UserRepository;
import com.example.moneytransfer.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
                              TokenBlacklistService tokenBlacklistService, JwtUtil jwtUtil) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.tokenBlacklistService = tokenBlacklistService;
        this.jwtUtil = jwtUtil;
    }

    public void createTransaction(User senderAccount, User receiverAccount, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public void transferMoney(String toAccountNumber, BigDecimal amount, String token) {
        // Verify that the JWT token is not blacklisted
        if (tokenBlacklistService.isBlacklisted(token)) {
            throw new IllegalArgumentException("Your session is expired. Please log in again.");
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Get authenticated user (sender)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fromAccountEmail = authentication.getName();
        Optional<User> senderOpt = userRepository.findByEmail(fromAccountEmail);

        if (senderOpt.isEmpty()) {
            throw new IllegalArgumentException("Sender account not found.");
        }

        Optional<User> receiverOpt = userRepository.findByAccountNumber(toAccountNumber);  // Find receiver by account number

        if (receiverOpt.isEmpty()) {
            throw new IllegalArgumentException("Receiver account not found.");
        }

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        if (sender.getBalance().compareTo(amount) >= 0) { // Check if sender has sufficient balance
            sender.setBalance(sender.getBalance().subtract(amount)); // Deduct from sender
            receiver.setBalance(receiver.getBalance().add(amount)); // Add to receiver

            // Save updated user balances
            userRepository.save(sender);
            userRepository.save(receiver);

            // Create a transaction record
            createTransaction(sender, receiver, amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance in sender's account.");
        }
    }
    public List<Transaction> getTransactionHistory() {
        // Get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(userEmail);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }

        User user = userOpt.get();
        return transactionRepository.findByAccountNumber(user.getAccountNumber());
    }


}
