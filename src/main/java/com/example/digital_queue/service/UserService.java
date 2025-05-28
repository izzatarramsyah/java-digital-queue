package com.example.digital_queue.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.digital_queue.dto.UserRequest;
import com.example.digital_queue.dto.UserResponse;
import com.example.digital_queue.entity.User;
import com.example.digital_queue.exception.CustomException;
import com.example.digital_queue.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) {
        String hashedInput = org.springframework.util.DigestUtils.md5DigestAsHex(password.getBytes());
        return userRepository.findByEmail(username)
            .filter(user -> user.getPasswordHash().equals(hashedInput))
            .orElseThrow(() -> new CustomException("AUTH_FAILED", "Invalid credentials", HttpStatus.UNAUTHORIZED));
    }

    public UserResponse register(UserRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new CustomException("REGISTER_FAILED", "User Already Exist", HttpStatus.UNAUTHORIZED);
        }
        User userSaved = new User(request);
        userRepository.save(userSaved);
        UserResponse UserResponse = new UserResponse( userSaved.getId(), userSaved.getFullname(), userSaved.getEmail(), userSaved.getRole().name());
        return UserResponse;
    }

}
