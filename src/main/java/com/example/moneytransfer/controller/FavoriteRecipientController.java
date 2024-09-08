package com.example.moneytransfer.controller;

import com.example.moneytransfer.dto.FavoriteRecipientDto;
import com.example.moneytransfer.model.FavoriteRecipient;
import com.example.moneytransfer.service.FavoriteRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteRecipientController {

    @Autowired
    private FavoriteRecipientService favoriteRecipientService;

    @PostMapping
    public ResponseEntity<?> addFavoriteRecipient(@RequestBody FavoriteRecipientDto favoriteRecipientDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            FavoriteRecipient favoriteRecipient = favoriteRecipientService.addFavoriteRecipient(favoriteRecipientDto, email);
            return new ResponseEntity<>(favoriteRecipient, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getFavoriteRecipients() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            List<FavoriteRecipient> favoriteRecipients = favoriteRecipientService.getFavoriteRecipients(email);
            return new ResponseEntity<>(favoriteRecipients, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFavoriteRecipient(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            favoriteRecipientService.removeFavoriteRecipient(id, email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}