package com.example.moneytransfer.repository;
import com.example.moneytransfer.model.*;
import org.springframework.data.jpa.repository.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

