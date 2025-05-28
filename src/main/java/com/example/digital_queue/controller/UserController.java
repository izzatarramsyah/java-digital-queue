package com.example.digital_queue.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_queue.dto.SuccessResponse;
import com.example.digital_queue.dto.UserRequest;
import com.example.digital_queue.dto.UserResponse;
import com.example.digital_queue.entity.User;
import com.example.digital_queue.service.UserService;
import com.example.digital_queue.utils.JWTUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        UserResponse userResponse = userService.register(request);
        SuccessResponse<UserResponse> successResponse = new SuccessResponse<>("success", userResponse);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        User user = userService.authenticate(request.getEmail(), request.getPassword());
        String token = JWTUtil.generateToken(user.getEmail());
        SuccessResponse<String> successResponse = new SuccessResponse<>("success", token);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }
}
