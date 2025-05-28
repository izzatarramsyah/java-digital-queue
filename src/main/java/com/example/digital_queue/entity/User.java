package com.example.digital_queue.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.digital_queue.dto.UserRequest;
import com.example.digital_queue.utils.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users", schema = "digital_queue")
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    private UUID id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING) 
    private RoleEnum role;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public User(){}
    
    public User (UserRequest userRequest) {
        this.id = UUID.randomUUID();
        this.fullname = userRequest.getFullname();
        this.email = userRequest.getEmail();
        this.passwordHash = hash(userRequest.getPassword());
        this.role = RoleEnum.fromString(userRequest.getRole());
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private String hash(String password) {
        return org.springframework.util.DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
