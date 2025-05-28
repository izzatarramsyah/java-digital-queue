package com.example.digital_queue.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class QueueEntryDTO {
    
    private Long id;
    private int queueNumber;
    private LocalDateTime joinedAt;
    private boolean called;
    private UUID userId;
    private String userFullName;

    public QueueEntryDTO(){
    }

    public QueueEntryDTO(Long id, int queueNumber, LocalDateTime joinedAt, boolean called, UUID userId, String userFullName) {
        this.id = id;
        this.queueNumber = queueNumber;
        this.joinedAt = joinedAt;
        this.called = called;
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public boolean isCalled() {
        return called;
    }

    public void setCalled(boolean called) {
        this.called = called;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    
}
