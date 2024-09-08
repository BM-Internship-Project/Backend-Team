package com.example.moneytransfer.service;

import com.example.moneytransfer.dto.FavoriteRecipientDto;
import com.example.moneytransfer.model.FavoriteRecipient;
import com.example.moneytransfer.model.User;
import com.example.moneytransfer.repository.FavoriteRecipientRepository;
import com.example.moneytransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteRecipientService {

    @Autowired
    private FavoriteRecipientRepository favoriteRecipientRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final String FAVORITES_CACHE_PREFIX = "favorites:";

    public FavoriteRecipient addFavoriteRecipient(FavoriteRecipientDto favoriteRecipientDTO, String email) {

        if (!isAccountNumberValid(favoriteRecipientDTO.getRecipientAccountNumber())) {
            throw new IllegalArgumentException("Account number is not valid");
        }

        User user = userService.getUserByEmail(email);

        if (doesFavoriteRecipientExist(user, favoriteRecipientDTO.getRecipientAccountNumber())) {
            throw new IllegalArgumentException("Favorite recipient with this account number already exists.");
        }

        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setUser(user);
        favoriteRecipient.setRecipientName(favoriteRecipientDTO.getRecipientName());
        favoriteRecipient.setRecipientAccountNumber(favoriteRecipientDTO.getRecipientAccountNumber());

        favoriteRecipientRepository.save(favoriteRecipient);

        String cacheKey = FAVORITES_CACHE_PREFIX + user.getId();
        redisTemplate.delete(cacheKey);

        return favoriteRecipient;
    }

    public List<FavoriteRecipient> getFavoriteRecipients(String email) {
        User user = userService.getUserByEmail(email);
        String cacheKey = FAVORITES_CACHE_PREFIX + user.getId();

        // Check cache first
        List<FavoriteRecipient> favorites = (List<FavoriteRecipient>) redisTemplate.opsForValue().get(cacheKey);

        if (favorites == null) {
            // If cache is empty, retrieve from db
            favorites = favoriteRecipientRepository.findByUser(user);
            // Update cache
            redisTemplate.opsForValue().set(cacheKey, favorites);
        }

        return favorites;
    }

    @Transactional
    public void removeFavoriteRecipient(Long id, String email) {
        User user = userService.getUserByEmail(email);

        FavoriteRecipient favoriteRecipient = favoriteRecipientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favorite recipient not found."));

        if (!favoriteRecipient.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("This recipient is not in your favorites.");
        }

        favoriteRecipientRepository.deleteById(id);

        String cacheKey = FAVORITES_CACHE_PREFIX + user.getId();
        redisTemplate.delete(cacheKey);
    }


    private boolean isAccountNumberValid(String accountNumber) {
        return userRepository.findByAccountNumber(accountNumber).isPresent();
    }

    private boolean doesFavoriteRecipientExist(User user, String recipientAccountNumber) {
        List<FavoriteRecipient> favorites = favoriteRecipientRepository.findByUser(user);

        return favorites.stream()
                .anyMatch(recipient -> recipient.getRecipientAccountNumber().equals(recipientAccountNumber));
    }
}

