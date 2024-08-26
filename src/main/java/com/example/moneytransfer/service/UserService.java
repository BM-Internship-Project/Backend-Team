package com.example.moneytransfer.service;

import com.example.moneytransfer.dto.UserRegistrationDto;
import com.example.moneytransfer.model.User;
import com.example.moneytransfer.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(@Valid UserRegistrationDto registrationDto) throws IllegalArgumentException {
        if (!isPasswordStrong(registrationDto.getPassword())) {
            throw new IllegalArgumentException("Password is not strong enough");
        }
        if (!isEmailValid(registrationDto.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

        // Create a new user instance
        User user = new User(registrationDto.getName(), registrationDto.getEmail(), encodedPassword);

        // Save the user to the database
        return userRepository.save(user);
    }

    private boolean isPasswordStrong(String password) {
        // At least one digit, one lower case letter, one upper case letter, and one special character
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }

    private boolean isEmailValid(String email) {
        return StringUtils.hasText(email) && email.contains("@");
    }

    public User login(String email, String password) {
        return userRepository.findByEmail(email)
                //encode the password and compare it with the encoded password in the database
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }
}
