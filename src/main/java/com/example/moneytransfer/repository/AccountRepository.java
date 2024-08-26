package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Account;
import com.example.moneytransfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}
