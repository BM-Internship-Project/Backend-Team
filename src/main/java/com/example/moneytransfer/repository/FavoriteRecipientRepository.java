package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.FavoriteRecipient;
import com.example.moneytransfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteRecipientRepository extends JpaRepository<FavoriteRecipient, Long> {
    List<FavoriteRecipient> findByUser(User user);
}
