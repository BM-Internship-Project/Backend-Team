package com.example.moneytransfer.service;

import com.example.moneytransfer.dto.UserRegistrationDto;
import com.example.moneytransfer.model.User;
import com.example.moneytransfer.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    public User registerUser(@Valid UserRegistrationDto registrationDto) throws IllegalArgumentException {
        // Check if the password is strong
        if (!isPasswordStrong(registrationDto.getPassword())) {
            throw new IllegalArgumentException("Password is not strong enough");
        }
        // Check if the email is valid
        if (!isEmailValid(registrationDto.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }
        // Check if the email is already in use
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        //check if password and confirm password match
        if(!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }
        //check date of birth is not in the future
        if(registrationDto.getDateOfBirth().after(new Date())){
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        //check date of birth is not more than 100 years ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -100);
        if(registrationDto.getDateOfBirth().before(calendar.getTime())){
            throw new IllegalArgumentException("Date of birth cannot be more than 100 years ago");
        }
        //check date of birth is not less than 18 years ago
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        if(registrationDto.getDateOfBirth().after(calendar.getTime())){
            throw new IllegalArgumentException("Date of birth cannot be less than 18 years ago");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        String accountNumber = generateAccountNumber();
        BigDecimal initialBalance = generateInitialBalance();

        // Create a new user instance
        User user = new User(registrationDto.getName(), registrationDto.getEmail(), encodedPassword, registrationDto.getCountry(), registrationDto.getDateOfBirth(), accountNumber, initialBalance);

        // Save the user to the database
        return userRepository.save(user);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
        } while (userRepository.findByAccountNumber(accountNumber).isPresent());

        return accountNumber;
    }

    private BigDecimal generateInitialBalance() {
        Random random = new Random();
        double min = 50_000.0;
        double max = 100_000.0;
        double randomValue = min + (max - min) * random.nextDouble();
        return new BigDecimal(randomValue).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean isPasswordStrong(String password) {
        // At least one digit, one lower case letter, one upper case letter, and one special character
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }

    private boolean isEmailValid(String email) {
        return StringUtils.hasText(email) && email.contains("@");
    }



    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public BigDecimal getUserBalance(String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found with account number: " + accountNumber));
        return user.getBalance();
    }

}
